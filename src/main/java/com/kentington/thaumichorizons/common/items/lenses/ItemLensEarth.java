package com.kentington.thaumichorizons.common.items.lenses;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.lenses.ILens;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemLensEarth extends Item implements ILens {

   IIcon icon;


   public ItemLensEarth() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public String lensName() {
      return "LensEarth";
   }

   public void handleRender(Minecraft mc, float partialTicks) {}

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.LensEarth";
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:lensearth");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public void handleRemoval(EntityPlayer p) {}
}
