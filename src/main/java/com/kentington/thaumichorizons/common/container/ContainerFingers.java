package com.kentington.thaumichorizons.common.container;

import com.kentington.thaumichorizons.common.container.InventoryFingers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import thaumcraft.common.container.ContainerDummy;
import thaumcraft.common.container.SlotCraftingArcaneWorkbench;
import thaumcraft.common.container.SlotLimitedByWand;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

public class ContainerFingers extends Container {

   private InventoryPlayer ip;
   public InventoryFingers tileEntity;


   public ContainerFingers(InventoryPlayer par1InventoryPlayer) {
      this.ip = par1InventoryPlayer;
      this.tileEntity = new InventoryFingers();
      this.tileEntity.eventHandler = this;
      this.addSlotToContainer(new SlotCraftingArcaneWorkbench(par1InventoryPlayer.player, this.tileEntity, this.tileEntity, 9, 160, 64));
      this.addSlotToContainer(new SlotLimitedByWand(this.tileEntity, 10, 160, 24));

      int var6;
      int var7;
      for(var6 = 0; var6 < 3; ++var6) {
         for(var7 = 0; var7 < 3; ++var7) {
            this.addSlotToContainer(new Slot(this.tileEntity, var7 + var6 * 3, 40 + var7 * 24, 40 + var6 * 24));
         }
      }

      for(var6 = 0; var6 < 3; ++var6) {
         for(var7 = 0; var7 < 9; ++var7) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 16 + var7 * 18, 151 + var6 * 18));
         }
      }

      for(var6 = 0; var6 < 9; ++var6) {
         this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 16 + var6 * 18, 209));
      }

      this.onCraftMatrixChanged(this.tileEntity);
   }

   public void onCraftMatrixChanged(IInventory par1IInventory) {
      InventoryCrafting ic = new InventoryCrafting(new ContainerDummy(), 3, 3);

      for(int wand = 0; wand < 9; ++wand) {
         ic.setInventorySlotContents(wand, this.tileEntity.getStackInSlot(wand));
      }

      this.tileEntity.setInventorySlotContentsSoftly(9, CraftingManager.getInstance().findMatchingRecipe(ic, this.ip.player.worldObj));
      if(this.tileEntity.getStackInSlot(9) == null && this.tileEntity.getStackInSlot(10) != null && this.tileEntity.getStackInSlot(10).getItem() instanceof ItemWandCasting) {
         ItemWandCasting var4 = (ItemWandCasting)this.tileEntity.getStackInSlot(10).getItem();
         if(var4.consumeAllVisCrafting(this.tileEntity.getStackInSlot(10), this.ip.player, ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(this.tileEntity, this.ip.player), false)) {
            this.tileEntity.setInventorySlotContentsSoftly(9, ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity, this.ip.player));
         }
      }

   }

   public void onContainerClosed(EntityPlayer par1EntityPlayer) {
      super.onContainerClosed(par1EntityPlayer);
      if(!this.ip.player.worldObj.isRemote) {
         this.tileEntity.eventHandler = null;
      }

      if(!this.ip.player.worldObj.isRemote) {
         for(int i = 0; i < 11; ++i) {
            ItemStack itemstack = this.tileEntity.getStackInSlotOnClosing(i);
            if(itemstack != null) {
               this.ip.player.dropPlayerItemWithRandomChoice(itemstack, false);
            }
         }
      }

   }

   public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
      return true;
   }

   public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
      ItemStack var2 = null;
      Slot var3 = (Slot)super.inventorySlots.get(par1);
      if(var3 != null && var3.getHasStack()) {
         ItemStack var4 = var3.getStack();
         var2 = var4.copy();
         if(par1 == 0) {
            if(!this.mergeItemStack(var4, 11, 47, true)) {
               return null;
            }

            var3.onSlotChange(var4, var2);
         } else if(par1 >= 11 && par1 < 38) {
            if(var4.getItem() instanceof ItemWandCasting && !((ItemWandCasting)var4.getItem()).isStaff(var4)) {
               if(!this.mergeItemStack(var4, 1, 2, false)) {
                  return null;
               }

               var3.onSlotChange(var4, var2);
            } else if(!this.mergeItemStack(var4, 38, 47, false)) {
               return null;
            }
         } else if(par1 >= 38 && par1 < 47) {
            if(var4.getItem() instanceof ItemWandCasting && !((ItemWandCasting)var4.getItem()).isStaff(var4)) {
               if(!this.mergeItemStack(var4, 1, 2, false)) {
                  return null;
               }

               var3.onSlotChange(var4, var2);
            } else if(!this.mergeItemStack(var4, 11, 38, false)) {
               return null;
            }
         } else if(!this.mergeItemStack(var4, 11, 47, false)) {
            return null;
         }

         if(var4.stackSize == 0) {
            var3.putStack((ItemStack)null);
         } else {
            var3.onSlotChanged();
         }

         if(var4.stackSize == var2.stackSize) {
            return null;
         }

         var3.onPickupFromSlot(this.ip.player, var4);
      }

      return var2;
   }

   public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer) {
      if(par3 == 4) {
         byte par21 = 1;
         return super.slotClick(par1, par21, par3, par4EntityPlayer);
      } else {
         if((par1 == 0 || par1 == 1) && par2 > 0) {
            par2 = 0;
         }

         return super.slotClick(par1, par2, par3, par4EntityPlayer);
      }
   }

   public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
      return par2Slot.inventory != this.tileEntity && super.func_94530_a(par1ItemStack, par2Slot);
   }
}
