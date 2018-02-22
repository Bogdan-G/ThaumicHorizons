package com.kentington.thaumichorizons.client.renderer.item;

import com.kentington.thaumichorizons.client.renderer.model.ModelSyringe;
import com.kentington.thaumichorizons.common.items.ItemSyringeBlood;
import com.kentington.thaumichorizons.common.items.ItemSyringeBloodSample;
import com.kentington.thaumichorizons.common.items.ItemSyringeEmpty;
import com.kentington.thaumichorizons.common.items.ItemSyringeInjection;
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

public class ItemSyringeRender implements IItemRenderer {

   private ModelSyringe syringe = new ModelSyringe();
   private String tx1 = "textures/models/syringe.png";


   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return item.getItemDamage() == 0;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return helper != ItemRendererHelper.BLOCK_3D;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      if(item != null && (item.getItem() instanceof ItemSyringeBlood || item.getItem() instanceof ItemSyringeBloodSample || item.getItem() instanceof ItemSyringeInjection || item.getItem() instanceof ItemSyringeEmpty)) {
         ItemRenderer ir = RenderManager.instance.itemRenderer;
         GL11.glPushMatrix();
         if(type != ItemRenderType.INVENTORY) {
            if(type == ItemRenderType.ENTITY) {
               GL11.glTranslated(0.0D, -2.0D, 0.0D);
            } else {
               GL11.glRotatef(-66.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslated(-1.0D, -2.25D, 0.75D);
            }
         } else {
            GL11.glTranslated(0.0D, -2.75D, 0.0D);
         }

         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", this.tx1));
         this.syringe.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.125F, item);
         GL11.glDisable(3042);
         GL11.glPopMatrix();
      }
   }
}
