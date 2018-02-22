package com.kentington.thaumichorizons.common.items;

import com.google.common.collect.HashMultimap;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.ItemFocusContainment;
import com.kentington.thaumichorizons.common.lib.CreatureInfusionRecipe;
import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.golems.EntityGolemBase;

public class ItemInfusionCheat extends Item {

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


   public ItemInfusionCheat() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon1 = ir.registerIcon("thaumichorizons:quicksilverlimbs");
      this.icon2 = ir.registerIcon("thaumichorizons:thaumclaws");
      this.icon3 = ir.registerIcon("thaumichorizons:awakenedblood");
      this.icon4 = ir.registerIcon("thaumichorizons:diamondskin");
      this.icon5 = ir.registerIcon("thaumichorizons:enderheart");
      this.icon6 = ir.registerIcon("thaumichorizons:shockskin");
      this.icon7 = ir.registerIcon("thaumichorizons:instilledloyalty");
      this.icon8 = ir.registerIcon("thaumichorizons:runichide");
      this.icon9 = ir.registerIcon("thaumichorizons:eldritchfangs");
      this.icon10 = ir.registerIcon("thaumichorizons:portability");
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
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.quicksilver");
         break;
      case 2:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.thaumClaws");
         break;
      case 3:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.awakeBlood");
         break;
      case 4:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.diamondSkin");
         break;
      case 5:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.enderHeart");
         break;
      case 6:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.shockSkin");
         break;
      case 7:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.instilledLoyalty");
         break;
      case 8:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.runicHide");
         break;
      case 9:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.eldritchFangs");
         break;
      case 10:
         stringy = stringy + StatCollector.translateToLocal("critterInfusions.portability");
      }

      return stringy;
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World world, EntityPlayer p) {
      Entity ent = ItemFocusContainment.getPointedEntity(world, p, 1.5D);
      if(ent != null && ent instanceof EntityLiving && this.isValidInfusionTarget((EntityLiving)ent)) {
         EntityLiving critter = (EntityLiving)ent;
         Iterator var7 = ThaumicHorizons.critterRecipes.iterator();

         while(var7.hasNext()) {
            CreatureInfusionRecipe recipe = (CreatureInfusionRecipe)var7.next();
            boolean blockLoyalty = false;
            if(recipe.getID((Class)null) == p_77659_1_.getItemDamage() && recipe.getRecipeOutput() instanceof NBTTagCompound && !((EntityInfusionProperties)critter.getExtendedProperties("CreatureInfusion")).hasInfusion(recipe.getID((Class)null))) {
               NBTTagCompound tagMods = (NBTTagCompound)recipe.getRecipeOutput();
               HashMultimap map = HashMultimap.create();
               if(tagMods.getDouble("generic.movementSpeed") > 0.0D) {
                  map.put("generic.movementSpeed", new AttributeModifier("generic.movementSpeed", tagMods.getDouble("generic.movementSpeed") / 10.0D, 1));
               }

               if(tagMods.getDouble("generic.maxHealth") > 0.0D) {
                  map.put("generic.maxHealth", new AttributeModifier("generic.maxHealth", tagMods.getDouble("generic.maxHealth"), 1));
               }

               if(tagMods.getDouble("generic.attackDamage") > 0.0D) {
                  map.put("generic.attackDamage", new AttributeModifier("generic.attackDamage", tagMods.getDouble("generic.attackDamage"), 1));
               }

               if(map.size() > 0) {
                  critter.getAttributeMap().applyAttributeModifiers(map);
               }

               Set keys = tagMods.func_150296_c();
               Iterator var13 = keys.iterator();

               while(var13.hasNext()) {
                  String s = (String)var13.next();
                  if(!s.substring(0, 8).equals("generic.")) {
                     if(tagMods.getInteger(s) == 7) {
                        if(critter.tasks.taskEntries.size() == 0) {
                           blockLoyalty = true;
                        } else {
                           ((EntityInfusionProperties)critter.getExtendedProperties("CreatureInfusion")).setOwner(p.getCommandSenderName());
                        }
                     }

                     if(!blockLoyalty) {
                        ((EntityInfusionProperties)critter.getExtendedProperties("CreatureInfusion")).addInfusion(tagMods.getInteger(s));
                     }
                  }
               }

               if(!blockLoyalty) {
                  ((EntityInfusionProperties)critter.getExtendedProperties("CreatureInfusion")).addCost(recipe.getAspects());
                  critter.func_110163_bv();
                  ThaumicHorizons.instance.eventHandlerEntity.applyInfusions(critter);
                  Thaumcraft.proxy.burst(world, critter.posX, critter.posY + (double)critter.getEyeHeight(), critter.posZ, 1.0F);
               }

               return p_77659_1_;
            }
         }
      }

      return p_77659_1_;
   }

   public boolean isValidInfusionTarget(EntityLiving ent) {
      if(ent != null && ent.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && !(ent instanceof EntityGolem) && !(ent instanceof EntityGolemBase) && !(ent instanceof IMerchant) && !(ent instanceof INpc)) {
         Iterator var2 = ThaumicHorizons.classBanList.iterator();

         Class clazz;
         do {
            if(!var2.hasNext()) {
               return true;
            }

            clazz = (Class)var2.next();
         } while(!ent.getClass().isAssignableFrom(clazz));

         return false;
      } else {
         return false;
      }
   }
}
