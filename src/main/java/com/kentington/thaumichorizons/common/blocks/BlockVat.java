package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import com.kentington.thaumichorizons.common.tiles.TileVatConnector;
import com.kentington.thaumichorizons.common.tiles.TileVatSlave;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockVat extends BlockContainer {

   public IIcon iconGlassTL;
   public IIcon iconGlassT;
   public IIcon iconGlassTR;
   public IIcon iconGlassBL;
   public IIcon iconGlassB;
   public IIcon iconGlassBR;


   public BlockVat() {
      super(Material.wood);
      this.setHardness(3.0F);
      this.setResistance(15.0F);
      super.lightValue = 8;
      this.setBlockName("ThaumicHorizons_vat");
   }

   public BlockVat(Material m) {
      super(m);
      this.setHardness(3.0F);
      this.setResistance(15.0F);
      super.lightValue = 8;
      this.setBlockName("ThaumicHorizons_vatSolid");
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      Object vat;
      if(metadata == 7) {
         vat = new TileVat();
      } else if(metadata == 4) {
         vat = new TileVatConnector();
      } else {
         vat = new TileVatSlave();
      }

      return (TileEntity)vat;
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int md) {
      if(md == 7) {
         ((TileVat)world.getTileEntity(x, y, z)).killMe();
      } else {
         ((TileVatSlave)world.getTileEntity(x, y, z)).killMyBoss(md);
      }

      super.breakBlock(world, x, y, z, block, md);
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      return world.getTileEntity(x, y, z) instanceof TileVat?((TileVat)world.getTileEntity(x, y, z)).activate(player, true):(world.getTileEntity(x, y, z) instanceof TileVatSlave?((TileVatSlave)world.getTileEntity(x, y, z)).activate(player):false);
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
      return ThaumicHorizons.blockVatRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.iconGlassTL = ir.registerIcon("thaumichorizons:vattopleft");
      this.iconGlassT = ir.registerIcon("thaumichorizons:vattop");
      this.iconGlassTR = ir.registerIcon("thaumichorizons:vattopright");
      this.iconGlassBL = ir.registerIcon("thaumichorizons:vatbottomleft");
      this.iconGlassB = ir.registerIcon("thaumichorizons:vatbottom");
      this.iconGlassBR = ir.registerIcon("thaumichorizons:vatbottomright");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.iconGlassT;
   }
}
