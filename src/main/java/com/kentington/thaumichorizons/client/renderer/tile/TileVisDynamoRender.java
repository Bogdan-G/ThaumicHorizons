package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelQuarterBlock;
import com.kentington.thaumichorizons.common.tiles.TileVisDynamo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileVisDynamoRender extends TileEntitySpecialRenderer {

   private IModelCustom model;
   private static final ResourceLocation SCANNER = new ResourceLocation("thaumcraft", "textures/models/scanner.obj");
   static String tx1 = "textures/models/goldring.png";
   static String tx2 = "textures/models/dynamobase.png";
   static String tx3 = "textures/items/lightningringv.png";
   private ModelQuarterBlock base;


   public TileVisDynamoRender() {
      this.model = AdvancedModelLoader.loadModel(SCANNER);
      this.base = new ModelQuarterBlock();
   }

   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      TileVisDynamo tco = (TileVisDynamo)te;
      int iiud;
      if(tco.rise >= 0.3F && tco.ticksProvided > 0) {
         GL11.glPushMatrix();
         GL11.glAlphaFunc(516, 0.003921569F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 1);
         long drainEntity = System.nanoTime();
         UtilsFX.bindTexture(tx3);
         int f10 = UtilsFX.getTextureAnimationSize(tx3);
         iiud = (int)(((double)(drainEntity / 40000000L) + x) % (double)f10);
         UtilsFX.renderFacingQuad((double)tco.xCoord + 0.5D, (double)((float)tco.yCoord + 0.5F), (double)tco.zCoord + 0.5D, 0.0F, 0.2F, 0.9F, f10, iiud, f, tco.color.getRGB());
         GL11.glDisable(3042);
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glPopMatrix();
      }

      if(tco.drainEntity != null && tco.drainCollision != null) {
         Entity drainEntity1 = tco.drainEntity;
         if(drainEntity1 instanceof EntityPlayer && !((EntityPlayer)drainEntity1).isUsingItem()) {
            tco.drainEntity = null;
            tco.drainCollision = null;
         } else {
            MovingObjectPosition drainCollision = tco.drainCollision;
            GL11.glPushMatrix();
            float f101 = 0.0F;
            iiud = ((EntityPlayer)drainEntity1).getItemInUseDuration();
            if(drainEntity1 instanceof EntityPlayer) {
               f101 = MathHelper.sin((float)iiud / 10.0F) * 10.0F;
            }

            Vec3 vec3 = Vec3.createVectorHelper(-0.1D, -0.1D, 0.5D);
            vec3.rotateAroundX(-(drainEntity1.prevRotationPitch + (drainEntity1.rotationPitch - drainEntity1.prevRotationPitch) * f) * 3.141593F / 180.0F);
            vec3.rotateAroundY(-(drainEntity1.prevRotationYaw + (drainEntity1.rotationYaw - drainEntity1.prevRotationYaw) * f) * 3.141593F / 180.0F);
            vec3.rotateAroundY(-f101 * 0.01F);
            vec3.rotateAroundX(-f101 * 0.015F);
            double d3 = drainEntity1.prevPosX + (drainEntity1.posX - drainEntity1.prevPosX) * (double)f + vec3.xCoord;
            double d4 = drainEntity1.prevPosY + (drainEntity1.posY - drainEntity1.prevPosY) * (double)f + vec3.yCoord;
            double d5 = drainEntity1.prevPosZ + (drainEntity1.posZ - drainEntity1.prevPosZ) * (double)f + vec3.zCoord;
            double d6 = drainEntity1 == Minecraft.getMinecraft().thePlayer?0.0D:(double)drainEntity1.getEyeHeight();
            UtilsFX.drawFloatyLine((double)drainCollision.blockX + 0.5D, (double)drainCollision.blockY + 0.5D, (double)drainCollision.blockZ + 0.5D, d3, d4 + d6, d5, f, tco.color.getRGB(), "textures/misc/wispy.png", -0.02F, (float)Math.min(iiud, 10) / 10.0F);
            GL11.glPopMatrix();
         }
      }

      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y, (float)z);
      UtilsFX.bindTexture("thaumichorizons", tx2);
      this.base.render();
      GL11.glTranslatef(0.5F, 0.2F + tco.rise, 0.5F);
      GL11.glRotatef(tco.rotation, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(tco.rotation2, 1.0F, 0.0F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      GL11.glScalef(0.36F, 0.36F, 0.36F);
      this.model.renderAll();
      GL11.glRotatef(-2.0F * tco.rotation2, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(0.5F, 1.0F, 0.5F);
      this.model.renderAll();
      GL11.glPopMatrix();
   }

}
