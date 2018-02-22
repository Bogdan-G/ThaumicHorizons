package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemWandCastingDisposable extends ItemWandCasting {

   public ItemStack wand;


   public ItemWandCastingDisposable() {
      this.maxStackSize = 1;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
      this.setCreativeTab(ThaumicHorizons.tabTH);
      ItemStack w1 = new ItemStack(this);
      this.setCap(w1, ThaumicHorizons.CAP_CRYSTAL);
      this.setRod(w1, ThaumicHorizons.ROD_CRYSTAL);
      Iterator var2 = Aspect.getPrimalAspects().iterator();

      while(var2.hasNext()) {
         Aspect aspect = (Aspect)var2.next();
         this.storeVis(w1, aspect, 25000);
      }

      this.wand = w1;
   }

   public int getMaxVis(ItemStack stack) {
      StackTraceElement[] above = Thread.currentThread().getStackTrace();
      StackTraceElement[] var3 = above;
      int var4 = above.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         StackTraceElement el = var3[var5];
         if(el.getMethodName().equals("onWornTick") || el.getMethodName().equals("updateEntity")) {
            return 0;
         }
      }

      return 25000;
   }

   public AspectList getAspectsWithRoom(ItemStack wandstack) {
      return new AspectList();
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(this.wand);
   }
}
