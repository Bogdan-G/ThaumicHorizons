package com.kentington.thaumichorizons.common.items.lenses;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.lenses.ILens;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class ItemLensAir extends Item implements ILens {

   ResourceLocation tex = new ResourceLocation("thaumichorizons", "textures/fx/ripple.png");
   ResourceLocation tex2 = new ResourceLocation("thaumichorizons", "textures/fx/vortex.png");
   IIcon icon;


   public ItemLensAir() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public String lensName() {
      return "LensAir";
   }

   @SideOnly(Side.CLIENT)
   public void handleRender(Minecraft mc, float partialTicks) {
      if(mc.gameSettings.thirdPersonView <= 0) {
         EntityClientPlayerMP player = mc.thePlayer;
         List critters = player.worldObj.getEntitiesWithinAABBExcludingEntity(mc.thePlayer, AxisAlignedBB.getBoundingBox(player.posX - 24.0D, player.posY - 24.0D, player.posZ - 24.0D, player.posX + 24.0D, player.posY + 24.0D, player.posZ + 24.0D));
         Iterator var5 = critters.iterator();

         while(var5.hasNext()) {
            Entity ent = (Entity)var5.next();
            if(ent instanceof EntityLivingBase) {
               Vec3 look = player.getLookVec();
               if(look.yCoord == -1.0D) {
                  look.xCoord = -0.1D * Math.sin(Math.toRadians((double)player.rotationYaw));
                  look.yCoord = -0.999D;
                  look.zCoord = 0.1D * Math.cos(Math.toRadians((double)player.rotationYaw));
               } else if(look.yCoord == 1.0D) {
                  look.xCoord = -0.1D * Math.sin(Math.toRadians((double)player.rotationYaw));
                  look.yCoord = 0.999D;
                  look.zCoord = 0.1D * Math.cos(Math.toRadians((double)player.rotationYaw));
               }

               Vec3 playerPos = player.getPosition(partialTicks);
               playerPos.yCoord += (double)(player.yOffset - player.height + player.getEyeHeight());
               Vec3 entityPos = ((EntityLivingBase)ent).getPosition(partialTicks);
               entityPos.yCoord += (double)(ent.yOffset + ent.height / 2.0F);
               Vec3 pos = entityPos.subtract(playerPos);
               double scale = pos.lengthVector();
               double dot = pos.dotProduct(look);
               if(dot < 0.0D) {
                  Vec3 proj = Vec3.createVectorHelper(look.xCoord * dot, look.yCoord * dot, look.zCoord * dot);
                  Vec3 rej = Vec3.createVectorHelper(pos.xCoord - proj.xCoord, pos.yCoord - proj.yCoord, pos.zCoord - proj.zCoord);
                  Vec3 right = look.crossProduct(Vec3.createVectorHelper(1.0E-4D, -1.0D, 1.0E-4D));
                  right = right.normalize();
                  Vec3 up = right.crossProduct(look);
                  up = up.normalize();
                  double vert = rej.dotProduct(up);
                  double horiz = rej.dotProduct(right);
                  ScaledResolution var51 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
                  int var6 = var51.getScaledWidth();
                  int var7 = var51.getScaledHeight();
                  float minScreen = (float)Math.min(var6, var7);
                  double hScale = proj.lengthVector() * Math.tan(Math.toRadians((double)mc.gameSettings.fovSetting) / 2.0D) * 2.0D;
                  horiz /= hScale;
                  vert = vert / hScale / (double)var7 * (double)var6;
                  GL11.glPushMatrix();
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  GL11.glAlphaFunc(518, 0.005F);
                  float minEnt = Math.min(ent.width, ent.height);
                  double size = (double)minEnt * ((double)minScreen / scale);
                  double xCenter = (double)var6 * (1.0D + horiz) / 2.0D;
                  double yCenter = (double)var7 * (1.0D - vert) / 2.0D;
                  Tessellator t = Tessellator.instance;
                  if(((EntityLivingBase)ent).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                     FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.tex);
                     float angle = (float)(ent.ticksExisted % 16);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F - (float)scale / 80.0F);
                     int numRipples = (int)(48.0D / scale + 1.0D);
                     if(numRipples > 4) {
                        numRipples = 4;
                     }

                     for(int sin = 0; sin < numRipples; ++sin) {
                        double ripSize = size * (double)(((float)(sin * 16 / (numRipples + 1)) + angle) % 16.0F) / 12.0D;
                        t.startDrawingQuads();
                        t.addVertexWithUV(xCenter - ripSize, yCenter + ripSize, 1.0D, 0.0D, 1.0D);
                        t.addVertexWithUV(xCenter + ripSize, yCenter + ripSize, 1.0D, 1.0D, 1.0D);
                        t.addVertexWithUV(xCenter + ripSize, yCenter - ripSize, 1.0D, 1.0D, 0.0D);
                        t.addVertexWithUV(xCenter - ripSize, yCenter - ripSize, 1.0D, 0.0D, 0.0D);
                        t.draw();
                     }

                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  } else {
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.1F - (float)scale / 240.0F);
                     size *= 2.0D;
                     FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.tex2);
                     double var47 = Math.toRadians((double)(ent.ticksExisted % 360));
                     double var48 = Math.sin(var47);
                     double cos = Math.cos(var47);
                     GL11.glPushMatrix();
                     t.startDrawingQuads();
                     t.addVertexWithUV(xCenter - size * var48, yCenter + size * cos, 1.0D, 0.0D, 0.0D);
                     t.addVertexWithUV(xCenter + size * cos, yCenter + size * var48, 1.0D, 1.0D, 0.0D);
                     t.addVertexWithUV(xCenter + size * var48, yCenter - size * cos, 1.0D, 1.0D, 1.0D);
                     t.addVertexWithUV(xCenter - size * cos, yCenter - size * var48, 1.0D, 0.0D, 1.0D);
                     t.draw();
                     GL11.glPopMatrix();
                     double sin2 = Math.sin(-var47);
                     double cos2 = Math.cos(-var47);
                     GL11.glPushMatrix();
                     t.startDrawingQuads();
                     t.addVertexWithUV(xCenter - size * sin2 / 2.0D, yCenter + size * cos2 / 2.0D, 1.0D, 0.0D, 0.0D);
                     t.addVertexWithUV(xCenter + size * cos2 / 2.0D, yCenter + size * sin2 / 2.0D, 1.0D, 1.0D, 0.0D);
                     t.addVertexWithUV(xCenter + size * sin2 / 2.0D, yCenter - size * cos2 / 2.0D, 1.0D, 1.0D, 1.0D);
                     t.addVertexWithUV(xCenter - size * cos2 / 2.0D, yCenter - size * sin2 / 2.0D, 1.0D, 0.0D, 1.0D);
                     t.draw();
                     GL11.glPopMatrix();
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  }

                  GL11.glDisable(3042);
                  GL11.glPopMatrix();
               }
            }
         }

      }
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.LensAir";
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:lensair");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public void handleRemoval(EntityPlayer p) {}
}
