package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Save;

public class EventHandlerWorld {

   @SubscribeEvent
   public void worldLoad(Load event) {
      if(!event.world.isRemote && event.world.provider.dimensionId == 0) {
         PocketPlaneData.loadPocketPlanes(event.world);
      }

   }

   @SubscribeEvent
   public void worldSave(Save event) {
      if(!event.world.isRemote && event.world.provider.dimensionId == 0) {
         PocketPlaneData.savePocketPlanes(event.world);
      }

   }
}
