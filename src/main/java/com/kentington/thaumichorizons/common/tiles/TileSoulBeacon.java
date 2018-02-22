package com.kentington.thaumichorizons.common.tiles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.common.Thaumcraft;

public class TileSoulBeacon extends TileThaumcraft {

   @SideOnly(Side.CLIENT)
   private float field_146014_j;
   @SideOnly(Side.CLIENT)
   private long field_146016_i;


   public boolean activate(EntityPlayer p) {
      p.getEntityData().setBoolean("soulBeacon", true);
      p.getEntityData().setIntArray("soulBeaconCoords", new int[]{super.xCoord, super.yCoord, super.zCoord});
      p.getEntityData().setInteger("soulBeaconDim", super.worldObj.provider.dimensionId);
      p.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + StatCollector.translateToLocal("thaumichorizons.setBeacon")));
      Thaumcraft.proxy.arcLightning(super.worldObj, p.posX, p.posY + (double)p.getEyeHeight(), p.posZ, (double)super.xCoord + 0.5D, (double)super.yCoord + 0.75D, (double)super.zCoord + 0.5D, 0.05F, 1.0F, 0.05F, 0.5F);
      super.worldObj.playSoundEffect((double)super.xCoord + 0.5D, (double)super.yCoord + 0.75D, (double)super.zCoord + 0.5D, "thaumcraft:zap", 1.0F, 1.0F);
      return true;
   }

   @SideOnly(Side.CLIENT)
   public float func_146002_i() {
      int i = (int)(super.worldObj.getTotalWorldTime() - this.field_146016_i);
      this.field_146016_i = super.worldObj.getTotalWorldTime();
      if(i > 1) {
         this.field_146014_j -= (float)i / 40.0F;
         if(this.field_146014_j < 0.0F) {
            this.field_146014_j = 0.0F;
         }
      }

      this.field_146014_j += 0.025F;
      if(this.field_146014_j > 1.0F) {
         this.field_146014_j = 1.0F;
      }

      return this.field_146014_j;
   }

   @SideOnly(Side.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 65536.0D;
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return TileEntity.INFINITE_EXTENT_AABB;
   }
}
