package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileVortex;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.QuadHelper;
import thaumcraft.client.lib.UtilsFX;

@SideOnly(Side.CLIENT)
public class TileVortexRender extends TileEntitySpecialRenderer {

   public static final ResourceLocation nodetex = new ResourceLocation("thaumcraft", "textures/misc/nodes.png");
   public static final ResourceLocation vortextex = new ResourceLocation("thaumcraft", "textures/misc/vortex.png");


   public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
      if(tile instanceof TileVortex) {
         float size = 10.0F;
         TileVortex node = (TileVortex)tile;
         double viewDistance = 64.0D;
         EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
         boolean condition = true;
         boolean depthIgnore = false;
         renderNode(viewer, viewDistance, condition, depthIgnore, size, tile.xCoord, tile.yCoord, tile.zCoord, partialTicks, ((TileVortex)tile).aspects, node.count, node.collapsing, node.beams, node.createdDimension, node.cheat);
      }
   }

   public static void renderNode(EntityLivingBase viewer, double viewDistance, boolean visible, boolean depthIgnore, float size, int x, int y, int z, float partialTicks, AspectList aspects, int timeOpen, boolean collapsing, int beams, boolean plane, boolean cheat) {
      long nt = System.nanoTime();
      byte frames = 32;
      if(aspects.size() > 0 && visible) {
         double distance = viewer.getDistance((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D);
         if(distance > viewDistance) {
            return;
         }

         float alpha = (float)((viewDistance - distance) / viewDistance);
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
         int i = (int)((nt / 40000000L + (long)x) % (long)frames);
         int count = 0;
         float scale = 0.0F;
         float angle = 0.0F;
         float average = 0.0F;
         UtilsFX.bindTexture(nodetex);
         Aspect[] corescale = aspects.getAspects();
         int var32 = corescale.length;

         int var33;
         for(var33 = 0; var33 < var32; ++var33) {
            Aspect aspect = corescale[var33];
            if(aspect == null) {
               aspect = Aspect.WATER;
            }

            if(aspect.getBlend() == 771) {
               alpha = (float)((double)alpha * 1.5D);
            }

            average += (float)aspects.getAmount(aspect);
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, aspect.getBlend());
            scale = MathHelper.sin((float)viewer.ticksExisted / (14.0F - (float)count)) * bscale + bscale * 2.0F;
            scale = 0.4F;
            scale *= size;
            angle = (float)(time % (long)(5000 + 500 * count)) / (5000.0F + (float)(500 * count)) * rad;
            if(beams >= 6 && timeOpen >= 50) {
               UtilsFX.renderFacingStrip((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, angle, scale / 3.0F, alpha / Math.max(1.0F, (float)aspects.size() / 2.0F), frames, 0, i, partialTicks, aspect.getColor());
            } else {
               UtilsFX.renderFacingStrip((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, angle, scale, alpha / Math.max(1.0F, (float)aspects.size() / 2.0F), frames, 0, i, partialTicks, aspect.getColor());
            }

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
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(1.0F, 0.0F, 1.0F, alpha);
         float var36 = 1.0F;
         if(timeOpen < 50 && !collapsing) {
            var36 = (float)timeOpen / 50.0F;
         } else if(collapsing) {
            var36 = 1.0F - (float)timeOpen / 25.0F;
         }

         if(!cheat && (beams < 6 || timeOpen < 50)) {
            UtilsFX.bindTexture(vortextex);
            renderVortex((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, angle * 20.0F * var36 / (float)(1 + 2 * beams), scale / 5.0F * var36, 0.8F, partialTicks, 16777215);
         } else {
            UtilsFX.bindTexture(nodetex);
            UtilsFX.renderFacingStrip((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, angle, scale * 0.75F, alpha, frames, 2, i, partialTicks, 16777215);
         }

         GL11.glDisable(3042);
         GL11.glPopMatrix();
         if(plane) {
            Aspect[] var37 = aspects.getAspects();
            var33 = var37.length;

            for(int var38 = 0; var38 < var33; ++var38) {
               Aspect aspect1 = var37[var38];
               if(aspect1 == null) {
                  aspect1 = Aspect.WATER;
               }

               if(aspect1.getBlend() == 771) {
                  alpha = (float)((double)alpha * 1.5D);
               }

               average += (float)aspects.getAmount(aspect1);
               GL11.glPushMatrix();
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               scale = MathHelper.sin((float)viewer.ticksExisted / (14.0F - (float)count)) * bscale + bscale * 2.0F;
               scale = 0.4F;
               float var10000 = scale * size;
               angle = (float)(time % (long)(5000 + 500 * count)) / (5000.0F + (float)(500 * count)) * rad;
               UtilsFX.renderFacingStrip((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, angle, 0.5F, alpha / Math.max(1.0F, (float)aspects.size() / 2.0F), frames, 0, i, partialTicks, aspect1.getColor());
               GL11.glDisable(3042);
               GL11.glPopMatrix();
               ++count;
               if(aspect1.getBlend() == 771) {
                  alpha = (float)((double)alpha / 1.5D);
               }
            }
         }

         GL11.glPopMatrix();
         GL11.glEnable(2884);
         if(depthIgnore) {
            GL11.glEnable(2929);
         }

         GL11.glDepthMask(true);
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glPopMatrix();
      }

   }

   static void renderVortex(double px, double py, double pz, float angle, float scale, float alpha, float partialTicks, int color) {
      Tessellator tessellator = Tessellator.instance;
      float arX = ActiveRenderInfo.rotationX;
      float arZ = ActiveRenderInfo.rotationZ;
      float arYZ = ActiveRenderInfo.rotationYZ;
      float arXY = ActiveRenderInfo.rotationXY;
      float arXZ = ActiveRenderInfo.rotationXZ;
      EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
      double iPX = player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks;
      double iPY = player.prevPosY + (player.posY - player.prevPosY) * (double)partialTicks;
      double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks;
      GL11.glTranslated(-iPX, -iPY, -iPZ);
      tessellator.startDrawingQuads();
      tessellator.setBrightness(220);
      tessellator.setColorRGBA_I(color, (int)(alpha * 255.0F));
      Vec3 v1 = Vec3.createVectorHelper((double)(-arX * scale - arYZ * scale), (double)(-arXZ * scale), (double)(-arZ * scale - arXY * scale));
      Vec3 v2 = Vec3.createVectorHelper((double)(-arX * scale + arYZ * scale), (double)(arXZ * scale), (double)(-arZ * scale + arXY * scale));
      Vec3 v3 = Vec3.createVectorHelper((double)(arX * scale + arYZ * scale), (double)(arXZ * scale), (double)(arZ * scale + arXY * scale));
      Vec3 v4 = Vec3.createVectorHelper((double)(arX * scale - arYZ * scale), (double)(-arXZ * scale), (double)(arZ * scale - arXY * scale));
      if(angle != 0.0F) {
         Vec3 f2 = Vec3.createVectorHelper(iPX, iPY, iPZ);
         Vec3 f3 = Vec3.createVectorHelper(px, py, pz);
         Vec3 f4 = f2.subtract(f3).normalize();
         QuadHelper.setAxis(f4, (double)angle).rotate(v1);
         QuadHelper.setAxis(f4, (double)angle).rotate(v2);
         QuadHelper.setAxis(f4, (double)angle).rotate(v3);
         QuadHelper.setAxis(f4, (double)angle).rotate(v4);
      }

      float f21 = 0.0F;
      float f31 = 1.0F;
      float f41 = 0.0F;
      float f5 = 1.0F;
      tessellator.setNormal(0.0F, 0.0F, -1.0F);
      tessellator.addVertexWithUV(px + v1.xCoord, py + v1.yCoord, pz + v1.zCoord, (double)f21, (double)f5);
      tessellator.addVertexWithUV(px + v2.xCoord, py + v2.yCoord, pz + v2.zCoord, (double)f31, (double)f5);
      tessellator.addVertexWithUV(px + v3.xCoord, py + v3.yCoord, pz + v3.zCoord, (double)f31, (double)f41);
      tessellator.addVertexWithUV(px + v4.xCoord, py + v4.yCoord, pz + v4.zCoord, (double)f21, (double)f41);
      tessellator.draw();
   }

}
