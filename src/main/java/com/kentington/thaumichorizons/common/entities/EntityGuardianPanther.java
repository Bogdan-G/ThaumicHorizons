package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.entities.IEntityInfusedStats;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityGuardianPanther extends EntityOcelot implements IEntityInfusedStats {

   public EntityGuardianPanther(World p_i1688_1_) {
      super(p_i1688_1_);
      this.setSize(1.2F, 1.6F);
      ArrayList toRemove = new ArrayList();
      Iterator var3 = super.tasks.taskEntries.iterator();

      EntityAITaskEntry task;
      while(var3.hasNext()) {
         task = (EntityAITaskEntry)var3.next();
         toRemove.add(task);
      }

      var3 = toRemove.iterator();

      while(var3.hasNext()) {
         task = (EntityAITaskEntry)var3.next();
         super.tasks.removeTask(task.action);
      }

      super.tasks.addTask(1, new EntityAISwimming(this));
      super.tasks.addTask(2, super.aiSit);
      super.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.5F));
      super.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
      super.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
      super.tasks.addTask(6, new EntityAIMate(this, 0.8D));
      super.tasks.addTask(7, new EntityAIWander(this, 0.8D));
      super.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
      super.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
      super.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
      super.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5000000119209289D);
   }

   public boolean interact(EntityPlayer p_70085_1_) {
      ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
      if(itemstack != null && itemstack.getItem() instanceof ItemFood) {
         ItemFood itemfood = (ItemFood)itemstack.getItem();
         if(this.getHealth() < this.getMaxHealth()) {
            --itemstack.stackSize;
            this.heal((float)itemfood.func_150905_g(itemstack));
            if(itemstack.stackSize <= 0) {
               p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack)null);
            }

            return true;
         }
      }

      return super.interact(p_70085_1_);
   }

   public boolean attackEntityAsMob(Entity p_70652_1_) {
      return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 6.0F);
   }

   public String getCommandSenderName() {
      return this.hasCustomNameTag()?this.getCustomNameTag():(this.isTamed()?StatCollector.translateToLocal("entity.ThaumicHorizons.GuardianPanther.name"):super.getCommandSenderName());
   }

   public void updateAITick() {
      super.updateAITick();
   }

   protected void entityInit() {
      super.entityInit();
      byte b0 = super.dataWatcher.getWatchableObjectByte(16);
      super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 4)));
   }

   public boolean isTamed() {
      return true;
   }

   public void resetStats() {
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5000000119209289D);
   }
}
