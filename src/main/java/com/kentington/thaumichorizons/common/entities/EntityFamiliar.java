package com.kentington.thaumichorizons.common.entities;

import baubles.api.BaublesApi;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.baubles.ItemAmuletVis;
import thaumcraft.common.items.wands.ItemWandCasting;

public class EntityFamiliar extends EntityOcelot {

   public EntityFamiliar(World p_i1688_1_) {
      super(p_i1688_1_);
   }

   public String getCommandSenderName() {
      return this.hasCustomNameTag()?this.getCustomNameTag():(this.isTamed()?StatCollector.translateToLocal("entity.ThaumicHorizons.Familiar.name"):super.getCommandSenderName());
   }

   public void updateAITick() {
      super.updateAITick();
      if(super.ticksExisted % 10 == 0) {
         List players = super.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(super.posX - 5.0D, super.posY - 5.0D, super.posZ - 5.0D, super.posX + 5.0D, super.posY + 5.0D, super.posZ + 5.0D));
         Iterator var2 = players.iterator();

         while(var2.hasNext()) {
            EntityPlayer player = (EntityPlayer)var2.next();
            int var7;
            if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWandCasting) {
               ItemWandCasting al = (ItemWandCasting)player.getHeldItem().getItem();
               AspectList al1 = al.getAspectsWithRoom(player.getHeldItem());
               Aspect[] var6 = al1.getAspects();
               var7 = var6.length;

               for(int aspect = 0; aspect < var7; ++aspect) {
                  Aspect aspect1 = var6[aspect];
                  if(aspect1 != null) {
                     al.storeVis(player.getHeldItem(), aspect1, this.getVis(player.getHeldItem(), aspect1) + 1);
                  }
               }
            }

            if(BaublesApi.getBaubles(player).getStackInSlot(0) != null && BaublesApi.getBaubles(player).getStackInSlot(0).getItem() == ConfigItems.itemAmuletVis) {
               AspectList var10 = ((ItemAmuletVis)ConfigItems.itemAmuletVis).getAspectsWithRoom(BaublesApi.getBaubles(player).getStackInSlot(0));
               Aspect[] var11 = var10.getAspects();
               int var12 = var11.length;

               for(var7 = 0; var7 < var12; ++var7) {
                  Aspect var13 = var11[var7];
                  if(var13 != null) {
                     ((ItemAmuletVis)ConfigItems.itemAmuletVis).addRealVis(BaublesApi.getBaubles(player).getStackInSlot(0), var13, 1, true);
                  }
               }
            }
         }
      }

   }

   protected void entityInit() {
      super.entityInit();
      byte b0 = super.dataWatcher.getWatchableObjectByte(16);
      super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 4)));
   }

   public boolean isTamed() {
      return true;
   }

   public int getVis(ItemStack is, Aspect aspect) {
      int out = 0;
      if(is.hasTagCompound() && is.stackTagCompound.hasKey(aspect.getTag())) {
         out = is.stackTagCompound.getInteger(aspect.getTag());
      }

      return out;
   }
}
