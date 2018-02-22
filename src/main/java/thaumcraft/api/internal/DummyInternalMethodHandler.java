package thaumcraft.api.internal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.internal.IInternalMethodHandler;

public class DummyInternalMethodHandler implements IInternalMethodHandler {

   public void generateVisEffect(int dim, int x, int y, int z, int x2, int y2, int z2, int color) {}

   public boolean isResearchComplete(String username, String researchkey) {
      return false;
   }

   public boolean hasDiscoveredAspect(String username, Aspect aspect) {
      return false;
   }

   public AspectList getDiscoveredAspects(String username) {
      return null;
   }

   public ItemStack getStackInRowAndColumn(Object instance, int row, int column) {
      return null;
   }

   public AspectList getObjectAspects(ItemStack is) {
      return null;
   }

   public AspectList getBonusObjectTags(ItemStack is, AspectList ot) {
      return null;
   }

   public AspectList generateTags(Item item, int meta) {
      return null;
   }

   public boolean consumeVisFromWand(ItemStack wand, EntityPlayer player, AspectList cost, boolean doit, boolean crafting) {
      return false;
   }

   public boolean consumeVisFromWandCrafting(ItemStack wand, EntityPlayer player, AspectList cost, boolean doit) {
      return false;
   }

   public boolean consumeVisFromInventory(EntityPlayer player, AspectList cost) {
      return false;
   }

   public void addWarpToPlayer(EntityPlayer player, int amount, boolean temporary) {}

   public void addStickyWarpToPlayer(EntityPlayer player, int amount) {}
}
