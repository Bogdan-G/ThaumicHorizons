package com.kentington.thaumichorizons.client.renderer.model;

import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.models.entities.ModelGolem;
import thaumcraft.common.entities.golems.EntityGolemBase;

public class ModelGolemTH extends ModelGolem {

   public ModelGolemTH(boolean p) {
      super(p);
   }

   public void setRotationAngles(Entity en, float par1, float par2, float par3, float par4, float par5, float par6) {
      byte core = 0;
      float s;
      if(en instanceof EntityGolemBase) {
         core = ((EntityGolemBase)en).getCore();
         if(this.pass == 0 && ((EntityGolemBase)en).healing > 0) {
            s = (float)((EntityGolemBase)en).healing / 10.0F;
            float h2 = (float)((EntityGolemBase)en).healing / 5.0F;
            GL11.glColor3f(0.5F + s, 0.9F + h2, 0.5F + s);
         }
      }

      this.golemHead.rotateAngleY = par4 / 57.295776F;
      this.golemHead.rotateAngleX = par5 / 57.295776F;
      this.golemRightLeg.rotateAngleX = -1.5F * this.func_78172_a(par1, 13.0F) * par2;
      this.golemLeftLeg.rotateAngleX = 1.5F * this.func_78172_a(par1, 13.0F) * par2;
      this.golemRightLeg.rotateAngleY = 0.0F;
      this.golemLeftLeg.rotateAngleY = 0.0F;
      this.golemLeftArm.rotateAngleZ = 0.0F;
      this.golemRightArm.rotateAngleZ = 0.0F;
      if(core == 6) {
         s = (1.0F - (0.5F + (float)Math.min(64, ((EntityGolemBase)en).getCarryLimit()) / 128.0F)) * 25.0F;
         this.golemLeftArm.rotateAngleZ = s / 57.295776F;
         this.golemRightArm.rotateAngleZ = -s / 57.295776F;
      }

   }

   private float func_78172_a(float par1, float par2) {
      return (Math.abs(par1 % par2 - par2 * 0.5F) - par2 * 0.25F) / (par2 * 0.25F);
   }
}
