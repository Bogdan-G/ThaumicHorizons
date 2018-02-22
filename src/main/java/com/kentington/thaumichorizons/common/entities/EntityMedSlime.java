package com.kentington.thaumichorizons.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityMedSlime extends EntitySlime {

   public EntityMedSlime(World p_i1742_1_) {
      super(p_i1742_1_);
   }

   protected String getSlimeParticle() {
      return "snowshovel";
   }

   protected Item getDropItem() {
      return Item.getItemById(0);
   }

   public void setDead() {
      int i = this.getSlimeSize();
      if(!super.worldObj.isRemote && i > 1) {
         int j = 2 + super.rand.nextInt(3);

         for(int k = 0; k < j; ++k) {
            float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
            float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
            EntityMedSlime entityslime = this.createInstance();
            entityslime.setSlimeSize(i / 2);
            entityslime.setLocationAndAngles(super.posX + (double)f, super.posY + 0.5D, super.posZ + (double)f1, super.rand.nextFloat() * 360.0F, 0.0F);
            super.worldObj.spawnEntityInWorld(entityslime);
         }
      }

      super.setDead();
   }

   protected EntityMedSlime createInstance() {
      return new EntityMedSlime(super.worldObj);
   }

   public void onCollideWithPlayer(EntityPlayer p_70100_1_) {
      if(p_70100_1_.getHealth() < p_70100_1_.getMaxHealth()) {
         int i = this.getSlimeSize();
         if(this.canEntityBeSeen(p_70100_1_) && this.getDistanceSqToEntity(p_70100_1_) < 0.6D * (double)i * 0.6D * (double)i) {
            p_70100_1_.heal((float)(this.getAttackStrength() + 1));
            this.playSound("mob.attack", 1.0F, (super.rand.nextFloat() - super.rand.nextFloat()) * 0.2F + 1.0F);
            super.worldObj.createExplosion((Entity)null, super.posX, super.posY + (double)(super.height / 2.0F), super.posZ, 0.0F, false);
            this.setDead();
         }
      }

   }
}
