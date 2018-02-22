package thaumcraft.api.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class CrucibleRecipe {

   private ItemStack recipeOutput;
   public Object catalyst;
   public AspectList aspects;
   public String key;
   public int hash;


   public CrucibleRecipe(String researchKey, ItemStack result, Object cat, AspectList tags) {
      this.recipeOutput = result;
      this.aspects = tags;
      this.key = researchKey;
      this.catalyst = cat;
      if(cat instanceof String) {
         this.catalyst = OreDictionary.getOres((String)cat);
      }

      String hc = researchKey + result.toString();
      Aspect[] var6 = tags.getAspects();
      int is = var6.length;

      for(int var8 = 0; var8 < is; ++var8) {
         Aspect tag = var6[var8];
         hc = hc + tag.getTag() + tags.getAmount(tag);
      }

      if(cat instanceof ItemStack) {
         hc = hc + ((ItemStack)cat).toString();
      } else {
         ItemStack var11;
         if(cat instanceof ArrayList && ((ArrayList)this.catalyst).size() > 0) {
            for(Iterator var10 = ((ArrayList)this.catalyst).iterator(); var10.hasNext(); hc = hc + var11.toString()) {
               var11 = (ItemStack)var10.next();
            }
         }
      }

      this.hash = hc.hashCode();
   }

   public boolean matches(AspectList itags, ItemStack cat) {
      if(this.catalyst instanceof ItemStack && !ThaumcraftApiHelper.itemMatches((ItemStack)this.catalyst, cat, false)) {
         return false;
      } else {
         if(this.catalyst instanceof ArrayList && ((ArrayList)this.catalyst).size() > 0) {
            ItemStack[] ores = (ItemStack[])((ArrayList)this.catalyst).toArray(new ItemStack[0]);
            if(!ThaumcraftApiHelper.containsMatch(false, new ItemStack[]{cat}, ores)) {
               return false;
            }
         }

         if(itags == null) {
            return false;
         } else {
            Aspect[] var7 = this.aspects.getAspects();
            int var4 = var7.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Aspect tag = var7[var5];
               if(itags.getAmount(tag) < this.aspects.getAmount(tag)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public boolean catalystMatches(ItemStack cat) {
      if(this.catalyst instanceof ItemStack && ThaumcraftApiHelper.itemMatches((ItemStack)this.catalyst, cat, false)) {
         return true;
      } else {
         if(this.catalyst instanceof ArrayList && ((ArrayList)this.catalyst).size() > 0) {
            ItemStack[] ores = (ItemStack[])((ArrayList)this.catalyst).toArray(new ItemStack[0]);
            if(ThaumcraftApiHelper.containsMatch(false, new ItemStack[]{cat}, ores)) {
               return true;
            }
         }

         return false;
      }
   }

   public AspectList removeMatching(AspectList itags) {
      AspectList temptags = new AspectList();
      temptags.aspects.putAll(itags.aspects);
      Aspect[] var3 = this.aspects.getAspects();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Aspect tag = var3[var5];
         temptags.remove(tag, this.aspects.getAmount(tag));
      }

      return temptags;
   }

   public ItemStack getRecipeOutput() {
      return this.recipeOutput;
   }
}
