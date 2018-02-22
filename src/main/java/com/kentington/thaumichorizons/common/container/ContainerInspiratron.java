package com.kentington.thaumichorizons.common.container;

import com.kentington.thaumichorizons.common.container.SlotRestricted;
import com.kentington.thaumichorizons.common.tiles.TileInspiratron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.common.config.ConfigItems;

public class ContainerInspiratron extends Container {

   TileInspiratron tile;
   int progress;


   public ContainerInspiratron(InventoryPlayer p_i1812_1_, TileInspiratron p_i1812_2_) {
      this.tile = p_i1812_2_;
      this.addSlotToContainer(new SlotRestricted(p_i1812_2_, 0, 15, 42, new ItemStack(Items.paper)));
      this.addSlotToContainer(new SlotRestricted(p_i1812_2_, 1, 146, 42, new ItemStack(ConfigItems.itemResource, 1, 9)));

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(p_i1812_1_, j + i * 9 + 9, 8 + j * 18, 137 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.addSlotToContainer(new Slot(p_i1812_1_, i, 8 + i * 18, 195));
      }

   }

   public void addCraftingToCrafters(ICrafting par1ICrafting) {
      super.addCraftingToCrafters(par1ICrafting);
      par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.progress);
   }

   public boolean canInteractWith(EntityPlayer p_75145_1_) {
      return this.tile.isUseableByPlayer(p_75145_1_);
   }

   public void detectAndSendChanges() {
      super.detectAndSendChanges();

      for(int i = 0; i < super.crafters.size(); ++i) {
         ICrafting icrafting = (ICrafting)super.crafters.get(i);
         if(this.progress != this.tile.progress) {
            icrafting.sendProgressBarUpdate(this, 0, this.tile.progress);
         }
      }

      this.progress = this.tile.progress;
   }

   @SideOnly(Side.CLIENT)
   public void updateProgressBar(int par1, int par2) {
      if(par1 == 0) {
         this.tile.progress = par2;
      }

   }

   public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)super.inventorySlots.get(p_82846_2_);
      if(slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if(p_82846_2_ == 0) {
            if(!this.mergeItemStack(itemstack1, 2, 38, true)) {
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
         } else if(p_82846_2_ == 1) {
            if(!this.mergeItemStack(itemstack1, 2, 38, true)) {
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
            if(!this.mergeItemStack(itemstack1, 0, 1, false)) {
               return null;
            }

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
}
