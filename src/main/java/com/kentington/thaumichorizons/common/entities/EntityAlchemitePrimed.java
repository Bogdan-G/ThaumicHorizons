package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.lib.ExplosionAlchemite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityAlchemitePrimed extends Entity {

   public EntityLivingBase alchemitePlacedBy;
   public int fuse;


   public EntityAlchemitePrimed(World p_i1729_1_) {
      super(p_i1729_1_);
      this.fuse = 80;
      super.preventEntitySpawning = true;
      this.setSize(0.98F, 0.98F);
      super.yOffset = super.height / 2.0F;
   }

   public EntityAlchemitePrimed(World p_i1730_1_, double p_i1730_2_, double p_i1730_4_, double p_i1730_6_, EntityLivingBase p_i1730_8_) {
      this(p_i1730_1_);
      this.setPosition(p_i1730_2_, p_i1730_4_, p_i1730_6_);
      float f = (float)((new org.bogdang.modifications.random.XSTR()).nextFloat() * 3.141592653589793D * 2.0D);
      this.setSize(0.98F, 0.98F);
      super.motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
      super.motionY = 0.20000000298023224D;
      super.motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
      this.fuse = 80;
      super.prevPosX = p_i1730_2_;
      super.prevPosY = p_i1730_4_;
      super.prevPosZ = p_i1730_6_;
      this.alchemitePlacedBy = p_i1730_8_;
   }

   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      super.motionY -= 0.03999999910593033D;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionX *= 0.9800000190734863D;
      super.motionY *= 0.9800000190734863D;
      super.motionZ *= 0.9800000190734863D;
      if(super.onGround) {
         super.motionX *= 0.699999988079071D;
         super.motionZ *= 0.699999988079071D;
         super.motionY *= -0.5D;
      }

      if(this.fuse-- <= 0) {
         this.setDead();
         if(!super.worldObj.isRemote) {
            this.explode();
         }
      } else {
         super.worldObj.spawnParticle("smoke", super.posX, super.posY + 0.5D, super.posZ, 0.0D, 0.0D, 0.0D);
      }

   }

   private void explode() {
      ExplosionAlchemite explosion = new ExplosionAlchemite(super.worldObj, this.alchemitePlacedBy, super.posX, super.posY, super.posZ, 5.0F);
      explosion.field_77286_a = false;
      explosion.field_82755_b = true;
      explosion.doExplosionA();
      explosion.doExplosionB(true);
   }

   protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
      p_70014_1_.setByte("Fuse", (byte)this.fuse);
   }

   protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
      this.fuse = p_70037_1_.getByte("Fuse");
   }

   protected void entityInit() {}

   public float getShadowSize() {
      return 0.0F;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return !super.isDead;
   }
}
