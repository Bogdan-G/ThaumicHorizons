package com.kentington.thaumichorizons.client.renderer.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class RenderSoul extends Render {

   int size1 = 0;
   int size2 = 0;


   public RenderSoul() {
      super.shadowSize = 0.0F;
   }

   public void renderEntityAt(Entity entity, double x, double y, double z, float fq, float pticks) {
      if(((EntityLiving)entity).getHealth() > 0.0F) {
         float f1 = ActiveRenderInfo.rotationX;
         float f2 = ActiveRenderInfo.rotationXZ;
         float f3 = ActiveRenderInfo.rotationZ;
         float f4 = ActiveRenderInfo.rotationYZ;
         float f5 = ActiveRenderInfo.rotationXY;
         float f10 = 0.25F;
         float f11 = (float)x;
         float f12 = (float)y;
         float f13 = (float)z;
         Tessellator tessellator = Tessellator.instance;
         GL11.glPushMatrix();
         GL11.glDepthMask(false);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 1);
         UtilsFX.bindTexture("thaumichorizons", "textures/misc/soul.png");
         int i = entity.ticksExisted % 16;
         float x0 = (float)i / (float)this.size1;
         float x1 = (float)(i + 1) / (float)this.size1;
         tessellator.startDrawingQuads();
         tessellator.setBrightness(240);
         tessellator.addVertexWithUV((double)(f11 - f1 * f10 - f4 * f10), (double)(f12 - f2 * f10), (double)(f13 - f3 * f10 - f5 * f10), (double)x1, 0.0D);
         tessellator.addVertexWithUV((double)(f11 - f1 * f10 + f4 * f10), (double)(f12 + f2 * f10), (double)(f13 - f3 * f10 + f5 * f10), (double)x1, 1.0D);
         tessellator.addVertexWithUV((double)(f11 + f1 * f10 + f4 * f10), (double)(f12 + f2 * f10), (double)(f13 + f3 * f10 + f5 * f10), (double)x0, 1.0D);
         tessellator.addVertexWithUV((double)(f11 + f1 * f10 - f4 * f10), (double)(f12 - f2 * f10), (double)(f13 + f3 * f10 - f5 * f10), (double)x0, 0.0D);
         tessellator.draw();
         GL11.glDisable(3042);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
      }
   }

   public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
      if(this.size1 == 0) {
         this.size1 = 16;
      }

      this.renderEntityAt(entity, d, d1, d2, f, f1);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return AbstractClientPlayer.locationStevePng;
   }
}
