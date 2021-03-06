package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityGoldChicken extends EntityChicken {

   public EntityGoldChicken(World p_i1682_1_) {
      super(p_i1682_1_);
   }

   public void onLivingUpdate() {
      int notTime = super.timeUntilNextEgg;
      super.timeUntilNextEgg = Integer.MAX_VALUE;
      super.onLivingUpdate();
      super.timeUntilNextEgg = notTime;
      super.field_70888_h = super.field_70886_e;
      super.field_70884_g = super.destPos;
      super.destPos = (float)((double)super.destPos + (double)(super.onGround?-1:4) * 0.3D);
      if(super.destPos < 0.0F) {
         super.destPos = 0.0F;
      }

      if(super.destPos > 1.0F) {
         super.destPos = 1.0F;
      }

      if(!super.onGround && super.field_70889_i < 1.0F) {
         super.field_70889_i = 1.0F;
      }

      super.field_70889_i = (float)((double)super.field_70889_i * 0.9D);
      if(!super.onGround && super.motionY < 0.0D) {
         super.motionY *= 0.6D;
      }

      super.field_70886_e += super.field_70889_i * 2.0F;
      if(!super.worldObj.isRemote && !this.isChild() && !this.func_152116_bZ() && --super.timeUntilNextEgg <= 0) {
         this.playSound("mob.chicken.plop", 1.0F, (super.rand.nextFloat() - super.rand.nextFloat()) * 0.2F + 1.0F);
         this.dropItem(ThaumicHorizons.itemGoldEgg, 1);
         super.timeUntilNextEgg = super.rand.nextInt(8000) + 8000;
      }

   }

   public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
      super.writeEntityToNBT(p_70014_1_);
      p_70014_1_.setInteger("egg", super.timeUntilNextEgg);
   }

   public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
      super.readEntityFromNBT(p_70037_1_);
      super.timeUntilNextEgg = p_70037_1_.getInteger("egg");
   }
}
