package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileTransductionAmplifier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

@SideOnly(Side.CLIENT)
public class TileTransductionAmplifierRender extends TileEntitySpecialRenderer {

   private IModelCustom model;
   private static final ResourceLocation MODEL = new ResourceLocation("thaumcraft", "textures/models/node_stabilizer.obj");


   public TileTransductionAmplifierRender() {
      this.model = AdvancedModelLoader.loadModel(MODEL);
   }

   public void renderTileEntityAt(TileTransductionAmplifier tile, double par2, double par4, double par6, float par8) {
      int bright = 20;
      GL11.glPushMatrix();
      if(tile.getWorldObj() != null) {
         bright = tile.getBlockType().getMixedBrightnessForBlock(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
         tile.direction = (byte)tile.getBlockMetadata();
         if(tile.direction == 3) {
            GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.5F, (float)par6 + 1.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         } else if(tile.direction == 4) {
            GL11.glTranslatef((float)par2, (float)par4 + 0.5F, (float)par6 + 0.5F);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         } else if(tile.direction == 5) {
            GL11.glTranslatef((float)par2 + 1.0F, (float)par4 + 0.5F, (float)par6 + 0.5F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
         } else if(tile.direction == 2) {
            GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.5F, (float)par6);
            GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
         }
      } else {
         GL11.glTranslatef((float)par2 + 0.75F, (float)par4 + 0.25F, (float)par6);
      }

      UtilsFX.bindTexture("textures/models/node_converter.png");
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float v = (float)Math.min(50, tile.count) / 137.0F;
      this.model.renderPart("lock");
      GL11.glColor4f(0.8F, 0.8F, 0.0F, 1.0F);
      int j;
      int k;
      if(tile.getWorldObj() != null) {
         float a = MathHelper.sin((float)Minecraft.getMinecraft().renderViewEntity.ticksExisted / 3.0F) * 0.1F + 0.9F;
         int scale = 50 + (int)(170.0F * v * 2.5F * a);
         j = scale % 65536;
         k = scale / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
      }

      UtilsFX.bindTexture("textures/models/node_converter_over.png");
      this.model.renderPart("lock");
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

      for(int var16 = 0; var16 < 4; ++var16) {
         GL11.glPushMatrix();
         if(tile.getWorldObj() != null) {
            j = bright % 65536;
            k = bright / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
         }

         GL11.glRotatef((float)(90 * var16), 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.0F, 0.0F, v);
         UtilsFX.bindTexture("textures/models/node_converter.png");
         this.model.renderPart("piston");
         GL11.glColor4f(0.8F, 0.8F, 0.0F, 1.0F);
         if(tile.getWorldObj() != null) {
            float var17 = MathHelper.sin((float)(Minecraft.getMinecraft().renderViewEntity.ticksExisted + var16 * 5) / 3.0F) * 0.1F + 0.9F;
            j = 50 + (int)(170.0F * v * 2.5F * var17);
            k = j % 65536;
            int l = j / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
         }

         UtilsFX.bindTexture("textures/models/node_converter_over.png");
         this.model.renderPart("piston");
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPopMatrix();
      }

      GL11.glPopMatrix();
   }

   public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
      this.renderTileEntityAt((TileTransductionAmplifier)par1TileEntity, par2, par4, par6, par8);
   }

}
