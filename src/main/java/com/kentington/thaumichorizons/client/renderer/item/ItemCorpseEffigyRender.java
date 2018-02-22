package com.kentington.thaumichorizons.client.renderer.item;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class ItemCorpseEffigyRender implements IItemRenderer {

   private ModelBiped corpse = new ModelBiped();
   private String tx1 = "textures/models/corpseeffigy.png";


   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return true;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return helper != ItemRendererHelper.BLOCK_3D;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      ItemRenderer ir = RenderManager.instance.itemRenderer;
      GL11.glPushMatrix();
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      if(type != ItemRenderType.INVENTORY) {
         if(type == ItemRenderType.ENTITY) {
            GL11.glTranslated(0.0D, -3.0D, 0.0D);
         } else {
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glTranslated(-1.0D, -4.0D, -1.0D);
         }
      } else {
         GL11.glTranslated(0.0D, -0.9D, 0.0D);
         GL11.glScalef(0.5F, 0.5F, 0.5F);
      }

      UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", this.tx1));
      this.corpse.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.125F);
      GL11.glPopMatrix();
   }
}
