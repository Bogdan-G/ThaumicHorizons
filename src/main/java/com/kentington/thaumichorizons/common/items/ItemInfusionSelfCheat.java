package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import com.kentington.thaumichorizons.common.lib.SelfInfusionRecipe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;

public class ItemInfusionSelfCheat extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon1;
   public IIcon icon2;
   public IIcon icon3;
   public IIcon icon4;
   public IIcon icon5;
   public IIcon icon6;
   public IIcon icon7;
   public IIcon icon8;
   public IIcon icon9;
   public IIcon icon10;


   public ItemInfusionSelfCheat() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon1 = ir.registerIcon("thaumichorizons:quicksilverlimbs");
      this.icon2 = ir.registerIcon("thaumichorizons:morphicfingers");
      this.icon3 = ir.registerIcon("thaumichorizons:awakenedblood");
      this.icon4 = ir.registerIcon("thaumichorizons:diamondskin");
      this.icon5 = ir.registerIcon("thaumichorizons:silverwoodheart");
      this.icon6 = ir.registerIcon("thaumichorizons:synthskin");
      this.icon7 = ir.registerIcon("fish_cod_raw");
      this.icon8 = ir.registerIcon("thaumichorizons:warpedtumor");
      this.icon9 = ir.registerIcon("thaumichorizons:spiderclimb");
      this.icon10 = ir.registerIcon("thaumichorizons:chameleonskin");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      switch(par1) {
      case 1:
         return this.icon1;
      case 2:
         return this.icon2;
      case 3:
         return this.icon3;
      case 4:
         return this.icon4;
      case 5:
         return this.icon5;
      case 6:
         return this.icon6;
      case 7:
         return this.icon7;
      case 8:
         return this.icon8;
      case 9:
         return this.icon9;
      case 10:
         return this.icon10;
      default:
         return null;
      }
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(this, 1, 1));
      par3List.add(new ItemStack(this, 1, 2));
      par3List.add(new ItemStack(this, 1, 3));
      par3List.add(new ItemStack(this, 1, 4));
      par3List.add(new ItemStack(this, 1, 5));
      par3List.add(new ItemStack(this, 1, 6));
      par3List.add(new ItemStack(this, 1, 7));
      par3List.add(new ItemStack(this, 1, 8));
      par3List.add(new ItemStack(this, 1, 9));
      par3List.add(new ItemStack(this, 1, 10));
   }

   public String getItemStackDisplayName(ItemStack stack) {
      String stringy = "";
      switch(stack.getItemDamage()) {
      case 1:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.quicksilver");
         break;
      case 2:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.morphic");
         break;
      case 3:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.awakeBlood");
         break;
      case 4:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.diamondSkin");
         break;
      case 5:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.silverHeart");
         break;
      case 6:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.synthSkin");
         break;
      case 7:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.amphibious");
         break;
      case 8:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.warpedTumor");
         break;
      case 9:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.spiderClimb");
         break;
      case 10:
         stringy = stringy + StatCollector.translateToLocal("selfInfusions.chameleonSkin");
      }

      return stringy;
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World world, EntityPlayer p) {
      EntityPlayer critter = p;
      Iterator var5 = ThaumicHorizons.selfRecipes.iterator();

      SelfInfusionRecipe recipe;
      do {
         if(!var5.hasNext()) {
            return p_77659_1_;
         }

         recipe = (SelfInfusionRecipe)var5.next();
      } while(recipe.getID() != p_77659_1_.getItemDamage() || ((EntityInfusionProperties)critter.getExtendedProperties("CreatureInfusion")).hasPlayerInfusion(recipe.getID()));

      ((EntityInfusionProperties)critter.getExtendedProperties("CreatureInfusion")).addPlayerInfusion(recipe.getID());
      ThaumicHorizons.instance.eventHandlerEntity.applyInfusions(critter);
      Thaumcraft.proxy.burst(world, critter.posX, critter.posY + (double)critter.getEyeHeight(), critter.posZ, 1.0F);
      return p_77659_1_;
   }
}
