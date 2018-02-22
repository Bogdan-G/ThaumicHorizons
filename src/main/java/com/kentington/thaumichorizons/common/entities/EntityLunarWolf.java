package com.kentington.thaumichorizons.common.entities;

import baubles.api.BaublesApi;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.baubles.ItemAmuletVis;
import thaumcraft.common.items.wands.ItemWandCasting;

public class EntityLunarWolf extends EntityWolf {

   public EntityLunarWolf(World p_i1696_1_) {
      super(p_i1696_1_);
   }

   public void updateAITick() {
      super.updateAITick();
      if(!super.worldObj.isDaytime()) {
         int tix = (int)(7.0F - super.worldObj.getCurrentMoonPhaseFactor() * 4.0F);
         if(super.ticksExisted % tix == 0) {
            List players = super.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(super.posX - 5.0D, super.posY - 5.0D, super.posZ - 5.0D, super.posX + 5.0D, super.posY + 5.0D, super.posZ + 5.0D));
            Iterator var3 = players.iterator();

            while(var3.hasNext()) {
               EntityPlayer player = (EntityPlayer)var3.next();
               int var8;
               if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWandCasting) {
                  ItemWandCasting al = (ItemWandCasting)player.getHeldItem().getItem();
                  AspectList al1 = al.getAspectsWithRoom(player.getHeldItem());
                  Aspect[] var7 = al1.getAspects();
                  var8 = var7.length;

                  for(int aspect = 0; aspect < var8; ++aspect) {
                     Aspect aspect1 = var7[aspect];
                     if(aspect1 != null) {
                        al.storeVis(player.getHeldItem(), aspect1, this.getVis(player.getHeldItem(), aspect1) + 1);
                     }
                  }
               }

               if(BaublesApi.getBaubles(player).getStackInSlot(0) != null && BaublesApi.getBaubles(player).getStackInSlot(0).getItem() == ConfigItems.itemAmuletVis) {
                  AspectList var11 = ((ItemAmuletVis)ConfigItems.itemAmuletVis).getAspectsWithRoom(BaublesApi.getBaubles(player).getStackInSlot(0));
                  Aspect[] var12 = var11.getAspects();
                  int var13 = var12.length;

                  for(var8 = 0; var8 < var13; ++var8) {
                     Aspect var14 = var12[var8];
                     if(var14 != null) {
                        ((ItemAmuletVis)ConfigItems.itemAmuletVis).addRealVis(BaublesApi.getBaubles(player).getStackInSlot(0), var14, 1, true);
                     }
                  }
               }
            }
         }

      }
   }

   public int getVis(ItemStack is, Aspect aspect) {
      int out = 0;
      if(is.hasTagCompound() && is.stackTagCompound.hasKey(aspect.getTag())) {
         out = is.stackTagCompound.getInteger(aspect.getTag());
      }

      return out;
   }
}
