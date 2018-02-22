package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.ItemFocusContainment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSyringeEmpty extends Item {

   @SideOnly(Side.CLIENT)
   public IIcon icon;


   public ItemSyringeEmpty() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:syringeEmpty");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.syringeEmpty";
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World world, EntityPlayer p) {
      Entity ent = ItemFocusContainment.getPointedEntity(world, p, 1.5D);
      if(ent != null && ent instanceof EntityLiving && !(ent instanceof EntityPlayer)) {
         EntityLiving critter = (EntityLiving)ent;
         if(critter.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && !(critter instanceof INpc) && !(critter instanceof IMerchant) && (critter.isCreatureType(EnumCreatureType.creature, false) || critter.isCreatureType(EnumCreatureType.ambient, false) || critter.isCreatureType(EnumCreatureType.waterCreature, false))) {
            ItemStack bloodSample = new ItemStack(ThaumicHorizons.itemSyringeBloodSample);
            bloodSample.stackTagCompound = new NBTTagCompound();
            NBTTagCompound critterTag = new NBTTagCompound();
            critter.writeToNBT(critterTag);
            critterTag.setString("id", EntityList.getEntityString(ent));
            bloodSample.stackTagCompound.setString("critterName", ent.getCommandSenderName());
            bloodSample.stackTagCompound.setTag("critter", critterTag);
            if(p.inventory.addItemStackToInventory(bloodSample)) {
               --p_77659_1_.stackSize;
            }
         }
      } else {
         ItemStack result = new ItemStack(ThaumicHorizons.itemSyringeHuman);
         if(p.inventory.addItemStackToInventory(result)) {
            --p_77659_1_.stackSize;
         }
      }

      return p_77659_1_;
   }
}
