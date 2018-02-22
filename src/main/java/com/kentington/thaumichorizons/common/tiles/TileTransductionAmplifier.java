package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.blocks.BlockTransductionAmplifier;
import com.kentington.thaumichorizons.common.tiles.TileVortex;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockZap;
import thaumcraft.common.tiles.TileNode;
import thaumcraft.common.tiles.TileNodeConverter;
import thaumcraft.common.tiles.TileNodeEnergized;

public class TileTransductionAmplifier extends TileThaumcraft {

   public int count = -1;
   public byte direction = -1;
   public boolean activated = false;
   public boolean shouldActivate = false;
   boolean lastActivated;
   boolean fireOnce = false;


   public void updateEntity() {
      super.updateEntity();
      if(!this.fireOnce) {
         this.direction = (byte)this.getBlockMetadata();
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

      ForgeDirection ecks;
      if(this.shouldActivate && !this.activated) {
         if(this.direction == -1) {
            this.direction = (byte)this.getBlockMetadata();
         }

         ecks = ForgeDirection.getOrientation(this.direction);
         if(ecks == ForgeDirection.UP && super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord) instanceof TileNodeEnergized) {
            this.boostNode(super.xCoord, super.yCoord - 1, super.zCoord);
         } else if(ecks == ForgeDirection.DOWN && super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof TileNodeEnergized) {
            this.boostNode(super.xCoord, super.yCoord + 1, super.zCoord);
         } else if(ecks == ForgeDirection.NORTH && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord + 1) instanceof TileNodeEnergized) {
            this.boostNode(super.xCoord, super.yCoord, super.zCoord + 1);
         } else if(ecks == ForgeDirection.SOUTH && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord - 1) instanceof TileNodeEnergized) {
            this.boostNode(super.xCoord, super.yCoord, super.zCoord - 1);
         } else if(ecks == ForgeDirection.WEST && super.worldObj.getTileEntity(super.xCoord + 1, super.yCoord, super.zCoord) instanceof TileNodeEnergized) {
            this.boostNode(super.xCoord + 1, super.yCoord, super.zCoord);
         } else if(ecks == ForgeDirection.EAST && super.worldObj.getTileEntity(super.xCoord - 1, super.yCoord, super.zCoord) instanceof TileNodeEnergized) {
            this.boostNode(super.xCoord - 1, super.yCoord, super.zCoord);
         } else {
            ((BlockTransductionAmplifier)ThaumicHorizons.blockTransducer).killMe(super.worldObj, super.xCoord, super.yCoord, super.zCoord, true);
            super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
         }

         this.activated = true;
         this.markDirty();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      } else if(!this.shouldActivate && this.activated) {
         if(this.direction == -1) {
            this.direction = (byte)this.getBlockMetadata();
         }

         ecks = ForgeDirection.getOrientation(this.direction);
         if(ecks == ForgeDirection.UP && super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord) instanceof TileNodeEnergized) {
            this.unboostNode(super.xCoord, super.yCoord - 1, super.zCoord);
         } else if(ecks == ForgeDirection.DOWN && super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof TileNodeEnergized) {
            this.unboostNode(super.xCoord, super.yCoord + 1, super.zCoord);
         } else if(ecks == ForgeDirection.NORTH && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord + 1) instanceof TileNodeEnergized) {
            this.unboostNode(super.xCoord, super.yCoord, super.zCoord + 1);
         } else if(ecks == ForgeDirection.SOUTH && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord - 1) instanceof TileNodeEnergized) {
            this.unboostNode(super.xCoord, super.yCoord, super.zCoord - 1);
         } else if(ecks == ForgeDirection.WEST && super.worldObj.getTileEntity(super.xCoord + 1, super.yCoord, super.zCoord) instanceof TileNodeEnergized) {
            this.unboostNode(super.xCoord + 1, super.yCoord, super.zCoord);
         } else if(ecks == ForgeDirection.EAST && super.worldObj.getTileEntity(super.xCoord - 1, super.yCoord, super.zCoord) instanceof TileNodeEnergized) {
            this.unboostNode(super.xCoord - 1, super.yCoord, super.zCoord);
         } else {
            ((BlockTransductionAmplifier)ThaumicHorizons.blockTransducer).killMe(super.worldObj, super.xCoord, super.yCoord, super.zCoord, true);
            super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
         }

         this.activated = false;
         this.markDirty();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      }

      if(!super.worldObj.isRemote && this.activated && this.count % 10 == 0) {
         int var9 = super.xCoord;
         int why = super.yCoord;
         int zee = super.zCoord;
         ForgeDirection dir = ForgeDirection.getOrientation(this.direction);
         if(dir == ForgeDirection.NORTH && (super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord + 1) instanceof TileNodeEnergized || super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord + 1) instanceof TileNode)) {
            ++zee;
         } else if(dir == ForgeDirection.SOUTH && (super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord - 1) instanceof TileNodeEnergized || super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord - 1) instanceof TileNode)) {
            --zee;
         } else if(dir == ForgeDirection.WEST && (super.worldObj.getTileEntity(super.xCoord + 1, super.yCoord, super.zCoord) instanceof TileNodeEnergized || super.worldObj.getTileEntity(super.xCoord + 1, super.yCoord, super.zCoord) instanceof TileNode)) {
            ++var9;
         } else if(dir == ForgeDirection.EAST && (super.worldObj.getTileEntity(super.xCoord - 1, super.yCoord, super.zCoord) instanceof TileNodeEnergized || super.worldObj.getTileEntity(super.xCoord - 1, super.yCoord, super.zCoord) instanceof TileNode)) {
            --var9;
         }

         int transducers = 0;
         if(super.worldObj.getBlock(var9 + 1, why, zee) == ThaumicHorizons.blockTransducer && ((TileTransductionAmplifier)super.worldObj.getTileEntity(var9 + 1, why, zee)).activated) {
            ++transducers;
         }

         if(super.worldObj.getBlock(var9 - 1, why, zee) == ThaumicHorizons.blockTransducer && ((TileTransductionAmplifier)super.worldObj.getTileEntity(var9 - 1, why, zee)).activated) {
            ++transducers;
         }

         if(super.worldObj.getBlock(var9, why, zee + 1) == ThaumicHorizons.blockTransducer && ((TileTransductionAmplifier)super.worldObj.getTileEntity(var9, why, zee + 1)).activated) {
            ++transducers;
         }

         if(super.worldObj.getBlock(var9, why, zee - 1) == ThaumicHorizons.blockTransducer && ((TileTransductionAmplifier)super.worldObj.getTileEntity(var9, why, zee - 1)).activated) {
            ++transducers;
         }

         if(transducers > 3 && this.count % 50 == 0) {
            this.unboostNode(var9, why, zee);
            ((TileNodeConverter)super.worldObj.getTileEntity(var9, why + 1, zee)).status = -1;
            AspectList i = ((TileNodeEnergized)super.worldObj.getTileEntity(var9, why, zee)).getAuraBase().copy();
            super.worldObj.setBlock(var9, why, zee, ThaumicHorizons.blockVortex, 0, 3);
            ((TileVortex)super.worldObj.getTileEntity(var9, why, zee)).aspects = i;
         }

         int var11;
         if(transducers > 2 && super.worldObj.rand.nextInt(4) == 2 && this.count % 50 == 0) {
            var11 = super.worldObj.rand.nextInt(16) - 8;
            int dy = super.worldObj.rand.nextInt(16) - 8;
            int target = super.worldObj.rand.nextInt(16) - 8;
            if(super.worldObj.isAirBlock(super.xCoord + var11, super.yCoord + dy, super.zCoord + target)) {
               PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float)super.xCoord + 0.5F, (float)super.yCoord + 0.5F, (float)super.zCoord + 0.5F, (float)super.xCoord + 0.5F + (float)var11, (float)super.yCoord + 0.5F + (float)dy, (float)super.zCoord + 0.5F + (float)target), new TargetPoint(super.worldObj.provider.dimensionId, (double)super.xCoord, (double)super.yCoord, (double)super.zCoord, 32.0D));
               if(dy > 0) {
                  super.worldObj.setBlock(super.xCoord + var11, super.yCoord + dy, super.zCoord + target, ConfigBlocks.blockFluxGas);
               } else {
                  super.worldObj.setBlock(super.xCoord + var11, super.yCoord + dy, super.zCoord + target, ConfigBlocks.blockFluxGoo);
               }
            }
         }

         if(transducers > 1 && this.count % 50 == 0) {
            List var10 = super.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand(10.0D, 10.0D, 10.0D));
            if(var10 != null && var10.size() > 0) {
               Iterator var12 = var10.iterator();

               while(var12.hasNext()) {
                  Entity var13 = (Entity)var12.next();
                  PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float)super.xCoord + 0.5F, (float)super.yCoord + 0.5F, (float)super.zCoord + 0.5F, (float)var13.posX, (float)var13.posY + var13.height / 2.0F, (float)var13.posZ), new TargetPoint(super.worldObj.provider.dimensionId, (double)super.xCoord, (double)super.yCoord, (double)super.zCoord, 32.0D));
                  var13.attackEntityFrom(DamageSource.magic, (float)(4 + super.worldObj.rand.nextInt(1)));
               }
            }
         }

         if(super.worldObj.getTileEntity(var9, why, zee) instanceof TileNode) {
            for(var11 = 0; var11 < transducers; ++var11) {
               this.unboostNode(var9, why, zee);
            }

            this.activated = false;
            this.markDirty();
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         }
      }

   }

   void boostNode(int x, int y, int z) {
      TileEntity tyle = super.worldObj.getTileEntity(x, y, z);
      if(tyle instanceof TileNodeEnergized) {
         TileNodeEnergized node = (TileNodeEnergized)tyle;
         AspectList baseVis = node.getAuraBase();
         Aspect[] var7 = baseVis.getAspects();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Aspect asp = var7[var9];
            baseVis.add(asp, 10);
         }

         node.setAspects(baseVis);
         node.setupNode();
      }

   }

   public void unBoostNode(int x, int y, int z) {
      ForgeDirection dir = ForgeDirection.getOrientation(this.direction);
      if(dir == ForgeDirection.UP && super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord) instanceof TileNodeEnergized) {
         this.unboostNode(super.xCoord, super.yCoord - 1, super.zCoord);
      } else if(dir == ForgeDirection.DOWN && super.worldObj.getTileEntity(super.xCoord, super.yCoord + 1, super.zCoord) instanceof TileNodeEnergized) {
         this.unboostNode(super.xCoord, super.yCoord + 1, super.zCoord);
      } else if(dir == ForgeDirection.NORTH && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord + 1) instanceof TileNodeEnergized) {
         this.unboostNode(super.xCoord, super.yCoord, super.zCoord + 1);
      } else if(dir == ForgeDirection.SOUTH && super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord - 1) instanceof TileNodeEnergized) {
         this.unboostNode(super.xCoord, super.yCoord, super.zCoord - 1);
      } else if(dir == ForgeDirection.WEST && super.worldObj.getTileEntity(super.xCoord + 1, super.yCoord, super.zCoord) instanceof TileNodeEnergized) {
         this.unboostNode(super.xCoord + 1, super.yCoord, super.zCoord);
      } else if(dir == ForgeDirection.EAST && super.worldObj.getTileEntity(super.xCoord - 1, super.yCoord, super.zCoord) instanceof TileNodeEnergized) {
         this.unboostNode(super.xCoord - 1, super.yCoord, super.zCoord);
      }

   }

   void unboostNode(int x, int y, int z) {
      TileEntity tyle = super.worldObj.getTileEntity(x, y, z);
      AspectList baseVis;
      Aspect[] var7;
      int var8;
      int var9;
      Aspect asp;
      if(tyle instanceof TileNodeEnergized) {
         TileNodeEnergized node = (TileNodeEnergized)tyle;
         baseVis = node.getAuraBase();
         var7 = baseVis.getAspects();
         var8 = var7.length;

         for(var9 = 0; var9 < var8; ++var9) {
            asp = var7[var9];
            baseVis.remove(asp, 10);
         }

         node.setAspects(baseVis);
         node.setupNode();
      } else if(tyle instanceof TileNode) {
         TileNode var11 = (TileNode)tyle;
         baseVis = var11.getAspectsBase();
         var7 = baseVis.getAspects();
         var8 = var7.length;

         for(var9 = 0; var9 < var8; ++var9) {
            asp = var7[var9];
            baseVis.remove(asp, 10);
         }

         var11.setAspects(baseVis);
      }

   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setBoolean("active", this.activated);
      nbttagcompound.setByte("dir", this.direction);
      nbttagcompound.setBoolean("shouldactivate", this.shouldActivate);
      nbttagcompound.setInteger("count", this.count);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.activated = nbttagcompound.getBoolean("active");
      this.direction = nbttagcompound.getByte("dir");
      this.shouldActivate = nbttagcompound.getBoolean("shouldactivate");
      this.count = nbttagcompound.getInteger("count");
   }
}
