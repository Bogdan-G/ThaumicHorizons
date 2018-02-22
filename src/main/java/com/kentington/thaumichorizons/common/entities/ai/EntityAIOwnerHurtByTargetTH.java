package com.kentington.thaumichorizons.common.entities.ai;

import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIOwnerHurtByTargetTH extends EntityAITarget {

   EntityLiving theDefendingTameable;
   EntityLivingBase theOwnerAttacker;
   private int field_142051_e;
   private static final String __OBFID = "CL_00001624";


   public EntityAIOwnerHurtByTargetTH(EntityLiving p_i1667_1_) {
      super((EntityCreature)p_i1667_1_, false);
      this.theDefendingTameable = p_i1667_1_;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      EntityInfusionProperties prop = (EntityInfusionProperties)this.theDefendingTameable.getExtendedProperties("CreatureInfusion");
      EntityPlayer entitylivingbase = this.theDefendingTameable.worldObj.getPlayerEntityByName(prop.getOwner());
      if(entitylivingbase == null) {
         return false;
      } else {
         this.theOwnerAttacker = entitylivingbase.getAITarget();
         int i = entitylivingbase.func_142015_aE();
         return i != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false);
      }
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget(this.theOwnerAttacker);
      EntityInfusionProperties prop = (EntityInfusionProperties)this.theDefendingTameable.getExtendedProperties("CreatureInfusion");
      EntityPlayer entitylivingbase = this.theDefendingTameable.worldObj.getPlayerEntityByName(prop.getOwner());
      if(entitylivingbase != null) {
         this.field_142051_e = entitylivingbase.func_142015_aE();
      }

      super.startExecuting();
   }
}
