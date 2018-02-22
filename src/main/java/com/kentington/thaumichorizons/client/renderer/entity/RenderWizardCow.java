package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntityWizardCow;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.tile.TileNodeRenderer;

public class RenderWizardCow extends RenderCow {

   public static HashMap cowAspects = new HashMap();
   public static HashMap cowTypes = new HashMap();
   public static HashMap cowMods = new HashMap();


   public RenderWizardCow(ModelBase p_i1253_1_, float p_i1253_2_) {
      super(p_i1253_1_, p_i1253_2_);
   }

   public void doRender(EntityWizardCow p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
      super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if(cowAspects.containsKey(p_76986_1_.getUniqueID().toString())) {
         this.renderMyNode(p_76986_1_.posX, p_76986_1_.posY + (double)(p_76986_1_.height / 2.0F), p_76986_1_.posZ, Minecraft.getMinecraft().renderViewEntity, 32.0D, true, true, 1.0F, p_76986_9_, (AspectList)cowAspects.get(p_76986_1_.getUniqueID().toString()), (NodeType)cowTypes.get(p_76986_1_.getUniqueID().toString()), (NodeModifier)cowMods.get(p_76986_1_.getUniqueID().toString()));
      }

      GL11.glDisable(3042);
   }

   public void renderMyNode(double x, double y, double z, EntityLivingBase viewer, double viewDistance, boolean visible, boolean depthIgnore, float size, float partialTicks, AspectList aspects, NodeType type, NodeModifier mod) {
      long nt = System.nanoTime();
      UtilsFX.bindTexture(TileNodeRenderer.nodetex);
      byte frames = 32;
      if(aspects.size() > 0 && visible) {
         double var36 = viewer.getDistance(x, y, z);
         if(var36 > viewDistance) {
            return;
         }

         float alpha = (float)((viewDistance - var36) / viewDistance);
         if(mod != null) {
            switch(mod.ordinal()) {
            case 1:
               alpha *= 1.5F;
               break;
            case 2:
               alpha *= 0.66F;
               break;
            case 3:
               alpha *= MathHelper.sin((float)viewer.ticksExisted / 3.0F) * 0.25F + 0.33F;
            }
         }

         GL11.glPushMatrix();
         GL11.glAlphaFunc(516, 0.003921569F);
         GL11.glDepthMask(false);
         if(depthIgnore) {
            GL11.glDisable(2929);
         }

         GL11.glDisable(2884);
         long time = nt / 5000000L;
         float bscale = 0.25F;
         GL11.glPushMatrix();
         float rad = 6.283186F;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
         int i1 = (int)(((double)(nt / 40000000L) + x) % (double)frames);
         int count = 0;
         float scale = 0.0F;
         float angle = 0.0F;
         float average = 0.0F;
         Aspect[] strip = aspects.getAspects();
         int var33 = strip.length;

         for(int var34 = 0; var34 < var33; ++var34) {
            Aspect aspect = strip[var34];
            if(aspect.getBlend() == 771) {
               alpha = (float)((double)alpha * 1.5D);
            }

            average += (float)aspects.getAmount(aspect);
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, aspect.getBlend());
            scale = MathHelper.sin((float)viewer.ticksExisted / (14.0F - (float)count)) * bscale + bscale * 2.0F;
            scale = 0.2F + scale * ((float)aspects.getAmount(aspect) / 50.0F);
            scale *= size;
            angle = (float)(time % (long)(5000 + 500 * count)) / (5000.0F + (float)(500 * count)) * rad;
            UtilsFX.renderFacingStrip(x, y, z, angle, scale, alpha / Math.max(1.0F, (float)aspects.size() / 2.0F), frames, 0, i1, partialTicks, aspect.getColor());
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            ++count;
            if(aspect.getBlend() == 771) {
               alpha = (float)((double)alpha / 1.5D);
            }
         }

         average /= (float)aspects.size();
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         i1 = (int)(((double)(nt / 40000000L) + x) % (double)frames);
         scale = 0.1F + average / 150.0F;
         scale *= size;
         byte var37 = 1;
         switch(type.ordinal()) {
         case 1:
            GL11.glBlendFunc(770, 1);
            break;
         case 2:
            GL11.glBlendFunc(770, 1);
            var37 = 6;
            angle = 0.0F;
            break;
         case 3:
            GL11.glBlendFunc(770, 771);
            var37 = 2;
            break;
         case 4:
            GL11.glBlendFunc(770, 771);
            var37 = 5;
            break;
         case 5:
            GL11.glBlendFunc(770, 1);
            var37 = 4;
            break;
         case 6:
            scale *= 0.75F;
            GL11.glBlendFunc(770, 1);
            var37 = 3;
         }

         GL11.glColor4f(1.0F, 0.0F, 1.0F, alpha);
         UtilsFX.renderFacingStrip(x, y, z, angle, scale, alpha, frames, var37, i1, partialTicks, 16777215);
         GL11.glDisable(3042);
         GL11.glPopMatrix();
         GL11.glPopMatrix();
         GL11.glEnable(2884);
         if(depthIgnore) {
            GL11.glEnable(2929);
         }

         GL11.glDepthMask(true);
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glPopMatrix();
      } else {
         GL11.glPushMatrix();
         GL11.glAlphaFunc(516, 0.003921569F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 1);
         GL11.glDepthMask(false);
         int i = (int)(((double)(nt / 40000000L) + x) % (double)frames);
         GL11.glColor4f(1.0F, 0.0F, 1.0F, 0.1F);
         UtilsFX.renderFacingStrip(x, y, z, 0.0F, 0.5F, 0.1F, frames, 1, i, partialTicks, 16777215);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glPopMatrix();
      }

   }

   public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityWizardCow)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityWizardCow)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityWizardCow)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

}
