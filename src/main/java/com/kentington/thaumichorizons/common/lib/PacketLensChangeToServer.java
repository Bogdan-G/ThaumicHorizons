package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.items.lenses.LensManager;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import thaumcraft.api.nodes.IRevealer;

public class PacketLensChangeToServer implements IMessage, IMessageHandler<PacketLensChangeToServer, IMessage> {

   private int dim;
   private int playerid;
   private String lens;


   public PacketLensChangeToServer() {}

   public PacketLensChangeToServer(EntityPlayer player, String lens) {
      this.dim = player.worldObj.provider.dimensionId;
      this.playerid = player.getEntityId();
      this.lens = lens;
   }

   public void toBytes(ByteBuf buffer) {
      buffer.writeInt(this.dim);
      buffer.writeInt(this.playerid);
      ByteBufUtils.writeUTF8String(buffer, this.lens);
   }

   public void fromBytes(ByteBuf buffer) {
      this.dim = buffer.readInt();
      this.playerid = buffer.readInt();
      this.lens = ByteBufUtils.readUTF8String(buffer);
   }

   public IMessage onMessage(PacketLensChangeToServer message, MessageContext ctx) {
      WorldServer world = DimensionManager.getWorld(message.dim);
      if(world != null && (ctx.getServerHandler().playerEntity == null || ctx.getServerHandler().playerEntity.getEntityId() == message.playerid)) {
         Entity player = world.getEntityByID(message.playerid);
         if(player != null && player instanceof EntityPlayer && ((EntityPlayer)player).inventory.armorItemInSlot(3) != null && ((EntityPlayer)player).inventory.armorItemInSlot(3).getItem() instanceof IRevealer) {
            LensManager.changeLens(((EntityPlayer)player).inventory.armorItemInSlot(3), world, (EntityPlayer)player, message.lens);
         }

         return null;
      } else {
         return null;
      }
   }
}
