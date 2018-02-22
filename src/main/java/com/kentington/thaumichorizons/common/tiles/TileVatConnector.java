package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.tiles.TileVat;
import com.kentington.thaumichorizons.common.tiles.TileVatSlave;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileVatConnector extends TileVatSlave implements IEssentiaTransport, ISidedInventory {

   public int getSizeInventory() {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.getSizeInventory():0;
   }

   public ItemStack getStackInSlot(int p_70301_1_) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.getStackInSlot(p_70301_1_):null;
   }

   public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.decrStackSize(p_70298_1_, p_70298_2_):null;
   }

   public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
      return null;
   }

   public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
      TileVat boss = this.getBoss(-1);
      if(boss != null) {
         boss.setInventorySlotContents(p_70299_1_, p_70299_2_);
      }

   }

   public String getInventoryName() {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.getInventoryName():null;
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public int getInventoryStackLimit() {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.getInventoryStackLimit():0;
   }

   public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
      return false;
   }

   public void openInventory() {}

   public void closeInventory() {}

   public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.isItemValidForSlot(p_94041_1_, p_94041_2_):false;
   }

   public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.getAccessibleSlotsFromSide(p_94128_1_):null;
   }

   public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.canInsertItem(p_102007_1_, p_102007_2_, p_102007_3_):false;
   }

   public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.canExtractItem(p_102008_1_, p_102008_2_, p_102008_3_):false;
   }

   public boolean isConnectable(ForgeDirection face) {
      return true;
   }

   public boolean canInputFrom(ForgeDirection face) {
      return true;
   }

   public boolean canOutputTo(ForgeDirection face) {
      return false;
   }

   public void setSuction(Aspect aspect, int amount) {}

   public Aspect getSuctionType(ForgeDirection face) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.getSuctionType(face):null;
   }

   public int getSuctionAmount(ForgeDirection face) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.getSuctionAmount(face):0;
   }

   public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
      return 0;
   }

   public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.addEssentia(aspect, amount, face):0;
   }

   public Aspect getEssentiaType(ForgeDirection face) {
      return null;
   }

   public int getEssentiaAmount(ForgeDirection face) {
      return 0;
   }

   public int getMinimumSuction() {
      return 0;
   }

   public boolean renderExtendedTube() {
      return false;
   }
}
