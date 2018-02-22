package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBoatGreatwood extends EntityBoat {

   private boolean isBoatEmpty;
   private double speedMultiplier;
   private int boatPosRotationIncrements;
   private double boatX;
   private double boatY;
   private double boatZ;
   private double boatYaw;
   private double boatPitch;
   @SideOnly(Side.CLIENT)
   private double velocityX;
   @SideOnly(Side.CLIENT)
   private double velocityY;
   @SideOnly(Side.CLIENT)
   private double velocityZ;
   private static final String __OBFID = "CL_00001667";


   public EntityBoatGreatwood(World p_i1704_1_) {
      super(p_i1704_1_);
      this.isBoatEmpty = true;
      this.speedMultiplier = 0.09D;
      super.preventEntitySpawning = true;
      this.setSize(1.5F, 0.6F);
      super.yOffset = super.height / 2.0F;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
      super.dataWatcher.addObject(17, new Integer(0));
      super.dataWatcher.addObject(18, new Integer(1));
      super.dataWatcher.addObject(19, new Float(0.0F));
   }

   public AxisAlignedBB getCollisionBox(Entity p_70114_1_) {
      return p_70114_1_.boundingBox;
   }

   public AxisAlignedBB getBoundingBox() {
      return super.boundingBox;
   }

   public boolean canBePushed() {
      return true;
   }

   public EntityBoatGreatwood(World p_i1705_1_, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
      this(p_i1705_1_);
      this.setPosition(p_i1705_2_, p_i1705_4_ + (double)super.yOffset, p_i1705_6_);
      super.motionX = 0.0D;
      super.motionY = 0.0D;
      super.motionZ = 0.0D;
      super.prevPosX = p_i1705_2_;
      super.prevPosY = p_i1705_4_;
      super.prevPosZ = p_i1705_6_;
   }

   public double getMountedYOffset() {
      return (double)super.height * 0.0D - 0.30000001192092896D;
   }

   public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
      if(this.isEntityInvulnerable()) {
         return false;
      } else if(!super.worldObj.isRemote && !super.isDead) {
         this.setForwardDirection(-this.getForwardDirection());
         this.setTimeSinceHit(10);
         this.setDamageTaken(this.getDamageTaken() + p_70097_2_ * 10.0F);
         this.setBeenAttacked();
         boolean flag = p_70097_1_.getEntity() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.getEntity()).capabilities.isCreativeMode;
         if(flag || this.getDamageTaken() > 40.0F) {
            if(super.riddenByEntity != null) {
               super.riddenByEntity.mountEntity(this);
            }

            if(!flag) {
               this.func_145778_a(ThaumicHorizons.itemBoatGreatwood, 1, 0.0F);
            }

            this.setDead();
         }

         return true;
      } else {
         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void performHurtAnimation() {
      this.setForwardDirection(-this.getForwardDirection());
      this.setTimeSinceHit(10);
      this.setDamageTaken(this.getDamageTaken() * 11.0F);
   }

   public boolean canBeCollidedWith() {
      return !super.isDead;
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
      if(this.isBoatEmpty) {
         this.boatPosRotationIncrements = p_70056_9_ + 5;
      } else {
         double d3 = p_70056_1_ - super.posX;
         double d4 = p_70056_3_ - super.posY;
         double d5 = p_70056_5_ - super.posZ;
         double d6 = d3 * d3 + d4 * d4 + d5 * d5;
         if(d6 <= 1.0D) {
            return;
         }

         this.boatPosRotationIncrements = 3;
      }

      this.boatX = p_70056_1_;
      this.boatY = p_70056_3_;
      this.boatZ = p_70056_5_;
      this.boatYaw = (double)p_70056_7_;
      this.boatPitch = (double)p_70056_8_;
      super.motionX = this.velocityX;
      super.motionY = this.velocityY;
      super.motionZ = this.velocityZ;
   }

   @SideOnly(Side.CLIENT)
   public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      this.velocityX = super.motionX = p_70016_1_;
      this.velocityY = super.motionY = p_70016_3_;
      this.velocityZ = super.motionZ = p_70016_5_;
   }

   public void onUpdate() {
      this.onEntityUpdate();
      if(this.getTimeSinceHit() > 0) {
         this.setTimeSinceHit(this.getTimeSinceHit() - 1);
      }

      if(this.getDamageTaken() > 0.0F) {
         this.setDamageTaken(this.getDamageTaken() - 1.0F);
      }

      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      byte b0 = 5;
      double d0 = 0.0D;

      for(int d10 = 0; d10 < b0; ++d10) {
         double d1 = super.boundingBox.minY + (super.boundingBox.maxY - super.boundingBox.minY) * (double)(d10 + 0) / (double)b0 - 0.125D;
         double d3 = super.boundingBox.minY + (super.boundingBox.maxY - super.boundingBox.minY) * (double)(d10 + 1) / (double)b0 - 0.125D;
         AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(super.boundingBox.minX, d1, super.boundingBox.minZ, super.boundingBox.maxX, d3, super.boundingBox.maxZ);
         if(super.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
            d0 += 1.0D / (double)b0;
         }
      }

      double var21 = Math.sqrt(super.motionX * super.motionX + super.motionZ * super.motionZ);
      double d2;
      double d4;
      int j;
      double d11;
      double d12;
      if(var21 > 0.26249999999999996D) {
         d2 = Math.cos((double)super.rotationYaw * 3.141592653589793D / 180.0D);
         d4 = Math.sin((double)super.rotationYaw * 3.141592653589793D / 180.0D);

         for(j = 0; (double)j < 1.0D + var21 * 60.0D; ++j) {
            d11 = (double)(super.rand.nextFloat() * 2.0F - 1.0F);
            d12 = (double)(super.rand.nextInt(2) * 2 - 1) * 0.7D;
            double l;
            double j1;
            if(super.rand.nextBoolean()) {
               l = super.posX - d2 * d11 * 0.8D + d4 * d12;
               j1 = super.posZ - d4 * d11 * 0.8D - d2 * d12;
               super.worldObj.spawnParticle("splash", l, super.posY - 0.125D, j1, super.motionX, super.motionY, super.motionZ);
            } else {
               l = super.posX + d2 + d4 * d11 * 0.7D;
               j1 = super.posZ + d4 - d2 * d11 * 0.7D;
               super.worldObj.spawnParticle("splash", l, super.posY - 0.125D, j1, super.motionX, super.motionY, super.motionZ);
            }
         }
      }

      if(super.worldObj.isRemote && this.isBoatEmpty) {
         if(this.boatPosRotationIncrements > 0) {
            d2 = super.posX + (this.boatX - super.posX) / (double)this.boatPosRotationIncrements;
            d4 = super.posY + (this.boatY - super.posY) / (double)this.boatPosRotationIncrements;
            d11 = super.posZ + (this.boatZ - super.posZ) / (double)this.boatPosRotationIncrements;
            d12 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)super.rotationYaw);
            super.rotationYaw = (float)((double)super.rotationYaw + d12 / (double)this.boatPosRotationIncrements);
            super.rotationPitch = (float)((double)super.rotationPitch + (this.boatPitch - (double)super.rotationPitch) / (double)this.boatPosRotationIncrements);
            --this.boatPosRotationIncrements;
            this.setPosition(d2, d4, d11);
            this.setRotation(super.rotationYaw, super.rotationPitch);
         } else {
            d2 = super.posX + super.motionX;
            d4 = super.posY + super.motionY;
            d11 = super.posZ + super.motionZ;
            this.setPosition(d2, d4, d11);
            if(super.onGround) {
               super.motionX *= 0.5D;
               super.motionY *= 0.5D;
               super.motionZ *= 0.5D;
            }

            super.motionX *= 0.9900000095367432D;
            super.motionY *= 0.949999988079071D;
            super.motionZ *= 0.9900000095367432D;
         }
      } else {
         if(d0 < 1.0D) {
            d2 = d0 * 2.0D - 1.0D;
            super.motionY += 0.03999999910593033D * d2;
         } else {
            if(super.motionY < 0.0D) {
               super.motionY /= 2.0D;
            }

            super.motionY += 0.007000000216066837D;
         }

         if(super.riddenByEntity != null && super.riddenByEntity instanceof EntityLivingBase) {
            EntityLivingBase var25 = (EntityLivingBase)super.riddenByEntity;
            float d7 = super.riddenByEntity.rotationYaw + -var25.moveStrafing * 90.0F;
            super.motionX += -Math.sin((double)(d7 * 3.1415927F / 180.0F)) * this.speedMultiplier * (double)var25.moveForward * 0.05000000074505806D;
            super.motionZ += Math.cos((double)(d7 * 3.1415927F / 180.0F)) * this.speedMultiplier * (double)var25.moveForward * 0.05000000074505806D;
         }

         d2 = Math.sqrt(super.motionX * super.motionX + super.motionZ * super.motionZ);
         if(d2 > 0.45D) {
            d4 = 0.45D / d2;
            super.motionX *= d4;
            super.motionZ *= d4;
            d2 = 0.45D;
         }

         if(d2 > var21 && this.speedMultiplier < 0.45D) {
            this.speedMultiplier += (0.45D - this.speedMultiplier) / 45.0D;
            if(this.speedMultiplier > 0.45D) {
               this.speedMultiplier = 0.45D;
            }
         } else {
            this.speedMultiplier -= (this.speedMultiplier - 0.09D) / 45.0D;
            if(this.speedMultiplier < 0.09D) {
               this.speedMultiplier = 0.09D;
            }
         }

         for(int var24 = 0; var24 < 4; ++var24) {
            int var23 = MathHelper.floor_double(super.posX + ((double)(var24 % 2) - 0.5D) * 0.8D);
            j = MathHelper.floor_double(super.posZ + ((double)(var24 / 2) - 0.5D) * 0.8D);

            for(int var26 = 0; var26 < 2; ++var26) {
               int list = MathHelper.floor_double(super.posY) + var26;
               Block k1 = super.worldObj.getBlock(var23, list, j);
               if(k1 == Blocks.snow_layer) {
                  super.worldObj.setBlockToAir(var23, list, j);
                  super.isCollidedHorizontally = false;
               } else if(k1 == Blocks.waterlily) {
                  super.worldObj.func_147480_a(var23, list, j, true);
                  super.isCollidedHorizontally = false;
               }
            }
         }

         if(super.onGround) {
            super.motionX *= 0.5D;
            super.motionY *= 0.5D;
            super.motionZ *= 0.5D;
         }

         this.moveEntity(super.motionX, super.motionY, super.motionZ);
         super.motionX *= 0.9900000095367432D;
         super.motionY *= 0.949999988079071D;
         super.motionZ *= 0.9900000095367432D;
         super.rotationPitch = 0.0F;
         d4 = (double)super.rotationYaw;
         d11 = super.prevPosX - super.posX;
         d12 = super.prevPosZ - super.posZ;
         if(d11 * d11 + d12 * d12 > 0.001D) {
            d4 = (double)((float)(Math.atan2(d12, d11) * 180.0D / 3.141592653589793D));
         }

         double var22 = MathHelper.wrapAngleTo180_double(d4 - (double)super.rotationYaw);
         if(var22 > 20.0D) {
            var22 = 20.0D;
         }

         if(var22 < -20.0D) {
            var22 = -20.0D;
         }

         super.rotationYaw = (float)((double)super.rotationYaw + var22);
         this.setRotation(super.rotationYaw, super.rotationPitch);
         if(!super.worldObj.isRemote) {
            List var28 = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
            if(var28 != null && !var28.isEmpty()) {
               for(int var27 = 0; var27 < var28.size(); ++var27) {
                  Entity entity = (Entity)var28.get(var27);
                  if(entity != super.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat) {
                     entity.applyEntityCollision(this);
                  }
               }
            }

            if(super.riddenByEntity != null && super.riddenByEntity.isDead) {
               super.riddenByEntity = null;
            }
         }
      }

   }

   public void updateRiderPosition() {
      if(super.riddenByEntity != null) {
         double d0 = Math.cos((double)super.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
         double d1 = Math.sin((double)super.rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
         super.riddenByEntity.setPosition(super.posX + d0, super.posY + this.getMountedYOffset() + super.riddenByEntity.getYOffset(), super.posZ + d1);
      }

   }

   protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}

   protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {}

   @SideOnly(Side.CLIENT)
   public float getShadowSize() {
      return 0.0F;
   }

   public boolean interactFirst(EntityPlayer p_130002_1_) {
      if(super.riddenByEntity != null && super.riddenByEntity instanceof EntityPlayer && super.riddenByEntity != p_130002_1_) {
         return true;
      } else {
         if(!super.worldObj.isRemote) {
            p_130002_1_.mountEntity(this);
         }

         return true;
      }
   }

   protected void updateFallState(double p_70064_1_, boolean p_70064_3_) {
      int i = MathHelper.floor_double(super.posX);
      int j = MathHelper.floor_double(super.posY);
      int k = MathHelper.floor_double(super.posZ);
      if(!p_70064_3_ && super.worldObj.getBlock(i, j - 1, k).getMaterial() != Material.water && p_70064_1_ < 0.0D) {
         super.fallDistance = (float)((double)super.fallDistance - p_70064_1_);
      }

   }

   public void setDamageTaken(float p_70266_1_) {
      super.dataWatcher.updateObject(19, Float.valueOf(p_70266_1_));
   }

   public float getDamageTaken() {
      return super.dataWatcher.getWatchableObjectFloat(19);
   }

   public void setTimeSinceHit(int p_70265_1_) {
      super.dataWatcher.updateObject(17, Integer.valueOf(p_70265_1_));
   }

   public int getTimeSinceHit() {
      return super.dataWatcher.getWatchableObjectInt(17);
   }

   public void setForwardDirection(int p_70269_1_) {
      super.dataWatcher.updateObject(18, Integer.valueOf(p_70269_1_));
   }

   public int getForwardDirection() {
      return super.dataWatcher.getWatchableObjectInt(18);
   }

   @SideOnly(Side.CLIENT)
   public void setIsBoatEmpty(boolean p_70270_1_) {
      this.isBoatEmpty = p_70270_1_;
   }
}
