package thaumcraft.api.research;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;

public class ResearchPage {

   public ResearchPage.PageType type;
   public String text;
   public String research;
   public ResourceLocation image;
   public AspectList aspects;
   public Object recipe;
   public ItemStack recipeOutput;


   public ResearchPage(String text) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.TEXT;
      this.text = text;
   }

   public ResearchPage(String research, String text) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.TEXT_CONCEALED;
      this.research = research;
      this.text = text;
   }

   public ResearchPage(IRecipe recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.NORMAL_CRAFTING;
      this.recipe = recipe;
      this.recipeOutput = recipe.getRecipeOutput();
   }

   public ResearchPage(IRecipe[] recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.NORMAL_CRAFTING;
      this.recipe = recipe;
   }

   public ResearchPage(IArcaneRecipe[] recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.ARCANE_CRAFTING;
      this.recipe = recipe;
   }

   public ResearchPage(CrucibleRecipe[] recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.CRUCIBLE_CRAFTING;
      this.recipe = recipe;
   }

   public ResearchPage(InfusionRecipe[] recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.INFUSION_CRAFTING;
      this.recipe = recipe;
   }

   public ResearchPage(List recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.COMPOUND_CRAFTING;
      this.recipe = recipe;
   }

   public ResearchPage(IArcaneRecipe recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.ARCANE_CRAFTING;
      this.recipe = recipe;
      this.recipeOutput = recipe.getRecipeOutput();
   }

   public ResearchPage(CrucibleRecipe recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.CRUCIBLE_CRAFTING;
      this.recipe = recipe;
      this.recipeOutput = recipe.getRecipeOutput();
   }

   public ResearchPage(ItemStack input) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.SMELTING;
      this.recipe = input;
      this.recipeOutput = FurnaceRecipes.smelting().getSmeltingResult(input);
   }

   public ResearchPage(InfusionRecipe recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.INFUSION_CRAFTING;
      this.recipe = recipe;
      if(recipe.getRecipeOutput() instanceof ItemStack) {
         this.recipeOutput = (ItemStack)recipe.getRecipeOutput();
      } else {
         this.recipeOutput = recipe.getRecipeInput();
      }

   }

   public ResearchPage(InfusionEnchantmentRecipe recipe) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.INFUSION_ENCHANTMENT;
      this.recipe = recipe;
   }

   public ResearchPage(ResourceLocation image, String caption) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.IMAGE;
      this.image = image;
      this.text = caption;
   }

   public ResearchPage(AspectList as) {
      this.type = ResearchPage.PageType.TEXT;
      this.text = null;
      this.research = null;
      this.image = null;
      this.aspects = null;
      this.recipe = null;
      this.recipeOutput = null;
      this.type = ResearchPage.PageType.ASPECTS;
      this.aspects = as;
   }

   public String getTranslatedText() {
      String ret = "";
      if(this.text != null) {
         ret = StatCollector.translateToLocal(this.text);
         if(ret.isEmpty()) {
            ret = this.text;
         }
      }

      return ret;
   }

   public static enum PageType {

      TEXT("TEXT", 0),
      TEXT_CONCEALED("TEXT_CONCEALED", 1),
      IMAGE("IMAGE", 2),
      CRUCIBLE_CRAFTING("CRUCIBLE_CRAFTING", 3),
      ARCANE_CRAFTING("ARCANE_CRAFTING", 4),
      ASPECTS("ASPECTS", 5),
      NORMAL_CRAFTING("NORMAL_CRAFTING", 6),
      INFUSION_CRAFTING("INFUSION_CRAFTING", 7),
      COMPOUND_CRAFTING("COMPOUND_CRAFTING", 8),
      INFUSION_ENCHANTMENT("INFUSION_ENCHANTMENT", 9),
      SMELTING("SMELTING", 10);
      // $FF: synthetic field
      private static final ResearchPage.PageType[] $VALUES = new ResearchPage.PageType[]{TEXT, TEXT_CONCEALED, IMAGE, CRUCIBLE_CRAFTING, ARCANE_CRAFTING, ASPECTS, NORMAL_CRAFTING, INFUSION_CRAFTING, COMPOUND_CRAFTING, INFUSION_ENCHANTMENT, SMELTING};


      private PageType(String var1, int var2) {}

   }
}
