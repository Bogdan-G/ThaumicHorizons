package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.ISoulReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.TileVisNode;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TileJarBrain;

public class TileSoulExtractor extends TileVisNode implements ISidedInventory {

   public ItemStack soulsand = null;
   public int ticksLeft = 0;
   public boolean extracting = false;
   public static final int MAX_TICKS = 1200;
   public int sieveMotion = 0;


   public int getSizeInventory() {
      return 1;
   }

   public ItemStack getStackInSlot(int p_70301_1_) {
      return this.soulsand;
   }

   public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
      int oldsize = this.soulsand.stackSize;
      this.soulsand.stackSize -= p_70298_2_;
      if(this.soulsand.stackSize <= 0) {
         this.soulsand = null;
      }

      return new ItemStack(Blocks.soul_sand, Math.min(p_70298_2_, oldsize));
   }

   public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
      return null;
   }

   public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
      this.soulsand = p_70299_2_;
   }

   public String getInventoryName() {
      return "container.soulsieve";
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
      return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) != this?false:p_70300_1_.getDistanceSq((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D) <= 64.0D;
   }

   public void openInventory() {}

   public void closeInventory() {}

   public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
      return p_94041_2_.isItemEqual(new ItemStack(Blocks.soul_sand));
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("ticks", this.ticksLeft);
      nbttagcompound.setBoolean("extracting", this.extracting);
      NBTTagList nbttaglist = new NBTTagList();
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      if(this.soulsand != null) {
         this.soulsand.writeToNBT(nbttagcompound1);
      }

      nbttaglist.appendTag(nbttagcompound1);
      nbttagcompound.setTag("Items", nbttaglist);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.ticksLeft = nbttagcompound.getInteger("ticks");
      this.extracting = nbttagcompound.getBoolean("extracting");
      NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
      NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
      this.soulsand = ItemStack.loadItemStackFromNBT(nbttagcompound1);
   }

   public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
      return new int[]{0};
   }

   public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
      return this.isItemValidForSlot(0, p_102007_2_);
   }

   public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
      return false;
   }

   public void updateEntity() {
      super.updateEntity();
      this.extracting = false;
      if(this.ticksLeft <= 0) {
         if(this.soulsand == null) {
            return;
         }

         --this.soulsand.stackSize;
         if(this.soulsand.stackSize <= 0) {
            this.soulsand = null;
         }

         this.ticksLeft = 1200;
      }

      TileEntity above = super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord);
      if(above != null) {
         if(above instanceof TileJarBrain && ((TileJarBrain)above).xp < ((TileJarBrain)above).xpMax || above instanceof ISoulReceiver && ((ISoulReceiver)above).canAcceptSouls()) {
            this.extracting = true;
            if(!super.worldObj.isRemote && this.ticksLeft > 0) {
               int visBoost = VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord, super.zCoord, Aspect.AIR, 10);
               this.ticksLeft -= 1 + visBoost;
               if(above instanceof TileJarBrain) {
                  for(int below = 0; below < 1 + visBoost; ++below) {
                     if(Math.random() > 0.99D) {
                        ++((TileJarBrain)above).xp;
                        if(((TileJarBrain)above).xp >= ((TileJarBrain)above).xpMax) {
                           ((TileJarBrain)above).xp = ((TileJarBrain)above).xpMax;
                        }
                     }
                  }
               } else {
                  ((ISoulReceiver)above).addSoulBits(1 + visBoost);
               }

               if(this.ticksLeft <= 0) {
                  TileEntity var10 = super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord);
                  ItemStack sand = new ItemStack(Blocks.sand);
                  if(var10 != null && var10 instanceof ISidedInventory) {
                     int[] var12 = ((ISidedInventory)var10).getAccessibleSlotsFromSide(1);
                     int[] var13 = var12;
                     int var7 = var12.length;

                     for(int var8 = 0; var8 < var7; ++var8) {
                        int i1 = var13[var8];
                        if(((ISidedInventory)var10).canInsertItem(i1, sand, 1)) {
                           InventoryUtils.placeItemStackIntoInventory(sand, (ISidedInventory)var10, 1, true);
                           break;
                        }
                     }
                  } else if(var10 != null && var10 instanceof IInventory) {
                     int var11 = ((IInventory)var10).getSizeInventory();

                     for(int i = 0; i < var11; ++i) {
                        if(((IInventory)var10).getStackInSlot(i) == null || ((IInventory)var10).getStackInSlot(i).getItem() == sand.getItem()) {
                           InventoryUtils.placeItemStackIntoInventory(sand, (IInventory)var10, 1, true);
                           break;
                        }
                     }
                  } else {
                     EntityItem fallenSand = new EntityItem(super.worldObj, (double)super.xCoord + 0.5D, (double)super.yCoord, (double)super.zCoord + 0.5D, sand);
                     super.worldObj.spawnEntityInWorld(fallenSand);
                  }
               }

               this.markDirty();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            } else if(this.ticksLeft > 0) {
               ++this.sieveMotion;
               if(this.sieveMotion >= 360) {
                  this.sieveMotion -= 360;
                  super.worldObj.playSound((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "dig.sand", 1.0F, 0.0F, false);
                  ThaumicHorizons var10000 = ThaumicHorizons.instance;
                  ThaumicHorizons.proxy.soulParticles(super.xCoord, super.yCoord, super.zCoord, super.worldObj);
               }
            }

         }
      }
   }

   public int getRange() {
      return 8;
   }

   public boolean isSource() {
      return false;
   }

   public boolean isExtracting() {
      return this.extracting;
   }

   @SideOnly(Side.CLIENT)
   public int getTimeRemainingScaled(int p_145955_1_) {
      return this.ticksLeft * p_145955_1_ / 1200;
   }
}
