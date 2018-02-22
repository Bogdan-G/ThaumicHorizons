package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelQuarterBlock extends ModelBase {

   public ModelRenderer block = new ModelRenderer(this, 0, 0);


   public ModelQuarterBlock() {
      this.block.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
      this.block.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.block.setTextureSize(64, 32);
      this.block.mirror = true;
      super.textureWidth = 64;
      super.textureHeight = 32;
   }

   public void render() {
      this.block.render(0.0625F);
   }
}
