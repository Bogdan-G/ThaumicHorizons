package com.kentington.thaumichorizons.common.lib;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectList;

public class SelfInfusionRecipe {

   protected AspectList aspects;
   protected String research;
   private ItemStack[] components;
   protected int instability;
   protected int id;


   public SelfInfusionRecipe(String research, int inst, AspectList aspects2, ItemStack[] recipe, int id) {
      this.research = research;
      this.instability = inst;
      this.aspects = aspects2.copy();
      this.components = recipe;
      this.id = id;
   }

   public boolean matches(ArrayList input, World world, EntityPlayer player) {
      if(this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) {
         return false;
      } else {
         ArrayList ii = new ArrayList();
         Iterator i2 = input.iterator();

         while(i2.hasNext()) {
            ItemStack is = (ItemStack)i2.next();
            ii.add(is.copy());
         }

         ItemStack[] var13 = this.getComponents();
         int var7 = var13.length;
         int var8 = 0;

         while(var8 < var7) {
            ItemStack comp = var13[var8];
            boolean b = false;
            int a = 0;

            while(true) {
               if(a < ii.size()) {
                  ItemStack var12 = ((ItemStack)ii.get(a)).copy();
                  if(comp.getItemDamage() == 32767) {
                     var12.setItemDamage(32767);
                  }

                  if(!areItemStacksEqual(var12, comp, true)) {
                     ++a;
                     continue;
                  }

                  ii.remove(a);
                  b = true;
               }

               if(!b) {
                  return false;
               }

               ++var8;
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

   public AspectList getAspects() {
      return this.aspects;
   }

   public int getInstability() {
      return this.instability;
   }

   public String getResearch() {
      return this.research;
   }

   public ItemStack[] getComponents() {
      return this.components;
   }

   public int getID() {
      return this.id;
   }
}
