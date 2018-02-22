package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVortexStabilizer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockVortexStabilizer extends BlockContainer {

   IIcon icon;


   public BlockVortexStabilizer() {
      super(Material.iron);
      this.setBlockName("ThaumicHorizons_vortexStabilizer");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public int onBlockPlaced(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_) {
      return p_149660_5_;
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileVortexStabilizer();
   }

   public void killMe(World world, int x, int y, int z, boolean drop) {
      if(((TileVortexStabilizer)world.getTileEntity(x, y, z)).hasTarget) {
         ((TileVortexStabilizer)world.getTileEntity(x, y, z)).reHungrifyTarget();
      }

      if(drop) {
         this.dropBlockAsItem(world, x, y, z, 0, 0);
      }

   }

   public void onBlockPreDestroy(World world, int x, int y, int z, int md) {
      this.killMe(world, x, y, z, false);
   }

   public void onNeighborBlockChange(World world, int x, int y, int z, Block nbid) {
      TileVortexStabilizer tile = (TileVortexStabilizer)world.getTileEntity(x, y, z);
      tile.redstoned = world.isBlockIndirectlyGettingPowered(x, y, z);
      if(!tile.redstoned && !world.isBlockIndirectlyGettingPowered(x, y, z) || tile.redstoned && world.isBlockIndirectlyGettingPowered(x, y, z)) {
         tile.markDirty();
         tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
      }

   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 1;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockVortexStabilizerRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("iron_block");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }
}
