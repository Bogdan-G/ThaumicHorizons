package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class VortexTeleporter extends Teleporter {

   private WorldServer worldServerInstance;
   private int planeNum;


   public VortexTeleporter(WorldServer p_i1963_1_, int num) {
      super(p_i1963_1_);
      this.worldServerInstance = p_i1963_1_;
      this.planeNum = num;
   }

   public void placeInPortal(Entity p_77185_1_, double p_77185_2_, double p_77185_4_, double p_77185_6_, float p_77185_8_) {
      if(this.worldServerInstance.provider.dimensionId != 0) {
         p_77185_1_.setPosition(0.5D, 129.0D, (double)((float)(256 * this.planeNum) + 0.5F));
      } else {
         p_77185_1_.setPosition(((Vec3)PocketPlaneData.positions.get(this.planeNum)).xCoord, ((Vec3)PocketPlaneData.positions.get(this.planeNum)).yCoord, ((Vec3)PocketPlaneData.positions.get(this.planeNum)).zCoord);
      }

   }
}
