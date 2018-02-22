package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlastPhialRender extends RenderSnowball {

   Item item;


   public BlastPhialRender() {
      super(ThaumicHorizons.itemSyringeInjection);
      this.item = ThaumicHorizons.itemSyringeInjection;
   }

   public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      IIcon iicon = this.item.getIconFromDamage(1);
      if(iicon != null) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
         GL11.glEnable('\u803a');
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         this.bindEntityTexture(p_76986_1_);
         Tessellator tessellator = Tessellator.instance;
         int i = PotionHelper.func_77915_a(((EntityPotion)p_76986_1_).getPotionDamage(), false);
         float f2 = (float)(i >> 16 & 255) / 255.0F;
         float f3 = (float)(i >> 8 & 255) / 255.0F;
         float f4 = (float)(i & 255) / 255.0F;
         GL11.glColor3f(f2, f3, f4);
         GL11.glPushMatrix();
         this.func_77026_a(tessellator, iicon);
         GL11.glPopMatrix();
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         GL11.glDisable('\u803a');
         GL11.glPopMatrix();
      }

   }

   private void func_77026_a(Tessellator p_77026_1_, IIcon p_77026_2_) {
      float f = p_77026_2_.getMinU();
      float f1 = p_77026_2_.getMaxU();
      float f2 = p_77026_2_.getMinV();
      float f3 = p_77026_2_.getMaxV();
      float f4 = 1.0F;
      float f5 = 0.5F;
      float f6 = 0.25F;
      GL11.glRotatef(180.0F - super.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-super.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      p_77026_1_.startDrawingQuads();
      p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
      p_77026_1_.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
      p_77026_1_.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
      p_77026_1_.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
      p_77026_1_.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
      p_77026_1_.draw();
   }

   protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
      return TextureMap.locationItemsTexture;
   }
}
