package com.kentington.thaumichorizons.common.lib;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectList;

public class CreatureInfusionRecipe {

   protected AspectList aspects;
   protected String research;
   private ItemStack[] components;
   private Class recipeInput;
   protected Object recipeOutput;
   protected int instability;
   protected int id;


   public CreatureInfusionRecipe(String research, Object output, int inst, AspectList aspects2, Class input, ItemStack[] recipe, int id) {
      this.research = research;
      this.recipeOutput = output;
      this.recipeInput = input;
      this.aspects = aspects2;
      this.components = recipe;
      this.instability = inst;
      this.id = id;
   }

   public boolean matches(ArrayList input, Class central, World world, EntityPlayer player) {
      if(this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) {
         return false;
      } else if(this.recipeInput != null && !central.isAssignableFrom(this.recipeInput)) {
         return false;
      } else {
         ArrayList ii = new ArrayList();
         Iterator i2 = input.iterator();

         while(i2.hasNext()) {
            ItemStack is = (ItemStack)i2.next();
            ii.add(is.copy());
         }

         ItemStack[] var14 = this.getComponents();
         int var8 = var14.length;
         int var9 = 0;

         while(var9 < var8) {
            ItemStack comp = var14[var9];
            boolean b = false;
            int a = 0;

            while(true) {
               if(a < ii.size()) {
                  ItemStack var13 = ((ItemStack)ii.get(a)).copy();
                  if(comp.getItemDamage() == 32767) {
                     var13.setItemDamage(32767);
                  }

                  if(!areItemStacksEqual(var13, comp, true)) {
                     ++a;
                     continue;
                  }

                  ii.remove(a);
                  b = true;
               }

               if(!b) {
                  return false;
               }

               ++var9;
               break;
            }
         }

         return ii.size() == 0;
      }
   }

   public static boolean areItemStacksEqual(ItemStack stack0, ItemStack stack1, boolean fuzzy) {
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
               int damage = OreDictionary.getOreID(stack0);
               if(damage != -1) {
                  ItemStack[] ores = (ItemStack[])OreDictionary.getOres(Integer.valueOf(damage)).toArray(new ItemStack[0]);
                  if(ThaumcraftApiHelper.containsMatch(false, new ItemStack[]{stack1}, ores)) {
                     return true;
                  }
               }
            }

            boolean damage1 = stack0.getItemDamage() == stack1.getItemDamage() || stack1.getItemDamage() == 32767;
            return stack0.getItem() != stack1.getItem()?false:(!damage1?false:stack0.stackSize <= stack0.getMaxStackSize());
         }
      }
   }

   public Object getRecipeOutput() {
      return this.getRecipeOutput(this.getRecipeInput());
   }

   public AspectList getAspects() {
      return this.getAspects(this.getRecipeInput());
   }

   public int getInstability() {
      return this.getInstability(this.getRecipeInput());
   }

   public String getResearch() {
      return this.research;
   }

   public Class getRecipeInput() {
      return this.recipeInput;
   }

   public ItemStack[] getComponents() {
      return this.components;
   }

   public Object getRecipeOutput(Class input) {
      return this.recipeOutput;
   }

   public AspectList getAspects(Class input) {
      return this.aspects;
   }

   public int getInstability(Class input) {
      return this.instability;
   }

   public int getID(Class input) {
      return this.id;
   }
}
