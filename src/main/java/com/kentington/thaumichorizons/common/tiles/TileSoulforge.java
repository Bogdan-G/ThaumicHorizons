package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.ISoulReceiver;
import cpw.mods.fml.common.registry.VillagerRegistry;
import java.util.Iterator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.utils.InventoryUtils;

public class TileSoulforge extends TileThaumcraft implements ISoulReceiver, IEssentiaTransport, IAspectContainer {

   int progress = 0;
   static final int PROGRESS_MAX = 9600;
   public int souls = 0;
   int essentia = 0;
   static final int ESSENTIA_MAX = 4000;
   public float rota;
   public int forging = 3;


   public boolean activate(World world, EntityPlayer player) {
      if(player.getHeldItem() != null && player.getHeldItem().getItemDamage() == 0 && player.getHeldItem().getItem() == Item.getItemFromBlock(ConfigBlocks.blockJar)) {
         if(this.souls <= 0) {
            return false;
         } else {
            ItemStack soul = new ItemStack(ThaumicHorizons.blockJar);
            soul.setTagCompound(new NBTTagCompound());
            soul.getTagCompound().setBoolean("isSoul", true);
            Integer[] newVillagerTypes = new Integer[VillagerRegistry.getRegisteredVillagers().size()];
            int pointer = 0;

            for(Iterator it = VillagerRegistry.getRegisteredVillagers().iterator(); it.hasNext(); ++pointer) {
               newVillagerTypes[pointer] = (Integer)it.next();
            }

            Integer[] villagerTypes = new Integer[newVillagerTypes.length + 5];

            int which;
            for(which = 0; which < 5; ++which) {
               villagerTypes[which] = Integer.valueOf(which);
            }

            for(which = 0; which < newVillagerTypes.length; ++which) {
               villagerTypes[which + 5] = newVillagerTypes[which];
            }

            which = world.rand.nextInt(villagerTypes.length);
            soul.getTagCompound().setInteger("villagerType", villagerTypes[which].intValue());
            EntityVillager dummyVillager = new EntityVillager(super.worldObj);
            dummyVillager.setProfession(villagerTypes[which].intValue());
            soul.getTagCompound().setString("jarredCritterName", dummyVillager.getCommandSenderName());
            player.inventory.decrStackSize(InventoryUtils.isPlayerCarrying(player, new ItemStack(ConfigBlocks.blockJar, 1, 0)), 1);
            if(!player.inventory.addItemStackToInventory(soul)) {
               player.entityDropItem(soul, 1.0F);
            }

            --this.souls;
            return true;
         }
      } else {
         return false;
      }
   }

   public void addSoulBits(int bits) {
      this.progress += bits;
      this.essentia -= bits;
      if(this.progress >= 9600) {
         this.progress -= 9600;
         ++this.souls;
      }

      this.forging = 3;
      this.markDirty();
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
   }

   public boolean canAcceptSouls() {
      return this.essentia > 0 && this.souls < 16;
   }

   public void updateEntity() {
      super.updateEntity();
      if(this.essentia < 3000) {
         this.drawEssentia();
      }

      if(this.forging > 0) {
         ++this.rota;
         if(this.rota > 360.0F) {
            this.rota -= 360.0F;
         }

         --this.forging;
      }

   }

   public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
      return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) != this?false:p_70300_1_.getDistanceSq((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D) <= 64.0D;
   }

   void drawEssentia() {
      ForgeDirection dir = ForgeDirection.UP;
      TileEntity te = ThaumcraftApiHelper.getConnectableTile(super.worldObj, super.xCoord, super.yCoord, super.zCoord, dir);
      if(te != null) {
         ForgeDirection opposite = ForgeDirection.DOWN;
         IEssentiaTransport ic = (IEssentiaTransport)te;
         if(!ic.canOutputTo(opposite)) {
            return;
         }

         Aspect ta = null;
         if(ic.getEssentiaAmount(opposite) > 0 && ic.getSuctionAmount(opposite) < this.getSuctionAmount(dir) && this.getSuctionAmount(dir) >= ic.getMinimumSuction()) {
            ta = ic.getEssentiaType(opposite);
         }

         if(ta != null && ta.getTag().equals(Aspect.MIND.getTag()) && ic.takeEssentia(ta, 1, opposite) == 1) {
            this.addEssentia(ta, 1, dir);
            return;
         }
      }

   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("souls", this.souls);
      nbttagcompound.setInteger("progress", this.progress);
      nbttagcompound.setInteger("essentia", this.essentia);
      nbttagcompound.setInteger("forging", this.forging);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      this.souls = nbttagcompound.getInteger("souls");
      this.progress = nbttagcompound.getInteger("progress");
      this.essentia = nbttagcompound.getInteger("essentia");
      this.forging = nbttagcompound.getInteger("forging");
   }

   public AspectList getAspects() {
      return this.essentia <= 0?null:(new AspectList()).add(Aspect.MIND, (int)Math.ceil((double)((float)this.essentia / 1000.0F)));
   }

   public void setAspects(AspectList aspects) {
      this.essentia = aspects.getAmount(Aspect.MIND) * 1000;
   }

   public boolean doesContainerAccept(Aspect tag) {
      return false;
   }

   public int addToContainer(Aspect tag, int amount) {
      return 0;
   }

   public boolean takeFromContainer(Aspect tag, int amount) {
      return false;
   }

   public boolean takeFromContainer(AspectList ot) {
      return false;
   }

   public boolean doesContainerContainAmount(Aspect tag, int amount) {
      return tag.getTag().equals(Aspect.MIND.getTag()) && this.essentia / 1000 >= amount;
   }

   public boolean doesContainerContain(AspectList ot) {
      return false;
   }

   public int containerContains(Aspect tag) {
      return tag.getTag().equals(Aspect.MIND.getTag())?this.essentia / 1000:0;
   }

   public boolean isConnectable(ForgeDirection face) {
      return face == ForgeDirection.UP;
   }

   public boolean canInputFrom(ForgeDirection face) {
      return face == ForgeDirection.UP;
   }

   public boolean canOutputTo(ForgeDirection face) {
      return false;
   }

   public void setSuction(Aspect aspect, int amount) {}

   public Aspect getSuctionType(ForgeDirection face) {
      return Aspect.MIND;
   }

   public int getSuctionAmount(ForgeDirection face) {
      return this.essentia < 3000?128:0;
   }

   public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
      return 0;
   }

   public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
      if(this.essentia < 3000) {
         this.essentia += 1000;
         this.markDirty();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         return 1;
      } else {
         return 0;
      }
   }

   public Aspect getEssentiaType(ForgeDirection face) {
      return Aspect.MIND;
   }

   public int getEssentiaAmount(ForgeDirection face) {
      return this.essentia / 1000;
   }

   public int getMinimumSuction() {
      return 0;
   }

   public boolean renderExtendedTube() {
      return false;
   }
}
