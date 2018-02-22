package com.kentington.thaumichorizons.common.items.crafting;

import com.kentington.thaumichorizons.common.items.ItemFocusIllumination;
import java.util.ArrayList;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipesFocusIlluminationDyes implements IRecipe {

   public boolean matches(InventoryCrafting par1InventoryCrafting, World p_77569_2_) {
      ItemStack itemstack = null;
      ArrayList arraylist = new ArrayList();

      for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i) {
         ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(i);
         if(itemstack1 != null) {
            if(itemstack1.getItem() instanceof ItemFocusIllumination) {
               ItemFocusIllumination itemarmor = (ItemFocusIllumination)itemstack1.getItem();
               if(itemstack != null) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if(itemstack1.getItem() != Items.dye) {
                  return false;
               }

               arraylist.add(itemstack1);
            }
         }
      }

      return itemstack != null && !arraylist.isEmpty();
   }

   public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting) {
      ItemStack itemstack = null;
      ItemFocusIllumination itemarmor = null;
      int color = 0;

      for(int k = 0; k < par1InventoryCrafting.getSizeInventory(); ++k) {
         ItemStack itemstack1 = par1InventoryCrafting.getStackInSlot(k);
         if(itemstack1 != null) {
            if(itemstack1.getItem() instanceof ItemFocusIllumination) {
               itemarmor = (ItemFocusIllumination)itemstack1.getItem();
               if(itemstack != null) {
                  return null;
               }

               itemstack = itemstack1.copy();
               itemstack.stackSize = 1;
            } else {
               if(itemstack1.getItem() != Items.dye) {
                  return null;
               }

               color = itemstack1.getItemDamage();
            }
         }
      }

      if(itemarmor == null) {
         return null;
      } else {
         itemstack.setItemDamage(color);
         return itemstack;
      }
   }

   public int getRecipeSize() {
      return 10;
   }

   public ItemStack getRecipeOutput() {
      return null;
   }
}
