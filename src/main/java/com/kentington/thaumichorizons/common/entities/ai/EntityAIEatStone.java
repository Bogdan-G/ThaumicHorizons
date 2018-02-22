package com.kentington.thaumichorizons.common.entities.ai;

import com.kentington.thaumichorizons.common.entities.EntityOrePig;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIEatStone extends EntityAIBase {

   private EntityOrePig thePig;
   private Entity targetEntity;
   int count = 0;


   public EntityAIEatStone(EntityOrePig par1EntityCreature) {
      this.thePig = par1EntityCreature;
   }

   public boolean shouldExecute() {
      return this.findItem();
   }

   private boolean findItem() {
      float dmod = 16.0F;
      List targets = this.thePig.worldObj.getEntitiesWithinAABBExcludingEntity(this.thePig, AxisAlignedBB.getBoundingBox(this.thePig.posX - 16.0D, this.thePig.posY - 16.0D, this.thePig.posZ - 16.0D, this.thePig.posX + 16.0D, this.thePig.posY + 16.0D, this.thePig.posZ + 16.0D));
      if(targets.size() == 0) {
         return false;
      } else {
         Iterator var3 = targets.iterator();

         while(var3.hasNext()) {
            Entity e = (Entity)var3.next();
            if(e instanceof EntityItem && ((EntityItem)e).getEntityItem().getItem() == Item.getItemFromBlock(Blocks.cobblestone) && ((EntityItem)e).delayBeforeCanPickup < 5) {
               double distance2 = e.getDistanceSq(this.thePig.posX, this.thePig.posY, this.thePig.posZ);
               if(distance2 < (double)(dmod * dmod)) {
                  this.targetEntity = e;
               }
            }
         }

         if(this.targetEntity == null) {
            return false;
         } else {
            return true;
         }
      }
   }

   public boolean continueExecuting() {
      return this.count-- > 0 && !this.thePig.getNavigator().noPath() && this.targetEntity.isEntityAlive();
   }

   public void resetTask() {
      this.count = 0;
      this.targetEntity = null;
      this.thePig.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      this.thePig.getLookHelper().setLookPositionWithEntity(this.targetEntity, 30.0F, 30.0F);
      double dist = this.thePig.getDistanceSqToEntity(this.targetEntity);
      if(dist <= 2.0D) {
         this.pickUp();
      }

   }

   private void pickUp() {
      boolean amount = false;
      if(this.targetEntity instanceof EntityItem) {
         this.thePig.eatStone();
         --((EntityItem)this.targetEntity).getEntityItem().stackSize;
         if(((EntityItem)this.targetEntity).getEntityItem().stackSize <= 0) {
            this.targetEntity.setDead();
         }
      }

      this.targetEntity.worldObj.playSoundAtEntity(this.targetEntity, "random.burp", 0.2F, ((this.targetEntity.worldObj.rand.nextFloat() - this.targetEntity.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
   }

   public void startExecuting() {
      this.count = 500;
      this.thePig.getNavigator().tryMoveToEntityLiving(this.targetEntity, (double)(this.thePig.getAIMoveSpeed() + 1.0F));
   }
}
