package com.kentington.thaumichorizons.common.items.lenses;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public interface ILens {

   String lensName();

   @SideOnly(Side.CLIENT)
   void handleRender(Minecraft var1, float var2);

   void handleRemoval(EntityPlayer var1);
}
