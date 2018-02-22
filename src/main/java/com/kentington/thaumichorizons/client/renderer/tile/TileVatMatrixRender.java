package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileVat;
import com.kentington.thaumichorizons.common.tiles.TileVatMatrix;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.models.ModelCube;
import thaumcraft.codechicken.lib.math.MathHelper;

@SideOnly(Side.CLIENT)
public class TileVatMatrixRender extends TileEntitySpecialRenderer {

   private ModelCube model = new ModelCube(0);
   private ModelCube model_over = new ModelCube(32);
   int type = 0;


   public TileVatMatrixRender(int type) {
      this.type = type;
   }

   private void drawHalo(TileEntity is, double x, double y, double z, float par8, int count) {
      GL11.glPushMatrix();
      GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
      int q = !FMLClientHandler.instance().getClient().gameSettings.fancyGraphics?10:20;
      Tessellator tessellator = Tessellator.instance;
      RenderHelper.disableStandardItemLighting();
      float f1 = (float)count / 500.0F;
      float f3 = 0.9F;
      float f2 = 0.0F;
      Random random = new Random(245L);
      GL11.glDisable(3553);
      GL11.glShadeModel(7425);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 1);
      GL11.glDisable(3008);
      GL11.glEnable(2884);
      GL11.glDepthMask(false);
      GL11.glPushMatrix();

      for(int i = 0; i < q; ++i) {
         GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 360.0F, 0.0F, 0.0F, 1.0F);
         tessellator.startDrawing(6);
         float fa = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
         float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
         fa /= 20.0F / ((float)Math.min(count, 50) / 50.0F);
         f4 /= 20.0F / ((float)Math.min(count, 50) / 50.0F);
         tessellator.setColorRGBA_I(16777215, (int)(255.0F * (1.0F - f2)));
         tessellator.addVertex(0.0D, 0.0D, 0.0D);
         tessellator.setColorRGBA_I(13369599, 0);
         tessellator.addVertex(-0.866D * (double)f4, (double)fa, (double)(-0.5F * f4));
         tessellator.addVertex(0.866D * (double)f4, (double)fa, (double)(-0.5F * f4));
         tessellator.addVertex(0.0D, (double)fa, (double)(1.0F * f4));
         tessellator.addVertex(-0.866D * (double)f4, (double)fa, (double)(-0.5F * f4));
         tessellator.draw();
      }

      GL11.glPopMatrix();
      GL11.glDepthMask(true);
      GL11.glDisable(2884);
      GL11.glDisable(3042);
      GL11.glShadeModel(7424);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(3008);
      RenderHelper.enableStandardItemLighting();
      GL11.glBlendFunc(770, 771);
      GL11.glPopMatrix();
   }

   public void renderInfusionMatrix(TileVatMatrix tile, double par2, double par4, double par6, float par8) {
      TileVat vat = tile.getVat();
      GL11.glPushMatrix();
      UtilsFX.bindTexture("textures/models/infuser.png");
      GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.5F, (float)par6 + 0.5F);
      float ticks = (float)Minecraft.getMinecraft().renderViewEntity.ticksExisted + par8;
      float instability = 0.0F;
      float startUp = 0.0F;
      float craftCount = 0.0F;
      if(vat != null) {
         startUp = vat.startUp;
         instability = (float)vat.instability;
         craftCount = (float)vat.craftCount;
      }

      if(tile.getWorldObj() != null) {
         GL11.glRotatef(ticks % 360.0F * startUp, 0.0F, 1.0F, 0.0F);
      }

      instability = Math.min(6.0F, 1.0F + instability * 0.66F * (Math.min(craftCount, 50.0F) / 50.0F));
      float b1 = 0.0F;
      float b2 = 0.0F;
      float b3 = 0.0F;
      boolean aa = false;
      boolean bb = false;
      boolean cc = false;

      int b;
      int a;
      int c;
      int var27;
      int var26;
      int var28;
      for(a = 0; a < 2; ++a) {
         for(b = 0; b < 2; ++b) {
            for(c = 0; c < 2; ++c) {
               b1 = (float)(MathHelper.sin((double)((ticks + (float)(a * 10)) / (15.0F - instability / 2.0F))) * 0.009999999776482582D * (double)startUp * (double)instability);
               b2 = (float)(MathHelper.sin((double)((ticks + (float)(b * 10)) / (14.0F - instability / 2.0F))) * 0.009999999776482582D * (double)startUp * (double)instability);
               b3 = (float)(MathHelper.sin((double)((ticks + (float)(c * 10)) / (13.0F - instability / 2.0F))) * 0.009999999776482582D * (double)startUp * (double)instability);
               var26 = a == 0?-1:1;
               var28 = b == 0?-1:1;
               var27 = c == 0?-1:1;
               GL11.glPushMatrix();
               GL11.glTranslatef(b1 + (float)var26 * 0.25F, b2 + (float)var28 * 0.25F, b3 + (float)var27 * 0.25F);
               if(a > 0) {
                  GL11.glRotatef(90.0F, (float)a, 0.0F, 0.0F);
               }

               if(b > 0) {
                  GL11.glRotatef(90.0F, 0.0F, (float)b, 0.0F);
               }

               if(c > 0) {
                  GL11.glRotatef(90.0F, 0.0F, 0.0F, (float)c);
               }

               GL11.glScaled(0.45D, 0.45D, 0.45D);
               this.model.render();
               GL11.glPopMatrix();
            }
         }
      }

      GL11.glPushMatrix();
      GL11.glAlphaFunc(516, 0.003921569F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 1);

      for(a = 0; a < 2; ++a) {
         for(b = 0; b < 2; ++b) {
            for(c = 0; c < 2; ++c) {
               b1 = (float)(MathHelper.sin((double)((ticks + (float)(a * 10)) / (15.0F - instability / 2.0F))) * 0.009999999776482582D * (double)startUp * (double)instability);
               b2 = (float)(MathHelper.sin((double)((ticks + (float)(b * 10)) / (14.0F - instability / 2.0F))) * 0.009999999776482582D * (double)startUp * (double)instability);
               b3 = (float)(MathHelper.sin((double)((ticks + (float)(c * 10)) / (13.0F - instability / 2.0F))) * 0.009999999776482582D * (double)startUp * (double)instability);
               var26 = a == 0?-1:1;
               var28 = b == 0?-1:1;
               var27 = c == 0?-1:1;
               GL11.glPushMatrix();
               GL11.glTranslatef(b1 + (float)var26 * 0.25F, b2 + (float)var28 * 0.25F, b3 + (float)var27 * 0.25F);
               if(a > 0) {
                  GL11.glRotatef(90.0F, (float)a, 0.0F, 0.0F);
               }

               if(b > 0) {
                  GL11.glRotatef(90.0F, 0.0F, (float)b, 0.0F);
               }

               if(c > 0) {
                  GL11.glRotatef(90.0F, 0.0F, 0.0F, (float)c);
               }

               GL11.glScaled(0.45D, 0.45D, 0.45D);
               int j = 15728880;
               int k = j % 65536;
               int l = j / 65536;
               OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
               GL11.glColor4f(0.8F, 0.1F, 1.0F, (float)((MathHelper.sin((double)((ticks + (float)(a * 2) + (float)(b * 3) + (float)(c * 4)) / 4.0F)) * 0.10000000149011612D + 0.20000000298023224D) * (double)startUp));
               this.model_over.render();
               GL11.glPopMatrix();
            }
         }
      }

      GL11.glDisable(3042);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glPopMatrix();
      GL11.glPopMatrix();
      if(vat != null && vat.mode == 2) {
         this.drawHalo(vat, par2, par4, par6, par8, vat.craftCount);
      }

   }

   public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
      this.renderInfusionMatrix((TileVatMatrix)par1TileEntity, par2, par4, par6, par8);
   }
}
