package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.tiles.ISoulReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.common.config.ConfigItems;

public class TileInspiratron extends TileThaumcraft implements ISoulReceiver, ISidedInventory {

   public ItemStack paper;
   public ItemStack knowledge;
   public int progress;
   private final int PROGRESS_MAX = 100;
   public float rota;
   public float rotb;
   public float field_40063_b;
   public float field_40061_d;
   public float field_40059_f;
   public float field_40066_q;


   public void updateEntity() {
      super.updateEntity();
      if(super.worldObj.isRemote) {
         EntityPlayer entity = null;
         this.rotb = this.rota;
         if(entity == null) {
            entity = super.worldObj.getClosestPlayer((double)((float)super.xCoord + 0.5F), (double)((float)super.yCoord + 0.5F), (double)((float)super.zCoord + 0.5F), 6.0D);
         }

         if(entity != null) {
            double f = entity.posX - (double)((float)super.xCoord + 0.5F);
            double d1 = entity.posZ - (double)((float)super.zCoord + 0.5F);
            this.field_40066_q = (float)Math.atan2(d1, f);
            this.field_40059_f += 0.1F;
            if(this.field_40059_f < 0.5F || entity.worldObj.rand.nextInt(40) == 0) {
               float f3 = this.field_40061_d;

               do {
                  this.field_40061_d += (float)(entity.worldObj.rand.nextInt(4) - entity.worldObj.rand.nextInt(4));
               } while(f3 == this.field_40061_d);
            }
         } else {
            this.field_40066_q += 0.01F;
         }

         while(this.rota >= 3.141593F) {
            this.rota -= 6.283185F;
         }

         while(this.rota < -3.141593F) {
            this.rota += 6.283185F;
         }

         while(this.field_40066_q >= 3.141593F) {
            this.field_40066_q -= 6.283185F;
         }

         while(this.field_40066_q < -3.141593F) {
            this.field_40066_q += 6.283185F;
         }

         float f1;
         for(f1 = this.field_40066_q - this.rota; f1 < -3.141593F; f1 += 6.283185F) {
            ;
         }

         this.rota += f1 * 0.04F;
      }

   }

   public void addSoulBits(int bits) {
      for(int i = 0; i < bits; ++i) {
         if(Math.random() >= 0.97D) {
            super.worldObj.playSoundEffect((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "thaumcraft:write", 0.2F, super.worldObj.rand.nextFloat());
            ++this.progress;
         }
      }

      if(this.progress >= 100) {
         this.progress -= 100;
         if(this.knowledge == null) {
            this.knowledge = new ItemStack(ConfigItems.itemResource, 1, 9);
         } else {
            ++this.knowledge.stackSize;
         }

         --this.paper.stackSize;
         if(this.paper.stackSize <= 0) {
            this.paper = null;
         }
      }

   }

   public boolean canAcceptSouls() {
      return this.paper != null && this.paper.stackSize > 0 && (this.knowledge == null || this.knowledge.stackSize < 64);
   }

   public int getSizeInventory() {
      return 2;
   }

   public ItemStack getStackInSlot(int p_70301_1_) {
      return p_70301_1_ == 0?this.paper:(p_70301_1_ == 1?this.knowledge:null);
   }

   public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
      int oldsize;
      if(p_70298_1_ == 0) {
         oldsize = this.paper.stackSize;
         this.paper.stackSize -= p_70298_2_;
         if(this.paper.stackSize <= 0) {
            this.paper = null;
         }

         return new ItemStack(Items.paper, Math.min(p_70298_2_, oldsize));
      } else if(p_70298_1_ == 1) {
         oldsize = this.knowledge.stackSize;
         this.knowledge.stackSize -= p_70298_2_;
         if(this.knowledge.stackSize <= 0) {
            this.knowledge = null;
         }

         return new ItemStack(ConfigItems.itemResource, Math.min(p_70298_2_, oldsize), 9);
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
      return null;
   }

   public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
      if(p_70299_1_ == 0) {
         this.paper = p_70299_2_;
      } else if(p_70299_1_ == 1) {
         this.knowledge = p_70299_2_;
      }

   }

   public String getInventoryName() {
      return "container.inspiratron";
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
      return p_94041_1_ == 0?p_94041_2_.isItemEqual(new ItemStack(Items.paper)):(p_94041_1_ == 1?p_94041_2_.isItemEqual(new ItemStack(ConfigItems.itemResource, 1, 9)):false);
   }

   public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
      return new int[]{0, 1};
   }

   public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
      return p_102007_1_ == 1?false:this.isItemValidForSlot(p_102007_1_, p_102007_2_);
   }

   public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
      return p_102008_1_ == 1 && this.knowledge != null;
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("progress", this.progress);
      NBTTagList nbttaglist = new NBTTagList();
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      if(this.paper != null) {
         this.paper.writeToNBT(nbttagcompound1);
      }

      nbttaglist.appendTag(nbttagcompound1);
      NBTTagCompound nbttagcompound2 = new NBTTagCompound();
      if(this.knowledge != null) {
         this.knowledge.writeToNBT(nbttagcompound2);
      }

      nbttaglist.appendTag(nbttagcompound2);
      nbttagcompound.setTag("Items", nbttaglist);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.progress = nbttagcompound.getInteger("progress");
      NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
      NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
      this.paper = ItemStack.loadItemStackFromNBT(nbttagcompound1);
      nbttagcompound1 = nbttaglist.getCompoundTagAt(1);
      this.knowledge = ItemStack.loadItemStackFromNBT(nbttagcompound1);
   }

   @SideOnly(Side.CLIENT)
   public int getTimeRemainingScaled(int p_145955_1_) {
      int var10000 = this.progress * p_145955_1_;
      this.getClass();
      return var10000 / 100;
   }
}
