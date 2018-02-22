package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockCrystalDeep extends BlockBreakable {

   public IIcon icon;


   public BlockCrystalDeep() {
      super("glass", Material.glass, false);
      this.setCreativeTab(ThaumicHorizons.tabTH);
      this.setBlockName("ThaumicHorizons_deepcrystal");
      this.setHardness(2.0F);
      this.setStepSound(Block.soundTypeGlass);
      this.setLightLevel(1.0F);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister par1IconRegister) {
      this.icon = par1IconRegister.registerIcon("thaumichorizons:blockCrystalDeep");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }

   public int getRenderBlockPass() {
      return 1;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }
}
