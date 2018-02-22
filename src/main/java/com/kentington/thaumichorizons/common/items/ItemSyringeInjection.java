package com.kentington.thaumichorizons.common.items;

import com.google.common.collect.HashMultimap;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityBlastPhial;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSyringeInjection extends ItemPotion {

   private IIcon field_94590_d;
   private IIcon field_94591_c;
   private IIcon field_94592_ct;


   public ItemSyringeInjection() {
      this.setHasSubtypes(true);
      this.setMaxStackSize(1);
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public int getMaxItemUseDuration(ItemStack p_77626_1_) {
      return 8;
   }

   public EnumAction getItemUseAction(ItemStack p_77661_1_) {
      return EnumAction.bow;
   }

   public String getItemStackDisplayName(ItemStack p_77653_1_) {
      return StatCollector.translateToLocal("item.injection." + p_77653_1_.getItemDamage() + ".name");
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {}

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.field_94590_d = ir.registerIcon("thaumichorizons:phialBlood");
      this.field_94591_c = ir.registerIcon("thaumichorizons:phialBlood");
      this.field_94592_ct = ir.registerIcon("thaumichorizons:phialBlood");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int p_77617_1_) {
      return isSplash(p_77617_1_)?this.field_94591_c:this.field_94590_d;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
      return p_77618_2_ == 0?this.field_94592_ct:super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
   }

   @SideOnly(Side.CLIENT)
   public static IIcon func_94589_d(String p_94589_0_) {
      return p_94589_0_.equals("bottle_drinkable")?((ItemSyringeInjection)ThaumicHorizons.itemSyringeInjection).field_94590_d:(p_94589_0_.equals("bottle_splash")?((ItemSyringeInjection)ThaumicHorizons.itemSyringeInjection).field_94591_c:(p_94589_0_.equals("overlay")?((ItemSyringeInjection)ThaumicHorizons.itemSyringeInjection).field_94592_ct:null));
   }

   public boolean isPhial(int p_77831_0_) {
      return p_77831_0_ != 0;
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      if(this.isPhial(p_77659_1_.getItemDamage())) {
         if(!p_77659_3_.capabilities.isCreativeMode) {
            --p_77659_1_.stackSize;
         }

         p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
         if(!p_77659_2_.isRemote) {
            p_77659_2_.spawnEntityInWorld(new EntityBlastPhial(p_77659_2_, p_77659_3_, 0.5F, p_77659_1_));
         }

         return p_77659_1_;
      } else {
         p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
         return p_77659_1_;
      }
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
      List list1 = Items.potionitem.getEffects(p_77624_1_);
      HashMultimap hashmultimap = HashMultimap.create();
      Iterator iterator1;
      if(list1 != null && !list1.isEmpty()) {
         iterator1 = list1.iterator();

         while(iterator1.hasNext()) {
            PotionEffect entry12 = (PotionEffect)iterator1.next();
            String attributemodifier2 = StatCollector.translateToLocal(entry12.getEffectName()).trim();
            Potion d0 = Potion.potionTypes[entry12.getPotionID()];
            Map map = d0.func_111186_k();
            if(map != null && map.size() > 0) {
               Iterator d1 = map.entrySet().iterator();

               while(d1.hasNext()) {
                  Entry entry = (Entry)d1.next();
                  AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
                  AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), d0.func_111183_a(entry12.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                  hashmultimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
               }
            }

            if(entry12.getAmplifier() > 0) {
               attributemodifier2 = attributemodifier2 + " " + StatCollector.translateToLocal("potion.potency." + entry12.getAmplifier()).trim();
            }

            if(entry12.getDuration() > 20) {
               attributemodifier2 = attributemodifier2 + " (" + Potion.getDurationString(entry12) + ")";
            }

            if(d0.isBadEffect()) {
               p_77624_3_.add(EnumChatFormatting.RED + attributemodifier2);
            } else {
               p_77624_3_.add(EnumChatFormatting.GRAY + attributemodifier2);
            }
         }
      } else {
         String entry1 = StatCollector.translateToLocal("potion.empty").trim();
         p_77624_3_.add(EnumChatFormatting.GRAY + entry1);
      }

      if(!hashmultimap.isEmpty()) {
         p_77624_3_.add("");
         p_77624_3_.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
         iterator1 = hashmultimap.entries().iterator();

         while(iterator1.hasNext()) {
            Entry entry11 = (Entry)iterator1.next();
            AttributeModifier attributemodifier21 = (AttributeModifier)entry11.getValue();
            double d01 = attributemodifier21.getAmount();
            double d11;
            if(attributemodifier21.getOperation() != 1 && attributemodifier21.getOperation() != 2) {
               d11 = attributemodifier21.getAmount();
            } else {
               d11 = attributemodifier21.getAmount() * 100.0D;
            }

            if(d01 > 0.0D) {
               p_77624_3_.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier21.getOperation(), new Object[]{ItemStack.field_111284_a.format(d11), StatCollector.translateToLocal("attribute.name." + (String)entry11.getKey())}));
            } else if(d01 < 0.0D) {
               d11 *= -1.0D;
               p_77624_3_.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier21.getOperation(), new Object[]{ItemStack.field_111284_a.format(d11), StatCollector.translateToLocal("attribute.name." + (String)entry11.getKey())}));
            }
         }
      }

   }

   public boolean hitEntity(ItemStack is, EntityLivingBase target, EntityLivingBase hitter) {
      if(!target.worldObj.isRemote) {
         List list = this.getEffects(is);
         if(list != null) {
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
               PotionEffect potioneffect = (PotionEffect)iterator.next();
               target.addPotionEffect(new PotionEffect(potioneffect));
            }
         }
      }

      --is.stackSize;
      return super.hitEntity(is, target, hitter);
   }

   public int getColorFromItemStack(ItemStack stack, int p_82790_2_) {
      if(stack.getItem() == ThaumicHorizons.itemSyringeInjection) {
         if(stack.hasTagCompound()) {
            return stack.getTagCompound().getInteger("color");
         }
      } else if(stack.getItem() != ThaumicHorizons.itemSyringeEmpty) {
         return Color.RED.getRGB();
      }

      return 16777215;
   }

   public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
      if(!p_77654_3_.capabilities.isCreativeMode) {
         --p_77654_1_.stackSize;
      }

      if(!p_77654_2_.isRemote) {
         List list = this.getEffects(p_77654_1_);
         if(list != null) {
            Iterator iterator = list.iterator();

            while(iterator.hasNext()) {
               PotionEffect potioneffect = (PotionEffect)iterator.next();
               p_77654_3_.addPotionEffect(new PotionEffect(potioneffect));
            }
         }
      }

      return p_77654_1_.stackSize <= 0?null:p_77654_1_;
   }
}
