package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileLight;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.Block.SoundType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.config.Config;

public class BlockLight extends BlockContainer {

   public IIcon blankIcon;


   public BlockLight() {
      super(Config.airyMaterial);
      this.setBlockName("ThaumicHorizons_light");
      this.setStepSound(new SoundType("cloth", 0.0F, 1.0F));
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.blankIcon = ir.registerIcon("thaumcraft:blank");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      return this.blankIcon;
   }

   public float getBlockHardness(World world, int x, int y, int z) {
      return 0.0F;
   }

   public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
      return 0.0F;
   }

   public int getLightValue(IBlockAccess world, int x, int y, int z) {
      return 14;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess ba, int x, int y, int z) {
      this.setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
   }

   public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
      return false;
   }

   public int getRenderType() {
      return ThaumicHorizons.blockLightRI;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public Item getItemDropped(int par1, Random par2Random, int par3) {
      return Item.getItemById(0);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileLight();
   }

   public TileEntity createNewTileEntity(World var1, int md) {
      return null;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
      return null;
   }
}
