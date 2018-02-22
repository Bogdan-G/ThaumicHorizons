package com.kentington.thaumichorizons.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileMagicWorkbench;

public class InventoryFingers extends TileMagicWorkbench implements IInventory {

   public int getSizeInventory() {
      return this.stackList.length;
   }

   public ItemStack getStackInSlot(int par1) {
      return par1 >= this.getSizeInventory()?null:this.stackList[par1];
   }

   public ItemStack getStackInRowAndColumn(int par1, int par2) {
      if(par1 >= 0 && par1 < 3) {
         int var3 = par1 + par2 * 3;
         return this.getStackInSlot(var3);
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int par1) {
      if(this.stackList[par1] != null) {
         ItemStack var2 = this.stackList[par1];
         this.stackList[par1] = null;
         this.markDirty();
         return var2;
      } else {
         return null;
      }
   }

   public ItemStack decrStackSize(int par1, int par2) {
      if(this.stackList[par1] != null) {
         ItemStack var3;
         if(this.stackList[par1].stackSize <= par2) {
            var3 = this.stackList[par1];
            this.stackList[par1] = null;
            if(this.eventHandler != null) {
               this.eventHandler.onCraftMatrixChanged(this);
            }

            this.markDirty();
            return var3;
         } else {
            var3 = this.stackList[par1].splitStack(par2);
            if(this.stackList[par1].stackSize == 0) {
               this.stackList[par1] = null;
            }

            if(this.eventHandler != null) {
               this.eventHandler.onCraftMatrixChanged(this);
            }

            this.markDirty();
            return var3;
         }
      } else {
         return null;
      }
   }

   public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
      this.stackList[par1] = par2ItemStack;
      this.markDirty();
      if(this.eventHandler != null) {
         this.eventHandler.onCraftMatrixChanged(this);
      }

   }

   public void setInventorySlotContentsSoftly(int par1, ItemStack par2ItemStack) {
      this.stackList[par1] = par2ItemStack;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public String getInventoryName() {
      return null;
   }

   public void openInventory() {}

   public void closeInventory() {}

   public boolean hasCustomInventoryName() {
      return false;
   }

   public boolean isItemValidForSlot(int i, ItemStack itemstack) {
      if(i == 10 && itemstack != null) {
         if(!(itemstack.getItem() instanceof ItemWandCasting)) {
            return false;
         } else {
            ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
            return !wand.isStaff(itemstack);
         }
      } else {
         return true;
      }
   }

   public int[] func_94128_d(int var1) {
      return new int[]{10};
   }

   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
      if(i == 10 && itemstack != null && itemstack.getItem() instanceof ItemWandCasting) {
         ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
         return !wand.isStaff(itemstack);
      } else {
         return false;
      }
   }

   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
      return i == 10;
   }

   public void markDirty() {}

   public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
      return true;
   }
}
