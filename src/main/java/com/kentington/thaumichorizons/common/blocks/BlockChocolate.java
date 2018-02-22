package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockChocolate extends Block {

   public IIcon coloredGrass;


   public BlockChocolate() {
      super(Material.cake);
      this.setHardness(0.5F);
      this.setResistance(0.5F);
      this.setBlockName("ThaumicHorizons_chocolate");
      this.setBlockTextureName("ThaumicHorizons:chocolate");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public void registerBlockIcons(IIconRegister register) {
      super.blockIcon = register.registerIcon("thaumichorizons:chocolate");
      this.coloredGrass = register.registerIcon("thaumichorizons:grasscolorized");
   }
}
