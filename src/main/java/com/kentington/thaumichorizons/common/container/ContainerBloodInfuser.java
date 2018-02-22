package com.kentington.thaumichorizons.common.container;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.container.SlotRestricted;
import com.kentington.thaumichorizons.common.tiles.TileBloodInfuser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.container.SlotOutput;

public class ContainerBloodInfuser extends Container {

   TileBloodInfuser tile;
   EntityPlayer player;
   AspectList aspectsKnown;


   public ContainerBloodInfuser(EntityPlayer p, TileBloodInfuser tileEntity) {
      this.player = p;
      InventoryPlayer inv = p.inventory;
      this.tile = tileEntity;
      this.aspectsKnown = Thaumcraft.proxy.getPlayerKnowledge().getAspectsDiscovered(p.getCommandSenderName());
      this.addSlotToContainer(new SlotRestricted(this.tile, 0, 16, 37, new ItemStack(ThaumicHorizons.itemSyringeHuman)));

      int i;
      int j;
      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 3; ++j) {
            this.addSlotToContainer(new SlotOutput(this.tile, i * 3 + j + 1, 108 + i * 18, 19 + j * 18));
         }
      }

      for(i = 0; i < 3; ++i) {
         for(j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 137 + i * 18));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 195));
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
         if(p_82846_2_ < 10) {
            if(!this.mergeItemStack(itemstack1, 10, 46, true)) {
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
            if(itemstack1.getItem() != ThaumicHorizons.itemSyringeHuman || !this.mergeItemStack(itemstack1, 0, 1, false)) {
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

   public void detectAndSendChanges() {
      super.detectAndSendChanges();
   }

   @SideOnly(Side.CLIENT)
   public void updateProgressBar(int par1, int par2) {
      super.updateProgressBar(par1, par2);
   }

   public void addCraftingToCrafters(ICrafting par1ICrafting) {
      super.addCraftingToCrafters(par1ICrafting);
   }

   public boolean enchantItem(EntityPlayer par1EntityPlayer, int button) {
      if(button < 0) {
         button = -1 - button;
         this.tile.aspectsSelected.add(this.aspectsKnown.getAspectsSorted()[button], 1);
         this.tile.markDirty();
         return true;
      } else if(button > 2) {
         button -= 3;
         this.tile.aspectsSelected.remove(this.aspectsKnown.getAspectsSorted()[button], 1);
         this.tile.markDirty();
         return true;
      } else {
         switch(button) {
         case 0:
            this.tile.mode = 0;
            this.tile.markDirty();
            return true;
         case 1:
            this.tile.mode = 1;
            this.tile.markDirty();
            return true;
         case 2:
            this.tile.mode = 2;
            this.tile.markDirty();
            return true;
         default:
            return false;
         }
      }
   }
}
