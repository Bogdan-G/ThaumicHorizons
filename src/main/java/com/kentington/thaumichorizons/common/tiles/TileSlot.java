package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;

public class TileSlot extends TileThaumcraft {

   public boolean hasKeystone = false;
   public boolean portalOpen = false;
   public int pocketID = 0;
   public int which = 0;
   public boolean xAligned = false;


   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("pocketPlaneID", this.pocketID);
      nbttagcompound.setInteger("which", this.which);
      nbttagcompound.setBoolean("hasKeystone", this.hasKeystone);
      nbttagcompound.setBoolean("portalOpen", this.portalOpen);
      nbttagcompound.setBoolean("aligned", this.xAligned);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.pocketID = nbttagcompound.getInteger("pocketPlaneID");
      this.which = nbttagcompound.getInteger("which");
      this.hasKeystone = nbttagcompound.getBoolean("hasKeystone");
      this.portalOpen = nbttagcompound.getBoolean("portalOpen");
      this.xAligned = nbttagcompound.getBoolean("aligned");
   }

   public void destroyPortal() {
      if(!super.worldObj.isRemote) {
         int z;
         int y;
         if(this.xAligned) {
            super.worldObj.setBlock(super.xCoord - 2, super.yCoord, super.zCoord, Blocks.lapis_block);
            super.worldObj.setBlock(super.xCoord + 2, super.yCoord, super.zCoord, Blocks.lapis_block);
            super.worldObj.setBlock(super.xCoord - 2, super.yCoord - 4, super.zCoord, Blocks.lapis_block);
            super.worldObj.setBlock(super.xCoord + 2, super.yCoord - 4, super.zCoord, Blocks.lapis_block);
            super.worldObj.setBlock(super.xCoord - 1, super.yCoord, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord + 1, super.yCoord, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord - 1, super.yCoord - 4, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 4, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord + 1, super.yCoord - 4, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord - 2, super.yCoord - 1, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord - 2, super.yCoord - 2, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord - 2, super.yCoord - 3, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord + 2, super.yCoord - 1, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord + 2, super.yCoord - 2, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord + 2, super.yCoord - 3, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);

            for(z = -1; z <= 1; ++z) {
               for(y = -1; y >= -3; --y) {
                  super.worldObj.setBlockToAir(super.xCoord + z, super.yCoord + y, super.zCoord);
               }
            }

            for(z = -2; z <= 2; ++z) {
               for(y = 0; y >= -4; --y) {
                  super.worldObj.markBlockForUpdate(super.xCoord + z, super.yCoord + y, super.zCoord);
               }
            }
         } else {
            super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord - 2, Blocks.lapis_block);
            super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord + 2, Blocks.lapis_block);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 4, super.zCoord - 2, Blocks.lapis_block);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 4, super.zCoord + 2, Blocks.lapis_block);
            super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord - 1, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord + 1, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 4, super.zCoord - 1, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 4, super.zCoord, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 4, super.zCoord + 1, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 1, super.zCoord - 2, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 2, super.zCoord - 2, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 3, super.zCoord - 2, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 1, super.zCoord + 2, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 2, super.zCoord + 2, ConfigBlocks.blockCosmeticSolid, 6, 3);
            super.worldObj.setBlock(super.xCoord, super.yCoord - 3, super.zCoord + 2, ConfigBlocks.blockCosmeticSolid, 6, 3);

            for(z = -1; z <= 1; ++z) {
               for(y = -1; y >= -3; --y) {
                  super.worldObj.setBlockToAir(super.xCoord, super.yCoord + y, super.zCoord + z);
               }
            }

            for(z = -2; z <= 2; ++z) {
               for(y = 0; y >= -4; --y) {
                  super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord + y, super.zCoord + z);
               }
            }
         }

         PocketPlaneData.destroyPortal(this.pocketID, this.which);
         super.worldObj.playSoundEffect((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "thaumcraft:craftfail", 1.0F, 1.0F);
         this.portalOpen = false;
         this.markDirty();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      }
   }

   public void makePortal(EntityPlayer player) {
      if(!super.worldObj.isRemote) {
         int portalNum = PocketPlaneData.firstAvailablePortal(this.pocketID);
         ItemWandCasting wand = (ItemWandCasting)player.getHeldItem().getItem();
         if(portalNum != 0 && wand.consumeAllVisCrafting(player.getHeldItem(), player, (new AspectList()).add(Aspect.WATER, 100).add(Aspect.EARTH, 100).add(Aspect.ORDER, 100).add(Aspect.FIRE, 100).add(Aspect.AIR, 100).add(Aspect.ENTROPY, 100), false)) {
            boolean madePortal = false;
            int md;
            int z;
            int y;
            if(this.checkPortalX()) {
               this.xAligned = true;
               md = 0;

               for(z = -2; z <= 2; ++z) {
                  for(y = 0; y >= -4; --y) {
                     if(!super.worldObj.isAirBlock(super.xCoord + z, super.yCoord + y, super.zCoord) && (z != 0 || y != 0)) {
                        super.worldObj.setBlock(super.xCoord + z, super.yCoord + y, super.zCoord, ThaumicHorizons.blockGateway, md, 3);
                        ++md;
                     } else if(z != 0 || y != 0) {
                        super.worldObj.setBlock(super.xCoord + z, super.yCoord + y, super.zCoord, ThaumicHorizons.blockPortal, 0, 3);
                     }
                  }
               }

               madePortal = true;
            } else if(this.checkPortalZ()) {
               this.xAligned = false;
               md = 0;

               for(z = -2; z <= 2; ++z) {
                  for(y = 0; y >= -4; --y) {
                     if(!super.worldObj.isAirBlock(super.xCoord, super.yCoord + y, super.zCoord + z) && (z != 0 || y != 0)) {
                        super.worldObj.setBlock(super.xCoord, super.yCoord + y, super.zCoord + z, ThaumicHorizons.blockGateway, md, 3);
                        ++md;
                     } else if(z != 0 || y != 0) {
                        super.worldObj.setBlock(super.xCoord, super.yCoord + y, super.zCoord + z, ThaumicHorizons.blockPortal, 0, 3);
                     }
                  }
               }

               madePortal = true;
            }

            if(madePortal) {
               switch(portalNum) {
               case 1:
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalA[0] = super.xCoord;
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalA[1] = super.yCoord;
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalA[2] = super.zCoord;
                  break;
               case 2:
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalB[0] = super.xCoord;
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalB[1] = super.yCoord;
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalB[2] = super.zCoord;
                  break;
               case 3:
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalC[0] = super.xCoord;
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalC[1] = super.yCoord;
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalC[2] = super.zCoord;
                  break;
               case 4:
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalD[0] = super.xCoord;
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalD[1] = super.yCoord;
                  ((PocketPlaneData)PocketPlaneData.planes.get(this.pocketID)).portalD[2] = super.zCoord;
               }

               PocketPlaneData.makePortal(this.pocketID, portalNum, super.xCoord, super.yCoord, super.zCoord);
               this.which = portalNum;
               this.portalOpen = true;
               wand.consumeAllVisCrafting(player.getHeldItem(), player, (new AspectList()).add(Aspect.WATER, 100).add(Aspect.EARTH, 100).add(Aspect.ORDER, 100).add(Aspect.FIRE, 100).add(Aspect.AIR, 100).add(Aspect.ENTROPY, 100), true);
               super.worldObj.playSoundEffect((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
               this.markDirty();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               return;
            }
         }

      }
   }

   boolean checkPortalX() {
      for(int x = -1; x <= 1; ++x) {
         for(int y = -1; y >= -3; --y) {
            if(!super.worldObj.isAirBlock(super.xCoord + x, super.yCoord + y, super.zCoord)) {
               return false;
            }
         }
      }

      if(super.worldObj.getBlock(super.xCoord - 2, super.yCoord, super.zCoord) == Blocks.lapis_block && super.worldObj.getBlock(super.xCoord + 2, super.yCoord, super.zCoord) == Blocks.lapis_block && super.worldObj.getBlock(super.xCoord - 2, super.yCoord - 4, super.zCoord) == Blocks.lapis_block && super.worldObj.getBlock(super.xCoord + 2, super.yCoord - 4, super.zCoord) == Blocks.lapis_block) {
         if(this.isArcStone(super.xCoord - 1, super.yCoord, super.zCoord) && this.isArcStone(super.xCoord + 1, super.yCoord, super.zCoord) && this.isArcStone(super.xCoord - 2, super.yCoord - 1, super.zCoord) && this.isArcStone(super.xCoord - 2, super.yCoord - 2, super.zCoord) && this.isArcStone(super.xCoord - 2, super.yCoord - 3, super.zCoord) && this.isArcStone(super.xCoord + 2, super.yCoord - 1, super.zCoord) && this.isArcStone(super.xCoord + 2, super.yCoord - 2, super.zCoord) && this.isArcStone(super.xCoord + 2, super.yCoord - 3, super.zCoord) && this.isArcStone(super.xCoord - 1, super.yCoord - 4, super.zCoord) && this.isArcStone(super.xCoord, super.yCoord - 4, super.zCoord) && this.isArcStone(super.xCoord + 1, super.yCoord - 4, super.zCoord)) {
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   boolean checkPortalZ() {
      for(int z = -1; z <= 1; ++z) {
         for(int y = -1; y >= -3; --y) {
            if(!super.worldObj.isAirBlock(super.xCoord, super.yCoord + y, super.zCoord + z)) {
               return false;
            }
         }
      }

      if(super.worldObj.getBlock(super.xCoord, super.yCoord, super.zCoord - 2) == Blocks.lapis_block && super.worldObj.getBlock(super.xCoord, super.yCoord, super.zCoord + 2) == Blocks.lapis_block && super.worldObj.getBlock(super.xCoord, super.yCoord - 4, super.zCoord - 2) == Blocks.lapis_block && super.worldObj.getBlock(super.xCoord, super.yCoord - 4, super.zCoord + 2) == Blocks.lapis_block) {
         if(this.isArcStone(super.xCoord, super.yCoord, super.zCoord - 1) && this.isArcStone(super.xCoord, super.yCoord, super.zCoord + 1) && this.isArcStone(super.xCoord, super.yCoord - 1, super.zCoord - 2) && this.isArcStone(super.xCoord, super.yCoord - 2, super.zCoord - 2) && this.isArcStone(super.xCoord, super.yCoord - 3, super.zCoord - 2) && this.isArcStone(super.xCoord, super.yCoord - 1, super.zCoord + 2) && this.isArcStone(super.xCoord, super.yCoord - 2, super.zCoord + 2) && this.isArcStone(super.xCoord, super.yCoord - 3, super.zCoord + 2) && this.isArcStone(super.xCoord, super.yCoord - 4, super.zCoord - 1) && this.isArcStone(super.xCoord, super.yCoord - 4, super.zCoord) && this.isArcStone(super.xCoord, super.yCoord - 4, super.zCoord + 1)) {
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   boolean isArcStone(int x, int y, int z) {
      return super.worldObj.getBlock(x, y, z) == ConfigBlocks.blockCosmeticSolid && super.worldObj.getBlockMetadata(x, y, z) == 6;
   }

   public void insertKeystone(int pocketNum) {
      this.hasKeystone = true;
      this.pocketID = pocketNum;
      this.markDirty();
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
   }

   public int removeKeystone() {
      this.hasKeystone = false;
      this.markDirty();
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      return this.pocketID;
   }
}
