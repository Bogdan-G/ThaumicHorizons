package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityChocolateCow extends EntityCow {

   public EntityChocolateCow(World p_i1683_1_) {
      super(p_i1683_1_);
   }

   public boolean interact(EntityPlayer p_70085_1_) {
      ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
      if(itemstack != null && itemstack.getItem() == Items.bucket) {
         if(itemstack.stackSize-- == 1) {
            p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, new ItemStack(ThaumicHorizons.itemBucketChocolate));
         } else if(!p_70085_1_.inventory.addItemStackToInventory(new ItemStack(ThaumicHorizons.itemBucketChocolate))) {
            p_70085_1_.dropPlayerItemWithRandomChoice(new ItemStack(ThaumicHorizons.itemBucketChocolate, 1, 0), false);
         }

         return true;
      } else {
         return super.interact(p_70085_1_);
      }
   }
}
