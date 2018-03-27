package com.kentington.thaumichorizons.common.lib;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class NightmareTeleporter extends Teleporter {

   int dim;
   private WorldServer worldServerInstance;
   private Random random;


   public NightmareTeleporter(WorldServer p_i1963_1_) {
      super(p_i1963_1_);
      this.worldServerInstance = p_i1963_1_;
      this.random = new org.bogdang.modifications.random.XSTR(p_i1963_1_.getSeed());
   }

   public void placeInPortal(Entity p_77185_1_, double p_77185_2_, double p_77185_4_, double p_77185_6_, float p_77185_8_) {
      this.moveToHole(p_77185_1_);
   }

   public void moveToHole(Entity p_85188_1_) {
      byte b0 = 16;
      double d0 = -1.0D;
      int i = MathHelper.floor_double(p_85188_1_.posX);
      int j = MathHelper.floor_double(p_85188_1_.posY);
      int k = MathHelper.floor_double(p_85188_1_.posZ);
      int l = i;
      int i1 = j;
      int j1 = k;
      int k1 = 0;
      int l1 = this.random.nextInt(4);

      int i2;
      double d1;
      double d2;
      int k2;
      int i3;
      int k3;
      int j3;
      int i4;
      int l3;
      int k4;
      int j4;
      int i5;
      int l4;
      double d3;
      double d4;
      for(i2 = i - b0; i2 <= i + b0; ++i2) {
         d1 = (double)i2 + 0.5D - p_85188_1_.posX;

         for(k2 = k - b0; k2 <= k + b0; ++k2) {
            d2 = (double)k2 + 0.5D - p_85188_1_.posZ;

            label171:
            for(i3 = this.worldServerInstance.getActualHeight() - 1; i3 >= 0; --i3) {
               if(this.worldServerInstance.isAirBlock(i2, i3, k2)) {
                  while(i3 > 0 && this.worldServerInstance.isAirBlock(i2, i3 - 1, k2)) {
                     --i3;
                  }

                  for(j3 = l1; j3 < l1 + 4; ++j3) {
                     k3 = j3 % 2;
                     l3 = 1 - k3;
                     if(j3 % 4 >= 2) {
                        k3 = -k3;
                        l3 = -l3;
                     }

                     for(i4 = 0; i4 < 3; ++i4) {
                        for(j4 = 0; j4 < 4; ++j4) {
                           for(k4 = -1; k4 < 4; ++k4) {
                              l4 = i2 + (j4 - 1) * k3 + i4 * l3;
                              i5 = i3 + k4;
                              int k5 = k2 + (j4 - 1) * l3 - i4 * k3;
                              if(k4 < 0 && !this.worldServerInstance.getBlock(l4, i5, k5).getMaterial().isSolid() || k4 >= 0 && !this.worldServerInstance.isAirBlock(l4, i5, k5)) {
                                 continue label171;
                              }
                           }
                        }
                     }

                     d3 = (double)i3 + 0.5D - p_85188_1_.posY;
                     d4 = d1 * d1 + d3 * d3 + d2 * d2;
                     if(d0 < 0.0D || d4 < d0) {
                        d0 = d4;
                        l = i2;
                        i1 = i3;
                        j1 = k2;
                        k1 = j3 % 4;
                     }
                  }
               }
            }
         }
      }

      if(d0 < 0.0D) {
         for(i2 = i - b0; i2 <= i + b0; ++i2) {
            d1 = (double)i2 + 0.5D - p_85188_1_.posX;

            for(k2 = k - b0; k2 <= k + b0; ++k2) {
               d2 = (double)k2 + 0.5D - p_85188_1_.posZ;

               label119:
               for(i3 = this.worldServerInstance.getActualHeight() - 1; i3 >= 0; --i3) {
                  if(this.worldServerInstance.isAirBlock(i2, i3, k2)) {
                     while(i3 > 0 && this.worldServerInstance.isAirBlock(i2, i3 - 1, k2)) {
                        --i3;
                     }

                     for(j3 = l1; j3 < l1 + 2; ++j3) {
                        k3 = j3 % 2;
                        l3 = 1 - k3;

                        for(i4 = 0; i4 < 4; ++i4) {
                           for(j4 = -1; j4 < 4; ++j4) {
                              k4 = i2 + (i4 - 1) * k3;
                              l4 = i3 + j4;
                              i5 = k2 + (i4 - 1) * l3;
                              if(j4 < 0 && !this.worldServerInstance.getBlock(k4, l4, i5).getMaterial().isSolid() || j4 >= 0 && !this.worldServerInstance.isAirBlock(k4, l4, i5)) {
                                 continue label119;
                              }
                           }
                        }

                        d3 = (double)i3 + 0.5D - p_85188_1_.posY;
                        d4 = d1 * d1 + d3 * d3 + d2 * d2;
                        if(d0 < 0.0D || d4 < d0) {
                           d0 = d4;
                           l = i2;
                           i1 = i3;
                           j1 = k2;
                           k1 = j3 % 2;
                        }
                     }
                  }
               }
            }
         }
      }

      int l5 = k1 % 2;
      int l2 = 1 - l5;
      if(k1 % 4 >= 2) {
         l5 = -l5;
         l2 = -l2;
      }

      p_85188_1_.setPosition((double)l, (double)i1, (double)j1);
   }
}
