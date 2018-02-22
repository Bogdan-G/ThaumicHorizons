package thaumcraft.api.wands;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.api.wands.WandRod;

public class StaffRod extends WandRod {

   boolean runes = false;


   public StaffRod(String tag, int capacity, ItemStack item, int craftCost) {
      super(tag + "_staff", capacity, item, craftCost);
      super.texture = new ResourceLocation("thaumcraft", "textures/models/wand_rod_" + tag + ".png");
   }

   public StaffRod(String tag, int capacity, ItemStack item, int craftCost, IWandRodOnUpdate onUpdate, ResourceLocation texture) {
      super(tag + "_staff", capacity, item, craftCost, onUpdate, texture);
   }

   public StaffRod(String tag, int capacity, ItemStack item, int craftCost, IWandRodOnUpdate onUpdate) {
      super(tag + "_staff", capacity, item, craftCost, onUpdate);
      super.texture = new ResourceLocation("thaumcraft", "textures/models/wand_rod_" + tag + ".png");
   }

   public StaffRod(String tag, int capacity, ItemStack item, int craftCost, ResourceLocation texture) {
      super(tag + "_staff", capacity, item, craftCost, texture);
   }

   public boolean hasRunes() {
      return this.runes;
   }

   public void setRunes(boolean hasRunes) {
      this.runes = hasRunes;
   }
}
