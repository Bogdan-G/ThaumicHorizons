package com.kentington.thaumichorizons.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBlastPhial extends EntityPotion {

   public EntityBlastPhial(World p_i1790_1_) {
      super(p_i1790_1_);
   }

   public EntityBlastPhial(World p_i1790_1_, EntityLivingBase p_i1790_2_, float power, ItemStack p_i1790_3_) {
      super(p_i1790_1_, p_i1790_2_, p_i1790_3_);
      this.setSize(0.25F, 0.25F);
      this.setSize(0.5F, 0.5F);
      this.setLocationAndAngles(p_i1790_2_.posX, p_i1790_2_.posY + (double)p_i1790_2_.getEyeHeight(), p_i1790_2_.posZ, p_i1790_2_.rotationYaw, p_i1790_2_.rotationPitch);
      super.posX -= (double)(MathHelper.cos(super.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
      super.posY -= 0.10000000149011612D;
      super.posZ -= (double)(MathHelper.sin(super.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
      this.setPosition(super.posX, super.posY, super.posZ);
      super.yOffset = 0.0F;
      super.motionX = (double)(-MathHelper.sin(super.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(super.rotationPitch / 180.0F * 3.1415927F));
      super.motionZ = (double)(MathHelper.cos(super.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(super.rotationPitch / 180.0F * 3.1415927F));
      super.motionY = (double)(-MathHelper.sin(super.rotationPitch / 180.0F * 3.1415927F));
      this.setThrowableHeading(super.motionX, super.motionY, super.motionZ, power * 1.5F, 1.0F);
   }

   public int getPotionDamage() {
      return 8229;
   }
}
