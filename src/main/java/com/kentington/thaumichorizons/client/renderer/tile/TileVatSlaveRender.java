package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileVat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileVatSlaveRender extends TileEntitySpecialRenderer {

   ModelBiped corpse = new ModelBiped();
   static String tx1 = "textures/models/corpseeffigy.png";
   static String tx2 = "textures/models/corpseeffigyrevived.png";
   EntityItem stack = null;


   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      if(te.getBlockMetadata() == 0 && te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord + 1, te.zCoord) == 7) {
         TileVat tco = (TileVat)te.getWorldObj().getTileEntity(te.xCoord, te.yCoord + 1, te.zCoord);
         GL11.glPushMatrix();
         if(tco.getEntityContained() != null && !(tco.getEntityContained() instanceof EntityPlayer)) {
            if(tco.mode == 1) {
               tco.getClass();
               float var10000 = (float)(800 - tco.progress);
               tco.getClass();
               float scale = var10000 / 800.0F;
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, scale);
               EntityLivingBase p_147936_1_ = tco.getEntityContained();
               double d0 = p_147936_1_.lastTickPosX + (p_147936_1_.posX - p_147936_1_.lastTickPosX) * (double)f;
               double d1 = p_147936_1_.lastTickPosY + (p_147936_1_.posY - p_147936_1_.lastTickPosY) * (double)f;
               double d2 = p_147936_1_.lastTickPosZ + (p_147936_1_.posZ - p_147936_1_.lastTickPosZ) * (double)f;
               float f1 = p_147936_1_.prevRotationYaw + (p_147936_1_.rotationYaw - p_147936_1_.prevRotationYaw) * f;
               RenderManager var10003 = RenderManager.instance;
               double var10002 = d0 - RenderManager.renderPosX;
               RenderManager var10004 = RenderManager.instance;
               double var19 = d1 - RenderManager.renderPosY;
               RenderManager var10005 = RenderManager.instance;
               RenderManager.instance.func_147939_a(p_147936_1_, var10002, var19, d2 - RenderManager.renderPosZ, f1, f, false);
            } else {
               GL11.glTranslatef(0.0F, 0.1F * (float)Math.cos(Math.toRadians((double)Minecraft.getMinecraft().thePlayer.ticksExisted)), 0.0F);
               RenderManager.instance.renderEntitySimple(tco.getEntityContained(), f);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(3042);
         } else if(tco.mode == 3) {
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef((float)(-x) - 0.5F, (float)(-y) - 1.5F + 0.1F * (float)Math.cos(Math.toRadians((double)Minecraft.getMinecraft().thePlayer.ticksExisted)), (float)z + 0.5F);
            UtilsFX.bindTexture("thaumichorizons", tx1);
            this.corpse.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F);
         } else if(tco.mode != 4 && (tco.mode != 2 || tco.recipeType != 1)) {
            if(tco.sample != null) {
               if(this.stack == null || tco.sample.getItem() != this.stack.getEntityItem().getItem()) {
                  this.stack = new EntityItem(tco.getWorldObj(), (double)tco.xCoord + 0.5D, (double)tco.yCoord - 1.0D, (double)tco.zCoord + 0.5D, tco.sample);
               }

               GL11.glTranslatef(0.0F, 0.1F * (float)Math.cos(Math.toRadians((double)Minecraft.getMinecraft().thePlayer.ticksExisted)), 0.0F);
               RenderManager.instance.renderEntitySimple(this.stack, f);
            }
         } else {
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef((float)(-x) - 0.5F, (float)(-y) - 1.5F + 0.1F * (float)Math.cos(Math.toRadians((double)Minecraft.getMinecraft().thePlayer.ticksExisted)), (float)z + 0.5F);
            UtilsFX.bindTexture("thaumichorizons", tx2);
            this.corpse.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F);
         }

         GL11.glEnable('\u803a');
         GL11.glPopMatrix();
      }

   }

}
