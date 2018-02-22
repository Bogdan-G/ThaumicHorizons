package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSlot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;

public class BlockSlot extends BlockContainer {

   public BlockSlot() {
      super(Material.rock);
      this.setHardness(2.5F);
      this.setResistance(2.5F);
      this.setBlockName("ThaumicHorizons_slot");
      this.setBlockTextureName("ThaumicHorizons:void");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int md) {
      TileSlot tco = (TileSlot)world.getTileEntity(x, y, z);
      if(tco.portalOpen) {
         tco.destroyPortal();
      }

      if(tco.hasKeystone) {
         int dim = tco.removeKeystone();
         ItemStack keystone = new ItemStack(ThaumicHorizons.itemKeystone);
         keystone.stackTagCompound = new NBTTagCompound();
         keystone.stackTagCompound.setInteger("dimension", dim);
         EntityItem dropped = new EntityItem(world);
         dropped.setEntityItemStack(keystone);
         dropped.setPosition((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D);
         world.spawnEntityInWorld(dropped);
      }

   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      TileSlot tco = (TileSlot)world.getTileEntity(x, y, z);
      ItemStack theItem = player.getHeldItem();
      if(tco.hasKeystone) {
         if(player.getHeldItem() == null) {
            int dim = tco.removeKeystone();
            ItemStack keystone = new ItemStack(ThaumicHorizons.itemKeystone);
            keystone.stackTagCompound = new NBTTagCompound();
            keystone.stackTagCompound.setInteger("dimension", dim);
            player.inventory.addItemStackToInventory(keystone);
            if(tco.portalOpen) {
               tco.destroyPortal();
            }
         } else if(!tco.portalOpen && player.getHeldItem().getItem() instanceof ItemWandCasting) {
            tco.makePortal(player);
         }
      } else if(theItem != null && theItem.getItem() == ThaumicHorizons.itemKeystone && theItem.stackTagCompound != null) {
         tco.insertKeystone(theItem.stackTagCompound.getInteger("dimension"));
         --theItem.stackSize;
      }

      world.markBlockForUpdate(x, y, z);
      return false;
   }

   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
      return new TileSlot();
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockSlotRI;
   }
}
