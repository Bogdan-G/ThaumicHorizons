package com.kentington.thaumichorizons.common.items.lenses;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemLensCase extends Item implements IBauble {

   private IIcon icon;


   public ItemLensCase() {
      this.setMaxStackSize(1);
      this.setHasSubtypes(false);
      this.setMaxDamage(0);
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      this.icon = par1IconRegister.registerIcon("thaumichorizons:lenscase");
   }

   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public boolean getShareTag() {
      return true;
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public boolean hasEffect(ItemStack par1ItemStack) {
      return false;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      if(!par2World.isRemote) {
         par3EntityPlayer.openGui(ThaumicHorizons.instance, 8, par2World, MathHelper.floor_double(par3EntityPlayer.posX), MathHelper.floor_double(par3EntityPlayer.posY), MathHelper.floor_double(par3EntityPlayer.posZ));
      }

      return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List list, boolean par4) {
      if(item.hasTagCompound()) {
         ;
      }

   }

   public ItemStack[] getInventory(ItemStack item) {
      ItemStack[] stackList = new ItemStack[18];
      if(item.hasTagCompound()) {
         NBTTagList var2 = item.stackTagCompound.getTagList("Inventory", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            int var5 = var4.getByte("Slot") & 255;
            if(var5 >= 0 && var5 < stackList.length) {
               stackList[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
         }
      }

      return stackList;
   }

   public void setInventory(ItemStack item, ItemStack[] stackList) {
      NBTTagList var2 = new NBTTagList();

      for(int var3 = 0; var3 < stackList.length; ++var3) {
         if(stackList[var3] != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setByte("Slot", (byte)var3);
            stackList[var3].writeToNBT(var4);
            var2.appendTag(var4);
         }
      }

      item.setTagInfo("Inventory", var2);
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.lensCase";
   }

   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.BELT;
   }

   public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}

   public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

   public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

   public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
      return true;
   }

   public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
      return true;
   }
}
