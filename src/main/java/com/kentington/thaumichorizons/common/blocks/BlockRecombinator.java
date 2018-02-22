package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileRecombinator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockRecombinator extends BlockContainer {

   IIcon icon;


   public BlockRecombinator() {
      super(Material.rock);
      this.setBlockName("ThaumicHorizons_recombinator");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileRecombinator();
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
      return ThaumicHorizons.blockRecombinatorRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("iron_block");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }

   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
      return side == ForgeDirection.UP;
   }

   public void onNeighborBlockChange(World world, int x, int y, int z, Block nbid) {
      TileRecombinator tile = (TileRecombinator)world.getTileEntity(x, y, z);
      if(tile.activated && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
         tile.shouldActivate = false;
      } else if(!tile.activated && world.isBlockIndirectlyGettingPowered(x, y, z)) {
         tile.shouldActivate = true;
      }

   }
}
