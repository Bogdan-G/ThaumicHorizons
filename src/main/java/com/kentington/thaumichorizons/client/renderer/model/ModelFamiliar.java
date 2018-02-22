package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.MathHelper;

public class ModelFamiliar extends ModelBase {

   ModelRenderer Body;
   ModelRenderer Tail1;
   ModelRenderer Tail2;
   ModelRenderer LegBackL;
   ModelRenderer Ear1;
   ModelRenderer Nose;
   ModelRenderer Main;
   ModelRenderer Ear2;
   ModelRenderer HatBuckle;
   ModelRenderer HatC;
   ModelRenderer HatB;
   ModelRenderer HatA;
   ModelRenderer HatBase;
   ModelRenderer LegBackR;
   ModelRenderer LegFrontL;
   ModelRenderer LegFrontR;
   int field_78163_i = 1;


   public ModelFamiliar() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.Body = new ModelRenderer(this, 20, 0);
      this.Body.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6);
      this.Body.setRotationPoint(0.0F, 12.0F, -10.0F);
      this.Body.setTextureSize(64, 32);
      this.Body.mirror = true;
      this.setRotation(this.Body, 1.570796F, 0.0F, 0.0F);
      this.Tail1 = new ModelRenderer(this, 0, 15);
      this.Tail1.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
      this.Tail1.setRotationPoint(0.0F, 15.0F, 8.0F);
      this.Tail1.setTextureSize(64, 32);
      this.Tail1.mirror = true;
      this.setRotation(this.Tail1, 1.570796F, 0.0F, 0.0F);
      this.Tail2 = new ModelRenderer(this, 4, 15);
      this.Tail2.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
      this.Tail2.setRotationPoint(0.0F, 15.0F, 14.0F);
      this.Tail2.setTextureSize(64, 32);
      this.Tail2.mirror = true;
      this.setRotation(this.Tail2, 1.570796F, 0.0F, 0.0F);
      this.LegBackL = new ModelRenderer(this, 8, 13);
      this.LegBackL.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
      this.LegBackL.setRotationPoint(1.1F, 18.0F, 5.0F);
      this.LegBackL.setTextureSize(64, 32);
      this.LegBackL.mirror = true;
      this.setRotation(this.LegBackL, 0.0F, 0.0F, 0.0F);
      this.Ear1 = new ModelRenderer(this, 0, 10);
      this.Ear1.addBox(-2.0F, -3.0F, 0.0F, 1, 1, 2);
      this.Ear1.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.Ear1.setTextureSize(64, 32);
      this.Ear1.mirror = true;
      this.setRotation(this.Ear1, 0.0F, 0.0F, 0.0F);
      this.Nose = new ModelRenderer(this, 0, 24);
      this.Nose.addBox(-1.5F, 0.0F, -4.0F, 3, 2, 2);
      this.Nose.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.Nose.setTextureSize(64, 32);
      this.Nose.mirror = true;
      this.setRotation(this.Nose, 0.0F, 0.0F, 0.0F);
      this.Main = new ModelRenderer(this, 0, 0);
      this.Main.addBox(-2.5F, -2.0F, -3.0F, 5, 4, 5);
      this.Main.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.Main.setTextureSize(64, 32);
      this.Main.mirror = true;
      this.setRotation(this.Main, 0.0F, 0.0F, 0.0F);
      this.Ear2 = new ModelRenderer(this, 6, 10);
      this.Ear2.addBox(1.0F, -3.0F, 0.0F, 1, 1, 2);
      this.Ear2.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.Ear2.setTextureSize(64, 32);
      this.Ear2.mirror = true;
      this.setRotation(this.Ear2, 0.0F, 0.0F, 0.0F);
      this.HatBuckle = new ModelRenderer(this, 48, 13);
      this.HatBuckle.addBox(-1.0F, -4.0F, -3.0F, 2, 1, 1);
      this.HatBuckle.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.HatBuckle.setTextureSize(64, 32);
      this.HatBuckle.mirror = true;
      this.setRotation(this.HatBuckle, 0.0F, 0.0F, 0.0F);
      this.HatC = new ModelRenderer(this, 48, 11);
      this.HatC.addBox(-0.5F, -7.0F, -1.0F, 1, 1, 1);
      this.HatC.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.HatC.setTextureSize(64, 32);
      this.HatC.mirror = true;
      this.setRotation(this.HatC, 0.0F, 0.0F, 0.0F);
      this.HatB = new ModelRenderer(this, 48, 8);
      this.HatB.addBox(-1.0F, -6.0F, -1.5F, 2, 1, 2);
      this.HatB.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.HatB.setTextureSize(64, 32);
      this.HatB.mirror = true;
      this.setRotation(this.HatB, 0.0F, 0.0F, 0.0F);
      this.HatA = new ModelRenderer(this, 48, 0);
      this.HatA.addBox(-1.5F, -5.0F, -2.0F, 3, 2, 3);
      this.HatA.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.HatA.setTextureSize(64, 32);
      this.HatA.mirror = true;
      this.setRotation(this.HatA, 0.0F, 0.0F, 0.0F);
      this.HatBase = new ModelRenderer(this, 16, 24);
      this.HatBase.addBox(-3.0F, -3.0F, -3.5F, 6, 1, 6);
      this.HatBase.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.HatBase.setTextureSize(64, 32);
      this.HatBase.mirror = true;
      this.setRotation(this.HatBase, 0.0F, 0.0F, 0.0F);
      this.LegBackR = new ModelRenderer(this, 8, 13);
      this.LegBackR.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
      this.LegBackR.setRotationPoint(-1.1F, 18.0F, 5.0F);
      this.LegBackR.setTextureSize(64, 32);
      this.LegBackR.mirror = true;
      this.setRotation(this.LegBackR, 0.0F, 0.0F, 0.0F);
      this.LegFrontL = new ModelRenderer(this, 40, 0);
      this.LegFrontL.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
      this.LegFrontL.setRotationPoint(1.2F, 13.8F, -5.0F);
      this.LegFrontL.setTextureSize(64, 32);
      this.LegFrontL.mirror = true;
      this.setRotation(this.LegFrontL, 0.0F, 0.0F, 0.0F);
      this.LegFrontR = new ModelRenderer(this, 40, 0);
      this.LegFrontR.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
      this.LegFrontR.setRotationPoint(-1.2F, 13.8F, -5.0F);
      this.LegFrontR.setTextureSize(64, 32);
      this.LegFrontR.mirror = true;
      this.setRotation(this.LegFrontR, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Body.render(f5);
      this.Tail1.render(f5);
      this.Tail2.render(f5);
      this.LegBackL.render(f5);
      this.Ear1.render(f5);
      this.Nose.render(f5);
      this.Main.render(f5);
      this.Ear2.render(f5);
      this.HatBuckle.render(f5);
      this.HatC.render(f5);
      this.HatB.render(f5);
      this.HatA.render(f5);
      this.HatBase.render(f5);
      this.LegBackR.render(f5);
      this.LegFrontL.render(f5);
      this.LegFrontR.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
      this.Main.rotateAngleX = p_78087_5_ / 57.295776F;
      this.Main.rotateAngleY = p_78087_4_ / 57.295776F;
      this.Nose.rotateAngleX = p_78087_5_ / 57.295776F;
      this.Nose.rotateAngleY = p_78087_4_ / 57.295776F;
      this.Ear1.rotateAngleX = p_78087_5_ / 57.295776F;
      this.Ear1.rotateAngleY = p_78087_4_ / 57.295776F;
      this.Ear2.rotateAngleX = p_78087_5_ / 57.295776F;
      this.Ear2.rotateAngleY = p_78087_4_ / 57.295776F;
      this.HatA.rotateAngleX = p_78087_5_ / 57.295776F;
      this.HatA.rotateAngleY = p_78087_4_ / 57.295776F;
      this.HatB.rotateAngleX = p_78087_5_ / 57.295776F;
      this.HatB.rotateAngleY = p_78087_4_ / 57.295776F;
      this.HatC.rotateAngleX = p_78087_5_ / 57.295776F;
      this.HatC.rotateAngleY = p_78087_4_ / 57.295776F;
      this.HatBuckle.rotateAngleX = p_78087_5_ / 57.295776F;
      this.HatBuckle.rotateAngleY = p_78087_4_ / 57.295776F;
      this.HatBase.rotateAngleX = p_78087_5_ / 57.295776F;
      this.HatBase.rotateAngleY = p_78087_4_ / 57.295776F;
      if(this.field_78163_i != 3) {
         this.Body.rotateAngleX = 1.5707964F;
         if(this.field_78163_i == 2) {
            this.LegBackL.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_;
            this.LegBackR.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + 0.3F) * 1.0F * p_78087_2_;
            this.LegFrontL.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F + 0.3F) * 1.0F * p_78087_2_;
            this.LegFrontR.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_;
            this.Tail2.rotateAngleX = 1.7278761F + 0.31415927F * MathHelper.cos(p_78087_1_) * p_78087_2_;
         } else {
            this.LegBackL.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_;
            this.LegBackR.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_;
            this.LegFrontL.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.0F * p_78087_2_;
            this.LegFrontR.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.0F * p_78087_2_;
            if(this.field_78163_i == 1) {
               this.Tail2.rotateAngleX = 1.7278761F + 0.7853982F * MathHelper.cos(p_78087_1_) * p_78087_2_;
            } else {
               this.Tail2.rotateAngleX = 1.7278761F + 0.47123894F * MathHelper.cos(p_78087_1_) * p_78087_2_;
            }
         }
      }

   }

   public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
      EntityOcelot entityocelot = (EntityOcelot)p_78086_1_;
      this.Body.rotationPointY = 12.0F;
      this.Body.rotationPointZ = -10.0F;
      this.Main.rotationPointY = 15.0F;
      this.Main.rotationPointZ = -9.0F;
      this.Nose.rotationPointY = 15.0F;
      this.Nose.rotationPointZ = -9.0F;
      this.Ear1.rotationPointY = 15.0F;
      this.Ear1.rotationPointZ = -9.0F;
      this.Ear2.rotationPointY = 15.0F;
      this.Ear2.rotationPointZ = -9.0F;
      this.HatBase.rotationPointY = 15.0F;
      this.HatBase.rotationPointZ = -9.0F;
      this.HatB.rotationPointY = 15.0F;
      this.HatB.rotationPointZ = -9.0F;
      this.HatA.rotationPointY = 15.0F;
      this.HatA.rotationPointZ = -9.0F;
      this.HatC.rotationPointY = 15.0F;
      this.HatC.rotationPointZ = -9.0F;
      this.HatBuckle.rotationPointY = 15.0F;
      this.HatBuckle.rotationPointZ = -9.0F;
      this.Tail1.rotationPointY = 15.0F;
      this.Tail1.rotationPointZ = 8.0F;
      this.Tail2.rotationPointY = 20.0F;
      this.Tail2.rotationPointZ = 14.0F;
      this.LegFrontL.rotationPointY = this.LegFrontR.rotationPointY = 13.8F;
      this.LegFrontL.rotationPointZ = this.LegFrontR.rotationPointZ = -5.0F;
      this.LegBackL.rotationPointY = this.LegBackR.rotationPointY = 18.0F;
      this.LegBackL.rotationPointZ = this.LegBackR.rotationPointZ = 5.0F;
      this.Tail1.rotateAngleX = 0.9F;
      if(entityocelot.isSneaking()) {
         ++this.Body.rotationPointY;
         this.Main.rotationPointY += 2.0F;
         this.Nose.rotationPointY += 2.0F;
         this.Ear1.rotationPointY += 2.0F;
         this.Ear2.rotationPointY += 2.0F;
         this.HatBase.rotationPointY += 2.0F;
         this.HatA.rotationPointY += 2.0F;
         this.HatB.rotationPointY += 2.0F;
         this.HatC.rotationPointY += 2.0F;
         this.HatBuckle.rotationPointY += 2.0F;
         ++this.Tail1.rotationPointY;
         this.Tail2.rotationPointY += -4.0F;
         this.Tail2.rotationPointZ += 2.0F;
         this.Tail1.rotateAngleX = 1.5707964F;
         this.Tail2.rotateAngleX = 1.5707964F;
         this.field_78163_i = 0;
      } else if(entityocelot.isSprinting()) {
         this.Tail2.rotationPointY = this.Tail1.rotationPointY;
         this.Tail2.rotationPointZ += 2.0F;
         this.Tail1.rotateAngleX = 1.5707964F;
         this.Tail2.rotateAngleX = 1.5707964F;
         this.field_78163_i = 2;
      } else if(entityocelot.isSitting()) {
         this.Body.rotateAngleX = 0.7853982F;
         this.Body.rotationPointY += -4.0F;
         this.Body.rotationPointZ += 5.0F;
         this.Main.rotationPointY += -3.3F;
         ++this.Main.rotationPointZ;
         this.Nose.rotationPointY += -3.3F;
         ++this.Nose.rotationPointZ;
         this.Ear1.rotationPointY += -3.3F;
         ++this.Ear1.rotationPointZ;
         this.Ear2.rotationPointY += -3.3F;
         ++this.Ear2.rotationPointZ;
         this.HatBase.rotationPointY += -3.3F;
         ++this.HatBase.rotationPointZ;
         this.HatA.rotationPointY += -3.3F;
         ++this.HatA.rotationPointZ;
         this.HatB.rotationPointY += -3.3F;
         ++this.HatB.rotationPointZ;
         this.HatC.rotationPointY += -3.3F;
         ++this.HatC.rotationPointZ;
         this.HatBuckle.rotationPointY += -3.3F;
         ++this.HatBuckle.rotationPointZ;
         this.Tail1.rotationPointY += 8.0F;
         this.Tail1.rotationPointZ += -2.0F;
         this.Tail2.rotationPointY += 2.0F;
         this.Tail2.rotationPointZ += -0.8F;
         this.Tail1.rotateAngleX = 1.7278761F;
         this.Tail2.rotateAngleX = 2.670354F;
         this.LegFrontL.rotateAngleX = this.LegFrontR.rotateAngleX = -0.15707964F;
         this.LegFrontL.rotationPointY = this.LegFrontR.rotationPointY = 15.8F;
         this.LegFrontL.rotationPointZ = this.LegFrontR.rotationPointZ = -7.0F;
         this.LegBackL.rotateAngleX = this.LegBackR.rotateAngleX = -1.5707964F;
         this.LegBackL.rotationPointY = this.LegBackR.rotationPointY = 21.0F;
         this.LegBackL.rotationPointZ = this.LegBackR.rotationPointZ = 1.0F;
         this.field_78163_i = 3;
      } else {
         this.field_78163_i = 1;
      }

   }
}
