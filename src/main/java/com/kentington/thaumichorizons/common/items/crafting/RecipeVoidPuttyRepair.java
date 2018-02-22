package com.kentington.thaumichorizons.common.items.crafting;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import java.util.ArrayList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeVoidPuttyRepair implements IRecipe {

   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
      ItemStack itemstack = null;
      ArrayList arraylist = new ArrayList();

      for(int output = 0; output < par1InventoryCrafting.getSizeInventory(); ++output) {
         ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(output);
         if(itemstack1 != null) {
            if(itemstack1.getItem().isDamageable() && itemstack1.getItem().isDamaged(itemstack1)) {
               if(itemstack != null) {
                  return null;
               }

               itemstack = itemstack1;
            } else {
               if(itemstack1.getItem() != ThaumicHorizons.itemVoidPutty) {
                  return null;
               }

               arraylist.add(itemstack1);
            }
         }
      }

      ItemStack var6 = itemstack.copy();
      var6.setItemDamage(0);
      return var6;
   }

   public ItemStack getRecipeOutput() {
      return null;
   }

   public int getRecipeSize() {
      return 10;
   }

   public boolean matches(InventoryCrafting par1InventoryCrafting, World arg1) {
      ItemStack itemstack = null;
      ArrayList arraylist = new ArrayList();

      for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i) {
         ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(i);
         if(itemstack1 != null) {
            if(itemstack1.getItem().isDamageable() && itemstack1.getItem().isDamaged(itemstack1)) {
               if(itemstack != null) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if(itemstack1.getItem() != ThaumicHorizons.itemVoidPutty) {
                  return false;
               }

               arraylist.add(itemstack1);
            }
         }
      }

      return itemstack != null && !arraylist.isEmpty();
   }
}
