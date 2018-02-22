package com.kentington.thaumichorizons.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.TileThaumcraft;

public class TileSpike extends TileThaumcraft {

   public byte direction = 1;
   public byte spikeType = 0;


   public TileSpike(byte metadata, byte type) {
      this.direction = metadata;
      this.spikeType = type;
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setByte("dir", this.direction);
      nbttagcompound.setByte("type", this.spikeType);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.direction = nbttagcompound.getByte("dir");
      this.spikeType = nbttagcompound.getByte("type");
   }
}
