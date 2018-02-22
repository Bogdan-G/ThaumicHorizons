package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntityNetherHound;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderNetherHound extends RenderWolf {

   ResourceLocation wolfTex = new ResourceLocation("thaumichorizons", "textures/entity/netherhound.png");


   public RenderNetherHound(ModelBase p_i1269_1_, ModelBase p_i1269_2_, float p_i1269_3_) {
      super(p_i1269_1_, p_i1269_2_, p_i1269_3_);
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntityNetherHound)p_110775_1_);
   }

   protected ResourceLocation getEntityTexture(EntityWolf p_110775_1_) {
      return this.getEntityTexture((EntityNetherHound)p_110775_1_);
   }

   protected ResourceLocation getEntityTexture(EntityNetherHound p_110775_1_) {
      return this.wolfTex;
   }
}
