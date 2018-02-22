package thaumcraft.api.wands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IWandable {

   int onWandRightClick(World var1, ItemStack var2, EntityPlayer var3, int var4, int var5, int var6, int var7, int var8);

   ItemStack onWandRightClick(World var1, ItemStack var2, EntityPlayer var3);

   void onUsingWandTick(ItemStack var1, EntityPlayer var2, int var3);

   void onWandStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4);
}
