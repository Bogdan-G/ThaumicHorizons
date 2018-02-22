package com.kentington.thaumichorizons.client.fx;

import java.awt.Color;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FXEssentiaBubble extends EntityFX {

   private int count = 0;
   private int delay = 0;
   public int particle = 24;


   public FXEssentiaBubble(World par1World, double par2, double par4, double par6, int count, int color, float scale, int delay) {
      super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
      super.particleRed = super.particleGreen = super.particleBlue = 0.6F;
      super.particleScale = (MathHelper.sin((float)count / 2.0F) * 0.1F + 1.0F) * scale;
      this.delay = delay;
      this.count = count;
      super.particleMaxAge = 20 + super.rand.nextInt(20);
      super.motionY = (double)(0.025F + MathHelper.sin((float)count / 3.0F) * 0.002F);
      super.motionX = super.motionZ = 0.0D;
      Color c = new Color(color);
      float mr = (float)c.getRed() / 255.0F * 0.2F;
      float mg = (float)c.getGreen() / 255.0F * 0.2F;
      float mb = (float)c.getBlue() / 255.0F * 0.2F;
      super.particleRed = (float)c.getRed() / 255.0F - mr + super.rand.nextFloat() * mr;
      super.particleGreen = (float)c.getGreen() / 255.0F - mg + super.rand.nextFloat() * mg;
      super.particleBlue = (float)c.getBlue() / 255.0F - mb + super.rand.nextFloat() * mb;
      super.particleGravity = 0.2F;
      super.noClip = false;
   }

   public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
      if(this.delay <= 0) {
         float t2 = 0.5625F;
         float t3 = 0.625F;
         float t4 = 0.0625F;
         float t5 = 0.125F;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
         int var10000 = this.particle + super.particleAge % 16;
         float s = MathHelper.sin((float)(super.particleAge - this.count) / 5.0F) * 0.25F + 1.0F;
         float var12 = 0.1F * super.particleScale * s;
         float var13 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)f - EntityFX.interpPosX);
         float var14 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)f - EntityFX.interpPosY);
         float var15 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)f - EntityFX.interpPosZ);
         float var16 = 1.0F;
         tessellator.setBrightness(240);
         tessellator.setColorRGBA_F(super.particleRed * var16, super.particleGreen * var16, super.particleBlue * var16, 0.5F);
         tessellator.addVertexWithUV((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12), (double)t2, (double)t5);
         tessellator.addVertexWithUV((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12), (double)t3, (double)t5);
         tessellator.addVertexWithUV((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12), (double)t3, (double)t4);
         tessellator.addVertexWithUV((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12), (double)t2, (double)t4);
      }
   }

   public int getFXLayer() {
      return 1;
   }

   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      if(this.delay > 0) {
         --this.delay;
      } else if(super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      } else {
         super.motionY += 0.00125D;
         super.particleScale *= 1.05F;
         this.moveEntity(super.motionX, super.motionY, super.motionZ);
         super.motionY *= 0.985D;
      }
   }
}
