package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.lib.ChunkProviderPocketPlane;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderPocketPlane extends WorldProvider {

   public void registerWorldChunkManager() {
      super.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F);
      super.dimensionId = ThaumicHorizons.dimensionPocketId;
      super.hasNoSky = true;
   }

   public IChunkProvider createChunkGenerator() {
      return new ChunkProviderPocketPlane(super.worldObj, super.worldObj.getSeed());
   }

   public int getAverageGroundLevel() {
      return 128;
   }

   public ChunkCoordinates getEntrancePortalLocation() {
      return null;
   }

   @SideOnly(Side.CLIENT)
   public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
      return Vec3.createVectorHelper(0.02734375D, 0.01171875D, 0.16015625D);
   }

   protected void generateLightBrightnessTable() {
      float f = 0.3F;

      for(int i = 0; i <= 15; ++i) {
         float f1 = 1.0F - (float)i / 15.0F;
         super.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
      }

   }

   public boolean isSurfaceWorld() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public boolean isSkyColored() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_) {
      return null;
   }

   public String getWelcomeMessage() {
      return "Entering pocket plane...";
   }

   public String getDepartMessage() {
      return "Leaving pocket plane...";
   }

   public boolean shouldMapSpin(String entity, double x, double y, double z) {
      return true;
   }

   public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
      return false;
   }

   public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
      return false;
   }

   public boolean canDoLightning(Chunk chunk) {
      return false;
   }

   public boolean canDoRainSnowIce(Chunk chunk) {
      return false;
   }

   public boolean canCoordinateBeSpawn(int p_76566_1_, int p_76566_2_) {
      return super.worldObj.getTopBlock(p_76566_1_, p_76566_2_).getMaterial().blocksMovement();
   }

   public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_) {
      return 0.5F;
   }

   public boolean canRespawnHere() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public boolean doesXZShowFog(int p_76568_1_, int p_76568_2_) {
      return false;
   }

   public String getDimensionName() {
      return "Pocket Plane";
   }
}
