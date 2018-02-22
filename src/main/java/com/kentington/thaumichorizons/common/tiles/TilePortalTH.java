package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.lib.NightmareTeleporter;
import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.common.Thaumcraft;

public class TilePortalTH extends TileThaumcraft {

   public int opencount = -1;
   private int count = 0;
   public int dimension = 0;
   public int pocket = -1;


   public boolean canUpdate() {
      return true;
   }

   public double getMaxRenderDistanceSquared() {
      return 9216.0D;
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.getBoundingBox((double)(super.xCoord - 1), (double)(super.yCoord - 1), (double)(super.zCoord - 1), (double)(super.xCoord + 2), (double)(super.yCoord + 2), (double)(super.zCoord + 2));
   }

   public void updateEntity() {
      ++this.count;
      if(super.worldObj.isRemote && (this.count % 250 == 0 || this.count == 0)) {
         super.worldObj.playSound((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "thaumcraft:evilportal", 1.0F, 1.0F, false);
      }

      if(super.worldObj.isRemote && this.opencount < 30) {
         ++this.opencount;
      }

      if(!super.worldObj.isRemote && this.count % 5 == 0) {
         List ents = super.worldObj.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand(0.5D, 1.0D, 0.5D));
         if(ents.size() > 0) {
            Iterator var2 = ents.iterator();

            while(var2.hasNext()) {
               Object e = var2.next();
               EntityPlayerMP player = (EntityPlayerMP)e;
               if(player.ridingEntity == null && player.riddenByEntity == null) {
                  MinecraftServer mServer = FMLCommonHandler.instance().getMinecraftServerInstance();
                  if(player.timeUntilPortal <= 0) {
                     player.timeUntilPortal = 50;
                     int oldDim = player.dimension;
                     player.mcServer.getConfigurationManager().transferPlayerToDimension(player, this.dimension, new NightmareTeleporter(mServer.worldServerForDimension(this.dimension)));
                     if(oldDim == ThaumicHorizons.dimensionPocketId && PocketPlaneData.positionsMap.containsKey(player.getCommandSenderName())) {
                        cpw.mods.fml.common.FMLLog.info("Loading position " + PocketPlaneData.positionsMap.get(player.getCommandSenderName()));
                        player.setPositionAndUpdate(((Vec3)PocketPlaneData.positionsMap.get(player.getCommandSenderName())).xCoord, ((Vec3)PocketPlaneData.positionsMap.get(player.getCommandSenderName())).yCoord, ((Vec3)PocketPlaneData.positionsMap.get(player.getCommandSenderName())).zCoord);
                     } else if(this.pocket >= 0) {
                        PocketPlaneData.positionsMap.put(player.getCommandSenderName(), Vec3.createVectorHelper((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D));
                        cpw.mods.fml.common.FMLLog.info("Saving position " + PocketPlaneData.positionsMap.get(player.getCommandSenderName()));
                        player.setPositionAndUpdate(0.5D, 129.0D, (double)((float)(128 * (this.pocket - 3)) + 0.5F));
                        super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
                     }
                  }
               }
            }
         }
      }

      if(this.count > 250) {
         super.worldObj.playSound((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "thaumcraft:craftfail", 1.0F, 1.0F, false);
         Thaumcraft.proxy.burst(this.getWorldObj(), (double)((float)super.xCoord + 0.5F), (double)((float)super.yCoord + 0.5F), (double)((float)super.zCoord + 0.5F), 3.0F);
         super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
      }

   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("dimension", this.dimension);
      nbttagcompound.setInteger("count", this.count);
      nbttagcompound.setInteger("pocket", this.pocket);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.dimension = nbttagcompound.getInteger("dimension");
      this.count = nbttagcompound.getInteger("count");
      this.pocket = nbttagcompound.getInteger("pocket");
   }
}
