package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class BlockEvanescent extends Block {

   public BlockEvanescent() {
      super(Material.glass);
      this.setHardness(Float.MAX_VALUE);
      this.setResistance(Float.MAX_VALUE);
      this.setBlockName("ThaumicHorizons_evanescent");
      this.setBlockTextureName("ThaumicHorizons:evanescent");
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
   public void breakBlock(World world, int x, int y, int z, Block block, int md) {
      ThaumicHorizons.instance.renderEventHandler.resetBlocks(Minecraft.getMinecraft().thePlayer);
   }

   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_) {
      return false;
   }
}
