package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityItemInvulnerable;
import com.kentington.thaumichorizons.common.tiles.TileCloud;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.research.ResearchManager;

public class BlockCloud extends BlockContainer {

   public IIcon[] icon = new IIcon[10];
   public IIcon[] icontop = new IIcon[10];
   boolean glow;


   public BlockCloud(boolean glowy) {
      super(Material.cloth);
      this.setHardness(Float.MAX_VALUE);
      this.setResistance(Float.MAX_VALUE);
      this.setBlockName("ThaumicHorizons_cloud");
      this.setBlockTextureName("ThaumicHorizons:cloud");
      this.setCreativeTab(ThaumicHorizons.tabTH);
      if(glowy) {
         this.setLightLevel(1.0F);
      }

      this.glow = glowy;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister par1IconRegister) {
      this.icon[0] = par1IconRegister.registerIcon("thaumichorizons:cloud");
      this.icon[1] = par1IconRegister.registerIcon("thaumichorizons:firecloud");
      this.icon[2] = par1IconRegister.registerIcon("thaumichorizons:thundercloud");
      this.icon[3] = par1IconRegister.registerIcon("thaumichorizons:acidcloud");
      this.icon[4] = par1IconRegister.registerIcon("thaumichorizons:alloycloud");
      this.icon[5] = par1IconRegister.registerIcon("thaumichorizons:fleshcloud");
      this.icon[6] = par1IconRegister.registerIcon("thaumichorizons:viscloud");
      this.icon[7] = par1IconRegister.registerIcon("thaumichorizons:glyphcloud");
      this.icon[8] = par1IconRegister.registerIcon("thaumichorizons:sporecloud");
      this.icon[9] = par1IconRegister.registerIcon("thaumichorizons:animuscloud");
      this.icontop[0] = par1IconRegister.registerIcon("thaumichorizons:cloudtop");
      this.icontop[1] = par1IconRegister.registerIcon("thaumichorizons:firecloudtop");
      this.icontop[2] = par1IconRegister.registerIcon("thaumichorizons:thundercloudtop");
      this.icontop[3] = par1IconRegister.registerIcon("thaumichorizons:acidcloudtop");
      this.icontop[4] = par1IconRegister.registerIcon("thaumichorizons:alloycloudtop");
      this.icontop[5] = par1IconRegister.registerIcon("thaumichorizons:fleshcloudtop");
      this.icontop[6] = par1IconRegister.registerIcon("thaumichorizons:viscloudtop");
      this.icontop[7] = par1IconRegister.registerIcon("thaumichorizons:glyphcloudtop");
      this.icontop[8] = par1IconRegister.registerIcon("thaumichorizons:sporecloudtop");
      this.icontop[9] = par1IconRegister.registerIcon("thaumichorizons:animuscloudtop");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int par1, int par2) {
      return par1 != 0 && par1 != 1?this.icon[par2]:this.icontop[par2];
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

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {}

   private void func_150186_m(World p_150186_1_, int p_150186_2_, int p_150186_3_, int p_150186_4_) {
      Random random = p_150186_1_.rand;
      double d0 = 0.0625D;

      for(int l = 0; l < 6; ++l) {
         double d1 = (double)((float)p_150186_2_ + random.nextFloat());
         double d2 = (double)((float)p_150186_3_ + random.nextFloat());
         double d3 = (double)((float)p_150186_4_ + random.nextFloat());
         if(l == 0 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube()) {
            d2 = (double)(p_150186_3_ + 1) + d0;
         }

         if(l == 1 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube()) {
            d2 = (double)(p_150186_3_ + 0) - d0;
         }

         if(l == 2 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube()) {
            d3 = (double)(p_150186_4_ + 1) + d0;
         }

         if(l == 3 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube()) {
            d3 = (double)(p_150186_4_ + 0) - d0;
         }

         if(l == 4 && !p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube()) {
            d1 = (double)(p_150186_2_ + 1) + d0;
         }

         if(l == 5 && !p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube()) {
            d1 = (double)(p_150186_2_ + 0) - d0;
         }

         if(random.nextInt(10) == 0 && (d1 < (double)p_150186_2_ || d1 > (double)(p_150186_2_ + 1) || d2 < 0.0D || d2 > (double)(p_150186_3_ + 1) || d3 < (double)p_150186_4_ || d3 > (double)(p_150186_4_ + 1))) {
            p_150186_1_.spawnParticle("cloud", d1, d2, d3, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
      return new TileCloud();
   }

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
      if(!world.isRemote && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemWandCasting && ResearchManager.isResearchComplete(player.getCommandSenderName(), "planarClouds") && ((ItemWandCasting)player.getHeldItem().getItem()).consumeVis(player.getHeldItem(), player, Aspect.AIR, 100, false)) {
         world.spawnEntityInWorld(new EntityItemInvulnerable(world, (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, new ItemStack(this, 1, world.getBlockMetadata(x, y, z))));
         world.setBlockToAir(x, y, z);
         world.markBlockForUpdate(x, y, z);
         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      if(!this.glow) {
         par3List.add(new ItemStack(this, 1, 0));
         par3List.add(new ItemStack(this, 1, 2));
         par3List.add(new ItemStack(this, 1, 3));
         par3List.add(new ItemStack(this, 1, 5));
         par3List.add(new ItemStack(this, 1, 8));
      } else {
         par3List.add(new ItemStack(this, 1, 1));
         par3List.add(new ItemStack(this, 1, 4));
         par3List.add(new ItemStack(this, 1, 6));
         par3List.add(new ItemStack(this, 1, 7));
         par3List.add(new ItemStack(this, 1, 9));
      }

   }

   public int damageDropped(int par1) {
      return par1;
   }

   @SideOnly(Side.CLIENT)
   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
      Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
      return block == this?p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]):true;
   }

   public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
      return false;
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
      return null;
   }

   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity ent) {
      int md = p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_);
      if(md != 1 && md != 4) {
         if(md == 3) {
            ent.attackEntityFrom(DamageSourceThaumcraft.dissolve, 1.0F);
         }
      } else {
         ent.setFire(6);
      }

   }
}
