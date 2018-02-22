package com.kentington.thaumichorizons.client.gui;

import com.kentington.thaumichorizons.common.container.ContainerBloodInfuser;
import com.kentington.thaumichorizons.common.tiles.TileBloodInfuser;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;

public class GuiBloodInfuser extends GuiContainer {

   TileBloodInfuser tile;
   AspectList aspectsKnown;
   Aspect[] aspectsSelected = new Aspect[8];
   int numSelected = 0;
   int offset = 0;
   boolean scrollLClicked;
   boolean scrollRClicked;
   int flashX;
   int flashY;
   Color flashColor = null;
   int flashTimer = 0;
   int topOut = 2;
   int bottomOut = 2;
   Aspect mousedOver = null;
   HashMap mousedEffects = null;
   NBTTagList cachedEffects = null;


   public GuiBloodInfuser(EntityPlayer p, TileBloodInfuser tile) {
      super(new ContainerBloodInfuser(p, tile));
      Aspect[] var3 = tile.aspectsSelected.getAspects();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Aspect asp = var3[var5];
         if(asp != null) {
            for(int i = 0; i < tile.aspectsSelected.getAmount(asp); ++i) {
               this.aspectsSelected[this.numSelected] = asp;
               ++this.numSelected;
            }
         }
      }

      this.aspectsKnown = Thaumcraft.proxy.getPlayerKnowledge().getAspectsDiscovered(p.getCommandSenderName());
      this.tile = tile;
      super.xSize = 176;
      super.ySize = 219;
      this.cachedEffects = tile.getCurrentEffects();
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", "textures/gui/guibloodinfuser.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int var5 = (super.width - super.xSize) / 2;
      int var6 = (super.height - super.ySize) / 2;
      this.drawTexturedModalRect(var5, var6, 0, 0, super.xSize, super.ySize);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   protected void drawGuiContainerForegroundLayer(int par1, int par2) {
      this.drawEssentiaSelected();
      this.drawAspectList();
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", "textures/gui/guibloodinfuser.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if(this.canScrollLeft()) {
         this.drawTexturedModalRect(111, 75, 177, 136, 24, 8);
      } else {
         this.drawTexturedModalRect(111, 75, 177, 144, 24, 8);
      }

      if(this.canScrollRight()) {
         this.drawTexturedModalRect(135, 75, 201, 136, 24, 8);
      } else {
         this.drawTexturedModalRect(135, 75, 201, 144, 24, 8);
      }

      if(this.tile.mode == 1) {
         this.drawTexturedModalRect(38, 57, 178, 130, 6, 6);
      } else if(this.tile.mode == 2) {
         this.drawTexturedModalRect(38, 70, 178, 130, 6, 6);
      }

      int gx = (super.width - super.xSize) / 2;
      int gy = (super.height - super.ySize) / 2;
      int x = par1 - (gx + 15);
      int y = par2 - (gy + 83);
      int j;
      int index;
      if(x >= 0 && y >= 0 && x <= 144 && y <= 34) {
         int i = x / 18;
         j = y / 18 - 1;
         index = i * 2 - j;
         Aspect id = null;
         if(index + this.offset < this.aspectsKnown.size()) {
            id = this.aspectsKnown.getAspectsSorted()[index + this.offset];
         }

         if(id != null && (this.mousedOver == null || !id.getTag().equals(this.mousedOver.getTag()))) {
            this.mousedOver = id;
            this.mousedEffects = this.tile.getEffects(this.mousedOver);
         }
      } else {
         x = par1 - (gx + 110);
         y = par2 - (gy + 74);
         if(x >= 0 && y >= 0 && x <= 49 && y <= 9) {
            this.mousedOver = null;
         }
      }

      Potion potion;
      int l;
      byte var14;
      if(this.mousedOver == null) {
         if(this.bottomOut > 2) {
            this.bottomOut -= 2;
         }

         this.drawTexturedModalRect(171, 69, 215 - this.bottomOut, 194, this.bottomOut, 59);
      } else {
         if(this.bottomOut < 38) {
            this.bottomOut += 2;
         }

         this.drawTexturedModalRect(171, 69, 215 - this.bottomOut, 194, this.bottomOut, 59);
         var14 = 0;
         j = 0;
         if(this.bottomOut == 38 && this.mousedEffects != null) {
            Iterator var15 = this.mousedEffects.keySet().iterator();

            while(var15.hasNext()) {
               Integer var16 = (Integer)var15.next();
               potion = Potion.potionTypes[var16.intValue()];
               if(potion.hasStatusIcon()) {
                  super.mc.getTextureManager().bindTexture(GuiContainer.field_147001_a);
                  l = potion.getStatusIconIndex();
                  this.drawTexturedModalRect(var14 * 18 + 171, j * 18 + 72, 0 + l % 8 * 18, 198 + l / 8 * 18, 18, 18);
                  super.fontRendererObj.drawString("" + this.mousedEffects.get(var16), var14 * 18 + 171, j * 18 + 72, Color.GRAY.getRGB());
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  if(var14 == 0) {
                     var14 = 1;
                  } else {
                     var14 = 0;
                     ++j;
                  }
               } else {
                  super.mc.getTextureManager().bindTexture(new ResourceLocation("thaumichorizons", "textures/misc/potions.png"));
                  byte var18 = 0;
                  short why = 216;
                  if(potion.getId() == 23) {
                     var18 = 36;
                  } else if(potion.getId() == Potion.heal.id) {
                     var18 = 0;
                  } else if(potion.getId() == Potion.harm.id) {
                     var18 = 18;
                  }

                  this.drawTexturedModalRect(var14 * 18 + 171, j * 18 + 72, var18, why, 18, 18);
                  super.fontRendererObj.drawString("" + this.mousedEffects.get(var16), var14 * 18 + 171, j * 18 + 72, Color.GRAY.getRGB());
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  if(var14 == 0) {
                     var14 = 1;
                  } else {
                     var14 = 0;
                     ++j;
                  }
               }
            }
         }
      }

      if(this.cachedEffects != null && this.cachedEffects.tagCount() > 0) {
         UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", "textures/gui/guibloodinfuser.png"));
         if(this.topOut < 38) {
            this.topOut += 2;
         }

         this.drawTexturedModalRect(171, 25, 215 - this.topOut, 152, this.topOut, 41);
         var14 = 0;
         j = 0;
         if(this.topOut == 38) {
            for(index = 0; index < this.cachedEffects.tagCount(); ++index) {
               Byte var17 = Byte.valueOf(this.cachedEffects.getCompoundTagAt(index).getByte("Id"));
               potion = Potion.potionTypes[var17.byteValue()];
               if(potion != null) {
                  super.mc.getTextureManager().bindTexture(GuiContainer.field_147001_a);
                  l = potion.getStatusIconIndex();
                  this.drawTexturedModalRect(var14 * 18 + 171, j * 18 + 28, 0 + l % 8 * 18, 198 + l / 8 * 18, 18, 18);
                  super.fontRendererObj.drawString("" + (this.cachedEffects.getCompoundTagAt(index).getByte("Amplifier") + 1), var14 * 18 + 171, j * 18 + 28, Color.GRAY.getRGB());
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  if(var14 == 0) {
                     var14 = 1;
                  } else {
                     var14 = 0;
                     ++j;
                  }
               }
            }
         }
      } else {
         if(this.topOut > 2) {
            this.topOut -= 2;
         }

         UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", "textures/gui/guibloodinfuser.png"));
         this.drawTexturedModalRect(171, 25, 215 - this.topOut, 152, this.topOut, 41);
      }

      if(this.flashTimer > 0) {
         --this.flashTimer;
         this.drawFlash();
      }

      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      int gx = (super.width - super.xSize) / 2;
      int gy = (super.height - super.ySize) / 2;
      this.scrollLClicked = false;
      this.scrollRClicked = false;
      int x = par1 - (gx + 38);
      int y = par2 - (gy + 57);
      if(x >= 0 && y >= 0 && x <= 6 && y <= 6) {
         if(this.tile.mode == 1) {
            this.tile.mode = 0;
            super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 0);
         } else {
            this.tile.mode = 1;
            super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 1);
         }
      }

      y = par2 - (gy + 70);
      if(x >= 0 && y >= 0 && x <= 6 && y <= 6) {
         if(this.tile.mode == 2) {
            this.tile.mode = 0;
            super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 0);
         } else {
            this.tile.mode = 2;
            super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 2);
         }
      }

      x = par1 - (gx + 111);
      y = par2 - (gy + 75);
      if(this.canScrollLeft() && x >= 0 && y >= 0 && x <= 24 && y <= 8) {
         this.offset -= 2;
      }

      x = par1 - (gx + 135);
      if(this.canScrollRight() && x >= 0 && y >= 0 && x <= 24 && y <= 8) {
         this.offset += 2;
      }

      int i;
      for(i = 0; i < 16; ++i) {
         x = par1 - (gx + 14 + 18 * (i / 2));
         y = par2 - (gy + 83 + (i % 2 == 0?18:0));
         if(this.offset + i < this.aspectsKnown.getAspectsSorted().length && this.numSelected < 8 && x >= 0 && x <= 16 && y >= 0 && y <= 16) {
            this.aspectsSelected[this.numSelected] = this.aspectsKnown.getAspectsSorted()[this.offset + i];
            ++this.numSelected;
            this.tile.aspectsSelected.add(this.aspectsKnown.getAspectsSorted()[this.offset + i], 1);
            super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, -1 * (i + this.offset) - 1);
            this.flashTimer = 8;
            this.flashColor = new Color(this.aspectsKnown.getAspectsSorted()[this.offset + i].getColor());
            this.flashX = par1 - gx - 8;
            this.flashY = par2 - gy - 8;
            super.mc.renderViewEntity.worldObj.playSound(super.mc.renderViewEntity.posX, super.mc.renderViewEntity.posY, super.mc.renderViewEntity.posZ, "thaumcraft:hhoff", 0.2F, 1.0F + super.mc.renderViewEntity.worldObj.rand.nextFloat() * 0.1F, false);
         }
      }

      for(i = 0; i < 8; ++i) {
         x = par1 - (gx + 54 + (i % 2 == 1?17:0));
         y = par2 - (gy + 12 + 17 * (i / 2));
         if(this.numSelected > i && x >= 0 && x <= 16 && y >= 0 && y <= 16 && Thaumcraft.proxy.playerKnowledge.hasDiscoveredAspect(super.mc.thePlayer.getCommandSenderName(), this.aspectsSelected[i])) {
            this.tile.aspectsSelected.remove(this.aspectsSelected[i], 1);
            super.mc.playerController.sendEnchantPacket(super.inventorySlots.windowId, 3 + this.findInList(this.aspectsSelected[i]));
            this.flashTimer = 8;
            this.flashColor = new Color(this.aspectsSelected[i].getColor());
            this.flashX = par1 - gx - 8;
            this.flashY = par2 - gy - 8;
            super.mc.renderViewEntity.worldObj.playSound(super.mc.renderViewEntity.posX, super.mc.renderViewEntity.posY, super.mc.renderViewEntity.posZ, "thaumcraft:hhoff", 0.2F, 1.0F + super.mc.renderViewEntity.worldObj.rand.nextFloat() * 0.1F, false);

            for(int j = i; j < 7; ++j) {
               this.aspectsSelected[j] = this.aspectsSelected[j + 1];
            }

            this.aspectsSelected[7] = null;
            --this.numSelected;
         }
      }

      this.cachedEffects = this.tile.getCurrentEffects();
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

   boolean canScrollLeft() {
      return this.offset > 0;
   }

   boolean canScrollRight() {
      return this.offset + 16 < this.aspectsKnown.size();
   }

   void drawAspectList() {
      Aspect[] known = this.aspectsKnown.getAspectsSorted();

      for(int i = 0; i < 16 && this.offset + i < known.length; ++i) {
         Aspect asp = known[this.offset + i];
         UtilsFX.drawTag(14 + 18 * (i / 2), 83 + (i % 2 == 0?18:0), asp, 0.0F, 0, (double)super.zLevel, 771, 1.0F, false);
      }

   }

   void drawEssentiaSelected() {
      AspectList alreadyUsed = new AspectList();

      for(int i = 0; i < this.numSelected; ++i) {
         Aspect asp = this.aspectsSelected[i];
         alreadyUsed.add(asp, 1);
         if(Thaumcraft.proxy.playerKnowledge.hasDiscoveredAspect(super.mc.thePlayer.getCommandSenderName(), this.aspectsSelected[i])) {
            UtilsFX.drawTag(54 + (i % 2 == 1?17:0), 12 + 17 * (i / 2), asp, 0.0F, 0, (double)super.zLevel, 771, 1.0F, this.tile.aspectsAcquired.getAmount(asp) < alreadyUsed.getAmount(asp));
         } else {
            Color col;
            if(this.tile.aspectsAcquired.getAmount(asp) < alreadyUsed.getAmount(asp)) {
               col = Color.DARK_GRAY;
               this.drawQuestionMark(54 + (i % 2 == 1?17:0), 12 + 17 * (i / 2), col);
            } else {
               col = new Color(asp.getColor());
               this.drawQuestionMark(54 + (i % 2 == 1?17:0), 12 + 17 * (i / 2), col);
            }
         }
      }

   }

   void drawQuestionMark(int x, int y, Color color) {
      Minecraft mc = Minecraft.getMinecraft();
      GL11.glPushMatrix();
      GL11.glDisable(2896);
      GL11.glAlphaFunc(516, 0.003921569F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glPushMatrix();
      mc.renderEngine.bindTexture(new ResourceLocation("thaumcraft", "textures/aspects/_unknown.png"));
      GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.8F);
      Tessellator var9 = Tessellator.instance;
      var9.startDrawingQuads();
      var9.setColorRGBA_F((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.8F);
      var9.addVertexWithUV((double)x + 0.0D, (double)y + 16.0D, (double)super.zLevel, 0.0D, 1.0D);
      var9.addVertexWithUV((double)x + 16.0D, (double)y + 16.0D, (double)super.zLevel, 1.0D, 1.0D);
      var9.addVertexWithUV((double)x + 16.0D, (double)y + 0.0D, (double)super.zLevel, 1.0D, 0.0D);
      var9.addVertexWithUV((double)x + 0.0D, (double)y + 0.0D, (double)super.zLevel, 0.0D, 0.0D);
      var9.draw();
      GL11.glPopMatrix();
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glEnable(2896);
      GL11.glPopMatrix();
   }

   int findInList(Aspect asp) {
      Aspect[] aspects = this.aspectsKnown.getAspectsSorted();

      for(int i = 0; i < aspects.length; ++i) {
         if(asp != null && aspects[i] != null && aspects[i].getTag().equals(asp.getTag())) {
            return i;
         }
      }

      return -1;
   }
}
