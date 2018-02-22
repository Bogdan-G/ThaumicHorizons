package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.common.entities.EntityFamiliar;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;
import thaumcraft.common.Thaumcraft;

public class RenderFamiliar extends RenderOcelot {

   ResourceLocation rl = new ResourceLocation("thaumichorizons", "textures/entity/familiar.png");


   public RenderFamiliar(ModelBase p_i1264_1_, float p_i1264_2_) {
      super(p_i1264_1_, p_i1264_2_);
   }

   protected ResourceLocation getEntityTexture(EntityOcelot p_110775_1_) {
      return this.rl;
   }

   public void doRender(EntityFamiliar p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      if(p_76986_1_.worldObj.rand.nextFloat() > 0.97F) {
         float angle = (float)((double)(p_76986_1_.worldObj.rand.nextFloat() * 2.0F) * 3.141592653589793D);
         Thaumcraft.proxy.sparkle((float)p_76986_1_.posX + p_76986_1_.width * (float)Math.cos((double)angle), (float)p_76986_1_.posY + p_76986_1_.height * (p_76986_1_.worldObj.rand.nextFloat() - 0.1F) * 1.2F, (float)p_76986_1_.posZ + p_76986_1_.width * (float)Math.sin((double)angle), 2.0F, 0, 0.0F);
      }

   }

   public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityFamiliar)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityFamiliar)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.doRender((EntityFamiliar)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }
}
