package com.kentington.thaumichorizons.common.items.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeEnchantClay implements IRecipe {

   public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
      return false;
   }

   public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
      return null;
   }

   public int getRecipeSize() {
      return 0;
   }

   public ItemStack getRecipeOutput() {
      return null;
   }
}
