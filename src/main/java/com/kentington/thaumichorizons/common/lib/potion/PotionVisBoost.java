package com.kentington.thaumichorizons.common.lib.potion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionVisBoost extends Potion {

   public static PotionVisBoost instance = null;
   private int statusIconIndex = 4;
   static final ResourceLocation rl = new ResourceLocation("thaumichorizons", "textures/misc/potions.png");


   public PotionVisBoost(int par1, boolean par2, int par3) {
      super(par1, par2, par3);
      this.setIconIndex(4, 0);
   }

   public static void init() {
      instance.setPotionName("potion.visboost");
      instance.setIconIndex(4, 0);
      instance.setEffectiveness(0.25D);
   }

   public boolean isBadEffect() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public int getStatusIconIndex() {
      Minecraft.getMinecraft().renderEngine.bindTexture(rl);
      return super.getStatusIconIndex();
   }

   public void performEffect(EntityLivingBase target, int par2) {}

}
