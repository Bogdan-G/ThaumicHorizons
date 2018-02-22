package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelInspiratron extends ModelBase {

   ModelRenderer Jar;
   ModelRenderer BottomA;
   ModelRenderer Bottom1B;
   ModelRenderer Top;


   public ModelInspiratron() {
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.Jar = new ModelRenderer(this, 0, 0);
      this.Jar.addBox(0.0F, 0.0F, 0.0F, 10, 14, 10);
      this.Jar.setRotationPoint(-5.0F, 11.0F, -5.0F);
      this.Jar.setTextureSize(64, 64);
      this.Jar.mirror = true;
      this.setRotation(this.Jar, 0.0F, 0.0F, 0.0F);
      this.BottomA = new ModelRenderer(this, 0, 39);
      this.BottomA.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
      this.BottomA.setRotationPoint(-8.0F, 23.0F, -8.0F);
      this.BottomA.setTextureSize(64, 64);
      this.BottomA.mirror = true;
      this.setRotation(this.BottomA, 0.0F, 0.0F, 0.0F);
      this.Bottom1B = new ModelRenderer(this, 0, 24);
      this.Bottom1B.addBox(0.0F, 0.0F, 0.0F, 12, 3, 12);
      this.Bottom1B.setRotationPoint(-6.0F, 20.0F, -6.0F);
      this.Bottom1B.setTextureSize(64, 64);
      this.Bottom1B.mirror = true;
      this.setRotation(this.Bottom1B, 0.0F, 0.0F, 0.0F);
      this.Top = new ModelRenderer(this, 0, 24);
      this.Top.addBox(0.0F, 0.0F, 0.0F, 12, 1, 12);
      this.Top.setRotationPoint(-6.0F, 10.0F, -6.0F);
      this.Top.setTextureSize(64, 64);
      this.Top.mirror = true;
      this.setRotation(this.Top, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Jar.render(f5);
      this.BottomA.render(f5);
      this.Bottom1B.render(f5);
      this.Top.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
