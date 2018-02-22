package com.kentington.thaumichorizons.common.lib;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class GatewayTeleporter extends Teleporter {

   private WorldServer worldServerInstance;
   private int x;
   private int y;
   private int z;
   private float yaw;


   public GatewayTeleporter(WorldServer p_i1963_1_, int x, int y, int z, float yaw) {
      super(p_i1963_1_);
      this.worldServerInstance = p_i1963_1_;
      this.x = x;
      this.y = y;
      this.z = z;
      this.yaw = yaw;
   }

   public void placeInPortal(Entity p_77185_1_, double p_77185_2_, double p_77185_4_, double p_77185_6_, float p_77185_8_) {
      p_77185_1_.setPositionAndRotation((double)this.x + 0.5D, (double)this.y, (double)this.z + 0.5D, this.yaw, 0.0F);
   }
}
