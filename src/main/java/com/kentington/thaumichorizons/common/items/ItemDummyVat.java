package com.kentington.thaumichorizons.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemDummyVat extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon;


   public ItemDummyVat() {
      this.setCreativeTab((CreativeTabs)null);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:vat");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.vat";
   }
}
