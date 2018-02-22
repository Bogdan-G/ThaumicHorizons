package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityEggIncubated;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemEggIncubated extends ItemEgg {

   @SideOnly(Side.CLIENT)
   public IIcon icon;


   public ItemEggIncubated() {
      super.maxStackSize = 16;
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      if(!p_77659_3_.capabilities.isCreativeMode) {
         --p_77659_1_.stackSize;
      }

      p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
      if(!p_77659_2_.isRemote) {
         p_77659_2_.spawnEntityInWorld(new EntityEggIncubated(p_77659_2_, p_77659_3_));
      }

      return p_77659_1_;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:eggincubated");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.eggIncubated";
   }
}
