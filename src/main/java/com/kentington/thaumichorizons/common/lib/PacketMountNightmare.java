package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.EventHandlerEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketMountNightmare implements IMessage, IMessageHandler<PacketMountNightmare, IMessage>  {

   int id;
   int playerId;


   public PacketMountNightmare() {}

   public PacketMountNightmare(Entity ent, EntityPlayer player) {
      this.id = ent.getEntityId();
      this.playerId = player.getEntityId();
   }

   public IMessage onMessage(PacketMountNightmare message, MessageContext ctx) {
      EventHandlerEntity.clientNightmareID = message.id;
      EventHandlerEntity.clientPlayerID = message.playerId;
      return null;
   }

   public void fromBytes(ByteBuf buf) {
      this.id = buf.readInt();
      this.playerId = buf.readInt();
   }

   public void toBytes(ByteBuf buf) {
      buf.writeInt(this.id);
      buf.writeInt(this.playerId);
   }
}
