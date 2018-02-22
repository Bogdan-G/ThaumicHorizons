package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelSoulBeacon;
import com.kentington.thaumichorizons.common.tiles.TileSoulBeacon;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileSoulBeaconRender extends TileEntitySpecialRenderer {

   static String tx1 = "textures/models/soulbeacon.png";
   private ModelSoulBeacon base = new ModelSoulBeacon();
   private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");
   private static final String __OBFID = "CL_00000962";


   public void renderTileEntityAt(TileSoulBeacon tco, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)p_147500_2_ + 0.5F, (float)p_147500_4_ + 1.5F, (float)p_147500_6_ + 0.5F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      this.base.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
      if(tco.getWorldObj() != null && tco.getWorldObj().canBlockSeeTheSky(tco.xCoord, tco.yCoord, tco.zCoord)) {
         float f1 = tco.func_146002_i();
         GL11.glAlphaFunc(516, 0.1F);
         if(f1 > 0.0F) {
            Tessellator tessellator = Tessellator.instance;
            this.bindTexture(field_147523_b);
            GL11.glTexParameterf(3553, 10242, 10497.0F);
            GL11.glTexParameterf(3553, 10243, 10497.0F);
            GL11.glDisable(2896);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            float f2 = (float)tco.getWorldObj().getTotalWorldTime() + p_147500_8_;
            float f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
            byte b0 = 1;
            double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(255, 255, 255, 32);
            double d5 = (double)b0 * 0.2D;
            double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
            double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
            double d11 = 0.5D + Math.cos(d3 + 0.7853981633974483D) * d5;
            double d13 = 0.5D + Math.sin(d3 + 0.7853981633974483D) * d5;
            double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
            double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
            double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
            double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
            double d23 = (double)(256.0F * f1);
            double d25 = 0.0D;
            double d27 = 1.0D;
            double d28 = (double)(-1.0F + f3);
            double d29 = (double)(256.0F * f1) * (0.5D / d5) + d28;
            tessellator.addVertexWithUV(p_147500_2_ + d7, p_147500_4_ + d23, p_147500_6_ + d9, d27, d29);
            tessellator.addVertexWithUV(p_147500_2_ + d7, p_147500_4_, p_147500_6_ + d9, d27, d28);
            tessellator.addVertexWithUV(p_147500_2_ + d11, p_147500_4_, p_147500_6_ + d13, d25, d28);
            tessellator.addVertexWithUV(p_147500_2_ + d11, p_147500_4_ + d23, p_147500_6_ + d13, d25, d29);
            tessellator.addVertexWithUV(p_147500_2_ + d19, p_147500_4_ + d23, p_147500_6_ + d21, d27, d29);
            tessellator.addVertexWithUV(p_147500_2_ + d19, p_147500_4_, p_147500_6_ + d21, d27, d28);
            tessellator.addVertexWithUV(p_147500_2_ + d15, p_147500_4_, p_147500_6_ + d17, d25, d28);
            tessellator.addVertexWithUV(p_147500_2_ + d15, p_147500_4_ + d23, p_147500_6_ + d17, d25, d29);
            tessellator.addVertexWithUV(p_147500_2_ + d11, p_147500_4_ + d23, p_147500_6_ + d13, d27, d29);
            tessellator.addVertexWithUV(p_147500_2_ + d11, p_147500_4_, p_147500_6_ + d13, d27, d28);
            tessellator.addVertexWithUV(p_147500_2_ + d19, p_147500_4_, p_147500_6_ + d21, d25, d28);
            tessellator.addVertexWithUV(p_147500_2_ + d19, p_147500_4_ + d23, p_147500_6_ + d21, d25, d29);
            tessellator.addVertexWithUV(p_147500_2_ + d15, p_147500_4_ + d23, p_147500_6_ + d17, d27, d29);
            tessellator.addVertexWithUV(p_147500_2_ + d15, p_147500_4_, p_147500_6_ + d17, d27, d28);
            tessellator.addVertexWithUV(p_147500_2_ + d7, p_147500_4_, p_147500_6_ + d9, d25, d28);
            tessellator.addVertexWithUV(p_147500_2_ + d7, p_147500_4_ + d23, p_147500_6_ + d9, d25, d29);
            tessellator.draw();
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDepthMask(false);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(255, 255, 255, 32);
            double d30 = 0.2D;
            double d4 = 0.2D;
            double d6 = 0.8D;
            double d8 = 0.2D;
            double d10 = 0.2D;
            double d12 = 0.8D;
            double d14 = 0.8D;
            double d16 = 0.8D;
            double d18 = (double)(256.0F * f1);
            double d20 = 0.0D;
            double d22 = 1.0D;
            double d24 = (double)(-1.0F + f3);
            double d26 = (double)(256.0F * f1) + d24;
            tessellator.addVertexWithUV(p_147500_2_ + d30, p_147500_4_ + d18, p_147500_6_ + d4, d22, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d30, p_147500_4_, p_147500_6_ + d4, d22, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d6, p_147500_4_, p_147500_6_ + d8, d20, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d6, p_147500_4_ + d18, p_147500_6_ + d8, d20, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d14, p_147500_4_ + d18, p_147500_6_ + d16, d22, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d14, p_147500_4_, p_147500_6_ + d16, d22, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d10, p_147500_4_, p_147500_6_ + d12, d20, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d10, p_147500_4_ + d18, p_147500_6_ + d12, d20, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d6, p_147500_4_ + d18, p_147500_6_ + d8, d22, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d6, p_147500_4_, p_147500_6_ + d8, d22, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d14, p_147500_4_, p_147500_6_ + d16, d20, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d14, p_147500_4_ + d18, p_147500_6_ + d16, d20, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d10, p_147500_4_ + d18, p_147500_6_ + d12, d22, d26);
            tessellator.addVertexWithUV(p_147500_2_ + d10, p_147500_4_, p_147500_6_ + d12, d22, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d30, p_147500_4_, p_147500_6_ + d4, d20, d24);
            tessellator.addVertexWithUV(p_147500_2_ + d30, p_147500_4_ + d18, p_147500_6_ + d4, d20, d26);
            tessellator.draw();
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glDepthMask(true);
         }

      }
   }

   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      this.renderTileEntityAt((TileSoulBeacon)te, x, y, z, f);
   }

}
