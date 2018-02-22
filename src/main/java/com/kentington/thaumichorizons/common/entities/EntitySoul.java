package com.kentington.thaumichorizons.common.entities;

import java.awt.Color;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;

public class EntitySoul extends EntityFlying implements IMob {

   public int courseChangeCooldown = 0;
   public double waypointX;
   public double waypointY;
   public double waypointZ;


   public EntitySoul(World world) {
      super(world);
      this.setSize(0.9F, 0.9F);
      super.experienceValue = 5;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public int decreaseAirSupply(int par1) {
      return par1;
   }

   public boolean attackEntityFrom(DamageSource damagesource, float i) {
      return false;
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void onDeath(DamageSource par1DamageSource) {
      super.onDeath(par1DamageSource);
      if(super.worldObj.isRemote) {
         Thaumcraft.proxy.burst(super.worldObj, super.posX, super.posY, super.posZ, 1.0F);
      }

   }

   public void onUpdate() {
      super.onUpdate();
      if(super.worldObj.isRemote && super.worldObj.rand.nextBoolean()) {
         Color color = Color.CYAN;
         Thaumcraft.proxy.wispFX(super.worldObj, super.posX + (double)((super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 0.7F), super.posY + (double)((super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 0.7F), super.posZ + (double)((super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 0.7F), 0.1F, (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F);
      }

   }

   protected void updateEntityActionState() {
      double attackrange = 16.0D;
      double d = this.waypointX - super.posX;
      double d1 = this.waypointY - super.posY;
      double d2 = this.waypointZ - super.posZ;
      double d3 = d * d + d1 * d1 + d2 * d2;
      if(d3 < 1.0D || d3 > 3600.0D) {
         this.waypointX = super.posX + (double)(super.rand.nextFloat() * 2.0F - 1.0F) * 16.0D;
         this.waypointY = super.posY + (double)(super.rand.nextFloat() * 2.0F - 1.0F) * 16.0D;
         this.waypointZ = super.posZ + (double)(super.rand.nextFloat() * 2.0F - 1.0F) * 16.0D;
      }

      if(this.courseChangeCooldown-- <= 0) {
         this.courseChangeCooldown += super.rand.nextInt(5) + 2;
         d3 = (double)MathHelper.sqrt_double(d3);
         if(this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
            super.motionX += d / d3 * 0.1D;
            super.motionY += d1 / d3 * 0.1D;
            super.motionZ += d2 / d3 * 0.1D;
         } else {
            this.waypointX = super.posX;
            this.waypointY = super.posY;
            this.waypointZ = super.posZ;
         }
      }

      super.renderYawOffset = super.rotationYaw = -((float)Math.atan2(super.motionX, super.motionZ)) * 180.0F / 3.141593F;
   }

   private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
      double d4 = (this.waypointX - super.posX) / d3;
      double d5 = (this.waypointY - super.posY) / d3;
      double d6 = (this.waypointZ - super.posZ) / d3;
      AxisAlignedBB axisalignedbb = super.boundingBox.copy();

      int x;
      for(x = 1; (double)x < d3; ++x) {
         axisalignedbb.offset(d4, d5, d6);
         if(!super.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty()) {
            return false;
         }
      }

      x = (int)this.waypointX;
      int y = (int)this.waypointY;
      int z = (int)this.waypointZ;
      if(super.worldObj.getBlock(x, y, z).getMaterial().isLiquid()) {
         return false;
      } else {
         for(int a = 0; a < 11; ++a) {
            if(!super.worldObj.isAirBlock(x, y - a, z)) {
               return true;
            }
         }

         return false;
      }
   }

   protected String getLivingSound() {
      return "thaumcraft:wisplive";
   }

   protected String getHurtSound() {
      return "random.fizz";
   }

   protected String getDeathSound() {
      return "thaumcraft:wispdead";
   }

   protected Item getDropItem() {
      return null;
   }

   protected void dropFewItems(boolean flag, int i) {}

   protected float getSoundVolume() {
      return 0.25F;
   }

   protected boolean canDespawn() {
      return false;
   }

   public boolean getCanSpawnHere() {
      return true;
   }

   protected boolean isValidLightLevel() {
      return true;
   }
}
