package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSoulJar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSoulJar extends BlockContainer {

   public IIcon iconJarBottom;
   public IIcon iconJarSide;
   public IIcon iconJarTop;


   public BlockSoulJar() {
      super(Material.glass);
      this.setHardness(0.3F);
      this.setResistance(1.0F);
      this.setBlockName("ThaumicHorizons_soulJar");
      this.setBlockTextureName("ThaumicHorizons:soulJar");
      this.setCreativeTab(ThaumicHorizons.tabTH);
      this.setLightLevel(0.66F);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.iconJarSide = ir.registerIcon("thaumcraft:jar_side");
      this.iconJarTop = ir.registerIcon("thaumcraft:jar_top");
      this.iconJarBottom = ir.registerIcon("thaumcraft:jar_bottom");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      return side == 1?this.iconJarTop:(side == 0?this.iconJarBottom:this.iconJarSide);
   }

   public int getRenderBlockPass() {
      return 1;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockJarRI;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
      this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
      super.setBlockBoundsBasedOnState(world, i, j, k);
   }

   public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileSoulJar te = new TileSoulJar();
      return te;
   }

   public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {
      this.dropBlockAsItem(par1World, par2, par3, par4, par5, 0);
      super.onBlockHarvested(par1World, par2, par3, par4, par5, par6EntityPlayer);
   }

   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      ArrayList drops = new ArrayList();
      TileEntity te = world.getTileEntity(x, y, z);
      if(te != null && te instanceof TileSoulJar) {
         ItemStack drop = new ItemStack(this);
         drop.setTagCompound(((TileSoulJar)te).jarTag);
         drops.add(drop);
      }

      return drops;
   }
}
