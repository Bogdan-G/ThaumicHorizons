package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSoulforge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSoulforge extends BlockContainer {

   public IIcon iconLiquid;
   public IIcon iconJarBottom;
   public IIcon iconJarSide;
   public IIcon iconJarTop;


   public BlockSoulforge() {
      super(Material.glass);
      this.setHardness(0.7F);
      this.setResistance(1.0F);
      this.setBlockName("ThaumicHorizons_soulforge");
      this.setBlockTextureName("ThaumicHorizons:soulforge");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileSoulforge te = new TileSoulforge();
      return te;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      TileEntity tile = world.getTileEntity(x, y, z);
      return tile instanceof TileSoulforge?((TileSoulforge)tile).activate(world, player):false;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.iconJarSide = ir.registerIcon("thaumichorizons:jar_side");
      this.iconJarTop = ir.registerIcon("thaumichorizons:jar_top");
      this.iconJarBottom = ir.registerIcon("thaumichorizons:jar_bottom");
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
      return ThaumicHorizons.blockSoulforgeRI;
   }
}
