package com.kentington.thaumichorizons.client.gui;

import com.kentington.thaumichorizons.common.container.ContainerCase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

@SideOnly(Side.CLIENT)
public class GuiCase extends GuiContainer {

   private int blockSlot;


   public GuiCase(InventoryPlayer par1InventoryPlayer, World world, int x, int y, int z) {
      super(new ContainerCase(par1InventoryPlayer, world, x, y, z));
      this.blockSlot = par1InventoryPlayer.currentItem;
      super.xSize = 175;
      super.ySize = 232;
   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
      UtilsFX.bindTexture("textures/gui/gui_focuspouch.png");
      float t = super.zLevel;
      super.zLevel = 200.0F;
      GL11.glEnable(3042);
      this.drawTexturedModalRect(8 + this.blockSlot * 18, 209, 240, 0, 16, 16);
      GL11.glDisable(3042);
      super.zLevel = t;
   }

   protected boolean checkHotbarKeys(int par1) {
      return false;
   }

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
      if(super.mc.thePlayer.inventory.mainInventory[this.blockSlot] == null) {
         super.mc.thePlayer.closeScreen();
      }

      UtilsFX.bindTexture("textures/gui/gui_focuspouch.png");
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int var5 = (super.width - super.xSize) / 2;
      int var6 = (super.height - super.ySize) / 2;
      GL11.glEnable(3042);
      this.drawTexturedModalRect(var5, var6, 0, 0, super.xSize, super.ySize);
      GL11.glDisable(3042);
   }
}
