package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemFocusAnimation extends ItemFocusBasic {

   private static final AspectList cost = (new AspectList()).add(Aspect.FIRE, 1000).add(Aspect.AIR, 1000).add(Aspect.ORDER, 1000).add(Aspect.WATER, 1000).add(Aspect.EARTH, 1000).add(Aspect.ENTROPY, 1000);
   public static FocusUpgradeType berserk = new FocusUpgradeType(FocusUpgradeType.types.length, new ResourceLocation("thaumichorizons", "textures/foci/berserk.png"), "focus.upgrade.berserk.name", "focus.upgrade.berserk.text", (new AspectList()).add(Aspect.WEAPON, 8));
   public static FocusUpgradeType detonation = new FocusUpgradeType(FocusUpgradeType.types.length, new ResourceLocation("thaumichorizons", "textures/foci/detonation.png"), "focus.upgrade.detonation.name", "focus.upgrade.detonation.text", (new AspectList()).add(Aspect.WEAPON, 8));
   public IIcon ornamentIcon;


   public ItemFocusAnimation() {
      this.setMaxDamage(0);
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public IIcon getOrnament(ItemStack focusstack) {
      return this.ornamentIcon;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      super.icon = ir.registerIcon("thaumichorizons:focus_animation");
      this.ornamentIcon = ir.registerIcon("thaumcraft:focus_whatever_orn");
   }

   public String getItemStackDisplayName(ItemStack stack) {
      return StatCollector.translateToLocal("item.focusAnimation.name");
   }

   public int getFocusColor(ItemStack focusstack) {
      return 15054592;
   }

   public AspectList getVisCost(ItemStack focusstack) {
      return cost;
   }

   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
      switch(rank) {
      case 1:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.extend};
      case 2:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.extend};
      case 3:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.extend, berserk};
      case 4:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.extend};
      case 5:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.extend, detonation};
      default:
         return null;
      }
   }

   public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition mop) {
      ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
      if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
         int x = mop.blockX;
         int y = mop.blockY;
         int z = mop.blockZ;
         Block blocky = world.getBlock(x, y, z);
         int md = world.getBlockMetadata(x, y, z);
         if(!blocky.hasTileEntity(md) && !blocky.isAir(world, x, y, z) && (blocky.isOpaqueCube() || isWhitelisted(blocky, md)) && blocky.getBlockHardness(world, x, y, z) != -1.0F) {
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
               golem.loadGolem((double)x + 0.5D, (double)y, (double)z + 0.5D, blocky, md, 600 + wand.getFocusExtend(itemstack) * 200, false, this.getUpgradeLevel(wand.getFocusItem(itemstack), berserk) > 0, this.getUpgradeLevel(wand.getFocusItem(itemstack), detonation) > 0);
               AspectList cost = (new AspectList()).add(Aspect.FIRE, golem.type.visCost).add(Aspect.ORDER, golem.type.visCost).add(Aspect.AIR, golem.type.visCost).add(Aspect.EARTH, golem.type.visCost).add(Aspect.ENTROPY, golem.type.visCost).add(Aspect.WATER, golem.type.visCost);
               if(!wand.consumeAllVis(itemstack, player, cost, false, false)) {
                  golem.setDead();
                  return itemstack;
               }

               BreakEvent event = ForgeHooks.onBlockBreakEvent(player.worldObj, gt, (EntityPlayerMP)player, x, y, z);
               if(event.isCanceled() || !wand.consumeAllVis(itemstack, player, cost, true, false)) {
                  golem.setDead();
                  return itemstack;
               }

               world.setBlockToAir(x, y, z);
               world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
               golem.setHomeArea((int)golem.posX, (int)golem.posY, (int)golem.posZ, 32);
               golem.setOwner(player.getCommandSenderName());
               world.spawnEntityInWorld(golem);
               world.setEntityState(golem, (byte)7);
            } else {
               Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(x, y, z, blocky, md);
               player.swingItem();
            }
         }
      }

      return itemstack;
   }

   public static boolean isWhitelisted(Block blocky, int md) {
      return blocky == Blocks.cake || blocky == Blocks.cactus || blocky == Blocks.glass || blocky == Blocks.packed_ice || blocky == Blocks.ice || blocky == Blocks.web || blocky == ConfigBlocks.blockCosmeticOpaque && md < 2 || blocky == ConfigBlocks.blockWoodenDevice && md == 6 || md == 7;
   }

}
