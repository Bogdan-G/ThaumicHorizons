package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import com.kentington.thaumichorizons.common.items.ItemFocusAnimation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class ItemGolemPowder extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon;


   public ItemGolemPowder() {
      this.setMaxStackSize(64);
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:golempowder");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.golemPowder";
   }

   public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
      Block blocky = world.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
      int md = world.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
      if(player.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
         if(world.isRemote) {
            return true;
         } else if(!blocky.hasTileEntity(md) && !blocky.isAir(world, p_77648_4_, p_77648_5_, p_77648_6_) && (blocky.isOpaqueCube() || ItemFocusAnimation.isWhitelisted(blocky, md)) && blocky.getBlockHardness(world, p_77648_4_, p_77648_5_, p_77648_6_) != -1.0F) {
            GameType gt = GameType.SURVIVAL;
            if(player.capabilities.allowEdit) {
               if(player.capabilities.isCreativeMode) {
                  gt = GameType.CREATIVE;
               }
            } else {
               gt = GameType.ADVENTURE;
            }

            if(!world.isRemote) {
               EntityGolemTH golem = new EntityGolemTH(world);
               golem.loadGolem((double)p_77648_4_ + 0.5D, (double)p_77648_5_, (double)p_77648_6_ + 0.5D, blocky, md, 1200, false, false, false);
               BreakEvent event = ForgeHooks.onBlockBreakEvent(player.worldObj, gt, (EntityPlayerMP)player, p_77648_4_, p_77648_5_, p_77648_6_);
               if(event.isCanceled()) {
                  golem.setDead();
                  return false;
               }

               world.setBlockToAir(p_77648_4_, p_77648_5_, p_77648_6_);
               world.playSoundEffect((double)p_77648_4_ + 0.5D, (double)p_77648_5_ + 0.5D, (double)p_77648_6_ + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
               golem.setHomeArea((int)golem.posX, (int)golem.posY, (int)golem.posZ, 32);
               golem.setOwner(player.getCommandSenderName());
               world.spawnEntityInWorld(golem);
               world.setEntityState(golem, (byte)7);
            } else {
               Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(p_77648_4_, p_77648_5_, p_77648_6_, blocky, md);
               player.swingItem();
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
