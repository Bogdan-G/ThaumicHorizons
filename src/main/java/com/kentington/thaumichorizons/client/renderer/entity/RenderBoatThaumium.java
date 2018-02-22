package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntityBoatThaumium;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBoatThaumium extends RenderBoat {

   private static final ResourceLocation boatTextures = new ResourceLocation("thaumichorizons", "textures/entity/boatthaumium.png");


   protected ResourceLocation getEntityTexture(EntityBoatThaumium p_110775_1_) {
      return boatTextures;
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntityBoatThaumium)p_110775_1_);
   }

}
