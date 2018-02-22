package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.items.ItemFocusContainment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;
import thaumcraft.common.entities.projectile.EntityEmber;

public class EntityNetherHound extends EntityWolf {

   long soundDelay = 0L;


   public EntityNetherHound(World p_i1696_1_) {
      super(p_i1696_1_);
      super.isImmuneToFire = true;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      EntityLivingBase target = null;
      if(this.getAITarget() != null) {
         target = this.getAITarget();
      }

      if(this.getAttackTarget() != null) {
         target = this.getAttackTarget();
      }

      if(target != null && ItemFocusContainment.getPointedEntity(super.worldObj, this, 7.0D) == target) {
         if(!super.worldObj.isRemote && this.soundDelay < System.currentTimeMillis()) {
            super.worldObj.playSoundAtEntity(this, "thaumcraft:fireloop", 0.33F, 2.0F);
            this.soundDelay = System.currentTimeMillis() + 500L;
         }

         float scatter = 8.0F;
         EntityEmber orb = new EntityEmber(super.worldObj, this, scatter);
         orb.damage = 1.0F;
         orb.firey = 1;
         orb.posX+= orb.motionX;
         orb.posY += orb.motionY;
         orb.posZ += orb.motionZ;
         super.worldObj.spawnEntityInWorld(orb);
      }

   }
}
