package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileNodeMonitor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.nodes.INode;

public class BlockNodeMonitor extends BlockContainer {

   IIcon icon;


   public BlockNodeMonitor() {
      super(Material.glass);
      this.setHardness(0.7F);
      this.setResistance(1.0F);
      this.setLightLevel(0.5F);
      this.setBlockName("ThaumicHorizons_nodeMonitor");
      this.setBlockTextureName("ThaumicHorizons:nodeMonitor");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileNodeMonitor node = new TileNodeMonitor();
      return node;
   }

   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
      return p_149742_1_.getTileEntity(p_149742_2_ - 1, p_149742_3_, p_149742_4_) instanceof INode || p_149742_1_.getTileEntity(p_149742_2_ + 1, p_149742_3_, p_149742_4_) instanceof INode || p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ - 1) instanceof INode || p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ + 1) instanceof INode || p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ - 1, p_149742_4_) instanceof INode || p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ + 1, p_149742_4_) instanceof INode;
   }

   public boolean canPlaceBlockOnSide(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_, int p_149742_5_) {
      ForgeDirection dir = ForgeDirection.getOrientation(p_149742_5_);
      return dir == ForgeDirection.DOWN && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ + 1, p_149742_4_) instanceof INode || dir == ForgeDirection.UP && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ - 1, p_149742_4_) instanceof INode || dir == ForgeDirection.NORTH && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ + 1) instanceof INode || dir == ForgeDirection.SOUTH && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ - 1) instanceof INode || dir == ForgeDirection.WEST && p_149742_1_.getTileEntity(p_149742_2_ + 1, p_149742_3_, p_149742_4_) instanceof INode || dir == ForgeDirection.EAST && p_149742_1_.getTileEntity(p_149742_2_ - 1, p_149742_3_, p_149742_4_) instanceof INode;
   }

   public int onBlockPlaced(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
      return p_149660_5_ == 0 && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ + 1, p_149742_4_) instanceof INode?0:(p_149660_5_ == 1 && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_ - 1, p_149742_4_) instanceof INode?1:(p_149660_5_ == 2 && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ + 1) instanceof INode?2:(p_149660_5_ == 3 && p_149742_1_.getTileEntity(p_149742_2_, p_149742_3_, p_149742_4_ - 1) instanceof INode?3:(p_149660_5_ == 4 && p_149742_1_.getTileEntity(p_149742_2_ + 1, p_149742_3_, p_149742_4_) instanceof INode?4:(p_149660_5_ == 5 && p_149742_1_.getTileEntity(p_149742_2_ - 1, p_149742_3_, p_149742_4_) instanceof INode?5:-1)))));
   }

   public boolean canProvidePower() {
      return true;
   }

   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_) {
      return ((TileNodeMonitor)p_149748_1_.getTileEntity(p_149748_2_, p_149748_3_, p_149748_4_)).activated?15:0;
   }

   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_) {
      return ((TileNodeMonitor)p_149709_1_.getTileEntity(p_149709_2_, p_149709_3_, p_149709_4_)).activated?15:0;
   }

   public void killMe(World world, int x, int y, int z) {
      this.dropBlockAsItem(world, x, y, z, 0, 0);
      world.setBlockToAir(x, y, z);
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
      return ThaumicHorizons.blockNodeMonRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("gold_block");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }
}
