package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelReceptacle;
import com.kentington.thaumichorizons.common.tiles.TileSlot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

@SideOnly(Side.CLIENT)
public class TileSlotRender extends TileEntitySpecialRenderer {

   static String tx1 = "textures/models/receptacle.png";
   private ModelReceptacle base = new ModelReceptacle();


   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      TileSlot tco = (TileSlot)te;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 0.5F, (float)y - 0.5F, (float)z + 0.5F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      this.base.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, tco.hasKeystone, tco.pocketID);
      GL11.glPopMatrix();
   }

}
