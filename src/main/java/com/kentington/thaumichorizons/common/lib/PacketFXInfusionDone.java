package com.kentington.thaumichorizons.common.lib;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import thaumcraft.common.Thaumcraft;

public class PacketFXInfusionDone implements IMessage, IMessageHandler<PacketFXInfusionDone, IMessage> {

   int x;
   int y;
   int z;


   public PacketFXInfusionDone() {}

   public PacketFXInfusionDone(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   @SideOnly(Side.CLIENT)
   public IMessage onMessage(PacketFXInfusionDone message, MessageContext ctx) {
      Thaumcraft.proxy.blockSparkle(Minecraft.getMinecraft().theWorld, message.x, message.y, message.z, -9999, 20);
      int var10003 = message.y - 1;
      Thaumcraft.proxy.blockSparkle(Minecraft.getMinecraft().theWorld, message.x, var10003, message.z, -9999, 20);
      return null;
   }

   public void fromBytes(ByteBuf buf) {
      this.x = buf.readInt();
      this.y = buf.readInt();
      this.z = buf.readInt();
   }

   public void toBytes(ByteBuf buf) {
      buf.writeInt(this.x);
      buf.writeInt(this.y);
      buf.writeInt(this.z);
   }
}
