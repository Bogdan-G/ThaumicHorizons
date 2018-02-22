package com.kentington.thaumichorizons.common.entities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemInvulnerable extends EntityItem {

   public EntityItemInvulnerable(World p_i1710_1_, double p_i1710_2_, double p_i1710_4_, double p_i1710_6_, ItemStack p_i1710_8_) {
      super(p_i1710_1_, p_i1710_2_, p_i1710_4_, p_i1710_6_, p_i1710_8_);
   }

   public boolean attackEntityFrom(DamageSource source, float num) {
      return false;
   }
}
