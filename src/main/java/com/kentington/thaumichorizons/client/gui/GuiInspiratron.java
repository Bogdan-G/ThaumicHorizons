package com.kentington.thaumichorizons.client.gui;

import com.kentington.thaumichorizons.common.container.ContainerInspiratron;
import com.kentington.thaumichorizons.common.tiles.TileInspiratron;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class GuiInspiratron extends GuiContainer {

   TileInspiratron tile;


   public GuiInspiratron(InventoryPlayer player, TileInspiratron tile) {
      super(new ContainerInspiratron(player, tile));
      this.tile = tile;
      super.xSize = 175;
      super.ySize = 219;
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", "textures/gui/guiinspiratron.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int var5 = (super.width - super.xSize) / 2;
      int var6 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(var5, var6, 0, 0, super.xSize, super.ySize);
      int i1 = this.tile.getTimeRemainingScaled(28);
      this.drawTexturedModalRect(var5 + 66, var6 + 102, 176, 158 - i1, 44, i1);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }
}
