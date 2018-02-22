package com.kentington.thaumichorizons.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class FXContainment extends EntityFX {

   public static final ResourceLocation portaltex = new ResourceLocation("thaumcraft", "textures/misc/eldritch_portal.png");


   public FXContainment(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
      super.particleRed = super.particleGreen = super.particleBlue = 0.6F;
      super.particleAlpha = 0.5F;
      super.particleScale = 1.0F;
      super.particleMaxAge = 40;
      super.motionX = super.motionY = super.motionZ = 0.0D;
      super.particleRed = 1.0F;
      super.particleGreen = 1.0F;
      super.particleBlue = 1.0F;
      super.particleGravity = 0.0F;
      super.noClip = true;
   }

   public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
      long nt = System.nanoTime();
      long time = nt / 50000000L;
      int frame = (int)time % 16;
      float f2 = (float)frame / 16.0F;
      float f3 = f2 + 0.0625F;
      float f4 = 0.0F;
      float f5 = 1.0F;
      UtilsFX.bindTexture(portaltex);
      GL11.glPushMatrix();
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      float f10 = 3.0F * super.particleScale;
      float f11 = (float)(super.posX - EntityFX.interpPosX);
      float f12 = (float)(super.posY - EntityFX.interpPosY);
      float f13 = (float)(super.posZ - EntityFX.interpPosZ);
      p_70539_1_.setColorRGBA_F(super.particleRed, super.particleGreen, super.particleBlue, 0.1F);
      p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f2, (double)f4);
      p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f3, (double)f4);
      p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f3, (double)f5);
      p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f2, (double)f5);
      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glPopMatrix();
   }

   public int getFXLayer() {
      return 1;
   }

   public void onUpdate() {
      if(super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      } else {
         super.particleScale /= 1.15F;
      }
   }

}
