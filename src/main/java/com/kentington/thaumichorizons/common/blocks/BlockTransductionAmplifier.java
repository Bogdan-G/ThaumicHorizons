package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileTransductionAmplifier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.tiles.TileNodeEnergized;

public class BlockTransductionAmplifier extends BlockContainer {

   IIcon icon;


   public BlockTransductionAmplifier() {
      super(Material.rock);
      this.setBlockName("ThaumicHorizons_transductionAmplifier");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileTransductionAmplifier();
   }

   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
      return p_149742_1_.getTileEntity(p_149742_2_ - 1, p_149742_3_, p_149742_4_) instanceof TileNodeEnergized || p_149742_1_.getTileEntity(p_149742_2_ + 1, p_149742_3_, p_149742_4_) instanceof TileNodeEnergized || p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ - 1) instanceof TileNodeEnergized || p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ + 1) instanceof TileNodeEnergized || p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ - 1, p_149742_4_) instanceof TileNodeEnergized || p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ + 1, p_149742_4_) instanceof TileNodeEnergized;
   }

   public boolean canPlaceBlockOnSide(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_, int p_149742_5_) {
      ForgeDirection dir = ForgeDirection.getOrientation(p_149742_5_);
      return dir == ForgeDirection.NORTH && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ + 1) instanceof TileNodeEnergized || dir == ForgeDirection.SOUTH && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ - 1) instanceof TileNodeEnergized || dir == ForgeDirection.WEST && p_149742_1_.getTileEntity(p_149742_2_ + 1, p_149742_3_, p_149742_4_) instanceof TileNodeEnergized || dir == ForgeDirection.EAST && p_149742_1_.getTileEntity(p_149742_2_ - 1, p_149742_3_, p_149742_4_) instanceof TileNodeEnergized;
   }

   public int onBlockPlaced(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
      return p_149660_5_ == 0 && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ + 1, p_149742_4_) instanceof TileNodeEnergized?0:(p_149660_5_ == 1 && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ - 1, p_149742_4_) instanceof TileNodeEnergized?1:(p_149660_5_ == 2 && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ + 1) instanceof TileNodeEnergized?2:(p_149660_5_ == 3 && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ - 1) instanceof TileNodeEnergized?3:(p_149660_5_ == 4 && p_149742_1_.getTileEntity(p_149742_2_ + 1, p_149742_3_, p_149742_4_) instanceof TileNodeEnergized?4:(p_149660_5_ == 5 && p_149742_1_.getTileEntity(p_149742_2_ - 1, p_149742_3_, p_149742_4_) instanceof TileNodeEnergized?5:-1)))));
   }

   public void killMe(World world, int x, int y, int z, boolean drop) {
      if(((TileTransductionAmplifier)world.getTileEntity(x, y, z)).activated) {
         ((TileTransductionAmplifier)world.getTileEntity(x, y, z)).unBoostNode(x, y, z);
      }

      if(drop) {
         this.dropBlockAsItem(world, x, y, z, 0, 0);
      }

   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 1;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockTransducerRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("iron_block");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }

   public void onBlockPreDestroy(World world, int x, int y, int z, int md) {
      this.killMe(world, x, y, z, false);
   }

   public void onNeighborBlockChange(World world, int x, int y, int z, Block nbid) {
      TileTransductionAmplifier tile = (TileTransductionAmplifier)world.getTileEntity(x, y, z);
      if(tile.activated && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
         tile.shouldActivate = false;
      } else if(!tile.activated && world.isBlockIndirectlyGettingPowered(x, y, z)) {
         tile.shouldActivate = true;
      }

   }

   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
      switch(world.getBlockMetadata(x, y, z)) {
      case 0:
         return side == ForgeDirection.DOWN;
      case 1:
         return side == ForgeDirection.UP;
      case 2:
         return side == ForgeDirection.NORTH;
      case 3:
         return side == ForgeDirection.SOUTH;
      case 4:
         return side == ForgeDirection.WEST;
      case 5:
         return side == ForgeDirection.EAST;
      default:
         return false;
      }
   }
}
