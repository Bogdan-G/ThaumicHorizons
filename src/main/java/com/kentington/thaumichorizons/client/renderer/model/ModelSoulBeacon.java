package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSoulBeacon extends ModelBase {

   ModelRenderer Base;
   ModelRenderer Core;
   ModelRenderer Claw1A;
   ModelRenderer Claw2A;
   ModelRenderer Claw3A;
   ModelRenderer Claw4A;
   ModelRenderer Claw3B;
   ModelRenderer Claw4B;
   ModelRenderer Claw1B;
   ModelRenderer Claw2B;
   ModelRenderer Claw1C;
   ModelRenderer Claw2C;
   ModelRenderer Claw3C;
   ModelRenderer Claw4C;


   public ModelSoulBeacon() {
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.Base = new ModelRenderer(this, 0, 23);
      this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
      this.Base.setRotationPoint(-8.0F, 23.0F, -8.0F);
      this.Base.setTextureSize(64, 64);
      this.Base.mirror = true;
      this.setRotation(this.Base, 0.0F, 0.0F, 0.0F);
      this.Core = new ModelRenderer(this, 0, 0);
      this.Core.addBox(0.0F, 0.0F, 0.0F, 12, 11, 12);
      this.Core.setRotationPoint(-6.0F, 12.0F, -6.0F);
      this.Core.setTextureSize(64, 64);
      this.Core.mirror = true;
      this.setRotation(this.Core, 0.0F, 0.0F, 0.0F);
      this.Claw1A = new ModelRenderer(this, 48, 0);
      this.Claw1A.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Claw1A.setRotationPoint(6.0F, 12.0F, -1.0F);
      this.Claw1A.setTextureSize(64, 64);
      this.Claw1A.mirror = true;
      this.setRotation(this.Claw1A, 0.0F, 0.0F, 0.0F);
      this.Claw2A = new ModelRenderer(this, 48, 0);
      this.Claw2A.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Claw2A.setRotationPoint(-8.0F, 12.0F, -1.0F);
      this.Claw2A.setTextureSize(64, 64);
      this.Claw2A.mirror = true;
      this.setRotation(this.Claw2A, 0.0F, 0.0F, 0.0F);
      this.Claw3A = new ModelRenderer(this, 48, 0);
      this.Claw3A.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Claw3A.setRotationPoint(-1.0F, 12.0F, -8.0F);
      this.Claw3A.setTextureSize(64, 64);
      this.Claw3A.mirror = true;
      this.setRotation(this.Claw3A, 0.0F, 0.0F, 0.0F);
      this.Claw4A = new ModelRenderer(this, 48, 0);
      this.Claw4A.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Claw4A.setRotationPoint(-1.0F, 12.0F, 6.0F);
      this.Claw4A.setTextureSize(64, 64);
      this.Claw4A.mirror = true;
      this.setRotation(this.Claw4A, 0.0F, 0.0F, 0.0F);
      this.Claw3B = new ModelRenderer(this, 48, 8);
      this.Claw3B.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1);
      this.Claw3B.setRotationPoint(-1.0F, 8.0F, -8.0F);
      this.Claw3B.setTextureSize(64, 64);
      this.Claw3B.mirror = true;
      this.setRotation(this.Claw3B, 0.0F, 0.0F, 0.0F);
      this.Claw4B = new ModelRenderer(this, 48, 8);
      this.Claw4B.addBox(0.0F, 0.0F, 0.0F, 2, 4, 1);
      this.Claw4B.setRotationPoint(-1.0F, 8.0F, 7.0F);
      this.Claw4B.setTextureSize(64, 64);
      this.Claw4B.mirror = true;
      this.setRotation(this.Claw4B, 0.0F, 0.0F, 0.0F);
      this.Claw1B = new ModelRenderer(this, 48, 16);
      this.Claw1B.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2);
      this.Claw1B.setRotationPoint(7.0F, 8.0F, -1.0F);
      this.Claw1B.setTextureSize(64, 64);
      this.Claw1B.mirror = true;
      this.setRotation(this.Claw1B, 0.0F, 0.0F, 0.0F);
      this.Claw2B = new ModelRenderer(this, 48, 16);
      this.Claw2B.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2);
      this.Claw2B.setRotationPoint(-8.0F, 8.0F, -1.0F);
      this.Claw2B.setTextureSize(64, 64);
      this.Claw2B.mirror = true;
      this.setRotation(this.Claw2B, 0.0F, 0.0F, 0.0F);
      this.Claw1C = new ModelRenderer(this, 48, 48);
      this.Claw1C.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
      this.Claw1C.setRotationPoint(3.0F, 8.0F, -1.0F);
      this.Claw1C.setTextureSize(64, 64);
      this.Claw1C.mirror = true;
      this.setRotation(this.Claw1C, 0.0F, 0.0F, 0.0F);
      this.Claw2C = new ModelRenderer(this, 48, 48);
      this.Claw2C.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
      this.Claw2C.setRotationPoint(-7.0F, 8.0F, -1.0F);
      this.Claw2C.setTextureSize(64, 64);
      this.Claw2C.mirror = true;
      this.setRotation(this.Claw2C, 0.0F, 0.0F, 0.0F);
      this.Claw3C = new ModelRenderer(this, 0, 48);
      this.Claw3C.addBox(0.0F, 0.0F, 0.0F, 2, 1, 4);
      this.Claw3C.setRotationPoint(-1.0F, 8.0F, -7.0F);
      this.Claw3C.setTextureSize(64, 64);
      this.Claw3C.mirror = true;
      this.setRotation(this.Claw3C, 0.0F, 0.0F, 0.0F);
      this.Claw4C = new ModelRenderer(this, 0, 48);
      this.Claw4C.addBox(0.0F, 0.0F, 0.0F, 2, 1, 4);
      this.Claw4C.setRotationPoint(-1.0F, 8.0F, 3.0F);
      this.Claw4C.setTextureSize(64, 64);
      this.Claw4C.mirror = true;
      this.setRotation(this.Claw4C, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Base.render(f5);
      this.Core.render(f5);
      this.Claw1A.render(f5);
      this.Claw2A.render(f5);
      this.Claw3A.render(f5);
      this.Claw4A.render(f5);
      this.Claw3B.render(f5);
      this.Claw4B.render(f5);
      this.Claw1B.render(f5);
      this.Claw2B.render(f5);
      this.Claw1C.render(f5);
      this.Claw2C.render(f5);
      this.Claw3C.render(f5);
      this.Claw4C.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
   }
}
