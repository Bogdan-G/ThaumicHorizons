package thaumcraft.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.internal.DummyInternalMethodHandler;
import thaumcraft.api.internal.IInternalMethodHandler;
import thaumcraft.api.internal.WeightedRandomLoot;
import thaumcraft.api.research.IScanEventHandler;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class ThaumcraftApi {

   public static ToolMaterial toolMatThaumium = EnumHelper.addToolMaterial("THAUMIUM", 3, 400, 7.0F, 2.0F, 22);
   public static ToolMaterial toolMatVoid = EnumHelper.addToolMaterial("VOID", 4, 150, 8.0F, 3.0F, 10);
   public static ToolMaterial toolMatElemental = EnumHelper.addToolMaterial("THAUMIUM_ELEMENTAL", 3, 1500, 10.0F, 3.0F, 18);
   public static ArmorMaterial armorMatThaumium = EnumHelper.addArmorMaterial("THAUMIUM", 25, new int[]{2, 6, 5, 2}, 25);
   public static ArmorMaterial armorMatSpecial = EnumHelper.addArmorMaterial("SPECIAL", 25, new int[]{1, 3, 2, 1}, 25);
   public static ArmorMaterial armorMatThaumiumFortress = EnumHelper.addArmorMaterial("FORTRESS", 40, new int[]{3, 7, 6, 3}, 25);
   public static ArmorMaterial armorMatVoid = EnumHelper.addArmorMaterial("VOID", 10, new int[]{3, 7, 6, 3}, 10);
   public static ArmorMaterial armorMatVoidFortress = EnumHelper.addArmorMaterial("VOIDFORTRESS", 18, new int[]{4, 8, 7, 4}, 10);
   public static int enchantFrugal;
   public static int enchantPotency;
   public static int enchantWandFortune;
   public static int enchantHaste;
   public static int enchantRepair;
   public static ArrayList portableHoleBlackList = new ArrayList();
   public static IInternalMethodHandler internalMethods = new DummyInternalMethodHandler();
   public static ArrayList scanEventhandlers = new ArrayList();
   public static ArrayList scanEntities = new ArrayList();
   private static ArrayList craftingRecipes = new ArrayList();
   private static HashMap smeltingBonus = new HashMap();
   private static HashMap keyCache = new HashMap();
   public static ConcurrentHashMap objectTags = new ConcurrentHashMap();
   public static ConcurrentHashMap groupedObjectTags = new ConcurrentHashMap();
   private static HashMap warpMap = new HashMap();


   public static void registerScanEventhandler(IScanEventHandler scanEventHandler) {
      scanEventhandlers.add(scanEventHandler);
   }

   public static void registerEntityTag(String entityName, AspectList aspects, ThaumcraftApi.EntityTagsNBT ... nbt) {
      scanEntities.add(new ThaumcraftApi.EntityTags(entityName, aspects, nbt));
   }

   public static void addSmeltingBonus(ItemStack in, ItemStack out) {
      smeltingBonus.put(Arrays.asList(new Object[]{in.getItem(), Integer.valueOf(in.getItemDamage())}), new ItemStack(out.getItem(), 0, out.getItemDamage()));
   }

   public static void addSmeltingBonus(String in, ItemStack out) {
      smeltingBonus.put(in, new ItemStack(out.getItem(), 0, out.getItemDamage()));
   }

   public static ItemStack getSmeltingBonus(ItemStack in) {
      ItemStack out = (ItemStack)smeltingBonus.get(Arrays.asList(new Object[]{in.getItem(), Integer.valueOf(in.getItemDamage())}));
      if(out == null) {
         out = (ItemStack)smeltingBonus.get(Arrays.asList(new Object[]{in.getItem(), Integer.valueOf(32767)}));
      }

      if(out == null) {
         String od = OreDictionary.getOreName(OreDictionary.getOreID(in));
         out = (ItemStack)smeltingBonus.get(od);
      }

      return out;
   }

   public static List getCraftingRecipes() {
      return craftingRecipes;
   }

   public static ShapedArcaneRecipe addArcaneCraftingRecipe(String research, ItemStack result, AspectList aspects, Object ... recipe) {
      ShapedArcaneRecipe r = new ShapedArcaneRecipe(research, result, aspects, recipe);
      craftingRecipes.add(r);
      return r;
   }

   public static ShapelessArcaneRecipe addShapelessArcaneCraftingRecipe(String research, ItemStack result, AspectList aspects, Object ... recipe) {
      ShapelessArcaneRecipe r = new ShapelessArcaneRecipe(research, result, aspects, recipe);
      craftingRecipes.add(r);
      return r;
   }

   public static InfusionRecipe addInfusionCraftingRecipe(String research, Object result, int instability, AspectList aspects, ItemStack input, ItemStack[] recipe) {
      if(!(result instanceof ItemStack) && !(result instanceof Object[])) {
         return null;
      } else {
         InfusionRecipe r = new InfusionRecipe(research, result, instability, aspects, input, recipe);
         craftingRecipes.add(r);
         return r;
      }
   }

   public static InfusionEnchantmentRecipe addInfusionEnchantmentRecipe(String research, Enchantment enchantment, int instability, AspectList aspects, ItemStack[] recipe) {
      InfusionEnchantmentRecipe r = new InfusionEnchantmentRecipe(research, enchantment, instability, aspects, recipe);
      craftingRecipes.add(r);
      return r;
   }

   public static InfusionRecipe getInfusionRecipe(ItemStack res) {
      Iterator var1 = getCraftingRecipes().iterator();

      Object r;
      do {
         if(!var1.hasNext()) {
            return null;
         }

         r = var1.next();
      } while(!(r instanceof InfusionRecipe) || !(((InfusionRecipe)r).getRecipeOutput() instanceof ItemStack) || !((ItemStack)((InfusionRecipe)r).getRecipeOutput()).isItemEqual(res));

      return (InfusionRecipe)r;
   }

   public static CrucibleRecipe addCrucibleRecipe(String key, ItemStack result, Object catalyst, AspectList tags) {
      CrucibleRecipe rc = new CrucibleRecipe(key, result, catalyst, tags);
      getCraftingRecipes().add(rc);
      return rc;
   }

   public static CrucibleRecipe getCrucibleRecipe(ItemStack stack) {
      Iterator var1 = getCraftingRecipes().iterator();

      Object r;
      do {
         if(!var1.hasNext()) {
            return null;
         }

         r = var1.next();
      } while(!(r instanceof CrucibleRecipe) || !((CrucibleRecipe)r).getRecipeOutput().isItemEqual(stack));

      return (CrucibleRecipe)r;
   }

   public static CrucibleRecipe getCrucibleRecipeFromHash(int hash) {
      Iterator var1 = getCraftingRecipes().iterator();

      Object r;
      do {
         if(!var1.hasNext()) {
            return null;
         }

         r = var1.next();
      } while(!(r instanceof CrucibleRecipe) || ((CrucibleRecipe)r).hash != hash);

      return (CrucibleRecipe)r;
   }

   public static Object[] getCraftingRecipeKey(EntityPlayer player, ItemStack stack) {
      int[] key = new int[]{Item.getIdFromItem(stack.getItem()), stack.getItemDamage()};
      if(keyCache.containsKey(key)) {
         return keyCache.get(key) == null?null:(ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), (String)((Object[])keyCache.get(key))[0])?(Object[])keyCache.get(key):null);
      } else {
         Iterator var3 = ResearchCategories.researchCategories.values().iterator();

         while(var3.hasNext()) {
            ResearchCategoryList rcl = (ResearchCategoryList)var3.next();
            Iterator var5 = rcl.research.values().iterator();

            while(var5.hasNext()) {
               ResearchItem ri = (ResearchItem)var5.next();
               if(ri.getPages() != null) {
                  for(int a = 0; a < ri.getPages().length; ++a) {
                     ResearchPage page = ri.getPages()[a];
                     if(page.recipe != null && page.recipe instanceof CrucibleRecipe[]) {
                        CrucibleRecipe[] crs = (CrucibleRecipe[])((CrucibleRecipe[])page.recipe);
                        CrucibleRecipe[] var10 = crs;
                        int var11 = crs.length;

                        for(int var12 = 0; var12 < var11; ++var12) {
                           CrucibleRecipe cr = var10[var12];
                           if(cr.getRecipeOutput().isItemEqual(stack)) {
                              keyCache.put(key, new Object[]{ri.key, Integer.valueOf(a)});
                              if(ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), ri.key)) {
                                 return new Object[]{ri.key, Integer.valueOf(a)};
                              }
                           }
                        }
                     } else if(page.recipeOutput != null && stack != null && page.recipeOutput.isItemEqual(stack)) {
                        keyCache.put(key, new Object[]{ri.key, Integer.valueOf(a)});
                        if(ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), ri.key)) {
                           return new Object[]{ri.key, Integer.valueOf(a)};
                        }

                        return null;
                     }
                  }
               }
            }
         }

         keyCache.put(key, (Object)null);
         return null;
      }
   }

   public static boolean exists(Item item, int meta) {
      AspectList tmp = (AspectList)objectTags.get(Arrays.asList(new Object[]{item, Integer.valueOf(meta)}));
      if(tmp == null) {
         tmp = (AspectList)objectTags.get(Arrays.asList(new Object[]{item, Integer.valueOf(32767)}));
         if(meta == 32767 && tmp == null) {
            int index = 0;

            do {
               tmp = (AspectList)objectTags.get(Arrays.asList(new Object[]{item, Integer.valueOf(index)}));
               ++index;
            } while(index < 16 && tmp == null);
         }

         if(tmp == null) {
            return false;
         }
      }

      return true;
   }

   public static void registerObjectTag(ItemStack item, AspectList aspects) {
      if(aspects == null) {
         aspects = new AspectList();
      }

      try {
         objectTags.put(Arrays.asList(new Object[]{item.getItem(), Integer.valueOf(item.getItemDamage())}), aspects);
      } catch (Exception var3) {
         ;
      }

   }

   public static void registerObjectTag(ItemStack item, int[] meta, AspectList aspects) {
      if(aspects == null) {
         aspects = new AspectList();
      }

      try {
         objectTags.put(Arrays.asList(new Object[]{item.getItem(), Integer.valueOf(meta[0])}), aspects);
         int[] e = meta;
         int var4 = meta.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            int m = e[var5];
            groupedObjectTags.put(Arrays.asList(new Object[]{item.getItem(), Integer.valueOf(m)}), meta);
         }
      } catch (Exception var7) {
         ;
      }

   }

   public static void registerObjectTag(String oreDict, AspectList aspects) {
      if(aspects == null) {
         aspects = new AspectList();
      }

      ArrayList ores = OreDictionary.getOres(oreDict);
      if(ores != null && ores.size() > 0) {
         Iterator var3 = ores.iterator();

         while(var3.hasNext()) {
            ItemStack ore = (ItemStack)var3.next();

            try {
               objectTags.put(Arrays.asList(new Object[]{ore.getItem(), Integer.valueOf(ore.getItemDamage())}), aspects);
            } catch (Exception var6) {
               ;
            }
         }
      }

   }

   public static void registerComplexObjectTag(ItemStack item, AspectList aspects) {
      AspectList tmp;
      Aspect[] var3;
      int var4;
      int var5;
      Aspect tag;
      if(!exists(item.getItem(), item.getItemDamage())) {
         tmp = ThaumcraftApiHelper.generateTags(item.getItem(), item.getItemDamage());
         if(tmp != null && tmp.size() > 0) {
            var3 = tmp.getAspects();
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
               tag = var3[var5];
               aspects.add(tag, tmp.getAmount(tag));
            }
         }

         registerObjectTag(item, aspects);
      } else {
         tmp = ThaumcraftApiHelper.getObjectAspects(item);
         var3 = aspects.getAspects();
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            tag = var3[var5];
            tmp.merge(tag, tmp.getAmount(tag));
         }

         registerObjectTag(item, tmp);
      }

   }

   public static void addWarpToItem(ItemStack craftresult, int amount) {
      warpMap.put(Arrays.asList(new Object[]{craftresult.getItem(), Integer.valueOf(craftresult.getItemDamage())}), Integer.valueOf(amount));
   }

   public static void addWarpToResearch(String research, int amount) {
      warpMap.put(research, Integer.valueOf(amount));
   }

   public static int getWarp(Object in) {
      return in == null?0:(in instanceof ItemStack && warpMap.containsKey(Arrays.asList(new Object[]{((ItemStack)in).getItem(), Integer.valueOf(((ItemStack)in).getItemDamage())}))?((Integer)warpMap.get(Arrays.asList(new Object[]{((ItemStack)in).getItem(), Integer.valueOf(((ItemStack)in).getItemDamage())}))).intValue():(in instanceof String && warpMap.containsKey((String)in)?((Integer)warpMap.get((String)in)).intValue():0));
   }

   public static void addLootBagItem(ItemStack item, int weight, int ... bagTypes) {
      if(bagTypes != null && bagTypes.length != 0) {
         int[] var3 = bagTypes;
         int var4 = bagTypes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            int rarity = var3[var5];
            switch(rarity) {
            case 0:
               WeightedRandomLoot.lootBagCommon.add(new WeightedRandomLoot(item, weight));
               break;
            case 1:
               WeightedRandomLoot.lootBagUncommon.add(new WeightedRandomLoot(item, weight));
               break;
            case 2:
               WeightedRandomLoot.lootBagRare.add(new WeightedRandomLoot(item, weight));
            }
         }
      } else {
         WeightedRandomLoot.lootBagCommon.add(new WeightedRandomLoot(item, weight));
      }

   }


   public static class EntityTags {

      public String entityName;
      public ThaumcraftApi.EntityTagsNBT[] nbts;
      public AspectList aspects;


      public EntityTags(String entityName, AspectList aspects, ThaumcraftApi.EntityTagsNBT ... nbts) {
         this.entityName = entityName;
         this.nbts = nbts;
         this.aspects = aspects;
      }
   }

   public static class EntityTagsNBT {

      public String name;
      public Object value;


      public EntityTagsNBT(String name, Object value) {
         this.name = name;
         this.value = value;
      }
   }
}
