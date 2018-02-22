package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelSoulSieve;
import com.kentington.thaumichorizons.common.tiles.TileSoulExtractor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileSoulSieveRender extends TileEntitySpecialRenderer {

   static String tx1 = "textures/models/soulsieve.png";
   private ModelSoulSieve model = new ModelSoulSieve();


   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      TileSoulExtractor tco = (TileSoulExtractor)te;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, 0.15625F * (float)(Math.cos(Math.toRadians((double)((float)tco.sieveMotion))) - 1.0D), (float)tco.ticksLeft);
      GL11.glPopMatrix();
   }

}
