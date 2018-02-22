package thaumcraft.api.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectList;

public class InfusionEnchantmentRecipe {

   public AspectList aspects;
   public String research;
   public ItemStack[] components;
   public Enchantment enchantment;
   public int recipeXP;
   public int instability;


   public InfusionEnchantmentRecipe(String research, Enchantment input, int inst, AspectList aspects2, ItemStack[] recipe) {
      this.research = research;
      this.enchantment = input;
      this.aspects = aspects2;
      this.components = recipe;
      this.instability = inst;
      this.recipeXP = Math.max(1, input.getMinEnchantability(1) / 3);
   }

   public boolean matches(ArrayList input, ItemStack central, World world, EntityPlayer player) {
      if(this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) {
         return false;
      } else if(this.enchantment.canApply(central) && central.getItem().isItemTool(central)) {
         Map map1 = EnchantmentHelper.getEnchantments(central);
         Iterator iterator = map1.keySet().iterator();

         while(true) {
            if(iterator.hasNext()) {
               int var15 = ((Integer)iterator.next()).intValue();
               Enchantment var16 = Enchantment.enchantmentsList[var15];
               if(var15 == this.enchantment.effectId && EnchantmentHelper.getEnchantmentLevel(var15, central) >= var16.getMaxLevel()) {
                  return false;
               }

               if(this.enchantment.effectId == var16.effectId || this.enchantment.canApplyTogether(var16) && var16.canApplyTogether(this.enchantment)) {
                  continue;
               }

               return false;
            }

            ItemStack i2 = null;
            ArrayList ii = new ArrayList();
            Iterator var9 = input.iterator();

            while(var9.hasNext()) {
               ItemStack is = (ItemStack)var9.next();
               ii.add(is.copy());
            }

            ItemStack[] var17 = this.components;
            int var18 = var17.length;
            int var11 = 0;

            while(var11 < var18) {
               ItemStack comp = var17[var11];
               boolean b = false;
               int a = 0;

               while(true) {
                  if(a < ii.size()) {
                     i2 = ((ItemStack)ii.get(a)).copy();
                     if(comp.getItemDamage() == 32767) {
                        i2.setItemDamage(32767);
                     }

                     if(!this.areItemStacksEqual(i2, comp, true)) {
                        ++a;
                        continue;
                     }

                     ii.remove(a);
                     b = true;
                  }

                  if(!b) {
                     return false;
                  }

                  ++var11;
                  break;
               }
            }

            return ii.size() == 0;
         }
      } else {
         return false;
      }
   }

   protected boolean areItemStacksEqual(ItemStack stack0, ItemStack stack1, boolean fuzzy) {
      if(stack0 == null && stack1 != null) {
         return false;
      } else if(stack0 != null && stack1 == null) {
         return false;
      } else if(stack0 == null && stack1 == null) {
         return true;
      } else {
         boolean t1 = ThaumcraftApiHelper.areItemStackTagsEqualForCrafting(stack0, stack1);
         if(!t1) {
            return false;
         } else {
            if(fuzzy) {
               int od = OreDictionary.getOreID(stack0);
               if(od != -1) {
                  ItemStack[] ores = (ItemStack[])OreDictionary.getOres(Integer.valueOf(od)).toArray(new ItemStack[0]);
                  if(ThaumcraftApiHelper.containsMatch(false, new ItemStack[]{stack1}, ores)) {
                     return true;
                  }
               }
            }

            return stack0.getItem() != stack1.getItem()?false:(stack0.getItemDamage() != stack1.getItemDamage()?false:stack0.stackSize <= stack0.getMaxStackSize());
         }
      }
   }

   public Enchantment getEnchantment() {
      return this.enchantment;
   }

   public AspectList getAspects() {
      return this.aspects;
   }

   public String getResearch() {
      return this.research;
   }

   public int calcInstability(ItemStack recipeInput) {
      int i = 0;
      Map map1 = EnchantmentHelper.getEnchantments(recipeInput);

      int j1;
      for(Iterator iterator = map1.keySet().iterator(); iterator.hasNext(); i += EnchantmentHelper.getEnchantmentLevel(j1, recipeInput)) {
         j1 = ((Integer)iterator.next()).intValue();
      }

      return i / 2 + this.instability;
   }

   public int calcXP(ItemStack recipeInput) {
      return this.recipeXP * (1 + EnchantmentHelper.getEnchantmentLevel(this.enchantment.effectId, recipeInput));
   }

   public float getEssentiaMod(ItemStack recipeInput) {
      float mod = (float)EnchantmentHelper.getEnchantmentLevel(this.enchantment.effectId, recipeInput);
      Map map1 = EnchantmentHelper.getEnchantments(recipeInput);
      Iterator iterator = map1.keySet().iterator();

      while(iterator.hasNext()) {
         int j1 = ((Integer)iterator.next()).intValue();
         if(j1 != this.enchantment.effectId) {
            mod += (float)EnchantmentHelper.getEnchantmentLevel(j1, recipeInput) * 0.1F;
         }
      }

      return mod;
   }
}
