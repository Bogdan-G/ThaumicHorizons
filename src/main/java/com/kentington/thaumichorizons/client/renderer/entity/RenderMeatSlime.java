package com.kentington.thaumichorizons.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderMeatSlime extends RenderSlime {

   private static final ResourceLocation slimeTextures = new ResourceLocation("thaumichorizons", "textures/entity/meatslime.png");


   protected ResourceLocation getEntityTexture(EntitySlime p_110775_1_) {
      return slimeTextures;
   }

   public RenderMeatSlime(ModelBase p_i1267_1_, ModelBase p_i1267_2_, float p_i1267_3_) {
      super(p_i1267_1_, p_i1267_2_, p_i1267_3_);
   }

}
