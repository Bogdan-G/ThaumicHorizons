package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityMeatSlime;
import com.kentington.thaumichorizons.common.entities.EntityMercurialSlime;
import com.kentington.thaumichorizons.common.tiles.TileVortex;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.monster.EntityTaintSporeSwarmer;
import thaumcraft.common.entities.monster.EntityTaintacle;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

public class PocketPlaneData {

   public int radius = 32;
   public int color = 0;
   public int[] potionEffects;
   public int[] portalA;
   public int[] portalB;
   public int[] portalC;
   public int[] portalD;
   public String name = "Generic Pocket Plane";
   public static ArrayList planes = new ArrayList();
   public static ArrayList positions = new ArrayList();
   public static HashMap positionsMap = new HashMap<String, Vec3>();


   public static void generatePocketPlane(AspectList aspects, PocketPlaneData data, World world, int vortexX, int vortexY, int vortexZ) {
      System.out.println("Starting pocket plane generation");
      byte xCenter = 0;
      short yCenter = 128;
      int zCenter = 256 * planes.size();
      if((float)aspects.visSize() * 0.75F < 128.0F) {
         data.radius = (int)Math.max(32.0F, (float)aspects.visSize() * 0.75F);
      } else {
         data.radius = 127;
      }

      data.color = getColor(aspects);
      BiomeGenBase bio = setBiome(xCenter, yCenter, zCenter, data, world, aspects);
      int noise = calcNoise(aspects);
      int life = calcLife(aspects);
      drawLayers(xCenter, yCenter, zCenter, data, world, aspects, noise, bio, life);
      drawCaves(xCenter, yCenter, zCenter, data, world, aspects, noise);
      drawPockets(xCenter, yCenter, zCenter, data, world, aspects, noise);
      drawRavines(xCenter, yCenter, zCenter, data, world, aspects, noise);
      drawClouds(xCenter, yCenter, zCenter, data, world, aspects, noise);
      drawSurfaceFeatures(xCenter, yCenter, zCenter, data, world, aspects, noise, (float)life);
      drawUndergroundFeatures(xCenter, yCenter, zCenter, data, world, aspects, noise, (float)life);
      drawLeviathanBones(xCenter, yCenter, zCenter, data, world, aspects, noise);
      addEffects(data, aspects);
      drawRings(xCenter, yCenter, zCenter, data, world, aspects);
      drawSphere(xCenter, yCenter, zCenter, data.radius, ThaumicHorizons.blockVoid, 0, world);

      for(int vortex = -2; vortex <= 2; ++vortex) {
         for(int z = -2; z <= 2; ++z) {
            world.setBlock(xCenter + vortex, yCenter, zCenter + z, ConfigBlocks.blockCosmeticSolid, 6, 0);
            world.setBlockToAir(xCenter + vortex, yCenter + 1, zCenter + z);
            world.setBlockToAir(xCenter + vortex, yCenter + 2, zCenter + z);
         }
      }

      world.setBlock(xCenter, yCenter + 1, zCenter, ThaumicHorizons.blockVortex);
      TileVortex var14 = (TileVortex)world.getTileEntity(xCenter, yCenter + 1, zCenter);
      var14.cheat = true;
      var14.dimensionID = planes.size();
      var14.createdDimension = true;
      data.portalA = new int[3];
      data.portalB = new int[3];
      data.portalC = new int[3];
      data.portalD = new int[3];
      planes.add(data);
      positions.add(Vec3.createVectorHelper((double)vortexX, (double)vortexY, (double)vortexZ));
      System.out.println("Finished with pocket plane generation!");
   }

   static int getColor(AspectList aspects) {
      int r = 0;
      int g = 0;
      int b = 0;
      Aspect[] color = aspects.getAspects();
      int var5 = color.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Aspect asp = color[var6];
         Color aspColor = new Color(asp.getColor());
         r = (int)((float)r + (float)aspColor.getRed() * aspectFraction(asp, aspects));
         g = (int)((float)g + (float)aspColor.getGreen() * aspectFraction(asp, aspects));
         b = (int)((float)b + (float)aspColor.getBlue() * aspectFraction(asp, aspects));
      }

      int var9 = r * 256 * 256 + g * 256 + b;
      System.out.println("Plane color is " + var9);
      return var9;
   }

   public static void addEffects(PocketPlaneData data, AspectList aspects) {
      int pointer = 0;
      data.potionEffects = new int[8];
      if(aspects.getAmount(Aspect.MOTION) > 0) {
         data.potionEffects[pointer] = Potion.moveSpeed.id;
         ++pointer;
      }

      if(aspects.getAmount(Aspect.FLIGHT) > 0) {
         data.potionEffects[pointer] = Potion.jump.id;
         ++pointer;
      }

      if(aspects.getAmount(Aspect.HEAL) > 0) {
         data.potionEffects[pointer] = Potion.regeneration.id;
         ++pointer;
      }

      if(aspects.getAmount(Aspect.TRAVEL) > 0) {
         data.potionEffects[pointer] = Potion.moveSpeed.id;
         ++pointer;
      }

      if(aspects.getAmount(Aspect.TOOL) > 0) {
         data.potionEffects[pointer] = Potion.digSpeed.id;
         ++pointer;
      }

      if(aspects.getAmount(Aspect.WEAPON) > 0) {
         data.potionEffects[pointer] = Potion.damageBoost.id;
         ++pointer;
      }

      if(aspects.getAmount(Aspect.ARMOR) > 0) {
         data.potionEffects[pointer] = Potion.resistance.id;
         ++pointer;
      }

   }

   public static boolean drawRings(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects) {
      boolean drewAnything = false;
      int numRings = 0;
      if(aspects.getAmount(Aspect.ELDRITCH) > 0) {
         ++numRings;
         drawSphere(xCenter, yCenter, zCenter, data.radius - numRings, ConfigBlocks.blockCosmeticSolid, 11, world);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.DARKNESS) > 0) {
         ++numRings;
         drawSphere(xCenter, yCenter, zCenter, data.radius - numRings, Blocks.obsidian, 0, world);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.LIGHT) > 0) {
         ++numRings;
         drawSphere(xCenter, yCenter, zCenter, data.radius - numRings, ThaumicHorizons.blockLight, 11, world);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.ELDRITCH) > 0) {
         ++numRings;
         drawSphere(xCenter, yCenter, zCenter, data.radius - numRings, ConfigBlocks.blockCosmeticSolid, 11, world);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.CRYSTAL) > 0) {
         ++numRings;
         drawSphere(xCenter, yCenter, zCenter, data.radius - numRings, Blocks.glass, 0, world);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.COLD) > 0) {
         ++numRings;
         drawSphere(xCenter, yCenter, zCenter, data.radius - numRings, Blocks.packed_ice, 0, world);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.WEATHER) > 0 || aspects.getAmount(Aspect.AIR) > 0 && aspects.getAmount(Aspect.WATER) > 0) {
         ++numRings;
         drawSphere(xCenter, yCenter, zCenter, data.radius - numRings, ThaumicHorizons.blockCloud, 0, world);
         drewAnything = true;
      }

      return drewAnything;
   }

   public static int calcNoise(AspectList aspects) {
      byte noise = 50;
      int noise1 = (int)((float)noise - aspectFraction(Aspect.ORDER, aspects) * 100.0F);
      noise1 = (int)((float)noise1 + aspectFraction(Aspect.ENTROPY, aspects) * 100.0F);
      noise1 = (int)((float)noise1 + aspectFraction(Aspect.MOTION, aspects) * 50.0F);
      noise1 = (int)((float)noise1 + aspectFraction(Aspect.EXCHANGE, aspects) * 100.0F);
      noise1 = (int)((float)noise1 + aspectFraction(Aspect.FLIGHT, aspects) * 50.0F);
      noise1 = (int)((float)noise1 - aspectFraction(Aspect.TRAVEL, aspects) * 100.0F);
      return noise1 > 100?100:(noise1 < 0?0:noise1);
   }

   public static int calcLife(AspectList aspects) {
      byte life = 0;
      int life1 = (int)((float)life + aspectFraction(Aspect.LIGHT, aspects) * 25.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.WEATHER, aspects) * 25.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.LIFE, aspects) * 150.0F);
      life1 = (int)((float)life1 - aspectFraction(Aspect.POISON, aspects) * 75.0F);
      life1 = (int)((float)life1 - aspectFraction(Aspect.DEATH, aspects) * 100.0F);
      life1 = (int)((float)life1 - aspectFraction(Aspect.DARKNESS, aspects) * 25.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.SOUL, aspects) * 50.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.HEAL, aspects) * 150.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.AURA, aspects) * 50.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.SLIME, aspects) * 50.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.PLANT, aspects) * 100.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.TREE, aspects) * 100.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.BEAST, aspects) * 100.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.FLESH, aspects) * 50.0F);
      life1 = (int)((float)life1 - aspectFraction(Aspect.UNDEAD, aspects) * 50.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.MIND, aspects) * 25.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.SENSES, aspects) * 25.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.MAN, aspects) * 25.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.CROP, aspects) * 50.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.HARVEST, aspects) * 25.0F);
      life1 = (int)((float)life1 - aspectFraction(Aspect.WEAPON, aspects) * 25.0F);
      life1 = (int)((float)life1 - aspectFraction(Aspect.HUNGER, aspects) * 25.0F);
      life1 = (int)((float)life1 + aspectFraction(Aspect.CLOTH, aspects) * 25.0F);
      if(life1 < 0) {
         life1 = 0;
      } else if(life1 > 100) {
         life1 = 100;
      }

      return life1;
   }

   public static float aspectFraction(Aspect asp, AspectList aspects) {
      return (float)aspects.getAmount(asp) / (float)aspects.visSize();
   }

   public static void drawLayers(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise, BiomeGenBase bio, int life) {
      int total = aspects.visSize();
      int level = yCenter;
      boolean drewAnything = false;
      if(aspects.getAmount(Aspect.COLD) > 0) {
         drawLayer(xCenter, yCenter, zCenter, data, world, Blocks.packed_ice, 0, yCenter, 0, (BiomeGenBase)null, 0, aspects);
         level = yCenter - (int)(aspectFraction(Aspect.COLD, aspects) * (float)data.radius);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.WATER) > 0) {
         drawLayer(xCenter, yCenter, zCenter, data, world, Blocks.water, 0, level, 0, bio, life, aspects);
         level -= (int)(aspectFraction(Aspect.WATER, aspects) * (float)data.radius / 1.5F);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.TAINT) > 0) {
         drawLayer(xCenter, yCenter, zCenter, data, world, ConfigBlocks.blockTaint, 1, level, noise, bio, life, aspects);
         level -= (int)(aspectFraction(Aspect.TAINT, aspects) * (float)data.radius);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.CLOTH) > 0) {
         drawLayer(xCenter, yCenter, zCenter, data, world, Blocks.wool, 0, level, noise, (BiomeGenBase)null, 0, aspects);
         level -= (int)(aspectFraction(Aspect.CLOTH, aspects) * (float)data.radius);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.FLESH) > 0) {
         drawLayer(xCenter, yCenter, zCenter, data, world, ConfigBlocks.blockTaint, 2, level, noise, (BiomeGenBase)null, 0, aspects);
         level -= (int)(aspectFraction(Aspect.CLOTH, aspects) * (float)data.radius);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.EARTH) > 0) {
         if(bio == BiomeGenBase.desert) {
            drawLayer(xCenter, yCenter, zCenter, data, world, Blocks.sand, 0, level, noise, bio, life, aspects);
         } else {
            drawLayer(xCenter, yCenter, zCenter, data, world, Blocks.dirt, 0, level, noise, bio, life, aspects);
         }

         if(aspects.getAmount(Aspect.ORDER) >= aspects.getAmount(Aspect.ENTROPY)) {
            drawLayer(xCenter, yCenter, zCenter, data, world, Blocks.stone, 0, level - 5, noise, (BiomeGenBase)null, 0, aspects);
         } else {
            drawLayer(xCenter, yCenter, zCenter, data, world, Blocks.cobblestone, 0, level - 5, noise, (BiomeGenBase)null, 0, aspects);
         }

         level -= (int)(aspectFraction(Aspect.EARTH, aspects) * (float)data.radius);
         drewAnything = true;
      }

      if(aspects.getAmount(Aspect.FIRE) > 0) {
         drawLayer(xCenter, yCenter, zCenter, data, world, Blocks.netherrack, 0, level, noise, (BiomeGenBase)null, 0, aspects);
         int var10000 = level - (int)(aspectFraction(Aspect.FIRE, aspects) * (float)data.radius);
         drewAnything = true;
      }

      if(!drewAnything) {
         ;
      }

   }

   public static void drawLayer(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, Block block, int md, int level, int noise, BiomeGenBase bio, int life, AspectList aspects) {
      NoiseGeneratorOctaves noiseGen = new NoiseGeneratorOctaves(world.rand, 10);
      double[] noiseData = null;
      if(noise != 0) {
         noiseData = noiseGen.generateNoiseOctaves(noiseData, xCenter - data.radius, yCenter, zCenter - data.radius, 2 * data.radius, 1, 2 * data.radius, (double)((float)noise / 25.0F), (double)((float)noise / 50.0F), (double)((float)noise / 25.0F));
      }

      for(int x = -data.radius; x <= data.radius; ++x) {
         for(int z = -data.radius; z <= data.radius; ++z) {
            int top = 0;
            if(noise != 0 && x + data.radius + z * 2 * data.radius + data.radius * data.radius * 2 < noiseData.length) {
               top = (int)(noiseData[x + data.radius + z * 2 * data.radius + data.radius * data.radius * 2] / 8.0D);
            }

            int offsettop = -level + yCenter;
            int ctop = x * x + z * z + offsettop * offsettop - data.radius * data.radius;
            int tmax = offsettop + (int)Math.sqrt((double)(offsettop * offsettop - ctop));
            int offsetbottom = level - yCenter;
            int cbottom = x * x + z * z + offsetbottom * offsetbottom - data.radius * data.radius;
            int bmax = offsetbottom + (int)Math.sqrt((double)(offsetbottom * offsetbottom - cbottom));
            if(top > tmax) {
               top = tmax;
            }

            int bottom = -bmax;

            for(int y = bottom; y <= top; ++y) {
               if(top != bottom && level + y > 0) {
                  if(y == top) {
                     if(block == Blocks.water) {
                        if(bio != null && bio.getTempCategory() == TempCategory.COLD) {
                           world.setBlock(x + xCenter, y + level, z + zCenter, Blocks.ice, 0, 0);
                        } else {
                           world.setBlock(x + xCenter, y + level, z + zCenter, block, 0, 0);
                           if((life > 40 || aspects.getAmount(Aspect.BEAST) > 0) && world.rand.nextInt(100) > 98) {
                              EntitySquid var28 = new EntitySquid(world);
                              var28.setPosition((double)(x + xCenter), (double)(y + level), (double)(z + zCenter));
                              var28.func_110163_bv();
                              world.spawnEntityInWorld(var28);
                           }

                           if(life > 0 && world.rand.nextInt(100) > 98) {
                              world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.waterlily, 0, 0);
                           }
                        }
                     } else {
                        int slime;
                        if(block == Blocks.dirt) {
                           if(life > 0 && world.isAirBlock(x + xCenter, y + level + 1, z + zCenter)) {
                              world.setBlock(x + xCenter, y + level, z + zCenter, Blocks.grass, 0, 0);
                              if(life >= 10) {
                                 if((life >= 20 || aspects.getAmount(Aspect.CROP) > 0 || aspects.getAmount(Aspect.HARVEST) > 0) && world.rand.nextInt(100) > 97) {
                                    switch(world.rand.nextInt(10)) {
                                    case 0:
                                       world.setBlock(x + xCenter, y + level, z + zCenter, Blocks.farmland, 0, 0);
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.carrots, 0, 0);
                                       break;
                                    case 1:
                                       world.setBlock(x + xCenter, y + level, z + zCenter, Blocks.farmland, 0, 0);
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.potatoes, 0, 0);
                                       break;
                                    case 2:
                                       world.setBlock(x + xCenter, y + level, z + zCenter, Blocks.farmland, 0, 0);
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.wheat, 0, 0);
                                       break;
                                    case 3:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.melon_block, 0, 0);
                                       break;
                                    case 4:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.pumpkin, 0, 0);
                                       break;
                                    case 5:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.reeds, 0, 0);
                                       break;
                                    case 6:
                                       world.setBlock(x + xCenter, y + level, z + zCenter, Blocks.mycelium, 0, 0);
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.red_mushroom, 0, 0);
                                       break;
                                    case 7:
                                       world.setBlock(x + xCenter, y + level, z + zCenter, Blocks.mycelium, 0, 0);
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.brown_mushroom, 0, 0);
                                       break;
                                    case 8:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, ConfigBlocks.blockCustomPlant, 2, 0);
                                       break;
                                    case 9:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, ConfigBlocks.blockCustomPlant, 5, 0);
                                    }
                                 } else if((life >= 15 || aspects.getAmount(Aspect.TREE) > 0) && world.rand.nextInt(100) > 97) {
                                    switch(world.rand.nextInt(10)) {
                                    case 0:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.sapling, 0, 0);
                                       break;
                                    case 1:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.sapling, 1, 0);
                                       break;
                                    case 2:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.sapling, 2, 0);
                                       break;
                                    case 3:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.sapling, 3, 0);
                                       break;
                                    case 4:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.sapling, 4, 0);
                                       break;
                                    case 5:
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.sapling, 5, 0);
                                       break;
                                    case 6:
                                       if((bio != null && bio.biomeID == Config.biomeMagicalForestID || aspects.getAmount(Aspect.MAGIC) > 10 || aspects.getAmount(Aspect.AURA) > 5) && world.rand.nextInt(4) == 0) {
                                          world.setBlock(x + xCenter, y + level + 1, z + zCenter, ConfigBlocks.blockCustomPlant, 1, 0);
                                       }
                                       break;
                                    case 7:
                                       if(world.rand.nextInt(3) == 0) {
                                          world.setBlock(x + xCenter, y + level + 1, z + zCenter, ConfigBlocks.blockCustomPlant, 0, 0);
                                       }
                                    }
                                 } else if((life >= 10 || aspects.getAmount(Aspect.PLANT) > 0) && world.rand.nextInt(100) > 94) {
                                    slime = world.rand.nextInt(18);
                                    if(slime <= 15) {
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.red_flower, slime, 0);
                                    } else {
                                       world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.tallgrass, slime - 16, 0);
                                    }
                                 }
                              }
                           } else {
                              world.setBlock(x + xCenter, y + level, z + zCenter, block, 0, 0);
                           }
                        } else if(block == Blocks.sand) {
                           world.setBlock(x + xCenter, y + level, z + zCenter, block, 0, 0);
                           if(life > 10) {
                              if(life >= 20 && world.rand.nextInt(100) > 98) {
                                 world.setBlock(x + xCenter, y + level + 1, z + zCenter, ConfigBlocks.blockCustomPlant, 3, 0);
                              } else if(world.rand.nextInt(100) > 97) {
                                 world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.cactus, 0, 0);
                              }
                           }
                        } else if(block == ConfigBlocks.blockTaint && md == 2 && world.rand.nextInt(300) > 298) {
                           int why;
                           int zee;
                           if(world.rand.nextBoolean()) {
                              for(slime = 0; slime < 3; ++slime) {
                                 for(zee = 0; zee < 2; ++zee) {
                                    for(why = 0; why < 2; ++why) {
                                       world.setBlock(x + xCenter + slime, y + level + why, z + zCenter + zee, ThaumicHorizons.blockBrain, 0, 0);
                                    }
                                 }
                              }
                           } else {
                              for(slime = 0; slime < 2; ++slime) {
                                 for(zee = 0; zee < 3; ++zee) {
                                    for(why = 0; why < 2; ++why) {
                                       world.setBlock(x + xCenter + slime, y + level + why, z + zCenter + zee, ThaumicHorizons.blockBrain, 0, 0);
                                    }
                                 }
                              }
                           }
                        }
                     }

                     if(bio != null && block.isNormalCube() && bio.getTempCategory() == TempCategory.COLD) {
                        world.setBlock(x + xCenter, y + level + 1, z + zCenter, Blocks.snow_layer, 1, 0);
                     }

                     if((life >= 50 || aspects.getAmount(Aspect.BEAST) > 0) && world.rand.nextInt(100) > 98) {
                        Object var29 = null;
                        switch(world.rand.nextInt(7)) {
                        case 0:
                           var29 = new EntityCow(world);
                           break;
                        case 1:
                           var29 = new EntityPig(world);
                           break;
                        case 2:
                           var29 = new EntityChicken(world);
                           break;
                        case 3:
                           var29 = new EntitySheep(world);
                           break;
                        case 4:
                           var29 = new EntityHorse(world);
                           break;
                        case 5:
                           var29 = new EntityOcelot(world);
                           break;
                        case 6:
                           var29 = new EntityWolf(world);
                        }

                        if(var29 != null) {
                           ((EntityLiving)var29).setPosition((double)(x + xCenter) + 0.5D, (double)(y + level + 1), (double)(z + zCenter) + 0.5D);
                           world.spawnEntityInWorld((Entity)var29);
                        }
                     }

                     if((aspects.getAmount(Aspect.POISON) > 0 || aspects.getAmount(Aspect.EXCHANGE) > 0 || aspects.getAmount(Aspect.METAL) > 0 || aspects.getAmount(Aspect.MECHANISM) > 0) && world.rand.nextInt(100) > 98) {
                        EntityMercurialSlime var34 = new EntityMercurialSlime(world);
                        var34.setPosition((double)(x + xCenter) + 0.5D, (double)(y + level + 1), (double)(z + zCenter) + 0.5D);
                        world.spawnEntityInWorld(var34);
                     }

                     if((aspects.getAmount(Aspect.SLIME) > 0 || aspects.getAmount(Aspect.FLESH) > 0 || aspects.getAmount(Aspect.HUNGER) > 0) && world.rand.nextInt(100) > 98) {
                        EntityMeatSlime var31 = new EntityMeatSlime(world);
                        var31.setPosition((double)(x + xCenter) + 0.5D, (double)(y + level + 1), (double)(z + zCenter) + 0.5D);
                        world.spawnEntityInWorld(var31);
                     }

                     if(aspects.getAmount(Aspect.SLIME) > 0 && world.rand.nextInt(100) > 98) {
                        EntitySlime var30 = new EntitySlime(world);
                        var30.setPosition((double)(x + xCenter) + 0.5D, (double)(y + level + 1), (double)(z + zCenter) + 0.5D);
                        world.spawnEntityInWorld(var30);
                     }

                     if(aspects.getAmount(Aspect.ELDRITCH) > 0 && world.rand.nextInt(200) > 198) {
                        world.setBlock(x + xCenter, y + level, z + zCenter, ConfigBlocks.blockCosmeticSolid, 1, 0);
                        world.setBlock(x + xCenter, y + level + 1, z + zCenter, ConfigBlocks.blockCosmeticSolid, 0, 0);
                        world.setBlock(x + xCenter, y + level + 2, z + zCenter, ConfigBlocks.blockCosmeticSolid, 0, 0);
                        world.setBlock(x + xCenter, y + level + 3, z + zCenter, ConfigBlocks.blockCosmeticSolid, 0, 0);
                     }

                     if(aspects.getAmount(Aspect.CRAFT) > 0 && world.rand.nextInt(200) > 198) {
                        world.setBlock(x + xCenter, y + level + 1, z + zCenter, ConfigBlocks.blockStoneDevice, 1, 0);
                     }

                     if(aspects.getAmount(Aspect.TAINT) > 0 && world.rand.nextInt(100) > 98) {
                        world.setBlock(x + xCenter, y + level, z + zCenter, ConfigBlocks.blockTaintFibres, 0, 0);
                     }

                     if(aspects.getAmount(Aspect.TAINT) > 0 && world.rand.nextInt(200) > 198) {
                        EntityTaintacle var33 = new EntityTaintacle(world);
                        var33.setPosition((double)(x + xCenter) + 0.5D, (double)(y + level + 1), (double)(z + zCenter) + 0.5D);
                        world.spawnEntityInWorld(var33);
                     }

                     if(aspects.getAmount(Aspect.TAINT) > 0 && world.rand.nextInt(200) > 198) {
                        EntityTaintSporeSwarmer var32 = new EntityTaintSporeSwarmer(world);
                        var32.setPosition((double)(x + xCenter) + 0.5D, (double)(y + level + 1), (double)(z + zCenter) + 0.5D);
                        world.spawnEntityInWorld(var32);
                     }
                  } else if(block != null) {
                     world.setBlock(x + xCenter, y + level, z + zCenter, block, md, 0);
                  }
               }
            }
         }
      }

   }

   public static void drawCaves(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise) {
      float solidity = 0.5F;
      solidity -= aspectFraction(Aspect.AIR, aspects) * 0.5F;
      solidity += aspectFraction(Aspect.ORDER, aspects) * 0.25F;
      solidity -= aspectFraction(Aspect.VOID, aspects);
      solidity -= aspectFraction(Aspect.FLIGHT, aspects) * 0.5F;
      solidity += aspectFraction(Aspect.CRAFT, aspects) * 0.5F;
      solidity -= aspectFraction(Aspect.TRAP, aspects) * 0.5F;
      if(solidity <= 0.0F) {
         solidity = 0.0F;
      } else if(solidity > 1.0F) {
         solidity = 1.0F;
      }

      int numCaves = (int)((float)(data.radius * data.radius * data.radius / 10000) * (solidity + 0.1F));
      HashMap dressing = new HashMap();
      if(aspects.getAmount(Aspect.EARTH) > 0) {
         dressing.put(Aspect.EARTH, Float.valueOf(aspectFraction(Aspect.EARTH, aspects)));
      }

      if(aspects.getAmount(Aspect.FIRE) > 0) {
         dressing.put(Aspect.FIRE, Float.valueOf(aspectFraction(Aspect.FIRE, aspects)));
      }

      if(aspects.getAmount(Aspect.ORDER) > 0) {
         dressing.put(Aspect.ORDER, Float.valueOf(aspectFraction(Aspect.ORDER, aspects)));
      }

      if(aspects.getAmount(Aspect.AIR) > 0) {
         dressing.put(Aspect.AIR, Float.valueOf(aspectFraction(Aspect.AIR, aspects)));
      }

      if(aspects.getAmount(Aspect.WATER) > 0) {
         dressing.put(Aspect.WATER, Float.valueOf(aspectFraction(Aspect.WATER, aspects)));
      }

      if(aspects.getAmount(Aspect.ENTROPY) > 0) {
         dressing.put(Aspect.ENTROPY, Float.valueOf(aspectFraction(Aspect.ENTROPY, aspects)));
      }

      if(aspects.getAmount(Aspect.METAL) > 0) {
         dressing.put(Aspect.METAL, Float.valueOf(aspectFraction(Aspect.METAL, aspects)));
      }

      if(aspects.getAmount(Aspect.DEATH) > 0) {
         dressing.put(Aspect.DEATH, Float.valueOf(aspectFraction(Aspect.DEATH, aspects)));
      }

      if(aspects.getAmount(Aspect.UNDEAD) > 0) {
         dressing.put(Aspect.UNDEAD, Float.valueOf(aspectFraction(Aspect.UNDEAD, aspects)));
      }

      if(aspects.getAmount(Aspect.SENSES) > 0) {
         dressing.put(Aspect.SENSES, Float.valueOf(aspectFraction(Aspect.SENSES, aspects)));
      }

      if(aspects.getAmount(Aspect.GREED) > 0) {
         dressing.put(Aspect.GREED, Float.valueOf(aspectFraction(Aspect.GREED, aspects)));
      }

      for(int i = 0; i < numCaves; ++i) {
         drawACave(xCenter, yCenter, zCenter, data, world, aspects, noise, Blocks.air, world.rand.nextInt(10) + 10, world.rand.nextInt(10) + 10, world.rand.nextInt((int)((float)data.radius * 1.5F)) - (int)((float)data.radius * 0.75F), -world.rand.nextInt((int)((float)data.radius * 0.5F)), world.rand.nextInt((int)((float)data.radius * 1.5F)) - (int)((float)data.radius * 0.75F), dressing);
      }

   }

   public static void drawACave(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise, Block block, int xSize, int zSize, int xOffset, int yOffset, int zOffset, HashMap dressing) {
      NoiseGeneratorOctaves noiseGen = new NoiseGeneratorOctaves(world.rand, 10);
      Object noiseData = null;
      double[] var24 = noiseGen.generateNoiseOctaves((double[])noiseData, xCenter - xSize + xOffset, yCenter + yOffset, zCenter - zSize + zOffset, xSize, 16, zSize, (double)((float)noise / 10.0F), (double)((float)noise / 25.0F), (double)((float)noise / 10.0F));
      int avg = getAvg(var24);

      for(int x = 0; x < xSize; ++x) {
         for(int z = 0; z < zSize; ++z) {
            for(int y = 0; y < 16; ++y) {
               if(var24[x + xSize * z + y * xSize * zSize] > (double)avg && world.getBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z) != Blocks.water) {
                  if(!world.isAirBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y - 1, zCenter - zSize + zOffset + z) && world.rand.nextInt(50) == 49) {
                     float which = world.rand.nextFloat();
                     Aspect whichAspect = null;
                     Iterator it = dressing.keySet().iterator();

                     boolean doDressing;
                     for(doDressing = false; it.hasNext(); which -= ((Float)dressing.get(whichAspect)).floatValue()) {
                        whichAspect = (Aspect)it.next();
                        if(which <= ((Float)dressing.get(whichAspect)).floatValue()) {
                           doDressing = true;
                           break;
                        }
                     }

                     if(doDressing && whichAspect != null && !world.isAirBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z) && world.getBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z) != Blocks.water) {
                        if(whichAspect == Aspect.EARTH) {
                           world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, ConfigBlocks.blockCrystal, 3, 0);
                        } else if(whichAspect == Aspect.AIR) {
                           world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, ConfigBlocks.blockCrystal, 0, 0);
                        } else if(whichAspect == Aspect.WATER) {
                           world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, ConfigBlocks.blockCrystal, 2, 0);
                        } else if(whichAspect == Aspect.FIRE) {
                           world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, ConfigBlocks.blockCrystal, 1, 0);
                        } else if(whichAspect == Aspect.ORDER) {
                           world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, ConfigBlocks.blockCrystal, 4, 0);
                        } else if(whichAspect == Aspect.ENTROPY) {
                           world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, ConfigBlocks.blockCrystal, 5, 0);
                        } else if(whichAspect != Aspect.DEATH && whichAspect != Aspect.UNDEAD) {
                           if(whichAspect == Aspect.SENSES) {
                              world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, Blocks.lapis_block, 0, 0);
                           } else if(whichAspect == Aspect.GREED) {
                              if(world.rand.nextBoolean()) {
                                 world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, Blocks.gold_block, 0, 0);
                              } else {
                                 world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, Blocks.emerald_block, 0, 0);
                              }
                           } else if(whichAspect == Aspect.METAL) {
                              if(world.rand.nextBoolean()) {
                                 world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, Blocks.gold_block, 0, 0);
                              } else {
                                 world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, Blocks.iron_block, 0, 0);
                              }
                           }
                        } else {
                           world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, Blocks.skull, 0, 0);
                        }
                     }
                  } else {
                     world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, Blocks.air, 0, 0);
                  }
               }
            }
         }
      }

   }

   public static void drawRavines(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise) {}

   public static void drawClouds(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise) {}

   public static void drawSurfaceFeatures(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise, float life) {}

   public static void drawUndergroundFeatures(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise, float life) {}

   public static void drawLeviathanBones(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise) {}

   public static int getAvg(double[] array) {
      long x = 0L;

      for(int i = 0; i < array.length; ++i) {
         x = (long)((double)x + array[i]);
      }

      x /= (long)array.length;
      return (int)x;
   }

   public static void drawPockets(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise) {
      int numPockets = data.radius * data.radius * data.radius / 200;
      HashMap probabilities = new HashMap();
      if(aspects.getAmount(Aspect.EARTH) > 0) {
         probabilities.put(Aspect.EARTH, Float.valueOf(aspectFraction(Aspect.EARTH, aspects)));
      }

      if(aspects.getAmount(Aspect.FIRE) > 0) {
         probabilities.put(Aspect.FIRE, Float.valueOf(aspectFraction(Aspect.FIRE, aspects)));
      }

      if(aspects.getAmount(Aspect.ORDER) > 0) {
         probabilities.put(Aspect.ORDER, Float.valueOf(aspectFraction(Aspect.ORDER, aspects)));
      }

      if(aspects.getAmount(Aspect.AIR) > 0) {
         probabilities.put(Aspect.AIR, Float.valueOf(aspectFraction(Aspect.AIR, aspects)));
      }

      if(aspects.getAmount(Aspect.WATER) > 0) {
         probabilities.put(Aspect.WATER, Float.valueOf(aspectFraction(Aspect.WATER, aspects)));
      }

      if(aspects.getAmount(Aspect.ENTROPY) > 0) {
         probabilities.put(Aspect.ENTROPY, Float.valueOf(aspectFraction(Aspect.ENTROPY, aspects)));
      }

      if(aspects.getAmount(Aspect.METAL) > 0) {
         probabilities.put(Aspect.METAL, Float.valueOf(aspectFraction(Aspect.METAL, aspects)));
      }

      if(aspects.getAmount(Aspect.CRYSTAL) > 0) {
         probabilities.put(Aspect.CRYSTAL, Float.valueOf(aspectFraction(Aspect.CRYSTAL, aspects)));
      }

      if(aspects.getAmount(Aspect.SOUL) > 0) {
         probabilities.put(Aspect.SOUL, Float.valueOf(aspectFraction(Aspect.SOUL, aspects)));
      }

      if(aspects.getAmount(Aspect.MAGIC) > 0) {
         probabilities.put(Aspect.MAGIC, Float.valueOf(aspectFraction(Aspect.MAGIC, aspects)));
      }

      if(aspects.getAmount(Aspect.AURA) > 0) {
         probabilities.put(Aspect.AURA, Float.valueOf(aspectFraction(Aspect.AURA, aspects)));
      }

      if(aspects.getAmount(Aspect.ENERGY) > 0) {
         probabilities.put(Aspect.ENERGY, Float.valueOf(aspectFraction(Aspect.ENERGY, aspects)));
      }

      if(aspects.getAmount(Aspect.TREE) > 0) {
         probabilities.put(Aspect.TREE, Float.valueOf(aspectFraction(Aspect.TREE, aspects)));
      }

      if(aspects.getAmount(Aspect.FLESH) > 0) {
         probabilities.put(Aspect.FLESH, Float.valueOf(aspectFraction(Aspect.FLESH, aspects)));
      }

      if(aspects.getAmount(Aspect.SENSES) > 0) {
         probabilities.put(Aspect.SENSES, Float.valueOf(aspectFraction(Aspect.SENSES, aspects)));
      }

      if(aspects.getAmount(Aspect.MINE) > 0) {
         probabilities.put(Aspect.MINE, Float.valueOf(aspectFraction(Aspect.MINE, aspects)));
      }

      if(aspects.getAmount(Aspect.TOOL) > 0) {
         probabilities.put(Aspect.TOOL, Float.valueOf(aspectFraction(Aspect.TOOL, aspects)));
      }

      if(aspects.getAmount(Aspect.GREED) > 0) {
         probabilities.put(Aspect.GREED, Float.valueOf(aspectFraction(Aspect.GREED, aspects)));
      }

      if(aspects.getAmount(Aspect.TAINT) > 0) {
         probabilities.put(Aspect.TAINT, Float.valueOf(aspectFraction(Aspect.TAINT, aspects)));
      }

      if(aspects.getAmount(Aspect.POISON) > 0) {
         probabilities.put(Aspect.POISON, Float.valueOf(aspectFraction(Aspect.POISON, aspects)));
      }

      if(aspects.getAmount(Aspect.EXCHANGE) > 0) {
         probabilities.put(Aspect.EXCHANGE, Float.valueOf(aspectFraction(Aspect.EXCHANGE, aspects)));
      }

      if(aspects.getAmount(Aspect.MIND) > 0) {
         probabilities.put(Aspect.MIND, Float.valueOf(aspectFraction(Aspect.MIND, aspects)));
      }

      if(aspects.getAmount(Aspect.MAN) > 0) {
         probabilities.put(Aspect.MAN, Float.valueOf(aspectFraction(Aspect.MAN, aspects)));
      }

      if(aspects.getAmount(Aspect.HUNGER) > 0) {
         probabilities.put(Aspect.HUNGER, Float.valueOf(aspectFraction(Aspect.HUNGER, aspects)));
      }

      if(aspects.getAmount(Aspect.CRAFT) > 0) {
         probabilities.put(Aspect.CRAFT, Float.valueOf(aspectFraction(Aspect.CRAFT, aspects)));
      }

      Block block = Blocks.air;
      int md = 0;
      int i = 0;

      while(i < numPockets) {
         float which = world.rand.nextFloat();
         Aspect whichAspect = null;
         Iterator it = probabilities.keySet().iterator();
         boolean doDressing = false;

         while(true) {
            if(it.hasNext()) {
               whichAspect = (Aspect)it.next();
               if(which > ((Float)probabilities.get(whichAspect)).floatValue()) {
                  which -= ((Float)probabilities.get(whichAspect)).floatValue();
                  continue;
               }

               doDressing = true;
            }

            if(doDressing && whichAspect != null) {
               if(whichAspect == Aspect.EARTH) {
                  block = ConfigBlocks.blockCustomOre;
                  md = 4;
               } else if(whichAspect == Aspect.AIR) {
                  block = ConfigBlocks.blockCustomOre;
                  md = 1;
               } else if(whichAspect == Aspect.WATER) {
                  block = ConfigBlocks.blockCustomOre;
                  md = 3;
               } else if(whichAspect == Aspect.FIRE) {
                  if(world.rand.nextBoolean()) {
                     block = ConfigBlocks.blockCustomOre;
                     md = 2;
                  } else {
                     block = Blocks.lava;
                  }
               } else if(whichAspect == Aspect.ORDER) {
                  block = ConfigBlocks.blockCustomOre;
                  md = 5;
               } else if(whichAspect == Aspect.ENTROPY) {
                  block = ConfigBlocks.blockCustomOre;
                  md = 6;
               } else if(whichAspect == Aspect.METAL) {
                  if((double)world.rand.nextFloat() <= 0.5D) {
                     block = Blocks.iron_ore;
                  } else if((double)world.rand.nextFloat() <= 0.8D) {
                     block = Blocks.gold_ore;
                  } else {
                     block = ConfigBlocks.blockCustomOre;
                     md = 0;
                  }
               } else if(whichAspect != Aspect.MAGIC && whichAspect != Aspect.AURA) {
                  if(whichAspect == Aspect.CRYSTAL) {
                     block = Blocks.quartz_block;
                  } else if(whichAspect == Aspect.SOUL) {
                     block = Blocks.soul_sand;
                  } else if(whichAspect == Aspect.ENERGY) {
                     block = Blocks.redstone_block;
                  } else if(whichAspect == Aspect.TREE) {
                     if(world.rand.nextBoolean()) {
                        block = Blocks.log;
                     } else {
                        block = ConfigBlocks.blockCustomOre;
                        md = 7;
                     }
                  } else if(whichAspect == Aspect.FLESH) {
                     block = ConfigBlocks.blockTaint;
                     md = 2;
                  } else if(whichAspect == Aspect.SENSES) {
                     block = Blocks.lapis_ore;
                  } else {
                     float man;
                     if(whichAspect != Aspect.MINE && whichAspect != Aspect.TOOL) {
                        if(whichAspect == Aspect.GREED) {
                           man = world.rand.nextFloat();
                           if(man < 0.4F) {
                              block = Blocks.gold_ore;
                           } else if(man < 0.6F) {
                              block = Blocks.diamond_ore;
                           } else if(man < 0.8F) {
                              block = Blocks.emerald_ore;
                           } else {
                              block = Blocks.lapis_ore;
                           }
                        } else if(whichAspect == Aspect.TAINT) {
                           block = ConfigBlocks.blockTaint;
                           md = 0;
                        } else if(whichAspect == Aspect.POISON) {
                           block = ConfigBlocks.blockFluidDeath;
                           md = 16;
                        } else if(whichAspect == Aspect.EXCHANGE) {
                           block = ConfigBlocks.blockCustomOre;
                           md = 0;
                        } else if(whichAspect == Aspect.MIND) {
                           if(world.rand.nextBoolean()) {
                              block = Blocks.bookshelf;
                           } else {
                              block = ThaumicHorizons.blockBrain;
                           }
                        } else if(whichAspect == Aspect.MAN) {
                           int var17 = world.rand.nextInt(3);
                           if(var17 == 0) {
                              block = Blocks.bookshelf;
                           } else if(var17 == 1) {
                              block = ThaumicHorizons.blockBrain;
                           } else {
                              block = ConfigBlocks.blockTaint;
                              md = 2;
                           }
                        } else if(whichAspect == Aspect.HUNGER) {
                           block = ConfigBlocks.blockTaint;
                           md = 2;
                        } else if(whichAspect == Aspect.CRAFT) {
                           block = Blocks.crafting_table;
                        }
                     } else {
                        man = world.rand.nextFloat();
                        if(man < 0.4F) {
                           block = Blocks.iron_ore;
                        } else if(man < 0.5F) {
                           block = Blocks.gold_ore;
                        } else if(man < 0.6F) {
                           block = Blocks.diamond_ore;
                        } else if(man < 0.7F) {
                           block = Blocks.emerald_ore;
                        } else if(man < 0.8F) {
                           block = Blocks.lapis_ore;
                        } else {
                           block = ConfigBlocks.blockCustomOre;
                           md = world.rand.nextInt(8);
                        }
                     }
                  }
               } else {
                  block = ConfigBlocks.blockCustomOre;
                  md = world.rand.nextInt(6) + 1;
               }
            }

            drawAPocket(xCenter, yCenter, zCenter, data, world, aspects, noise, block, md, world.rand.nextInt(3) + 1, world.rand.nextInt(3) + 1, world.rand.nextInt((int)((float)data.radius * 2.0F)) - (int)((float)data.radius * 1.0F), -world.rand.nextInt((int)((float)data.radius * 1.0F)), world.rand.nextInt((int)((float)data.radius * 2.0F)) - (int)((float)data.radius * 1.0F));
            ++i;
            break;
         }
      }

   }

   public static void drawAPocket(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects, int noise, Block block, int md, int xSize, int zSize, int xOffset, int yOffset, int zOffset) {
      System.out.println("Drawing a pocket of " + block + " with width " + xSize + " and length " + zSize);
      NoiseGeneratorOctaves noiseGen = new NoiseGeneratorOctaves(world.rand, 10);
      Object noiseDataTop = null;
      double[] var22 = noiseGen.generateNoiseOctaves((double[])noiseDataTop, xCenter - xSize + xOffset, yCenter + yOffset, zCenter - zSize + zOffset, xSize, 1, zSize, (double)((float)noise / 50.0F), (double)((float)noise / 25.0F), (double)((float)noise / 50.0F));
      Object noiseDataBottom = null;
      double[] var23 = noiseGen.generateNoiseOctaves((double[])noiseDataBottom, xCenter - xSize + xOffset, yCenter + yOffset, zCenter - zSize + zOffset, xSize, 1, zSize, (double)((float)noise / 50.0F), (double)((float)noise / 25.0F), (double)((float)noise / 50.0F));

      for(int x = 0; x < xSize; ++x) {
         for(int z = 0; z < zSize; ++z) {
            int top = (int)(var22[x + z * xSize] / 16.0D) + 1;
            int bottom = (int)(var23[x + z * xSize] / 16.0D) - 1;

            for(int y = bottom; y < top; ++y) {
               if(!world.isAirBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z) && world.getBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z) != Blocks.water) {
                  world.setBlock(xCenter - xSize + xOffset + x, yCenter + yOffset + y, zCenter - zSize + zOffset + z, block, md, 0);
               }
            }
         }
      }

   }

   public static BiomeGenBase setBiome(int xCenter, int yCenter, int zCenter, PocketPlaneData data, World world, AspectList aspects) {
      BiomeGenBase bio = null;
      if(aspects.getAmount(Aspect.TAINT) > 0) {
         bio = ThaumcraftWorldGenerator.biomeTaint;
      } else if(aspects.getAmount(Aspect.ELDRITCH) <= 0 && aspects.getAmount(Aspect.UNDEAD) <= 0) {
         if(aspects.getAmount(Aspect.MAGIC) <= 0 && aspects.getAmount(Aspect.AURA) <= 0) {
            float x = 0.5F;
            float z = 0.5F;
            x += aspectFraction(Aspect.FIRE, aspects);
            x -= aspectFraction(Aspect.WATER, aspects) * 0.5F;
            x -= aspectFraction(Aspect.ORDER, aspects) * 0.5F;
            x -= aspectFraction(Aspect.VOID, aspects) * 0.5F;
            x += aspectFraction(Aspect.LIGHT, aspects) * 0.5F;
            x -= aspectFraction(Aspect.WEATHER, aspects) * 0.25F;
            x += aspectFraction(Aspect.MOTION, aspects) * 0.5F;
            x -= aspectFraction(Aspect.COLD, aspects);
            x += aspectFraction(Aspect.ENERGY, aspects) * 0.5F;
            x -= aspectFraction(Aspect.DARKNESS, aspects) * 0.25F;
            if(x < 0.0F) {
               x = 0.0F;
            } else if(x > 1.0F) {
               x = 1.0F;
            }

            z += aspectFraction(Aspect.WATER, aspects);
            z -= aspectFraction(Aspect.FIRE, aspects) * 0.5F;
            z -= aspectFraction(Aspect.ENTROPY, aspects) * 0.25F;
            z -= aspectFraction(Aspect.VOID, aspects) * 0.25F;
            z += aspectFraction(Aspect.WEATHER, aspects) * 0.5F;
            z += aspectFraction(Aspect.SLIME, aspects) * 0.5F;
            if(z < 0.0F) {
               z = 0.0F;
            } else if(z > 1.0F) {
               z = 1.0F;
            }

            if((double)x > 0.8D) {
               bio = BiomeGenBase.desert;
            } else if((double)x > 0.5D) {
               if((double)z < 0.4D) {
                  bio = BiomeGenBase.savanna;
               } else {
                  bio = BiomeGenBase.jungle;
               }
            } else if((double)x > 0.2D) {
               if((double)z < 0.4D) {
                  bio = BiomeGenBase.plains;
               } else {
                  bio = BiomeGenBase.forest;
               }
            } else {
               bio = BiomeGenBase.icePlains;
            }
         } else {
            bio = ThaumcraftWorldGenerator.biomeMagicalForest;
         }
      } else {
         bio = ThaumcraftWorldGenerator.biomeEerie;
      }

      for(int var9 = -data.radius; var9 <= data.radius; ++var9) {
         for(int var10 = -data.radius; var10 <= data.radius; ++var10) {
            Utils.setBiomeAt(world, var9 + xCenter, var10 + zCenter, bio);
         }
      }

      return bio;
   }

   public static void drawCircle(int x0, int y0, int z0, int y1, int radius, int error0, Block block, int md, World world) {
      int x = radius;
      int z = 0;
      int radiusError = error0;

      while(x >= z) {
         world.setBlock(x0 + x, y0 + y1, z0 + z, block, md, 0);
         world.setBlock(x0 + z, y0 + y1, z0 + x, block, md, 0);
         world.setBlock(x0 - x, y0 + y1, z0 + z, block, md, 0);
         world.setBlock(x0 - z, y0 + y1, z0 + x, block, md, 0);
         world.setBlock(x0 - x, y0 + y1, z0 - z, block, md, 0);
         world.setBlock(x0 - z, y0 + y1, z0 - x, block, md, 0);
         world.setBlock(x0 + x, y0 + y1, z0 - z, block, md, 0);
         world.setBlock(x0 + z, y0 + y1, z0 - x, block, md, 0);
         world.setBlock(x0 + x, y0 - y1, z0 + z, block, md, 0);
         world.setBlock(x0 + z, y0 - y1, z0 + x, block, md, 0);
         world.setBlock(x0 - x, y0 - y1, z0 + z, block, md, 0);
         world.setBlock(x0 - z, y0 - y1, z0 + x, block, md, 0);
         world.setBlock(x0 - x, y0 - y1, z0 - z, block, md, 0);
         world.setBlock(x0 - z, y0 - y1, z0 - x, block, md, 0);
         world.setBlock(x0 + x, y0 - y1, z0 - z, block, md, 0);
         world.setBlock(x0 + z, y0 - y1, z0 - x, block, md, 0);
         world.setBlock(x0 + y1, y0 + x, z0 + z, block, md, 0);
         world.setBlock(x0 + z, y0 + x, z0 + y1, block, md, 0);
         world.setBlock(x0 - y1, y0 + x, z0 + z, block, md, 0);
         world.setBlock(x0 - z, y0 + x, z0 + y1, block, md, 0);
         world.setBlock(x0 + y1, y0 + x, z0 - z, block, md, 0);
         world.setBlock(x0 + z, y0 + x, z0 - y1, block, md, 0);
         world.setBlock(x0 - y1, y0 + x, z0 - z, block, md, 0);
         world.setBlock(x0 - z, y0 + x, z0 - y1, block, md, 0);
         world.setBlock(x0 + y1, y0 - x, z0 + z, block, md, 0);
         world.setBlock(x0 + z, y0 - x, z0 + y1, block, md, 0);
         world.setBlock(x0 - y1, y0 - x, z0 + z, block, md, 0);
         world.setBlock(x0 - z, y0 - x, z0 + y1, block, md, 0);
         world.setBlock(x0 + y1, y0 - x, z0 - z, block, md, 0);
         world.setBlock(x0 + z, y0 - x, z0 - y1, block, md, 0);
         world.setBlock(x0 - y1, y0 - x, z0 - z, block, md, 0);
         world.setBlock(x0 - z, y0 - x, z0 - y1, block, md, 0);
         ++z;
         if(radiusError < 0) {
            radiusError += 2 * z + 1;
         } else {
            --x;
            radiusError += 2 * (z - x + 1);
         }
      }

   }

   public static void drawSphere(int x0, int y0, int z0, int radius, Block block, int md, World world) {
      int x = radius;
      int y = 0;
      int radiusError = 1 - radius;

      while(x >= y) {
         drawCircle(x0, y0, z0, y, x, radiusError, block, md, world);
         ++y;
         if(radiusError < 0) {
            radiusError += 2 * y + 1;
         } else {
            --x;
            radiusError += 2 * (y - x + 1);
         }
      }

   }

   public static void loadPocketPlanes(World world) {
      File planeFile = new File(world.getSaveHandler().getWorldDirectory(), "pocketplane.dat");
      NBTTagCompound root = null;
      if(planeFile.exists()) {
         try {
            root = CompressedStreamTools.readCompressed(new FileInputStream(planeFile));
         } catch (FileNotFoundException var7) {
            var7.printStackTrace();
         } catch (IOException var8) {
            var8.printStackTrace();
         }

         if(root != null) {
            planes.clear();
            NBTTagList planeNBT = root.getTagList("Data", 10);

            for(int positionz = 0; positionz < planeNBT.tagCount(); ++positionz) {
               NBTTagCompound it = planeNBT.getCompoundTagAt(positionz);
               PocketPlaneData id = new PocketPlaneData();
               id.radius = it.getInteger("radius");
               id.potionEffects = it.getIntArray("effects");
               id.name = it.getString("name");
               id.color = it.getInteger("color");
               id.portalA = it.getIntArray("portalA");
               id.portalB = it.getIntArray("portalB");
               id.portalC = it.getIntArray("portalC");
               id.portalD = it.getIntArray("portalD");
               planes.add(id);
            }

            NBTTagCompound var9 = root.getCompoundTag("Positions");
            positions.clear();
            Iterator var10 = var9.func_150296_c().iterator();

            while(var10.hasNext()) {
               String var11 = (String)var10.next();
               positions.add(Vec3.createVectorHelper((double)var9.getIntArray(var11)[0] + 0.5D, (double)(var9.getIntArray(var11)[1] + 1), (double)var9.getIntArray(var11)[2] + 0.5D));
            }
         }
      }

   }

   public static void savePocketPlanes(World world) {
      File planeFile = new File(world.getSaveHandler().getWorldDirectory(), "pocketplane.dat");
      NBTTagCompound root = new NBTTagCompound();
      NBTTagCompound positionz = new NBTTagCompound();
      int which = 0;

      for(Iterator it = positions.iterator(); it.hasNext(); ++which) {
         Vec3 planeNBT = (Vec3)it.next();
         positionz.setIntArray(which + "", new int[]{(int)planeNBT.xCoord, (int)planeNBT.yCoord, (int)planeNBT.zCoord});
      }

      root.setTag("Positions", positionz);
      NBTTagList var12 = new NBTTagList();
      root.setTag("Data", var12);
      Iterator e = planes.iterator();

      while(e.hasNext()) {
         PocketPlaneData data = (PocketPlaneData)e.next();
         if(data != null) {
            NBTTagCompound thePlane = new NBTTagCompound();
            thePlane.setInteger("radius", data.radius);
            thePlane.setIntArray("effects", data.potionEffects);
            thePlane.setString("name", data.name);
            thePlane.setInteger("color", data.color);
            thePlane.setIntArray("portalA", data.portalA);
            thePlane.setIntArray("portalB", data.portalB);
            thePlane.setIntArray("portalC", data.portalC);
            thePlane.setIntArray("portalD", data.portalD);
            var12.appendTag(thePlane);
         }
      }

      try {
         CompressedStreamTools.writeCompressed(root, new FileOutputStream(planeFile));
      } catch (FileNotFoundException var10) {
         var10.printStackTrace();
      } catch (IOException var11) {
         var11.printStackTrace();
      }

   }

   public static int firstAvailablePortal(int num) {
      PocketPlaneData data = (PocketPlaneData)planes.get(num);
      return data.portalA[0] == 0 && data.portalA[1] == 0 && data.portalA[2] == 0?1:(data.portalB[0] == 0 && data.portalB[1] == 0 && data.portalB[2] == 0?2:(data.portalC[0] == 0 && data.portalC[1] == 0 && data.portalC[2] == 0?3:(data.portalD[0] == 0 && data.portalD[1] == 0 && data.portalD[2] == 0?4:0)));
   }

   public static void destroyPortal(int id, int which) {
      System.out.println("Destroying portal " + which + " in plane " + id);
      PocketPlaneData data = (PocketPlaneData)planes.get(id);
      WorldServer world = MinecraftServer.getServer().worldServerForDimension(ThaumicHorizons.dimensionPocketId);
      int z;
      int y;
      if(which == 1) {
         for(z = -1; z <= 1; ++z) {
            for(y = 126; y <= 128; ++y) {
               world.setBlock(z, y, 256 * id + data.radius, ThaumicHorizons.blockVoid);
            }
         }

         data.portalA[0] = 0;
         data.portalA[1] = 0;
         data.portalA[2] = 0;
      } else if(which == 2) {
         for(z = -1; z <= 1; ++z) {
            for(y = 126; y <= 128; ++y) {
               world.setBlock(z, y, 256 * id - data.radius, ThaumicHorizons.blockVoid);
            }
         }

         data.portalB[0] = 0;
         data.portalB[1] = 0;
         data.portalB[2] = 0;
      } else if(which == 3) {
         for(z = -1; z <= 1; ++z) {
            for(y = 126; y <= 128; ++y) {
               world.setBlock(data.radius, y, 256 * id + z, ThaumicHorizons.blockVoid);
            }
         }

         data.portalC[0] = 0;
         data.portalC[1] = 0;
         data.portalC[2] = 0;
      } else if(which == 4) {
         for(z = -1; z <= 1; ++z) {
            for(y = 126; y <= 128; ++y) {
               world.setBlock(-data.radius, y, 256 * id + z, ThaumicHorizons.blockVoid);
            }
         }

         data.portalD[0] = 0;
         data.portalD[1] = 0;
         data.portalD[2] = 0;
      }

   }

   public static void makePortal(int id, int which, int xCoord, int yCoord, int zCoord) {
      System.out.println("Creating portal " + which + " in plane " + id);
      PocketPlaneData data = (PocketPlaneData)planes.get(id);
      WorldServer world = MinecraftServer.getServer().worldServerForDimension(ThaumicHorizons.dimensionPocketId);
      int z;
      int y;
      if(which == 1) {
         for(z = -1; z <= 1; ++z) {
            for(y = 126; y <= 128; ++y) {
               world.setBlock(z, y, 256 * id + data.radius, ThaumicHorizons.blockPortal, 0, 3);
               world.setBlock(z, y, 256 * id + data.radius + 1, ThaumicHorizons.blockVoid);
            }
         }

         data.portalA[0] = xCoord;
         data.portalA[1] = yCoord;
         data.portalA[2] = zCoord;
      } else if(which == 2) {
         for(z = -1; z <= 1; ++z) {
            for(y = 126; y <= 128; ++y) {
               world.setBlock(z, y, 256 * id - data.radius, ThaumicHorizons.blockPortal, 2, 3);
               world.setBlock(z, y, 256 * id - data.radius - 1, ThaumicHorizons.blockVoid);
            }
         }

         data.portalB[0] = xCoord;
         data.portalB[1] = yCoord;
         data.portalB[2] = zCoord;
      } else if(which == 3) {
         for(z = -1; z <= 1; ++z) {
            for(y = 126; y <= 128; ++y) {
               world.setBlock(data.radius, y, 256 * id + z, ThaumicHorizons.blockPortal, 1, 3);
               world.setBlock(data.radius + 1, y, 256 * id + z, ThaumicHorizons.blockVoid);
            }
         }

         data.portalC[0] = xCoord;
         data.portalC[1] = yCoord;
         data.portalC[2] = zCoord;
      } else if(which == 4) {
         for(z = -1; z <= 1; ++z) {
            for(y = 126; y <= 128; ++y) {
               world.setBlock(-data.radius, y, 256 * id + z, ThaumicHorizons.blockPortal, 3, 3);
               world.setBlock(-data.radius - 1, y, 256 * id + z, ThaumicHorizons.blockVoid);
            }
         }

         data.portalD[0] = xCoord;
         data.portalD[1] = yCoord;
         data.portalD[2] = zCoord;
      }

   }

   void buildStructure(int x, int y, int z, PocketPlaneData.Structure struct) {}


   private class Structure {

      public int x;
      public int y;
      public int z;
      public Block[] blocks;
      public int[] meta;


      public Structure(int x, int y, int z, Block[] blocks, int[] meta) {
         this.x = x;
         this.y = y;
         this.z = z;
         this.blocks = blocks;
         this.meta = meta;
      }
   }
}
