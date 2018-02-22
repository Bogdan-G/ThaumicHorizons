package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelVat extends ModelBase {

   ModelRenderer Base;
   ModelRenderer Water;
   ModelRenderer Glass;
   ModelRenderer Top;
   ModelRenderer Hatch;


   public ModelVat() {
      super.textureWidth = 512;
      super.textureHeight = 512;
      this.Base = new ModelRenderer(this, 0, 0);
      this.Base.addBox(0.0F, 0.0F, 0.0F, 48, 16, 48);
      this.Base.setRotationPoint(-24.0F, 8.0F, -24.0F);
      this.Base.setTextureSize(512, 512);
      this.Base.mirror = true;
      this.setRotation(this.Base, 0.0F, 0.0F, 0.0F);
      this.Water = new ModelRenderer(this, 256, 0);
      this.Water.addBox(0.0F, 0.0F, 0.0F, 40, 32, 40);
      this.Water.setRotationPoint(-20.0F, -24.0F, -20.0F);
      this.Water.setTextureSize(512, 512);
      this.Water.mirror = true;
      this.setRotation(this.Water, 0.0F, 0.0F, 0.0F);
      this.Glass = new ModelRenderer(this, 0, 128);
      this.Glass.addBox(0.0F, 0.0F, 0.0F, 48, 32, 48);
      this.Glass.setRotationPoint(-24.0F, -24.0F, -24.0F);
      this.Glass.setTextureSize(512, 512);
      this.Glass.mirror = true;
      this.setRotation(this.Glass, 0.0F, 0.0F, 0.0F);
      this.Top = new ModelRenderer(this, 256, 128);
      this.Top.addBox(0.0F, 0.0F, 0.0F, 48, 14, 48);
      this.Top.setRotationPoint(-24.0F, -38.0F, -24.0F);
      this.Top.setTextureSize(512, 512);
      this.Top.mirror = true;
      this.setRotation(this.Top, 0.0F, 0.0F, 0.0F);
      this.Hatch = new ModelRenderer(this, 0, 80);
      this.Hatch.addBox(0.0F, 0.0F, 0.0F, 16, 2, 16);
      this.Hatch.setRotationPoint(-8.0F, -40.0F, -8.0F);
      this.Hatch.setTextureSize(512, 512);
      this.Hatch.mirror = true;
      this.setRotation(this.Hatch, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.Water.render(f5);
      this.Glass.render(f5);
      this.Top.render(f5);
      this.Hatch.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
