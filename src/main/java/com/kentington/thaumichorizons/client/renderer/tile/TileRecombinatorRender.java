package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelRecombinator;
import com.kentington.thaumichorizons.common.tiles.TileRecombinator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.tiles.TileNode;

@SideOnly(Side.CLIENT)
public class TileRecombinatorRender extends TileEntitySpecialRenderer {

   static String tx1 = "textures/models/recombinator.png";
   static String tx2 = "textures/items/lightningringv.png";
   private ModelRecombinator base = new ModelRecombinator();


   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      TileRecombinator tco = (TileRecombinator)te;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 0.5F, (float)y - 0.5F, (float)z + 0.5F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      float sin = (float)Math.sin((double)((float)tco.count / 8.0F));
      this.base.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, sin * 2.0F);
      if(tco.activated) {
         long nt = System.nanoTime();
         int frames = UtilsFX.getTextureAnimationSize(tx2);
         int i = (int)(((double)(nt / 40000000L) + x) % (double)frames);
         UtilsFX.bindTexture("thaumcraft", tx2);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glScalef(1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         UtilsFX.renderFacingQuad((double)tco.xCoord + 0.5D, (double)tco.yCoord + 1.5D + 0.2D * (double)sin, (double)tco.zCoord + 0.5D, 0.0F, 1.5F, 0.9F, frames, i, f, 16777215);
         GL11.glPopMatrix();
         if(te.getWorldObj().getTileEntity(te.xCoord, te.yCoord - 1, te.zCoord) instanceof TileNode) {
            double var10002 = (double)te.xCoord + 0.5D;
            double var10003 = (double)te.yCoord + 0.2D;
            double var10004 = (double)te.zCoord + 0.5D;
            double var10005 = (double)te.xCoord + 0.5D;
            double var10006 = (double)te.yCoord - 0.5D;
            Thaumcraft.proxy.beam(Minecraft.getMinecraft().theWorld, var10002, var10003, var10004, var10005, var10006, (double)te.zCoord + 0.5D, 1, 16777215, false, 0.1F, 3);
         }

         GL11.glDisable(3042);
      }

      GL11.glPopMatrix();
   }

}
