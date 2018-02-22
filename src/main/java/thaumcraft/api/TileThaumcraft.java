package thaumcraft.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileThaumcraft extends TileEntity {

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      super.readFromNBT(nbttagcompound);
      this.readCustomNBT(nbttagcompound);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {}

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      super.writeToNBT(nbttagcompound);
      this.writeCustomNBT(nbttagcompound);
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {}

   public Packet getDescriptionPacket() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.writeCustomNBT(nbttagcompound);
      return new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, -999, nbttagcompound);
   }

   public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
      super.onDataPacket(net, pkt);
      this.readCustomNBT(pkt.func_148857_g());
   }
}
