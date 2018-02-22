package thaumcraft.api.internal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public interface IInternalMethodHandler {

   void generateVisEffect(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

   boolean isResearchComplete(String var1, String var2);

   ItemStack getStackInRowAndColumn(Object var1, int var2, int var3);

   AspectList getObjectAspects(ItemStack var1);

   AspectList getBonusObjectTags(ItemStack var1, AspectList var2);

   AspectList generateTags(Item var1, int var2);

   boolean consumeVisFromWand(ItemStack var1, EntityPlayer var2, AspectList var3, boolean var4, boolean var5);

   boolean consumeVisFromWandCrafting(ItemStack var1, EntityPlayer var2, AspectList var3, boolean var4);

   boolean consumeVisFromInventory(EntityPlayer var1, AspectList var2);

   void addWarpToPlayer(EntityPlayer var1, int var2, boolean var3);

   void addStickyWarpToPlayer(EntityPlayer var1, int var2);

   boolean hasDiscoveredAspect(String var1, Aspect var2);

   AspectList getDiscoveredAspects(String var1);
}
