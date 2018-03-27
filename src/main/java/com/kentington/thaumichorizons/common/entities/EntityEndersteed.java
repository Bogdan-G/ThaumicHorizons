package com.kentington.thaumichorizons.common.entities;

import com.google.common.collect.HashMultimap;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EntityEndersteed extends EntityHorse {

   boolean initialized = false;


   public EntityEndersteed(World p_i1685_1_) {
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

   }

   public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
      super.writeEntityToNBT(p_70014_1_);
      p_70014_1_.setBoolean("initialized", this.initialized);
   }

   public void setJumpPower(int p_110206_1_) {
      double blocks = (double)p_110206_1_ / 7.0D;
      this.teleportTo(super.posX - blocks * Math.sin(Math.toRadians((double)super.rotationYaw)), super.posY, super.posZ + blocks * Math.cos(Math.toRadians((double)super.rotationYaw)));
   }

   public String getCommandSenderName() {
      return this.hasCustomNameTag()?this.getCustomNameTag():StatCollector.translateToLocal("entity.ThaumicHorizons.Endersteed.name");
   }

   protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
      EnderTeleportEvent event = new EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0.0F);
      if(MinecraftForge.EVENT_BUS.post(event)) {
         return false;
      } else {
         double d3 = super.posX;
         double d4 = super.posY;
         double d5 = super.posZ;
         super.posX = event.targetX;
         super.posY = (double)(MathHelper.floor_double(event.targetY) - 3);
         super.posZ = event.targetZ;
         boolean flag = false;
         int i = MathHelper.floor_double(super.posX);
         int j = MathHelper.floor_double(super.posY);
         int k = MathHelper.floor_double(super.posZ);
         boolean foundGround = false;
         boolean foundAir = false;

         while((!foundGround || !foundAir) && (double)j < d4 + 4.0D) {
            Block short1 = super.worldObj.getBlock(i, j - 1, k);
            if(short1.getMaterial().blocksMovement()) {
               foundGround = true;
            } else if(!foundGround) {
               ++j;
               ++super.posY;
            }

            if(foundGround) {
               this.setPosition(super.posX, super.posY, super.posZ);
               if(super.worldObj.getCollidingBoundingBoxes(this, super.boundingBox).isEmpty() && !super.worldObj.isAnyLiquid(super.boundingBox)) {
                  flag = true;
                  foundAir = true;
               }

               ++j;
               ++super.posY;
            }
         }

         this.setPosition(super.posX, super.posY, super.posZ);
         if(!flag) {
            this.setPosition(d3, d4, d5);
            return false;
         } else {
            short var33 = 128;

            for(int l = 0; l < var33; ++l) {
               double d6 = (double)l / ((double)var33 - 1.0D);
               float f = (super.rand.nextFloat() - 0.5F) * 0.2F;
               float f1 = (super.rand.nextFloat() - 0.5F) * 0.2F;
               float f2 = (super.rand.nextFloat() - 0.5F) * 0.2F;
               double d7 = d3 + (super.posX - d3) * d6 + (super.rand.nextFloat() - 0.5F) * (double)super.width * 2.0D;
               double d8 = d4 + (super.posY - d4) * d6 + super.rand.nextFloat() * (double)super.height;
               double d9 = d5 + (super.posZ - d5) * d6 + (super.rand.nextFloat() - 0.5F) * (double)super.width * 2.0D;
               super.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
            }

            super.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            this.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
         }
      }
   }
}
