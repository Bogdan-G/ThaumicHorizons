package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSoulBeacon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSoulBeacon extends BlockContainer {

   IIcon icon;


   public BlockSoulBeacon() {
      super(Material.iron);
      this.setHardness(7.0F);
      this.setResistance(7.0F);
      this.setBlockName("ThaumicHorizons_soulBeacon");
      this.setBlockTextureName("ThaumicHorizons:soulBeacon");
      this.setCreativeTab(ThaumicHorizons.tabTH);
      this.setLightLevel(1.0F);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileSoulBeacon te = new TileSoulBeacon();
      return te;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      TileEntity te = world.getTileEntity(x, y, z);
      if(te instanceof TileSoulBeacon) {
         TileSoulBeacon tile = (TileSoulBeacon)te;
         return tile.activate(player);
      } else {
         return false;
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
      return ThaumicHorizons.blockSoulBeaconRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("endframe_eye");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }
}
