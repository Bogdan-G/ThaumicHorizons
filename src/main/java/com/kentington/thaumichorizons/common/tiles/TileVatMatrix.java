package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.tiles.TileVat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.visnet.TileVisNode;
import thaumcraft.api.wands.IWandable;

public class TileVatMatrix extends TileVisNode implements IWandable, IAspectContainer {

   public TileVat getVat() {
      if(super.worldObj == null) {
         return null;
      } else {
         TileEntity t = super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord);
         return t != null && t instanceof TileVat?(TileVat)t:null;
      }
   }

   public AspectList getAspects() {
      TileVat t = this.getVat();
      return t != null?t.getAspects():null;
   }

   public void setAspects(AspectList aspects) {}

   public boolean doesContainerAccept(Aspect tag) {
      return false;
   }

   public int addToContainer(Aspect tag, int amount) {
      return 0;
   }

   public boolean takeFromContainer(Aspect tag, int amount) {
      return false;
   }

   public boolean takeFromContainer(AspectList ot) {
      return false;
   }

   public boolean doesContainerContainAmount(Aspect tag, int amount) {
      return false;
   }

   public boolean doesContainerContain(AspectList ot) {
      return false;
   }

   public int containerContains(Aspect tag) {
      return 0;
   }

   public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
      this.onWandRightClick(world, wandstack, player);
      return 0;
   }

   public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
      TileVat t = this.getVat();
      if(t != null && (t.isValidInfusionTarget() && t.mode == 0 || t.mode == 4)) {
         t.startInfusion(player);
      }

      return wandstack;
   }

   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}

   public int getRange() {
      return 8;
   }

   public boolean isSource() {
      return false;
   }
}
