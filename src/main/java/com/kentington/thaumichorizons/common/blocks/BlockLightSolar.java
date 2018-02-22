package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.blocks.BlockLight;
import com.kentington.thaumichorizons.common.tiles.TileLight;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockLightSolar extends BlockLight {

   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
      if(p_149670_5_ instanceof EntityLivingBase) {
         EntityLivingBase critter = (EntityLivingBase)p_149670_5_;
         if(critter.isEntityUndead()) {
            critter.setFire(5);
         }
      }

   }

   public int getRenderType() {
      return ThaumicHorizons.blockLightSolarRI;
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileLight();
   }
}
