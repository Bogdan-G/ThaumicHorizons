package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityEndersteed;
import com.kentington.thaumichorizons.common.lib.NightmareTeleporter;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import com.kentington.thaumichorizons.common.lib.PacketMountNightmare;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityNightmare extends EntityEndersteed {

   NightmareTeleporter nightmareTeleporterOverworld;
   NightmareTeleporter nightmareTeleporterNether;


   public EntityNightmare(World p_i1685_1_) {
      super(p_i1685_1_);
      super.isImmuneToFire = true;
      if(!p_i1685_1_.isRemote) {
         this.nightmareTeleporterOverworld = new NightmareTeleporter(MinecraftServer.getServer().worldServerForDimension(0));
         this.nightmareTeleporterNether = new NightmareTeleporter(MinecraftServer.getServer().worldServerForDimension(-1));
      }

   }

   public void setJumpPower(int p_110206_1_) {
      double blocks = (double)p_110206_1_ / 7.0D;
      if(p_110206_1_ >= 90 && (super.worldObj.provider.dimensionId == 0 || super.worldObj.provider.dimensionId == -1)) {
         if(super.dimension == 0 || super.dimension == -1) {
            if(super.dimension == 0) {
               this.netherport(-1);
            } else {
               this.netherport(0);
            }
         }
      } else {
         this.teleportTo(super.posX - blocks * Math.sin(Math.toRadians((double)super.rotationYaw)), super.posY, super.posZ + blocks * Math.cos(Math.toRadians((double)super.rotationYaw)));
      }

   }

   private void netherport(int dim) {
      super.worldObj.newExplosion(this, super.posX, super.posY + (double)(super.height / 2.0F), super.posZ, 2.0F, true, true);
      EntityPlayerMP player = (EntityPlayerMP)super.riddenByEntity;
      player.mountEntity((Entity)null);
      Entity newNightmare;
      if(super.dimension == 0) {
         player = this.playerTravelToDimension(player, -1);
         newNightmare = this.nightmareTravelToDimension(-1);
      } else {
         player = this.playerTravelToDimension(player, 0);
         newNightmare = this.nightmareTravelToDimension(0);
      }

      player.rotationYaw = newNightmare.rotationYaw;
      player.rotationPitch = newNightmare.rotationPitch;
      player.mountEntity((Entity)null);
      player.mountEntity(newNightmare);
      PacketHandler.INSTANCE.sendTo(new PacketMountNightmare(newNightmare, player), player);
   }

   public Entity nightmareTravelToDimension(int p_71027_1_) {
      if(!super.worldObj.isRemote && !super.isDead) {
         super.worldObj.theProfiler.startSection("changeDimension");
         MinecraftServer minecraftserver = MinecraftServer.getServer();
         int j = super.dimension;
         WorldServer worldserver = minecraftserver.worldServerForDimension(j);
         WorldServer worldserver1 = minecraftserver.worldServerForDimension(p_71027_1_);
         super.dimension = p_71027_1_;
         super.worldObj.removeEntity(this);
         super.isDead = false;
         super.worldObj.theProfiler.startSection("reposition");
         if(p_71027_1_ == -1) {
            minecraftserver.getConfigurationManager().transferEntityToWorld(this, j, worldserver, worldserver1, this.nightmareTeleporterNether);
         } else {
            minecraftserver.getConfigurationManager().transferEntityToWorld(this, j, worldserver, worldserver1, this.nightmareTeleporterOverworld);
         }

         super.worldObj.theProfiler.endStartSection("reloading");
         Entity entity = EntityList.createEntityByName(EntityList.getEntityString(this), worldserver1);
         if(entity != null) {
            entity.copyDataFrom(this, true);
            worldserver1.spawnEntityInWorld(entity);
         }

         super.isDead = true;
         super.worldObj.theProfiler.endSection();
         worldserver.resetUpdateEntityTick();
         worldserver1.resetUpdateEntityTick();
         super.worldObj.theProfiler.endSection();
         return entity;
      } else {
         return null;
      }
   }

   public EntityPlayerMP playerTravelToDimension(EntityPlayerMP player, int p_71027_1_) {
      if(p_71027_1_ == -1) {
         player.mcServer.getConfigurationManager().transferPlayerToDimension(player, p_71027_1_, this.nightmareTeleporterNether);
      } else {
         player.mcServer.getConfigurationManager().transferPlayerToDimension(player, p_71027_1_, this.nightmareTeleporterOverworld);
      }

      return player;
   }

   public String getCommandSenderName() {
      return this.hasCustomNameTag()?this.getCustomNameTag():StatCollector.translateToLocal("entity.ThaumicHorizons.Nightmare.name");
   }

   public void onUpdate() {
      AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(super.boundingBox.minX, super.boundingBox.minY, super.boundingBox.minZ, super.boundingBox.maxX, super.boundingBox.maxY, super.boundingBox.maxZ);
      if(super.worldObj.isAABBInMaterial(axisalignedbb, Material.lava)) {
         super.motionY += 0.1D;
         if(super.motionY > 0.25D) {
            super.motionY = 0.25D;
         }

         super.onGround = true;
         super.fallDistance = 0.0F;
      } else if(super.worldObj.getBlock((int)super.posX, (int)Math.floor(super.posY - 1.0D), (int)super.posZ).getMaterial() == Material.lava) {
         super.onGround = true;
         super.fallDistance = 0.0F;
         if(super.motionY < 0.0D) {
            super.motionY = 0.0D;
         }
      }

      super.onUpdate();
      Block underfoot = super.worldObj.getBlock((int)super.posX, (int)super.posY - 1, (int)super.posZ);
      Block in = super.worldObj.getBlock((int)super.posX, (int)super.posY, (int)super.posZ);
      Block up = super.worldObj.getBlock((int)super.posX, (int)super.posY + 1, (int)super.posZ);
      if(underfoot.getMaterial() == Material.grass) {
         super.worldObj.setBlock((int)super.posX, (int)super.posY - 1, (int)super.posZ, Blocks.dirt);
         ThaumicHorizons.proxy.smeltFX((double)((int)super.posX), (double)((int)super.posY - 1), (double)((int)super.posZ), super.worldObj, 10, false);
      }

      if(super.worldObj.isRemote && underfoot.isBlockNormalCube() && super.moveForward > 0.0F) {
         ThaumicHorizons.proxy.smeltFX((double)((int)super.posX), (double)((int)super.posY - 1), (double)((int)super.posZ), super.worldObj, 3, false);
      }

      if(in.getMaterial() == Material.leaves || in.getMaterial() == Material.web || in.getMaterial() == Material.vine || in.getMaterial() == Material.plants) {
         super.worldObj.setBlockToAir((int)super.posX, (int)super.posY, (int)super.posZ);
         ThaumicHorizons.proxy.smeltFX((double)((int)super.posX), (double)((int)super.posY), (double)((int)super.posZ), super.worldObj, 15, false);
      }

      if(up.getMaterial() == Material.leaves || up.getMaterial() == Material.web || up.getMaterial() == Material.vine || up.getMaterial() == Material.plants) {
         super.worldObj.setBlockToAir((int)super.posX, (int)super.posY + 1, (int)super.posZ);
         ThaumicHorizons.proxy.smeltFX((double)((int)super.posX), (double)((int)super.posY + 1), (double)((int)super.posZ), super.worldObj, 15, false);
      }

   }

   public void setInWeb() {
      super.isInWeb = false;
      super.fallDistance = 0.0F;
   }
}
