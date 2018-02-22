package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileSpike;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileSpikeRender extends TileEntitySpecialRenderer {

   private IModelCustom model;
   private static final ResourceLocation SPIKE = new ResourceLocation("thaumichorizons", "textures/models/spike.obj");
   static String tx1 = "textures/models/metalspike.png";
   static String tx2 = "textures/models/woodenspike.png";
   static String tx3 = "textures/models/toothspike.png";


   public TileSpikeRender() {
      this.model = AdvancedModelLoader.loadModel(SPIKE);
   }

   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      TileSpike tco = (TileSpike)te;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 0.5F, (float)(y + 0.5D), (float)z + 0.5F);
      GL11.glScalef(0.35F, 0.35F, 0.35F);
      switch(tco.direction) {
      case 0:
         GL11.glTranslatef(0.0F, 1.45F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         break;
      case 1:
         GL11.glTranslatef(0.0F, -1.45F, 0.0F);
         break;
      case 2:
         GL11.glTranslatef(0.0F, 0.0F, 1.45F);
         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
         break;
      case 3:
         GL11.glTranslatef(0.0F, 0.0F, -1.45F);
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         break;
      case 4:
         GL11.glTranslatef(1.45F, 0.0F, 0.0F);
         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
         break;
      case 5:
         GL11.glTranslatef(-1.45F, 0.0F, 0.0F);
         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      }

      if(tco.spikeType == 0) {
         UtilsFX.bindTexture("thaumichorizons", tx1);
      } else if(tco.spikeType == 1) {
         UtilsFX.bindTexture("thaumichorizons", tx2);
      } else {
         UtilsFX.bindTexture("thaumichorizons", tx3);
      }

      this.model.renderAll();
      GL11.glPopMatrix();
   }

}
