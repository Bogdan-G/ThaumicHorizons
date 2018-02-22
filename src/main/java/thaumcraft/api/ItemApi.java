package thaumcraft.api;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemApi {

   public static ItemStack getItem(String itemString, int meta) {
      ItemStack item = null;

      try {
         String ex = "thaumcraft.common.config.ConfigItems";
         Object obj = Class.forName(ex).getField(itemString).get((Object)null);
         if(obj instanceof Item) {
            item = new ItemStack((Item)obj, 1, meta);
         } else if(obj instanceof ItemStack) {
            item = (ItemStack)obj;
         }
      } catch (Exception var5) {
         FMLLog.warning("[Thaumcraft] Could not retrieve item identified by: " + itemString, new Object[0]);
      }

      return item;
   }

   public static ItemStack getBlock(String itemString, int meta) {
      ItemStack item = null;

      try {
         String ex = "thaumcraft.common.config.ConfigBlocks";
         Object obj = Class.forName(ex).getField(itemString).get((Object)null);
         if(obj instanceof Block) {
            item = new ItemStack((Block)obj, 1, meta);
         } else if(obj instanceof ItemStack) {
            item = (ItemStack)obj;
         }
      } catch (Exception var5) {
         FMLLog.warning("[Thaumcraft] Could not retrieve block identified by: " + itemString, new Object[0]);
      }

      return item;
   }
}
