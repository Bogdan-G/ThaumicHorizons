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
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemFocusIllumination extends ItemFocusBasic {

   public static FocusUpgradeType solar = new FocusUpgradeType(FocusUpgradeType.types.length, new ResourceLocation("thaumichorizons", "textures/foci/solar.png"), "focus.upgrade.solar.name", "focus.upgrade.solar.text", (new AspectList()).add(Aspect.ORDER, 8).add(Aspect.VOID, 4));
   public static final int[] colors = new int[]{1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};
   private static final AspectList cost = (new AspectList()).add(Aspect.FIRE, 100).add(Aspect.AIR, 100);
   public static final IIcon[] icons = new IIcon[16];


   public ItemFocusIllumination() {
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      for(int i = 0; i < 16; ++i) {
         icons[i] = ir.registerIcon("thaumichorizons:focus_illumination." + i);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int damage) {
      return damage > 15?null:icons[damage];
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
      for(int i = 0; i < 16; ++i) {
         p_150895_3_.add(new ItemStack(p_150895_1_, 1, i));
      }

   }

   public String getSortingHelper(ItemStack itemstack) {
      return "I" + itemstack.getItemDamage() + super.getSortingHelper(itemstack);
   }

   public String getItemStackDisplayName(ItemStack p_77653_1_) {
      return StatCollector.translateToLocal("item.focusIllumination." + p_77653_1_.getItemDamage() + ".name");
   }

   public int getFocusColor(ItemStack focusstack) {
      return focusstack.getItemDamage() < 16?colors[focusstack.getItemDamage()]:420;
   }

   public AspectList getVisCost(ItemStack focusstack) {
      return cost;
   }

   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
      switch(rank) {
      case 1:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal};
      case 2:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal};
      case 3:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, solar};
      case 4:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal};
      case 5:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal};
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
         switch(mop.sideHit) {
         case 0:
            --y;
            break;
         case 1:
            ++y;
            break;
         case 2:
            --z;
            break;
         case 3:
            ++z;
            break;
         case 4:
            --x;
            break;
         case 5:
            ++x;
         }

         if(world.isAirBlock(x, y, z) && wand.consumeAllVis(itemstack, player, this.getVisCost(itemstack), true, false)) {
            if(this.getUpgradeLevel(wand.getFocusItem(itemstack), solar) > 0) {
               world.setBlock(x, y, z, ThaumicHorizons.blockLightSolar, wand.getFocusItem(itemstack).getItemDamage(), 3);
            } else {
               world.setBlock(x, y, z, ThaumicHorizons.blockLight, wand.getFocusItem(itemstack).getItemDamage(), 3);
            }

            player.swingItem();
            if(!world.isRemote) {
               world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
            }
         }
      }

      return itemstack;
   }

}
