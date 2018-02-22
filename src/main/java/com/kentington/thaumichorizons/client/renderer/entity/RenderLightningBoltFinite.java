package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntityLightningBoltFinite;
import java.util.Random;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLightningBolt;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderLightningBoltFinite extends RenderLightningBolt {

   public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {}

   public void doRender(EntityLightningBoltFinite p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      Tessellator tessellator = Tessellator.instance;
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 1);
      double[] adouble = new double[8];
      double[] adouble1 = new double[8];
      double d3 = 0.0D;
      double d4 = 0.0D;
      Random random = new Random(p_76986_1_.boltVertex);
      double mult = (double)p_76986_1_.boltLength / 128.0D;

      int k1;
      for(k1 = 7; k1 >= 0; --k1) {
         adouble[k1] = d3;
         adouble1[k1] = d4;
         d3 += (double)(random.nextInt(11) - 5) * mult;
         d4 += (double)(random.nextInt(11) - 5) * mult;
      }

      for(k1 = 0; k1 < 4; ++k1) {
         Random random1 = new Random(p_76986_1_.boltVertex);

         for(int j = 0; j < 3; ++j) {
            int k = 7;
            int l = 0;
            if(j > 0) {
               k = 7 - j;
            }

            if(j > 0) {
               l = k - 2;
            }

            double d5 = adouble[k] - d3;
            double d6 = adouble1[k] - d4;

            for(int i1 = k; i1 >= l; --i1) {
               double d7 = d5;
               double d8 = d6;
               if(j == 0) {
                  d5 += (double)(random1.nextInt(11) - 5) * mult;
                  d6 += (double)(random1.nextInt(11) - 5) * mult;
               } else {
                  d5 += (double)(random1.nextInt(31) - 15) * mult;
                  d6 += (double)(random1.nextInt(31) - 15) * mult;
               }

               tessellator.startDrawing(5);
               float f2 = 0.5F;
               tessellator.setColorRGBA_F(0.9F * f2, 0.9F * f2, 1.0F * f2, 0.3F);
               double d9 = 0.1D + (double)k1 * 0.2D;
               if(j == 0) {
                  d9 *= (double)i1 * 0.1D + 1.0D;
               }

               double d10 = 0.1D + (double)k1 * 0.2D;
               if(j == 0) {
                  d10 *= (double)(i1 - 1) * 0.1D + 1.0D;
               }

               for(int j1 = 0; j1 < 5; ++j1) {
                  double d11 = p_76986_2_ + 0.5D - d9;
                  double d12 = p_76986_6_ + 0.5D - d9;
                  if(j1 == 1 || j1 == 2) {
                     d11 += d9 * 2.0D;
                  }

                  if(j1 == 2 || j1 == 3) {
                     d12 += d9 * 2.0D;
                  }

                  double d13 = p_76986_2_ + 0.5D - d10;
                  double d14 = p_76986_6_ + 0.5D - d10;
                  if(j1 == 1 || j1 == 2) {
                     d13 += d10 * 2.0D;
                  }

                  if(j1 == 2 || j1 == 3) {
                     d14 += d10 * 2.0D;
                  }

                  tessellator.addVertex(d13 + d5, p_76986_4_ + (double)p_76986_1_.boltLength / (double)(k - l + 1) * (double)i1 / 2.0D, d14 + d6);
                  tessellator.addVertex(d11 + d7, p_76986_4_ + (double)p_76986_1_.boltLength / (double)(k - l + 1) * (double)(i1 + 1) / 2.0D, d12 + d8);
               }

               tessellator.draw();
            }
         }
      }

      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
   }
}
