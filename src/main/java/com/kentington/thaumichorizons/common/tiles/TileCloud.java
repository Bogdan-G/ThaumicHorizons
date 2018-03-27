package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityItemInvulnerable;
import com.kentington.thaumichorizons.common.entities.EntityLightningBoltFinite;
import com.kentington.thaumichorizons.common.items.ItemFocusLiquefaction;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.EntityAspectOrb;
import thaumcraft.common.entities.monster.EntityFireBat;

public class TileCloud extends TileThaumcraft {

   public int md = -1;
   int dropTimer = -1;
   boolean raining = false;
   public int howManyDown;
   public Block cachedBlock;
   int cachedMD;
   static int[] dropTimers = new int[]{-1, -1, 120, -1, 480, 480, 80, 360, 280, 550};


   public boolean isRaining() {
      return this.raining;
   }

   public void updateEntity() {
      super.updateEntity();
      if(this.md == -1) {
         this.md = super.worldObj.getBlockMetadata(super.xCoord, super.yCoord, super.zCoord);
         this.dropTimer = dropTimers[this.md];
         this.findBlockBelow();
      }

      boolean newRain = MinecraftServer.getServer().worldServerForDimension(0).isRaining();
      if(newRain != this.raining) {
         this.raining = newRain;
         this.markDirty();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      }

      if(this.raining) {
         if(super.worldObj.getTotalWorldTime() % 10L == 0L) {
            this.findBlockBelow();
            int critters;
            switch(this.md) {
            case 1:
               critters = ((ItemFocusLiquefaction)ThaumicHorizons.itemFocusLiquefaction).isMeltableBlock(this.cachedBlock, this.cachedMD);
               if(critters == 1) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.fire);
                  ThaumicHorizons.proxy.smeltFX((double)super.xCoord, (double)(super.yCoord - this.howManyDown), (double)super.zCoord, super.worldObj, 25, false);
               } else if(critters == 2) {
                  if(super.worldObj.provider.dimensionId != -1) {
                     super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.water, 0, 3);
                  } else {
                     super.worldObj.setBlockToAir(super.xCoord, super.yCoord - this.howManyDown, super.zCoord);
                  }
               } else if(critters == 3) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.dirt, 0, 3);
               } else if(critters == 4) {
                  Blocks.tnt.onBlockDestroyedByPlayer(super.worldObj, super.xCoord, super.yCoord - this.howManyDown, super.zCoord, 1);
                  super.worldObj.setBlockToAir(super.xCoord, super.yCoord - this.howManyDown, super.zCoord);
               } else if(this.cachedBlock.isFlammable(super.worldObj, super.xCoord, super.yCoord - this.howManyDown, super.zCoord, ForgeDirection.UNKNOWN)) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.fire);
                  ThaumicHorizons.proxy.smeltFX((double)super.xCoord, (double)(super.yCoord - this.howManyDown), (double)super.zCoord, super.worldObj, 25, false);
               }
               break;
            case 2:
            default:
               if((double)super.worldObj.getBiomeGenForCoords(super.xCoord, super.zCoord).getFloatTemperature(super.xCoord, super.yCoord, super.zCoord) >= 0.15D) {
                  if(this.cachedBlock == Blocks.farmland && super.worldObj.getBlockMetadata(super.xCoord, super.yCoord - this.howManyDown, super.zCoord) != 7) {
                     super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, 7, 3);
                  } else if(super.worldObj.getBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord) == Blocks.fire) {
                     super.worldObj.setBlockToAir(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord);
                  } else if(this.cachedBlock == Blocks.cauldron) {
                     super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, 3, 3);
                  }
               } else if(this.cachedBlock.isOpaqueCube() && super.worldObj.isAirBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord)) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.snow_layer);
               }
               break;
            case 3:
               critters = ((ItemFocusLiquefaction)ThaumicHorizons.itemFocusLiquefaction).isMeltableBlock(this.cachedBlock, this.cachedMD);
               if(critters == 1) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.air);
                  ThaumicHorizons.proxy.disintegrateExplodeFX(super.worldObj, (double)super.xCoord + 0.5D, (double)(super.yCoord - this.howManyDown) + 0.5D, (double)super.zCoord + 0.5D);
               } else if(critters == 3) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.dirt, 0, 3);
               } else if(this.cachedBlock.isFlammable(super.worldObj, super.xCoord, super.yCoord - this.howManyDown, super.zCoord, ForgeDirection.UNKNOWN)) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.air);
                  ThaumicHorizons.proxy.disintegrateExplodeFX(super.worldObj, (double)super.xCoord + 0.5D, (double)(super.yCoord - this.howManyDown) + 0.5D, (double)super.zCoord + 0.5D);
               }
               break;
            case 4:
               critters = ((ItemFocusLiquefaction)ThaumicHorizons.itemFocusLiquefaction).isMeltableBlock(this.cachedBlock, this.cachedMD);
               if(critters == 1) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.fire);
                  ThaumicHorizons.proxy.smeltFX((double)super.xCoord, (double)(super.yCoord - this.howManyDown), (double)super.zCoord, super.worldObj, 25, false);
               } else if(critters == 2) {
                  if(super.worldObj.provider.dimensionId != -1) {
                     super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.water, 0, 3);
                  } else {
                     super.worldObj.setBlockToAir(super.xCoord, super.yCoord - this.howManyDown, super.zCoord);
                  }
               } else if(critters == 3) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.dirt, 0, 3);
               } else if(critters == 4) {
                  Blocks.tnt.onBlockDestroyedByPlayer(super.worldObj, super.xCoord, super.yCoord - this.howManyDown, super.zCoord, 1);
                  super.worldObj.setBlockToAir(super.xCoord, super.yCoord - this.howManyDown, super.zCoord);
               } else if(this.cachedBlock.isFlammable(super.worldObj, super.xCoord, super.yCoord - this.howManyDown, super.zCoord, ForgeDirection.UNKNOWN)) {
                  super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.fire);
                  ThaumicHorizons.proxy.smeltFX((double)super.xCoord, (double)(super.yCoord - this.howManyDown), (double)super.zCoord, super.worldObj, 25, false);
               }
            }
         }

         Iterator it;
         Entity type;
         List var10;
         label289:
         switch(this.md) {
         case 1:
            var10 = this.getCrittersBelow();
            it = var10.iterator();

            while(true) {
               if(!it.hasNext()) {
                  break label289;
               }

               type = (Entity)it.next();
               type.setFire(6);
            }
         case 2:
         default:
            if((double)super.worldObj.getBiomeGenForCoords(super.xCoord, super.zCoord).getFloatTemperature(super.xCoord, super.yCoord, super.zCoord) >= 0.15D) {
               var10 = this.getCrittersBelow();
               it = var10.iterator();

               while(it.hasNext()) {
                  type = (Entity)it.next();
                  if(type instanceof EntityEnderman || type instanceof EntityBlaze || type instanceof EntityFireBat) {
                     type.attackEntityFrom(DamageSource.drown, 1.0F);
                  }

                  if(type.isBurning()) {
                     type.extinguish();
                  }

                  if(!super.worldObj.isRemote && this.md == 8 && type instanceof EntityCow && !(type instanceof EntityMooshroom)) {
                     type.setDead();
                     EntityMooshroom asp = new EntityMooshroom(super.worldObj);
                     asp.setLocationAndAngles(type.posX, type.posY, type.posZ, type.rotationYaw, type.rotationPitch);
                     asp.setHealth(((EntityCow)type).getHealth());
                     asp.renderYawOffset = ((EntityCow)type).renderYawOffset;
                     super.worldObj.spawnEntityInWorld(asp);
                     super.worldObj.spawnParticle("largeexplode", type.posX, type.posY + (double)(type.height / 2.0F), type.posZ, 0.0D, 0.0D, 0.0D);
                  }
               }
            }
            break;
         case 3:
            var10 = this.getCrittersBelow();
            it = var10.iterator();

            while(true) {
               if(!it.hasNext()) {
                  break label289;
               }

               type = (Entity)it.next();
               type.attackEntityFrom(DamageSourceThaumcraft.dissolve, 1.0F);
            }
         case 4:
            var10 = this.getCrittersBelow();
            it = var10.iterator();

            while(it.hasNext()) {
               type = (Entity)it.next();
               type.setFire(6);
            }
         }

         if(this.dropTimer != -1) {
            --this.dropTimer;
            if(this.dropTimer == 0 && !super.worldObj.isRemote) {
               this.dropTimer = dropTimers[this.md] / 2 + super.worldObj.rand.nextInt(dropTimers[this.md] / 2);
               switch(this.md) {
               case 2:
                  super.worldObj.spawnEntityInWorld(new EntityLightningBoltFinite(super.worldObj, (double)super.xCoord + 0.5D, (double)(super.yCoord - this.howManyDown), (double)super.zCoord + 0.5D, this.howManyDown, false));
               case 3:
               default:
                  break;
               case 4:
                  int var12 = super.worldObj.rand.nextInt(75);
                  if(var12 < 6) {
                     this.entityDropItem(new ItemStack(Items.gold_nugget), 0.3F);
                  } else if(var12 < 12) {
                     if(Config.foundSilverIngot) {
                        this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 3), 0.3F);
                     } else {
                        this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 0), 0.3F);
                     }
                  } else if(var12 < 20) {
                     if(Config.foundCopperIngot) {
                        this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 1), 0.3F);
                     } else {
                        this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 0), 0.3F);
                     }
                  } else if(var12 < 30) {
                     if(Config.foundTinIngot) {
                        this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 2), 0.3F);
                     } else {
                        this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 0), 0.3F);
                     }
                  } else if(var12 < 40) {
                     if(Config.foundLeadIngot) {
                        this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 4), 0.3F);
                     } else {
                        this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 0), 0.3F);
                     }
                  } else if(var12 < 50) {
                     this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 5), 0.3F);
                  } else {
                     this.entityDropItem(new ItemStack(ConfigItems.itemNugget, 1, 0), 0.3F);
                  }
                  break;
               case 5:
                  switch(super.worldObj.rand.nextInt(10)) {
                  case 0:
                     this.entityDropItem(new ItemStack(Items.beef), 0.3F);
                     return;
                  case 1:
                     this.entityDropItem(new ItemStack(Items.porkchop), 0.3F);
                     return;
                  case 2:
                     this.entityDropItem(new ItemStack(Items.chicken), 0.3F);
                     return;
                  case 3:
                     switch(super.worldObj.rand.nextInt(3)) {
                     case 0:
                        this.entityDropItem(new ItemStack(Items.fish), 0.3F);
                        return;
                     case 1:
                        this.entityDropItem(new ItemStack(Items.fish, 1, 1), 0.3F);
                        return;
                     case 2:
                        this.entityDropItem(new ItemStack(Items.fish, 1, 2), 0.3F);
                        return;
                     default:
                        return;
                     }
                  default:
                     this.entityDropItem(new ItemStack(ThaumicHorizons.itemMeat), 0.3F);
                     return;
                  }
               case 6:
                  Aspect var11;
                  switch(super.worldObj.rand.nextInt(6)) {
                  case 1:
                     var11 = Aspect.FIRE;
                     break;
                  case 2:
                     var11 = Aspect.ORDER;
                     break;
                  case 3:
                     var11 = Aspect.ENTROPY;
                     break;
                  case 4:
                     var11 = Aspect.AIR;
                     break;
                  case 5:
                     var11 = Aspect.EARTH;
                     break;
                  default:
                     var11 = Aspect.WATER;
                  }

                  EntityAspectOrb orb = new EntityAspectOrb(super.worldObj, (double)super.xCoord + super.worldObj.rand.nextFloat(), (double)super.yCoord + 0.5D, (double)super.zCoord + super.worldObj.rand.nextFloat(), var11, 1);
                  super.worldObj.spawnEntityInWorld(orb);
                  break;
               case 7:
                  EntityXPOrb xporb = new EntityXPOrb(super.worldObj, (double)super.xCoord + super.worldObj.rand.nextFloat(), (double)super.yCoord + 0.5D, (double)super.zCoord + super.worldObj.rand.nextFloat(), super.worldObj.rand.nextInt(4));
                  super.worldObj.spawnEntityInWorld(xporb);
                  break;
               case 8:
                  this.findBlockBelow();
                  if(super.worldObj.isAirBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord)) {
                     if(this.cachedBlock == Blocks.farmland) {
                        switch(super.worldObj.rand.nextInt(8)) {
                        case 1:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.melon_stem);
                           break;
                        case 2:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.pumpkin_stem);
                           break;
                        case 3:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.carrots);
                        case 4:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.potatoes);
                        default:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.wheat);
                        }
                     } else if(this.cachedBlock == Blocks.dirt) {
                        switch(super.worldObj.rand.nextInt(10)) {
                        case 4:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.mycelium);
                           break;
                        default:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown, super.zCoord, Blocks.grass);
                        }
                     } else if(this.cachedBlock != Blocks.stone && this.cachedBlock != Blocks.mycelium) {
                        if(this.cachedBlock == Blocks.grass) {
                           int plant = super.worldObj.rand.nextInt(1000);
                           if(plant == 666) {
                              super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, ConfigBlocks.blockCustomPlant);
                              super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 1, 3);
                           } else if(plant < 750) {
                              switch(super.worldObj.rand.nextInt(14)) {
                              case 0:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.tallgrass);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 1, 3);
                                 break;
                              case 1:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.tallgrass);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 2, 3);
                                 break;
                              case 2:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.yellow_flower);
                                 break;
                              case 3:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 0, 3);
                                 break;
                              case 4:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 0, 3);
                                 break;
                              case 5:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 1, 3);
                                 break;
                              case 6:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 2, 3);
                                 break;
                              case 7:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 3, 3);
                                 break;
                              case 8:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 4, 3);
                                 break;
                              case 9:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 5, 3);
                                 break;
                              case 10:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 6, 3);
                                 break;
                              case 11:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 7, 3);
                                 break;
                              case 12:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_flower);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 8, 3);
                                 break;
                              case 13:
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.reeds);
                              }
                           } else if(plant < 950) {
                              int sapling = super.worldObj.rand.nextInt(100);
                              if(sapling < 10) {
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, ConfigBlocks.blockCustomPlant);
                                 super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 0, 3);
                              } else {
                                 super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.sapling);
                                 if(sapling < 25) {
                                    super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 0, 3);
                                 } else if(sapling < 40) {
                                    super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 1, 3);
                                 } else if(sapling < 55) {
                                    super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 2, 3);
                                 } else if(sapling < 70) {
                                    super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 3, 3);
                                 } else if(sapling < 85) {
                                    super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 4, 3);
                                 } else {
                                    super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 5, 3);
                                 }
                              }
                           } else if(plant < 975) {
                              super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, ConfigBlocks.blockCustomPlant);
                              super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 2, 3);
                           } else {
                              super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, ConfigBlocks.blockCustomPlant);
                              super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 5, 3);
                           }
                        } else if(this.cachedBlock == Blocks.sand) {
                           switch(super.worldObj.rand.nextInt(10)) {
                           case 4:
                              super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, ConfigBlocks.blockCustomPlant);
                              super.worldObj.setBlockMetadataWithNotify(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, 3, 3);
                              break;
                           default:
                              super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.cactus);
                           }
                        } else if(this.cachedBlock == Blocks.soul_sand) {
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.nether_wart);
                        }
                     } else {
                        switch(super.worldObj.rand.nextInt(3)) {
                        case 1:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.brown_mushroom);
                           break;
                        default:
                           super.worldObj.setBlock(super.xCoord, super.yCoord - this.howManyDown + 1, super.zCoord, Blocks.red_mushroom);
                        }
                     }
                  }
                  break;
               case 9:
                  super.worldObj.spawnEntityInWorld(new EntityLightningBoltFinite(super.worldObj, (double)super.xCoord + 0.5D, (double)(super.yCoord - this.howManyDown), (double)super.zCoord + 0.5D, this.howManyDown, true));
               }
            }
         }
      }

   }

   private void entityDropItem(ItemStack itemStack, float f) {
      EntityItemInvulnerable theItem = new EntityItemInvulnerable(super.worldObj, (double)super.xCoord + super.worldObj.rand.nextFloat(), (double)super.yCoord + 0.5D, (double)super.zCoord + super.worldObj.rand.nextFloat(), itemStack);
      super.worldObj.spawnEntityInWorld(theItem);
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setBoolean("raining", this.raining);
      nbttagcompound.setInteger("dropTimer", this.dropTimer);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.raining = nbttagcompound.getBoolean("raining");
      this.dropTimer = nbttagcompound.getInteger("dropTimer");
   }

   @SideOnly(Side.CLIENT)
   public double getMaxRenderDistanceSquared() {
      return 65536.0D;
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return AxisAlignedBB.getBoundingBox((double)(super.xCoord - 32), 0.0D, (double)(super.zCoord - 32), (double)(super.xCoord + 32), 256.0D, (double)(super.zCoord + 32));
   }

   public void findBlockBelow() {
      MovingObjectPosition mop = super.worldObj.rayTraceBlocks(Vec3.createVectorHelper((double)super.xCoord + 0.5D, (double)super.yCoord - 0.5D, (double)super.zCoord + 0.5D), Vec3.createVectorHelper((double)super.xCoord + 0.5D, (double)(super.yCoord - 256), (double)super.zCoord + 0.5D));
      if(mop != null) {
         this.howManyDown = super.yCoord - mop.blockY;
         this.cachedBlock = super.worldObj.getBlock(super.xCoord, mop.blockY, super.zCoord);
         this.cachedMD = super.worldObj.getBlockMetadata(super.xCoord, mop.blockY, super.zCoord);
      } else {
         this.howManyDown = 256;
         this.cachedBlock = Blocks.air;
      }

   }

   public List getCrittersBelow() {
      return super.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)(super.yCoord - this.howManyDown), (double)super.zCoord, (double)(super.xCoord + 1), (double)super.yCoord, (double)(super.zCoord + 1)));
   }

}
