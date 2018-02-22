package com.kentington.thaumichorizons.common.lib;

import baubles.api.BaublesApi;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PacketNoMoreItems implements IMessage, IMessageHandler<PacketNoMoreItems, IMessage>  {

   @SideOnly(Side.CLIENT)
   public IMessage onMessage(PacketNoMoreItems message, MessageContext ctx) {
      Minecraft.getMinecraft().thePlayer.inventory.clearInventory((Item)null, -1);
      IInventory baubles = BaublesApi.getBaubles(Minecraft.getMinecraft().thePlayer);
      baubles.setInventorySlotContents(0, (ItemStack)null);
      baubles.setInventorySlotContents(1, (ItemStack)null);
      baubles.setInventorySlotContents(2, (ItemStack)null);
      baubles.setInventorySlotContents(3, (ItemStack)null);
      return null;
   }

   public void fromBytes(ByteBuf buf) {}

   public void toBytes(ByteBuf buf) {}
}
