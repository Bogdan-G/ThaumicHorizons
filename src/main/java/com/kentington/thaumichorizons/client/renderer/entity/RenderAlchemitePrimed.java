package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityAlchemitePrimed;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderAlchemitePrimed extends RenderEntity {

   private RenderBlocks blockRenderer = new RenderBlocks();


   public RenderAlchemitePrimed() {
      super.shadowSize = 0.5F;
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return this.getEntityTexture((EntityAlchemitePrimed)p_110775_1_);
   }

   protected ResourceLocation getEntityTexture(EntityAlchemitePrimed p_110775_1_) {
      return TextureMap.locationBlocksTexture;
   }

   public void doRenderStuff(EntityAlchemitePrimed entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
      float f2;
      if((float)entity.fuse - p_76986_9_ + 1.0F < 10.0F) {
         f2 = 1.0F - ((float)entity.fuse - p_76986_9_ + 1.0F) / 10.0F;
         if(f2 < 0.0F) {
            f2 = 0.0F;
         }

         if(f2 > 1.0F) {
            f2 = 1.0F;
         }

         f2 *= f2;
         f2 *= f2;
         float f3 = 1.0F + f2 * 0.3F;
         GL11.glScalef(f3, f3, f3);
      }

      f2 = (1.0F - ((float)entity.fuse - p_76986_9_ + 1.0F) / 100.0F) * 0.8F;
      this.bindEntityTexture(entity);
      this.blockRenderer.renderBlockAsItem(ThaumicHorizons.blockAlchemite, 0, entity.getBrightness(p_76986_9_));
      if(entity.fuse / 5 % 2 == 0) {
         GL11.glDisable(3553);
         GL11.glDisable(2896);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 772);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, f2);
         this.blockRenderer.renderBlockAsItem(ThaumicHorizons.blockAlchemite, 0, 1.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(2896);
         GL11.glEnable(3553);
      }

      GL11.glPopMatrix();
   }

   public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRenderStuff((EntityAlchemitePrimed)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }
}
