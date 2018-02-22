package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelInjector extends ModelBase {

   ModelRenderer Drum;
   ModelRenderer Front;
   ModelRenderer BowL1;
   ModelRenderer BowR1;
   ModelRenderer BowL2;
   ModelRenderer BowR2;
   ModelRenderer Stock;
   ModelRenderer Grip;
   ModelRenderer Thingy;


   public ModelInjector() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.Drum = new ModelRenderer(this, 0, 0);
      this.Drum.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 1);
      this.Drum.setRotationPoint(2.5F, 2.25F, 0.0F);
      this.Drum.setTextureSize(64, 32);
      this.Drum.mirror = true;
      this.setRotation(this.Drum, 0.0F, 0.0F, 0.0F);
      this.Front = new ModelRenderer(this, 12, 0);
      this.Front.addBox(0.0F, 0.0F, 0.0F, 3, 1, 8);
      this.Front.setRotationPoint(1.0F, 1.0F, -8.0F);
      this.Front.setTextureSize(64, 32);
      this.Front.mirror = true;
      this.setRotation(this.Front, 0.0F, 0.0F, 0.0F);
      this.BowL1 = new ModelRenderer(this, 0, 6);
      this.BowL1.addBox(0.0F, -1.0F, 0.0F, 4, 1, 1);
      this.BowL1.setRotationPoint(2.5F, 1.0F, -8.0F);
      this.BowL1.setTextureSize(64, 32);
      this.BowL1.mirror = true;
      this.setRotation(this.BowL1, 0.0F, 0.0F, 0.0F);
      this.BowR1 = new ModelRenderer(this, 0, 6);
      this.BowR1.addBox(0.0F, -1.0F, -1.0F, 4, 1, 1);
      this.BowR1.setRotationPoint(2.5F, 1.0F, -8.0F);
      this.BowR1.setTextureSize(64, 32);
      this.BowR1.mirror = true;
      this.setRotation(this.BowR1, 0.0F, 3.141593F, 0.0F);
      this.BowL2 = new ModelRenderer(this, 0, 8);
      this.BowL2.addBox(0.0F, -1.0F, 0.0F, 3, 1, 1);
      this.BowL2.setRotationPoint(6.0F, 1.0F, -7.5F);
      this.BowL2.setTextureSize(64, 32);
      this.BowL2.mirror = true;
      this.setRotation(this.BowL2, 0.0F, -0.3490659F, 0.0F);
      this.BowR2 = new ModelRenderer(this, 0, 8);
      this.BowR2.addBox(0.0F, -1.0F, -1.0F, 3, 1, 1);
      this.BowR2.setRotationPoint(-1.0F, 1.0F, -7.5F);
      this.BowR2.setTextureSize(64, 32);
      this.BowR2.mirror = true;
      this.setRotation(this.BowR2, 0.0F, 3.490659F, 0.0F);
      this.Thingy = new ModelRenderer(this, 13, 0);
      this.Thingy.addBox(-0.5F, 0.0F, 0.5F, 1, 1, 1);
      this.Thingy.setRotationPoint((this.BowR2.rotationPointX + this.BowL2.rotationPointX) / 2.0F, 0.0F, -6.5F);
      this.Thingy.setTextureSize(64, 32);
      this.Thingy.mirror = true;
      this.Stock = new ModelRenderer(this, 0, 10);
      this.Stock.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
      this.Stock.setRotationPoint(1.0F, 1.0F, 1.0F);
      this.Stock.setTextureSize(64, 32);
      this.Stock.mirror = true;
      this.setRotation(this.Stock, 0.0F, 0.0F, 0.0F);
      this.Grip = new ModelRenderer(this, 0, 14);
      this.Grip.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Grip.setRotationPoint(1.0F, 2.0F, 3.0F);
      this.Grip.setTextureSize(64, 32);
      this.Grip.mirror = true;
      this.setRotation(this.Grip, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Drum.render(f5);
      this.Front.render(f5);
      this.BowL1.render(f5);
      this.BowR1.render(f5);
      this.BowL2.render(f5);
      this.BowR2.render(f5);
      this.Stock.render(f5);
      this.Grip.render(f5);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawing(3);
      tessellator.setColorOpaque_I(0);
      tessellator.addVertex((double)this.BowR2.rotationPointX + 0.6D + (double)f3 * 0.25D, (double)this.BowR2.rotationPointY - 0.9375D, (double)this.BowR2.rotationPointZ + 6.8125D + (double)f3 * 0.125D);
      tessellator.addVertex((double)((this.BowR2.rotationPointX + this.BowL2.rotationPointX) / 2.0F) - 2.175D, (double)this.BowR2.rotationPointY - 0.9375D, (double)this.BowR2.rotationPointZ + 6.8125D + (double)f3 * 0.65D);
      tessellator.addVertex((double)this.BowL2.rotationPointX - 4.95D - (double)f3 * 0.25D, (double)this.BowL2.rotationPointY - 0.9375D, (double)this.BowL2.rotationPointZ + 6.8125D + (double)f3 * 0.125D);
      tessellator.draw();
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      this.Thingy.setRotationPoint((this.BowR2.rotationPointX + this.BowL2.rotationPointX) / 2.0F, 0.0F, -6.5F + 5.0F * f3);
      this.Thingy.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
      this.BowL1.rotateAngleY = -f;
      this.BowR1.rotateAngleY = f + 3.1415927F;
      this.BowL2.rotateAngleY = -0.34906238F - f1;
      this.BowR2.rotateAngleY = 3.490655F + f1;
      this.Drum.rotateAngleZ = f2 * 2.0F * 3.1415927F / 180.0F;
   }
}
