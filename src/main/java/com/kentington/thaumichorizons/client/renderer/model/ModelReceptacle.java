package com.kentington.thaumichorizons.client.renderer.model;

import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import java.awt.Color;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelReceptacle extends ModelBase {

   ModelRenderer Corner1;
   ModelRenderer Corner2;
   ModelRenderer Corner3;
   ModelRenderer Corner4;
   ModelRenderer Side1A;
   ModelRenderer Corner5;
   ModelRenderer Corner6;
   ModelRenderer Corner7;
   ModelRenderer Corner8;
   ModelRenderer Side1B;
   ModelRenderer Side1C;
   ModelRenderer Side1D;
   ModelRenderer Side2A;
   ModelRenderer Side2B;
   ModelRenderer Side2C;
   ModelRenderer Side2D;
   ModelRenderer Side3A;
   ModelRenderer Side3B;
   ModelRenderer Side3C;
   ModelRenderer Side3D;
   ModelRenderer Keystone;


   public ModelReceptacle() {
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.Corner1 = new ModelRenderer(this, 0, 0);
      this.Corner1.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Corner1.setRotationPoint(-8.0F, 20.0F, 4.0F);
      this.Corner1.setTextureSize(64, 64);
      this.Corner1.mirror = true;
      this.setRotation(this.Corner1, 0.0F, 0.0F, 0.0F);
      this.Corner2 = new ModelRenderer(this, 0, 0);
      this.Corner2.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Corner2.setRotationPoint(-8.0F, 20.0F, -8.0F);
      this.Corner2.setTextureSize(64, 64);
      this.Corner2.mirror = true;
      this.setRotation(this.Corner2, 0.0F, 0.0F, 0.0F);
      this.Corner3 = new ModelRenderer(this, 0, 0);
      this.Corner3.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Corner3.setRotationPoint(4.0F, 20.0F, -8.0F);
      this.Corner3.setTextureSize(64, 64);
      this.Corner3.mirror = true;
      this.setRotation(this.Corner3, 0.0F, 0.0F, 0.0F);
      this.Corner4 = new ModelRenderer(this, 0, 0);
      this.Corner4.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Corner4.setRotationPoint(4.0F, 20.0F, 4.0F);
      this.Corner4.setTextureSize(64, 64);
      this.Corner4.mirror = true;
      this.setRotation(this.Corner4, 0.0F, 0.0F, 0.0F);
      this.Side1A = new ModelRenderer(this, 0, 8);
      this.Side1A.addBox(0.0F, 0.0F, 0.0F, 8, 4, 4);
      this.Side1A.setRotationPoint(-4.0F, 8.0F, 4.0F);
      this.Side1A.setTextureSize(64, 64);
      this.Side1A.mirror = true;
      this.setRotation(this.Side1A, 0.0F, 0.0F, 0.0F);
      this.Corner5 = new ModelRenderer(this, 0, 0);
      this.Corner5.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Corner5.setRotationPoint(-8.0F, 8.0F, 4.0F);
      this.Corner5.setTextureSize(64, 64);
      this.Corner5.mirror = true;
      this.setRotation(this.Corner5, 0.0F, 0.0F, 0.0F);
      this.Corner6 = new ModelRenderer(this, 0, 0);
      this.Corner6.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Corner6.setRotationPoint(-8.0F, 8.0F, -8.0F);
      this.Corner6.setTextureSize(64, 64);
      this.Corner6.mirror = true;
      this.setRotation(this.Corner6, 0.0F, 0.0F, 0.0F);
      this.Corner7 = new ModelRenderer(this, 0, 0);
      this.Corner7.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Corner7.setRotationPoint(4.0F, 8.0F, -8.0F);
      this.Corner7.setTextureSize(64, 64);
      this.Corner7.mirror = true;
      this.setRotation(this.Corner7, 0.0F, 0.0F, 0.0F);
      this.Corner8 = new ModelRenderer(this, 0, 0);
      this.Corner8.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      this.Corner8.setRotationPoint(4.0F, 8.0F, 4.0F);
      this.Corner8.setTextureSize(64, 64);
      this.Corner8.mirror = true;
      this.setRotation(this.Corner8, 0.0F, 0.0F, 0.0F);
      this.Side1B = new ModelRenderer(this, 0, 8);
      this.Side1B.addBox(0.0F, 0.0F, 0.0F, 8, 4, 4);
      this.Side1B.setRotationPoint(-4.0F, 8.0F, -8.0F);
      this.Side1B.setTextureSize(64, 64);
      this.Side1B.mirror = true;
      this.setRotation(this.Side1B, 0.0F, 0.0F, 0.0F);
      this.Side1C = new ModelRenderer(this, 0, 8);
      this.Side1C.addBox(0.0F, 0.0F, 0.0F, 8, 4, 4);
      this.Side1C.setRotationPoint(-4.0F, 20.0F, -8.0F);
      this.Side1C.setTextureSize(64, 64);
      this.Side1C.mirror = true;
      this.setRotation(this.Side1C, 0.0F, 0.0F, 0.0F);
      this.Side1D = new ModelRenderer(this, 0, 8);
      this.Side1D.addBox(0.0F, 0.0F, 0.0F, 8, 4, 4);
      this.Side1D.setRotationPoint(-4.0F, 20.0F, 4.0F);
      this.Side1D.setTextureSize(64, 64);
      this.Side1D.mirror = true;
      this.setRotation(this.Side1D, 0.0F, 0.0F, 0.0F);
      this.Side2A = new ModelRenderer(this, 0, 16);
      this.Side2A.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4);
      this.Side2A.setRotationPoint(-8.0F, 12.0F, -8.0F);
      this.Side2A.setTextureSize(64, 64);
      this.Side2A.mirror = true;
      this.setRotation(this.Side2A, 0.0F, 0.0F, 0.0F);
      this.Side2B = new ModelRenderer(this, 0, 16);
      this.Side2B.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4);
      this.Side2B.setRotationPoint(4.0F, 12.0F, 4.0F);
      this.Side2B.setTextureSize(64, 64);
      this.Side2B.mirror = true;
      this.setRotation(this.Side2B, 0.0F, 0.0F, 0.0F);
      this.Side2C = new ModelRenderer(this, 0, 16);
      this.Side2C.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4);
      this.Side2C.setRotationPoint(-8.0F, 12.0F, 4.0F);
      this.Side2C.setTextureSize(64, 64);
      this.Side2C.mirror = true;
      this.setRotation(this.Side2C, 0.0F, 0.0F, 0.0F);
      this.Side2D = new ModelRenderer(this, 0, 16);
      this.Side2D.addBox(0.0F, 0.0F, 0.0F, 4, 8, 4);
      this.Side2D.setRotationPoint(4.0F, 12.0F, -8.0F);
      this.Side2D.setTextureSize(64, 64);
      this.Side2D.mirror = true;
      this.setRotation(this.Side2D, 0.0F, 0.0F, 0.0F);
      this.Side3A = new ModelRenderer(this, 0, 28);
      this.Side3A.addBox(0.0F, 0.0F, 0.0F, 4, 4, 8);
      this.Side3A.setRotationPoint(-8.0F, 8.0F, -4.0F);
      this.Side3A.setTextureSize(64, 64);
      this.Side3A.mirror = true;
      this.setRotation(this.Side3A, 0.0F, 0.0F, 0.0F);
      this.Side3B = new ModelRenderer(this, 0, 28);
      this.Side3B.addBox(0.0F, 0.0F, 0.0F, 4, 4, 8);
      this.Side3B.setRotationPoint(4.0F, 8.0F, -4.0F);
      this.Side3B.setTextureSize(64, 64);
      this.Side3B.mirror = true;
      this.setRotation(this.Side3B, 0.0F, 0.0F, 0.0F);
      this.Side3C = new ModelRenderer(this, 0, 28);
      this.Side3C.addBox(0.0F, 0.0F, 0.0F, 4, 4, 8);
      this.Side3C.setRotationPoint(-8.0F, 20.0F, -4.0F);
      this.Side3C.setTextureSize(64, 64);
      this.Side3C.mirror = true;
      this.setRotation(this.Side3C, 0.0F, 0.0F, 0.0F);
      this.Side3D = new ModelRenderer(this, 0, 28);
      this.Side3D.addBox(0.0F, 0.0F, 0.0F, 4, 4, 8);
      this.Side3D.setRotationPoint(4.0F, 20.0F, -4.0F);
      this.Side3D.setTextureSize(64, 64);
      this.Side3D.mirror = true;
      this.setRotation(this.Side3D, 0.0F, 0.0F, 0.0F);
      this.Keystone = new ModelRenderer(this, 24, 0);
      this.Keystone.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8);
      this.Keystone.setRotationPoint(-4.0F, 12.0F, -4.0F);
      this.Keystone.setTextureSize(64, 64);
      this.Keystone.mirror = true;
      this.setRotation(this.Keystone, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, boolean keystone, int plane) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5);
      this.Corner1.render(f5);
      this.Corner2.render(f5);
      this.Corner3.render(f5);
      this.Corner4.render(f5);
      this.Side1A.render(f5);
      this.Corner5.render(f5);
      this.Corner6.render(f5);
      this.Corner7.render(f5);
      this.Corner8.render(f5);
      this.Side1B.render(f5);
      this.Side1C.render(f5);
      this.Side1D.render(f5);
      this.Side2A.render(f5);
      this.Side2B.render(f5);
      this.Side2C.render(f5);
      this.Side2D.render(f5);
      this.Side3A.render(f5);
      this.Side3B.render(f5);
      this.Side3C.render(f5);
      this.Side3D.render(f5);
      if(keystone) {
         Color col = new Color(((PocketPlaneData)PocketPlaneData.planes.get(plane)).color);
         GL11.glColor4f((float)col.getRed() / 255.0F, (float)col.getGreen() / 255.0F, (float)col.getBlue() / 255.0F, 1.0F);
         this.Keystone.render(f5);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

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
