package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntitySheeder;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderSheeder extends RenderSpider {

   private static final ResourceLocation spiderTextures = new ResourceLocation("thaumichorizons", "textures/entity/sheeder.png");
   private static final ResourceLocation spiderEyesTextures = new ResourceLocation("thaumichorizons", "textures/entity/sheederoverlay.png");


   protected ResourceLocation getEntityTexture(EntitySpider p_110775_1_) {
      return spiderTextures;
   }

   protected int shouldRenderPass(EntitySpider p_77032_1_, int p_77032_2_, float p_77032_3_) {
      if(p_77032_2_ != 0) {
         return -1;
      } else if(!((EntitySheeder)p_77032_1_).getSheared()) {
         this.bindTexture(spiderEyesTextures);
         return 1;
      } else {
         return -1;
      }
   }

}
