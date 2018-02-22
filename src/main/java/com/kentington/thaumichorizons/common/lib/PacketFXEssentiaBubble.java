package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.client.fx.FXEssentiaBubble;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import thaumcraft.client.fx.ParticleEngine;

public class PacketFXEssentiaBubble implements IMessage, IMessageHandler<PacketFXEssentiaBubble, IMessage> {

   double x;
   double y;
   double z;
   int color;


   public PacketFXEssentiaBubble() {}

   public PacketFXEssentiaBubble(double x, double y, double z, int color) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.color = color;
   }

   @SideOnly(Side.CLIENT)
   public IMessage onMessage(PacketFXEssentiaBubble message, MessageContext ctx) {
      for(int i = 0; i < 10; ++i) {
         FXEssentiaBubble fb = new FXEssentiaBubble(Minecraft.getMinecraft().theWorld, message.x + (double)(1.5F * (Minecraft.getMinecraft().theWorld.rand.nextFloat() - 0.5F)), message.y, message.z + (double)(1.5F * (Minecraft.getMinecraft().theWorld.rand.nextFloat() - 0.5F)), Minecraft.getMinecraft().thePlayer.ticksExisted, message.color, 0.3F, i * 2 + 2);
         fb.noClip = true;
         ParticleEngine.instance.addEffect(Minecraft.getMinecraft().theWorld, fb);
      }

      return null;
   }

   public void fromBytes(ByteBuf buf) {
      this.x = buf.readDouble();
      this.y = buf.readDouble();
      this.z = buf.readDouble();
      this.color = buf.readInt();
   }

   public void toBytes(ByteBuf buf) {
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
      buf.writeInt(this.color);
   }
}
