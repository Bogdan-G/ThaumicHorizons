package com.kentington.thaumichorizons.client.renderer.item;

import com.kentington.thaumichorizons.client.renderer.model.ModelInjector;
import net.minecraft.client.model.ModelBase;
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

public class ItemInjectorRender implements IItemRenderer {

   private ModelBase injector = new ModelInjector();
   private String tx1 = "textures/models/injector.png";


   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return true;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return helper != ItemRendererHelper.BLOCK_3D;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      int ticksUsed = 0;
      int rotation = 0;
      if(item.stackTagCompound != null) {
         ticksUsed = item.stackTagCompound.getInteger("usetime");
         rotation = item.stackTagCompound.getInteger("rotation");
         int f = item.stackTagCompound.getInteger("rotationTarget");
         if(rotation < f) {
            ++rotation;
            item.stackTagCompound.setInteger("rotation", rotation);
         } else if(rotation > f) {
            --rotation;
            item.stackTagCompound.setInteger("rotation", rotation);
         }
      }

      float var8 = (float)ticksUsed / 30.0F;
      var8 = (var8 * var8 + var8 * 2.0F) / 3.0F;
      if(var8 > 1.0F) {
         var8 = 1.0F;
      }

      ItemRenderer ir = RenderManager.instance.itemRenderer;
      GL11.glPushMatrix();
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      if(type != ItemRenderType.INVENTORY) {
         if(type == ItemRenderType.ENTITY) {
            GL11.glTranslated(0.0D, -0.5D, 0.0D);
         } else if(type != ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslated(0.25D, -1.0D, -1.25D);
            GL11.glRotated(-45.0D, 0.0D, 0.0D, 1.0D);
            GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
         } else {
            GL11.glScaled(4.0D, 4.0D, 4.0D);
            GL11.glTranslated(1.5D, 0.0D, 0.0D);
            GL11.glRotated(230.0D, 0.0D, 1.0D, 0.0D);
         }
      } else {
         GL11.glTranslated(0.38D, -0.1D, 0.38D);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      }

      UtilsFX.bindTexture(new ResourceLocation("thaumichorizons", this.tx1));
      this.injector.render((Entity)null, var8 * 3.1415927F / 16.0F, var8 * 3.1415927F / 4.0F, (float)rotation, var8, 0.0F, 0.125F);
      GL11.glPopMatrix();
   }
}
