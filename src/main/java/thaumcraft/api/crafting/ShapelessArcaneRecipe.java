package thaumcraft.api.crafting;

import java.util.ArrayList;
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

public class ShapelessArcaneRecipe implements IArcaneRecipe {

   private ItemStack output;
   private ArrayList input;
   public AspectList aspects;
   public String research;


   public ShapelessArcaneRecipe(String research, Block result, AspectList aspects, Object ... recipe) {
      this(research, new ItemStack(result), aspects, recipe);
   }

   public ShapelessArcaneRecipe(String research, Item result, AspectList aspects, Object ... recipe) {
      this(research, new ItemStack(result), aspects, recipe);
   }

   public ShapelessArcaneRecipe(String research, ItemStack result, AspectList aspects, Object ... recipe) {
      this.output = null;
      this.input = new ArrayList();
      this.aspects = null;
      this.output = result.copy();
      this.research = research;
      this.aspects = aspects;
      Object[] var5 = recipe;
      int var6 = recipe.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object in = var5[var7];
         if(in instanceof ItemStack) {
            this.input.add(((ItemStack)in).copy());
         } else if(in instanceof Item) {
            this.input.add(new ItemStack((Item)in));
         } else if(in instanceof Block) {
            this.input.add(new ItemStack((Block)in));
         } else {
            if(!(in instanceof String)) {
               String ret = "Invalid shapeless ore recipe: ";
               Object[] var10 = recipe;
               int var11 = recipe.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  Object tmp = var10[var12];
                  ret = ret + tmp + ", ";
               }

               ret = ret + this.output;
               throw new RuntimeException(ret);
            }

            this.input.add(OreDictionary.getOres((String)in));
         }
      }

   }

   public int getRecipeSize() {
      return this.input.size();
   }

   public ItemStack getRecipeOutput() {
      return this.output;
   }

   public ItemStack getCraftingResult(IInventory var1) {
      return this.output.copy();
   }

   public boolean matches(IInventory var1, World world, EntityPlayer player) {
      if(this.research.length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), this.research)) {
         return false;
      } else {
         ArrayList required = new ArrayList(this.input);

         for(int x = 0; x < 9; ++x) {
            ItemStack slot = var1.getStackInSlot(x);
            if(slot != null) {
               boolean inRecipe = false;
               Iterator req = required.iterator();

               while(req.hasNext()) {
                  boolean match = false;
                  Object next = req.next();
                  if(next instanceof ItemStack) {
                     match = this.checkItemEquals((ItemStack)next, slot);
                  } else {
                     ItemStack item;
                     if(next instanceof ArrayList) {
                        for(Iterator var11 = ((ArrayList)next).iterator(); var11.hasNext(); match = match || this.checkItemEquals(item, slot)) {
                           item = (ItemStack)var11.next();
                        }
                     }
                  }

                  if(match) {
                     inRecipe = true;
                     required.remove(next);
                     break;
                  }
               }

               if(!inRecipe) {
                  return false;
               }
            }
         }

         return required.isEmpty();
      }
   }

   private boolean checkItemEquals(ItemStack target, ItemStack input) {
      return (input != null || target == null) && (input == null || target != null)?target.getItem() == input.getItem() && (!target.hasTagCompound() || ThaumcraftApiHelper.areItemStackTagsEqualForCrafting(input, target)) && (target.getItemDamage() == 32767 || target.getItemDamage() == input.getItemDamage()):false;
   }

   public ArrayList getInput() {
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
