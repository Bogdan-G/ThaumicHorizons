package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFamiliarHat extends ModelBase {

   ModelRenderer HatBase;
   ModelRenderer HatA;
   ModelRenderer HatB;
   ModelRenderer HatC;
   ModelRenderer GoldBuckle;


   public ModelFamiliarHat() {
      super.textureWidth = 32;
      super.textureHeight = 32;
      this.HatBase = new ModelRenderer(this, 0, 0);
      this.HatBase.addBox(0.0F, 0.0F, 0.0F, 7, 1, 7);
      this.HatBase.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.HatBase.setTextureSize(32, 32);
      this.HatBase.mirror = true;
      this.setRotation(this.HatBase, 0.0F, 0.0F, 0.0F);
      this.HatA = new ModelRenderer(this, 0, 8);
      this.HatA.addBox(0.0F, -1.0F, 1.0F, 5, 2, 5);
      this.HatA.setRotationPoint(1.0F, -1.0F, 0.0F);
      this.HatA.setTextureSize(32, 32);
      this.HatA.mirror = true;
      this.setRotation(this.HatA, 0.0F, 0.0F, 0.0F);
      this.HatB = new ModelRenderer(this, 0, 15);
      this.HatB.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3);
      this.HatB.setRotationPoint(2.0F, -4.0F, 2.0F);
      this.HatB.setTextureSize(32, 32);
      this.HatB.mirror = true;
      this.setRotation(this.HatB, 0.0F, 0.0F, 0.0F);
      this.HatC = new ModelRenderer(this, 0, 20);
      this.HatC.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.HatC.setRotationPoint(3.0F, -5.0F, 3.0F);
      this.HatC.setTextureSize(32, 32);
      this.HatC.mirror = true;
      this.setRotation(this.HatC, 0.0F, 0.0F, 0.0F);
      this.GoldBuckle = new ModelRenderer(this, 0, 22);
      this.GoldBuckle.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.GoldBuckle.setRotationPoint(0.0F, -1.0F, 3.0F);
      this.GoldBuckle.setTextureSize(32, 32);
      this.GoldBuckle.mirror = true;
      this.setRotation(this.GoldBuckle, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.HatBase.render(f5);
      this.HatA.render(f5);
      this.HatB.render(f5);
      this.HatC.render(f5);
      this.GoldBuckle.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
