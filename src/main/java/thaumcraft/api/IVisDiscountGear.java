package thaumcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;

public interface IVisDiscountGear {

   int getVisDiscount(ItemStack var1, EntityPlayer var2, Aspect var3);
}
