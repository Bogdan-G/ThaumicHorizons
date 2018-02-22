package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileInspiratron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockInspiratron extends BlockContainer {

   public IIcon iconLiquid;
   public IIcon iconJarBottom;
   public IIcon iconJarSide;
   public IIcon iconJarTop;


   public BlockInspiratron() {
      super(Material.glass);
      this.setHardness(0.7F);
      this.setResistance(1.0F);
      this.setBlockName("ThaumicHorizons_inspiratron");
      this.setBlockTextureName("ThaumicHorizons:inspiratron");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileInspiratron te = new TileInspiratron();
      return te;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      player.openGui(ThaumicHorizons.instance, 3, world, x, y, z);
      return true;
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int md) {
      TileEntity te = world.getTileEntity(x, y, z);
      if(te instanceof TileInspiratron) {
         TileInspiratron tile = (TileInspiratron)te;
         if(tile.paper != null) {
            world.spawnEntityInWorld(new EntityItem(world, (double)x, (double)y, (double)z, tile.paper));
         }

         if(tile.knowledge != null) {
            world.spawnEntityInWorld(new EntityItem(world, (double)x, (double)y, (double)z, tile.knowledge));
         }
      }

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
      return ThaumicHorizons.blockInspiratronRI;
   }
}
