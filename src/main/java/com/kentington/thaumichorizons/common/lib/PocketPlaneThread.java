package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;

public class PocketPlaneThread implements Runnable {

   PocketPlaneData data;
   AspectList aspects;
   World world;
   int x;
   int y;
   int z;


   public PocketPlaneThread(PocketPlaneData data, AspectList aspects, World world, int x, int y, int z) {
      this.data = data;
      this.aspects = aspects;
      this.world = world;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void run() {
      System.out.println("Starting pocket plane generation thread...");
      PocketPlaneData.generatePocketPlane(this.aspects, this.data, this.world, this.x, this.y, this.z);
   }
}
