package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.blocks.BlockNodeMonitor;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.INode;

public class TileNodeMonitor extends TileThaumcraft {

   public byte direction = -1;
   public boolean activated = false;
   boolean lastActivated = false;
   public int rotation = 0;
   public boolean switchy = false;


   public TileNodeMonitor() {
      this.direction = (byte)super.blockMetadata;
   }

   public void updateEntity() {
      if(!this.activated) {
         this.switchy = false;
         ++this.rotation;
         if(this.rotation > 360) {
            this.rotation -= 360;
         }
      } else if(super.worldObj.isRemote && Minecraft.getMinecraft().thePlayer.ticksExisted % 15 == 0) {
         this.switchy = !this.switchy;
      }

      if(this.direction == -1) {
         this.direction = (byte)this.getBlockMetadata();
      }

      ForgeDirection dir = ForgeDirection.getOrientation(this.direction);
      if(dir == ForgeDirection.UP && super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord) instanceof INode) {
         this.activated = this.aspectCritical(super.xCoord, super.yCoord - 1, super.zCoord);
      } else if(dir == ForgeDirection.DOWN && super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof INode) {
         this.activated = this.aspectCritical(super.xCoord, super.yCoord + 1, super.zCoord);
      } else if(dir == ForgeDirection.NORTH && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord + 1) instanceof INode) {
         this.activated = this.aspectCritical(super.xCoord, super.yCoord, super.zCoord + 1);
      } else if(dir == ForgeDirection.SOUTH && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord - 1) instanceof INode) {
         this.activated = this.aspectCritical(super.xCoord, super.yCoord, super.zCoord - 1);
      } else if(dir == ForgeDirection.WEST && super.worldObj.getTileEntity(super.xCoord + 1, super.yCoord, super.zCoord) instanceof INode) {
         this.activated = this.aspectCritical(super.xCoord + 1, super.yCoord, super.zCoord);
      } else if(dir == ForgeDirection.EAST && super.worldObj.getTileEntity(super.xCoord - 1, super.yCoord, super.zCoord) instanceof INode) {
         this.activated = this.aspectCritical(super.xCoord - 1, super.yCoord, super.zCoord);
      } else {
         ((BlockNodeMonitor)ThaumicHorizons.blockNodeMonitor).killMe(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
      }

      if(this.activated != this.lastActivated) {
         super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord, super.zCoord, ThaumicHorizons.blockNodeMonitor);
      }

      this.lastActivated = this.activated;
   }

   private boolean aspectCritical(int x, int y, int z) {
      TileEntity node = super.worldObj.getTileEntity(x, y, z);
      if(node instanceof INode) {
         Aspect[] var5 = ((INode)node).getAspects().getAspects();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Aspect asp = var5[var7];
            if(((INode)node).getAspects().getAmount(asp) <= 1) {
               return true;
            }
         }
      }

      return false;
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setBoolean("active", this.activated);
      nbttagcompound.setBoolean("lastactive", this.lastActivated);
      nbttagcompound.setByte("dir", this.direction);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.activated = nbttagcompound.getBoolean("active");
      this.lastActivated = nbttagcompound.getBoolean("lastactive");
      this.direction = nbttagcompound.getByte("dir");
   }
}
