package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileSyntheticNode;
import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.models.ModelCrystal;

public class TileEtherealShardRender extends TileEntitySpecialRenderer {

   private ModelCrystal model = new ModelCrystal();
   static String tx1 = "textures/items/lightningringv.png";
   static String tx2 = "textures/misc/nodes.png";


   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      GL11.glPushMatrix();
      TileSyntheticNode tco = (TileSyntheticNode)te;
      int red = 255;
      int green = 255;
      int blue = 255;
      int numPoints = 0;
      int numPointsFilled = 0;
      if(tco != null && tco.getAspectsBase() != null && tco.getAspects() != null && tco.getAspects().size() > 0 && tco.getAspectsBase().size() > 0) {
         Aspect[] col = tco.getAspectsBase().getAspects();
         int rand = col.length;

         for(int nt = 0; nt < rand; ++nt) {
            Aspect asp = col[nt];
            int frames = tco.getAspectsBase().getAmount(asp);
            Color i = new Color(asp.getColor());
            red += i.getRed() * frames;
            green += i.getGreen() * frames;
            blue += i.getBlue() * frames;
            numPoints += frames;
            numPointsFilled += tco.getAspects().getAmount(asp);
         }

         red /= numPoints + 1;
         green /= numPoints + 1;
         blue /= numPoints + 1;
      }

      Color var34 = new Color(red, green, blue);
      UtilsFX.bindTexture("textures/models/crystal.png");
      Random var35 = new org.bogdang.modifications.random.XSTR((long)(tco.getBlockMetadata() + tco.xCoord + tco.yCoord * tco.zCoord));
      this.drawCrystal(0, (float)x, (float)y, (float)z, tco.rotation, 0.0F, var35, var34.getRGB(), 1.1F);
      long var36 = System.nanoTime();
      UtilsFX.bindTexture(tx2);
      byte var37 = 32;
      int var10000 = (int)(((double)(var36 / 40000000L) + x) % (double)var37);
      if(tco != null && tco.getAspectsBase() != null && tco.getAspects() != null && tco.getAspectsBase().size() > 0 && tco.getAspects().getAspects()[0] != null && tco.getAspectsBase().getAspects()[0] != null) {
         double drainEntity = 6.283185307179586D / (double)tco.getAspectsBase().size();
         int f10 = 0;
         GL11.glAlphaFunc(516, 0.003921569F);
         GL11.glDepthMask(false);
         Aspect[] iiud = tco.getAspectsBase().getAspects();
         int vec3 = iiud.length;

         for(int d3 = 0; d3 < vec3; ++d3) {
            Aspect asp1 = iiud[d3];
            if(asp1 == null) {
               break;
            }

            Color d4 = new Color(asp1.getColor());
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            double radian = Math.toRadians((double)tco.rotation);
            double dist = 0.4D + 0.1D * Math.cos(radian);
            UtilsFX.renderFacingStrip((double)tco.xCoord + 0.5D + dist * Math.sin(2.0D * radian + drainEntity * (double)f10), (double)tco.yCoord + 0.64D + 0.10000000149011612D * Math.sin(Math.toRadians((double)tco.rotation)), (double)tco.zCoord + 0.5D + dist * Math.cos(2.0D * radian + drainEntity * (double)f10), 0.0F, 0.1F + 0.005F * (float)tco.getAspects().getAmount(asp1), 0.9F, var37, 1, (int)tco.rotation % var37, f, d4.getRGB());
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            ++f10;
         }

         GL11.glDepthMask(true);
         GL11.glAlphaFunc(516, 0.1F);
      }

      if(tco != null && tco.drainEntity != null && tco.drainCollision != null) {
         Entity var38 = tco.drainEntity;
         if(var38 instanceof EntityPlayer && !((EntityPlayer)var38).isUsingItem()) {
            tco.drainEntity = null;
            tco.drainCollision = null;
            return;
         }

         MovingObjectPosition drainCollision = tco.drainCollision;
         GL11.glPushMatrix();
         float var40 = 0.0F;
         int var41 = ((EntityPlayer)var38).getItemInUseDuration();
         if(var38 instanceof EntityPlayer) {
            var40 = MathHelper.sin((float)var41 / 10.0F) * 10.0F;
         }

         Vec3 var39 = Vec3.createVectorHelper(-0.1D, -0.1D, 0.5D);
         var39.rotateAroundX(-(var38.prevRotationPitch + (var38.rotationPitch - var38.prevRotationPitch) * f) * 3.141593F / 180.0F);
         var39.rotateAroundY(-(var38.prevRotationYaw + (var38.rotationYaw - var38.prevRotationYaw) * f) * 3.141593F / 180.0F);
         var39.rotateAroundY(-var40 * 0.01F);
         var39.rotateAroundX(-var40 * 0.015F);
         double var43 = var38.prevPosX + (var38.posX - var38.prevPosX) * (double)f + var39.xCoord;
         double var42 = var38.prevPosY + (var38.posY - var38.prevPosY) * (double)f + var39.yCoord;
         double d5 = var38.prevPosZ + (var38.posZ - var38.prevPosZ) * (double)f + var39.zCoord;
         double d6 = var38 == Minecraft.getMinecraft().thePlayer?0.0D:(double)var38.getEyeHeight();
         UtilsFX.drawFloatyLine(var43, var42 + d6, d5, (double)drainCollision.blockX + 0.5D, (double)drainCollision.blockY + 0.5D, (double)drainCollision.blockZ + 0.5D, f, tco.color.getRGB(), "textures/misc/wispy.png", -0.02F, (float)Math.min(var41, 10) / 10.0F);
         GL11.glPopMatrix();
      }

      GL11.glDisable(3042);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glPopMatrix();
   }

   private void drawCrystal(int ori, float x, float y, float z, float a1, float a2, Random rand, int color, float size) {
      EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
      float shade = MathHelper.sin((float)(p.ticksExisted + rand.nextInt(10)) / (5.0F + rand.nextFloat())) * 0.075F + 0.925F;
      Color c = new Color(color);
      float r = (float)c.getRed() / 220.0F;
      float g = (float)c.getGreen() / 220.0F;
      float b = (float)c.getBlue() / 220.0F;
      GL11.glPushMatrix();
      GL11.glEnable(2977);
      GL11.glEnable(3042);
      GL11.glEnable('\u803a');
      GL11.glBlendFunc(770, 771);
      GL11.glTranslatef(x + 0.5F, (float)((double)(y - 0.15F) + 0.10000000149011612D * Math.sin(Math.toRadians((double)a1))), z + 0.5F);
      GL11.glRotatef(a1, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(a2, 1.0F, 0.0F, 0.0F);
      GL11.glScalef((0.15F + rand.nextFloat() * 0.075F) * size, (0.5F + rand.nextFloat() * 0.1F) * size, (0.15F + rand.nextFloat() * 0.05F) * size);
      int var19 = (int)(210.0F * shade);
      int var20 = var19 % 65536;
      int var21 = var19 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var20 / 1.0F, (float)var21 / 1.0F);
      GL11.glColor4f(r, g, b, 1.0F);
      this.model.render();
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glDisable('\u803a');
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

}
