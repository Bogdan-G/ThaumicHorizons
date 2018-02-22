package com.kentington.thaumichorizons.common.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import thaumcraft.api.IRunicArmor;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileMirror;

public class ItemAmuletMirror extends Item implements IBauble, IRunicArmor {

   public IIcon icon;


   public ItemAmuletMirror() {
      super.maxStackSize = 1;
      super.canRepair = false;
      this.setMaxDamage(0);
      this.setCreativeTab(ThaumicHorizons.tabTH);
      this.setHasSubtypes(true);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:amuletmirror");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.amuletMirror";
   }

   public EnumRarity getRarity(ItemStack itemstack) {
      return EnumRarity.rare;
   }

   public BaubleType getBaubleType(ItemStack itemstack) {
      return BaubleType.AMULET;
   }

   public int getRunicCharge(ItemStack itemstack) {
      return 0;
   }

   public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
      return true;
   }

   public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
      return true;
   }

   public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {}

   public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {}

   public void onWornTick(ItemStack arg0, EntityLivingBase arg1) {}

   public boolean getShareTag() {
      return true;
   }

   public boolean hasEffect(ItemStack par1ItemStack) {
      return par1ItemStack.hasTagCompound();
   }

   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
      Block bi = world.getBlock(x, y, z);
      if(bi == ConfigBlocks.blockMirror) {
         if(world.isRemote) {
            player.swingItem();
            return super.onItemUseFirst(itemstack, player, world, x, y, z, par7, par8, par9, par10);
         } else {
            TileEntity tm = world.getTileEntity(x, y, z);
            if(tm != null && tm instanceof TileMirror) {
               itemstack.setTagInfo("linkX", new NBTTagInt(tm.xCoord));
               itemstack.setTagInfo("linkY", new NBTTagInt(tm.yCoord));
               itemstack.setTagInfo("linkZ", new NBTTagInt(tm.zCoord));
               itemstack.setTagInfo("linkDim", new NBTTagInt(world.provider.dimensionId));
               itemstack.setTagInfo("dimname", new NBTTagString(DimensionManager.getProvider(world.provider.dimensionId).getDimensionName()));
               world.playSoundEffect((double)x, (double)y, (double)z, "thaumcraft:jar", 1.0F, 2.0F);
               player.addChatMessage(new ChatComponentText("ï¿½5ï¿½o" + StatCollector.translateToLocal("tc.handmirrorlinked")));
               player.inventoryContainer.detectAndSendChanges();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List list, boolean par4) {
      if(item.hasTagCompound()) {
         int lx = item.stackTagCompound.getInteger("linkX");
         int ly = item.stackTagCompound.getInteger("linkY");
         int lz = item.stackTagCompound.getInteger("linkZ");
         int ldim = item.stackTagCompound.getInteger("linkDim");
         String dimname = item.stackTagCompound.getString("dimname");
         list.add(StatCollector.translateToLocal("tc.handmirrorlinkedto") + " " + lx + "," + ly + "," + lz + " in " + dimname);
      }

   }
}
