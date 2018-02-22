package com.kentington.thaumichorizons.common.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.common.config.ConfigBlocks;

public class BiomeGenPocket extends BiomeGenBase {

   public BiomeGenPocket(int inty) {
      super(inty);
      super.spawnableMonsterList.clear();
      super.spawnableCreatureList.clear();
      super.spawnableWaterCreatureList.clear();
      super.spawnableCaveCreatureList.clear();
      super.topBlock = ConfigBlocks.blockEldritchNothing;
      super.fillerBlock = ConfigBlocks.blockEldritchNothing;
      this.setBiomeName("Pocket Plane");
      this.setDisableRain();
   }

   @SideOnly(Side.CLIENT)
   public int getSkyColorByTemp(float p_76731_1_) {
      return 0;
   }

   public void decorate(World world, Random random, int x, int z) {}

   public BiomeGenBase createMutation() {
      return null;
   }
}
