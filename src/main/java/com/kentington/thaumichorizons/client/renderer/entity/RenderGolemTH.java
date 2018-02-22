package com.kentington.thaumichorizons.client.renderer.entity;

import com.kentington.thaumichorizons.client.renderer.model.ModelGolemTH;
import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.entity.RenderGolemBase;
import thaumcraft.client.renderers.models.entities.ModelGolemAccessories;
import thaumcraft.common.config.ConfigItems;

public class RenderGolemTH extends RenderGolemBase {

   ResourceLocation voidGolem = new ResourceLocation("thaumichorizons", "textures/models/golem_void.png");
   ModelBase damage;
   ModelBase accessories = new ModelGolemAccessories(0.0F, 30.0F);


   public RenderGolemTH(ModelBase arg0) {
      super(arg0);
      if(arg0 instanceof ModelGolemTH) {
         ModelGolemTH mg = new ModelGolemTH(false);
         mg.pass = 2;
         this.damage = mg;
      }

   }

   protected ResourceLocation func_110775_a(Entity entity) {
      if(!(entity instanceof EntityGolemTH)) {
         return null;
      } else {
         EntityGolemTH golem = (EntityGolemTH)entity;
         if(golem.texture == null && golem.blocky != null && golem.blocky != Blocks.air) {
            golem.loadTexture();
         } else if(golem.texture == null) {
            return this.voidGolem;
         }

         return golem.texture;
      }
   }

   public void render(EntityGolemTH e, double par2, double par4, double par6, float par8, float par9) {
      super.doRender(e, par2, par4, par6, par8, par9);
   }

   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
      this.render((EntityGolemTH)par1EntityLiving, par2, par4, par6, par8, par9);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.render((EntityGolemTH)par1Entity, par2, par4, par6, par8, par9);
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float par3) {
      if(pass == 0) {
         String deco = ((EntityGolemTH)entity).getGolemDecoration();
         float f1;
         if(((EntityGolemTH)entity).getCore() > -1) {
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0875F, -0.96F, 0.15F + (deco.contains("P")?0.03F:0.0F));
            GL11.glScaled(0.175D, 0.175D, 0.175D);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            Tessellator upgrades = Tessellator.instance;
            IIcon shift = ConfigItems.itemGolemCore.getIconFromDamage(((EntityGolemTH)entity).getCore());
            float a = shift.getMaxU();
            float tessellator = shift.getMinV();
            float icon = shift.getMinU();
            f1 = shift.getMaxV();
            this.renderManager.renderEngine.bindTexture(TextureMap.locationItemsTexture);
            ItemRenderer.renderItemIn2D(upgrades, a, tessellator, icon, f1, shift.getIconWidth(), shift.getIconHeight(), 0.2F);
            GL11.glPopMatrix();
         }

         int var14 = ((EntityGolemTH)entity).upgrades.length;
         float var15 = 0.08F;
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);

         for(int var16 = 0; var16 < var14; ++var16) {
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(-0.05F - var15 * (float)(var14 - 1) / 2.0F + var15 * (float)var16, -1.106F, 0.099F);
            GL11.glScaled(0.1D, 0.1D, 0.1D);
            Tessellator var17 = Tessellator.instance;
            IIcon var18 = ConfigItems.itemGolemUpgrade.getIconFromDamage(((EntityGolemTH)entity).getUpgrade(var16));
            f1 = var18.getMaxU();
            float f2 = var18.getMinV();
            float f3 = var18.getMinU();
            float f4 = var18.getMaxV();
            this.renderManager.renderEngine.bindTexture(TextureMap.locationItemsTexture);
            var17.startDrawingQuads();
            var17.setNormal(0.0F, 0.0F, 1.0F);
            var17.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)f1, (double)f4);
            var17.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)f3, (double)f4);
            var17.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)f3, (double)f2);
            var17.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)f1, (double)f2);
            var17.draw();
            GL11.glPopMatrix();
         }

         GL11.glDisable(3042);
         GL11.glPopMatrix();
      } else {
         if(pass == 1 && (((EntityGolemTH)entity).getGolemDecoration().length() > 0 || ((EntityGolemTH)entity).advanced)) {
            UtilsFX.bindTexture("textures/models/golem_decoration.png");
            this.setRenderPassModel(this.accessories);
            return 1;
         }

         if(pass == 2 && ((EntityGolemTH)entity).getHealthPercentage() < 1.0F) {
            UtilsFX.bindTexture("textures/models/golem_damage.png");
            this.setRenderPassModel(this.damage);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - ((EntityGolemTH)entity).getHealthPercentage());
            return 2;
         }
      }

      return -1;
   }
}
