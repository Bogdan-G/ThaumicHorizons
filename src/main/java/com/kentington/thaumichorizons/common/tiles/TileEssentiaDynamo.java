package com.kentington.thaumichorizons.common.tiles;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.WorldCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.visnet.TileVisNode;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.common.lib.research.ResearchManager;

public class TileEssentiaDynamo extends TileVisNode implements IAspectContainer, IEssentiaTransport {

   AspectList primalsActuallyProvided = new AspectList();
   AspectList primalsProvided = new AspectList();
   public Aspect essentia = null;
   public int ticksProvided = 0;
   public float rise = 0.0F;
   public float rotation = 0.0F;
   public float rotation2 = 0.0F;


   public AspectList getAspects() {
      return this.primalsProvided.getAspects().length > 0 && this.primalsProvided.getAspects()[0] != null?this.primalsProvided:null;
   }

   public void setAspects(AspectList aspects) {}

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
      return false;
   }

   public boolean doesContainerContain(AspectList ot) {
      return false;
   }

   public int containerContains(Aspect tag) {
      return 0;
   }

   public int getRange() {
      return 0;
   }

   public boolean isSource() {
      return this.ticksProvided > 0;
   }

   public int consumeVis(Aspect aspect, int amount) {
      int drain = Math.min(this.primalsActuallyProvided.getAmount(aspect), amount);
      if(drain > 0) {
         this.primalsActuallyProvided.reduce(aspect, drain);
      }

      return drain;
   }

   public boolean isConnectable(ForgeDirection face) {
      return face == ForgeDirection.DOWN;
   }

   public boolean canInputFrom(ForgeDirection face) {
      return face == ForgeDirection.DOWN;
   }

   public boolean canOutputTo(ForgeDirection face) {
      return false;
   }

   public void setSuction(Aspect aspect, int amount) {}

   public Aspect getSuctionType(ForgeDirection face) {
      return null;
   }

   public int getSuctionAmount(ForgeDirection face) {
      return this.ticksProvided <= 20?128:0;
   }

   public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
      return 0;
   }

   public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
      this.ticksProvided += 21;
      this.essentia = aspect;
      if(VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId)) == null) {
         VisNetHandler.sources.put(Integer.valueOf(super.worldObj.provider.dimensionId), new HashMap());
      }

      if(((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).get(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId)) == null) {
         ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).put(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId), new WeakReference(this));
      } else if(((WeakReference)((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).get(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId))).get() == null) {
         ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).remove(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId));
         ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).put(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId), new WeakReference(this));
      }

      this.markDirty();
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      return 1;
   }

   public Aspect getEssentiaType(ForgeDirection face) {
      return this.essentia;
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

   public void updateEntity() {
      super.updateEntity();
      if(this.ticksProvided >= 0) {
         if(this.rise < 0.3F) {
            this.rise += 0.02F;
         } else {
            this.rotation2 += 2.0F;
            if(this.rotation2 >= 360.0F) {
               this.rotation2 -= 360.0F;
            }
         }

         this.rotation += 2.0F;
         if(this.rotation >= 360.0F) {
            this.rotation -= 360.0F;
         }
      } else if(this.ticksProvided < 0 && (this.rise > 0.0F || this.rotation2 != 0.0F)) {
         if(this.rotation2 > 0.0F) {
            this.rotation2 -= 8.0F;
            if(this.rotation2 < 0.0F) {
               this.rotation2 = 0.0F;
            }
         } else if(this.rise > 0.0F) {
            this.rise -= 0.02F;
         }
      }

      if(this.ticksProvided > 0) {
         this.primalsProvided = ResearchManager.reduceToPrimals((new AspectList()).add(this.essentia, 1));
         int numEach = 12 / this.primalsProvided.size();
         Aspect[] var2 = this.primalsProvided.getAspects();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Aspect asp = var2[var4];
            int num = this.primalsProvided.getAmount(asp);
            if(num > numEach) {
               this.primalsProvided.reduce(asp, num - numEach);
            } else if(num < numEach) {
               this.primalsProvided.add(asp, numEach - num);
            }
         }

         this.primalsActuallyProvided = this.primalsProvided.copy();
         --this.ticksProvided;
         if(!super.worldObj.isRemote) {
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.markDirty();
         }
      } else if(this.ticksProvided == 0) {
         if(!super.worldObj.isRemote && !this.drawEssentia() && VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId)) != null) {
            --this.ticksProvided;
            this.killMe();
            this.primalsProvided = new AspectList();
            this.primalsActuallyProvided = new AspectList();
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.markDirty();
         }
      } else if(!super.worldObj.isRemote && this.ticksProvided < 0) {
         this.drawEssentia();
      }

   }

   public void killMe() {
      if(VisNetHandler.sources != null && super.worldObj != null && super.worldObj.provider != null && VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId)) != null) {
         ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).remove(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId));
         this.removeThisNode();
      }

   }

   boolean drawEssentia() {
      TileEntity te = ThaumcraftApiHelper.getConnectableTile(super.worldObj, super.xCoord, super.yCoord, super.zCoord, ForgeDirection.DOWN);
      if(te != null) {
         IEssentiaTransport ic = (IEssentiaTransport)te;
         if(!ic.canOutputTo(ForgeDirection.UP)) {
            return false;
         }

         Aspect ta = null;
         if(ic.getEssentiaAmount(ForgeDirection.UP) > 0 && ic.getSuctionAmount(ForgeDirection.UP) < this.getSuctionAmount(ForgeDirection.DOWN) && this.getSuctionAmount(ForgeDirection.DOWN) >= ic.getMinimumSuction()) {
            ta = ic.getEssentiaType(ForgeDirection.UP);
         }

         if(ta != null && ic.takeEssentia(ta, 1, ForgeDirection.UP) == 1) {
            this.addEssentia(ta, 1, ForgeDirection.DOWN);
            return true;
         }
      }

      return false;
   }

   public void debug() {}

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      if(this.essentia != null) {
         nbttagcompound.setString("key", this.essentia.getTag());
      }

      nbttagcompound.setInteger("ticks", this.ticksProvided);
      NBTTagList tlist = new NBTTagList();
      nbttagcompound.setTag("AspectsProvided", tlist);
      Aspect[] var3 = this.primalsProvided.getAspects();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Aspect aspect = var3[var5];
         if(aspect != null) {
            NBTTagCompound f = new NBTTagCompound();
            f.setString("key", aspect.getTag());
            f.setInteger("amount", this.primalsProvided.getAmount(aspect));
            tlist.appendTag(f);
         }
      }

   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.essentia = Aspect.getAspect(nbttagcompound.getString("key"));
      this.ticksProvided = nbttagcompound.getInteger("ticks");
      AspectList al = new AspectList();
      NBTTagList tlist = nbttagcompound.getTagList("AspectsProvided", 10);

      for(int j = 0; j < tlist.tagCount(); ++j) {
         NBTTagCompound rs = tlist.getCompoundTagAt(j);
         if(rs.hasKey("key")) {
            al.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
         }
      }

      this.primalsProvided = al.copy();
      this.primalsActuallyProvided = this.primalsProvided.copy();
      if(this.ticksProvided < 0) {
         this.killMe();
      }

   }
}
