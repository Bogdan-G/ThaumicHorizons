package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;

public class BlockBrain extends Block {

   public BlockBrain() {
      super(Config.taintMaterial);
      this.setHardness(0.5F);
      this.setResistance(0.5F);
      this.setBlockName("ThaumicHorizons_brain");
      this.setBlockTextureName("ThaumicHorizons:brain");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
      return ConfigItems.itemZombieBrain;
   }

   public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_) {
      return this.quantityDropped(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1);
   }

   public int quantityDropped(Random p_149745_1_) {
      return 2 + p_149745_1_.nextInt(2);
   }
}
