package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.research.ResearchManager;

public class WandManagerTH implements IWandTriggerManager {

   public boolean performTrigger(World world, ItemStack wand, EntityPlayer player, int x, int y, int z, int side, int event) {
      switch(event) {
      case 0:
         if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "healingVat")) {
            return this.constructVat(world, wand, player, x, y, z, side);
         }
      default:
         return false;
      }
   }

   boolean constructVat(World world, ItemStack itemstack, EntityPlayer player, int x, int y, int z, int side) {
      ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();

      for(int xx = x - 2; xx <= x; ++xx) {
         for(int yy = y - 3; yy <= y; ++yy) {
            for(int zz = z - 2; zz <= z; ++zz) {
               if(this.fitVat(world, xx, yy, zz) && wand.consumeAllVisCrafting(itemstack, player, (new AspectList()).add(Aspect.WATER, 50).add(Aspect.EARTH, 50).add(Aspect.ORDER, 50), true)) {
                  if(!world.isRemote) {
                     this.replaceVat(world, xx, yy, zz);
                     return true;
                  }

                  return false;
               }
            }
         }
      }

      return false;
   }

   boolean fitVat(World world, int x, int y, int z) {
      Block g = Blocks.glass;
      Block w = Blocks.water;
      Block p = ConfigBlocks.blockWoodenDevice;
      Block a = ConfigBlocks.blockMetalDevice;
      Block[][][] blueprint = new Block[][][]{{{p, p, p}, {p, a, p}, {p, p, p}}, {{g, g, g}, {g, w, g}, {g, g, g}}, {{g, g, g}, {g, w, g}, {g, g, g}}, {{p, p, p}, {p, a, p}, {p, p, p}}};

      for(int yy = 0; yy < 4; ++yy) {
         for(int xx = 0; xx < 3; ++xx) {
            for(int zz = 0; zz < 3; ++zz) {
               Block block = world.getBlock(x + xx, y - yy + 3, z + zz);
               if(world.isAirBlock(x + xx, y - yy + 3, z + zz)) {
                  block = Blocks.air;
               }

               if(block != blueprint[yy][xx][zz] || block == p && world.getBlockMetadata(x + xx, y - yy + 3, z + zz) != 6 || block == a && world.getBlockMetadata(x + xx, y - yy + 3, z + zz) != 9) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   void replaceVat(World world, int x, int y, int z) {
      int yy;
      int zz;
      int xx;
      for(yy = 0; yy < 4; ++yy) {
         for(zz = 0; zz < 3; ++zz) {
            for(xx = 0; xx < 3; ++xx) {
               byte md = 0;
               if(world.getBlock(x + xx, y + yy, z + zz) != Blocks.water && world.getBlock(x + xx, y + yy, z + zz) != Blocks.flowing_water) {
                  if(world.getBlock(x + xx, y + yy, z + zz) == Blocks.glass) {
                     md = 10;
                  } else if(world.getBlock(x + xx, y + yy, z + zz) != ConfigBlocks.blockWoodenDevice) {
                     if(world.getBlock(x + xx, y + yy, z + zz) == ConfigBlocks.blockMetalDevice) {
                        if(yy == 0) {
                           md = 6;
                        } else {
                           md = 7;
                        }
                     }
                  } else if(yy == 0 && (xx == 1 && zz == 0 || xx == 1 && zz == 2 || xx == 0 && zz == 1 || xx == 2 && zz == 1)) {
                     md = 4;
                  } else {
                     md = 5;
                  }
               } else {
                  md = 0;
               }

               if(!world.isAirBlock(x + xx, y + yy, z + zz)) {
                  if(md != 4 && md != 5 && md != 6 && md != 7) {
                     if(md != 0) {
                        world.setBlock(x + xx, y + yy, z + zz, ThaumicHorizons.blockVat, md, 3);
                     } else {
                        world.setBlock(x + xx, y + yy, z + zz, ThaumicHorizons.blockVatInterior, md, 3);
                     }
                  } else {
                     world.setBlock(x + xx, y + yy, z + zz, ThaumicHorizons.blockVatSolid, md, 3);
                  }

                  world.addBlockEvent(x + xx, y + yy, z + zz, ThaumicHorizons.blockVat, 1, 4);
               }
            }
         }
      }

      for(yy = 0; yy < 4; ++yy) {
         for(zz = 0; zz < 3; ++zz) {
            for(xx = 0; xx < 3; ++xx) {
               world.markBlockForUpdate(x + xx, y + yy, z + zz);
            }
         }
      }

      world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
   }
}
