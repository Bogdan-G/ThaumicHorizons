package com.kentington.thaumichorizons.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;

public class RenderScholarChicken extends RenderChicken {

   ResourceLocation rl = new ResourceLocation("thaumichorizons", "textures/entity/scholarchicken.png");


   public RenderScholarChicken(ModelBase p_i1252_1_, float p_i1252_2_) {
      super(p_i1252_1_, p_i1252_2_);
   }

   protected ResourceLocation getEntityTexture(EntityChicken p_110775_1_) {
      return this.rl;
   }
}
