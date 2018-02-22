package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.client.renderer.texture.IIconRegister;
import thaumcraft.common.config.Config;

public class BlockVoid extends Block {

   public BlockVoid() {
      super(Config.airyMaterial);
      this.setHardness(-1.0F);
      this.setResistance(60000.0F);
      this.setBlockName("ThaumicHorizons_void");
      this.setBlockTextureName("ThaumicHorizons:void");
      this.setStepSound(new SoundType("cloth", 0.0F, 1.0F));
      this.setLightLevel(1.0F);
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public void registerBlockIcons(IIconRegister register) {
      super.blockIcon = register.registerIcon("thaumichorizons:void");
   }
}
