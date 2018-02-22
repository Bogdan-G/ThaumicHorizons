package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.utils.BlockUtils;

public class ItemFocusCompound extends ItemFocusBasic {

   public static FocusUpgradeType fission = new FocusUpgradeType(FocusUpgradeType.types.length, new ResourceLocation("thaumichorizons", "textures/foci/fission.png"), "focus.upgrade.fission.name", "focus.upgrade.fission.text", (new AspectList()).add(Aspect.EXCHANGE, 8));
   private static final AspectList cost = (new AspectList()).add(Aspect.FIRE, 0).add(Aspect.WATER, 0).add(Aspect.AIR, 0).add(Aspect.EARTH, 0).add(Aspect.ORDER, 0).add(Aspect.ENTROPY, 0);


   public ItemFocusCompound() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public String getItemStackDisplayName(ItemStack stack) {
      return StatCollector.translateToLocal("item.focusCompound.name");
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      super.icon = ir.registerIcon("thaumichorizons:focus_containment");
   }

   public int getFocusColor(ItemStack focusstack) {
      return 15054592;
   }

   public String getSortingHelper(ItemStack itemstack) {
      return "CR" + super.getSortingHelper(itemstack);
   }

   public AspectList getVisCost(ItemStack focusstack) {
      return cost.copy();
   }

   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
      return new FocusUpgradeType[]{fission};
   }

   public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition movingobjectposition) {
      ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
      p.setItemInUse(itemstack, Integer.MAX_VALUE);
      return itemstack;
   }

   public Aspect chooseRandomFilteredFromSource(AspectList filter, boolean preserve, AspectList nodeAspects, World world) {
      int min = preserve?1:0;
      ArrayList validaspects = new ArrayList();
      Aspect[] asp = nodeAspects.getAspects();
      int var8 = asp.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Aspect prim = asp[var9];
         if(filter.getAmount(prim) > 0 && nodeAspects.getAmount(prim) > min) {
            validaspects.add(prim);
         }
      }

      if(validaspects.size() == 0) {
         return null;
      } else {
         Aspect var11 = (Aspect)validaspects.get(world.rand.nextInt(validaspects.size()));
         if(var11 != null && nodeAspects.getAmount(var11) > min) {
            return var11;
         } else {
            return null;
         }
      }
   }

   public void onUsingFocusTick(ItemStack wandstack, EntityPlayer player, int count) {
      boolean mfu = false;
      ItemWandCasting wand = (ItemWandCasting)wandstack.getItem();
      MovingObjectPosition movingobjectposition = BlockUtils.getTargetBlock(player.worldObj, player, true);
      int i = 0;
      int j = 0;
      int k = 0;
      AspectList nodeAsp = new AspectList();
      INode node = null;
      int color = 0;
      if(movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
         i = movingobjectposition.blockX;
         j = movingobjectposition.blockY;
         k = movingobjectposition.blockZ;
         if(!(player.worldObj.getTileEntity(i, j, k) instanceof INode)) {
            player.stopUsingItem();
            return;
         }

         node = (INode)player.worldObj.getTileEntity(i, j, k);
         nodeAsp = node.getAspects();
      } else {
         player.stopUsingItem();
      }

      if(count % 5 == 0) {
         int tap = 1;
         if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "NODETAPPER1")) {
            ++tap;
         }

         if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "NODETAPPER2")) {
            ++tap;
         }

         boolean preserve = !player.isSneaking() && ResearchManager.isResearchComplete(player.getCommandSenderName(), "NODEPRESERVE") && !wand.getRod(wandstack).getTag().equals("wood") && !wand.getCap(wandstack).getTag().equals("iron");
         boolean success = false;
         Aspect aspect = null;
         if((aspect = this.chooseRandomFilteredFromSource(wand.getAspectsWithRoom(wandstack), preserve, nodeAsp, player.worldObj)) != null) {
            int col = nodeAsp.getAmount(aspect);
            if(tap > col) {
               tap = col;
            }

            if(preserve && tap == col) {
               --tap;
            }

            if(tap > 0) {
               int rem = wand.addVis(wandstack, aspect, tap, !player.worldObj.isRemote);
               if(rem < tap) {
                  color = aspect.getColor();
                  if(!player.worldObj.isRemote) {
                     node.takeFromContainer(aspect, tap - rem);
                     mfu = true;
                  }

                  success = true;
               }
            }
         }

         if(success) {
            Color var19 = new Color(color);
            Thaumcraft.proxy.beamPower(player.worldObj, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, player.posX, player.posY + (double)player.eyeHeight, player.posZ, (float)var19.getRed() / 255.0F, (float)var19.getGreen() / 255.0F, (float)var19.getBlue() / 255.0F, true, node);
         }

         if(mfu) {
            player.worldObj.markBlockForUpdate(i, j, k);
            ((TileEntity)node).markDirty();
         }
      }

   }

}
