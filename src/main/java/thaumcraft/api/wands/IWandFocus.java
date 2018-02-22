package thaumcraft.api.wands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;

public interface IWandFocus {

   int getFocusColor();

   IIcon getFocusDepthLayerIcon();

   IIcon getOrnament();

   IWandFocus.WandFocusAnimation getAnimation();

   AspectList getVisCost();

   boolean isVisCostPerTick();

   ItemStack onFocusRightClick(ItemStack var1, World var2, EntityPlayer var3, MovingObjectPosition var4);

   void onUsingFocusTick(ItemStack var1, EntityPlayer var2, int var3);

   void onPlayerStoppedUsingFocus(ItemStack var1, World var2, EntityPlayer var3, int var4);

   String getSortingHelper(ItemStack var1);

   boolean onFocusBlockStartBreak(ItemStack var1, int var2, int var3, int var4, EntityPlayer var5);

   boolean acceptsEnchant(int var1);

   public static enum WandFocusAnimation {

      WAVE("WAVE", 0),
      CHARGE("CHARGE", 1);
      // $FF: synthetic field
      private static final IWandFocus.WandFocusAnimation[] $VALUES = new IWandFocus.WandFocusAnimation[]{WAVE, CHARGE};


      private WandFocusAnimation(String var1, int var2) {}

   }
}
