package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelVortexAttenuator;
import com.kentington.thaumichorizons.common.tiles.TileVortexStabilizer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileVortexStabilizerRender extends TileEntitySpecialRenderer {

   private ModelVortexAttenuator model = new ModelVortexAttenuator();
   static String tx1 = "textures/models/attenuator.png";


   public void renderTileEntityAt(TileEntity p_147500_1_, double x, double y, double z, float p_147500_8_) {
      TileVortexStabilizer te = (TileVortexStabilizer)p_147500_1_;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
      switch(te.blockMetadata) {
      case 0:
         GL11.glTranslatef(0.0F, -0.5F, 0.0F);
         break;
      case 1:
         GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
         GL11.glTranslatef(0.0F, -1.5F, 0.0F);
         break;
      case 2:
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.5F, -1.0F, 0.0F);
         break;
      case 3:
         GL11.glRotatef(270.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(-0.5F, -1.0F, 0.0F);
         break;
      case 4:
         GL11.glRotatef(270.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.0F, -1.0F, -0.5F);
         break;
      case 5:
         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.0F, -1.0F, 0.5F);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable('\u803a');
      GL11.glPopMatrix();
      UtilsFX.bindTexture("thaumichorizons", tx1);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

}
