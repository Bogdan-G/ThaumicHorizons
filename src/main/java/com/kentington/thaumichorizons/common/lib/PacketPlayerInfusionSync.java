package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class PacketPlayerInfusionSync implements IMessage, IMessageHandler<PacketPlayerInfusionSync, IMessage> {

   int[] infusions = new int[10];
   String name = "";


   public PacketPlayerInfusionSync() {}

   public PacketPlayerInfusionSync(String name, int[] infusions) {
      this.name = name;
      this.infusions = infusions;
   }

   public IMessage onMessage(PacketPlayerInfusionSync message, MessageContext ctx) {
      if(Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().theWorld.getPlayerEntityByName(message.name) != null && Minecraft.getMinecraft().theWorld.getPlayerEntityByName(message.name).getExtendedProperties("CreatureInfusion") != null) {
         ((EntityInfusionProperties)Minecraft.getMinecraft().theWorld.getPlayerEntityByName(message.name).getExtendedProperties("CreatureInfusion")).playerInfusions = message.infusions;
      }

      return null;
   }

   public void fromBytes(ByteBuf buf) {
      int length = buf.readInt();
      byte[] bites = new byte[length];
      char[] chars = new char[length];
      buf.readBytes(bites);

      int i;
      for(i = 0; i < length; ++i) {
         chars[i] = (char)bites[i];
      }

      this.name = String.copyValueOf(chars);

      for(i = 0; i < 10; ++i) {
         this.infusions[i] = buf.readInt();
      }

   }

   public void toBytes(ByteBuf buf) {
      buf.writeInt(this.name.length());
      byte[] bites = new byte[this.name.length()];
      char[] chars = this.name.toCharArray();

      int i;
      for(i = 0; i < this.name.length(); ++i) {
         bites[i] = (byte)chars[i];
      }

      buf.writeBytes(bites);

      for(i = 0; i < 10; ++i) {
         buf.writeInt(this.infusions[i]);
      }

   }
}
