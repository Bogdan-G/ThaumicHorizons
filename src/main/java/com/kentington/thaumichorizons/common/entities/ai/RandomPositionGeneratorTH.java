package com.kentington.thaumichorizons.common.entities.ai;

import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RandomPositionGeneratorTH {

   private static Vec3 staticVector = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
   private static final String __OBFID = "CL_00001629";


   public static Vec3 findRandomTarget(EntityLiving p_75463_0_, int p_75463_1_, int p_75463_2_) {
      return findRandomTargetBlock(p_75463_0_, p_75463_1_, p_75463_2_, (Vec3)null);
   }

   public static Vec3 findRandomTargetBlockTowards(EntityLiving p_75464_0_, int p_75464_1_, int p_75464_2_, Vec3 p_75464_3_) {
      staticVector.xCoord = p_75464_3_.xCoord - p_75464_0_.posX;
      staticVector.yCoord = p_75464_3_.yCoord - p_75464_0_.posY;
      staticVector.zCoord = p_75464_3_.zCoord - p_75464_0_.posZ;
      return findRandomTargetBlock(p_75464_0_, p_75464_1_, p_75464_2_, staticVector);
   }

   public static Vec3 findRandomTargetBlockAwayFrom(EntityLiving p_75461_0_, int p_75461_1_, int p_75461_2_, Vec3 p_75461_3_) {
      staticVector.xCoord = p_75461_0_.posX - p_75461_3_.xCoord;
      staticVector.yCoord = p_75461_0_.posY - p_75461_3_.yCoord;
      staticVector.zCoord = p_75461_0_.posZ - p_75461_3_.zCoord;
      return findRandomTargetBlock(p_75461_0_, p_75461_1_, p_75461_2_, staticVector);
   }

   private static Vec3 findRandomTargetBlock(EntityLiving p_75462_0_, int p_75462_1_, int p_75462_2_, Vec3 p_75462_3_) {
      Random random = p_75462_0_.getRNG();
      boolean flag = false;
      int k = 0;
      int l = 0;
      int i1 = 0;
      float f = -99999.0F;
      boolean flag1 = false;

      for(int l1 = 0; l1 < 10; ++l1) {
         int j1 = random.nextInt(2 * p_75462_1_) - p_75462_1_;
         int i2 = random.nextInt(2 * p_75462_2_) - p_75462_2_;
         int k1 = random.nextInt(2 * p_75462_1_) - p_75462_1_;
         if(p_75462_3_ == null || (double)j1 * p_75462_3_.xCoord + (double)k1 * p_75462_3_.zCoord >= 0.0D) {
            j1 += MathHelper.floor_double(p_75462_0_.posX);
            i2 += MathHelper.floor_double(p_75462_0_.posY);
            k1 += MathHelper.floor_double(p_75462_0_.posZ);
            if(!flag1) {
               k = j1;
               l = i2;
               i1 = k1;
               flag = true;
            }
         }
      }

      if(flag) {
         return Vec3.createVectorHelper((double)k, (double)l, (double)i1);
      } else {
         return null;
      }
   }

}
