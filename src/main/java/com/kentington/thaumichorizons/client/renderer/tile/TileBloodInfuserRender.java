package com.kentington.thaumichorizons.client.renderer.tile;

import com.kentington.thaumichorizons.client.renderer.model.ModelBloodInfuser;
import com.kentington.thaumichorizons.client.renderer.model.ModelSyringe;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileBloodInfuser;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;

public class TileBloodInfuserRender extends TileEntitySpecialRenderer {

   static String tx1 = "textures/models/bloodinfuser.png";
   private ModelBloodInfuser base = new ModelBloodInfuser();
   static String tx2 = "textures/models/syringe.png";
   private ModelSyringe syringe = new ModelSyringe();


   public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
      TileBloodInfuser tco = (TileBloodInfuser)te;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      UtilsFX.bindTexture("thaumichorizons", tx1);
      this.base.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
      if(tco.hasBlood()) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)x + 1.90625F, (float)y + 0.75F, (float)z + 0.46875F);
         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
         UtilsFX.bindTexture("thaumichorizons", tx2);
         this.syringe.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, new ItemStack(ThaumicHorizons.itemSyringeHuman));
         GL11.glPopMatrix();
      }

   }

}
