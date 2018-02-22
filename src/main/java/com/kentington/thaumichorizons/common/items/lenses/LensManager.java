package com.kentington.thaumichorizons.common.items.lenses;

import baubles.api.BaublesApi;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.lenses.ILens;
import com.kentington.thaumichorizons.common.items.lenses.ItemLensCase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.nodes.IRevealer;

public class LensManager {

   public static long nightVisionOffTime = 0L;


   public static void changeLens(ItemStack is, World w, EntityPlayer player, String lens) {
      IRevealer goggles = (IRevealer)is.getItem();
      TreeMap lenses = new TreeMap();
      HashMap pouches = new HashMap();
      int pouchcount = 0;
      ItemStack item = null;
      IInventory baubles = BaublesApi.getBaubles(player);

      int oldLens;
      ItemStack[] currentLens;
      int currentLens1;
      for(oldLens = 0; oldLens < 4; ++oldLens) {
         if(baubles.getStackInSlot(oldLens) != null && baubles.getStackInSlot(oldLens).getItem() instanceof ItemLensCase) {
            ++pouchcount;
            item = baubles.getStackInSlot(oldLens);
            pouches.put(Integer.valueOf(pouchcount), Integer.valueOf(oldLens - 4));
            currentLens = ((ItemLensCase)item.getItem()).getInventory(item);

            for(currentLens1 = 0; currentLens1 < currentLens.length; ++currentLens1) {
               item = currentLens[currentLens1];
               if(item != null && item.getItem() instanceof ILens) {
                  lenses.put(((ILens)item.getItem()).lensName(), Integer.valueOf(currentLens1 + pouchcount * 1000));
               }
            }
         }
      }

      int pouchslot;
      for(oldLens = 0; oldLens < 36; ++oldLens) {
         item = player.inventory.mainInventory[oldLens];
         if(item != null && item.getItem() instanceof ILens) {
            lenses.put(((ILens)item.getItem()).lensName(), Integer.valueOf(oldLens));
         }

         if(item != null && item.getItem() instanceof ItemLensCase) {
            ++pouchcount;
            pouches.put(Integer.valueOf(pouchcount), Integer.valueOf(oldLens));
            currentLens = ((ItemLensCase)item.getItem()).getInventory(item);

            for(pouchslot = 0; pouchslot < currentLens.length; ++pouchslot) {
               item = currentLens[pouchslot];
               if(item != null && item.getItem() instanceof ILens) {
                  lenses.put(((ILens)item.getItem()).lensName(), Integer.valueOf(pouchslot + pouchcount * 1000));
               }
            }
         }
      }

      ItemStack var16 = null;
      String var17;
      if(!lens.equals("REMOVE") && lenses.size() != 0) {
         if(lenses != null && lenses.size() > 0 && lens != null) {
            var17 = lens;
            if(lenses.get(lens) == null) {
               var17 = (String)lenses.higherKey(lens);
            }

            if(var17 == null || lenses.get(var17) == null) {
               var17 = (String)lenses.firstKey();
            }

            if(((Integer)lenses.get(var17)).intValue() < 1000) {
               item = player.inventory.mainInventory[((Integer)lenses.get(var17)).intValue()].copy();
            } else {
               currentLens1 = ((Integer)lenses.get(var17)).intValue() / 1000;
               if(pouches.containsKey(Integer.valueOf(currentLens1))) {
                  pouchslot = ((Integer)pouches.get(Integer.valueOf(currentLens1))).intValue();
                  int lensSlot = ((Integer)lenses.get(var17)).intValue() - currentLens1 * 1000;
                  ItemStack tmp;
                  if(pouchslot >= 0) {
                     tmp = player.inventory.mainInventory[pouchslot].copy();
                  } else {
                     tmp = baubles.getStackInSlot(pouchslot + 4).copy();
                  }

                  item = fetchLensFromPouch(player, lensSlot, tmp, pouchslot);
               }
            }

            if(item == null) {
               return;
            }

            if(((Integer)lenses.get(var17)).intValue() < 1000) {
               player.inventory.setInventorySlotContents(((Integer)lenses.get(var17)).intValue(), (ItemStack)null);
            }

            w.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3F, 1.0F);
            String var18 = "";
            if(is.stackTagCompound != null) {
               var18 = is.stackTagCompound.getString("Lens");
            }

            var16 = getLensItem(var18);
            if(!var18.equals("") && (addLensToPouch(player, var16, pouches) || player.inventory.addItemStackToInventory(var16))) {
               setLensItem(is, item);
            } else if(var18.equals("")) {
               setLensItem(is, item);
            } else if(!addLensToPouch(player, item, pouches)) {
               player.inventory.addItemStackToInventory(item);
            }
         }
      } else {
         var17 = "";
         if(is.stackTagCompound != null) {
            var17 = is.stackTagCompound.getString("Lens");
         }

         var16 = getLensItem(var17);
         if(!var17.equals("") && (addLensToPouch(player, var16, pouches) || player.inventory.addItemStackToInventory(var16))) {
            setLensItem(is, (ItemStack)null);
            w.playSoundAtEntity(player, "thaumcraft:cameraticks", 0.3F, 0.9F);
         }
      }

      if(var16 != null) {
         ((ILens)var16.getItem()).handleRemoval(player);
      }

   }

   private static ItemStack fetchLensFromPouch(EntityPlayer player, int lensid, ItemStack pouch, int pouchslot) {
      ItemStack lens = null;
      ItemStack[] inv = ((ItemLensCase)pouch.getItem()).getInventory(pouch);
      ItemStack contents = inv[lensid];
      if(contents != null && contents.getItem() instanceof ILens) {
         lens = contents.copy();
         inv[lensid] = null;
         ((ItemLensCase)pouch.getItem()).setInventory(pouch, inv);
         if(pouchslot >= 0) {
            player.inventory.setInventorySlotContents(pouchslot, pouch);
            player.inventory.markDirty();
         } else {
            IInventory baubles = BaublesApi.getBaubles(player);
            baubles.setInventorySlotContents(pouchslot + 4, pouch);
            baubles.markDirty();
         }
      }

      return lens;
   }

   private static boolean addLensToPouch(EntityPlayer player, ItemStack lens, HashMap pouches) {
      Iterator i$ = pouches.values().iterator();

      while(i$.hasNext()) {
         IInventory baubles = BaublesApi.getBaubles(player);
         Integer pouchslot = (Integer)i$.next();
         ItemStack pouch;
         if(pouchslot.intValue() >= 0) {
            pouch = player.inventory.mainInventory[pouchslot.intValue()];
         } else {
            pouch = baubles.getStackInSlot(pouchslot.intValue() + 4);
         }

         ItemStack[] inv = ((ItemLensCase)pouch.getItem()).getInventory(pouch);

         for(int q = 0; q < inv.length; ++q) {
            ItemStack contents = inv[q];
            if(contents == null) {
               inv[q] = lens.copy();
               ((ItemLensCase)pouch.getItem()).setInventory(pouch, inv);
               if(pouchslot.intValue() >= 0) {
                  player.inventory.setInventorySlotContents(pouchslot.intValue(), pouch);
                  player.inventory.markDirty();
               } else {
                  baubles.setInventorySlotContents(pouchslot.intValue() + 4, pouch);
                  baubles.markDirty();
               }

               player.inventory.markDirty();
               return true;
            }
         }
      }

      return false;
   }

   public static ItemStack getLensItem(String lens) {
      return getLens(lens) != null?new ItemStack(getLens(lens)):null;
   }

   public static Item getLens(String lens) {
      return lens.equals("LensFire")?ThaumicHorizons.itemLensFire:(lens.equals("LensWater")?ThaumicHorizons.itemLensWater:(lens.equals("LensEarth")?ThaumicHorizons.itemLensEarth:(lens.equals("LensAir")?ThaumicHorizons.itemLensAir:(lens.equals("LensOrderEntropy")?ThaumicHorizons.itemLensOrderEntropy:null))));
   }

   public static void setLensItem(ItemStack goggles, ItemStack lens) {
      if(!goggles.hasTagCompound()) {
         goggles.stackTagCompound = new NBTTagCompound();
      }

      int lensIndex = goggles.stackTagCompound.getInteger("LensIndex");
      NBTTagList lore = null;
      if(goggles.stackTagCompound != null && goggles.stackTagCompound.getCompoundTag("display") != null) {
         lore = goggles.stackTagCompound.getCompoundTag("display").getTagList("Lore", 8);
      }

      if(lore == null || lore.tagCount() == 0) {
         if(goggles.stackTagCompound == null) {
            goggles.stackTagCompound = new NBTTagCompound();
         }

         if(goggles.stackTagCompound.getCompoundTag("display").hasNoTags()) {
            goggles.stackTagCompound.setTag("display", new NBTTagCompound());
         }

         if(goggles.stackTagCompound.getCompoundTag("display").getTagList("Lore", 8).tagCount() == 0) {
            goggles.stackTagCompound.getCompoundTag("display").setTag("Lore", new NBTTagList());
         }

         lore = goggles.stackTagCompound.getCompoundTag("display").getTagList("Lore", 8);
         lensIndex = 0;
      }

      if(lens == null) {
         goggles.stackTagCompound.removeTag("Lens");
         if(lensIndex >= 0 && lore.tagCount() > lensIndex) {
            lore.removeTag(lensIndex);
         }

         goggles.stackTagCompound.setInteger("LensIndex", -1);
      } else {
         goggles.stackTagCompound.removeTag("Lens");
         goggles.stackTagCompound.setString("Lens", ((ILens)lens.getItem()).lensName());
         if(lensIndex != -1 && lore.tagCount() > lensIndex) {
            lore.removeTag(lensIndex);
         }

         goggles.stackTagCompound.setInteger("LensIndex", lore.tagCount());
         lore.appendTag(new NBTTagString(StatCollector.translateToLocal("item." + ((ILens)lens.getItem()).lensName() + ".name")));
      }

   }

}
