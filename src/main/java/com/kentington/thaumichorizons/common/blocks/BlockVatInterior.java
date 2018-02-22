package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVatSlave;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockVatInterior extends BlockContainer {

   public BlockVatInterior() {
      super(Material.water);
      this.setHardness(3.0F);
      this.setResistance(15.0F);
      super.lightValue = 8;
      this.setBlockName("ThaumicHorizons_vatInterior");
      this.setBlockTextureName("ThaumicHorizons:vatInterior");
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileVatSlave();
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int md) {
      ((TileVatSlave)world.getTileEntity(x, y, z)).killMyBoss(md);
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockVatInteriorRI;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      return ((TileVatSlave)world.getTileEntity(x, y, z)).activate(player);
   }

   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
      p_149670_5_.setAir(300);
   }
}
