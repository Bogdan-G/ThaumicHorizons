package thaumcraft.api.aspects;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;

public interface IEssentiaContainerItem {

   AspectList getAspects(ItemStack var1);

   void setAspects(ItemStack var1, AspectList var2);
}
