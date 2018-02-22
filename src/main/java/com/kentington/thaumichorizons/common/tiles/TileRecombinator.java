package com.kentington.thaumichorizons.common.tiles;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockZap;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.tiles.TileNode;

public class TileRecombinator extends TileThaumcraft {

   public int count = -1;
   public boolean activated = false;
   public boolean shouldActivate = false;
   boolean fireOnce = false;


   public void updateEntity() {
      super.updateEntity();
      if(!this.fireOnce) {
         super.worldObj.getBlock(super.xCoord, super.yCoord, super.zCoord).onNeighborBlockChange(super.worldObj, super.xCoord, super.yCoord, super.zCoord, (Block)null);
         this.fireOnce = true;
      }

      if(this.activated) {
         ++this.count;
      } else if(!this.activated && this.count > 0) {
         if(this.count > 50) {
            this.count = 50;
         }

         --this.count;
      }

      if(this.shouldActivate && !this.activated) {
         this.activated = true;
         this.markDirty();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      } else if(!this.shouldActivate && this.activated) {
         this.activated = false;
         this.markDirty();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      }

      if(!super.worldObj.isRemote && this.activated && this.count > 50 && super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord) instanceof TileNode) {
         TileNode tile = (TileNode)super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord);
         int x = super.worldObj.rand.nextInt(5) - super.worldObj.rand.nextInt(5);
         int y = super.worldObj.rand.nextInt(5) - super.worldObj.rand.nextInt(5) - 1;
         int z = super.worldObj.rand.nextInt(5) - super.worldObj.rand.nextInt(5);
         if(x != 0 || y != -1 || z != 0) {
            TileEntity te = super.worldObj.getTileEntity(super.xCoord + x, super.yCoord + y, super.zCoord + z);
            if(te != null && te instanceof TileNode && super.worldObj.getBlock(super.xCoord + x, super.yCoord + y, super.zCoord + z) == ConfigBlocks.blockAiry) {
               if(te instanceof TileNode && ((TileNode)te).getLock() > 0) {
                  return;
               }

               TileNode nd = (TileNode)te;
               if(nd.getAspects().size() == 0) {
                  return;
               }

               this.processCombos(nd, tile, x, y, z);
            }
         }
      }

   }

   void processCombos(TileNode nd, TileNode tile, int x, int y, int z) {
      AspectList possibleCombos = new AspectList();
      Aspect[] var7 = tile.getAspectsBase().getAspects();
      int var8 = var7.length;

      int var9;
      Aspect asp;
      Aspect[] var11;
      int var12;
      int var13;
      Aspect asp2;
      for(var9 = 0; var9 < var8; ++var9) {
         asp = var7[var9];
         if(asp.isPrimal()) {
            var11 = nd.getAspectsBase().getAspects();
            var12 = var11.length;

            for(var13 = 0; var13 < var12; ++var13) {
               asp2 = var11[var13];
               if(asp2.isPrimal() && ResearchManager.getCombinationResult(asp, asp2) != null) {
                  possibleCombos.add(ResearchManager.getCombinationResult(asp, asp2), 1);
               }
            }
         }
      }

      if(possibleCombos.size() > 0 && possibleCombos.getAspects()[0] != null) {
         this.doMerge(possibleCombos, nd, tile, x, y, z);
      } else {
         var7 = tile.getAspectsBase().getAspects();
         var8 = var7.length;

         for(var9 = 0; var9 < var8; ++var9) {
            asp = var7[var9];
            if(asp.isPrimal()) {
               var11 = nd.getAspectsBase().getAspects();
               var12 = var11.length;

               for(var13 = 0; var13 < var12; ++var13) {
                  asp2 = var11[var13];
                  if(ResearchManager.getCombinationResult(asp, asp2) != null) {
                     possibleCombos.add(ResearchManager.getCombinationResult(asp, asp2), 1);
                  }
               }
            }
         }

         if(possibleCombos.size() > 0 && possibleCombos.getAspects()[0] != null) {
            this.doMerge(possibleCombos, nd, tile, x, y, z);
         } else {
            var7 = tile.getAspectsBase().getAspects();
            var8 = var7.length;

            for(var9 = 0; var9 < var8; ++var9) {
               asp = var7[var9];
               var11 = nd.getAspectsBase().getAspects();
               var12 = var11.length;

               for(var13 = 0; var13 < var12; ++var13) {
                  asp2 = var11[var13];
                  if(asp2.isPrimal() && ResearchManager.getCombinationResult(asp, asp2) != null) {
                     possibleCombos.add(ResearchManager.getCombinationResult(asp, asp2), 1);
                  }
               }
            }

            if(possibleCombos.size() > 0 && possibleCombos.getAspects()[0] != null) {
               this.doMerge(possibleCombos, nd, tile, x, y, z);
            } else {
               var7 = tile.getAspectsBase().getAspects();
               var8 = var7.length;

               for(var9 = 0; var9 < var8; ++var9) {
                  asp = var7[var9];
                  var11 = nd.getAspectsBase().getAspects();
                  var12 = var11.length;

                  for(var13 = 0; var13 < var12; ++var13) {
                     asp2 = var11[var13];
                     if(ResearchManager.getCombinationResult(asp, asp2) != null) {
                        possibleCombos.add(ResearchManager.getCombinationResult(asp, asp2), 1);
                     }
                  }
               }

               if(possibleCombos.size() > 0 && possibleCombos.getAspects()[0] != null) {
                  this.doMerge(possibleCombos, nd, tile, x, y, z);
               }
            }
         }
      }
   }

   public void doMerge(AspectList possibleCombos, TileNode nd, TileNode tile, int x, int y, int z) {
      int which = super.worldObj.rand.nextInt(possibleCombos.getAspects().length);
      Aspect toAdd = possibleCombos.getAspects()[which];
      tile.getAspectsBase().add(toAdd, 1);
      tile.getAspects().add(toAdd, 1);
      Aspect aspA;
      Aspect aspB;
      if(tile.getAspectsBase().getAmount(toAdd.getComponents()[0]) > 0) {
         aspA = toAdd.getComponents()[0];
         aspB = toAdd.getComponents()[1];
      } else {
         aspA = toAdd.getComponents()[1];
         aspB = toAdd.getComponents()[0];
      }

      tile.getAspectsBase().remove(aspA, 1);
      tile.getAspects().remove(aspA, 1);
      nd.getAspects().remove(aspB, 1);
      if(super.worldObj.rand.nextInt(3) == 0) {
         nd.setNodeVisBase(aspB, (short)(nd.getNodeVisBase(aspB) - 1));
      }

      super.worldObj.markBlockForUpdate(super.xCoord + x, super.yCoord + y, super.zCoord + z);
      nd.markDirty();
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord - 1, super.zCoord);
      tile.markDirty();
      PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float)(super.xCoord + x) + 0.5F, (float)(super.yCoord + y) + 0.5F, (float)(super.zCoord + z) + 0.5F, (float)super.xCoord + 0.5F, (float)super.yCoord + 0.5F, (float)super.zCoord + 0.5F), new TargetPoint(super.worldObj.provider.dimensionId, (double)super.xCoord, (double)super.yCoord, (double)super.zCoord, 32.0D));
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setBoolean("active", this.activated);
      nbttagcompound.setBoolean("shouldactivate", this.shouldActivate);
      nbttagcompound.setInteger("count", this.count);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.activated = nbttagcompound.getBoolean("active");
      this.shouldActivate = nbttagcompound.getBoolean("shouldactivate");
      this.count = nbttagcompound.getInteger("count");
   }
}
