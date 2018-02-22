package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBloodInfuser extends ModelBase {

   ModelRenderer Body;
   ModelRenderer SyringeSlot;
   ModelRenderer Pipe1A;
   ModelRenderer Pipe1B;
   ModelRenderer Pipe2A;
   ModelRenderer Pipe2B;
   ModelRenderer Pipe3A;
   ModelRenderer Pipe3B;
   ModelRenderer Pipe4A;
   ModelRenderer Pipe4B;


   public ModelBloodInfuser() {
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.Body = new ModelRenderer(this, 0, 0);
      this.Body.addBox(0.0F, 0.0F, 0.0F, 16, 10, 16);
      this.Body.setRotationPoint(-8.0F, 14.0F, -8.0F);
      this.Body.setTextureSize(64, 64);
      this.Body.mirror = true;
      this.setRotation(this.Body, 0.0F, 0.0F, 0.0F);
      this.SyringeSlot = new ModelRenderer(this, 0, 32);
      this.SyringeSlot.addBox(0.0F, 0.0F, 0.0F, 6, 6, 6);
      this.SyringeSlot.setRotationPoint(-3.0F, 8.0F, -3.0F);
      this.SyringeSlot.setTextureSize(64, 64);
      this.SyringeSlot.mirror = true;
      this.setRotation(this.SyringeSlot, 0.0F, 0.0F, 0.0F);
      this.Pipe1A = new ModelRenderer(this, 0, 48);
      this.Pipe1A.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3);
      this.Pipe1A.setRotationPoint(-1.0F, 9.0F, 3.0F);
      this.Pipe1A.setTextureSize(64, 64);
      this.Pipe1A.mirror = true;
      this.setRotation(this.Pipe1A, 0.0F, 0.0F, 0.0F);
      this.Pipe1B = new ModelRenderer(this, 0, 54);
      this.Pipe1B.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
      this.Pipe1B.setRotationPoint(-1.0F, 11.0F, 4.0F);
      this.Pipe1B.setTextureSize(64, 64);
      this.Pipe1B.mirror = true;
      this.setRotation(this.Pipe1B, 0.0F, 0.0F, 0.0F);
      this.Pipe2A = new ModelRenderer(this, 0, 48);
      this.Pipe2A.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3);
      this.Pipe2A.setRotationPoint(-1.0F, 9.0F, -6.0F);
      this.Pipe2A.setTextureSize(64, 64);
      this.Pipe2A.mirror = true;
      this.setRotation(this.Pipe2A, 0.0F, 0.0F, 0.0F);
      this.Pipe2B = new ModelRenderer(this, 0, 54);
      this.Pipe2B.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
      this.Pipe2B.setRotationPoint(-1.0F, 11.0F, -6.0F);
      this.Pipe2B.setTextureSize(64, 64);
      this.Pipe2B.mirror = true;
      this.setRotation(this.Pipe2B, 0.0F, 0.0F, 0.0F);
      this.Pipe3A = new ModelRenderer(this, 0, 48);
      this.Pipe3A.addBox(0.0F, 0.0F, 0.0F, 3, 2, 2);
      this.Pipe3A.setRotationPoint(3.0F, 9.0F, -1.0F);
      this.Pipe3A.setTextureSize(64, 64);
      this.Pipe3A.mirror = true;
      this.setRotation(this.Pipe3A, 0.0F, 0.0F, 0.0F);
      this.Pipe3B = new ModelRenderer(this, 0, 54);
      this.Pipe3B.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
      this.Pipe3B.setRotationPoint(4.0F, 11.0F, -1.0F);
      this.Pipe3B.setTextureSize(64, 64);
      this.Pipe3B.mirror = true;
      this.setRotation(this.Pipe3B, 0.0F, 0.0F, 0.0F);
      this.Pipe4A = new ModelRenderer(this, 0, 48);
      this.Pipe4A.addBox(0.0F, 0.0F, 0.0F, 3, 2, 2);
      this.Pipe4A.setRotationPoint(-6.0F, 9.0F, -1.0F);
      this.Pipe4A.setTextureSize(64, 64);
      this.Pipe4A.mirror = true;
      this.setRotation(this.Pipe4A, 0.0F, 0.0F, 0.0F);
      this.Pipe4B = new ModelRenderer(this, 0, 54);
      this.Pipe4B.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2);
      this.Pipe4B.setRotationPoint(-6.0F, 11.0F, -1.0F);
      this.Pipe4B.setTextureSize(64, 64);
      this.Pipe4B.mirror = true;
      this.setRotation(this.Pipe4B, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.Body.render(f5);
      this.SyringeSlot.render(f5);
      this.Pipe1A.render(f5);
      this.Pipe1B.render(f5);
      this.Pipe2A.render(f5);
      this.Pipe2B.render(f5);
      this.Pipe3A.render(f5);
      this.Pipe3B.render(f5);
      this.Pipe4A.render(f5);
      this.Pipe4B.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }
}
