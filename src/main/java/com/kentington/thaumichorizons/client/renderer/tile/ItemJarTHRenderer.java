package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSoulJar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.config.ConfigBlocks;

public class ItemJarTHRenderer implements IItemRenderer {

   static String tx3 = "textures/misc/soul.png";


   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return true;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return helper != ItemRendererHelper.EQUIPPED_BLOCK;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      if(item.getItem() == Item.getItemFromBlock(ThaumicHorizons.blockJar)) {
         float short1 = 240.0F;
         float short2 = 240.0F;
         GL11.glTranslated(0.5D, 0.0D, 0.5D);
         if(type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(-0.5F, -0.25F, -0.5F);
         } else if(type == ItemRenderType.EQUIPPED && data[1] instanceof EntityPlayer) {
            GL11.glTranslatef(0.0F, 0.0F, -0.5F);
         }

         if(item.hasTagCompound() && item.stackTagCompound.getBoolean("isSoul")) {
            long rb2 = System.nanoTime();
            UtilsFX.bindTexture("thaumichorizons", tx3);
            GL11.glEnable(3042);
            GL11.glAlphaFunc(516, 0.003921569F);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, 0.4D, 0.0D);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            UtilsFX.renderAnimatedQuad(0.3F, 0.9F, 16, (int)(rb2 / 40000000L % 16L), 0.0F, 16777215);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glEnable(2884);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
         } else if(item.hasTagCompound()) {
            TileSoulJar rb = new TileSoulJar();
            GL11.glPushMatrix();
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
            EntityClientPlayerMP viewer = Minecraft.getMinecraft().thePlayer;
            rb.entity = EntityList.createEntityFromNBT(item.getTagCompound(), viewer.worldObj);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(rb, 0.0D, 0.0D, 0.0D, 0.0F);
            GL11.glBlendFunc(770, 771);
            Minecraft.getMinecraft().entityRenderer.disableLightmap(0.0D);
            GL11.glPopMatrix();
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 0.5F, 0.0F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
         RenderBlocks rb1 = (RenderBlocks)data[0];
         rb1.useInventoryTint = true;
         rb1.renderBlockAsItem(ConfigBlocks.blockJar, 0, 1.0F);
         GL11.glPopMatrix();
         GL11.glDisable(3042);
      }

   }

}
