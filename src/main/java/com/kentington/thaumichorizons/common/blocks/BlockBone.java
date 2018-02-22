package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockBone extends Block {

   public BlockBone() {
      super(Material.rock);
      this.setHardness(2.5F);
      this.setResistance(2.5F);
      this.setBlockName("ThaumicHorizons_bone");
      this.setBlockTextureName("ThaumicHorizons:bone");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
      return Items.bone;
   }

   public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_) {
      return this.quantityDropped(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1);
   }

   public int quantityDropped(Random p_149745_1_) {
      return 3 + p_149745_1_.nextInt(3);
   }
}
