package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TilePortalTH;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TilePortalTHRender extends TileEntitySpecialRenderer {

   public static final ResourceLocation portaltex = new ResourceLocation("thaumichorizons", "textures/misc/vortex.png");


   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      if(te.getWorldObj() != null) {
         this.renderPortal((TilePortalTH)te, x, y, z, f);
      }

   }

   private void renderPortal(TilePortalTH te, double x, double y, double z, float f) {
      long nt = System.nanoTime();
      long time = nt / 50000000L;
      int c = (int)Math.min(30.0F, (float)te.opencount + f);
      int e = (int)Math.min(5.0F, (float)te.opencount + f);
      float scale = (float)e / 5.0F;
      float scaley = (float)c / 30.0F;
      UtilsFX.bindTexture(portaltex);
      GL11.glPushMatrix();
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 0.0F, 1.0F, 1.0F);
      if(Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
         Tessellator tessellator = Tessellator.instance;
         float arX = ActiveRenderInfo.rotationX;
         float arZ = ActiveRenderInfo.rotationZ;
         float arYZ = ActiveRenderInfo.rotationYZ;
         float arXY = ActiveRenderInfo.rotationXY;
         float arXZ = ActiveRenderInfo.rotationXZ;
         EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
         double var10000 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
         var10000 = player.prevPosY + (player.posY - player.prevPosY) * (double)f;
         var10000 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
         tessellator.startDrawingQuads();
         tessellator.setBrightness(220);
         switch(te.dimension) {
         case -1:
            tessellator.setColorRGBA_F(1.0F, 0.1F, 0.1F, 1.0F);
            break;
         case 0:
            tessellator.setColorRGBA_F(0.1F, 0.8F, 0.8F, 1.0F);
            break;
         case 1:
            tessellator.setColorRGBA_F(0.25F, 0.25F, 0.25F, 1.0F);
            break;
         default:
            tessellator.setColorRGBA_F(0.5F, 0.5F, 0.1F, 1.0F);
         }

         double px = x + 0.5D;
         double py = y + 0.5D;
         double pz = z + 0.5D;
         Vec3 v1 = Vec3.createVectorHelper((double)(-arX - arYZ), (double)(-arXZ), (double)(-arZ - arXY));
         Vec3 v2 = Vec3.createVectorHelper((double)(-arX + arYZ), (double)arXZ, (double)(-arZ + arXY));
         Vec3 v3 = Vec3.createVectorHelper((double)(arX + arYZ), (double)arXZ, (double)(arZ + arXY));
         Vec3 v4 = Vec3.createVectorHelper((double)(arX - arYZ), (double)(-arXZ), (double)(arZ - arXY));
         int frame = (int)time % 16;
         float f2 = (float)frame / 16.0F;
         float f3 = f2 + 0.0625F;
         float f4 = 0.0F;
         float f5 = 1.0F;
         tessellator.setNormal(0.0F, 0.0F, -1.0F);
         tessellator.addVertexWithUV(px + v1.xCoord * (double)scale, py + v1.yCoord * (double)scaley, pz + v1.zCoord * (double)scale, (double)f2, (double)f5);
         tessellator.addVertexWithUV(px + v2.xCoord * (double)scale, py + v2.yCoord * (double)scaley, pz + v2.zCoord * (double)scale, (double)f3, (double)f5);
         tessellator.addVertexWithUV(px + v3.xCoord * (double)scale, py + v3.yCoord * (double)scaley, pz + v3.zCoord * (double)scale, (double)f3, (double)f4);
         tessellator.addVertexWithUV(px + v4.xCoord * (double)scale, py + v4.yCoord * (double)scaley, pz + v4.zCoord * (double)scale, (double)f2, (double)f4);
         tessellator.draw();
      }

      GL11.glDisable(3042);
      GL11.glDepthMask(true);
      GL11.glPopMatrix();
   }

}
