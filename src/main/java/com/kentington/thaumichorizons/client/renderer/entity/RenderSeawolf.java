package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntitySeawolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderSeawolf extends RenderWolf {

   ResourceLocation wolfTex = new ResourceLocation("thaumichorizons", "textures/entity/seawolf.png");


   public RenderSeawolf(ModelBase p_i1269_1_, ModelBase p_i1269_2_, float p_i1269_3_) {
      super(p_i1269_1_, p_i1269_2_, p_i1269_3_);
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntitySeawolf)p_110775_1_);
   }

   protected ResourceLocation getEntityTexture(EntityWolf p_110775_1_) {
      return this.getEntityTexture((EntitySeawolf)p_110775_1_);
   }

   protected ResourceLocation getEntityTexture(EntitySeawolf p_110775_1_) {
      return this.wolfTex;
   }
}
