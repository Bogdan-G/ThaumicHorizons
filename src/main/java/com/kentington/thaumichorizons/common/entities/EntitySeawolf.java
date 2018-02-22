package com.kentington.thaumichorizons.common.entities;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;

public class EntitySeawolf extends EntityWolf {

   public EntitySeawolf(World p_i1696_1_) {
      super(p_i1696_1_);
      this.getNavigator().setAvoidsWater(false);
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(this.isInWater()) {
         float bonus = 0.025F;
         this.moveFlying(0.0F, 1.0F, bonus);
         this.setAir(300);
      }

   }
}
