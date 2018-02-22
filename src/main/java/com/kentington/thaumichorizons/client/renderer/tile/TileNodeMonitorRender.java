package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileNodeMonitor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileNodeMonitorRender extends TileEntitySpecialRenderer {

   private IModelCustom model;
   private IModelCustom modelScreen;
   private static final ResourceLocation SCANNER = new ResourceLocation("thaumcraft", "textures/models/scanner.obj");
   private static final ResourceLocation SCANNERSCREEN = new ResourceLocation("thaumichorizons", "textures/models/hexagon.obj");
   static String tx1 = "textures/models/nodemon.png";
   static String tx2 = "textures/models/nodemonscreen.png";
   static String tx3 = "textures/models/nodemonscreenactive.png";


   public TileNodeMonitorRender() {
      this.model = AdvancedModelLoader.loadModel(SCANNER);
      this.modelScreen = AdvancedModelLoader.loadModel(SCANNERSCREEN);
   }

   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      TileNodeMonitor tco = (TileNodeMonitor)te;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 0.5F, (float)(y + 0.5D), (float)z + 0.5F);
      GL11.glScalef(0.4F, 0.4F, 0.4F);
      switch(tco.direction) {
      case 0:
         GL11.glTranslatef(0.0F, 0.8F, 0.0F);
         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef((float)tco.rotation, 0.0F, 1.0F, 0.0F);
         break;
      case 1:
         GL11.glTranslatef(0.0F, -0.8F, 0.0F);
         GL11.glRotatef((float)tco.rotation, 0.0F, 1.0F, 0.0F);
         break;
      case 2:
         GL11.glTranslatef(0.0F, 0.0F, 0.8F);
         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef((float)tco.rotation, 0.0F, 1.0F, 0.0F);
         break;
      case 3:
         GL11.glTranslatef(0.0F, 0.0F, -0.8F);
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef((float)tco.rotation, 0.0F, 1.0F, 0.0F);
         break;
      case 4:
         GL11.glTranslatef(0.8F, 0.0F, 0.0F);
         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef((float)tco.rotation, 0.0F, 1.0F, 0.0F);
         break;
      case 5:
         GL11.glTranslatef(-0.8F, 0.0F, 0.0F);
         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef((float)tco.rotation, 0.0F, 1.0F, 0.0F);
      }

      UtilsFX.bindTexture("thaumichorizons", tx1);
      this.model.renderAll();
      GL11.glEnable(2977);
      GL11.glEnable(3042);
      GL11.glEnable('\u803a');
      GL11.glBlendFunc(770, 771);
      if(tco.switchy) {
         UtilsFX.bindTexture("thaumichorizons", tx3);
      } else {
         UtilsFX.bindTexture("thaumichorizons", tx2);
      }

      this.modelScreen.renderAll();
      GL11.glPopMatrix();
   }

}
