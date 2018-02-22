package com.kentington.thaumichorizons.client.gui;

import com.kentington.thaumichorizons.common.container.ContainerVat;
import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.lib.research.ResearchManager;

public class GuiVat extends GuiContainer {

   TileVat tile;
   EntityPlayer player;


   public GuiVat(EntityPlayer p, TileVat t) {
      super(new ContainerVat(p, t));
      this.tile = t;
      this.player = p;
      super.xSize = 176;
      super.ySize = 209;
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", "textures/gui/guivat.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int var5 = (super.width - super.xSize) / 2;
      int var6 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(var5, var6, 0, 0, super.xSize, super.ySize);
      if(!ResearchManager.isResearchComplete(this.player.getCommandSenderName(), "incarnationVat")) {
         this.drawTexturedModalRect(var5 + 58, var6 + 30, 176, 163, 57, 20);
      }

      float i;
      if(this.tile.getEntity() != null) {
         EntityLivingBase infusions = this.tile.getEntity();
         i = infusions.getHealth() / 2.0F;
         float i1 = infusions.getMaxHealth() / 2.0F;

         for(int i2 = 0; i2 < (int)i1; ++i2) {
            if(i >= (float)i2) {
               this.drawTexturedModalRect(var5 + 56 + 7 * i2 - 63 * (i2 / 9), var6 + 12 + 7 * (i2 / 9), 176, 126, 7, 6);
            } else {
               this.drawTexturedModalRect(var5 + 56 + 7 * i2 - 63 * (i2 / 9), var6 + 12 + 7 * (i2 / 9), 176, 120, 7, 6);
               if(i >= (float)i2 - 0.5F) {
                  this.drawTexturedModalRect(var5 + 56 + 7 * i2 - 63 * (i2 / 9), var6 + 12 + 7 * (i2 / 9), 176, 126, 4, 6);
               }
            }
         }
      } else if(this.tile.mode == 4 || this.tile.mode == 2) {
         float var11 = this.tile.selfInfusionHealth / 2.0F;
         i = 10.0F;

         for(int var13 = 0; var13 < (int)i; ++var13) {
            if(var11 >= (float)var13) {
               this.drawTexturedModalRect(var5 + 56 + 7 * var13 - 63 * (var13 / 9), var6 + 12 + 7 * (var13 / 9), 176, 126, 7, 6);
            } else {
               this.drawTexturedModalRect(var5 + 56 + 7 * var13 - 63 * (var13 / 9), var6 + 12 + 7 * (var13 / 9), 176, 120, 7, 6);
               if(var11 >= (float)var13 - 0.5F) {
                  this.drawTexturedModalRect(var5 + 56 + 7 * var13 - 63 * (var13 / 9), var6 + 12 + 7 * (var13 / 9), 176, 126, 4, 6);
               }
            }
         }
      }

      int[] var10;
      int var12;
      if(this.tile.getEntity() != null) {
         var10 = ((EntityInfusionProperties)this.tile.getEntity().getExtendedProperties("CreatureInfusion")).getInfusions();
         if(var10[0] != 0) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            for(var12 = 0; var12 < 12 && var10[var12] != 0; ++var12) {
               this.drawTexturedModalRect(var5 + 55 + 16 * (var12 % 4), var6 + 56 + 17 * (var12 / 4), (var10[var12] - 1) * 16, 209, 16, 16);
            }
         }
      }

      if(this.tile.mode == 4 || this.tile.selfInfusions[0] != 0) {
         var10 = this.tile.selfInfusions;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

         for(var12 = 0; var12 < 12 && var10[var12] != 0; ++var12) {
            this.drawTexturedModalRect(var5 + 55 + 16 * (var12 % 4), var6 + 56 + 17 * (var12 / 4), (var10[var12] - 1) * 16, 225, 16, 16);
         }
      }

      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
}
