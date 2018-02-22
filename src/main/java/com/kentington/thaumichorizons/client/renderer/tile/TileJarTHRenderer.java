package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.common.tiles.TileSoulJar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.models.ModelJar;

@SideOnly(Side.CLIENT)
public class TileJarTHRenderer extends TileEntitySpecialRenderer {

   private ModelJar model = new ModelJar();
   static String tx3 = "textures/misc/soul.png";


   public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
      if(tile instanceof TileSoulJar) {
         TileSoulJar th = (TileSoulJar)tile;
         if(th.jarTag != null && th.jarTag.getBoolean("isSoul")) {
            long f11 = System.nanoTime();
            UtilsFX.bindTexture("thaumichorizons", tx3);
            GL11.glEnable(3042);
            GL11.glAlphaFunc(516, 0.003921569F);
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            UtilsFX.renderFacingQuad((double)tile.xCoord + 0.5D, (double)tile.yCoord + 0.4D, (double)tile.zCoord + 0.5D, 0.0F, 0.1F, 0.9F, 16, (int)(f11 / 40000000L % 16L), f, 16777215);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glEnable(2884);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
         } else {
            GL11.glPushMatrix();
            GL11.glDisable(2884);
            GL11.glTranslatef((float)x + 0.5F, (float)y + 0.01F, (float)z + 0.5F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.bindTexture(th.getTexture());
            if(th.entity != null) {
               float f1 = 0.25F;
               GL11.glScalef(f1, f1, f1);
               th.entity.setLocationAndAngles((double)th.xCoord + 0.5D, (double)th.yCoord + 0.5D, (double)th.zCoord + 0.5D, 0.0F, 0.0F);
               Render render = null;
               render = RenderManager.instance.getEntityRenderObject(th.entity);
               if(render != null) {
                  render.doRender(th.entity, 0.0D, 0.0D, 0.0D, 0.0F, f);
               }
            }

            GL11.glEnable(2884);
            GL11.glPopMatrix();
         }
      }
   }

}
