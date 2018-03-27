package com.kentington.thaumichorizons.common.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderPocketPlane implements IChunkProvider {

   private Random rand;
   private World worldObj;
   private WorldType worldType;
   private BiomeGenBase[] biomesForGeneration;


   public ChunkProviderPocketPlane(World p_i2005_1_, long p_i2005_2_) {
      this.worldObj = p_i2005_1_;
      this.worldType = p_i2005_1_.getWorldInfo().getTerrainType();
      this.rand = new org.bogdang.modifications.random.XSTR(p_i2005_2_);
   }

   public boolean chunkExists(int p_73149_1_, int p_73149_2_) {
      return true;
   }

   public Chunk provideChunk(int p_73154_1_, int p_73154_2_) {
      this.rand.setSeed((long)p_73154_1_ * 341873128712L + (long)p_73154_2_ * 132897987541L);
      Block[] ablock = new Block['\u8000'];
      byte[] meta = new byte[ablock.length];

      int k;
      for(int chunk = 0; chunk < 16; ++chunk) {
         for(int abyte = 0; abyte < 16; ++abyte) {
            for(k = 127; k >= 0; --k) {
               int k1 = (abyte * 16 + chunk) * 128 + k;
               ablock[k1] = null;
               meta[k1] = 0;
            }
         }
      }

      this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
      Chunk var9 = new Chunk(this.worldObj, ablock, meta, p_73154_1_, p_73154_2_);
      byte[] var10 = var9.getBiomeArray();

      for(k = 0; k < var10.length; ++k) {
         var10[k] = (byte)this.biomesForGeneration[k].biomeID;
      }

      var9.generateSkylightMap();
      return var9;
   }

   public Chunk loadChunk(int p_73158_1_, int p_73158_2_) {
      return this.provideChunk(p_73158_1_, p_73158_2_);
   }

   public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {}

   public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
      return true;
   }

   public boolean unloadQueuedChunks() {
      return false;
   }

   public boolean canSave() {
      return true;
   }

   public String makeString() {
      return "RandomLevelSource";
   }

   public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {
      return new ArrayList();
   }

   public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
      return null;
   }

   public int getLoadedChunkCount() {
      return 0;
   }

   public void recreateStructures(int p_82695_1_, int p_82695_2_) {}

   public void saveExtraData() {}
}
