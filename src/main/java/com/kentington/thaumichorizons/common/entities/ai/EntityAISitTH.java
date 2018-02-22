package com.kentington.thaumichorizons.common.entities.ai;

import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAISitTH extends EntityAIBase {

   private EntityLiving theEntity;
   private boolean isSitting;
   private static final String __OBFID = "CL_00001613";


   public EntityAISitTH(EntityLiving p_i1654_1_) {
      this.theEntity = p_i1654_1_;
      this.setMutexBits(5);
   }

   public boolean shouldExecute() {
      if(this.theEntity.isInWater()) {
         return false;
      } else if(!this.theEntity.onGround) {
         return false;
      } else {
         EntityInfusionProperties prop = (EntityInfusionProperties)this.theEntity.getExtendedProperties("CreatureInfusion");
         EntityPlayer entitylivingbase = this.theEntity.worldObj.getPlayerEntityByName(prop.getOwner());
         return entitylivingbase == null?true:(this.theEntity.getDistanceSqToEntity(entitylivingbase) < 144.0D && entitylivingbase.getAITarget() != null?false:this.isSitting);
      }
   }

   public void startExecuting() {
      EntityInfusionProperties prop = (EntityInfusionProperties)this.theEntity.getExtendedProperties("CreatureInfusion");
      this.theEntity.getNavigator().clearPathEntity();
      prop.setSitting(true);
   }

   public void resetTask() {
      EntityInfusionProperties prop = (EntityInfusionProperties)this.theEntity.getExtendedProperties("CreatureInfusion");
      prop.setSitting(false);
   }

   public void setSitting(boolean p_75270_1_) {
      this.isSitting = p_75270_1_;
   }
}
