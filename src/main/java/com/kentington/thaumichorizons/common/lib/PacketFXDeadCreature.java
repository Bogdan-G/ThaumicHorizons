package com.kentington.thaumichorizons.common.lib;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntitySpellParticleFX;

public class PacketFXDeadCreature implements IMessage, IMessageHandler<PacketFXDeadCreature, IMessage> {

   double x;
   double y;
   double z;


   public PacketFXDeadCreature() {}

   public PacketFXDeadCreature(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   @SideOnly(Side.CLIENT)
   public IMessage onMessage(PacketFXDeadCreature message, MessageContext ctx) {
      WorldClient world = Minecraft.getMinecraft().theWorld;

      for(int i = 0; i < 36; ++i) {
         EntitySpellParticleFX fb = new EntitySpellParticleFX(world, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
         fb.setRBGColorF(0.8F, 0.2F, 0.2F);
         fb.motionX = (double)((world.rand.nextFloat() - 0.5F) * 0.4F);
         fb.motionY = (double)((world.rand.nextFloat() - 0.5F) * 0.2F);
         fb.motionZ = (double)((world.rand.nextFloat() - 0.5F) * 0.4F);
         fb.noClip = true;
         FMLClientHandler.instance().getClient().effectRenderer.addEffect(fb);
      }

      world.playSoundEffect(this.x + 0.5D, this.y, this.z + 0.5D, "thaumcraft:gore", 2.0F, 1.0F);
      return null;
   }

   public void fromBytes(ByteBuf buf) {
      this.x = buf.readDouble();
      this.y = buf.readDouble();
      this.z = buf.readDouble();
   }

   public void toBytes(ByteBuf buf) {
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
   }
}
