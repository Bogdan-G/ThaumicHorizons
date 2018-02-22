package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import com.kentington.thaumichorizons.common.items.ItemGolemBellTH;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.codechicken.lib.math.MathHelper;

public class ItemGolemPlacer extends thaumcraft.common.entities.golems.ItemGolemPlacer {

   public IIcon icon;
   public IIcon newBell;


   public ItemGolemPlacer() {
      this.setCreativeTab((CreativeTabs)null);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      super.registerIcons(ir);
      this.icon = ir.registerIcon("thaumichorizons:golem");
      this.newBell = ir.registerIcon("thaumichorizons:newbell");
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int par1) {
      return this.icon;
   }

   @SideOnly(Side.CLIENT)
   public int getColorFromItemStack(ItemStack stack, int p_82790_2_) {
      if(stack.getTagCompound().hasKey("block")) {
         int[] block = stack.getTagCompound().getIntArray("block");
         if(Block.getBlockById(block[0]) == Blocks.air) {
            return 0;
         } else {
            int color = Block.getBlockById(block[0]).getMapColor(block[1]).colorValue;
            return color != 0?color:-1;
         }
      } else {
         return 0;
      }
   }

   public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
      if(stack.hasTagCompound() && stack.getTagCompound().hasKey("block")) {
         int[] block = stack.getTagCompound().getIntArray("block");
         String name = "?";
         ItemStack blockStack = new ItemStack(Block.getBlockById(block[0]), 1, block[1]);
         if(blockStack.getItem() != null) {
            list.add(blockStack.getDisplayName());
         } else if(Block.getBlockById(block[0]) == Blocks.air) {
            list.add("Voidling");
         } else {
            list.add(Block.getBlockById(block[0]).getLocalizedName());
         }
      }

      super.addInformation(stack, par2EntityPlayer, list, par4);
   }

   public boolean spawnCreature(World par0World, double par2, double par4, double par6, int side, ItemStack stack, EntityPlayer player) {
      boolean adv = false;
      if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("advanced")) {
         adv = true;
      }

      EntityGolemTH golem = new EntityGolemTH(par0World);
      if(golem != null) {
         golem.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
         golem.playLivingSound();
         golem.setHomeArea(MathHelper.floor_double(par2), MathHelper.floor_double(par4), MathHelper.floor_double(par6), 32);
         int[] block = new int[]{0, 0};
         if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("core")) {
            golem.setCore(stack.stackTagCompound.getByte("core"));
         }

         String deco = "";
         if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("deco")) {
            deco = stack.stackTagCompound.getString("deco");
            golem.decoration = deco;
         }

         if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("block")) {
            block = stack.stackTagCompound.getIntArray("block");
         }

         golem.setup(side);
         golem.loadGolem(golem.posX, golem.posY, golem.posZ, Block.getBlockById(block[0]), block[1], 600, adv, stack.stackTagCompound.getBoolean("berserk"), stack.stackTagCompound.getBoolean("explosive"));
         int a;
         int a1;
         byte[] nbttaglist2;
         if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("upgrades")) {
            a = golem.upgrades.length;
            golem.upgrades = stack.stackTagCompound.getByteArray("upgrades");
            if(a != golem.upgrades.length) {
               nbttaglist2 = new byte[a];

               for(a1 = 0; a1 < a; ++a1) {
                  nbttaglist2[a1] = -1;
               }

               for(a1 = 0; a1 < golem.upgrades.length; ++a1) {
                  if(a1 < a) {
                     nbttaglist2[a1] = golem.upgrades[a1];
                  }
               }

               golem.upgrades = nbttaglist2;
            }
         }

         par0World.spawnEntityInWorld(golem);
         golem.setGolemDecoration(deco);
         golem.setOwner(player.getCommandSenderName());
         golem.setMarkers(ItemGolemBellTH.getMarkers(stack));
         a = 0;
         nbttaglist2 = golem.upgrades;
         a1 = nbttaglist2.length;

         for(int var18 = 0; var18 < a1; ++var18) {
            byte b = nbttaglist2[var18];
            golem.setUpgrade(a, b);
            ++a;
         }

         if(stack.hasDisplayName()) {
            golem.setCustomNameTag(stack.getDisplayName());
            golem.func_110163_bv();
         }

         if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("Inventory")) {
            NBTTagList var20 = stack.stackTagCompound.getTagList("Inventory", 10);
            golem.inventory.readFromNBT(var20);
         }
      }

      return golem != null;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.golemPlacer";
   }
}
