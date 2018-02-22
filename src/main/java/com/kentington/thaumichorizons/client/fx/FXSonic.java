package com.kentington.thaumichorizons.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class FXSonic extends EntityFX {

   float yaw = 0.0F;
   float pitch = 0.0F;
   private IModelCustom model;
   private static final ResourceLocation MODEL = new ResourceLocation("thaumcraft", "textures/models/hemis.obj");


   public FXSonic(World world, double d, double d1, double d2, int age, int dir) {
      super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
      super.particleRed = 0.0F;
      super.particleGreen = 1.0F;
      super.particleBlue = 1.0F;
      super.particleGravity = 0.0F;
      super.motionX = super.motionY = super.motionZ = 0.0D;
      super.particleMaxAge = age + super.rand.nextInt(age / 2);
      super.noClip = false;
      this.setSize(0.01F, 0.01F);
      super.noClip = true;
      super.particleScale = 1.0F;
      switch(dir) {
      case 0:
         this.pitch = 90.0F;
         break;
      case 1:
         this.pitch = -90.0F;
         break;
      case 2:
         this.yaw = 180.0F;
         break;
      case 3:
         this.yaw = 0.0F;
         break;
      case 4:
         this.yaw = 90.0F;
         break;
      case 5:
         this.yaw = 270.0F;
      }

      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
   }

   public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
      tessellator.draw();
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 1);
      if(this.model == null) {
         this.model = AdvancedModelLoader.loadModel(MODEL);
      }

      float fade = ((float)super.particleAge + f) / (float)super.particleMaxAge;
      float xx = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)f - EntityFX.interpPosX);
      float yy = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)f - EntityFX.interpPosY);
      float zz = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)f - EntityFX.interpPosZ);
      GL11.glTranslated((double)xx, (double)yy, (double)zz);
      float b = 1.0F;
      int frame = Math.min(15, (int)(14.0F * fade) + 1);
      UtilsFX.bindTexture("textures/models/ripple" + frame + ".png");
      b = 0.5F;
      short i = 220;
      int j = i % 65536;
      int k = i / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
      GL11.glRotatef(-this.yaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(this.pitch, 1.0F, 0.0F, 0.0F);
      GL11.glScaled(0.5D, 0.5D, -0.5D);
      GL11.glColor4f(0.0F, b, b, 1.0F);
      this.model.renderAll();
      GL11.glDisable(3042);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
      Minecraft.getMinecraft().renderEngine.bindTexture(UtilsFX.getParticleTexture());
      tessellator.startDrawingQuads();
   }

   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      if(super.particleAge++ >= super.particleMaxAge) {
         this.setDead();
      }

   }

}
