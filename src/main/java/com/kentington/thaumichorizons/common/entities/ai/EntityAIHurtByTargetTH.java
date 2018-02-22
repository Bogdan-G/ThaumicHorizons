package com.kentington.thaumichorizons.common.entities.ai;

import com.kentington.thaumichorizons.common.entities.ai.EntityAITargetTH;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;

public class EntityAIHurtByTargetTH extends EntityAITargetTH {

   boolean entityCallsForHelp;
   private int field_142052_b;
   private static final String __OBFID = "CL_00001619";


   public EntityAIHurtByTargetTH(EntityLiving p_i1660_1_, boolean p_i1660_2_) {
      super(p_i1660_1_, false);
      this.entityCallsForHelp = p_i1660_2_;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      int i = super.taskOwner.func_142015_aE();
      return i != this.field_142052_b && this.isSuitableTarget(super.taskOwner.getAITarget(), false);
   }

   public void startExecuting() {
      super.taskOwner.setAttackTarget(super.taskOwner.getAITarget());
      this.field_142052_b = super.taskOwner.func_142015_aE();
      if(this.entityCallsForHelp) {
         double d0 = this.getTargetDistance();
         List list = super.taskOwner.worldObj.getEntitiesWithinAABB(super.taskOwner.getClass(), AxisAlignedBB.getBoundingBox(super.taskOwner.posX, super.taskOwner.posY, super.taskOwner.posZ, super.taskOwner.posX + 1.0D, super.taskOwner.posY + 1.0D, super.taskOwner.posZ + 1.0D).expand(d0, 10.0D, d0));
         Iterator iterator = list.iterator();

         while(iterator.hasNext()) {
            EntityLiving EntityLiving = (EntityLiving)iterator.next();
            if(super.taskOwner != EntityLiving && EntityLiving.getAttackTarget() == null && !EntityLiving.isOnSameTeam(super.taskOwner.getAITarget())) {
               EntityLiving.setAttackTarget(super.taskOwner.getAITarget());
            }
         }
      }

      super.startExecuting();
   }
}
