package com.kentington.thaumichorizons.common.tiles;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.config.ConfigBlocks;

public class TileSoulJar extends TileThaumcraft implements IWandable {

   public NBTTagCompound jarTag = null;
   public Entity entity = null;
   public boolean drop = true;
   ResourceLocation texture = new ResourceLocation("thaumcraft", "textures/models/jar.png");


   public void updateEntity() {
      if(this.entity == null && this.jarTag != null && !this.jarTag.getBoolean("isSoul")) {
         this.entity = EntityList.createEntityFromNBT(this.jarTag, super.worldObj);
      }

   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.jarTag = nbttagcompound.getCompoundTag("jarTag");
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setTag("jarTag", this.jarTag);
   }

   public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
      if(this.jarTag.getBoolean("isSoul")) {
         return 0;
      } else {
         if(!world.isRemote) {
            this.drop = false;
            world.setBlockToAir(x, y, z);
            Entity ent = EntityList.createEntityFromNBT(this.jarTag, world);
            if(ent == null) {
               return 0;
            }

            if(ent instanceof EntityTameable && ((EntityTameable)ent).getOwner() == null) {
               ((EntityTameable)ent).func_152115_b(player.getUniqueID().toString());
            }

            ent.setLocationAndAngles((double)x + 0.5D, (double)y + 0.1D, (double)z + 0.5D, 0.0F, 0.0F);
            world.spawnEntityInWorld(ent);
            this.markDirty();
         }

         world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(ConfigBlocks.blockJar) + '\uf000');
         player.worldObj.playSound((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.glass", 1.0F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
         player.swingItem();
         return 0;
      }
   }

   public ResourceLocation getTexture() {
      return this.texture;
   }

   public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
      return null;
   }

   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
}
