package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntityBoatGreatwood;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBoatGreatwood extends RenderBoat {

   private static final ResourceLocation boatTextures = new ResourceLocation("thaumichorizons", "textures/entity/boatgreatwood.png");


   protected ResourceLocation getEntityTexture(EntityBoatGreatwood p_110775_1_) {
      return boatTextures;
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntityBoatGreatwood)p_110775_1_);
   }

}
