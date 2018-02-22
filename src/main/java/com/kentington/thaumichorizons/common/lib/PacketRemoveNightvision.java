package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.items.lenses.LensManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class PacketRemoveNightvision implements IMessage, IMessageHandler<PacketRemoveNightvision, IMessage> {

   @SideOnly(Side.CLIENT)
   public IMessage onMessage(PacketRemoveNightvision message, MessageContext ctx) {
      Minecraft.getMinecraft().thePlayer.removePotionEffect(Potion.nightVision.id);
      Minecraft.getMinecraft();
      LensManager.nightVisionOffTime = Minecraft.getSystemTime() + 100L;
      return null;
   }

   public void fromBytes(ByteBuf buf) {}

   public void toBytes(ByteBuf buf) {}
}
