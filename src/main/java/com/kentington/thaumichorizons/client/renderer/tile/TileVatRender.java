package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelVat;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileVatRender extends TileEntitySpecialRenderer {

   private static ModelVat model = new ModelVat();
   private String tx1 = "textures/models/vat.png";


   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 0.5F, (float)y - 0.5F, (float)z + 0.5F);
      UtilsFX.bindTexture("thaumichorizons", this.tx1);
      model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glEnable('\u803a');
      GL11.glPopMatrix();
   }

}
