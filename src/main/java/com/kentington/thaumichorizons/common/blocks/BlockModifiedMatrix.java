package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import com.kentington.thaumichorizons.common.tiles.TileVatMatrix;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockModifiedMatrix extends BlockContainer {

   IIcon icon;


   public BlockModifiedMatrix() {
      super(Material.rock);
      this.setHardness(2.0F);
      this.setResistance(2.0F);
      this.setBlockName("ThaumicHorizons_modMatrix");
      this.setBlockTextureName("ThaumicHorizons:modMatrix");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileVatMatrix te = new TileVatMatrix();
      return te;
   }

   public boolean canPlaceBlockAt(World world, int x, int y, int z) {
      return world.getTileEntity(x, y - 1, z) instanceof TileVat;
   }

   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
      TileVat tile = (TileVat)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_ - 1, p_149749_4_);
      if(tile != null && tile.mode == 2) {
         tile.killSubject();
      }

      super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
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
      return ThaumicHorizons.blockVatMatrixRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumcraft:arcane_stone");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }
}
