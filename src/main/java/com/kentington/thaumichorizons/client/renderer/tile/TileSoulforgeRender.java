package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelSoulforge;
import com.kentington.thaumichorizons.common.tiles.TileSoulforge;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.models.ModelBrain;

public class TileSoulforgeRender extends TileEntitySpecialRenderer {

   private ModelBrain brain = new ModelBrain();
   private ModelSoulforge forge = new ModelSoulforge();
   static String tx1 = "textures/models/soulforge.png";
   static String tx2 = "textures/items/lightningringv.png";
   static String tx3 = "textures/misc/soul.png";


   public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -0.25F, 0.0F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      this.renderBrains((TileSoulforge)tile, x, y, z, f);
      GL11.glEnable('\u803a');
      GL11.glPopMatrix();
      GL11.glTranslatef(0.0F, -1.5F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      this.forge.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
      long nt = System.nanoTime();
      if(((TileSoulforge)tile).forging > 0) {
         int frames = UtilsFX.getTextureAnimationSize(tx2);
         int i = (int)(((double)(nt / 40000000L) + x) % (double)frames);
         UtilsFX.bindTexture("thaumcraft", tx2);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glScalef(1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         UtilsFX.renderFacingQuad((double)tile.xCoord + 0.025D, (double)tile.yCoord + 0.75D, (double)tile.zCoord + 0.025D, 0.0F, 0.2F, 0.9F, frames, i, f, 16777215);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         UtilsFX.renderFacingQuad((double)tile.xCoord + 0.975D, (double)tile.yCoord + 0.75D, (double)tile.zCoord + 0.025D, 0.0F, 0.2F, 0.9F, frames, i, f, 16777215);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         UtilsFX.renderFacingQuad((double)tile.xCoord + 0.975D, (double)tile.yCoord + 0.75D, (double)tile.zCoord + 0.975D, 0.0F, 0.2F, 0.9F, frames, i, f, 16777215);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         UtilsFX.renderFacingQuad((double)tile.xCoord + 0.025D, (double)tile.yCoord + 0.75D, (double)tile.zCoord + 0.975D, 0.0F, 0.2F, 0.9F, frames, i, f, 16777215);
         GL11.glPopMatrix();
         GL11.glDisable(3042);
      }

      if(((TileSoulforge)tile).souls > 0) {
         double offset = 6.283185307179586D / (double)((TileSoulforge)tile).souls;
         byte var20 = 16;
         double radian = Math.toRadians((double)((int)(nt / 40000000L % 360L)));
         double dist = 0.1D + 0.1D * Math.cos(radian);
         UtilsFX.bindTexture("thaumichorizons", tx3);
         GL11.glEnable(3042);
         GL11.glAlphaFunc(516, 0.003921569F);
         GL11.glDisable(2929);
         GL11.glDisable(2884);

         for(int which = 0; which < ((TileSoulforge)tile).souls; ++which) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            UtilsFX.renderFacingQuad((double)tile.xCoord + 0.5D + dist * Math.sin(2.0D * radian + offset * (double)which), (double)tile.yCoord + 0.85D, (double)tile.zCoord + 0.5D + dist * Math.cos(2.0D * radian + offset * (double)which), 0.0F, 0.1F, 0.9F, var20, (int)(nt / 40000000L % (long)var20), f, 16777215);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
         }

         GL11.glEnable(2884);
         GL11.glEnable(2929);
         GL11.glDisable(3042);
      }

   }

   public void renderBrains(TileSoulforge te, double x, double y, double z, float f) {
      GL11.glPushMatrix();
      float f2;
      if(te != null) {
         f2 = te.rota;
         GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
      }

      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(0.0F, -0.55F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", "textures/models/brain.png");
      GL11.glScalef(0.4F, 0.4F, 0.4F);
      this.brain.render();
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      if(te != null) {
         f2 = te.rota;
         GL11.glRotatef(f2, 0.0F, 1.0F, 0.0F);
      }

      GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(0.0F, -0.55F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", "textures/models/brain.png");
      GL11.glScalef(0.4F, 0.4F, 0.4F);
      this.brain.render();
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      GL11.glTranslatef(0.0F, -1.25F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", "textures/models/jarbrine2.png");
      this.forge.renderBrine();
   }

}
