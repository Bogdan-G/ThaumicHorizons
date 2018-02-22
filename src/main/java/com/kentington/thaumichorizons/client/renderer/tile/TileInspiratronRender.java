package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelInspiratron;
import com.kentington.thaumichorizons.common.tiles.TileInspiratron;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.models.ModelBrain;
import thaumcraft.client.renderers.models.ModelJar;

public class TileInspiratronRender extends TileEntitySpecialRenderer {

   private ModelJar model = new ModelJar();
   private ModelBrain brain = new ModelBrain();
   private ModelInspiratron inspiratron = new ModelInspiratron();
   static String tx1 = "textures/models/inspiratron.png";


   public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -0.125F, 0.0F);
      this.renderBrain((TileInspiratron)tile, x, y, z, f);
      GL11.glEnable('\u803a');
      GL11.glPopMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glTranslatef(0.0F, -1.5F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      this.inspiratron.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public void renderBrain(TileInspiratron te, double x, double y, double z, float f) {
      float bob = MathHelper.sin((float)Minecraft.getMinecraft().thePlayer.ticksExisted / 14.0F) * 0.03F + 0.03F;
      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -0.8F + bob, 0.0F);
      if(te != null) {
         float f2;
         for(f2 = te.rota - te.rotb; f2 < -3.141593F; f2 += 6.283185F) {
            ;
         }

         float f3 = te.rotb + f2 * f;
         GL11.glRotatef(f3 * 180.0F / 3.141593F, 0.0F, 1.0F, 0.0F);
      }

      GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", "textures/models/brain.png");
      GL11.glScalef(0.4F, 0.4F, 0.4F);
      this.brain.render();
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
      UtilsFX.bindTexture("thaumichorizons", "textures/models/jarbrine.png");
      this.model.renderBrine();
   }

}
