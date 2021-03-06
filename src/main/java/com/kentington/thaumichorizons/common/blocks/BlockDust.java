package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockDust extends Block {

   private static final String __OBFID = "CL_00000310";


   public BlockDust() {
      super(Material.sand);
      this.setHardness(0.5F);
      this.setStepSound(Block.soundTypeSand);
      this.setBlockName("ThaumicHorizons_dust");
      this.setBlockTextureName("ThaumicHorizons:dust");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
      float f = 0.125F;
      return AxisAlignedBB.getBoundingBox((double)p_149668_2_, (double)p_149668_3_, (double)p_149668_4_, (double)(p_149668_2_ + 1), (double)((float)(p_149668_3_ + 1) - f), (double)(p_149668_4_ + 1));
   }

   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
      p_149670_5_.motionX *= 0.4D;
      p_149670_5_.motionZ *= 0.4D;
   }
}
