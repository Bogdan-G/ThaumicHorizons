package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRecombinator extends ModelBase {

   ModelRenderer Base;
   ModelRenderer Pearl;
   ModelRenderer Claw1A;
   ModelRenderer Claw2A;
   ModelRenderer Claw3A;
   ModelRenderer Claw4A;
   ModelRenderer EndA;
   ModelRenderer Shape1;
   ModelRenderer Claw1B;
   ModelRenderer Claw2B;
   ModelRenderer Claw3B;
   ModelRenderer Claw4B;


   public ModelRecombinator() {
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.Base = new ModelRenderer(this, 0, 0);
      this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 4, 16);
      this.Base.setRotationPoint(-8.0F, 20.0F, -8.0F);
      this.Base.setTextureSize(64, 64);
      this.Base.mirror = true;
      this.setRotation(this.Base, 0.0F, 0.0F, 0.0F);
      this.Pearl = new ModelRenderer(this, 0, 20);
      this.Pearl.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Pearl.setRotationPoint(-2.0F, 14.0F, -2.0F);
      this.Pearl.setTextureSize(64, 64);
      this.Pearl.mirror = true;
      this.setRotation(this.Pearl, 0.0F, 0.0F, 0.0F);
      this.Claw1A = new ModelRenderer(this, 0, 28);
      this.Claw1A.addBox(0.0F, 0.0F, 0.0F, 2, 8, 1);
      this.Claw1A.setRotationPoint(-1.0F, 12.0F, 7.0F);
      this.Claw1A.setTextureSize(64, 64);
      this.Claw1A.mirror = true;
      this.setRotation(this.Claw1A, 0.0F, 0.0F, 0.0F);
      this.Claw2A = new ModelRenderer(this, 0, 28);
      this.Claw2A.addBox(0.0F, 0.0F, 0.0F, 2, 8, 1);
      this.Claw2A.setRotationPoint(-1.0F, 12.0F, -8.0F);
      this.Claw2A.setTextureSize(64, 64);
      this.Claw2A.mirror = true;
      this.setRotation(this.Claw2A, 0.0F, 0.0F, 0.0F);
      this.Claw3A = new ModelRenderer(this, 6, 28);
      this.Claw3A.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2);
      this.Claw3A.setRotationPoint(-8.0F, 12.0F, -1.0F);
      this.Claw3A.setTextureSize(64, 64);
      this.Claw3A.mirror = true;
      this.setRotation(this.Claw3A, 0.0F, 0.0F, 0.0F);
      this.Claw4A = new ModelRenderer(this, 6, 28);
      this.Claw4A.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2);
      this.Claw4A.setRotationPoint(7.0F, 12.0F, -1.0F);
      this.Claw4A.setTextureSize(64, 64);
      this.Claw4A.mirror = true;
      this.setRotation(this.Claw4A, 0.0F, 0.0F, 0.0F);
      this.EndA = new ModelRenderer(this, 0, 48);
      this.EndA.addBox(0.0F, 0.0F, 0.0F, 4, 2, 4);
      this.EndA.setRotationPoint(-2.0F, 8.0F, -2.0F);
      this.EndA.setTextureSize(64, 64);
      this.EndA.mirror = true;
      this.setRotation(this.EndA, 0.0F, 0.0F, 0.0F);
      this.Shape1 = new ModelRenderer(this, 0, 38);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 8, 2, 8);
      this.Shape1.setRotationPoint(-4.0F, 10.0F, -4.0F);
      this.Shape1.setTextureSize(64, 64);
      this.Shape1.mirror = true;
      this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
      this.Claw1B = new ModelRenderer(this, 0, 54);
      this.Claw1B.addBox(0.0F, 0.0F, 0.0F, 2, 1, 4);
      this.Claw1B.setRotationPoint(-1.0F, 11.0F, 4.0F);
      this.Claw1B.setTextureSize(64, 64);
      this.Claw1B.mirror = true;
      this.setRotation(this.Claw1B, 0.0F, 0.0F, 0.0F);
      this.Claw2B = new ModelRenderer(this, 0, 54);
      this.Claw2B.addBox(0.0F, 0.0F, 0.0F, 2, 1, 4);
      this.Claw2B.setRotationPoint(-1.0F, 11.0F, -8.0F);
      this.Claw2B.setTextureSize(64, 64);
      this.Claw2B.mirror = true;
      this.setRotation(this.Claw2B, 0.0F, 0.0F, 0.0F);
      this.Claw3B = new ModelRenderer(this, 0, 59);
      this.Claw3B.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
      this.Claw3B.setRotationPoint(4.0F, 11.0F, -1.0F);
      this.Claw3B.setTextureSize(64, 64);
      this.Claw3B.mirror = true;
      this.setRotation(this.Claw3B, 0.0F, 0.0F, 0.0F);
      this.Claw4B = new ModelRenderer(this, 0, 59);
      this.Claw4B.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
      this.Claw4B.setRotationPoint(-8.0F, 11.0F, -1.0F);
      this.Claw4B.setTextureSize(64, 64);
      this.Claw4B.mirror = true;
      this.setRotation(this.Claw4B, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float motion) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5);
      this.Base.render(f5);
      this.Pearl.setRotationPoint(-2.0F, 14.0F + motion, -2.0F);
      this.Pearl.render(f5);
      this.Claw1A.render(f5);
      this.Claw2A.render(f5);
      this.Claw3A.render(f5);
      this.Claw4A.render(f5);
      this.EndA.render(f5);
      this.Shape1.render(f5);
      this.Claw1B.render(f5);
      this.Claw2B.render(f5);
      this.Claw3B.render(f5);
      this.Claw4B.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, (Entity)null);
   }
}
