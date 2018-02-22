package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.tiles.TileVat;
import com.kentington.thaumichorizons.common.tiles.TileVatSlave;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.tiles.TilePedestal;

public class PacketInfusionFX implements IMessage, IMessageHandler<PacketInfusionFX, IMessage> {

   private int x;
   private int y;
   private int z;
   private byte dx;
   private byte dy;
   private byte dz;
   private int color;


   public PacketInfusionFX() {}

   public PacketInfusionFX(int x, int y, int z, byte dx, byte dy, byte dz, int color) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.dx = dx;
      this.dy = dy;
      this.dz = dz;
      this.color = color;
   }

   public void toBytes(ByteBuf buffer) {
      buffer.writeInt(this.x);
      buffer.writeInt(this.y);
      buffer.writeInt(this.z);
      buffer.writeInt(this.color);
      buffer.writeByte(this.dx);
      buffer.writeByte(this.dy);
      buffer.writeByte(this.dz);
   }

   public void fromBytes(ByteBuf buffer) {
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
      this.color = buffer.readInt();
      this.dx = buffer.readByte();
      this.dy = buffer.readByte();
      this.dz = buffer.readByte();
   }

   @SideOnly(Side.CLIENT)
   public IMessage onMessage(PacketInfusionFX message, MessageContext ctx) {
      int tx = message.x - message.dx;
      int ty = message.y - message.dy;
      int tz = message.z - message.dz;
      String key = tx + ":" + ty + ":" + tz + ":" + message.color;
      TileEntity tile = Thaumcraft.proxy.getClientWorld().getTileEntity(message.x, message.y, message.z);
      if(tile != null && tile instanceof TileVatSlave) {
         TileVat is;
         int count = 15;
         if(Thaumcraft.proxy.getClientWorld().getTileEntity(tx, ty, tz) != null && Thaumcraft.proxy.getClientWorld().getTileEntity(tx, ty, tz) instanceof TilePedestal) {
            count = 60;
         }

         if ((is = ((TileVatSlave)tile).getBoss(-1)) == null) {
            return null;
         }

         if(is.sourceFX.containsKey(key)) {
            TileVat.SourceFX tmp232_230 = (TileVat.SourceFX)is.sourceFX.get(key);
            tmp232_230.ticks = count;
            is.sourceFX.put(key, tmp232_230);
         } else {
            TileVat tmp232_230 = is;
            tmp232_230.getClass();
            HashMap<String, TileVat.SourceFX> sourceFX = is.sourceFX;
            String s = key;
            TileVat this$0 = is;
            this$0.getClass();
            sourceFX.put(s, this$0.new SourceFX(new ChunkCoordinates(tx, ty, tz), count, message.color));
         }
      }

      return null;
   }
}
