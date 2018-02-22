package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileEssentiaDynamo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEssentiaDynamo extends BlockContainer {

   IIcon icon;


   public BlockEssentiaDynamo(Material p_i45386_1_) {
      super(p_i45386_1_);
      this.setHardness(0.7F);
      this.setResistance(1.0F);
      this.setLightLevel(0.5F);
      this.setBlockName("ThaumicHorizons_essentiaDynamo");
      this.setBlockTextureName("ThaumicHorizons:essentiaDynamo");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
      return this.createTileEntity(p_149915_1_, p_149915_2_);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileEssentiaDynamo();
   }

   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
      ((TileEssentiaDynamo)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_)).killMe();
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
      return ThaumicHorizons.blockEssentiaDynamoRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumcraft:thaumiumblock");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }
}
