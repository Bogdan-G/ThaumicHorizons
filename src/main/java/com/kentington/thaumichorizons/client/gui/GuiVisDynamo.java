package com.kentington.thaumichorizons.client.gui;

import com.kentington.thaumichorizons.common.container.ContainerVisDynamo;
import com.kentington.thaumichorizons.common.tiles.TileVisDynamo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.Config;

@SideOnly(Side.CLIENT)
public class GuiVisDynamo extends GuiContainer {

   TileVisDynamo tile;
   int flashX;
   int flashY;
   Color flashColor = null;
   int flashTimer = 0;


   public GuiVisDynamo(EntityPlayer player, TileVisDynamo tileEntity) {
      super(new ContainerVisDynamo(player, tileEntity));
      this.tile = tileEntity;
      super.xSize = 111;
      super.ySize = 104;
   }

   protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
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

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
      if(this.tile.provideAer) {
         UtilsFX.drawTag(11, 12, Aspect.AIR, 0.0F, 0, (double)super.zLevel, 771, 1.0F, false);
      } else {
         UtilsFX.drawTag(11, 12, Aspect.AIR, 0.0F, 0, (double)super.zLevel, 771, 1.0F, true);
      }

      if(this.tile.provideTerra) {
         UtilsFX.drawTag(83, 11, Aspect.EARTH, 0.0F, 0, (double)super.zLevel, 771, 1.0F, false);
      } else {
         UtilsFX.drawTag(83, 11, Aspect.EARTH, 0.0F, 0, (double)super.zLevel, 771, 1.0F, true);
      }

      if(this.tile.provideIgnis) {
         UtilsFX.drawTag(11, 45, Aspect.FIRE, 0.0F, 0, (double)super.zLevel, 771, 1.0F, false);
      } else {
         UtilsFX.drawTag(11, 45, Aspect.FIRE, 0.0F, 0, (double)super.zLevel, 771, 1.0F, true);
      }

      if(this.tile.provideAqua) {
         UtilsFX.drawTag(83, 45, Aspect.WATER, 0.0F, 0, (double)super.zLevel, 771, 1.0F, false);
      } else {
         UtilsFX.drawTag(83, 45, Aspect.WATER, 0.0F, 0, (double)super.zLevel, 771, 1.0F, true);
      }

      if(this.tile.provideOrdo) {
         UtilsFX.drawTag(11, 78, Aspect.ORDER, 0.0F, 0, (double)super.zLevel, 771, 1.0F, false);
      } else {
         UtilsFX.drawTag(11, 78, Aspect.ORDER, 0.0F, 0, (double)super.zLevel, 771, 1.0F, true);
      }

      if(this.tile.providePerditio) {
         UtilsFX.drawTag(83, 78, Aspect.ENTROPY, 0.0F, 0, (double)super.zLevel, 771, 1.0F, false);
      } else {
         UtilsFX.drawTag(83, 78, Aspect.ENTROPY, 0.0F, 0, (double)super.zLevel, 771, 1.0F, true);
      }

      if(this.flashTimer > 0) {
         --this.flashTimer;
         this.drawFlash();
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      int gx = (super.width - super.xSize) / 2;
      int gy = (super.height - super.ySize) / 2;
      int x = par1 - (gx + 11);
      int y = par2 - (gy + 12);
      if(x >= 0 && y >= 0 && x <= 16 && y <= 16) {
         this.tile.provideAer = !this.tile.provideAer;
         super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 1);
         this.flashTimer = 8;
         this.flashColor = new Color(Aspect.AIR.getColor());
         this.flashX = par1 - gx - 8;
         this.flashY = par2 - gy - 8;
         super.mc.renderViewEntity.worldObj.playSound(super.mc.renderViewEntity.posX, super.mc.renderViewEntity.posY, super.mc.renderViewEntity.posZ, "thaumcraft:hhoff", 0.2F, 1.0F + super.mc.renderViewEntity.worldObj.rand.nextFloat() * 0.1F, false);
      } else {
         x = par1 - (gx + 83);
         if(x >= 0 && y >= 0 && x <= 16 && y <= 16) {
            this.tile.provideTerra = !this.tile.provideTerra;
            super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 2);
            this.flashTimer = 8;
            this.flashColor = new Color(Aspect.EARTH.getColor());
            this.flashX = par1 - gx - 8;
            this.flashY = par2 - gy - 8;
            super.mc.renderViewEntity.worldObj.playSound(super.mc.renderViewEntity.posX, super.mc.renderViewEntity.posY, super.mc.renderViewEntity.posZ, "thaumcraft:hhoff", 0.2F, 1.0F + super.mc.renderViewEntity.worldObj.rand.nextFloat() * 0.1F, false);
         } else {
            x = par1 - (gx + 11);
            y = par2 - (gy + 43);
            if(x >= 0 && y >= 0 && x <= 16 && y <= 16) {
               this.tile.provideIgnis = !this.tile.provideIgnis;
               super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 3);
               this.flashTimer = 8;
               this.flashColor = new Color(Aspect.FIRE.getColor());
               this.flashX = par1 - gx - 8;
               this.flashY = par2 - gy - 8;
               super.mc.renderViewEntity.worldObj.playSound(super.mc.renderViewEntity.posX, super.mc.renderViewEntity.posY, super.mc.renderViewEntity.posZ, "thaumcraft:hhoff", 0.2F, 1.0F + super.mc.renderViewEntity.worldObj.rand.nextFloat() * 0.1F, false);
            } else {
               x = par1 - (gx + 83);
               if(x >= 0 && y >= 0 && x <= 16 && y <= 16) {
                  this.tile.provideAqua = !this.tile.provideAqua;
                  super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 4);
                  this.flashTimer = 8;
                  this.flashColor = new Color(Aspect.WATER.getColor());
                  this.flashX = par1 - gx - 8;
                  this.flashY = par2 - gy - 8;
                  super.mc.renderViewEntity.worldObj.playSound(super.mc.renderViewEntity.posX, super.mc.renderViewEntity.posY, super.mc.renderViewEntity.posZ, "thaumcraft:hhoff", 0.2F, 1.0F + super.mc.renderViewEntity.worldObj.rand.nextFloat() * 0.1F, false);
               } else {
                  x = par1 - (gx + 11);
                  y = par2 - (gy + 78);
                  if(x >= 0 && y >= 0 && x <= 16 && y <= 16) {
                     this.tile.provideOrdo = !this.tile.provideOrdo;
                     super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 5);
                     this.flashTimer = 8;
                     this.flashColor = new Color(Aspect.ORDER.getColor());
                     this.flashX = par1 - gx - 8;
                     this.flashY = par2 - gy - 8;
                     super.mc.renderViewEntity.worldObj.playSound(super.mc.renderViewEntity.posX, super.mc.renderViewEntity.posY, super.mc.renderViewEntity.posZ, "thaumcraft:hhoff", 0.2F, 1.0F + super.mc.renderViewEntity.worldObj.rand.nextFloat() * 0.1F, false);
                  } else {
                     x = par1 - (gx + 83);
                     if(x >= 0 && y >= 0 && x <= 16 && y <= 16) {
                        this.tile.providePerditio = !this.tile.providePerditio;
                        super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 6);
                        this.flashTimer = 8;
                        this.flashColor = new Color(Aspect.ENTROPY.getColor());
                        this.flashX = par1 - gx - 8;
                        this.flashY = par2 - gy - 8;
                        super.mc.renderViewEntity.worldObj.playSound(super.mc.renderViewEntity.posX, super.mc.renderViewEntity.posY, super.mc.renderViewEntity.posZ, "thaumcraft:hhoff", 0.2F, 1.0F + super.mc.renderViewEntity.worldObj.rand.nextFloat() * 0.1F, false);
                     }
                  }
               }
            }
         }
      }
   }

   private void drawFlash() {
      float red = (float)this.flashColor.getRed() / 255.0F;
      float green = (float)this.flashColor.getGreen() / 255.0F;
      float blue = (float)this.flashColor.getBlue() / 255.0F;
      if(Config.colorBlind) {
         red /= 1.8F;
         green /= 1.8F;
         blue /= 1.8F;
      }

      GL11.glPushMatrix();
      UtilsFX.bindTexture(ParticleEngine.particleTexture);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glTranslated((double)this.flashX, (double)this.flashY, 0.0D);
      Tessellator tessellator = Tessellator.instance;
      int part = this.flashTimer;
      float var8 = 0.5F + (float)part / 8.0F;
      float var9 = var8 + 0.0624375F;
      float var10 = 0.5F;
      float var11 = var10 + 0.0624375F;
      tessellator.startDrawingQuads();
      tessellator.setBrightness(240);
      tessellator.setColorRGBA_F(red, green, blue, 1.0F);
      tessellator.addVertexWithUV(0.0D, 16.0D, (double)super.zLevel, (double)var9, (double)var11);
      tessellator.addVertexWithUV(16.0D, 16.0D, (double)super.zLevel, (double)var9, (double)var10);
      tessellator.addVertexWithUV(16.0D, 0.0D, (double)super.zLevel, (double)var8, (double)var10);
      tessellator.addVertexWithUV(0.0D, 0.0D, (double)super.zLevel, (double)var8, (double)var11);
      tessellator.draw();
      GL11.glPopMatrix();
   }
}
