package com.kentington.thaumichorizons.client.gui;

import com.kentington.thaumichorizons.common.container.ContainerSoulforge;
import com.kentington.thaumichorizons.common.tiles.TileSoulforge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

@SideOnly(Side.CLIENT)
public class GuiSoulforge extends GuiContainer {

   TileSoulforge tile;


   public GuiSoulforge(EntityPlayer player, TileSoulforge tile) {
      super(new ContainerSoulforge(player, tile));
      super.xSize = 0;
      super.ySize = 0;
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", "textures/gui/guidynamo.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int var5 = (super.width - super.xSize) / 2;
      int var6 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(var5, var6, 0, 0, super.xSize, super.ySize);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
}
