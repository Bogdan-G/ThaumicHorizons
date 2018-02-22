package thaumcraft.api.aspects;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;

public class AspectList implements Serializable {

   public LinkedHashMap aspects = new LinkedHashMap();


   public AspectList(ItemStack stack) {
      try {
         AspectList e = ThaumcraftApiHelper.getObjectAspects(stack);
         if(e != null) {
            Aspect[] var3 = e.getAspects();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Aspect tag = var3[var5];
               this.add(tag, e.getAmount(tag));
            }
         }
      } catch (Exception var7) {
         ;
      }

   }

   public AspectList() {}

   public AspectList copy() {
      AspectList out = new AspectList();
      Aspect[] var2 = this.getAspects();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Aspect a = var2[var4];
         out.add(a, this.getAmount(a));
      }

      return out;
   }

   public int size() {
      return this.aspects.size();
   }

   public int visSize() {
      int q = 0;

      Aspect as;
      for(Iterator var2 = this.aspects.keySet().iterator(); var2.hasNext(); q += this.getAmount(as)) {
         as = (Aspect)var2.next();
      }

      return q;
   }

   public Aspect[] getAspects() {
      Aspect[] q = new Aspect[1];
      return (Aspect[])this.aspects.keySet().toArray(q);
   }

   public Aspect[] getPrimalAspects() {
      AspectList t = new AspectList();
      Iterator q = this.aspects.keySet().iterator();

      while(q.hasNext()) {
         Aspect as = (Aspect)q.next();
         if(as.isPrimal()) {
            t.add(as, 1);
         }
      }

      Aspect[] q1 = new Aspect[1];
      return (Aspect[])t.aspects.keySet().toArray(q1);
   }

   public Aspect[] getAspectsSorted() {
      try {
         Aspect[] e = (Aspect[])this.aspects.keySet().toArray(new Aspect[0]);
         boolean change = false;

         while(true) {
            change = false;
            int a = 0;

            while(true) {
               if(a < e.length - 1) {
                  Aspect e1 = e[a];
                  Aspect e2 = e[a + 1];
                  if(e1 == null || e2 == null || e1.getTag().compareTo(e2.getTag()) <= 0) {
                     ++a;
                     continue;
                  }

                  e[a] = e2;
                  e[a + 1] = e1;
                  change = true;
               }

               if(!change) {
                  return e;
               }
               break;
            }
         }
      } catch (Exception var6) {
         return this.getAspects();
      }
   }

   public Aspect[] getAspectsSortedAmount() {
      try {
         Aspect[] e = (Aspect[])this.aspects.keySet().toArray(new Aspect[1]);
         boolean change = false;

         while(true) {
            change = false;
            int a = 0;

            while(true) {
               if(a < e.length - 1) {
                  int e1 = this.getAmount(e[a]);
                  int e2 = this.getAmount(e[a + 1]);
                  if(e1 <= 0 || e2 <= 0 || e2 <= e1) {
                     ++a;
                     continue;
                  }

                  Aspect ea = e[a];
                  Aspect eb = e[a + 1];
                  e[a] = eb;
                  e[a + 1] = ea;
                  change = true;
               }

               if(!change) {
                  return e;
               }
               break;
            }
         }
      } catch (Exception var8) {
         return this.getAspects();
      }
   }

   public int getAmount(Aspect key) {
      return this.aspects.get(key) == null?0:((Integer)this.aspects.get(key)).intValue();
   }

   public boolean reduce(Aspect key, int amount) {
      if(this.getAmount(key) >= amount) {
         int am = this.getAmount(key) - amount;
         this.aspects.put(key, Integer.valueOf(am));
         return true;
      } else {
         return false;
      }
   }

   public AspectList remove(Aspect key, int amount) {
      int am = this.getAmount(key) - amount;
      if(am <= 0) {
         this.aspects.remove(key);
      } else {
         this.aspects.put(key, Integer.valueOf(am));
      }

      return this;
   }

   public AspectList remove(Aspect key) {
      this.aspects.remove(key);
      return this;
   }

   public AspectList add(Aspect aspect, int amount) {
      if(this.aspects.containsKey(aspect)) {
         int oldamount = ((Integer)this.aspects.get(aspect)).intValue();
         amount += oldamount;
      }

      this.aspects.put(aspect, Integer.valueOf(amount));
      return this;
   }

   public AspectList merge(Aspect aspect, int amount) {
      if(this.aspects.containsKey(aspect)) {
         int oldamount = ((Integer)this.aspects.get(aspect)).intValue();
         if(amount < oldamount) {
            amount = oldamount;
         }
      }

      this.aspects.put(aspect, Integer.valueOf(amount));
      return this;
   }

   public AspectList add(AspectList in) {
      Aspect[] var2 = in.getAspects();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Aspect a = var2[var4];
         this.add(a, in.getAmount(a));
      }

      return this;
   }

   public AspectList merge(AspectList in) {
      Aspect[] var2 = in.getAspects();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Aspect a = var2[var4];
         this.merge(a, in.getAmount(a));
      }

      return this;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.aspects.clear();
      NBTTagList tlist = nbttagcompound.getTagList("Aspects", 10);

      for(int j = 0; j < tlist.tagCount(); ++j) {
         NBTTagCompound rs = tlist.getCompoundTagAt(j);
         if(rs.hasKey("key")) {
            this.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
         }
      }

   }

   public void readFromNBT(NBTTagCompound nbttagcompound, String label) {
      this.aspects.clear();
      NBTTagList tlist = nbttagcompound.getTagList(label, 10);

      for(int j = 0; j < tlist.tagCount(); ++j) {
         NBTTagCompound rs = tlist.getCompoundTagAt(j);
         if(rs.hasKey("key")) {
            this.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
         }
      }

   }

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      NBTTagList tlist = new NBTTagList();
      nbttagcompound.setTag("Aspects", tlist);
      Aspect[] var3 = this.getAspects();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Aspect aspect = var3[var5];
         if(aspect != null) {
            NBTTagCompound f = new NBTTagCompound();
            f.setString("key", aspect.getTag());
            f.setInteger("amount", this.getAmount(aspect));
            tlist.appendTag(f);
         }
      }

   }

   public void writeToNBT(NBTTagCompound nbttagcompound, String label) {
      NBTTagList tlist = new NBTTagList();
      nbttagcompound.setTag(label, tlist);
      Aspect[] var4 = this.getAspects();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Aspect aspect = var4[var6];
         if(aspect != null) {
            NBTTagCompound f = new NBTTagCompound();
            f.setString("key", aspect.getTag());
            f.setInteger("amount", this.getAmount(aspect));
            tlist.appendTag(f);
         }
      }

   }
}
