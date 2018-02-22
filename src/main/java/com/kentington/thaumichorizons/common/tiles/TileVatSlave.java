package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

public class TileVatSlave extends TileThaumcraft implements IAspectContainer {

   boolean bossFound;
   int bossX;
   int bossY;
   int bossZ;
   int renderMe;


   public boolean activate(EntityPlayer player) {
      TileVat boss = this.getBoss(-1);
      return boss != null?(super.blockMetadata != 0?boss.activate(player, false):boss.activate(player, true)):false;
   }

   public TileVat getBoss(int mdOverride) {
      if(!this.bossFound) {
         int md = mdOverride;
         if(mdOverride == -1) {
            md = super.worldObj.getBlockMetadata(super.xCoord, super.yCoord, super.zCoord);
         }

         if(md == 0) {
            this.bossX = super.xCoord;
            this.bossZ = super.zCoord;
            if(super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof TileVat) {
               this.bossY = super.yCoord + 1;
               this.bossFound = true;
            } else if(super.worldObj.getTileEntity(super.xCoord, super.yCoord + 2, super.zCoord) instanceof TileVat) {
               this.bossY = super.yCoord + 2;
               this.bossFound = true;
            }
         } else if(md == 10) {
            for(int boss = -1; boss < 2; ++boss) {
               for(int z = -1; z < 2; ++z) {
                  if(super.worldObj.getBlock(super.xCoord + boss, super.yCoord, super.zCoord + z) == ThaumicHorizons.blockVatInterior && super.worldObj.getBlockMetadata(super.xCoord + boss, super.yCoord, super.zCoord + z) == 0 && super.worldObj.getTileEntity(super.xCoord + boss, super.yCoord, super.zCoord + z) instanceof TileVatSlave) {
                     TileVat boss1 = ((TileVatSlave)super.worldObj.getTileEntity(super.xCoord + boss, super.yCoord, super.zCoord + z)).getBoss(-1);
                     if(boss1 != null) {
                        this.bossX = boss1.xCoord;
                        this.bossY = boss1.yCoord;
                        this.bossZ = boss1.zCoord;
                        this.bossFound = true;
                     }

                     return boss1;
                  }
               }
            }
         } else {
            TileVat var6;
            if(md == 4) {
               if(super.worldObj.getBlock(super.xCoord, super.yCoord + 1, super.zCoord) == ThaumicHorizons.blockVat && super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof TileVatSlave) {
                  var6 = ((TileVatSlave)super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord)).getBoss(-1);
                  if(var6 != null) {
                     this.bossX = var6.xCoord;
                     this.bossY = var6.yCoord;
                     this.bossZ = var6.zCoord;
                     this.bossFound = true;
                  }

                  return var6;
               }
            } else if(md == 5) {
               if(super.worldObj.getBlock(super.xCoord, super.yCoord + 1, super.zCoord) == ThaumicHorizons.blockVat && super.worldObj.getBlockMetadata(super.xCoord, super.yCoord + 1, super.zCoord) == 10 && super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof TileVatSlave) {
                  var6 = ((TileVatSlave)super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord)).getBoss(-1);
                  if(var6 != null) {
                     this.bossX = var6.xCoord;
                     this.bossY = var6.yCoord;
                     this.bossZ = var6.zCoord;
                     this.bossFound = true;
                  }

                  return var6;
               }

               if(super.worldObj.getBlock(super.xCoord, super.yCoord - 1, super.zCoord) == ThaumicHorizons.blockVat && super.worldObj.getBlockMetadata(super.xCoord, super.yCoord - 1, super.zCoord) == 10 && super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord) instanceof TileVatSlave) {
                  var6 = ((TileVatSlave)super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord)).getBoss(-1);
                  if(var6 != null) {
                     this.bossX = var6.xCoord;
                     this.bossY = var6.yCoord;
                     this.bossZ = var6.zCoord;
                     this.bossFound = true;
                  }

                  return var6;
               }
            } else if(md == 6) {
               this.bossX = super.xCoord;
               this.bossZ = super.zCoord;
               if(super.worldObj.getTileEntity(super.xCoord, super.yCoord + 3, super.zCoord) instanceof TileVat) {
                  this.bossY = super.yCoord + 3;
                  this.bossFound = true;
               }
            }
         }
      }

      return super.worldObj.getTileEntity(this.bossX, this.bossY, this.bossZ) instanceof TileVat?(TileVat)super.worldObj.getTileEntity(this.bossX, this.bossY, this.bossZ):null;
   }

   public void killMyBoss(int mdOverride) {
      TileVat boss = this.getBoss(mdOverride);
      if(boss != null) {
         boss.killMe();
      }

   }

   public AspectList getAspects() {
      TileVat boss = this.getBoss(-1);
      return boss != null?boss.getAspects():null;
   }

   public void setAspects(AspectList aspects) {}

   public boolean doesContainerAccept(Aspect tag) {
      return false;
   }

   public int addToContainer(Aspect tag, int amount) {
      return 0;
   }

   public boolean takeFromContainer(Aspect tag, int amount) {
      return false;
   }

   public boolean takeFromContainer(AspectList ot) {
      return false;
   }

   public boolean doesContainerContainAmount(Aspect tag, int amount) {
      return false;
   }

   public boolean doesContainerContain(AspectList ot) {
      return false;
   }

   public int containerContains(Aspect tag) {
      return 0;
   }
}
