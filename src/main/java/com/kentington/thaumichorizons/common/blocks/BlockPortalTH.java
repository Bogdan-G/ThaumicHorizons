package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.lib.GatewayTeleporter;
import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import com.kentington.thaumichorizons.common.tiles.TileSlot;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.BlockBreakable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockPortalTH extends BlockBreakable {

   public BlockPortalTH() {
      super("portal", ThaumicHorizons.portal, false);
      this.setTickRandomly(true);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
      return null;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 1;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
      for(int l = 0; l < 4; ++l) {
         double d0 = (double)((float)p_149734_2_ + p_149734_5_.nextFloat());
         double d1 = (double)((float)p_149734_3_ + p_149734_5_.nextFloat());
         double d2 = (double)((float)p_149734_4_ + p_149734_5_.nextFloat());
         double d3 = 0.0D;
         double d4 = 0.0D;
         double d5 = 0.0D;
         int i1 = p_149734_5_.nextInt(2) * 2 - 1;
         d3 = ((double)p_149734_5_.nextFloat() - 0.5D) * 0.5D;
         d4 = ((double)p_149734_5_.nextFloat() - 0.5D) * 0.5D;
         d5 = ((double)p_149734_5_.nextFloat() - 0.5D) * 0.5D;
         if(p_149734_1_.getBlock(p_149734_2_ - 1, p_149734_3_, p_149734_4_) != this && p_149734_1_.getBlock(p_149734_2_ + 1, p_149734_3_, p_149734_4_) != this) {
            d0 = (double)p_149734_2_ + 0.5D + 0.25D * (double)i1;
            d3 = (double)(p_149734_5_.nextFloat() * 2.0F * (float)i1);
         } else {
            d2 = (double)p_149734_4_ + 0.5D + 0.25D * (double)i1;
            d5 = (double)(p_149734_5_.nextFloat() * 2.0F * (float)i1);
         }

         p_149734_1_.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
      }

   }

   @SideOnly(Side.CLIENT)
   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
      return Item.getItemById(0);
   }

   public int quantityDropped(Random p_149745_1_) {
      return 0;
   }

   public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity player) {
      if(player.ridingEntity == null && player.riddenByEntity == null && player instanceof EntityPlayerMP) {
         if(player.timeUntilPortal > 0) {
            player.timeUntilPortal = 100;
            return;
         }

         player.timeUntilPortal = 100;
         int targetX = 0;
         int targetY = 0;
         int targetZ = 0;
         int slotY;
         int slotX;
         if(world.provider.dimensionId == ThaumicHorizons.dimensionPocketId) {
            slotY = (z + 128) / 256;
            slotX = world.getBlockMetadata(x, y, z);
            switch(slotX) {
            case 0:
               targetX = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalA[0];
               targetY = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalA[1] - 2;
               targetZ = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalA[2];
               break;
            case 1:
               targetX = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalC[0];
               targetY = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalC[1] - 2;
               targetZ = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalC[2];
               break;
            case 2:
               targetX = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalB[0];
               targetY = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalB[1] - 2;
               targetZ = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalB[2];
               break;
            case 3:
               targetX = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalD[0];
               targetY = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalD[1] - 2;
               targetZ = ((PocketPlaneData)PocketPlaneData.planes.get(slotY)).portalD[2];
            }

            MinecraftServer slotZ = FMLCommonHandler.instance().getMinecraftServerInstance();
            ((EntityPlayerMP)player).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)player, 0, new GatewayTeleporter(slotZ.worldServerForDimension(ThaumicHorizons.dimensionPocketId), targetX, targetY, targetZ, player.rotationYaw));
         } else {
            slotY = y;
            slotX = x;
            int var17 = z;

            do {
               ++slotY;
            } while(world.getBlock(slotX, slotY, var17) == ThaumicHorizons.blockPortal);

            if(world.getBlock(slotX, slotY, var17) == ThaumicHorizons.blockGateway) {
               if(world.getBlock(slotX + 1, slotY, var17) == ThaumicHorizons.blockSlot) {
                  ++slotX;
               } else if(world.getBlock(slotX - 1, slotY, var17) == ThaumicHorizons.blockSlot) {
                  --slotX;
               } else if(world.getBlock(slotX, slotY, var17 + 1) == ThaumicHorizons.blockSlot) {
                  ++var17;
               } else if(world.getBlock(slotX, slotY, var17 - 1) == ThaumicHorizons.blockSlot) {
                  --var17;
               }
            }

            TileEntity te = world.getTileEntity(slotX, slotY, var17);
            if(te instanceof TileSlot) {
               TileSlot tco = (TileSlot)te;
               short var16 = 128;
               float targetYaw = 0.0F;
               switch(tco.which) {
               case 1:
                  targetZ = tco.pocketID * 256 + ((PocketPlaneData)PocketPlaneData.planes.get(tco.pocketID)).radius;
                  targetYaw = 180.0F;
                  break;
               case 2:
                  targetZ = tco.pocketID * 256 - ((PocketPlaneData)PocketPlaneData.planes.get(tco.pocketID)).radius;
                  break;
               case 3:
                  targetZ = tco.pocketID * 256;
                  targetX = ((PocketPlaneData)PocketPlaneData.planes.get(tco.pocketID)).radius;
                  targetYaw = 90.0F;
                  break;
               case 4:
                  targetZ = tco.pocketID * 256;
                  targetX = -((PocketPlaneData)PocketPlaneData.planes.get(tco.pocketID)).radius;
                  targetYaw = 270.0F;
               }

               MinecraftServer mServer = FMLCommonHandler.instance().getMinecraftServerInstance();
               ((EntityPlayerMP)player).mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)player, ThaumicHorizons.dimensionPocketId, new GatewayTeleporter(mServer.worldServerForDimension(ThaumicHorizons.dimensionPocketId), targetX, var16, targetZ, targetYaw));
            }
         }
      }

   }
}
