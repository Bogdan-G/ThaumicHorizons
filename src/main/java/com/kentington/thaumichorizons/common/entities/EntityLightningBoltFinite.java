package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import com.kentington.thaumichorizons.common.items.ItemFocusAnimation;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityLightningBoltFinite extends EntityLightningBolt {

   public int boltLength;
   public boolean animate;


   public EntityLightningBoltFinite(World p_i1703_1_, double p_i1703_2_, double p_i1703_4_, double p_i1703_6_, int boltLength, boolean animate) {
      super(p_i1703_1_, p_i1703_2_, p_i1703_4_, p_i1703_6_);
      this.boltLength = boltLength;
      this.animate = animate;
   }

   public EntityLightningBoltFinite(World w) {
      super(w, 0.0D, 0.0D, 0.0D);
   }

   protected void readEntityFromNBT(NBTTagCompound tag) {
      super.readEntityFromNBT(tag);
      this.boltLength = tag.getInteger("length");
      this.animate = tag.getBoolean("animate");
   }

   protected void writeEntityToNBT(NBTTagCompound tag) {
      super.writeEntityToNBT(tag);
      tag.setBoolean("animate", this.animate);
      tag.setInteger("length", this.boltLength);
   }

   public void onUpdate() {
      super.onUpdate();
      ThaumicHorizons.proxy.lightningBolt(super.worldObj, super.posX, super.posY, super.posZ, this.boltLength);
      if(this.animate) {
         int p_77648_4_ = (int)Math.floor(super.posX);
         int p_77648_5_ = (int)super.posY;
         int p_77648_6_ = (int)Math.floor(super.posZ);
         Block blocky = super.worldObj.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
         int md = super.worldObj.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
         if(!super.worldObj.isRemote) {
            if(!blocky.hasTileEntity(md) && !blocky.isAir(super.worldObj, p_77648_4_, p_77648_5_, p_77648_6_) && (blocky.isOpaqueCube() || ItemFocusAnimation.isWhitelisted(blocky, md)) && blocky.getBlockHardness(super.worldObj, p_77648_4_, p_77648_5_, p_77648_6_) != -1.0F) {
               if(!super.worldObj.isRemote && ThaumicHorizons.blockCloud.getBlockHardness(super.worldObj, p_77648_4_, p_77648_5_, p_77648_6_) > 0.0F) {
                  EntityGolemTH golem = new EntityGolemTH(super.worldObj);
                  golem.loadGolem((double)p_77648_4_ + 0.5D, (double)p_77648_5_, (double)p_77648_6_ + 0.5D, blocky, md, 1200, false, false, false);
                  super.worldObj.setBlockToAir(p_77648_4_, p_77648_5_, p_77648_6_);
                  super.worldObj.playSoundEffect((double)p_77648_4_ + 0.5D, (double)p_77648_5_ + 0.5D, (double)p_77648_6_ + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
                  golem.setHomeArea((int)golem.posX, (int)golem.posY, (int)golem.posZ, 32);
                  golem.setOwner("");
                  golem.berserk = true;
                  golem.extinguish();
                  golem.heal(100.0F);
                  super.worldObj.spawnEntityInWorld(golem);
                  super.worldObj.setEntityState(golem, (byte)7);
               } else {
                  Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(p_77648_4_, p_77648_5_, p_77648_6_, blocky, md);
               }

            }
         }
      }
   }
}
