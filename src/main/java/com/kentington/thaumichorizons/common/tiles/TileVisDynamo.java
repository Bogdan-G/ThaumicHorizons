package com.kentington.thaumichorizons.common.tiles;

import java.awt.Color;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import thaumcraft.api.WorldCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.visnet.TileVisNode;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.EntityUtils;

public class TileVisDynamo extends TileVisNode implements IAspectContainer, IWandable {

   AspectList primalsActuallyProvided = new AspectList();
   AspectList primalsProvided = new AspectList();
   public boolean provideAer = true;
   public boolean provideAqua = true;
   public boolean provideIgnis = true;
   public boolean provideOrdo = true;
   public boolean providePerditio = true;
   public boolean provideTerra = true;
   public int ticksProvided = 0;
   public float rise = 0.0F;
   public float rotation = 0.0F;
   public float rotation2 = 0.0F;
   public Entity drainEntity = null;
   public MovingObjectPosition drainCollision = null;
   public int drainColor = 16777215;
   public Color targetColor = new Color(16777215);
   public Color color = new Color(16777215);


   public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
      return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) != this?false:p_70300_1_.getDistanceSq((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D) <= 64.0D;
   }

   public AspectList getAspects() {
      return this.primalsProvided.getAspects().length > 0 && this.primalsProvided.getAspects()[0] != null?this.primalsProvided:null;
   }

   public void setAspects(AspectList aspects) {}

   public boolean doesContainerAccept(Aspect tag) {
      return false;
   }

   public int addToContainer(Aspect tag, int amount) {
      return amount;
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

   public void updateEntity() {
      super.updateEntity();
      if(this.ticksProvided > 0) {
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

         --this.ticksProvided;
         if(this.ticksProvided == 0) {
            this.primalsProvided = new AspectList();
            this.markDirty();
         }

         this.primalsActuallyProvided = this.primalsProvided.copy();
      } else if(this.ticksProvided == 0) {
         --this.ticksProvided;
         if(VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId)) != null) {
            ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).remove(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId));
         }

         this.removeThisNode();
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

   }

   public void killMe() {
      if(VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId)) != null) {
         ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).remove(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId));
      }

      this.removeThisNode();
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("ticks", this.ticksProvided);
      nbttagcompound.setBoolean("aer", this.provideAer);
      nbttagcompound.setBoolean("aqua", this.provideAqua);
      nbttagcompound.setBoolean("ignis", this.provideIgnis);
      nbttagcompound.setBoolean("ordo", this.provideOrdo);
      nbttagcompound.setBoolean("perditio", this.providePerditio);
      nbttagcompound.setBoolean("terra", this.provideTerra);
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

      if(this.drainEntity != null && this.drainEntity instanceof EntityPlayer) {
         nbttagcompound.setString("drainer", this.drainEntity.getCommandSenderName());
      }

      nbttagcompound.setInteger("draincolor", this.drainColor);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.ticksProvided = nbttagcompound.getInteger("ticks");
      this.provideAer = nbttagcompound.getBoolean("aer");
      this.provideAqua = nbttagcompound.getBoolean("aqua");
      this.provideIgnis = nbttagcompound.getBoolean("ignis");
      this.provideOrdo = nbttagcompound.getBoolean("ordo");
      this.providePerditio = nbttagcompound.getBoolean("perditio");
      this.provideTerra = nbttagcompound.getBoolean("terra");
      AspectList al = new AspectList();
      NBTTagList tlist = nbttagcompound.getTagList("AspectsProvided", 10);

      for(int de = 0; de < tlist.tagCount(); ++de) {
         NBTTagCompound rs = tlist.getCompoundTagAt(de);
         if(rs.hasKey("key")) {
            al.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
         }
      }

      this.primalsProvided = al.copy();
      String var6 = nbttagcompound.getString("drainer");
      if(var6 != null && var6.length() > 0 && this.getWorldObj() != null) {
         this.drainEntity = this.getWorldObj().getPlayerEntityByName(var6);
         if(this.drainEntity != null) {
            this.drainCollision = new MovingObjectPosition(super.xCoord, super.yCoord, super.zCoord, 0, Vec3.createVectorHelper(this.drainEntity.posX, this.drainEntity.posY, this.drainEntity.posZ));
         }
      }

      this.drainColor = nbttagcompound.getInteger("draincolor");
   }

   public int onWandRightClick(World paramWorld, ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
      return -1;
   }

   public ItemStack onWandRightClick(World paramWorld, ItemStack wandstack, EntityPlayer player) {
      player.setItemInUse(wandstack, Integer.MAX_VALUE);
      ItemWandCasting wand = (ItemWandCasting)wandstack.getItem();
      wand.setObjectInUse(wandstack, super.xCoord, super.yCoord, super.zCoord);
      if(this.provideAer || this.provideAqua || this.provideIgnis || this.provideOrdo || this.providePerditio || this.provideTerra) {
         if(VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId)) == null) {
            VisNetHandler.sources.put(Integer.valueOf(super.worldObj.provider.dimensionId), new HashMap());
         }

         if(((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).get(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId)) == null) {
            ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).put(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId), new WeakReference(this));
         } else if(((WeakReference)((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).get(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId))).get() == null) {
            ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).remove(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId));
            ((HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId))).put(new WorldCoordinates(super.xCoord, super.yCoord, super.zCoord, super.worldObj.provider.dimensionId), new WeakReference(this));
         }
      }

      return wandstack;
   }

   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {
      boolean mfu = false;
      ItemWandCasting wand = (ItemWandCasting)wandstack.getItem();
      MovingObjectPosition movingobjectposition = EntityUtils.getMovingObjectPositionFromPlayer(super.worldObj, player, true);
      int r;
      int g;
      int b;
      if(movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
         r = movingobjectposition.blockX;
         g = movingobjectposition.blockY;
         b = movingobjectposition.blockZ;
         if(r != super.xCoord || g != super.yCoord || b != super.zCoord) {
            player.stopUsingItem();
         }
      } else {
         player.stopUsingItem();
      }

      int r2;
      int g2;
      int var21;
      if(count % 5 == 0) {
         boolean var18 = false;
         AspectList var19 = new AspectList();
         this.primalsProvided = new AspectList();
         if(this.provideAer) {
            var19 = var19.add(Aspect.AIR, 25);
         }

         if(this.provideAqua) {
            var19 = var19.add(Aspect.WATER, 25);
         }

         if(this.provideIgnis) {
            var19 = var19.add(Aspect.FIRE, 25);
         }

         if(this.provideOrdo) {
            var19 = var19.add(Aspect.ORDER, 25);
         }

         if(this.providePerditio) {
            var19 = var19.add(Aspect.ENTROPY, 25);
         }

         if(this.provideTerra) {
            var19 = var19.add(Aspect.EARTH, 25);
         }

         Aspect[] var20 = var19.getAspects();
         r2 = var20.length;

         for(g2 = 0; g2 < r2; ++g2) {
            Aspect b2 = var20[g2];
            if(wand.consumeVis(wandstack, player, b2, 40, false)) {
               this.primalsProvided = this.primalsProvided.add(b2, 5);
               var18 = true;
            }
         }

         if(var18) {
            this.ticksProvided = 10;
            this.drainEntity = player;
            this.drainCollision = movingobjectposition;
            b = 0;
            r2 = 0;
            g2 = 0;
            var21 = 0;
            Aspect[] var13 = this.primalsProvided.getAspects();
            int var14 = var13.length;

            for(int var15 = 0; var15 < var14; ++var15) {
               Aspect asp = var13[var15];
               Color col = new Color(asp.getColor());
               b += col.getRed();
               r2 += col.getGreen();
               g2 += col.getBlue();
               ++var21;
            }

            b /= var21;
            r2 /= var21;
            g2 /= var21;
            this.drainColor = (new Color(b, r2, g2)).getRGB();
            this.targetColor = new Color(this.drainColor);
            if(!super.worldObj.isRemote) {
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               this.markDirty();
            }
         } else {
            this.drainEntity = null;
            this.drainCollision = null;
         }
      }

      if(player.worldObj.isRemote) {
         r = this.targetColor.getRed();
         g = this.targetColor.getGreen();
         b = this.targetColor.getBlue();
         r2 = this.color.getRed() * 4;
         g2 = this.color.getGreen() * 4;
         var21 = this.color.getBlue() * 4;
         this.color = new Color((r + r2) / 5, (g + g2) / 5, (b + var21) / 5);
      }

   }

   public void onWandStoppedUsing(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer, int paramInt) {}
}
