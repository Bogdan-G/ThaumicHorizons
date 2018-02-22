package com.kentington.thaumichorizons.common.container;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.container.InventoryInjector;
import com.kentington.thaumichorizons.common.container.SlotRestricted;
import com.kentington.thaumichorizons.common.items.ItemInjector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ContainerInjector extends Container {

   EntityPlayer player;
   ItemStack[] ammo = new ItemStack[7];
   public IInventory ammoInv = new InventoryInjector(this);
   ItemStack injector = null;
   int blockSlot;


   public ContainerInjector(EntityPlayer p) {
      this.player = p;
      this.blockSlot = this.player.inventory.currentItem + 34;
      this.injector = this.player.inventory.getCurrentItem();

      int i;
      for(i = 0; i < 7; ++i) {
         this.ammoInv.setInventorySlotContents(i, ((ItemInjector)ThaumicHorizons.itemInjector).getAmmo(this.injector, i));
      }

      this.addSlotToContainer(new SlotRestricted(this.ammoInv, 0, 73, 10, new ItemStack(ThaumicHorizons.itemSyringeInjection)));
      this.addSlotToContainer(new SlotRestricted(this.ammoInv, 1, 99, 20, new ItemStack(ThaumicHorizons.itemSyringeInjection)));
      this.addSlotToContainer(new SlotRestricted(this.ammoInv, 2, 107, 47, new ItemStack(ThaumicHorizons.itemSyringeInjection)));
      this.addSlotToContainer(new SlotRestricted(this.ammoInv, 3, 92, 70, new ItemStack(ThaumicHorizons.itemSyringeInjection)));
      this.addSlotToContainer(new SlotRestricted(this.ammoInv, 4, 64, 72, new ItemStack(ThaumicHorizons.itemSyringeInjection)));
      this.addSlotToContainer(new SlotRestricted(this.ammoInv, 5, 45, 51, new ItemStack(ThaumicHorizons.itemSyringeInjection)));
      this.addSlotToContainer(new SlotRestricted(this.ammoInv, 6, 49, 24, new ItemStack(ThaumicHorizons.itemSyringeInjection)));

      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(p.inventory, j + i * 9 + 9, 8 + j * 18, 108 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.addSlotToContainer(new Slot(p.inventory, i, 8 + i * 18, 166));
      }

      if(!this.player.worldObj.isRemote) {
         ;
      }

   }

   public boolean canInteractWith(EntityPlayer p_75145_1_) {
      return true;
   }

   public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)super.inventorySlots.get(p_82846_2_);
      if(slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if(p_82846_2_ < 7) {
            if(!this.mergeItemStack(itemstack1, 7, 43, true)) {
               return null;
            }

            slot.onSlotChange(itemstack1, itemstack);
            if(itemstack1.stackSize == 0) {
               slot.putStack((ItemStack)null);
            }

            if(itemstack1.stackSize == itemstack.stackSize) {
               return null;
            }

            slot.onPickupFromSlot(p_82846_1_, itemstack1);
         } else {
            if(itemstack1.getItem() != ThaumicHorizons.itemSyringeInjection || !this.mergeItemStack(itemstack1, 0, 7, false)) {
               return null;
            }

            slot.onSlotChange(itemstack1, itemstack);
            if(itemstack1.stackSize == 0) {
               slot.putStack((ItemStack)null);
            }

            if(itemstack1.stackSize == itemstack.stackSize) {
               return null;
            }
         }
      }

      return itemstack;
   }

   public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
      if(par1 == this.blockSlot) {
         return null;
      } else {
         InventoryPlayer inventoryplayer = par4EntityPlayer.inventory;
         return par1 == 0 && !this.ammoInv.isItemValidForSlot(par1, inventoryplayer.getItemStack()) && (par1 != 0 || inventoryplayer.getItemStack() != null)?null:super.slotClick(par1, par2, par3, par4EntityPlayer);
      }
   }

   public void onContainerClosed(EntityPlayer par1EntityPlayer) {
      if(!this.player.worldObj.isRemote) {
         NBTTagList ammo = new NBTTagList();

         for(int newTag = 0; newTag < 7; ++newTag) {
            ItemStack var3 = this.ammoInv.getStackInSlotOnClosing(newTag);
            if(var3 != null) {
               NBTTagCompound var4 = new NBTTagCompound();
               var3.writeToNBT(var4);
               ammo.appendTag(var4);
            } else {
               ammo.appendTag(new NBTTagCompound());
            }
         }

         NBTTagCompound var6 = new NBTTagCompound();
         var6.setTag("ammo", ammo);
         this.injector.setTagCompound(var6);
         if(this.player == null) {
            return;
         }

         if(this.player.getHeldItem() != null && this.player.getHeldItem().isItemEqual(this.injector)) {
            this.player.setCurrentItemOrArmor(0, this.injector);
         }

         this.player.inventory.markDirty();
      }

   }

   public void putStackInSlot(int par1, ItemStack par2ItemStack) {
      if(this.ammoInv.isItemValidForSlot(par1, par2ItemStack)) {
         super.putStackInSlot(par1, par2ItemStack);
      }

   }
}
