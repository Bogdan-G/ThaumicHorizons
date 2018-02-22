package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSoulforge extends ModelBase {

   ModelRenderer BrainTank;
   ModelRenderer Neck;
   ModelRenderer SoulTank;
   ModelRenderer Rod1;
   ModelRenderer Rod2;
   ModelRenderer Rod3;
   ModelRenderer Rod4;
   public ModelRenderer Brine;


   public ModelSoulforge() {
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.BrainTank = new ModelRenderer(this, 0, 0);
      this.BrainTank.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16);
      this.BrainTank.setRotationPoint(-8.0F, 16.0F, -8.0F);
      this.BrainTank.setTextureSize(64, 64);
      this.BrainTank.mirror = true;
      this.setRotation(this.BrainTank, 0.0F, 0.0F, 0.0F);
      this.Neck = new ModelRenderer(this, 0, 24);
      this.Neck.addBox(0.0F, 0.0F, 0.0F, 5, 2, 5);
      this.Neck.setRotationPoint(-2.5F, 14.0F, -2.5F);
      this.Neck.setTextureSize(64, 64);
      this.Neck.mirror = true;
      this.setRotation(this.Neck, 0.0F, 0.0F, 0.0F);
      this.SoulTank = new ModelRenderer(this, 0, 48);
      this.SoulTank.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10);
      this.SoulTank.setRotationPoint(-5.0F, 8.0F, -5.0F);
      this.SoulTank.setTextureSize(64, 64);
      this.SoulTank.mirror = true;
      this.setRotation(this.SoulTank, 0.0F, 0.0F, 0.0F);
      this.Rod1 = new ModelRenderer(this, 0, 31);
      this.Rod1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Rod1.setRotationPoint(-8.0F, 12.0F, -8.0F);
      this.Rod1.setTextureSize(64, 64);
      this.Rod1.mirror = true;
      this.setRotation(this.Rod1, 0.0F, 0.0F, 0.0F);
      this.Rod2 = new ModelRenderer(this, 0, 31);
      this.Rod2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Rod2.setRotationPoint(-8.0F, 12.0F, 7.0F);
      this.Rod2.setTextureSize(64, 64);
      this.Rod2.mirror = true;
      this.setRotation(this.Rod2, 0.0F, 0.0F, 0.0F);
      this.Rod3 = new ModelRenderer(this, 0, 31);
      this.Rod3.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Rod3.setRotationPoint(7.0F, 12.0F, 7.0F);
      this.Rod3.setTextureSize(64, 64);
      this.Rod3.mirror = true;
      this.setRotation(this.Rod3, 0.0F, 0.0F, 0.0F);
      this.Rod4 = new ModelRenderer(this, 0, 31);
      this.Rod4.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Rod4.setRotationPoint(7.0F, 12.0F, -8.0F);
      this.Rod4.setTextureSize(64, 64);
      this.Rod4.mirror = true;
      this.setRotation(this.Rod4, 0.0F, 0.0F, 0.0F);
      this.Brine = new ModelRenderer(this, 0, 0);
      this.Brine.addBox(-7.0F, 17.0F, -7.0F, 14, 6, 14);
      this.Brine.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Brine.setTextureSize(64, 32);
      this.Brine.mirror = true;
      this.setRotation(this.Brine, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.BrainTank.render(f5);
      this.Neck.render(f5);
      this.SoulTank.render(f5);
      this.Rod1.render(f5);
      this.Rod2.render(f5);
      this.Rod3.render(f5);
      this.Rod4.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
   }

   public void renderBrine() {
      this.Brine.render(0.0625F);
   }
}
