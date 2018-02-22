package thaumcraft.api.wands;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFocusArchitect {

   ArrayList getArchitectBlocks(ItemStack var1, World var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, EntityPlayer var10);

   boolean is3D(ItemStack var1, World var2, EntityPlayer var3);
}
