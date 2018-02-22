package com.kentington.thaumichorizons.common.lib.potion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionVisRegen extends Potion {

   public static PotionVisRegen instance = null;
   private int statusIconIndex = 3;
   static final ResourceLocation rl = new ResourceLocation("thaumichorizons", "textures/misc/potions.png");


   public PotionVisRegen(int par1, boolean par2, int par3) {
      super(par1, par2, par3);
      this.setIconIndex(3, 0);
   }

   public static void init() {
      instance.setPotionName("potion.visregen");
      instance.setIconIndex(3, 0);
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
