package com.kentington.thaumichorizons.common.tiles;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.api.visnet.TileVisNode;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemWispEssence;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.utils.EntityUtils;

public class TileSyntheticNode extends TileVisNode implements INode, IWandable {

   AspectList aspects = new AspectList();
   AspectList fractionalAspects = new AspectList();
   AspectList aspectsMax = new AspectList();
   private NodeType nodeType;
   private NodeModifier nodeModifier;
   public float rotation;
   float increment;
   public Entity drainEntity;
   public MovingObjectPosition drainCollision;
   public int drainColor;
   public Color targetColor;
   public Color color;


   public TileSyntheticNode() {
      this.nodeType = NodeType.NORMAL;
      this.nodeModifier = null;
      this.rotation = 0.0F;
      this.increment = 1.0F;
      this.drainEntity = null;
      this.drainCollision = null;
      this.drainColor = 16777215;
      this.targetColor = new Color(16777215);
      this.color = new Color(16777215);
   }

   public void debug() {
      if(super.worldObj.isRemote) {
         ;
      }

   }

   public AspectList getAspects() {
      return this.aspects.getAspects().length > 0 && this.aspects.getAspects()[0] != null?this.aspects:new AspectList();
   }

   public AspectList getMaxAspects() {
      return this.aspectsMax;
   }

   public void setAspects(AspectList aspects) {
      this.aspectsMax = aspects;
      this.aspects = new AspectList();
      this.fractionalAspects = new AspectList();
      Aspect[] var2 = aspects.getAspectsSortedAmount();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Aspect asp = var2[var4];
         this.aspects.add(asp, 0);
         this.fractionalAspects.add(asp, 0);
      }

   }

   public boolean doesContainerAccept(Aspect tag) {
      return true;
   }

   public int addToContainer(Aspect tag, int amount) {
      this.aspects.add(tag, amount);
      int toReturn = amount;
      if(this.aspectsMax.getAmount(tag) < this.aspects.getAmount(tag)) {
         toReturn = amount - (this.aspectsMax.getAmount(tag) - this.aspects.getAmount(tag));
         this.aspects.reduce(tag, this.aspectsMax.getAmount(tag) - this.aspects.getAmount(tag));
      }

      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      this.markDirty();
      return toReturn;
   }

   public boolean addFractionalToContainer(Aspect tag, int amount) {
      if(this.aspects.getAmount(tag) >= this.aspectsMax.getAmount(tag)) {
         return false;
      } else {
         this.fractionalAspects.add(tag, amount);

         while(this.fractionalAspects.getAmount(tag) > 100) {
            this.addToContainer(tag, 1);
            this.fractionalAspects.remove(tag, 100);
         }

         return true;
      }
   }

   public boolean takeFromContainer(Aspect tag, int amount) {
      return this.aspects.reduce(tag, amount);
   }

   public boolean takeFromContainer(AspectList ot) {
      Aspect[] toRemove = ot.getAspects();

      for(int i = 0; i < toRemove.length; ++i) {
         if(!this.aspects.reduce(toRemove[i], ot.getAmount(toRemove[i]))) {
            return false;
         }
      }

      return true;
   }

   public boolean doesContainerContainAmount(Aspect tag, int amount) {
      return this.aspects.getAmount(tag) >= amount;
   }

   public boolean doesContainerContain(AspectList ot) {
      Aspect[] var2 = ot.getAspectsSortedAmount();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Aspect asp = var2[var4];
         if(this.aspects.getAmount(asp) <= 0) {
            return false;
         }
      }

      return true;
   }

   public int containerContains(Aspect tag) {
      return this.aspects.getAmount(tag);
   }

   public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
      return -1;
   }

   public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
      player.setItemInUse(wandstack, Integer.MAX_VALUE);
      ItemWandCasting wand = (ItemWandCasting)wandstack.getItem();
      wand.setObjectInUse(wandstack, super.xCoord, super.yCoord, super.zCoord);
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

      int g2;
      int b2;
      if(count % 5 == 0) {
         r = 1;
         if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "NODETAPPER1")) {
            ++r;
         }

         if(ResearchManager.isResearchComplete(player.getCommandSenderName(), "NODETAPPER2")) {
            ++r;
         }

         boolean var13 = !player.isSneaking() && ResearchManager.isResearchComplete(player.getCommandSenderName(), "NODEPRESERVE") && !wand.getRod(wandstack).getTag().equals("wood") && !wand.getCap(wandstack).getTag().equals("iron");
         boolean var14 = false;
         Aspect r2 = null;
         if((r2 = this.chooseRandomFilteredFromSource(wand.getAspectsWithRoom(wandstack), var13)) != null) {
            g2 = this.getAspects().getAmount(r2);
            if(r > g2) {
               r = g2;
            }

            if(var13 && r == g2) {
               --r;
            }

            if(r > 0) {
               b2 = wand.addVis(wandstack, r2, r, !super.worldObj.isRemote);
               if(b2 < r) {
                  this.drainColor = r2.getColor();
                  if(!super.worldObj.isRemote) {
                     this.takeFromContainer(r2, r - b2);
                     mfu = true;
                  }

                  var14 = true;
               }
            }
         }

         if(var14) {
            this.drainEntity = player;
            this.drainCollision = movingobjectposition;
            this.targetColor = new Color(this.drainColor);
         } else {
            this.drainEntity = null;
            this.drainCollision = null;
         }

         if(mfu) {
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.markDirty();
         }
      }

      if(player.worldObj.isRemote) {
         r = this.targetColor.getRed();
         g = this.targetColor.getGreen();
         b = this.targetColor.getBlue();
         int var15 = this.color.getRed() * 4;
         g2 = this.color.getGreen() * 4;
         b2 = this.color.getBlue() * 4;
         this.color = new Color((r + var15) / 5, (g + g2) / 5, (b + b2) / 5);
      }

   }

   public Aspect chooseRandomFilteredFromSource(AspectList filter, boolean preserve) {
      int min = preserve?1:0;
      ArrayList validaspects = new ArrayList();
      Aspect[] asp = this.aspects.getAspects();
      int var6 = asp.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Aspect prim = asp[var7];
         if(filter.getAmount(prim) > 0 && this.aspects.getAmount(prim) > min) {
            validaspects.add(prim);
         }
      }

      if(validaspects.size() == 0) {
         return null;
      } else {
         Aspect var9 = (Aspect)validaspects.get(super.worldObj.rand.nextInt(validaspects.size()));
         if(var9 != null && this.aspects.getAmount(var9) > min) {
            return var9;
         } else {
            return null;
         }
      }
   }

   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {
      this.drainEntity = null;
      this.drainCollision = null;
   }

   public String getId() {
      return "SYNTHETIC";
   }

   public AspectList getAspectsBase() {
      return this.aspectsMax;
   }

   public NodeType getNodeType() {
      return NodeType.NORMAL;
   }

   public void setNodeType(NodeType nodeType) {}

   public void setNodeModifier(NodeModifier nodeModifier) {}

   public NodeModifier getNodeModifier() {
      return null;
   }

   public int getNodeVisBase(Aspect aspect) {
      return this.aspectsMax.getAmount(aspect);
   }

   public void setNodeVisBase(Aspect aspect, short nodeVisBase) {}

   public int getRange() {
      return 8;
   }

   public boolean isSource() {
      return false;
   }

   public void updateEntity() {
      if(!super.worldObj.isRemote) {
         Aspect[] var1 = this.aspectsMax.getAspectsSortedAmount();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Aspect aspect = var1[var3];
            if(aspect != null && this.aspects.getAmount(aspect) < this.aspectsMax.getAmount(aspect)) {
               if(aspect.isPrimal()) {
                  int primalComponents = VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord, super.zCoord, aspect, 10);
                  if(primalComponents > 0) {
                     this.addFractionalToContainer(aspect, primalComponents);
                  }
               } else {
                  AspectList var12 = ResearchManager.reduceToPrimals((new AspectList()).add(aspect, 1));
                  int actuallyDrained = 100;
                  Aspect[] var7 = var12.getAspects();
                  int var8 = var7.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     Aspect primal = var7[var9];
                     int drained = VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord, super.zCoord, primal, 10);
                     if(drained < actuallyDrained) {
                        actuallyDrained = drained;
                     }
                  }

                  if(actuallyDrained > 0) {
                     this.addFractionalToContainer(aspect, actuallyDrained);
                  }
               }
            }
         }
      } else {
         this.rotation += this.increment;
         if(this.rotation > 360.0F) {
            this.rotation -= 360.0F;
         }
      }

   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      NBTTagList tlist = new NBTTagList();
      nbttagcompound.setTag("AspectsMax", tlist);
      Aspect[] tlist2 = this.aspectsMax.getAspects();
      int tlist3 = tlist2.length;

      int var5;
      for(var5 = 0; var5 < tlist3; ++var5) {
         Aspect aspect = tlist2[var5];
         if(aspect != null) {
            NBTTagCompound aspect1 = new NBTTagCompound();
            aspect1.setString("key", aspect.getTag());
            aspect1.setInteger("amount", this.aspectsMax.getAmount(aspect));
            tlist.appendTag(aspect1);
         }
      }

      NBTTagList var10 = new NBTTagList();
      nbttagcompound.setTag("Aspects", var10);
      Aspect[] var11 = this.aspects.getAspects();
      var5 = var11.length;

      int var14;
      for(var14 = 0; var14 < var5; ++var14) {
         Aspect var15 = var11[var14];
         if(var15 != null) {
            NBTTagCompound aspect2 = new NBTTagCompound();
            aspect2.setString("key", var15.getTag());
            aspect2.setInteger("amount", this.aspects.getAmount(var15));
            var10.appendTag(aspect2);
         }
      }

      NBTTagList var12 = new NBTTagList();
      nbttagcompound.setTag("AspectsFractional", var12);
      Aspect[] var13 = this.fractionalAspects.getAspects();
      var14 = var13.length;

      for(int var16 = 0; var16 < var14; ++var16) {
         Aspect var17 = var13[var16];
         if(var17 != null) {
            NBTTagCompound f = new NBTTagCompound();
            f.setString("key", var17.getTag());
            f.setInteger("amount", this.fractionalAspects.getAmount(var17));
            var12.appendTag(f);
         }
      }

      if(this.drainEntity != null && this.drainEntity instanceof EntityPlayer) {
         nbttagcompound.setString("drainer", this.drainEntity.getCommandSenderName());
      }

      nbttagcompound.setInteger("draincolor", this.drainColor);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      AspectList al = new AspectList();
      NBTTagList tlist = nbttagcompound.getTagList("AspectsMax", 10);

      for(int al2 = 0; al2 < tlist.tagCount(); ++al2) {
         NBTTagCompound tlist2 = tlist.getCompoundTagAt(al2);
         if(tlist2.hasKey("key")) {
            al.add(Aspect.getAspect(tlist2.getString("key")), tlist2.getInteger("amount"));
         }
      }

      this.aspectsMax = al.copy();
      AspectList var10 = new AspectList();
      NBTTagList var11 = nbttagcompound.getTagList("Aspects", 10);

      for(int al3 = 0; al3 < var11.tagCount(); ++al3) {
         NBTTagCompound tlist3 = var11.getCompoundTagAt(al3);
         if(tlist3.hasKey("key")) {
            var10.add(Aspect.getAspect(tlist3.getString("key")), tlist3.getInteger("amount"));
         }
      }

      this.aspects = var10.copy();
      AspectList var12 = new AspectList();
      NBTTagList var13 = nbttagcompound.getTagList("AspectsFractional", 10);

      for(int de = 0; de < var13.tagCount(); ++de) {
         NBTTagCompound rs = var13.getCompoundTagAt(de);
         if(rs.hasKey("key")) {
            var12.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
         }
      }

      this.fractionalAspects = var12.copy();
      String var14 = nbttagcompound.getString("drainer");
      if(var14 != null && var14.length() > 0 && this.getWorldObj() != null) {
         this.drainEntity = this.getWorldObj().getPlayerEntityByName(var14);
         if(this.drainEntity != null) {
            this.drainCollision = new MovingObjectPosition(super.xCoord, super.yCoord, super.zCoord, 0, Vec3.createVectorHelper(this.drainEntity.posX, this.drainEntity.posY, this.drainEntity.posZ));
         }
      }

      this.drainColor = nbttagcompound.getInteger("draincolor");
   }

   public void addEssence(EntityPlayer player) {
      Item essence = player.getHeldItem().getItem();
      if(essence == ConfigItems.itemWispEssence && ((ItemWispEssence)essence).getAspects(player.getHeldItem()) != null && ((ItemWispEssence)essence).getAspects(player.getHeldItem()).getAspects().length > 0) {
         Aspect asp = ((ItemWispEssence)essence).getAspects(player.getHeldItem()).getAspects()[0];
         this.aspectsMax.add(asp, 4);
         this.aspects.add(asp, 0);
         this.fractionalAspects.add(asp, 0);
         --player.getHeldItem().stackSize;
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         this.markDirty();
      }

   }
}
