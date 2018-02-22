package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVortex;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.Block.SoundType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;

public class BlockVortex extends BlockContainer {

   IIcon icon;


   public BlockVortex() {
      super(Config.airyMaterial);
      this.setHardness(-1.0F);
      this.setResistance(20000.0F);
      this.setBlockName("ThaumicHorizons_vortex");
      this.setStepSound(new SoundType("cloth", 0.0F, 1.0F));
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public float getBlockHardness(World world, int x, int y, int z) {
      return -1.0F;
   }

   public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
      return 20000.0F;
   }

   public int getLightValue(IBlockAccess world, int x, int y, int z) {
      return 15;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess ba, int x, int y, int z) {
      this.setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
   }

   public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockVortexRI;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 1;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public Item getItemDropped(int par1, Random par2Random, int par3) {
      return Item.getItemById(0);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileVortex();
   }

   public TileEntity createNewTileEntity(World var1, int md) {
      return this.createTileEntity(var1, md);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
      return null;
   }

   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:vortex");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }

   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
      TileVortex tile;
      if(entity instanceof EntityPlayer) {
         tile = (TileVortex)world.getTileEntity(x, y, z);
         tile.aspects = new AspectList();
         int numAspects = world.rand.nextInt(4) + 1;

         for(int a = 0; a < numAspects; ++a) {
            if(world.rand.nextInt(3) == 0) {
               tile.aspects.add((Aspect)Aspect.getCompoundAspects().get(world.rand.nextInt(Aspect.getCompoundAspects().size())), world.rand.nextInt(30));
            } else {
               tile.aspects.add((Aspect)Aspect.getPrimalAspects().get(world.rand.nextInt(Aspect.getPrimalAspects().size())), world.rand.nextInt(30));
            }
         }
      }

      if(stack.getItemDamage() == 1) {
         tile = (TileVortex)world.getTileEntity(x, y, z);
         tile.cheat = true;
      }

   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(this, 1, 0));
      par3List.add(new ItemStack(this, 1, 1));
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int md) {
      TileVortex tco = (TileVortex)world.getTileEntity(x, y, z);
      MinecraftServer.getServer().worldServerForDimension(ThaumicHorizons.dimensionPocketId).setBlockToAir(0, 129, tco.dimensionID * 256);
   }
}
