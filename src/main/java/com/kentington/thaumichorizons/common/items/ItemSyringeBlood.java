package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemSyringeBlood extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon;
   @SideOnly(Side.CLIENT)
   public IIcon phial;


   public ItemSyringeBlood() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
      this.setHasSubtypes(true);
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(this, 1, 0));
      par3List.add(new ItemStack(this, 1, 1));
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:syringeBlood");
      this.phial = ir.registerIcon("thaumichorizons:phialBlood");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return par1 == 0?this.icon:this.phial;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.syringeBlood." + par1ItemStack.getItemDamage();
   }
}
