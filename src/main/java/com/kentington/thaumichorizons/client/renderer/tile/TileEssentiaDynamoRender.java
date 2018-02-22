package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.fx.FXEssentiaTrail;
import com.kentington.thaumichorizons.client.renderer.model.ModelQuarterBlock;
import com.kentington.thaumichorizons.common.tiles.TileEssentiaDynamo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.lib.UtilsFX;

public class TileEssentiaDynamoRender extends TileEntitySpecialRenderer {

   private IModelCustom model;
   private static final ResourceLocation SCANNER = new ResourceLocation("thaumcraft", "textures/models/scanner.obj");
   static String tx1 = "textures/models/thaumiumring.png";
   static String tx2 = "textures/models/dynamoessentiabase.png";
   static String tx3 = "textures/items/lightningringv.png";
   private ModelQuarterBlock base;


   public TileEssentiaDynamoRender() {
      this.model = AdvancedModelLoader.loadModel(SCANNER);
      this.base = new ModelQuarterBlock();
   }

   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      TileEssentiaDynamo tco = (TileEssentiaDynamo)te;
      if(tco.essentia != null && tco.ticksProvided > 0) {
         FXEssentiaTrail nt = new FXEssentiaTrail(tco.getWorldObj(), (double)tco.xCoord + 0.5D, (double)tco.yCoord, (double)tco.zCoord + 0.5D, (double)tco.xCoord + 0.5D, (double)tco.yCoord + 0.5D, (double)tco.zCoord + 0.5D, Minecraft.getMinecraft().thePlayer.ticksExisted, tco.essentia.getColor(), 0.3F);
         nt.noClip = true;
         ParticleEngine.instance.addEffect(tco.getWorldObj(), nt);
      }

      if(tco.rise >= 0.3F && tco.ticksProvided > 0) {
         GL11.glPushMatrix();
         GL11.glAlphaFunc(516, 0.003921569F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 1);
         long nt1 = System.nanoTime();
         UtilsFX.bindTexture(tx3);
         int frames = UtilsFX.getTextureAnimationSize(tx3);
         int i = (int)(((double)(nt1 / 40000000L) + x) % (double)frames);
         UtilsFX.renderFacingQuad((double)tco.xCoord + 0.5D, (double)((float)tco.yCoord + 0.5F), (double)tco.zCoord + 0.5D, 0.0F, 0.2F, 0.9F, frames, i, f, tco.essentia.getColor());
         GL11.glDisable(3042);
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glPopMatrix();
      }

      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y, (float)z);
      UtilsFX.bindTexture("thaumichorizons", tx2);
      this.base.render();
      GL11.glTranslatef(0.5F, 0.2F + tco.rise, 0.5F);
      GL11.glRotatef(tco.rotation, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(tco.rotation2, 1.0F, 0.0F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      GL11.glScalef(0.36F, 0.36F, 0.36F);
      this.model.renderAll();
      GL11.glRotatef(-2.0F * tco.rotation2, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(0.5F, 1.0F, 0.5F);
      this.model.renderAll();
      GL11.glPopMatrix();
   }

}
