package thaumcraft.api.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;

public class ShapedArcaneRecipe implements IArcaneRecipe {

   private static final int MAX_CRAFT_GRID_WIDTH = 3;
   private static final int MAX_CRAFT_GRID_HEIGHT = 3;
   public ItemStack output;
   public Object[] input;
   public AspectList aspects;
   public String research;
   public int width;
   public int height;
   private boolean mirrored;


   public ShapedArcaneRecipe(String research, Block result, AspectList aspects, Object ... recipe) {
      this(research, new ItemStack(result), aspects, recipe);
   }

   public ShapedArcaneRecipe(String research, Item result, AspectList aspects, Object ... recipe) {
      this(research, new ItemStack(result), aspects, recipe);
   }

   public ShapedArcaneRecipe(String research, ItemStack result, AspectList aspects, Object ... recipe) {
      this.output = null;
      this.input = null;
      this.aspects = null;
      this.width = 0;
      this.height = 0;
      this.mirrored = true;
      this.output = result.copy();
      this.research = research;
      this.aspects = aspects;
      String shape = "";
      int idx = 0;
      if(recipe[idx] instanceof Boolean) {
         this.mirrored = ((Boolean)recipe[idx]).booleanValue();
         if(recipe[idx + 1] instanceof Object[]) {
            recipe = (Object[])((Object[])recipe[idx + 1]);
         } else {
            idx = 1;
         }
      }

      int in;
      int ret;
      String var15;
      if(recipe[idx] instanceof String[]) {
         String[] itemMap = (String[])((String[])recipe[idx++]);
         String[] x = itemMap;
         in = itemMap.length;

         for(ret = 0; ret < in; ++ret) {
            String tmp = x[ret];
            this.width = tmp.length();
            shape = shape + tmp;
         }

         this.height = itemMap.length;
      } else {
         while(recipe[idx] instanceof String) {
            var15 = (String)recipe[idx++];
            shape = shape + var15;
            this.width = var15.length();
            ++this.height;
         }
      }

      if(this.width * this.height != shape.length()) {
         var15 = "Invalid shaped ore recipe: ";
         Object[] var19 = recipe;
         in = recipe.length;

         for(ret = 0; ret < in; ++ret) {
            Object var24 = var19[ret];
            var15 = var15 + var24 + ", ";
         }

         var15 = var15 + this.output;
         throw new RuntimeException(var15);
      } else {
         HashMap var16;
         for(var16 = new HashMap(); idx < recipe.length; idx += 2) {
            Character var18 = (Character)recipe[idx];
            Object var20 = recipe[idx + 1];
            if(var20 instanceof ItemStack) {
               var16.put(var18, ((ItemStack)var20).copy());
            } else if(var20 instanceof Item) {
               var16.put(var18, new ItemStack((Item)var20));
            } else if(var20 instanceof Block) {
               var16.put(var18, new ItemStack((Block)var20, 1, 32767));
            } else {
               if(!(var20 instanceof String)) {
                  String var23 = "Invalid shaped ore recipe: ";
                  Object[] var22 = recipe;
                  int chr = recipe.length;

                  for(int var13 = 0; var13 < chr; ++var13) {
                     Object tmp1 = var22[var13];
                     var23 = var23 + tmp1 + ", ";
                  }

                  var23 = var23 + this.output;
                  throw new RuntimeException(var23);
               }

               var16.put(var18, OreDictionary.getOres((String)var20));
            }
         }

         this.input = new Object[this.width * this.height];
         int var17 = 0;
         char[] var21 = shape.toCharArray();
         ret = var21.length;

         for(int var26 = 0; var26 < ret; ++var26) {
            char var25 = var21[var26];
            this.input[var17++] = var16.get(Character.valueOf(var25));
         }

      }
   }

   public ItemStack getCraftingResult(IInventory var1) {
      return this.output.copy();
   }

   public int getRecipeSize() {
      return this.input.length;
   }

   public ItemStack getRecipeOutput() {
      return this.output;
   }

   public boolean matches(IInventory inv, World world, EntityPlayer player) {
      if(this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) {
         return false;
      } else {
         for(int x = 0; x <= 3 - this.width; ++x) {
            for(int y = 0; y <= 3 - this.height; ++y) {
               if(this.checkMatch(inv, x, y, false)) {
                  return true;
               }

               if(this.mirrored && this.checkMatch(inv, x, y, true)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean checkMatch(IInventory inv, int startX, int startY, boolean mirror) {
      for(int x = 0; x < 3; ++x) {
         for(int y = 0; y < 3; ++y) {
            int subX = x - startX;
            int subY = y - startY;
            Object target = null;
            if(subX >= 0 && subY >= 0 && subX < this.width && subY < this.height) {
               if(mirror) {
                  target = this.input[this.width - subX - 1 + subY * this.width];
               } else {
                  target = this.input[subX + subY * this.width];
               }
            }

            ItemStack slot = ThaumcraftApiHelper.getStackInRowAndColumn(inv, x, y);
            if(target instanceof ItemStack) {
               if(!this.checkItemEquals((ItemStack)target, slot)) {
                  return false;
               }
            } else if(target instanceof ArrayList) {
               boolean matched = false;

               ItemStack item;
               for(Iterator var12 = ((ArrayList)target).iterator(); var12.hasNext(); matched = matched || this.checkItemEquals(item, slot)) {
                  item = (ItemStack)var12.next();
               }

               if(!matched) {
                  return false;
               }
            } else if(target == null && slot != null) {
               return false;
            }
         }
      }

      return true;
   }

   private boolean checkItemEquals(ItemStack target, ItemStack input) {
      return (input != null || target == null) && (input == null || target != null)?target.getItem() == input.getItem() && (!target.hasTagCompound() || ThaumcraftApiHelper.areItemStackTagsEqualForCrafting(input, target)) && (target.getItemDamage() == 32767 || target.getItemDamage() == input.getItemDamage()):false;
   }

   public ShapedArcaneRecipe setMirrored(boolean mirror) {
      this.mirrored = mirror;
      return this;
   }

   public Object[] getInput() {
      return this.input;
   }

   public AspectList getAspects() {
      return this.aspects;
   }

   public AspectList getAspects(IInventory inv) {
      return this.aspects;
   }

   public String getResearch() {
      return this.research;
   }
}
