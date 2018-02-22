package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntityLunarWolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.tile.TileNodeRenderer;

public class RenderLunarWolf extends RenderWolf {

   ResourceLocation wolfTex = new ResourceLocation("thaumichorizons", "textures/entity/lunarwolf.png");


   public RenderLunarWolf(ModelBase p_i1269_1_, ModelBase p_i1269_2_, float p_i1269_3_) {
      super(p_i1269_1_, p_i1269_2_, p_i1269_3_);
   }

   public void doRender(EntityLunarWolf p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      if(p_76986_1_.worldObj.getWorldTime() % 24000L >= 12000L) {
         float scale = p_76986_1_.worldObj.getCurrentMoonPhaseFactor() * 3.0F;
         GL11.glPushMatrix();
         GL11.glAlphaFunc(516, 0.003921569F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 1);
         GL11.glPushMatrix();
         GL11.glDepthMask(false);
         GL11.glDisable(2884);
         int i = p_76986_1_.ticksExisted % 32;
         UtilsFX.bindTexture(TileNodeRenderer.nodetex);
         UtilsFX.renderFacingStrip(p_76986_1_.posX, p_76986_1_.posY + (double)p_76986_1_.height / 1.75D, p_76986_1_.posZ, 0.0F, scale, 0.75F, 32, 1, i, p_76986_9_, 11197951);
         GL11.glEnable(2884);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
         GL11.glDisable(3042);
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glPopMatrix();
      }
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntityLunarWolf)p_110775_1_);
   }

   protected ResourceLocation getEntityTexture(EntityWolf p_110775_1_) {
      return this.getEntityTexture((EntityLunarWolf)p_110775_1_);
   }

   protected ResourceLocation getEntityTexture(EntityLunarWolf p_110775_1_) {
      return this.wolfTex;
   }

   public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityLunarWolf)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityLunarWolf)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityLunarWolf)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }
}
