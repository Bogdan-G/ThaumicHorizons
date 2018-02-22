package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class PacketToggleInvisibleToServer implements IMessage, IMessageHandler<PacketToggleInvisibleToServer, IMessage> {

   private int playerid;
   private int dim;


   public PacketToggleInvisibleToServer() {}

   public PacketToggleInvisibleToServer(EntityPlayer player, int dim) {
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

   public IMessage onMessage(PacketToggleInvisibleToServer message, MessageContext ctx) {
      WorldServer world = DimensionManager.getWorld(message.dim);
      EntityPlayer player = (EntityPlayer)world.getEntityByID(message.playerid);
      ((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleClimb = !((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleClimb;
      if(((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleClimb) {
         player.removePotionEffect(Potion.invisibility.id);
         player.setInvisible(false);
      } else {
         PotionEffect effect = new PotionEffect(Potion.invisibility.id, Integer.MAX_VALUE, 0, true);
         effect.setCurativeItems(new ArrayList());
         player.addPotionEffect(effect);
         player.setInvisible(true);
      }

      return null;
   }
}
