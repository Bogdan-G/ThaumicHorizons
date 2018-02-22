package com.kentington.thaumichorizons.client.renderer.model;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntitySyringe;
import java.awt.Color;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ModelSyringe extends ModelBase {

   ModelRenderer Body;
   ModelRenderer Needle;
   ModelRenderer PlungerA;
   ModelRenderer PlungerB;


   public ModelSyringe() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.Body = new ModelRenderer(this, 0, 0);
      this.Body.addBox(0.0F, 0.0F, 0.0F, 9, 3, 3);
      this.Body.setRotationPoint(-3.0F, 21.0F, -1.0F);
      this.Body.setTextureSize(64, 32);
      this.Body.mirror = true;
      this.setRotation(this.Body, 0.0F, 0.0F, 0.0F);
      this.Needle = new ModelRenderer(this, 25, 0);
      this.Needle.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
      this.Needle.setRotationPoint(-7.0F, 22.0F, 0.0F);
      this.Needle.setTextureSize(64, 32);
      this.Needle.mirror = true;
      this.setRotation(this.Needle, 0.0F, 0.0F, 0.0F);
      this.PlungerA = new ModelRenderer(this, 0, 8);
      this.PlungerA.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.PlungerA.setRotationPoint(6.0F, 22.0F, 0.0F);
      this.PlungerA.setTextureSize(64, 32);
      this.PlungerA.mirror = true;
      this.setRotation(this.PlungerA, 0.0F, 0.0F, 0.0F);
      this.PlungerB = new ModelRenderer(this, 0, 12);
      this.PlungerB.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3);
      this.PlungerB.setRotationPoint(7.0F, 21.0F, -1.0F);
      this.PlungerB.setTextureSize(64, 32);
      this.PlungerB.mirror = true;
      this.setRotation(this.PlungerB, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ItemStack item) {
      Color col;
      float red;
      float green;
      float blue;
      if(item != null) {
         col = new Color(ThaumicHorizons.itemSyringeInjection.getColorFromItemStack(item, 0));
         red = (float)col.getRed() / 255.0F;
         green = (float)col.getGreen() / 255.0F;
         blue = (float)col.getBlue() / 255.0F;
         if(item.getItem() != ThaumicHorizons.itemSyringeEmpty) {
            GL11.glColor4f(red, green, blue, 0.75F);
         } else {
            GL11.glColor4f(red, green, blue, 0.5F);
         }
      } else if(entity != null && entity instanceof EntitySyringe) {
         col = new Color(((EntitySyringe)entity).color);
         red = (float)col.getRed() / 255.0F;
         green = (float)col.getGreen() / 255.0F;
         blue = (float)col.getBlue() / 255.0F;
         GL11.glColor4f(red, green, blue, 0.75F);
         GL11.glTranslatef(0.0F, -1.36F, 0.0F);
      }

      this.Body.render(f5);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.Needle.render(f5);
      this.PlungerA.render(f5);
      this.PlungerB.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent) {
      this.Body.rotateAngleX = ent.rotationPitch;
      this.Body.rotateAngleY = ent.rotationYaw;
      this.Needle.rotateAngleX = ent.rotationPitch;
      this.Needle.rotateAngleY = ent.rotationYaw;
      this.PlungerA.rotateAngleX = ent.rotationPitch;
      this.PlungerA.rotateAngleY = ent.rotationYaw;
      this.PlungerB.rotateAngleX = ent.rotationPitch;
      this.PlungerB.rotateAngleY = ent.rotationYaw;
   }
}
