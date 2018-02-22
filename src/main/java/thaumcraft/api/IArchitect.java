package thaumcraft.api;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IArchitect {

   ArrayList getArchitectBlocks(ItemStack var1, World var2, int var3, int var4, int var5, int var6, EntityPlayer var7);

   boolean showAxis(ItemStack var1, World var2, EntityPlayer var3, int var4, IArchitect.EnumAxis var5);

   public static enum EnumAxis {

      X("X", 0),
      Y("Y", 1),
      Z("Z", 2);
      // $FF: synthetic field
      private static final IArchitect.EnumAxis[] $VALUES = new IArchitect.EnumAxis[]{X, Y, Z};


      private EnumAxis(String var1, int var2) {}

   }
}
