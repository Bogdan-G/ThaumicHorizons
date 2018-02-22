package com.kentington.thaumichorizons.common.container;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.container.SlotRestricted;
import com.kentington.thaumichorizons.common.container.SlotSample;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.common.lib.research.ResearchManager;

public class ContainerVat extends Container {

   TileVat tile;
   EntityPlayer player;
   SlotSample slotSample;


   public ContainerVat(EntityPlayer p, TileVat t) {
      this.tile = t;
      this.player = p;
      if(ResearchManager.isResearchComplete(this.player.getCommandSenderName(), "incarnationVat")) {
         this.slotSample = new SlotSample(this.tile, 0, 63, 32);
         this.addSlotToContainer(this.slotSample);
         this.addSlotToContainer(new SlotRestricted(this.tile, 1, 96, 32, new ItemStack(ThaumicHorizons.itemNutrients)));
      }

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(p.inventory, j + i * 9 + 9, 8 + j * 18, 127 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.addSlotToContainer(new Slot(p.inventory, i, 8 + i * 18, 185));
      }

   }

   public boolean canInteractWith(EntityPlayer p_75145_1_) {
      return this.tile.isUseableByPlayer(p_75145_1_);
   }

   public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)super.inventorySlots.get(p_82846_2_);
      if(slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if(this.slotSample != null && p_82846_2_ == 0) {
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
         } else if(this.slotSample != null && p_82846_2_ == 1) {
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
            if(this.slotSample == null) {
               return null;
            }

            if((itemstack.getItem() != ThaumicHorizons.itemNutrients || !this.mergeItemStack(itemstack1, 1, 2, false)) && (!this.slotSample.isItemValid(itemstack1) || !this.mergeItemStack(itemstack1, 0, 1, false))) {
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

   public void detectAndSendChanges() {}

   @SideOnly(Side.CLIENT)
   public void updateProgressBar(int par1, int par2) {}

   public void addCraftingToCrafters(ICrafting par1ICrafting) {}
}
