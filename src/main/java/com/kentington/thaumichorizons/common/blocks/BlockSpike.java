package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSpike;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSpike extends BlockContainer {

   int type;
   IIcon icon;
   IIcon iconWood;
   IIcon iconTooth;


   public BlockSpike(int spikeType, Material mat, String name) {
      super(mat);
      this.type = spikeType;
      this.setHardness(3.0F);
      this.setResistance(3.0F);
      this.setBlockName("ThaumicHorizons_" + name);
      this.setBlockTextureName("ThaumicHorizons:spike");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 0;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockSpikeRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("iron_block");
      this.iconWood = ir.registerIcon("thaumcraft:greatwoodtop");
      this.iconTooth = ir.registerIcon("thaumichorizons:bone");
   }

   public IIcon getIcon(int par1, int par2) {
      switch(this.type) {
      case 1:
         return this.iconWood;
      case 2:
         return this.iconTooth;
      default:
         return this.icon;
      }
   }

   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
      p_149670_5_.attackEntityFrom(DamageSource.cactus, 1.0F);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileSpike spike = new TileSpike((byte)metadata, (byte)this.type);
      return spike;
   }

   public boolean canPlaceBlockOnSide(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_, int p_149742_5_) {
      ForgeDirection dir = ForgeDirection.getOrientation(p_149742_5_);
      return dir == ForgeDirection.DOWN && p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_ + 1, p_149742_4_, ForgeDirection.DOWN) || dir == ForgeDirection.UP && p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_ - 1, p_149742_4_, ForgeDirection.UP) || dir == ForgeDirection.NORTH && p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_, p_149742_4_ + 1, ForgeDirection.NORTH) || dir == ForgeDirection.SOUTH && p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_, p_149742_4_ - 1, ForgeDirection.SOUTH) || dir == ForgeDirection.WEST && p_149742_1_.isSideSolid(p_149742_2_ + 1, p_149742_3_, p_149742_4_, ForgeDirection.WEST) || dir == ForgeDirection.EAST && p_149742_1_.isSideSolid(p_149742_2_ - 1, p_149742_3_, p_149742_4_, ForgeDirection.EAST);
   }

   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
      return p_149742_1_.isSideSolid(p_149742_2_ - 1, p_149742_3_, p_149742_4_, ForgeDirection.EAST) || p_149742_1_.isSideSolid(p_149742_2_ + 1, p_149742_3_, p_149742_4_, ForgeDirection.WEST) || p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_, p_149742_4_ - 1, ForgeDirection.SOUTH) || p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_, p_149742_4_ + 1, ForgeDirection.NORTH) || p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_ - 1, p_149742_4_, ForgeDirection.UP) || p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_ + 1, p_149742_4_, ForgeDirection.DOWN);
   }

   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
      byte b0 = -1;
      if(p_149660_5_ == 0 && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_ + 1, p_149660_4_, ForgeDirection.DOWN)) {
         b0 = 0;
      }

      if(p_149660_5_ == 1 && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_ - 1, p_149660_4_, ForgeDirection.UP)) {
         b0 = 1;
      }

      if(p_149660_5_ == 2 && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_, p_149660_4_ + 1, ForgeDirection.NORTH)) {
         b0 = 2;
      }

      if(p_149660_5_ == 3 && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_, p_149660_4_ - 1, ForgeDirection.SOUTH)) {
         b0 = 3;
      }

      if(p_149660_5_ == 4 && p_149660_1_.isSideSolid(p_149660_2_ + 1, p_149660_3_, p_149660_4_, ForgeDirection.WEST)) {
         b0 = 4;
      }

      if(p_149660_5_ == 5 && p_149660_1_.isSideSolid(p_149660_2_ - 1, p_149660_3_, p_149660_4_, ForgeDirection.EAST)) {
         b0 = 5;
      }

      return b0;
   }

   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
      this.func_149820_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
   }

   private void func_149820_e(World p_149820_1_, int p_149820_2_, int p_149820_3_, int p_149820_4_) {
      if(!this.canPlaceBlockOnSide(p_149820_1_, p_149820_2_, p_149820_3_, p_149820_4_, p_149820_1_.getBlockMetadata(p_149820_2_, p_149820_3_, p_149820_4_))) {
         this.dropBlockAsItem(p_149820_1_, p_149820_2_, p_149820_3_, p_149820_4_, p_149820_1_.getBlockMetadata(p_149820_2_, p_149820_3_, p_149820_4_), 0);
         p_149820_1_.setBlockToAir(p_149820_2_, p_149820_3_, p_149820_4_);
      }

   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
      float f = 0.0625F;
      int md = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_);
      return md == 0?AxisAlignedBB.getBoundingBox((double)p_149668_2_, (double)((float)p_149668_3_ + f), (double)p_149668_4_, (double)(p_149668_2_ + 1), (double)(p_149668_3_ + 1), (double)(p_149668_4_ + 1)):(md == 1?AxisAlignedBB.getBoundingBox((double)p_149668_2_, (double)p_149668_3_, (double)p_149668_4_, (double)(p_149668_2_ + 1), (double)((float)(p_149668_3_ + 1) - f), (double)(p_149668_4_ + 1)):(md == 4?AxisAlignedBB.getBoundingBox((double)((float)p_149668_2_ + f), (double)p_149668_3_, (double)p_149668_4_, (double)(p_149668_2_ + 1), (double)(p_149668_3_ + 1), (double)(p_149668_4_ + 1)):(md == 5?AxisAlignedBB.getBoundingBox((double)p_149668_2_, (double)p_149668_3_, (double)p_149668_4_, (double)((float)(p_149668_2_ + 1) - f), (double)(p_149668_3_ + 1), (double)(p_149668_4_ + 1)):(md == 2?AxisAlignedBB.getBoundingBox((double)p_149668_2_, (double)p_149668_3_, (double)((float)p_149668_4_ + f), (double)(p_149668_2_ + 1), (double)(p_149668_3_ + 1), (double)(p_149668_4_ + 1)):(md == 3?AxisAlignedBB.getBoundingBox((double)p_149668_2_, (double)p_149668_3_, (double)p_149668_4_, (double)(p_149668_2_ + 1), (double)(p_149668_3_ + 1), (double)((float)(p_149668_4_ + 1) - f)):AxisAlignedBB.getBoundingBox((double)((float)p_149668_2_ + f), (double)p_149668_3_, (double)((float)p_149668_4_ + f), (double)((float)(p_149668_2_ + 1) - f), (double)((float)(p_149668_3_ + 1) - f), (double)((float)(p_149668_4_ + 1) - f)))))));
   }
}
