package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVortex;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.tiles.TileNode;

public class ItemNodeCheat extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon1;


   public ItemNodeCheat() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
      this.setUnlocalizedName("nodeCheat");
   }

   public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
      TileEntity te = p_77648_3_.getTileEntity(p_77648_4_, p_77648_5_, p_77648_6_);
      if(te != null && te instanceof TileNode) {
         AspectList aspects = ((TileNode)te).getAspectsBase();
         p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, ThaumicHorizons.blockVortex);
         TileVortex tco = (TileVortex)p_77648_3_.getTileEntity(p_77648_4_, p_77648_5_, p_77648_6_);
         tco.aspects = aspects.copy();
         tco.cheat = true;
         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon1 = ir.registerIcon("thaumichorizons:nodecheat");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon1;
   }

   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      par3List.add("Use on a node to transform it");
      par3List.add("into a self-stabilized vortex");
      super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
   }
}
