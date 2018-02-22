package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemKeystone extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon;


   public ItemKeystone() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
      this.setMaxStackSize(1);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:keystone");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().getInteger("dimension") != 0?"item.keystoneTH":"item.keystoneBlank";
   }

   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      if(par1ItemStack.getTagCompound() != null && par1ItemStack.getTagCompound().getInteger("dimension") != 0) {
         par3List.add(((PocketPlaneData)PocketPlaneData.planes.get(par1ItemStack.getTagCompound().getInteger("dimension"))).name);
      }

      super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
   }

   @SideOnly(Side.CLIENT)
   public int getColorFromItemStack(ItemStack par1ItemStack, int p_82790_2_) {
      return par1ItemStack.getTagCompound() != null?((PocketPlaneData)PocketPlaneData.planes.get(par1ItemStack.getTagCompound().getInteger("dimension"))).color:16777215;
   }

   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer p) {
      if(stack.getTagCompound() == null && p.dimension == ThaumicHorizons.dimensionPocketId) {
         ItemStack newStack = new ItemStack(ThaumicHorizons.itemKeystone);
         newStack.stackTagCompound = new NBTTagCompound();
         newStack.stackTagCompound.setInteger("dimension", ((int)p.posZ + 128) / 256);
         return newStack;
      } else {
         return stack;
      }
   }
}
