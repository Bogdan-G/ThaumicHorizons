package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.client.renderer.model.ModelSyringe;
import com.kentington.thaumichorizons.common.entities.EntitySyringe;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderSyringe extends Render {

   private ModelSyringe model;


   public RenderSyringe() {
      super.shadowSize = 0.0F;
      this.model = new ModelSyringe();
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return new ResourceLocation("thaumichorizons", "textures/models/syringe.png");
   }

   public void doRender(Entity ent, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      if(ent instanceof EntitySyringe) {
         EntitySyringe syringe = (EntitySyringe)ent;
         GL11.glPushMatrix();
         GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
         GL11.glRotatef(syringe.rotationYaw + 90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-syringe.rotationPitch, 0.0F, 0.0F, 1.0F);
         this.bindEntityTexture(syringe);
         this.model.render(syringe, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (ItemStack)null);
         GL11.glPopMatrix();
      }

   }
}
