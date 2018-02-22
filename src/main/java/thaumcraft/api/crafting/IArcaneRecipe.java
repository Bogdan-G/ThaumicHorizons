package thaumcraft.api.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;

public interface IArcaneRecipe {

   boolean matches(IInventory var1, World var2, EntityPlayer var3);

   ItemStack getCraftingResult(IInventory var1);

   int getRecipeSize();

   ItemStack getRecipeOutput();

   AspectList getAspects();

   AspectList getAspects(IInventory var1);

   String getResearch();
}
