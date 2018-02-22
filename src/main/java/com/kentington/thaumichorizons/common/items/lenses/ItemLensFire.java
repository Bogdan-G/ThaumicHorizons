package com.kentington.thaumichorizons.common.items.lenses;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.lenses.ILens;
import com.kentington.thaumichorizons.common.items.lenses.LensManager;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import com.kentington.thaumichorizons.common.lib.PacketRemoveNightvision;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public class ItemLensFire extends Item implements ILens {

   IIcon icon;


   public ItemLensFire() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public String lensName() {
      return "LensFire";
   }

   public void handleRender(Minecraft mc, float partialTicks) {
      boolean inWater = mc.thePlayer.isInsideOfMaterial(Material.water);
      if(!inWater && (mc.thePlayer.getActivePotionEffect(Potion.nightVision) == null || mc.thePlayer.getActivePotionEffect(Potion.nightVision).getDuration() < 242) && Minecraft.getSystemTime() > LensManager.nightVisionOffTime) {
         LensManager.nightVisionOffTime = Minecraft.getSystemTime();
         mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 255, 0, true));
      } else if(inWater) {
         mc.thePlayer.removePotionEffect(Potion.nightVision.id);
      }

   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.LensFire";
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:lensfire");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public void handleRemoval(EntityPlayer p) {
      p.removePotionEffect(Potion.nightVision.id);
      PacketHandler.INSTANCE.sendTo(new PacketRemoveNightvision(), (EntityPlayerMP)p);
   }
}
