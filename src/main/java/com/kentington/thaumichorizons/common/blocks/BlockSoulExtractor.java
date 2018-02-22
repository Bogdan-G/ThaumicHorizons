package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSoulExtractor;
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

public class BlockSoulExtractor extends BlockContainer {

   IIcon icon;


   public BlockSoulExtractor() {
      super(Material.wood);
      this.setHardness(0.7F);
      this.setResistance(1.0F);
      this.setBlockName("ThaumicHorizons_soulSieve");
      this.setBlockTextureName("ThaumicHorizons:soulSieve");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileSoulExtractor te = new TileSoulExtractor();
      return te;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      player.openGui(ThaumicHorizons.instance, 2, world, x, y, z);
      return true;
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int md) {
      TileEntity te = world.getTileEntity(x, y, z);
      if(te instanceof TileSoulExtractor) {
         TileSoulExtractor tile = (TileSoulExtractor)te;
         if(tile.soulsand != null) {
            world.spawnEntityInWorld(new EntityItem(world, (double)x, (double)y, (double)z, tile.soulsand));
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 0;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockSoulSieveRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("iron_block");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }
}
