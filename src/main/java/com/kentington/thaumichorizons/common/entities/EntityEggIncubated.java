package com.kentington.thaumichorizons.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEggIncubated extends EntityEgg {

   public EntityEggIncubated(World p_i1779_1_) {
      super(p_i1779_1_);
   }

   public EntityEggIncubated(World p_i1780_1_, EntityLivingBase p_i1780_2_) {
      super(p_i1780_1_, p_i1780_2_);
   }

   public EntityEggIncubated(World p_i1781_1_, double p_i1781_2_, double p_i1781_4_, double p_i1781_6_) {
      super(p_i1781_1_, p_i1781_2_, p_i1781_4_, p_i1781_6_);
   }

   protected void onImpact(MovingObjectPosition p_70184_1_) {
      if(p_70184_1_.entityHit != null) {
         p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
      }

      if(!super.worldObj.isRemote) {
         EntityChicken j = new EntityChicken(super.worldObj);
         j.setGrowingAge(-24000);
         j.setLocationAndAngles(super.posX, super.posY, super.posZ, super.rotationYaw, 0.0F);
         super.worldObj.spawnEntityInWorld(j);
      }

      for(int var3 = 0; var3 < 8; ++var3) {
         super.worldObj.spawnParticle("snowballpoof", super.posX, super.posY, super.posZ, 0.0D, 0.0D, 0.0D);
      }

      if(!super.worldObj.isRemote) {
         this.setDead();
      }

   }
}
