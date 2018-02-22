package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;

public class ItemSyringeBloodSample extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon;


   public ItemSyringeBloodSample() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:syringeBlood");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getItemStackDisplayName(ItemStack stack) {
      return stack.hasTagCompound()?StatCollector.translateToLocal("item.syringeSample.name") + ": " + stack.getTagCompound().getString("critterName"):StatCollector.translateToLocal("item.syringeSample.name") + ": INVALID";
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {}

   public void addInformation(ItemStack sample, EntityPlayer player, List list, boolean par4) {
      list.add("Essentia required to clone:");
      AspectList asp = (new AspectList()).add(Aspect.LIFE, 4);
      int j;
      if(sample.hasTagCompound() && sample.stackTagCompound.getCompoundTag("critter") != null && sample.stackTagCompound.getCompoundTag("critter").getCompoundTag("CreatureInfusion") != null) {
         NBTTagCompound tlist = sample.stackTagCompound.getCompoundTag("critter").getCompoundTag("CreatureInfusion").getCompoundTag("InfusionCosts");
         if(tlist != null && tlist.hasKey("Aspects")) {
            NBTTagList aspex = tlist.getTagList("Aspects", 10);

            for(j = 0; j < aspex.tagCount(); ++j) {
               NBTTagCompound tag = aspex.getCompoundTagAt(j);
               if(tag.hasKey("key")) {
                  asp.add(Aspect.getAspect(tag.getString("key")), tag.getInteger("amount"));
               }
            }
         }
      }

      Aspect[] var10 = asp.getAspectsSorted();
      int var11 = var10.length;

      for(j = 0; j < var11; ++j) {
         Aspect var12 = var10[j];
         if(Thaumcraft.proxy.playerKnowledge.hasDiscoveredAspect(player.getCommandSenderName(), var12)) {
            list.add(var12.getName() + " x" + asp.getAmount(var12));
         } else {
            list.add(StatCollector.translateToLocal("tc.aspect.unknown"));
         }
      }

   }
}
