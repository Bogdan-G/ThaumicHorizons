package com.kentington.thaumichorizons.common.entities.ai;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityTaintPig;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.utils.Utils;

public class EntityAIEatTaint extends EntityAIBase {

   private EntityTaintPig thePig;
   private Vec3 targetCoordinates;
   int cooldown;
   int count = 0;


   public EntityAIEatTaint(EntityTaintPig par1EntityCreature) {
      this.thePig = par1EntityCreature;
   }

   public boolean shouldExecute() {
      if(this.cooldown > 0) {
         --this.cooldown;
         return false;
      } else {
         return this.findTaint();
      }
   }

   private boolean findTaint() {
      int tries;
      int x;
      int z;
      for(tries = -2; tries < 3; ++tries) {
         for(x = -2; x < 3; ++x) {
            for(z = -2; z < 3; ++z) {
               if(this.thePig.worldObj.getBlock((int)this.thePig.posX + tries, (int)this.thePig.posY + x, (int)this.thePig.posZ + z).getMaterial() == Config.taintMaterial || this.thePig.worldObj.getBlock((int)this.thePig.posX + tries, (int)this.thePig.posY + x, (int)this.thePig.posZ + z) == Blocks.grass && this.thePig.worldObj.getBiomeGenForCoords((int)this.thePig.posX + tries, (int)this.thePig.posZ + z).biomeID == Config.biomeTaintID) {
                  this.targetCoordinates = Vec3.createVectorHelper((double)((int)this.thePig.posX + tries), (double)((int)this.thePig.posY + x), (double)((int)this.thePig.posZ + z));
                  return true;
               }
            }
         }
      }

      for(tries = 0; tries < 30; ++tries) {
         x = (int)this.thePig.posX + this.thePig.worldObj.rand.nextInt(17) - 8;
         z = (int)this.thePig.posZ + this.thePig.worldObj.rand.nextInt(17) - 8;
         int y = (int)this.thePig.posY + this.thePig.worldObj.rand.nextInt(5) - 2;
         if(this.thePig.worldObj.isAirBlock(x, y + 1, z) && this.thePig.worldObj.getBlock(x, y, z).getMaterial() == Config.taintMaterial || this.thePig.worldObj.getBlock(x, y, z) == Blocks.grass && this.thePig.worldObj.getBiomeGenForCoords(x, z).biomeID == Config.biomeTaintID) {
            this.targetCoordinates = Vec3.createVectorHelper((double)x, (double)y, (double)z);
            return true;
         }
      }

      return false;
   }

   public boolean continueExecuting() {
      return this.count-- > 0 && !this.thePig.getNavigator().noPath() && this.cooldown-- <= 0;
   }

   public void resetTask() {
      this.count = 0;
      this.targetCoordinates = null;
      this.thePig.getNavigator().clearPathEntity();
   }

   public void updateTask() {
      if(this.targetCoordinates != null) {
         this.thePig.getLookHelper().setLookPosition(this.targetCoordinates.xCoord + 0.5D, this.targetCoordinates.yCoord + 0.5D, this.targetCoordinates.zCoord + 0.5D, 30.0F, 30.0F);
         double dist = this.thePig.getDistanceSq(this.targetCoordinates.xCoord + 0.5D, this.targetCoordinates.yCoord + 0.5D, this.targetCoordinates.zCoord + 0.5D);
         if(dist <= 4.0D) {
            this.eatTaint();
         }

      }
   }

   private void eatTaint() {
      if(this.thePig.worldObj.getBlock((int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.yCoord, (int)this.targetCoordinates.zCoord).getMaterial() == Config.taintMaterial) {
         ThaumicHorizons.proxy.blockSplosionFX((int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.yCoord, (int)this.targetCoordinates.zCoord, this.thePig.worldObj.getBlock((int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.yCoord, (int)this.targetCoordinates.zCoord), this.thePig.worldObj.getBlockMetadata((int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.yCoord, (int)this.targetCoordinates.zCoord));
         this.thePig.worldObj.setBlockToAir((int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.yCoord, (int)this.targetCoordinates.zCoord);
         Utils.setBiomeAt(this.thePig.worldObj, (int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.zCoord, BiomeGenBase.plains);
         this.thePig.worldObj.playSoundAtEntity(this.thePig, "random.burp", 0.2F, ((this.thePig.worldObj.rand.nextFloat() - this.thePig.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         this.thePig.heal(1.0F);
         this.cooldown = 20;
      } else if(this.thePig.worldObj.getBlock((int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.yCoord, (int)this.targetCoordinates.zCoord) == Blocks.grass && this.thePig.worldObj.getBiomeGenForCoords((int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.zCoord).biomeID == Config.biomeTaintID) {
         this.thePig.worldObj.setBlock((int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.yCoord, (int)this.targetCoordinates.zCoord, Blocks.dirt);
         Utils.setBiomeAt(this.thePig.worldObj, (int)this.targetCoordinates.xCoord, (int)this.targetCoordinates.zCoord, BiomeGenBase.plains);
         this.thePig.worldObj.playSoundAtEntity(this.thePig, "random.burp", 0.2F, ((this.thePig.worldObj.rand.nextFloat() - this.thePig.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         this.thePig.heal(1.0F);
         this.cooldown = 10;
      } else {
         this.resetTask();
      }

   }

   public void startExecuting() {
      this.count = 500;
      if(this.targetCoordinates != null) {
         this.thePig.getNavigator().tryMoveToXYZ(this.targetCoordinates.xCoord + 0.5D, this.targetCoordinates.yCoord + 0.5D, this.targetCoordinates.zCoord + 0.5D, 1.0D);
      }

   }
}
