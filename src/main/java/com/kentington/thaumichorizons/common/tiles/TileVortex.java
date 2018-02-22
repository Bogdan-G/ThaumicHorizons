package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import com.kentington.thaumichorizons.common.lib.PocketPlaneData;
import com.kentington.thaumichorizons.common.lib.PocketPlaneThread;
import com.kentington.thaumichorizons.common.lib.VortexTeleporter;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveHandler;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.blocks.BlockAiry;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityWisp;
import thaumcraft.common.items.wands.ItemWandCasting;

public class TileVortex extends TileThaumcraft implements IWandable, IAspectContainer {

   final int MAX_COUNT = 2400;
   public int count;
   public int beams;
   public int dimensionID;
   public AspectList aspects = new AspectList();
   boolean ateDevices = false;
   public boolean collapsing = false;
   public boolean createdDimension = false;
   public boolean generating = false;
   public boolean cheat = false;
   ArrayList items = new ArrayList();
   Thread ppThread = null;


   public void updateEntity() {
      super.updateEntity();
      if(this.generating) {
         super.worldObj.createExplosion((Entity)null, (double)((float)super.xCoord + super.worldObj.rand.nextFloat()), (double)((float)super.yCoord + super.worldObj.rand.nextFloat()), (double)((float)super.zCoord + super.worldObj.rand.nextFloat()), 1.0F, false);
         if(this.ppThread == null) {
            this.createDimension((EntityItem)null);
         } else {
            if(!this.ppThread.isAlive()) {
               this.generating = false;
               this.createdDimension = true;
               this.markDirty();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            }

         }
      } else if(this.collapsing) {
         ++this.count;
         if(this.count > 25) {
            if(this.createdDimension) {
               MinecraftServer.getServer().worldServerForDimension(ThaumicHorizons.dimensionPocketId).setBlockToAir(0, 129, this.dimensionID * 256);
            }

            super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
            Thaumcraft.proxy.burst(super.worldObj, (double)super.xCoord, (double)super.yCoord, (double)super.zCoord, 4.0F);
            BlockAiry.explodify(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
         }

      } else {
         if(this.count < 50) {
            ++this.count;
         } else {
            if(!this.ateDevices) {
               if(!this.cheat) {
                  for(int ents = -1; ents < 2; ++ents) {
                     for(int dy = -1; dy < 2; ++dy) {
                        for(int e = -1; e < 2; ++e) {
                           if(ents != 0 || dy != 0 || e != 0) {
                              super.worldObj.setBlockToAir(super.xCoord + ents, super.yCoord + dy, super.zCoord + e);
                              Thaumcraft.proxy.burst(super.worldObj, (double)(super.xCoord + ents), (double)(super.yCoord + dy), (double)(super.zCoord + e), 2.0F);
                           }
                        }
                     }
                  }
               }

               this.ateDevices = true;
            }

            if(this.beams < 6 && !this.cheat) {
               this.handleHungryNode();
            } else if(!this.createdDimension && !this.generating) {
               this.handlePocketPlaneStuff();
            }

            if(!this.cheat) {
               this.count += 6 - this.beams;
            }

            if(this.count > 2400) {
               this.collapsing = true;
               this.count = 0;
            }

            if(this.createdDimension) {
               List var6 = super.worldObj.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)));
               if(var6.size() > 0) {
                  Iterator var7 = var6.iterator();

                  while(var7.hasNext()) {
                     Object var8 = var7.next();
                     EntityPlayerMP player = (EntityPlayerMP)var8;
                     if(player.ridingEntity == null && player.riddenByEntity == null) {
                        MinecraftServer mServer = FMLCommonHandler.instance().getMinecraftServerInstance();
                        if(player.timeUntilPortal > 0) {
                           player.timeUntilPortal = 100;
                        } else if(player.dimension != ThaumicHorizons.dimensionPocketId) {
                           player.timeUntilPortal = 100;
                           player.mcServer.getConfigurationManager().transferPlayerToDimension(player, ThaumicHorizons.dimensionPocketId, new VortexTeleporter(mServer.worldServerForDimension(ThaumicHorizons.dimensionPocketId), this.dimensionID));
                        } else {
                           player.timeUntilPortal = 100;
                           player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new VortexTeleporter(mServer.worldServerForDimension(0), this.dimensionID));
                        }
                     }
                  }
               }
            }
         }

      }
   }

   void handlePocketPlaneStuff() {
      List ents = super.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand(1.0D, 1.0D, 1.0D));
      if(ents != null && ents.size() > 0) {
         Iterator var2 = ents.iterator();

         while(var2.hasNext()) {
            Object ent = var2.next();
            Entity eo = (Entity)ent;
            if(eo instanceof EntityItem) {
               EntityItem item = (EntityItem)eo;
               if(ThaumicHorizons.enablePocket && item.getEntityItem().getItem() == ConfigItems.itemEldritchObject && item.getEntityItem().getItemDamage() == 3) {
                  this.createDimension(item);
                  item.setDead();
               } else {
                  this.handleVoidCrafting(item);
               }
            }
         }
      }

   }

   void handleVoidCrafting(EntityItem item) {
      if(item.getEntityItem().getItem() == ConfigItems.itemResource && item.getEntityItem().getItemDamage() == 16) {
         this.items.add(new ItemStack(ThaumicHorizons.itemVoidPutty, item.getEntityItem().stackSize));
         item.setDead();
      } else {
         int var4;
         if(item.getEntityItem().getItem() == ConfigItems.itemResource && item.getEntityItem().getItemDamage() == 14) {
            for(var4 = 0; var4 < item.getEntityItem().stackSize; ++var4) {
               this.spawnWisps();
            }

            item.setDead();
         } else if(item.getEntityItem().getItem() == ThaumicHorizons.itemCrystalWand) {
            ItemStack i = new ItemStack(ThaumicHorizons.itemWandCastingDisposable);
            ((ItemWandCasting)i.getItem()).setRod(i, ThaumicHorizons.ROD_CRYSTAL);
            ((ItemWandCasting)i.getItem()).setCap(i, ThaumicHorizons.CAP_CRYSTAL);
            ((ItemWandCasting)i.getItem()).storeVis(i, Aspect.EARTH, 25000);
            ((ItemWandCasting)i.getItem()).storeVis(i, Aspect.AIR, 25000);
            ((ItemWandCasting)i.getItem()).storeVis(i, Aspect.FIRE, 25000);
            ((ItemWandCasting)i.getItem()).storeVis(i, Aspect.WATER, 25000);
            ((ItemWandCasting)i.getItem()).storeVis(i, Aspect.ORDER, 25000);
            ((ItemWandCasting)i.getItem()).storeVis(i, Aspect.ENTROPY, 25000);
            this.items.add(i);
            item.setDead();
         } else if(item.getEntityItem().getItem() == ThaumicHorizons.itemGolemPowder && item.func_145800_j() != null) {
            if(!super.worldObj.isRemote) {
               for(var4 = 0; var4 < item.getEntityItem().stackSize; ++var4) {
                  EntityGolemTH golem = new EntityGolemTH(super.worldObj);
                  golem.setOwner(item.func_145800_j());
                  golem.loadGolem((double)super.xCoord + 0.5D, (double)super.yCoord, (double)super.zCoord + 0.5D, (Block)null, 0, -420, false, false, false);
                  super.worldObj.playSoundEffect((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
                  golem.setHomeArea((int)golem.posX, (int)golem.posY, (int)golem.posZ, 32);
                  super.worldObj.spawnEntityInWorld(golem);
                  super.worldObj.setEntityState(golem, (byte)7);
               }
            }

            item.setDead();
         }
      }

   }

   void spawnWisps() {
      if(!super.worldObj.isRemote) {
         int wisps = super.worldObj.rand.nextInt(4) + 1;

         for(int i = 0; i < wisps; ++i) {
            EntityWisp wisp = new EntityWisp(super.worldObj);
            wisp.setPosition((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D);
            if(this.aspects.size() > 0 && this.aspects.getAspects()[0] != null) {
               wisp.setType(this.aspects.getAspects()[super.worldObj.rand.nextInt(this.aspects.size())].getTag());
            }

            super.worldObj.spawnEntityInWorld(wisp);
         }

      }
   }

   void createDimension(EntityItem pearl) {
      PocketPlaneData data = new PocketPlaneData();
      String name = "";
      if(pearl != null && pearl.getEntityItem().hasTagCompound()) {
         name = pearl.getEntityItem().getDisplayName();
      } else if(pearl != null) {
         name = pearl.func_145800_j() + StatCollector.translateToLocal("thaumichorizons.pocketplane");
      }

      data.name = name;
      this.dimensionID = PocketPlaneData.planes.size();
      MinecraftServer server = MinecraftServer.getServer();
      if(server.worldServerForDimension(ThaumicHorizons.dimensionPocketId) == null) {
         new WorldServer(server, (ISaveHandler)null, (String)null, ThaumicHorizons.dimensionPocketId, (WorldSettings)null, server.theProfiler);
      }

      this.generating = true;
      if(!super.worldObj.isRemote) {
         this.ppThread = new Thread(new PocketPlaneThread(data, this.aspects, MinecraftServer.getServer().worldServerForDimension(ThaumicHorizons.dimensionPocketId), super.xCoord, super.yCoord, super.zCoord));
         this.ppThread.run();
      }

      this.markDirty();
   }

   void handleHungryNode() {
      int i;
      int tx;
      int ty;
      Vec3 v1;
      if(super.worldObj.isRemote && this.beams < 6) {
         for(int ents = 0; ents < Thaumcraft.proxy.particleCount(1); ++ents) {
            i = super.xCoord + super.worldObj.rand.nextInt(16) - super.worldObj.rand.nextInt(16);
            tx = super.yCoord + super.worldObj.rand.nextInt(16) - super.worldObj.rand.nextInt(16);
            ty = super.zCoord + super.worldObj.rand.nextInt(16) - super.worldObj.rand.nextInt(16);
            if(tx > super.worldObj.getHeightValue(i, ty)) {
               tx = super.worldObj.getHeightValue(i, ty);
            }

            Vec3 tz = Vec3.createVectorHelper((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D);
            v1 = Vec3.createVectorHelper((double)i + 0.5D, (double)tx + 0.5D, (double)ty + 0.5D);
            MovingObjectPosition v2 = ThaumcraftApiHelper.rayTraceIgnoringSource(super.worldObj, tz, v1, true, false, false);
            if(v2 != null && this.getDistanceFrom((double)v2.blockX, (double)v2.blockY, (double)v2.blockZ) < 256.0D) {
               i = v2.blockX;
               tx = v2.blockY;
               ty = v2.blockZ;
               Block mop = super.worldObj.getBlock(i, tx, ty);
               int bi = super.worldObj.getBlockMetadata(i, tx, ty);
               if(!mop.isAir(super.worldObj, i, tx, ty)) {
                  Thaumcraft.proxy.hungryNodeFX(super.worldObj, i, tx, ty, super.xCoord, super.yCoord, super.zCoord, mop, bi);
               }
            }
         }
      }

      List var17 = super.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand(15.0D, 15.0D, 15.0D));
      if(var17 != null && var17.size() > 0) {
         Iterator var18 = var17.iterator();

         while(var18.hasNext()) {
            Object var19 = var18.next();
            Entity var20 = (Entity)var19;
            if(!(var20 instanceof EntityPlayer) || !((EntityPlayer)var20).capabilities.disableDamage) {
               double var21;
               if(var20.isEntityAlive() && !var20.isEntityInvulnerable()) {
                  var21 = this.getDistanceTo(var20.posX, var20.posY, var20.posZ);
                  if(var21 < 2.0D) {
                     if(var20 instanceof EntityFallingBlock) {
                        var20.setDead();
                     } else {
                        var20.attackEntityFrom(DamageSource.outOfWorld, 3.0F - (float)this.beams / 2.0F);
                     }
                  }
               }

               var21 = ((double)super.xCoord + 0.5D - var20.posX) / 15.0D;
               double var23 = ((double)super.yCoord + 0.5D - var20.posY) / 15.0D;
               double var26 = ((double)super.zCoord + 0.5D - var20.posZ) / 15.0D;
               double h = Math.sqrt(var21 * var21 + var23 * var23 + var26 * var26);
               double var11 = 1.0D - h;
               double modifier = 2.0D - (double)this.beams / 3.0D;
               if(var11 > 0.0D) {
                  var11 *= var11;
                  var20.motionX += var21 / h * var11 * 0.15D * modifier;
                  var20.motionY += var23 / h * var11 * 0.25D * modifier;
                  var20.motionZ += var26 / h * var11 * 0.15D * modifier;
               }
            }
         }
      }

      for(i = 0; i < 3; ++i) {
         if(!super.worldObj.isRemote && (this.beams <= 0 || super.worldObj.rand.nextInt(this.beams) == 0)) {
            tx = super.xCoord + super.worldObj.rand.nextInt(16) - super.worldObj.rand.nextInt(16);
            ty = super.yCoord + super.worldObj.rand.nextInt(16) - super.worldObj.rand.nextInt(16);
            int var22 = super.zCoord + super.worldObj.rand.nextInt(16) - super.worldObj.rand.nextInt(16);
            if(ty > super.worldObj.getHeightValue(tx, var22)) {
               ty = super.worldObj.getHeightValue(tx, var22);
            }

            v1 = Vec3.createVectorHelper((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D);
            Vec3 var24 = Vec3.createVectorHelper((double)tx + 0.5D, (double)ty + 0.5D, (double)var22 + 0.5D);
            MovingObjectPosition var25 = ThaumcraftApiHelper.rayTraceIgnoringSource(super.worldObj, v1, var24, true, false, false);
            if(var25 != null && this.getDistanceFrom((double)var25.blockX, (double)var25.blockY, (double)var25.blockZ) < 256.0D) {
               tx = var25.blockX;
               ty = var25.blockY;
               var22 = var25.blockZ;
               Block var27 = super.worldObj.getBlock(tx, ty, var22);
               super.worldObj.getBlockMetadata(tx, ty, var22);
               if(!var27.isAir(super.worldObj, tx, ty, var22)) {
                  float var28 = var27.getBlockHardness(super.worldObj, tx, ty, var22);
                  if(var28 >= 0.0F && var28 < 10.0F) {
                     super.worldObj.func_147480_a(tx, ty, var22, true);
                  }
               }
            }
         }
      }

   }

   public double getDistanceTo(double par1, double par3, double par5) {
      double var7 = (double)super.xCoord + 0.5D - par1;
      double var9 = (double)super.yCoord + 0.5D - par3;
      double var11 = (double)super.zCoord + 0.5D - par5;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("count", this.count);
      nbttagcompound.setInteger("beams", this.beams);
      nbttagcompound.setInteger("dimensionID", this.dimensionID);
      nbttagcompound.setBoolean("ateDevices", this.ateDevices);
      nbttagcompound.setBoolean("collapsing", this.collapsing);
      nbttagcompound.setBoolean("createdDimension", this.createdDimension);
      nbttagcompound.setBoolean("isGenerating", this.generating);
      nbttagcompound.setBoolean("cheat", this.cheat);
      NBTTagList tlist = new NBTTagList();
      nbttagcompound.setTag("aspects", tlist);
      Aspect[] itemz = this.aspects.getAspects();
      int var4 = itemz.length;

      for(int item = 0; item < var4; ++item) {
         Aspect itemTag = itemz[item];
         if(itemTag != null) {
            NBTTagCompound f = new NBTTagCompound();
            f.setString("key", itemTag.getTag());
            f.setInteger("amount", this.aspects.getAmount(itemTag));
            tlist.appendTag(f);
         }
      }

      NBTTagList var8 = new NBTTagList();
      Iterator var9 = this.items.iterator();

      while(var9.hasNext()) {
         ItemStack var10 = (ItemStack)var9.next();
         NBTTagCompound var11 = new NBTTagCompound();
         var10.writeToNBT(var11);
         var8.appendTag(var11);
      }

      nbttagcompound.setTag("items", var8);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.count = nbttagcompound.getInteger("count");
      this.beams = nbttagcompound.getInteger("beams");
      this.dimensionID = nbttagcompound.getInteger("dimensionID");
      this.ateDevices = nbttagcompound.getBoolean("ateDevices");
      this.collapsing = nbttagcompound.getBoolean("collapsing");
      this.createdDimension = nbttagcompound.getBoolean("createdDimension");
      this.generating = nbttagcompound.getBoolean("isGenerating");
      this.cheat = nbttagcompound.getBoolean("cheat");
      AspectList al = new AspectList();
      NBTTagList tlist = nbttagcompound.getTagList("aspects", 10);

      for(int itemz = 0; itemz < tlist.tagCount(); ++itemz) {
         NBTTagCompound i = tlist.getCompoundTagAt(itemz);
         if(i.hasKey("key")) {
            al.add(Aspect.getAspect(i.getString("key")), i.getInteger("amount"));
         }
      }

      this.aspects = al.copy();
      this.items.clear();
      NBTTagList var7 = nbttagcompound.getTagList("items", 10);

      for(int var8 = 0; var8 < var7.tagCount(); ++var8) {
         ItemStack item = ItemStack.loadItemStackFromNBT(var7.getCompoundTagAt(var8));
         this.items.add(item);
      }

   }

   public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
      if(!super.worldObj.isRemote && this.items.size() > 0) {
         ItemStack item = (ItemStack)this.items.get(0);
         EntityItem key = new EntityItem(super.worldObj);
         key.setEntityItemStack(item);
         key.setPosition((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D);
         super.worldObj.spawnEntityInWorld(key);
         this.items.remove(0);
         player.worldObj.playSound((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 0.5F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
         player.swingItem();
         this.markDirty();
      }

      player.worldObj.playSound((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:wand", 0.5F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
      player.swingItem();
      this.markDirty();
      return 0;
   }

   public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
      return null;
   }

   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}

   public AspectList getAspects() {
      return this.aspects.getAspects()[0] != null?this.aspects:new AspectList();
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
}
