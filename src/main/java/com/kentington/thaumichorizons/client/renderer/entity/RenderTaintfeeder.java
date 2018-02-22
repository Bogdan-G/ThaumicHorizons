package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntityTaintPig;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.tile.TileNodeRenderer;

public class RenderTaintfeeder extends RenderPig {

   private static final ResourceLocation pigTextures = new ResourceLocation("thaumichorizons", "textures/entity/taintfeeder.png");
   private static final ResourceLocation pigEyesTextures = new ResourceLocation("thaumichorizons", "textures/entity/taintfeeder_eyes.png");


   public RenderTaintfeeder(ModelBase p_i1265_1_, ModelBase p_i1265_2_, float p_i1265_3_) {
      super(p_i1265_1_, p_i1265_2_, p_i1265_3_);
   }

   protected ResourceLocation getEntityTexture(EntityPig p_110775_1_) {
      return pigTextures;
   }

   public void doRender(EntityTaintPig p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      GL11.glPushMatrix();
      GL11.glAlphaFunc(516, 0.003921569F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 1);
      GL11.glPushMatrix();
      GL11.glDepthMask(false);
      GL11.glDisable(2884);
      int i = p_76986_1_.ticksExisted % 32;
      UtilsFX.bindTexture(TileNodeRenderer.nodetex);
      UtilsFX.renderFacingStrip(p_76986_1_.posX, p_76986_1_.posY + (double)p_76986_1_.height / 1.75D, p_76986_1_.posZ, 0.0F, 2.0F, 1.0F, 32, 6, i, p_76986_9_, 11197951);
      GL11.glEnable(2884);
      GL11.glDepthMask(true);
      GL11.glPopMatrix();
      GL11.glDisable(3042);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glPopMatrix();
   }

   public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityTaintPig)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityTaintPig)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityTaintPig)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
      return this.shouldRenderPass((EntityTaintPig)p_77032_1_, p_77032_2_, p_77032_3_);
   }

   protected int shouldRenderPass(EntityTaintPig p_77032_1_, int p_77032_2_, float p_77032_3_) {
      if(p_77032_2_ != 0) {
         return -1;
      } else {
         float f = (float)Math.sin(Math.toRadians((double)(p_77032_1_.ticksExisted % 45)) * 8.0D);
         this.bindTexture(pigEyesTextures);
         GL11.glEnable(3042);
         GL11.glDisable(3008);
         GL11.glBlendFunc(770, 771);
         if(p_77032_1_.isInvisible()) {
            GL11.glDepthMask(false);
         } else {
            GL11.glDepthMask(true);
         }

         char c0 = '\uf0f0';
         int j = c0 % 65536;
         int k = c0 / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.32F + 0.32F * f);
         return 1;
      }
   }

}
