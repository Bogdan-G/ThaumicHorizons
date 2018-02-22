package com.kentington.thaumichorizons.client.gui;

import com.kentington.thaumichorizons.common.container.ContainerSoulExtractor;
import com.kentington.thaumichorizons.common.tiles.TileSoulExtractor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class GuiSoulExtractor extends GuiContainer {

   TileSoulExtractor tile;


   public GuiSoulExtractor(InventoryPlayer player, TileSoulExtractor tile) {
      super(new ContainerSoulExtractor(player, tile));
      this.tile = tile;
      super.xSize = 175;
      super.ySize = 165;
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", "textures/gui/guisieve.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int var5 = (super.width - super.xSize) / 2;
      int var6 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(var5, var6, 0, 0, super.xSize, super.ySize);
      if(this.tile.isExtracting() || this.tile.ticksLeft > 0) {
         int i1 = this.tile.getTimeRemainingScaled(39);
         this.drawTexturedModalRect(var5 + 91, var6 + 58 - i1, 176, 166 - i1, 35, i1);
      }

      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }
}
