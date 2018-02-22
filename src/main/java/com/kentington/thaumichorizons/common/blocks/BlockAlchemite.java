package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityAlchemitePrimed;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockAlchemite extends BlockTNT {

   private static IIcon blockIconTop;
   private static IIcon blockIconBottom;


   public BlockAlchemite() {
      this.setHardness(0.7F);
      this.setResistance(1.0F);
      this.setLightLevel(0.5F);
      this.setBlockName("ThaumicHorizons_alchemite");
      this.setBlockTextureName("ThaumicHorizons:alchemite");
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {
      if(!p_149723_1_.isRemote) {
         EntityAlchemitePrimed entitytntprimed = new EntityAlchemitePrimed(p_149723_1_, (double)((float)p_149723_2_ + 0.5F), (double)((float)p_149723_3_ + 0.5F), (double)((float)p_149723_4_ + 0.5F), p_149723_5_.getExplosivePlacedBy());
         entitytntprimed.fuse = p_149723_1_.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
         p_149723_1_.spawnEntityInWorld(entitytntprimed);
      }

   }

   public void func_150114_a(World p_150114_1_, int p_150114_2_, int p_150114_3_, int p_150114_4_, int p_150114_5_, EntityLivingBase p_150114_6_) {
      if(!p_150114_1_.isRemote && (p_150114_5_ & 1) == 1) {
         EntityAlchemitePrimed entitytntprimed = new EntityAlchemitePrimed(p_150114_1_, (double)((float)p_150114_2_ + 0.5F), (double)((float)p_150114_3_ + 0.5F), (double)((float)p_150114_4_ + 0.5F), p_150114_6_);
         p_150114_1_.spawnEntityInWorld(entitytntprimed);
         p_150114_1_.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
      return p_149691_1_ == 0?blockIconBottom:(p_149691_1_ == 1?blockIconTop:super.blockIcon);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister p_149651_1_) {
      super.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
      blockIconTop = p_149651_1_.registerIcon(this.getTextureName() + "_top");
      blockIconBottom = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
   }
}
