package com.kentington.thaumichorizons.common.entities;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityMeatSlime extends EntitySlime {

   public EntityMeatSlime(World p_i1742_1_) {
      super(p_i1742_1_);
   }

   protected String getSlimeParticle() {
      return "reddust";
   }

   protected Item getDropItem() {
      return Item.getItemById(0);
   }

   public void setDead() {
      int i = this.getSlimeSize();
      if(!super.worldObj.isRemote && i > 1 && this.getHealth() <= 0.0F) {
         int j = 2 + super.rand.nextInt(3);

         for(int k = 0; k < j; ++k) {
            float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
            float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
            EntityMeatSlime entityslime = this.createInstance();
            entityslime.setSlimeSize(i / 2);
            entityslime.setLocationAndAngles(super.posX + (double)f, super.posY + 0.5D, super.posZ + (double)f1, super.rand.nextFloat() * 360.0F, 0.0F);
            super.worldObj.spawnEntityInWorld(entityslime);
         }
      }

      super.setDead();
   }

   protected EntityMeatSlime createInstance() {
      return new EntityMeatSlime(super.worldObj);
   }
}
