package com.kentington.thaumichorizons.client.fx;

import java.awt.Color;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FXEssentiaTrail extends EntityFX {

   private double targetX;
   private double targetY;
   private double targetZ;
   private int count = 0;
   public int particle = 24;


   public FXEssentiaTrail(World par1World, double par2, double par4, double par6, double tx, double ty, double tz, int count, int color, float scale) {
      super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
      super.particleRed = super.particleGreen = super.particleBlue = 0.6F;
      super.particleScale = (MathHelper.sin((float)count / 2.0F) * 0.1F + 1.0F) * scale;
      this.count = count;
      this.targetX = tx;
      this.targetY = ty;
      this.targetZ = tz;
      double var10000 = tx - super.posX;
      var10000 = ty - super.posY;
      var10000 = tz - super.posZ;
      super.particleMaxAge = 20 + super.rand.nextInt(20);
      super.motionX = (double)(MathHelper.sin((float)count / 4.0F) * 0.003F) + super.rand.nextGaussian() * 0.002000000094994903D;
      super.motionY = (double)(0.1F + MathHelper.sin((float)count / 3.0F) * 0.002F);
      super.motionZ = (double)(MathHelper.sin((float)count / 2.0F) * 0.003F) + super.rand.nextGaussian() * 0.002000000094994903D;
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

   public int getFXLayer() {
      return 1;
   }

   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      if(super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      } else {
         super.motionY += 0.01D * (double)super.particleGravity;
         if(!super.noClip) {
            this.pushOutOfBlocks(super.posX, super.posY, super.posZ);
         }

         this.moveEntity(super.motionX, super.motionY, super.motionZ);
         super.motionX *= 0.985D;
         super.motionY *= 0.985D;
         super.motionZ *= 0.985D;
         super.motionX = (double)MathHelper.clamp_float((float)super.motionX, -0.05F, 0.05F);
         super.motionY = (double)MathHelper.clamp_float((float)super.motionY, -0.05F, 0.05F);
         super.motionZ = (double)MathHelper.clamp_float((float)super.motionZ, -0.05F, 0.05F);
         double dx = this.targetX - super.posX;
         double dy = this.targetY - super.posY;
         double dz = this.targetZ - super.posZ;
         double d13 = 0.01D;
         double d11 = (double)MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
         if(d11 < 2.0D) {
            super.particleScale *= 0.98F;
         }

         if(super.particleScale < 0.2F) {
            this.setDead();
         } else {
            dx /= d11;
            dy /= d11;
            dz /= d11;
            super.motionX += dx * (d13 / Math.min(1.0D, d11));
            super.motionY += dy * (d13 / Math.min(1.0D, d11));
            super.motionZ += dz * (d13 / Math.min(1.0D, d11));
         }
      }
   }

   public void setGravity(float value) {
      super.particleGravity = value;
   }

   protected boolean pushOutOfBlocks(double par1, double par3, double par5) {
      int var7 = MathHelper.floor_double(par1);
      int var8 = MathHelper.floor_double(par3);
      int var9 = MathHelper.floor_double(par5);
      double var10 = par1 - (double)var7;
      double var12 = par3 - (double)var8;
      double var14 = par5 - (double)var9;
      if(!super.worldObj.isAirBlock(var7, var8, var9) && super.worldObj.isBlockNormalCubeDefault(var7, var8, var9, true) && !super.worldObj.isAnyLiquid(super.boundingBox)) {
         boolean var16 = !super.worldObj.isBlockNormalCubeDefault(var7 - 1, var8, var9, true);
         boolean var17 = !super.worldObj.isBlockNormalCubeDefault(var7 + 1, var8, var9, true);
         boolean var18 = !super.worldObj.isBlockNormalCubeDefault(var7, var8 - 1, var9, true);
         boolean var19 = !super.worldObj.isBlockNormalCubeDefault(var7, var8 + 1, var9, true);
         boolean var20 = !super.worldObj.isBlockNormalCubeDefault(var7, var8, var9 - 1, true);
         boolean var21 = !super.worldObj.isBlockNormalCubeDefault(var7, var8, var9 + 1, true);
         byte var22 = -1;
         double var23 = 9999.0D;
         if(var16 && var10 < var23) {
            var23 = var10;
            var22 = 0;
         }

         if(var17 && 1.0D - var10 < var23) {
            var23 = 1.0D - var10;
            var22 = 1;
         }

         if(var18 && var12 < var23) {
            var23 = var12;
            var22 = 2;
         }

         if(var19 && 1.0D - var12 < var23) {
            var23 = 1.0D - var12;
            var22 = 3;
         }

         if(var20 && var14 < var23) {
            var23 = var14;
            var22 = 4;
         }

         if(var21 && 1.0D - var14 < var23) {
            var23 = 1.0D - var14;
            var22 = 5;
         }

         float var25 = super.rand.nextFloat() * 0.05F + 0.025F;
         float var26 = (super.rand.nextFloat() - super.rand.nextFloat()) * 0.1F;
         if(var22 == 0) {
            super.motionX = (double)(-var25);
            super.motionY = super.motionZ = (double)var26;
         }

         if(var22 == 1) {
            super.motionX = (double)var25;
            super.motionY = super.motionZ = (double)var26;
         }

         if(var22 == 2) {
            super.motionY = (double)(-var25);
            super.motionX = super.motionZ = (double)var26;
         }

         if(var22 == 3) {
            super.motionY = (double)var25;
            super.motionX = super.motionZ = (double)var26;
         }

         if(var22 == 4) {
            super.motionZ = (double)(-var25);
            super.motionY = super.motionX = (double)var26;
         }

         if(var22 == 5) {
            super.motionZ = (double)var25;
            super.motionY = super.motionX = (double)var26;
         }

         return true;
      } else {
         return false;
      }
   }
}
