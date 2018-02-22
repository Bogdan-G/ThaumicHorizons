package com.kentington.thaumichorizons.client.gui;

import com.kentington.thaumichorizons.common.container.ContainerFingers;
import com.kentington.thaumichorizons.common.container.InventoryFingers;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

@SideOnly(Side.CLIENT)
public class GuiFingers extends GuiContainer {

   private InventoryPlayer ip;
   private InventoryFingers tileEntity;
   private int[][] aspectLocs = new int[][]{{72, 21}, {24, 43}, {24, 102}, {72, 124}, {120, 102}, {120, 43}};
   ArrayList primals = Aspect.getPrimalAspects();


   public GuiFingers(InventoryPlayer par1InventoryPlayer) {
      super(new ContainerFingers(par1InventoryPlayer));
      this.tileEntity = ((ContainerFingers)super.inventorySlots).tileEntity;
      this.ip = par1InventoryPlayer;
      super.ySize = 234;
      super.xSize = 190;
   }

   protected void drawGuiContainerForegroundLayer() {}

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
      UtilsFX.bindTexture("thaumichorizons", "textures/gui/guifingers.png");
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3042);
      int var5 = (super.width - super.xSize) / 2;
      int var6 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(var5, var6, 0, 0, super.xSize, super.ySize);
      GL11.glDisable(3042);
      ItemWandCasting wand = null;
      if(this.tileEntity.getStackInSlot(10) != null && this.tileEntity.getStackInSlot(10).getItem() instanceof ItemWandCasting) {
         wand = (ItemWandCasting)this.tileEntity.getStackInSlot(10).getItem();
      }

      AspectList cost = null;
      if(ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity, this.ip.player) != null) {
         cost = ThaumcraftCraftingManager.findMatchingArcaneRecipeAspects(this.tileEntity, this.ip.player);
         int count = 0;
         Iterator var40 = this.primals.iterator();

         while(var40.hasNext()) {
            Aspect text = (Aspect)var40.next();
            float ll = (float)cost.getAmount(text);
            if(cost.getAmount(text) > 0) {
               float alpha = 0.5F + (MathHelper.sin((float)(this.ip.player.ticksExisted + count * 10) / 2.0F) * 0.2F - 0.2F);
               if(wand != null) {
                  ll *= wand.getConsumptionModifier(this.tileEntity.getStackInSlot(10), this.ip.player, text, true);
                  if(ll * 100.0F <= (float)wand.getVis(this.tileEntity.getStackInSlot(10), text)) {
                     alpha = 1.0F;
                  }
               }

               UtilsFX.drawTag(var5 + this.aspectLocs[count][0] - 8, var6 + this.aspectLocs[count][1] - 8, text, ll, 0, (double)super.zLevel, 771, alpha, false);
            }

            ++count;
            if(count > 5) {
               break;
            }
         }
      }

      if(wand != null && cost != null && !wand.consumeAllVisCrafting(this.tileEntity.getStackInSlot(10), this.ip.player, cost, false)) {
         GL11.glPushMatrix();
         float var13 = 0.33F;
         GL11.glColor4f(var13, var13, var13, 0.66F);
         GuiScreen.itemRender.renderWithColor = false;
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         GL11.glEnable(3042);
         GuiScreen.itemRender.renderItemAndEffectIntoGUI(super.mc.fontRenderer, super.mc.renderEngine, ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity, this.ip.player), var5 + 160, var6 + 64);
         GuiScreen.itemRender.renderItemOverlayIntoGUI(super.mc.fontRenderer, super.mc.renderEngine, ThaumcraftCraftingManager.findMatchingArcaneRecipe(this.tileEntity, this.ip.player), var5 + 160, var6 + 64);
         GuiScreen.itemRender.renderWithColor = true;
         GL11.glDisable(3042);
         GL11.glDisable(2896);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(var5 + 168), (float)(var6 + 46), 0.0F);
         GL11.glScalef(0.5F, 0.5F, 0.0F);
         String var14 = "Insufficient vis";
         int var15 = super.fontRendererObj.getStringWidth(var14) / 2;
         super.fontRendererObj.drawString(var14, -var15, 0, 15625838);
         GL11.glScalef(1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }

   }
}
