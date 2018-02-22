package com.kentington.thaumichorizons.common.entities;

import com.google.common.collect.HashMultimap;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityThunderhorse extends EntityHorse {

   boolean initialized = false;
   boolean flying = false;


   public EntityThunderhorse(World p_i1685_1_) {
      super(p_i1685_1_);
   }

   public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
      super.readEntityFromNBT(p_70037_1_);
      this.initialized = p_70037_1_.getBoolean("initialized");
      if(!this.initialized) {
         HashMultimap map = HashMultimap.create();
         map.put("generic.movementSpeed", new AttributeModifier("generic.movementSpeed", 0.1D, 1));
         map.put("horse.jumpStrength", new AttributeModifier("horse.jumpStrength", 0.25D, 1));
         map.put("generic.maxHealth", new AttributeModifier("generic.maxHealth", 4.0D, 1));
         this.getAttributeMap().applyAttributeModifiers(map);
         this.initialized = true;
      }

      this.flying = p_70037_1_.getBoolean("flying");
   }

   public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
      super.writeEntityToNBT(p_70014_1_);
      p_70014_1_.setBoolean("initialized", this.initialized);
      p_70014_1_.setBoolean("flying", this.flying);
   }

   protected void fall(float p_70069_1_) {
      if(p_70069_1_ > 1.0F) {
         this.playSound("mob.horse.land", 0.4F, 1.0F);
      }

      int i = MathHelper.ceiling_float_int(p_70069_1_ * 0.5F - 3.0F);
      if(i > 0) {
         Block block = super.worldObj.getBlock(MathHelper.floor_double(super.posX), MathHelper.floor_double(super.posY - 0.2D - (double)super.prevRotationYaw), MathHelper.floor_double(super.posZ));
         if(block.getMaterial() != Material.air) {
            SoundType soundtype = block.stepSound;
            super.worldObj.playSoundAtEntity(this, soundtype.getStepResourcePath(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
         }
      }

   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   public void toggleFlying() {
      if(super.riddenByEntity != null && super.riddenByEntity instanceof EntityPlayer) {
         if(!this.flying) {
            this.flying = true;
            ((EntityPlayer)super.riddenByEntity).capabilities.isFlying = true;
         } else {
            this.flying = false;
            ((EntityPlayer)super.riddenByEntity).capabilities.isFlying = false;
         }

      }
   }

   public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
      if(super.riddenByEntity != null && super.riddenByEntity instanceof EntityLivingBase && this.isHorseSaddled()) {
         super.prevRotationYaw = super.rotationYaw = super.riddenByEntity.rotationYaw;
         super.rotationPitch = super.riddenByEntity.rotationPitch * 0.5F;
         this.setRotation(super.rotationYaw, super.rotationPitch);
         super.rotationYawHead = super.renderYawOffset = super.rotationYaw;
         p_70612_1_ = ((EntityLivingBase)super.riddenByEntity).moveStrafing * 0.5F;
         p_70612_2_ = ((EntityLivingBase)super.riddenByEntity).moveForward;
         if(p_70612_2_ <= 0.0F) {
            p_70612_2_ *= 0.25F;
         }

         if(super.motionY > 0.0D || super.motionY < 0.0D) {
            super.motionY *= 0.8999999761581421D;
         }

         super.stepHeight = 1.0F;
         super.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
         if(!super.worldObj.isRemote) {
            this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
            super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
         }

         if(super.onGround) {
            super.jumpPower = 0.0F;
            this.setHorseJumping(false);
         }

         super.prevLimbSwingAmount = super.limbSwingAmount;
         double d1 = super.posX - super.prevPosX;
         double d0 = super.posZ - super.prevPosZ;
         float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
         if(f4 > 1.0F) {
            f4 = 1.0F;
         }

         super.limbSwingAmount += (f4 - super.limbSwingAmount) * 0.4F;
         super.limbSwing += super.limbSwingAmount;
      } else {
         super.stepHeight = 0.5F;
         super.jumpMovementFactor = 0.02F;
         super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
      }

   }
}
