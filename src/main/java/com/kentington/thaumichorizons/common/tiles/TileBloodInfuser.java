package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.config.Config;

public class TileBloodInfuser extends TileThaumcraft implements IAspectContainer, IEssentiaTransport, ISidedInventory {

   public AspectList aspectsSelected = new AspectList();
   public AspectList aspectsAcquired = new AspectList();
   Aspect currentlySucking = null;
   public int mode = 0;
   public ItemStack syringe = null;
   public ItemStack[] output = new ItemStack[9];
   private HashMap effectWeights = new HashMap();
   private HashMap duration = new HashMap();
   private Color color;


   public TileBloodInfuser() {
      int speed = Potion.moveSpeed.id;
      int slow = Potion.moveSlowdown.id;
      int haste = Potion.digSpeed.id;
      int fatigue = Potion.digSlowdown.id;
      int strength = Potion.damageBoost.id;
      int health = Potion.heal.id;
      int harm = Potion.harm.id;
      int jump = Potion.jump.id;
      byte nausea = 9;
      int regen = Potion.regeneration.id;
      int resist = Potion.resistance.id;
      int fireres = Potion.fireResistance.id;
      int water = Potion.waterBreathing.id;
      int invis = Potion.invisibility.id;
      int blind = Potion.blindness.id;
      int night = Potion.nightVision.id;
      int hunger = Potion.hunger.id;
      int weak = Potion.weakness.id;
      int poison = Potion.poison.id;
      int wither = Potion.wither.id;
      byte hboost = 21;
      byte satur = 23;
      int taint = Config.potionTaintPoisonID;
      int visBoost = Config.potionVisExhaustID;
      int visRegen = ThaumicHorizons.potionVisRegenID;
      int vacuum = ThaumicHorizons.potionVacuumID;
      int shock = ThaumicHorizons.potionShockID;
      int synth = ThaumicHorizons.potionSynthesisID;
      this.duration.put(Integer.valueOf(speed), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(slow), Float.valueOf(0.8F));
      this.duration.put(Integer.valueOf(haste), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(fatigue), Float.valueOf(0.8F));
      this.duration.put(Integer.valueOf(strength), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(jump), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(nausea), Float.valueOf(0.2F));
      this.duration.put(Integer.valueOf(regen), Float.valueOf(0.6F));
      this.duration.put(Integer.valueOf(resist), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(fireres), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(water), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(invis), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(blind), Float.valueOf(0.2F));
      this.duration.put(Integer.valueOf(night), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(hunger), Float.valueOf(0.4F));
      this.duration.put(Integer.valueOf(weak), Float.valueOf(0.8F));
      this.duration.put(Integer.valueOf(poison), Float.valueOf(0.6F));
      this.duration.put(Integer.valueOf(wither), Float.valueOf(0.4F));
      this.duration.put(Integer.valueOf(hboost), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(satur), Float.valueOf(0.4F));
      this.duration.put(Integer.valueOf(taint), Float.valueOf(0.6F));
      this.duration.put(Integer.valueOf(visBoost), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(visRegen), Float.valueOf(0.6F));
      this.duration.put(Integer.valueOf(vacuum), Float.valueOf(1.2F));
      this.duration.put(Integer.valueOf(shock), Float.valueOf(0.8F));
      this.duration.put(Integer.valueOf(synth), Float.valueOf(1.5F));
      HashMap map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(3));
      map.put(Integer.valueOf(jump), Integer.valueOf(4));
      map.put(Integer.valueOf(water), Integer.valueOf(3));
      this.effectWeights.put(Aspect.AIR, map);
      map = new HashMap();
      map.put(Integer.valueOf(slow), Integer.valueOf(3));
      map.put(Integer.valueOf(haste), Integer.valueOf(4));
      map.put(Integer.valueOf(strength), Integer.valueOf(1));
      map.put(Integer.valueOf(resist), Integer.valueOf(2));
      this.effectWeights.put(Aspect.EARTH, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(1));
      map.put(Integer.valueOf(strength), Integer.valueOf(2));
      map.put(Integer.valueOf(fireres), Integer.valueOf(6));
      map.put(Integer.valueOf(night), Integer.valueOf(1));
      this.effectWeights.put(Aspect.FIRE, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(2));
      map.put(Integer.valueOf(haste), Integer.valueOf(2));
      map.put(Integer.valueOf(regen), Integer.valueOf(1));
      map.put(Integer.valueOf(fireres), Integer.valueOf(2));
      map.put(Integer.valueOf(water), Integer.valueOf(3));
      this.effectWeights.put(Aspect.WATER, map);
      map = new HashMap();
      map.put(Integer.valueOf(slow), Integer.valueOf(2));
      map.put(Integer.valueOf(fatigue), Integer.valueOf(3));
      map.put(Integer.valueOf(resist), Integer.valueOf(3));
      map.put(Integer.valueOf(hboost), Integer.valueOf(2));
      this.effectWeights.put(Aspect.ORDER, map);
      map = new HashMap();
      map.put(Integer.valueOf(harm), Integer.valueOf(1));
      map.put(Integer.valueOf(nausea), Integer.valueOf(1));
      map.put(Integer.valueOf(hunger), Integer.valueOf(1));
      map.put(Integer.valueOf(weak), Integer.valueOf(2));
      map.put(Integer.valueOf(poison), Integer.valueOf(2));
      map.put(Integer.valueOf(wither), Integer.valueOf(3));
      this.effectWeights.put(Aspect.ENTROPY, map);
      map = new HashMap();
      map.put(Integer.valueOf(invis), Integer.valueOf(4));
      map.put(Integer.valueOf(hunger), Integer.valueOf(2));
      map.put(Integer.valueOf(vacuum), Integer.valueOf(4));
      this.effectWeights.put(Aspect.VOID, map);
      map = new HashMap();
      map.put(Integer.valueOf(blind), Integer.valueOf(2));
      map.put(Integer.valueOf(night), Integer.valueOf(8));
      this.effectWeights.put(Aspect.LIGHT, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(1));
      map.put(Integer.valueOf(jump), Integer.valueOf(2));
      map.put(Integer.valueOf(blind), Integer.valueOf(1));
      map.put(Integer.valueOf(shock), Integer.valueOf(8));
      this.effectWeights.put(Aspect.WEATHER, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(5));
      map.put(Integer.valueOf(jump), Integer.valueOf(5));
      this.effectWeights.put(Aspect.MOTION, map);
      map = new HashMap();
      map.put(Integer.valueOf(slow), Integer.valueOf(2));
      map.put(Integer.valueOf(fatigue), Integer.valueOf(2));
      map.put(Integer.valueOf(resist), Integer.valueOf(2));
      map.put(Integer.valueOf(invis), Integer.valueOf(2));
      map.put(Integer.valueOf(weak), Integer.valueOf(2));
      this.effectWeights.put(Aspect.COLD, map);
      map = new HashMap();
      map.put(Integer.valueOf(fatigue), Integer.valueOf(2));
      map.put(Integer.valueOf(strength), Integer.valueOf(2));
      map.put(Integer.valueOf(invis), Integer.valueOf(6));
      this.effectWeights.put(Aspect.CRYSTAL, map);
      map = new HashMap();
      map.put(Integer.valueOf(health), Integer.valueOf(2));
      map.put(Integer.valueOf(regen), Integer.valueOf(6));
      map.put(Integer.valueOf(hboost), Integer.valueOf(2));
      this.effectWeights.put(Aspect.LIFE, map);
      map = new HashMap();
      map.put(Integer.valueOf(fatigue), Integer.valueOf(1));
      map.put(Integer.valueOf(nausea), Integer.valueOf(1));
      map.put(Integer.valueOf(hunger), Integer.valueOf(1));
      map.put(Integer.valueOf(poison), Integer.valueOf(6));
      map.put(Integer.valueOf(weak), Integer.valueOf(1));
      this.effectWeights.put(Aspect.POISON, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(2));
      map.put(Integer.valueOf(haste), Integer.valueOf(2));
      map.put(Integer.valueOf(strength), Integer.valueOf(1));
      map.put(Integer.valueOf(jump), Integer.valueOf(1));
      map.put(Integer.valueOf(visRegen), Integer.valueOf(1));
      map.put(Integer.valueOf(shock), Integer.valueOf(3));
      this.effectWeights.put(Aspect.ENERGY, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(2));
      map.put(Integer.valueOf(haste), Integer.valueOf(2));
      map.put(Integer.valueOf(jump), Integer.valueOf(1));
      map.put(Integer.valueOf(weak), Integer.valueOf(2));
      map.put(Integer.valueOf(vacuum), Integer.valueOf(3));
      this.effectWeights.put(Aspect.EXCHANGE, map);
      map = new HashMap();
      map.put(Integer.valueOf(slow), Integer.valueOf(2));
      map.put(Integer.valueOf(haste), Integer.valueOf(2));
      map.put(Integer.valueOf(strength), Integer.valueOf(2));
      map.put(Integer.valueOf(resist), Integer.valueOf(4));
      this.effectWeights.put(Aspect.METAL, map);
      map = new HashMap();
      map.put(Integer.valueOf(harm), Integer.valueOf(6));
      map.put(Integer.valueOf(nausea), Integer.valueOf(1));
      map.put(Integer.valueOf(hunger), Integer.valueOf(1));
      map.put(Integer.valueOf(weak), Integer.valueOf(1));
      map.put(Integer.valueOf(wither), Integer.valueOf(1));
      this.effectWeights.put(Aspect.DEATH, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(2));
      map.put(Integer.valueOf(jump), Integer.valueOf(8));
      this.effectWeights.put(Aspect.FLIGHT, map);
      map = new HashMap();
      map.put(Integer.valueOf(invis), Integer.valueOf(4));
      map.put(Integer.valueOf(blind), Integer.valueOf(5));
      map.put(Integer.valueOf(night), Integer.valueOf(1));
      this.effectWeights.put(Aspect.DARKNESS, map);
      map = new HashMap();
      map.put(Integer.valueOf(health), Integer.valueOf(2));
      map.put(Integer.valueOf(regen), Integer.valueOf(1));
      map.put(Integer.valueOf(invis), Integer.valueOf(3));
      map.put(Integer.valueOf(night), Integer.valueOf(2));
      map.put(Integer.valueOf(hboost), Integer.valueOf(1));
      map.put(Integer.valueOf(visRegen), Integer.valueOf(1));
      this.effectWeights.put(Aspect.SOUL, map);
      map = new HashMap();
      map.put(Integer.valueOf(health), Integer.valueOf(6));
      map.put(Integer.valueOf(regen), Integer.valueOf(2));
      map.put(Integer.valueOf(hboost), Integer.valueOf(2));
      this.effectWeights.put(Aspect.HEAL, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(8));
      map.put(Integer.valueOf(jump), Integer.valueOf(2));
      this.effectWeights.put(Aspect.TRAVEL, map);
      map = new HashMap();
      map.put(Integer.valueOf(invis), Integer.valueOf(2));
      map.put(Integer.valueOf(blind), Integer.valueOf(2));
      map.put(Integer.valueOf(night), Integer.valueOf(2));
      map.put(Integer.valueOf(visBoost), Integer.valueOf(2));
      map.put(Integer.valueOf(visRegen), Integer.valueOf(2));
      this.effectWeights.put(Aspect.ELDRITCH, map);
      map = new HashMap();
      map.put(Integer.valueOf(visBoost), Integer.valueOf(5));
      map.put(Integer.valueOf(visRegen), Integer.valueOf(5));
      this.effectWeights.put(Aspect.MAGIC, map);
      map = new HashMap();
      map.put(Integer.valueOf(visRegen), Integer.valueOf(10));
      this.effectWeights.put(Aspect.AURA, map);
      map = new HashMap();
      map.put(Integer.valueOf(visBoost), Integer.valueOf(2));
      map.put(Integer.valueOf(wither), Integer.valueOf(2));
      map.put(Integer.valueOf(taint), Integer.valueOf(6));
      this.effectWeights.put(Aspect.TAINT, map);
      map = new HashMap();
      map.put(Integer.valueOf(slow), Integer.valueOf(4));
      map.put(Integer.valueOf(fatigue), Integer.valueOf(2));
      map.put(Integer.valueOf(nausea), Integer.valueOf(2));
      map.put(Integer.valueOf(poison), Integer.valueOf(2));
      this.effectWeights.put(Aspect.SLIME, map);
      map = new HashMap();
      map.put(Integer.valueOf(satur), Integer.valueOf(2));
      map.put(Integer.valueOf(synth), Integer.valueOf(8));
      this.effectWeights.put(Aspect.PLANT, map);
      map = new HashMap();
      map.put(Integer.valueOf(slow), Integer.valueOf(2));
      map.put(Integer.valueOf(resist), Integer.valueOf(2));
      map.put(Integer.valueOf(synth), Integer.valueOf(6));
      this.effectWeights.put(Aspect.TREE, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(1));
      map.put(Integer.valueOf(strength), Integer.valueOf(5));
      map.put(Integer.valueOf(regen), Integer.valueOf(1));
      map.put(Integer.valueOf(water), Integer.valueOf(1));
      map.put(Integer.valueOf(night), Integer.valueOf(1));
      map.put(Integer.valueOf(hboost), Integer.valueOf(1));
      this.effectWeights.put(Aspect.BEAST, map);
      map = new HashMap();
      map.put(Integer.valueOf(regen), Integer.valueOf(2));
      map.put(Integer.valueOf(hboost), Integer.valueOf(2));
      map.put(Integer.valueOf(satur), Integer.valueOf(6));
      this.effectWeights.put(Aspect.FLESH, map);
      map = new HashMap();
      map.put(Integer.valueOf(hunger), Integer.valueOf(2));
      map.put(Integer.valueOf(wither), Integer.valueOf(2));
      map.put(Integer.valueOf(harm), Integer.valueOf(6));
      this.effectWeights.put(Aspect.UNDEAD, map);
      map = new HashMap();
      map.put(Integer.valueOf(night), Integer.valueOf(2));
      map.put(Integer.valueOf(visBoost), Integer.valueOf(3));
      map.put(Integer.valueOf(visRegen), Integer.valueOf(3));
      map.put(Integer.valueOf(vacuum), Integer.valueOf(2));
      this.effectWeights.put(Aspect.MIND, map);
      map = new HashMap();
      map.put(Integer.valueOf(invis), Integer.valueOf(4));
      map.put(Integer.valueOf(night), Integer.valueOf(6));
      this.effectWeights.put(Aspect.SENSES, map);
      map = new HashMap();
      map.put(Integer.valueOf(health), Integer.valueOf(3));
      map.put(Integer.valueOf(regen), Integer.valueOf(3));
      map.put(Integer.valueOf(hboost), Integer.valueOf(4));
      this.effectWeights.put(Aspect.MAN, map);
      map = new HashMap();
      map.put(Integer.valueOf(satur), Integer.valueOf(6));
      map.put(Integer.valueOf(synth), Integer.valueOf(4));
      this.effectWeights.put(Aspect.CROP, map);
      map = new HashMap();
      map.put(Integer.valueOf(haste), Integer.valueOf(10));
      this.effectWeights.put(Aspect.MINE, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(1));
      map.put(Integer.valueOf(haste), Integer.valueOf(8));
      map.put(Integer.valueOf(resist), Integer.valueOf(1));
      this.effectWeights.put(Aspect.TOOL, map);
      map = new HashMap();
      map.put(Integer.valueOf(haste), Integer.valueOf(2));
      map.put(Integer.valueOf(health), Integer.valueOf(1));
      map.put(Integer.valueOf(satur), Integer.valueOf(5));
      map.put(Integer.valueOf(synth), Integer.valueOf(2));
      this.effectWeights.put(Aspect.HARVEST, map);
      map = new HashMap();
      map.put(Integer.valueOf(haste), Integer.valueOf(2));
      map.put(Integer.valueOf(strength), Integer.valueOf(8));
      this.effectWeights.put(Aspect.WEAPON, map);
      map = new HashMap();
      map.put(Integer.valueOf(resist), Integer.valueOf(8));
      map.put(Integer.valueOf(fireres), Integer.valueOf(2));
      this.effectWeights.put(Aspect.ARMOR, map);
      map = new HashMap();
      map.put(Integer.valueOf(weak), Integer.valueOf(1));
      map.put(Integer.valueOf(satur), Integer.valueOf(8));
      map.put(Integer.valueOf(vacuum), Integer.valueOf(1));
      this.effectWeights.put(Aspect.HUNGER, map);
      map = new HashMap();
      map.put(Integer.valueOf(satur), Integer.valueOf(2));
      map.put(Integer.valueOf(vacuum), Integer.valueOf(8));
      this.effectWeights.put(Aspect.GREED, map);
      map = new HashMap();
      map.put(Integer.valueOf(fatigue), Integer.valueOf(2));
      map.put(Integer.valueOf(health), Integer.valueOf(2));
      map.put(Integer.valueOf(resist), Integer.valueOf(2));
      map.put(Integer.valueOf(hboost), Integer.valueOf(4));
      this.effectWeights.put(Aspect.CRAFT, map);
      map = new HashMap();
      map.put(Integer.valueOf(fatigue), Integer.valueOf(2));
      map.put(Integer.valueOf(resist), Integer.valueOf(4));
      map.put(Integer.valueOf(blind), Integer.valueOf(2));
      map.put(Integer.valueOf(weak), Integer.valueOf(2));
      this.effectWeights.put(Aspect.CLOTH, map);
      map = new HashMap();
      map.put(Integer.valueOf(speed), Integer.valueOf(2));
      map.put(Integer.valueOf(haste), Integer.valueOf(4));
      map.put(Integer.valueOf(strength), Integer.valueOf(4));
      this.effectWeights.put(Aspect.MECHANISM, map);
      map = new HashMap();
      map.put(Integer.valueOf(slow), Integer.valueOf(8));
      map.put(Integer.valueOf(fatigue), Integer.valueOf(2));
      this.effectWeights.put(Aspect.TRAP, map);
   }

   public void updateEntity() {
      super.updateEntity();
      this.currentlySucking = null;
      if(this.mode > 0 && this.syringe != null && this.syringe.stackSize > 0 && this.emptyOutputSlot()) {
         Aspect[] theInjection = this.aspectsSelected.getAspects();
         int tag = theInjection.length;

         int i;
         for(i = 0; i < tag; ++i) {
            Aspect asp = theInjection[i];
            if(this.aspectsAcquired.getAmount(asp) < this.aspectsSelected.getAmount(asp)) {
               this.currentlySucking = asp;
               break;
            }
         }

         if(this.currentlySucking == null && this.aspectsAcquired != null && (this.aspectsAcquired.size() > 0 && this.aspectsAcquired.getAspects()[0] != null || this.aspectsAcquired.size() > 1 && this.aspectsAcquired.getAspects()[1] != null)) {
            ItemStack var5 = new ItemStack(ThaumicHorizons.itemSyringeInjection);
            var5.setItemDamage(this.syringe.getItemDamage());
            this.decrStackSize(0, 1);
            NBTTagCompound var6 = new NBTTagCompound();
            var6.setTag("CustomPotionEffects", this.getCurrentEffects());
            var6.setInteger("color", this.color.getRGB());
            var5.setTagCompound(var6);

            for(i = 0; i < 9; ++i) {
               if(this.output[i] == null) {
                  this.output[i] = var5;
                  break;
               }
            }

            this.aspectsAcquired = new AspectList();
            this.markDirty();
            if(this.mode == 1) {
               this.mode = 0;
            }
         } else {
            this.tryDrawEssentia();
         }
      }

   }

   public NBTTagList getCurrentEffects() {
      NBTTagList effectList = new NBTTagList();
      if(this.aspectsSelected == null) {
         return effectList;
      } else {
         HashMap effects = new HashMap();
         int totalEssentia = 0;
         int green = 0;
         int red = 0;
         int blue = 0;
         Aspect[] i = this.aspectsSelected.getAspects();
         int largestWeight = i.length;

         for(int largestEffect = 0; largestEffect < largestWeight; ++largestEffect) {
            Aspect potTag = i[largestEffect];
            if(this.effectWeights.get(potTag) != null) {
               Iterator key = ((HashMap)this.effectWeights.get(potTag)).keySet().iterator();

               while(key.hasNext()) {
                  Integer in = (Integer)key.next();
                  if(effects.get(in) != null) {
                     effects.put(in, Integer.valueOf(((Integer)effects.get(in)).intValue() + this.aspectsSelected.getAmount(potTag) * ((Integer)((HashMap)this.effectWeights.get(potTag)).get(in)).intValue()));
                  } else {
                     effects.put(in, Integer.valueOf(this.aspectsSelected.getAmount(potTag) * ((Integer)((HashMap)this.effectWeights.get(potTag)).get(in)).intValue()));
                  }
               }

               totalEssentia += this.aspectsSelected.getAmount(potTag);
               Color var16 = new Color(potTag.getColor());
               red += var16.getRed() * this.aspectsSelected.getAmount(potTag);
               blue += var16.getBlue() * this.aspectsSelected.getAmount(potTag);
               green += var16.getGreen() * this.aspectsSelected.getAmount(potTag);
            }
         }

         if(totalEssentia > 0) {
            red /= totalEssentia;
            green /= totalEssentia;
            blue /= totalEssentia;
         }

         for(int var13 = 0; var13 < (totalEssentia + 1) / 2; ++var13) {
            Integer var14 = Integer.valueOf(0);
            Integer var15 = Integer.valueOf(0);
            Iterator var17 = effects.keySet().iterator();

            while(var17.hasNext()) {
               Integer var19 = (Integer)var17.next();
               if(((Integer)effects.get(var19)).intValue() > var14.intValue()) {
                  var14 = (Integer)effects.get(var19);
                  var15 = var19;
               }
            }

            NBTTagCompound var18 = new NBTTagCompound();
            var18.setByte("Id", (byte)var15.intValue());
            var18.setByte("Amplifier", (byte)(var14.intValue() / 30));
            if(var15.intValue() != Potion.harm.id && var15.intValue() != Potion.heal.id) {
               var18.setInteger("Duration", 100 * var14.intValue());
            } else {
               var18.setInteger("Duration", 1);
            }

            var18.setBoolean("Ambient", false);
            effectList.appendTag(var18);
            effects.remove(var15);
         }

         this.color = new Color(red, green, blue);
         return effectList;
      }
   }

   public HashMap getEffects(Aspect asp) {
      return (HashMap)this.effectWeights.get(asp);
   }

   public void setEssentiaSelected(AspectList as) {
      this.aspectsSelected = as.copy();
   }

   public boolean emptyOutputSlot() {
      for(int i = 0; i < 9; ++i) {
         if(this.output[i] == null) {
            return true;
         }
      }

      return false;
   }

   void tryDrawEssentia() {
      TileEntity te = null;
      IEssentiaTransport ic = null;
      ForgeDirection[] var3 = ForgeDirection.VALID_DIRECTIONS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ForgeDirection dir = var3[var5];
         if(dir != ForgeDirection.UP) {
            te = ThaumcraftApiHelper.getConnectableTile(super.worldObj, super.xCoord, super.yCoord, super.zCoord, dir);
            if(te != null) {
               ic = (IEssentiaTransport)te;
               if(ic.getEssentiaAmount(dir.getOpposite()) > 0 && ic.getSuctionAmount(dir.getOpposite()) < this.getSuctionAmount((ForgeDirection)null) && this.getSuctionAmount((ForgeDirection)null) >= ic.getMinimumSuction()) {
                  int ess = ic.takeEssentia(this.currentlySucking, 1, dir.getOpposite());
                  if(ess > 0) {
                     this.addToContainer(this.currentlySucking, ess);
                     return;
                  }
               }
            }
         }
      }

   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("mode", this.mode);
      if(this.currentlySucking != null) {
         nbttagcompound.setString("sucking", this.currentlySucking.getTag());
      } else {
         nbttagcompound.setString("sucking", "");
      }

      NBTTagList tlist = new NBTTagList();
      nbttagcompound.setTag("AspectsSelected", tlist);
      Aspect[] nbttaglist = this.aspectsSelected.getAspects();
      int nbttagcompound1 = nbttaglist.length;

      int i;
      Aspect aspect;
      NBTTagCompound f;
      for(i = 0; i < nbttagcompound1; ++i) {
         aspect = nbttaglist[i];
         if(aspect != null) {
            f = new NBTTagCompound();
            f.setString("key", aspect.getTag());
            f.setInteger("amount", this.aspectsSelected.getAmount(aspect));
            tlist.appendTag(f);
         }
      }

      tlist = new NBTTagList();
      nbttagcompound.setTag("AspectsAcquired", tlist);
      nbttaglist = this.aspectsAcquired.getAspects();
      nbttagcompound1 = nbttaglist.length;

      for(i = 0; i < nbttagcompound1; ++i) {
         aspect = nbttaglist[i];
         if(aspect != null) {
            f = new NBTTagCompound();
            f.setString("key", aspect.getTag());
            f.setInteger("amount", this.aspectsAcquired.getAmount(aspect));
            tlist.appendTag(f);
         }
      }

      NBTTagList var8 = new NBTTagList();
      NBTTagCompound var9 = new NBTTagCompound();
      if(this.syringe != null) {
         this.syringe.writeToNBT(var9);
      }

      var8.appendTag(var9);

      for(i = 0; i < 9; ++i) {
         var9 = new NBTTagCompound();
         if(this.output[i] != null) {
            this.output[i].writeToNBT(var9);
         }

         var8.appendTag(var9);
      }

      nbttagcompound.setTag("Items", var8);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.mode = nbttagcompound.getInteger("mode");
      this.currentlySucking = Aspect.getAspect(nbttagcompound.getString("sucking"));
      AspectList al = new AspectList();
      NBTTagList tlist = nbttagcompound.getTagList("AspectsSelected", 10);

      int nbttaglist;
      NBTTagCompound nbttagcompound1;
      for(nbttaglist = 0; nbttaglist < tlist.tagCount(); ++nbttaglist) {
         nbttagcompound1 = tlist.getCompoundTagAt(nbttaglist);
         if(nbttagcompound1.hasKey("key")) {
            al.add(Aspect.getAspect(nbttagcompound1.getString("key")), nbttagcompound1.getInteger("amount"));
         }
      }

      this.aspectsSelected = al.copy();
      al = new AspectList();
      tlist = nbttagcompound.getTagList("AspectsAcquired", 10);

      for(nbttaglist = 0; nbttaglist < tlist.tagCount(); ++nbttaglist) {
         nbttagcompound1 = tlist.getCompoundTagAt(nbttaglist);
         if(nbttagcompound1.hasKey("key")) {
            al.add(Aspect.getAspect(nbttagcompound1.getString("key")), nbttagcompound1.getInteger("amount"));
         }
      }

      this.aspectsAcquired = al.copy();
      NBTTagList var7 = nbttagcompound.getTagList("Items", 10);
      nbttagcompound1 = var7.getCompoundTagAt(0);
      this.syringe = ItemStack.loadItemStackFromNBT(nbttagcompound1);

      for(int i = 0; i < 9; ++i) {
         nbttagcompound1 = var7.getCompoundTagAt(i + 1);
         this.output[i] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
      }

   }

   public boolean isConnectable(ForgeDirection face) {
      return face != ForgeDirection.UP;
   }

   public boolean canInputFrom(ForgeDirection face) {
      return face != ForgeDirection.UP;
   }

   public boolean canOutputTo(ForgeDirection face) {
      return false;
   }

   public void setSuction(Aspect aspect, int amount) {}

   public Aspect getSuctionType(ForgeDirection face) {
      return face == ForgeDirection.UP?null:this.currentlySucking;
   }

   public int getSuctionAmount(ForgeDirection face) {
      return face == ForgeDirection.UP?0:(this.currentlySucking != null?128:0);
   }

   public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
      return 0;
   }

   public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
      return this.canInputFrom(face)?amount - this.addToContainer(aspect, amount):0;
   }

   public Aspect getEssentiaType(ForgeDirection face) {
      return null;
   }

   public int getEssentiaAmount(ForgeDirection face) {
      return 0;
   }

   public int getMinimumSuction() {
      return 0;
   }

   public boolean renderExtendedTube() {
      return false;
   }

   public AspectList getAspects() {
      return this.aspectsAcquired.getAspects().length > 0 && this.aspectsAcquired.getAspects()[0] != null?this.aspectsAcquired:null;
   }

   public void setAspects(AspectList aspects) {}

   public boolean doesContainerAccept(Aspect tag) {
      return tag.getTag().equals(this.currentlySucking.getTag());
   }

   public int addToContainer(Aspect tag, int amount) {
      this.aspectsAcquired.add(tag, amount);
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      this.markDirty();
      return 0;
   }

   public boolean takeFromContainer(Aspect tag, int amount) {
      return false;
   }

   public boolean takeFromContainer(AspectList ot) {
      return false;
   }

   public boolean doesContainerContainAmount(Aspect tag, int amount) {
      return this.aspectsAcquired.getAmount(tag) >= amount;
   }

   public boolean doesContainerContain(AspectList ot) {
      return false;
   }

   public int containerContains(Aspect tag) {
      return this.aspectsAcquired.getAmount(tag);
   }

   public int getSizeInventory() {
      return 10;
   }

   public ItemStack getStackInSlot(int slot) {
      return slot == 0?this.syringe:(slot <= 9?this.output[slot - 1]:null);
   }

   public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
      ItemStack theStack;
      if(p_70298_1_ == 0) {
         theStack = this.syringe;
      } else {
         theStack = this.output[p_70298_1_ - 1];
      }

      if(theStack != null) {
         ItemStack outStack;
         if(theStack.stackSize <= p_70298_2_) {
            if(p_70298_1_ == 0) {
               outStack = this.syringe.copy();
               this.syringe = null;
            } else {
               outStack = this.output[p_70298_1_ - 1].copy();
               this.output[p_70298_1_ - 1] = null;
            }

            return outStack;
         } else {
            outStack = theStack.splitStack(p_70298_2_);
            if(theStack.stackSize == 0) {
               if(p_70298_1_ == 0) {
                  this.syringe = null;
               } else {
                  this.output[p_70298_1_ - 1] = null;
               }
            }

            return outStack;
         }
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
      return null;
   }

   public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
      if(p_70299_1_ == 0) {
         this.syringe = p_70299_2_;
      } else if(p_70299_1_ < 10) {
         this.output[p_70299_1_ - 1] = p_70299_2_;
      }

   }

   public String getInventoryName() {
      return "container.bloodInfuser";
   }

   public boolean hasCustomInventoryName() {
      return false;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
      return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) != this?false:p_70300_1_.getDistanceSq((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D) <= 64.0D;
   }

   public void openInventory() {}

   public void closeInventory() {}

   public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
      return p_94041_1_ == 0 && p_94041_2_.isItemEqual(new ItemStack(ThaumicHorizons.itemSyringeHuman));
   }

   public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
      return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
   }

   public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
      return p_102007_1_ == 0 && p_102007_2_.isItemEqual(new ItemStack(ThaumicHorizons.itemSyringeHuman));
   }

   public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
      return p_102008_1_ > 0 && p_102008_2_ != null;
   }

   public boolean hasBlood() {
      if(this.syringe != null && this.syringe.stackSize > 0) {
         return true;
      } else {
         for(int i = 0; i < 9; ++i) {
            if(this.output[i] != null && this.output[i].stackSize > 0) {
               return true;
            }
         }

         return false;
      }
   }
}
