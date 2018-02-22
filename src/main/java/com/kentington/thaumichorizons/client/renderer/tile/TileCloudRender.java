package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileCloud;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;

public class TileCloudRender extends TileEntitySpecialRenderer {

   private Minecraft mc = Minecraft.getMinecraft();
   Random random = new Random();
   private int rendererUpdateCount;
   private static final ResourceLocation locationRainPng = new ResourceLocation("thaumichorizons", "textures/environment/rain.png");
   private static final ResourceLocation locationEmberPng = new ResourceLocation("thaumichorizons", "textures/environment/firerain.png");
   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");


   public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partial) {
      if(((TileCloud)tile).isRaining()) {
         this.renderRainSnowToo((TileCloud)tile, x, y, z, partial);
         ++this.rendererUpdateCount;
      }

   }

   public void renderRainSnowToo(TileCloud tco, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)p_147500_2_ + 0.5F, (float)p_147500_4_ + 1.5F, (float)p_147500_6_ + 0.5F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glPopMatrix();
      if(tco.getWorldObj() != null) {
         float f1 = 1.0F;
         GL11.glAlphaFunc(516, 0.1F);
         if(f1 > 0.0F) {
            Tessellator tessellator = Tessellator.instance;
            BiomeGenBase biomegenbase = tco.getWorldObj().getBiomeGenForCoords(tco.xCoord, tco.zCoord);
            if(tco.md == 1) {
               this.bindTexture(locationEmberPng);
            } else if((double)biomegenbase.getFloatTemperature(tco.xCoord, tco.yCoord, tco.zCoord) >= 0.15D) {
               this.bindTexture(locationRainPng);
            } else {
               this.bindTexture(locationSnowPng);
            }

            GL11.glTexParameterf(3553, 10242, 10497.0F);
            GL11.glTexParameterf(3553, 10243, 10497.0F);
            GL11.glDisable(2896);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            float f2 = (float)tco.getWorldObj().getTotalWorldTime() + p_147500_8_ + (float)tco.xCoord + (float)(16 * tco.zCoord);
            float f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
            byte b0 = 1;
            double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
            tessellator.startDrawingQuads();
            switch(tco.md) {
            case 1:
               tessellator.setColorRGBA(255, 255, 255, 255);
               break;
            case 2:
            default:
               tessellator.setColorRGBA(32, 64, 255, 255);
               break;
            case 3:
               tessellator.setColorRGBA(32, 255, 64, 255);
               break;
            case 4:
               tessellator.setColorRGBA(64, 64, 64, 255);
               break;
            case 5:
               tessellator.setColorRGBA(255, 64, 32, 255);
               break;
            case 6:
               tessellator.setColorRGBA(170, 64, 200, 255);
               break;
            case 7:
               tessellator.setColorRGBA(255, 255, 255, 255);
               break;
            case 8:
               tessellator.setColorRGBA(160, 255, 160, 255);
               break;
            case 9:
               tessellator.setColorRGBA(255, 230, 64, 255);
            }

            double d30 = 0.0D;
            double d4 = 0.0D;
            double d6 = 1.0D;
            double d8 = 0.0D;
            double d10 = 0.0D;
            double d12 = 1.0D;
            double d14 = 1.0D;
            double d16 = 1.0D;
            double d18 = tco.howManyDown != -1?(double)(tco.howManyDown - 1):(double)(256.0F * f1);
            double d20 = 0.0D;
            double d22 = 1.0D;
            double d24 = (double)(-1.0F + f3);
            double d26 = (double)tco.howManyDown / 4.0D + d24;
            tessellator.addVertexWithUV(p_147500_2_ + d30, p_147500_4_ - d18, p_147500_6_ + d4, d22, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d30, p_147500_4_, p_147500_6_ + d4, d22, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d6, p_147500_4_, p_147500_6_ + d8, d20, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d6, p_147500_4_ - d18, p_147500_6_ + d8, d20, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d14, p_147500_4_ - d18, p_147500_6_ + d16, d22, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d14, p_147500_4_, p_147500_6_ + d16, d22, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d10, p_147500_4_, p_147500_6_ + d12, d20, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d10, p_147500_4_ - d18, p_147500_6_ + d12, d20, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d6, p_147500_4_ - d18, p_147500_6_ + d8, d22, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d6, p_147500_4_, p_147500_6_ + d8, d22, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d14, p_147500_4_, p_147500_6_ + d16, d20, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d14, p_147500_4_ - d18, p_147500_6_ + d16, d20, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d10, p_147500_4_ - d18, p_147500_6_ + d12, d22, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d10, p_147500_4_, p_147500_6_ + d12, d22, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d30, p_147500_4_, p_147500_6_ + d4, d20, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d30, p_147500_4_ - d18, p_147500_6_ + d4, d20, d26);
            tessellator.draw();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glDepthMask(true);
         }

      }
   }

}
