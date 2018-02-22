package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSyntheticNode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemWispEssence;

public class BlockSyntheticNode extends BlockContainer {

   IIcon icon;


   public BlockSyntheticNode() {
      super(Material.glass);
      this.setHardness(0.7F);
      this.setResistance(1.0F);
      this.setLightLevel(0.5F);
      this.setBlockName("ThaumicHorizons_synthNode");
      this.setCreativeTab(ThaumicHorizons.tabTH);
      this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
   }

   public TileEntity createNewTileEntity(World world, int md) {
      return this.createTileEntity(world, md);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      TileSyntheticNode node = new TileSyntheticNode();
      return node;
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
      return ThaumicHorizons.blockSyntheticNodeRI;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumcraft:crystal");
   }

   public IIcon getIcon(int par1, int par2) {
      return this.icon;
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      if(player.getHeldItem() != null && player.getHeldItem().getItem() == ConfigItems.itemWispEssence) {
         ((TileSyntheticNode)world.getTileEntity(x, y, z)).addEssence(player);
         return true;
      } else {
         return false;
      }
   }

   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
      TileSyntheticNode tile = (TileSyntheticNode)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
      if(tile != null) {
         Aspect[] var8 = tile.getMaxAspects().getAspects();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            Aspect asp = var8[var10];
            ItemStack essence = new ItemStack(ConfigItems.itemWispEssence, tile.getMaxAspects().getAmount(asp) / 4);
            ((ItemWispEssence)ConfigItems.itemWispEssence).setAspects(essence, (new AspectList()).add(asp, 2));
            p_149749_1_.spawnEntityInWorld(new EntityItem(p_149749_1_, (double)p_149749_2_, (double)p_149749_3_, (double)p_149749_4_, essence));
         }
      }

      super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
   }
}
