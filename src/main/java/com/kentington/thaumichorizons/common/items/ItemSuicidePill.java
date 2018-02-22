package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSuicidePill extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon;


   public ItemSuicidePill() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:syringeEmpty");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.suicidePill";
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World world, EntityPlayer p) {
      p.attackEntityFrom((new DamageSource("suicide")).setDamageAllowedInCreativeMode(), 1000.0F);
      return p_77659_1_;
   }

   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      if(par1ItemStack.getItemDamage() == 0) {
         par3List.add("For testing purposes only");
         par3List.add("FDA approval pending");
      }

      super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
   }
}
