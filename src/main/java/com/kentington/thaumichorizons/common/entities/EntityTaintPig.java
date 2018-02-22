package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.entities.ai.EntityAIEatTaint;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import thaumcraft.common.config.Config;

public class EntityTaintPig extends EntityPig {

   public EntityTaintPig(World p_i1689_1_) {
      super(p_i1689_1_);
      super.tasks.addTask(9, new EntityAIEatTaint(this));
   }

   public void updateAITick() {
      super.updateAITick();
      if(this.getActivePotionEffect(Potion.potionTypes[Config.potionTaintPoisonID]) != null) {
         this.removePotionEffect(Config.potionTaintPoisonID);
      }

      if(this.getActivePotionEffect(Potion.potionTypes[Config.potionInfVisExhaustID]) != null) {
         this.removePotionEffect(Config.potionInfVisExhaustID);
      }

      if(this.getActivePotionEffect(Potion.potionTypes[Config.potionVisExhaustID]) != null) {
         this.removePotionEffect(Config.potionVisExhaustID);
      }

   }
}
