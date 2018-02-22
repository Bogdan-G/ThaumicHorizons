package thaumcraft.api.wands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.wands.IWandTriggerManager;

public class WandTriggerRegistry {

   private static HashMap triggers = new HashMap();
   private static final String DEFAULT = "default";


   public static void registerWandBlockTrigger(IWandTriggerManager manager, int event, Block block, int meta, String modid) {
      if(!triggers.containsKey(modid)) {
         triggers.put(modid, new HashMap());
      }

      HashMap temp = (HashMap)triggers.get(modid);
      temp.put(Arrays.asList(new Object[]{block, Integer.valueOf(meta)}), Arrays.asList(new Object[]{manager, Integer.valueOf(event)}));
      triggers.put(modid, temp);
   }

   public static void registerWandBlockTrigger(IWandTriggerManager manager, int event, Block block, int meta) {
      registerWandBlockTrigger(manager, event, block, meta, "default");
   }

   public static boolean hasTrigger(Block block, int meta) {
      Iterator var2 = triggers.keySet().iterator();

      HashMap temp;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         String modid = (String)var2.next();
         temp = (HashMap)triggers.get(modid);
      } while(!temp.containsKey(Arrays.asList(new Object[]{block, Integer.valueOf(meta)})) && !temp.containsKey(Arrays.asList(new Object[]{block, Integer.valueOf(-1)})));

      return true;
   }

   public static boolean hasTrigger(Block block, int meta, String modid) {
      if(!triggers.containsKey(modid)) {
         return false;
      } else {
         HashMap temp = (HashMap)triggers.get(modid);
         return temp.containsKey(Arrays.asList(new Object[]{block, Integer.valueOf(meta)})) || temp.containsKey(Arrays.asList(new Object[]{block, Integer.valueOf(-1)}));
      }
   }

   public static boolean performTrigger(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side, Block block, int meta) {
      Iterator var9 = triggers.keySet().iterator();

      while(var9.hasNext()) {
         String modid = (String)var9.next();
         HashMap temp = (HashMap)triggers.get(modid);
         List l = (List)temp.get(Arrays.asList(new Object[]{block, Integer.valueOf(meta)}));
         if(l == null) {
            l = (List)temp.get(Arrays.asList(new Object[]{block, Integer.valueOf(-1)}));
         }

         if(l != null) {
            IWandTriggerManager manager = (IWandTriggerManager)l.get(0);
            int event = ((Integer)l.get(1)).intValue();
            boolean result = manager.performTrigger(world, wand, player, x, y, z, side, event);
            if(result) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean performTrigger(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side, Block block, int meta, String modid) {
      if(!triggers.containsKey(modid)) {
         return false;
      } else {
         HashMap temp = (HashMap)triggers.get(modid);
         List l = (List)temp.get(Arrays.asList(new Object[]{block, Integer.valueOf(meta)}));
         if(l == null) {
            l = (List)temp.get(Arrays.asList(new Object[]{block, Integer.valueOf(-1)}));
         }

         if(l == null) {
            return false;
         } else {
            IWandTriggerManager manager = (IWandTriggerManager)l.get(0);
            int event = ((Integer)l.get(1)).intValue();
            return manager.performTrigger(world, wand, player, x, y, z, side, event);
         }
      }
   }

}
