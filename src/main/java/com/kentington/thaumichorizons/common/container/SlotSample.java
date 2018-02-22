package com.kentington.thaumichorizons.common.container;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSample extends Slot {

   public SlotSample(TileVat p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
      super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
   }

   public boolean isItemValid(ItemStack what) {
      return what.getItem() == ThaumicHorizons.itemSyringeBloodSample || what.getItem() == ThaumicHorizons.itemCorpseEffigy || ThaumicHorizons.incarnationItems.containsKey(what.getItem());
   }
}
