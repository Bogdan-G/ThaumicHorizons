package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.ItemSyringeInjection;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySyringe extends Entity implements IProjectile, IEntityAdditionalSpawnData {

   public int color;
   public NBTTagCompound effects;
   private boolean inGround;
   private int field_145791_d;
   private int field_145792_e;
   private int field_145789_f;
   private Block field_145790_g;
   private int inData;
   private int ticksInGround;
   private int ticksInAir;
   private int knockbackStrength;
   private EntityLivingBase shootingEntity;
   private int canBePickedUp;
   private byte arrowShake;
   private double damage;


   public EntitySyringe(World p_i1753_1_) {
      super(p_i1753_1_);
      this.color = Color.RED.getRGB();
      this.field_145791_d = -1;
      this.field_145792_e = -1;
      this.field_145789_f = -1;
      this.damage = 1.5D;
      super.renderDistanceWeight = 10.0D;
      this.setSize(0.5F, 0.5F);
   }

   public EntitySyringe(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_) {
      super(p_i1754_1_);
      this.color = Color.RED.getRGB();
      this.field_145791_d = -1;
      this.field_145792_e = -1;
      this.field_145789_f = -1;
      this.damage = 1.5D;
      super.renderDistanceWeight = 10.0D;
      this.setSize(0.5F, 0.5F);
      this.setPosition(p_i1754_2_, p_i1754_4_, p_i1754_6_);
      super.yOffset = 0.0F;
   }

   public EntitySyringe(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_, NBTTagCompound tag) {
      super(p_i1755_1_);
      this.color = Color.RED.getRGB();
      this.field_145791_d = -1;
      this.field_145792_e = -1;
      this.field_145789_f = -1;
      this.damage = 1.5D;
      super.renderDistanceWeight = 10.0D;
      this.shootingEntity = p_i1755_2_;
      this.effects = (NBTTagCompound)tag.copy();
      this.color = this.effects.getInteger("color");
      if(p_i1755_2_ instanceof EntityPlayer) {
         this.canBePickedUp = 1;
      }

      super.posY = p_i1755_2_.posY + (double)p_i1755_2_.getEyeHeight() - 0.10000000149011612D;
      double d0 = p_i1755_3_.posX - p_i1755_2_.posX;
      double d1 = p_i1755_3_.boundingBox.minY + (double)(p_i1755_3_.height / 3.0F) - super.posY;
      double d2 = p_i1755_3_.posZ - p_i1755_2_.posZ;
      double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
      if(d3 >= 1.0E-7D) {
         float f2 = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
         float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D));
         double d4 = d0 / d3;
         double d5 = d2 / d3;
         this.setLocationAndAngles(p_i1755_2_.posX + d4, super.posY, p_i1755_2_.posZ + d5, f2, f3);
         super.yOffset = 0.0F;
         float f4 = (float)d3 * 0.2F;
         this.setThrowableHeading(d0, d1 + (double)f4, d2, p_i1755_4_, p_i1755_5_);
      }

   }

   public EntitySyringe(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_, NBTTagCompound tag) {
      super(p_i1756_1_);
      this.color = Color.RED.getRGB();
      this.field_145791_d = -1;
      this.field_145792_e = -1;
      this.field_145789_f = -1;
      this.damage = 1.5D;
      super.renderDistanceWeight = 10.0D;
      this.shootingEntity = p_i1756_2_;
      this.effects = (NBTTagCompound)tag.copy();
      this.color = this.effects.getInteger("color");
      if(p_i1756_2_ instanceof EntityPlayer) {
         this.canBePickedUp = 1;
      }

      this.setSize(0.5F, 0.5F);
      this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + (double)p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ, p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
      super.posX -= (double)(MathHelper.cos(super.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
      super.posY -= 0.10000000149011612D;
      super.posZ -= (double)(MathHelper.sin(super.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
      this.setPosition(super.posX, super.posY, super.posZ);
      super.yOffset = 0.0F;
      super.motionX = (double)(-MathHelper.sin(super.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(super.rotationPitch / 180.0F * 3.1415927F));
      super.motionZ = (double)(MathHelper.cos(super.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(super.rotationPitch / 180.0F * 3.1415927F));
      super.motionY = (double)(-MathHelper.sin(super.rotationPitch / 180.0F * 3.1415927F));
      this.setThrowableHeading(super.motionX, super.motionY, super.motionZ, p_i1756_3_ * 1.5F, 1.0F);
   }

   public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
      p_70014_1_.setTag("effects", this.effects);
      p_70014_1_.setShort("xTile", (short)this.field_145791_d);
      p_70014_1_.setShort("yTile", (short)this.field_145792_e);
      p_70014_1_.setShort("zTile", (short)this.field_145789_f);
      p_70014_1_.setShort("life", (short)this.ticksInGround);
      p_70014_1_.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145790_g));
      p_70014_1_.setByte("inData", (byte)this.inData);
      p_70014_1_.setByte("shake", this.arrowShake);
      p_70014_1_.setByte("inGround", (byte)(this.inGround?1:0));
      p_70014_1_.setByte("pickup", (byte)this.canBePickedUp);
      p_70014_1_.setDouble("damage", this.damage);
      p_70014_1_.setInteger("color", this.color);
   }

   public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
      this.effects = p_70037_1_.getCompoundTag("effects");
      this.field_145791_d = p_70037_1_.getShort("xTile");
      this.field_145792_e = p_70037_1_.getShort("yTile");
      this.field_145789_f = p_70037_1_.getShort("zTile");
      this.ticksInGround = p_70037_1_.getShort("life");
      this.field_145790_g = Block.getBlockById(p_70037_1_.getByte("inTile") & 255);
      this.inData = p_70037_1_.getByte("inData") & 255;
      this.arrowShake = (byte)(p_70037_1_.getByte("shake") & 255);
      this.inGround = p_70037_1_.getByte("inGround") == 1;
      if(p_70037_1_.hasKey("damage", 99)) {
         this.damage = p_70037_1_.getDouble("damage");
      }

      if(p_70037_1_.hasKey("pickup", 99)) {
         this.canBePickedUp = p_70037_1_.getByte("pickup");
      } else if(p_70037_1_.hasKey("player", 99)) {
         this.canBePickedUp = p_70037_1_.getBoolean("player")?1:0;
      }

      this.color = p_70037_1_.getInteger("color");
   }

   void applyEffects(Entity entityHit) {
      if(entityHit instanceof EntityLivingBase && !super.worldObj.isRemote) {
         EntityLivingBase ent = (EntityLivingBase)entityHit;
         ItemStack psuedoPotion = new ItemStack(ThaumicHorizons.itemSyringeInjection);
         psuedoPotion.setTagCompound(this.effects);
         List list = ((ItemSyringeInjection)ThaumicHorizons.itemSyringeInjection).getEffects(psuedoPotion);
         if(list != null) {
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
               PotionEffect potioneffect = (PotionEffect)iterator.next();
               ent.addPotionEffect(new PotionEffect(potioneffect));
            }
         }
      }

   }

   public void onCollideWithPlayer(EntityPlayer p_70100_1_) {
      if(!super.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
         boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && p_70100_1_.capabilities.isCreativeMode;
         ItemStack psuedoPotion = new ItemStack(ThaumicHorizons.itemSyringeInjection);
         psuedoPotion.setTagCompound(this.effects);
         if(this.canBePickedUp == 1 && !p_70100_1_.inventory.addItemStackToInventory(psuedoPotion)) {
            flag = false;
         }

         if(flag) {
            this.playSound("random.pop", 0.2F, ((super.rand.nextFloat() - super.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            p_70100_1_.onItemPickup(new EntityItem(super.worldObj, super.posX, super.posY, super.posZ, psuedoPotion), 1);
            this.setDead();
         }
      }

   }

   public void onUpdate() {
      super.onUpdate();
      if(super.prevRotationPitch == 0.0F && super.prevRotationYaw == 0.0F) {
         float block = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
         super.prevRotationYaw = super.rotationYaw = (float)(Math.atan2(super.motionX, super.motionZ) * 180.0D / 3.141592653589793D);
         super.prevRotationPitch = super.rotationPitch = (float)(Math.atan2(super.motionY, (double)block) * 180.0D / 3.141592653589793D);
      }

      Block var16 = super.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
      if(var16.getMaterial() != Material.air) {
         var16.setBlockBoundsBasedOnState(super.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
         AxisAlignedBB vec31 = var16.getCollisionBoundingBoxFromPool(super.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
         if(vec31 != null && vec31.isVecInside(Vec3.createVectorHelper(super.posX, super.posY, super.posZ))) {
            this.inGround = true;
         }
      }

      if(this.arrowShake > 0) {
         --this.arrowShake;
      }

      if(this.inGround) {
         int var17 = super.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
         if(var16 == this.field_145790_g && var17 == this.inData) {
            ++this.ticksInGround;
            if(this.ticksInGround == 1200) {
               this.setDead();
            }
         } else {
            this.inGround = false;
            super.motionX *= (double)(super.rand.nextFloat() * 0.2F);
            super.motionY *= (double)(super.rand.nextFloat() * 0.2F);
            super.motionZ *= (double)(super.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         }
      } else {
         ++this.ticksInAir;
         Vec3 var18 = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
         Vec3 vec3 = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
         MovingObjectPosition movingobjectposition = super.worldObj.func_147447_a(var18, vec3, false, true, false);
         var18 = Vec3.createVectorHelper(super.posX, super.posY, super.posZ);
         vec3 = Vec3.createVectorHelper(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
         if(movingobjectposition != null) {
            vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
         }

         Entity entity = null;
         List list = super.worldObj.getEntitiesWithinAABBExcludingEntity(this, super.boundingBox.addCoord(super.motionX, super.motionY, super.motionZ).expand(1.0D, 1.0D, 1.0D));
         double d0 = 0.0D;

         int i;
         float f1;
         for(i = 0; i < list.size(); ++i) {
            Entity f2 = (Entity)list.get(i);
            if(f2.canBeCollidedWith() && (f2 != this.shootingEntity || this.ticksInAir >= 5)) {
               f1 = 0.3F;
               AxisAlignedBB f4 = f2.boundingBox.expand((double)f1, (double)f1, (double)f1);
               MovingObjectPosition f3 = f4.calculateIntercept(var18, vec3);
               if(f3 != null) {
                  double l = var18.distanceTo(f3.hitVec);
                  if(l < d0 || d0 == 0.0D) {
                     entity = f2;
                     d0 = l;
                  }
               }
            }
         }

         if(entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
         }

         if(movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
            EntityPlayer var19 = (EntityPlayer)movingobjectposition.entityHit;
            if(var19.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(var19)) {
               movingobjectposition = null;
            }
         }

         float var21;
         float var20;
         if(movingobjectposition != null) {
            if(movingobjectposition.entityHit != null) {
               var21 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
               int var22 = MathHelper.ceiling_double_int((double)var21 * this.getDamage());
               if(this.getIsCritical()) {
                  var22 += super.rand.nextInt(var22 / 2 + 2);
               }

               DamageSource var23 = null;
               if(this.shootingEntity == null) {
                  var23 = DamageSource.causeThrownDamage(this, this);
               } else {
                  var23 = DamageSource.causeThrownDamage(this, this.shootingEntity);
               }

               if(this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
                  movingobjectposition.entityHit.setFire(5);
               }

               if(movingobjectposition.entityHit.attackEntityFrom(var23, (float)var22)) {
                  if(movingobjectposition.entityHit instanceof EntityLivingBase) {
                     EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
                     if(!super.worldObj.isRemote) {
                        entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                     }

                     if(this.knockbackStrength > 0) {
                        var20 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
                        if(var20 > 0.0F) {
                           movingobjectposition.entityHit.addVelocity(super.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)var20, 0.1D, super.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)var20);
                        }
                     }

                     if(this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity);
                        EnchantmentHelper.func_151385_b(this.shootingEntity, entitylivingbase);
                     }

                     if(this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                     }
                  }

                  this.playSound("random.bowhit", 1.0F, 1.2F / (super.rand.nextFloat() * 0.2F + 0.9F));
                  if(!(movingobjectposition.entityHit instanceof EntityEnderman)) {
                     this.setDead();
                     this.applyEffects(movingobjectposition.entityHit);
                  }
               } else {
                  super.motionX *= -0.10000000149011612D;
                  super.motionY *= -0.10000000149011612D;
                  super.motionZ *= -0.10000000149011612D;
                  super.rotationYaw += 180.0F;
                  super.prevRotationYaw += 180.0F;
                  this.ticksInAir = 0;
               }
            } else {
               this.field_145791_d = movingobjectposition.blockX;
               this.field_145792_e = movingobjectposition.blockY;
               this.field_145789_f = movingobjectposition.blockZ;
               this.field_145790_g = super.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
               this.inData = super.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
               super.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - super.posX));
               super.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - super.posY));
               super.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - super.posZ));
               var21 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
               super.posX -= super.motionX / (double)var21 * 0.05000000074505806D;
               super.posY -= super.motionY / (double)var21 * 0.05000000074505806D;
               super.posZ -= super.motionZ / (double)var21 * 0.05000000074505806D;
               this.playSound("random.bowhit", 1.0F, 1.2F / (super.rand.nextFloat() * 0.2F + 0.9F));
               this.inGround = true;
               this.arrowShake = 7;
               this.setIsCritical(false);
               if(this.field_145790_g.getMaterial() != Material.air) {
                  this.field_145790_g.onEntityCollidedWithBlock(super.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
               }
            }
         }

         if(this.getIsCritical()) {
            for(i = 0; i < 4; ++i) {
               super.worldObj.spawnParticle("crit", super.posX + super.motionX * (double)i / 4.0D, super.posY + super.motionY * (double)i / 4.0D, super.posZ + super.motionZ * (double)i / 4.0D, -super.motionX, -super.motionY + 0.2D, -super.motionZ);
            }
         }

         super.posX += super.motionX;
         super.posY += super.motionY;
         super.posZ += super.motionZ;
         var21 = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionZ * super.motionZ);
         super.rotationYaw = (float)(Math.atan2(super.motionX, super.motionZ) * 180.0D / 3.141592653589793D);

         for(super.rotationPitch = (float)(Math.atan2(super.motionY, (double)var21) * 180.0D / 3.141592653589793D); super.rotationPitch - super.prevRotationPitch < -180.0F; super.prevRotationPitch -= 360.0F) {
            ;
         }

         while(super.rotationPitch - super.prevRotationPitch >= 180.0F) {
            super.prevRotationPitch += 360.0F;
         }

         while(super.rotationYaw - super.prevRotationYaw < -180.0F) {
            super.prevRotationYaw -= 360.0F;
         }

         while(super.rotationYaw - super.prevRotationYaw >= 180.0F) {
            super.prevRotationYaw += 360.0F;
         }

         super.rotationPitch = super.prevRotationPitch + (super.rotationPitch - super.prevRotationPitch) * 0.2F;
         super.rotationYaw = super.prevRotationYaw + (super.rotationYaw - super.prevRotationYaw) * 0.2F;
         float var24 = 0.99F;
         f1 = 0.05F;
         if(this.isInWater()) {
            for(int var25 = 0; var25 < 4; ++var25) {
               var20 = 0.25F;
               super.worldObj.spawnParticle("bubble", super.posX - super.motionX * (double)var20, super.posY - super.motionY * (double)var20, super.posZ - super.motionZ * (double)var20, super.motionX, super.motionY, super.motionZ);
            }

            var24 = 0.8F;
         }

         if(this.isWet()) {
            this.extinguish();
         }

         super.motionX *= (double)var24;
         super.motionY *= (double)var24;
         super.motionZ *= (double)var24;
         super.motionY -= (double)f1;
         this.setPosition(super.posX, super.posY, super.posZ);
         this.func_145775_I();
      }

   }

   public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
      float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
      p_70186_1_ /= (double)f2;
      p_70186_3_ /= (double)f2;
      p_70186_5_ /= (double)f2;
      p_70186_1_ += super.rand.nextGaussian() * (double)(super.rand.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_3_ += super.rand.nextGaussian() * (double)(super.rand.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_5_ += super.rand.nextGaussian() * (double)(super.rand.nextBoolean()?-1:1) * 0.007499999832361937D * (double)p_70186_8_;
      p_70186_1_ *= (double)p_70186_7_;
      p_70186_3_ *= (double)p_70186_7_;
      p_70186_5_ *= (double)p_70186_7_;
      super.motionX = p_70186_1_;
      super.motionY = p_70186_3_;
      super.motionZ = p_70186_5_;
      float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
      super.prevRotationYaw = super.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / 3.141592653589793D);
      super.prevRotationPitch = super.rotationPitch = (float)(Math.atan2(p_70186_3_, (double)f3) * 180.0D / 3.141592653589793D);
      this.ticksInGround = 0;
   }

   @SideOnly(Side.CLIENT)
   public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {}

   @SideOnly(Side.CLIENT)
   public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
      super.motionX = p_70016_1_;
      super.motionY = p_70016_3_;
      super.motionZ = p_70016_5_;
      if(super.prevRotationPitch == 0.0F && super.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
         super.prevRotationYaw = super.rotationYaw = (float)(Math.atan2(p_70016_1_, p_70016_5_) * 180.0D / 3.141592653589793D);
         super.prevRotationPitch = super.rotationPitch = (float)(Math.atan2(p_70016_3_, (double)f) * 180.0D / 3.141592653589793D);
         super.prevRotationPitch = super.rotationPitch;
         super.prevRotationYaw = super.rotationYaw;
         this.setLocationAndAngles(super.posX, super.posY, super.posZ, super.rotationYaw, super.rotationPitch);
         this.ticksInGround = 0;
      }

   }

   protected void entityInit() {
      super.dataWatcher.addObject(16, Byte.valueOf((byte)0));
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public float getShadowSize() {
      return 0.0F;
   }

   public void setDamage(double p_70239_1_) {
      this.damage = p_70239_1_;
   }

   public double getDamage() {
      return this.damage;
   }

   public void setKnockbackStrength(int p_70240_1_) {
      this.knockbackStrength = p_70240_1_;
   }

   public boolean canAttackWithItem() {
      return false;
   }

   public void setIsCritical(boolean p_70243_1_) {
      byte b0 = super.dataWatcher.getWatchableObjectByte(16);
      if(p_70243_1_) {
         super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 1)));
      } else {
         super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -2)));
      }

   }

   public boolean getIsCritical() {
      byte b0 = super.dataWatcher.getWatchableObjectByte(16);
      return (b0 & 1) != 0;
   }

   public void writeSpawnData(ByteBuf data) {
      data.writeInt(this.color);
      data.writeDouble(super.motionX);
      data.writeDouble(super.motionY);
      data.writeDouble(super.motionZ);
      data.writeFloat(super.rotationYaw);
      data.writeFloat(super.rotationPitch);
   }

   public void readSpawnData(ByteBuf data) {
      this.color = data.readInt();
      super.motionX = data.readDouble();
      super.motionY = data.readDouble();
      super.motionZ = data.readDouble();
      super.rotationYaw = data.readFloat();
      super.rotationPitch = data.readFloat();
      super.prevRotationYaw = super.rotationYaw;
      super.prevRotationPitch = super.rotationPitch;
   }
}
