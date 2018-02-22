package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class PacketToggleClimbToServer implements IMessage, IMessageHandler<PacketToggleClimbToServer, IMessage> {

   private int playerid;
   private int dim;


   public PacketToggleClimbToServer() {}

   public PacketToggleClimbToServer(EntityPlayer player, int dim) {
      this.playerid = player.getEntityId();
      this.dim = dim;
   }

   public void toBytes(ByteBuf buffer) {
      buffer.writeInt(this.playerid);
      buffer.writeInt(this.dim);
   }

   public void fromBytes(ByteBuf buffer) {
      this.playerid = buffer.readInt();
      this.dim = buffer.readInt();
   }

   public IMessage onMessage(PacketToggleClimbToServer message, MessageContext ctx) {
      WorldServer world = DimensionManager.getWorld(message.dim);
      EntityPlayer player = (EntityPlayer)world.getEntityByID(message.playerid);
      ((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleClimb = !((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleClimb;
      return null;
   }
}
