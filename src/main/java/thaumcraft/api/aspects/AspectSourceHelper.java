package thaumcraft.api.aspects;

import cpw.mods.fml.common.FMLLog;
import java.lang.reflect.Method;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;

public class AspectSourceHelper {

   static Method drainEssentia;
   static Method findEssentia;


   public static boolean drainEssentia(TileEntity tile, Aspect aspect, ForgeDirection direction, int range) {
      try {
         if(drainEssentia == null) {
            Class ex = Class.forName("thaumcraft.common.lib.events.EssentiaHandler");
            drainEssentia = ex.getMethod("drainEssentia", new Class[]{TileEntity.class, Aspect.class, ForgeDirection.class, Integer.TYPE});
         }

         return ((Boolean)drainEssentia.invoke((Object)null, new Object[]{tile, aspect, direction, Integer.valueOf(range)})).booleanValue();
      } catch (Exception var5) {
         FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.lib.events.EssentiaHandler method drainEssentia", new Object[0]);
         return false;
      }
   }

   public static boolean findEssentia(TileEntity tile, Aspect aspect, ForgeDirection direction, int range) {
      try {
         if(findEssentia == null) {
            Class ex = Class.forName("thaumcraft.common.lib.events.EssentiaHandler");
            findEssentia = ex.getMethod("findEssentia", new Class[]{TileEntity.class, Aspect.class, ForgeDirection.class, Integer.TYPE});
         }

         return ((Boolean)findEssentia.invoke((Object)null, new Object[]{tile, aspect, direction, Integer.valueOf(range)})).booleanValue();
      } catch (Exception var5) {
         FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.lib.events.EssentiaHandler method findEssentia", new Object[0]);
         return false;
      }
   }
}
