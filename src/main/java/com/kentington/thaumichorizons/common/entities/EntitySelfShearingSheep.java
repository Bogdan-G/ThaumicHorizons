package com.kentington.thaumichorizons.common.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySelfShearingSheep extends EntitySheep {

   public EntitySelfShearingSheep(World p_i1691_1_) {
      super(p_i1691_1_);
   }

   public void onLivingUpdate() {
      if(!super.worldObj.isRemote && !this.getSheared() && super.ticksExisted % 100 == 0) {
         ArrayList drops = this.onSheared(new ItemStack(Items.shears), super.worldObj, (int)super.posX, (int)super.posY, (int)super.posZ, 0);
         Random rand = new org.bogdang.modifications.random.XSTR();

         EntityItem ent;
         for(Iterator var3 = drops.iterator(); var3.hasNext(); ent.motionZ += (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)) {
            ItemStack stack = (ItemStack)var3.next();
            ent = this.entityDropItem(stack, 1.0F);
            ent.motionY += (double)(rand.nextFloat() * 0.05F);
            ent.motionX += (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F);
         }
      }

      super.onLivingUpdate();
   }
}
