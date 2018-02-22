package thaumcraft.api.wands;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;

public abstract class ItemFocusBasic extends Item {

   public IIcon icon;


   public ItemFocusBasic() {
      super.maxStackSize = 1;
      super.canRepair = false;
      this.setMaxDamage(0);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public boolean isDamageable() {
      return false;
   }

   public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
      AspectList al = this.getVisCost(stack);
      if(al != null && al.size() > 0) {
         list.add(StatCollector.translateToLocal(this.isVisCostPerTick(stack)?"item.Focus.cost2":"item.Focus.cost1"));
         Aspect[] var6 = al.getAspectsSorted();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Aspect aspect = var6[var8];
            DecimalFormat myFormatter = new DecimalFormat("#####.##");
            String amount = myFormatter.format((double)((float)al.getAmount(aspect) / 100.0F));
            list.add(" §" + aspect.getChatcolor() + aspect.getName() + "§r x " + amount);
         }
      }

      this.addFocusInformation(stack, player, list, par4);
   }

   public void addFocusInformation(ItemStack focusstack, EntityPlayer player, List list, boolean par4) {
      LinkedHashMap map = new LinkedHashMap();
      short[] var6 = this.getAppliedUpgrades(focusstack);
      int id = var6.length;

      for(int var8 = 0; var8 < id; ++var8) {
         short id1 = var6[var8];
         if(id1 >= 0) {
            int amt = 1;
            if(map.containsKey(Short.valueOf(id1))) {
               amt = ((Integer)map.get(Short.valueOf(id1))).intValue() + 1;
            }

            map.put(Short.valueOf(id1), Integer.valueOf(amt));
         }
      }

      Iterator var11 = map.keySet().iterator();

      while(var11.hasNext()) {
         Short var12 = (Short)var11.next();
         list.add(EnumChatFormatting.DARK_PURPLE + FocusUpgradeType.types[var12.shortValue()].getLocalizedName() + (((Integer)map.get(var12)).intValue() > 1?" " + StatCollector.translateToLocal("enchantment.level." + map.get(var12)):""));
      }

   }

   public boolean isVisCostPerTick(ItemStack focusstack) {
      return false;
   }

   public EnumRarity getRarity(ItemStack focusstack) {
      return EnumRarity.rare;
   }

   public abstract int getFocusColor(ItemStack var1);

   public IIcon getOrnament(ItemStack focusstack) {
      return null;
   }

   public IIcon getFocusDepthLayerIcon(ItemStack focusstack) {
      return null;
   }

   public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack focusstack) {
      return ItemFocusBasic.WandFocusAnimation.WAVE;
   }

   public String getSortingHelper(ItemStack focusstack) {
      String out = "";
      short[] var3 = this.getAppliedUpgrades(focusstack);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         short id = var3[var5];
         out = out + id;
      }

      return out;
   }

   public abstract AspectList getVisCost(ItemStack var1);

   public int getActivationCooldown(ItemStack focusstack) {
      return 0;
   }

   public int getMaxAreaSize(ItemStack focusstack) {
      return 1;
   }

   public abstract FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack var1, int var2);

   public short[] getAppliedUpgrades(ItemStack focusstack) {
      short[] l = new short[]{(short)-1, (short)-1, (short)-1, (short)-1, (short)-1};
      NBTTagList nbttaglist = this.getFocusUpgradeTagList(focusstack);
      if(nbttaglist == null) {
         return l;
      } else {
         for(int j = 0; j < nbttaglist.tagCount() && j < 5; ++j) {
            l[j] = nbttaglist.getCompoundTagAt(j).getShort("id");
         }

         return l;
      }
   }

   public boolean applyUpgrade(ItemStack focusstack, FocusUpgradeType type, int rank) {
      short[] upgrades = this.getAppliedUpgrades(focusstack);
      if(upgrades[rank - 1] == -1 && rank >= 1 && rank <= 5) {
         upgrades[rank - 1] = type.id;
         this.setFocusUpgradeTagList(focusstack, upgrades);
         return true;
      } else {
         return false;
      }
   }

   public boolean canApplyUpgrade(ItemStack focusstack, EntityPlayer player, FocusUpgradeType type, int rank) {
      return true;
   }

   public boolean isUpgradedWith(ItemStack focusstack, FocusUpgradeType focusUpgradetype) {
      return this.getUpgradeLevel(focusstack, focusUpgradetype) > 0;
   }

   public int getUpgradeLevel(ItemStack focusstack, FocusUpgradeType focusUpgradetype) {
      short[] list = this.getAppliedUpgrades(focusstack);
      int level = 0;
      short[] var5 = list;
      int var6 = list.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         short id = var5[var7];
         if(id == focusUpgradetype.id) {
            ++level;
         }
      }

      return level;
   }

   public ItemStack onFocusRightClick(ItemStack wandstack, World world, EntityPlayer player, MovingObjectPosition movingobjectposition) {
      return null;
   }

   public void onUsingFocusTick(ItemStack wandstack, EntityPlayer player, int count) {}

   public void onPlayerStoppedUsingFocus(ItemStack wandstack, World world, EntityPlayer player, int count) {}

   public boolean onFocusBlockStartBreak(ItemStack wandstack, int x, int y, int z, EntityPlayer player) {
      return false;
   }

   private NBTTagList getFocusUpgradeTagList(ItemStack focusstack) {
      return focusstack.stackTagCompound == null?null:focusstack.stackTagCompound.getTagList("upgrade", 10);
   }

   private void setFocusUpgradeTagList(ItemStack focusstack, short[] upgrades) {
      if(!focusstack.hasTagCompound()) {
         focusstack.setTagCompound(new NBTTagCompound());
      }

      NBTTagCompound nbttagcompound = focusstack.getTagCompound();
      NBTTagList tlist = new NBTTagList();
      nbttagcompound.setTag("upgrade", tlist);
      short[] var5 = upgrades;
      int var6 = upgrades.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         short id = var5[var7];
         NBTTagCompound f = new NBTTagCompound();
         f.setShort("id", id);
         tlist.appendTag(f);
      }

   }

   public static enum WandFocusAnimation {

      WAVE("WAVE", 0),
      CHARGE("CHARGE", 1);
      // $FF: synthetic field
      private static final ItemFocusBasic.WandFocusAnimation[] $VALUES = new ItemFocusBasic.WandFocusAnimation[]{WAVE, CHARGE};


      private WandFocusAnimation(String var1, int var2) {}

   }
}
