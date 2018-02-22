package com.kentington.thaumichorizons.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

public class RenderNightmare extends RenderHorse {

   ResourceLocation enderTex = new ResourceLocation("thaumichorizons", "textures/entity/nightmare.png");
   ResourceLocation enderTexIron = new ResourceLocation("thaumichorizons", "textures/entity/nightmareiron.png");
   ResourceLocation enderTexGold = new ResourceLocation("thaumichorizons", "textures/entity/nightmaregold.png");
   ResourceLocation enderTexDiamond = new ResourceLocation("thaumichorizons", "textures/entity/nightmarediamond.png");
   private static final Map field_110852_a = Maps.newHashMap();


   public RenderNightmare(ModelBase p_i1256_1_, float p_i1256_2_) {
      super(p_i1256_1_, p_i1256_2_);
   }

   protected ResourceLocation getEntityTexture(EntityHorse p_110775_1_) {
      switch(p_110775_1_.func_110241_cb()) {
      case 1:
         return this.enderTexIron;
      case 2:
         return this.enderTexGold;
      case 3:
         return this.enderTexDiamond;
      default:
         return this.enderTex;
      }
   }

}
