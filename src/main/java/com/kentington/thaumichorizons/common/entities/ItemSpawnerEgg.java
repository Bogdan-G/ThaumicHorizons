package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemSpawnerEgg extends Item {

   static ArrayList spawnList = new ArrayList();
   @SideOnly(Side.CLIENT)
   private IIcon theIcon;


   public static void addMapping(String name, int c1, int c2) {
      spawnList.add(new ItemSpawnerEgg.EntityEggStuff("ThaumicHorizons." + name, c1, c2));
   }

   public ItemSpawnerEgg() {
      this.setHasSubtypes(true);
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public String getItemStackDisplayName(ItemStack par1ItemStack) {
      String s = ("" + StatCollector.translateToLocal("item.monsterPlacer.name")).trim();
      String s1 = ((ItemSpawnerEgg.EntityEggStuff)spawnList.get(par1ItemStack.getItemDamage())).name;
      if(s1 != null) {
         s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
      }

      return s;
   }

   @SideOnly(Side.CLIENT)
   public int getColorFromItemStack(ItemStack stack, int layer) {
      ItemSpawnerEgg.EntityEggStuff entityegginfo = (ItemSpawnerEgg.EntityEggStuff)spawnList.get(stack.getItemDamage());
      return entityegginfo != null?entityegginfo.color2:(layer == 0?entityegginfo.color1:16777215);
   }

   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10) {
      if(world.isRemote) {
         return true;
      } else {
         Block block = world.getBlock(x, y, z);
         x += Facing.offsetsXForSide[side];
         y += Facing.offsetsYForSide[side];
         z += Facing.offsetsZForSide[side];
         double d0 = 0.0D;
         if(side == 1 && block.getRenderType() == 11) {
            d0 = 0.5D;
         }

         Entity entity = spawnCreature(world, stack.getItemDamage(), (double)x + 0.5D, (double)y + d0, (double)z + 0.5D);
         if(entity != null) {
            if(entity instanceof EntityLivingBase && stack.hasDisplayName()) {
               ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
            }

            if(!player.capabilities.isCreativeMode) {
               --stack.stackSize;
            }
         }

         return true;
      }
   }

   public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
      if(world.isRemote) {
         return stack;
      } else {
         MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
         if(movingobjectposition == null) {
            return stack;
         } else {
            if(movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
               int i = movingobjectposition.blockX;
               int j = movingobjectposition.blockY;
               int k = movingobjectposition.blockZ;
               if(!world.canMineBlock(player, i, j, k)) {
                  return stack;
               }

               if(!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
                  return stack;
               }

               if(world.getBlock(i, j, k) instanceof BlockLiquid) {
                  Entity entity = spawnCreature(world, stack.getItemDamage(), (double)i, (double)j, (double)k);
                  if(entity != null) {
                     if(entity instanceof EntityLivingBase && stack.hasDisplayName()) {
                        ((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
                     }

                     if(!player.capabilities.isCreativeMode) {
                        --stack.stackSize;
                     }
                  }
               }
            }

            return stack;
         }
      }
   }

   public static Entity spawnCreature(World par0World, int par1, double par2, double par4, double par6) {
      if(spawnList.get(par1) == null) {
         return null;
      } else {
         Entity entity = null;

         for(int j = 0; j < 1; ++j) {
            entity = EntityList.createEntityByName(((ItemSpawnerEgg.EntityEggStuff)spawnList.get(par1)).name, par0World);
            if(entity != null && entity instanceof EntityLivingBase) {
               EntityLiving entityliving = (EntityLiving)entity;
               entity.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0F), 0.0F);
               entityliving.rotationYawHead = entityliving.rotationYaw;
               entityliving.renderYawOffset = entityliving.rotationYaw;
               entityliving.onSpawnWithEgg((IEntityLivingData)null);
               par0World.spawnEntityInWorld(entity);
               entityliving.playLivingSound();
            }
         }

         return entity;
      }
   }

   @SideOnly(Side.CLIENT)
   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
      return par2 > 0?this.theIcon:super.getIconFromDamageForRenderPass(par1, par2);
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
      for(int a = 0; a < spawnList.size(); ++a) {
         p_150895_3_.add(new ItemStack(p_150895_1_, 1, a));
      }

   }

   protected String getIconString() {
      return "spawn_egg";
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      super.registerIcons(par1IconRegister);
      this.theIcon = par1IconRegister.registerIcon(this.getIconString() + "_overlay");
   }


   static class EntityEggStuff {

      String name;
      int color1;
      int color2;


      public EntityEggStuff(String name, int color1, int color2) {
         this.name = name;
         this.color1 = color1;
         this.color2 = color2;
      }
   }
}
