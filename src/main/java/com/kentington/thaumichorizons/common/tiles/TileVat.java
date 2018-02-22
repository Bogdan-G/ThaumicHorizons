package com.kentington.thaumichorizons.common.tiles;

import com.google.common.collect.HashMultimap;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.IEntityInfusedStats;
import com.kentington.thaumichorizons.common.lib.CreatureInfusionRecipe;
import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import com.kentington.thaumichorizons.common.lib.PacketFXEssentiaBubble;
import com.kentington.thaumichorizons.common.lib.PacketFXInfusionDone;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import com.kentington.thaumichorizons.common.lib.PacketInfusionFX;
import com.kentington.thaumichorizons.common.lib.SelfInfusionRecipe;
import com.kentington.thaumichorizons.common.tiles.TileVatConnector;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.visnet.VisNetHandler;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.lib.network.fx.PacketFXBlockZap;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TilePedestal;

public class TileVat extends TileThaumcraft implements IAspectContainer, IEssentiaTransport, ISidedInventory {

   public int mode = 0;
   AspectList myEssentia = new AspectList();
   AspectList essentiaDemanded = new AspectList();
   Aspect currentlySucking = null;
   public ItemStack sample = null;
   public ItemStack nutrients = null;
   public int progress;
   private EntityLivingBase entityContained = null;
   public final int CLONE_TIME = 800;
   public int[] selfInfusions = new int[12];
   public float selfInfusionHealth = 20.0F;
   private ArrayList pedestals = new ArrayList();
   private int dangerCount = 0;
   public boolean checkSurroundings = true;
   public int symmetry = 0;
   public int instability = 0;
   private ArrayList recipeIngredients = null;
   private Object recipeOutput = null;
   private String recipePlayer = null;
   private String recipeOutputLabel = "";
   private int recipeInstability = 0;
   private int recipeXP = 0;
   public int recipeType = 0;
   int itemCount = 0;
   public int count = 0;
   public int craftCount = 0;
   public float startUp;
   private int countDelay = 10;
   ArrayList ingredients = new ArrayList();
   public HashMap sourceFX = new HashMap();


   public boolean activate(EntityPlayer player, boolean direct) {
      ItemStack possibleJar = player.getHeldItem();
      if(possibleJar != null && Block.getBlockFromItem(possibleJar.getItem()) == ThaumicHorizons.blockJar && possibleJar.hasTagCompound() && !possibleJar.stackTagCompound.getBoolean("isSoul")) {
         if(this.mode == 0 && this.getEntityContained() == null && player.inventory.addItemStackToInventory(new ItemStack(ConfigBlocks.blockJar))) {
            this.setEntityContained((EntityLivingBase)EntityList.createEntityFromNBT(possibleJar.getTagCompound(), super.worldObj));
            --possibleJar.stackSize;
            this.markDirty();
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            return true;
         }
      } else if(possibleJar != null && Block.getBlockFromItem(possibleJar.getItem()) == ConfigBlocks.blockJar && possibleJar.getItemDamage() == 0 && this.getEntityContained() != null && !(this.getEntityContained() instanceof EntityPlayer)) {
         if(this.mode == 0) {
            return this.jarCritter(possibleJar, player);
         }
      } else {
         if(this.mode != 4 || possibleJar == null || Block.getBlockFromItem(possibleJar.getItem()) != ThaumicHorizons.blockJar || !possibleJar.hasTagCompound() || !possibleJar.stackTagCompound.getBoolean("isSoul")) {
            if(this.mode == 0 && direct && this.getEntityContained() == null) {
               player.setPositionAndUpdate((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D);
               this.setEntityContained(player);
               this.markDirty();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               return true;
            }

            if(this.mode == 0 && this.getEntityContained() == player) {
               if(super.worldObj.getBlock(super.xCoord, super.yCoord + 1, super.zCoord) == ThaumicHorizons.blockSoulBeacon) {
                  player.setPositionAndUpdate((double)super.xCoord + 0.5D, (double)super.yCoord + 2.0D, (double)super.zCoord + 0.5D);
               } else {
                  player.setPositionAndUpdate((double)super.xCoord + 0.5D, (double)super.yCoord + 1.0D, (double)super.zCoord + 0.5D);
               }

               this.setEntityContained((EntityLivingBase)null);
               this.markDirty();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               return true;
            }

            player.openGui(ThaumicHorizons.instance, 7, super.worldObj, super.xCoord, super.yCoord, super.zCoord);
            return true;
         }

         if(this.selfInfusions[1] == 0 && player.inventory.addItemStackToInventory(new ItemStack(ConfigBlocks.blockJar))) {
            super.worldObj.playSoundEffect((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "thaumcraft:whispers", 1.0F, super.worldObj.rand.nextFloat());
            Thaumcraft.proxy.blockSparkle(super.worldObj, super.xCoord, super.yCoord - 1, super.zCoord, 16777215, 20);
            Thaumcraft.proxy.blockSparkle(super.worldObj, super.xCoord, super.yCoord - 2, super.zCoord, 16777215, 20);
            EntityVillager villager = new EntityVillager(super.worldObj);
            villager.setProfession(possibleJar.getTagCompound().getInteger("villagerType"));
            this.setEntityContained(villager);
            this.mode = 0;
            this.markDirty();
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.selfInfusions = new int[12];
            --possibleJar.stackSize;
         }
      }

      return false;
   }

   public void updateEntity() {
      super.updateEntity();
      if(this.getEntityContained() != null && this.getEntityContained().isBurning()) {
         this.getEntityContained().extinguish();
      }

      if(super.worldObj.isRemote) {
         if(this.mode == 2) {
            this.doEffects();
         } else if(this.mode != 2 && this.startUp > 0.0F) {
            if(this.startUp > 0.0F) {
               this.startUp -= this.startUp / 10.0F;
            }

            if((double)this.startUp < 0.001D) {
               this.startUp = 0.0F;
            }
         }

         if(this.mode == 1) {
            Thaumcraft.proxy.blockSparkle(super.worldObj, super.xCoord, super.yCoord - 1, super.zCoord, 14184241, 1);
            Thaumcraft.proxy.blockSparkle(super.worldObj, super.xCoord, super.yCoord - 2, super.zCoord, 14184241, 1);
         }

      } else {
         if(this.mode == 0) {
            this.essentiaDemanded = new AspectList();
            if(this.getEntityContained() != null) {
               if(this.getEntityContained().getHealth() < this.getEntityContained().getMaxHealth()) {
                  if(this.getEntityContained().getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                     if(this.myEssentia.getAmount(Aspect.HEAL) > 0 && this.progress <= 0) {
                        this.getEntityContained().heal(8.0F);
                        this.myEssentia.remove(Aspect.HEAL, 1);
                        this.markDirty();
                        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                        this.progress += 40;
                     }

                     if(this.getEntityContained().getHealth() < this.getEntityContained().getMaxHealth() && this.essentiaDemanded.getAmount(Aspect.HEAL) < 1) {
                        this.essentiaDemanded.add(Aspect.HEAL, 1);
                     }

                     if(this.myEssentia.getAmount(Aspect.LIFE) > 0 && this.progress <= 0) {
                        this.getEntityContained().heal(4.0F);
                        this.myEssentia.remove(Aspect.LIFE, 1);
                        this.markDirty();
                        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                        this.progress += 50;
                     }

                     if(this.getEntityContained().getHealth() < this.getEntityContained().getMaxHealth() && this.essentiaDemanded.getAmount(Aspect.LIFE) < 1) {
                        this.essentiaDemanded.add(Aspect.LIFE, 1);
                     }
                  } else {
                     if(this.myEssentia.getAmount(Aspect.UNDEAD) > 0 && this.progress <= 0) {
                        this.getEntityContained().heal(8.0F);
                        this.myEssentia.remove(Aspect.UNDEAD, 1);
                        this.markDirty();
                        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                        this.progress += 40;
                     }

                     if(this.getEntityContained().getHealth() < this.getEntityContained().getMaxHealth() && this.essentiaDemanded.getAmount(Aspect.UNDEAD) < 1) {
                        this.essentiaDemanded.add(Aspect.UNDEAD, 1);
                     }

                     if(this.myEssentia.getAmount(Aspect.DEATH) > 0 && this.progress <= 0) {
                        this.getEntityContained().heal(4.0F);
                        this.myEssentia.remove(Aspect.DEATH, 1);
                        this.markDirty();
                        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                        this.progress += 50;
                     }

                     if(this.getEntityContained().getHealth() < this.getEntityContained().getMaxHealth() && this.essentiaDemanded.getAmount(Aspect.DEATH) < 1) {
                        this.essentiaDemanded.add(Aspect.DEATH, 1);
                     }
                  }
               }

               if(this.hasNegativeEffect(this.getEntityContained())) {
                  if(this.getEntityContained().getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                     if(this.myEssentia.getAmount(Aspect.HEAL) > 0 && this.progress <= 0) {
                        this.removeNegativeEffects(this.getEntityContained());
                        this.myEssentia.remove(Aspect.HEAL, 1);
                        this.markDirty();
                        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                        this.progress += 50;
                     } else if(this.essentiaDemanded.getAmount(Aspect.HEAL) < 1) {
                        this.essentiaDemanded.add(Aspect.HEAL, 1);
                     }
                  } else if(this.myEssentia.getAmount(Aspect.UNDEAD) > 0 && this.progress <= 0) {
                     this.removeNegativeEffects(this.getEntityContained());
                     this.myEssentia.remove(Aspect.UNDEAD, 1);
                     this.markDirty();
                     super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                     this.progress += 50;
                  } else if(this.essentiaDemanded.getAmount(Aspect.UNDEAD) < 1) {
                     this.essentiaDemanded.add(Aspect.UNDEAD, 1);
                  }
               }

               if(this.getEntityContained() instanceof EntityPlayer && ((EntityPlayer)this.getEntityContained()).getFoodStats().needFood()) {
                  if(this.myEssentia.getAmount(Aspect.HUNGER) > 0 && this.progress <= 0) {
                     ((EntityPlayer)this.getEntityContained()).getFoodStats().addStats(4, 2.0F);
                     this.markDirty();
                     super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                     this.progress += 50;
                  }

                  if(((EntityPlayer)this.getEntityContained()).getFoodStats().needFood() && this.essentiaDemanded.getAmount(Aspect.HUNGER) < 1) {
                     this.essentiaDemanded.add(Aspect.HUNGER, 1);
                  }
               }
            } else if(this.sample != null && this.sample.getItem() == ThaumicHorizons.itemCorpseEffigy) {
               this.mode = 3;
               this.essentiaDemanded = (new AspectList()).add(Aspect.LIFE, 8).add(Aspect.HEAL, 8);
               this.progress = 80;
               this.markDirty();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            } else if(this.sample != null && this.nutrients != null) {
               this.mode = 1;
               this.essentiaDemanded = (new AspectList()).add(Aspect.LIFE, 4);
               if(this.sample.getItem() == ThaumicHorizons.itemSyringeBloodSample && this.sample.hasTagCompound() && this.sample.stackTagCompound.getCompoundTag("critter") != null && this.sample.stackTagCompound.getCompoundTag("critter").getCompoundTag("ForgeData") != null) {
                  NBTTagCompound tlist = this.sample.stackTagCompound.getCompoundTag("critter").getCompoundTag("CreatureInfusion").getCompoundTag("InfusionCosts");
                  if(tlist != null && tlist.hasKey("Aspects")) {
                     NBTTagList aspex = tlist.getTagList("Aspects", 10);

                     for(int j = 0; j < aspex.tagCount(); ++j) {
                        NBTTagCompound rs = aspex.getCompoundTagAt(j);
                        if(rs.hasKey("key")) {
                           this.essentiaDemanded.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
                        }
                     }
                  }
               }

               this.progress = 40;
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               this.markDirty();
            }
         } else if(this.mode == 1) {
            if(this.sample == null && this.getEntityContained() == null) {
               this.progress = 0;
               this.mode = 0;
               this.essentiaDemanded = new AspectList();
               this.myEssentia = new AspectList();
               return;
            }

            if(this.getEntityContained() == null && this.myEssentia.getAmount(Aspect.LIFE) >= 4) {
               if(this.sample.getItem() == ThaumicHorizons.itemSyringeBloodSample) {
                  this.setEntityContained((EntityLivingBase)EntityList.createEntityFromNBT(this.sample.getTagCompound().getCompoundTag("critter"), super.worldObj));
                  if(this.getEntityContained() instanceof EntityTameable) {
                     ((EntityTameable)this.getEntityContained()).setTamed(false);
                  }
               } else {
                  this.setEntityContained((EntityLivingBase)EntityList.createEntityByID(((Integer)ThaumicHorizons.incarnationItems.get(this.sample.getItem())).intValue(), super.worldObj));
               }

               --this.sample.stackSize;
               if(this.sample.stackSize <= 0) {
                  this.sample = null;
               }

               --this.nutrients.stackSize;
               if(this.nutrients.stackSize <= 0) {
                  this.nutrients = null;
               }

               this.progress = 800;
               this.essentiaDemanded = new AspectList();
               this.myEssentia = new AspectList();
               if(this.getEntityContained() == null) {
                  this.progress = 0;
                  this.mode = 0;
                  super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                  this.markDirty();
                  return;
               }

               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               this.markDirty();
            } else if(this.progress <= 0) {
               this.mode = 0;
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               this.markDirty();
            }
         } else if(this.mode == 2) {
            ++this.count;
            if(this.checkSurroundings) {
               this.checkSurroundings = false;
               this.getSurroundings();
            } else if(this.count % this.countDelay == 0) {
               this.craftCycle();
               this.markDirty();
            }
         } else if(this.mode == 3) {
            if(this.sample == null || this.sample.getItem() != ThaumicHorizons.itemCorpseEffigy) {
               this.progress = 0;
               this.mode = 0;
               this.essentiaDemanded = new AspectList();
               this.myEssentia = new AspectList();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               this.markDirty();
               return;
            }

            if(this.progress <= 0 && this.myEssentia.getAmount(Aspect.LIFE) >= 8 && this.myEssentia.getAmount(Aspect.HEAL) >= 8) {
               super.worldObj.playSoundEffect((double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, "thaumcraft:wand", 1.0F, super.worldObj.rand.nextFloat());
               Thaumcraft.proxy.blockSparkle(super.worldObj, super.xCoord, super.yCoord - 2, super.zCoord, 16720418, 20);
               Thaumcraft.proxy.blockSparkle(super.worldObj, super.xCoord, super.yCoord - 1, super.zCoord, 16720418, 20);
               this.mode = 4;
               this.selfInfusionHealth = 20.0F;
               this.sample = null;
               this.essentiaDemanded = new AspectList();
               this.myEssentia = new AspectList();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               this.markDirty();
            }
         }

         if(this.mode != 2 && this.needsEssentia()) {
            this.tryDrawAllEssentia();
         }

         if(this.progress > 0) {
            --this.progress;
            this.markDirty();
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         }

      }
   }

   boolean needsEssentia() {
      this.currentlySucking = null;
      if(this.progress > 0) {
         return false;
      } else {
         Aspect[] var1 = this.essentiaDemanded.getAspects();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Aspect asp = var1[var3];
            if(this.myEssentia.getAmount(asp) < this.essentiaDemanded.getAmount(asp)) {
               this.currentlySucking = asp;
               break;
            }
         }

         return this.currentlySucking != null;
      }
   }

   boolean tryDrawAllEssentia() {
      boolean drew = false;
      TileEntity conn = super.worldObj.getTileEntity(super.xCoord - 1, super.yCoord - 3, super.zCoord);
      if(conn != null && conn instanceof TileVatConnector) {
         drew |= this.tryDrawEssentia((TileVatConnector)conn);
      }

      conn = super.worldObj.getTileEntity(super.xCoord + 1, super.yCoord - 3, super.zCoord);
      if(conn != null && conn instanceof TileVatConnector) {
         drew |= this.tryDrawEssentia((TileVatConnector)conn);
      }

      conn = super.worldObj.getTileEntity(super.xCoord, super.yCoord - 3, super.zCoord - 1);
      if(conn != null && conn instanceof TileVatConnector) {
         drew |= this.tryDrawEssentia((TileVatConnector)conn);
      }

      conn = super.worldObj.getTileEntity(super.xCoord, super.yCoord - 3, super.zCoord + 1);
      if(conn != null && conn instanceof TileVatConnector) {
         drew |= this.tryDrawEssentia((TileVatConnector)conn);
      }

      return drew;
   }

   boolean tryDrawEssentia(TileVatConnector conn) {
      TileEntity te = null;
      IEssentiaTransport ic = null;
      ForgeDirection[] var4 = ForgeDirection.VALID_DIRECTIONS;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ForgeDirection dir = var4[var6];
         te = ThaumcraftApiHelper.getConnectableTile(super.worldObj, conn.xCoord, conn.yCoord, conn.zCoord, dir);
         if(te != null) {
            ic = (IEssentiaTransport)te;
            if(ic.getEssentiaAmount(dir.getOpposite()) > 0 && ic.getSuctionAmount(dir.getOpposite()) < this.getSuctionAmount((ForgeDirection)null) && this.getSuctionAmount((ForgeDirection)null) >= ic.getMinimumSuction()) {
               Aspect[] var8 = this.essentiaDemanded.getAspects();
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  Aspect asp = var8[var10];
                  if(this.mode == 2 || this.myEssentia.getAmount(asp) < this.essentiaDemanded.getAmount(asp)) {
                     int ess = ic.takeEssentia(asp, 1, dir.getOpposite());
                     if(ess > 0) {
                        this.addToContainer(asp, ess);
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   boolean hasNegativeEffect(EntityLivingBase ent) {
      return ent.getActivePotionEffect(Potion.poison) != null?true:(ent.getActivePotionEffect(Potion.blindness) != null?true:(ent.getActivePotionEffect(Potion.hunger) != null?true:(ent.getActivePotionEffect(Potion.weakness) != null?true:(ent.getActivePotionEffect(Potion.wither) != null?true:(ent.getActivePotionEffect(Potion.confusion) != null?true:(ent.getActivePotionEffect(Potion.digSlowdown) != null?true:(ent.getActivePotionEffect(Potion.moveSlowdown) != null?true:(ent.getActivePotionEffect(Potion.potionTypes[Config.potionBlurredID]) != null?true:(ent.getActivePotionEffect(Potion.potionTypes[Config.potionInfVisExhaustID]) != null?true:(ent.getActivePotionEffect(Potion.potionTypes[Config.potionTaintPoisonID]) != null?true:(ent.getActivePotionEffect(Potion.potionTypes[Config.potionThaumarhiaID]) != null?true:ent.getActivePotionEffect(Potion.potionTypes[Config.potionVisExhaustID]) != null)))))))))));
   }

   void removeNegativeEffects(EntityLivingBase ent) {
      ent.removePotionEffect(Config.potionBlurredID);
      ent.removePotionEffect(Config.potionInfVisExhaustID);
      ent.removePotionEffect(Config.potionTaintPoisonID);
      ent.removePotionEffect(Config.potionThaumarhiaID);
      ent.removePotionEffect(Config.potionVisExhaustID);
      ent.removePotionEffect(Potion.blindness.id);
      ent.removePotionEffect(Potion.confusion.id);
      ent.removePotionEffect(Potion.digSlowdown.id);
      ent.removePotionEffect(Potion.hunger.id);
      ent.removePotionEffect(Potion.moveSlowdown.id);
      ent.removePotionEffect(Potion.hunger.id);
      ent.removePotionEffect(Potion.poison.id);
      ent.removePotionEffect(Potion.weakness.id);
      ent.removePotionEffect(Potion.wither.id);
   }

   public boolean jarCritter(ItemStack possibleJar, EntityPlayer player) {
      ItemStack jar = new ItemStack(ThaumicHorizons.blockJar);
      NBTTagCompound entityData = new NBTTagCompound();
      entityData.setString("id", EntityList.getEntityString(this.getEntityContained()));
      this.getEntityContained().writeToNBT(entityData);
      jar.setTagCompound(entityData);
      jar.getTagCompound().setString("jarredCritterName", this.getEntityContained().getCommandSenderName());
      jar.getTagCompound().setBoolean("isSoul", false);
      if(player.inventory.addItemStackToInventory(jar)) {
         --possibleJar.stackSize;
         this.setEntityContained((EntityLivingBase)null);
         this.markDirty();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         return true;
      } else {
         return false;
      }
   }

   public EntityLivingBase getEntity() {
      return this.getEntityContained();
   }

   public void startInfusion(EntityPlayer player) {
      this.getSurroundings();
      ArrayList components = new ArrayList();
      Iterator recipe = this.pedestals.iterator();

      while(recipe.hasNext()) {
         ChunkCoordinates i = (ChunkCoordinates)recipe.next();
         TileEntity te = super.worldObj.getTileEntity(i.posX, i.posY, i.posZ);
         if(te != null && te instanceof TilePedestal) {
            TilePedestal ped = (TilePedestal)te;
            if(ped.getStackInSlot(0) != null) {
               components.add(ped.getStackInSlot(0).copy());
            }
         }
      }

      if(components.size() != 0) {
         int var7;
         ItemStack ing;
         ItemStack[] var12;
         int var14;
         if(this.mode != 4) {
            CreatureInfusionRecipe var10 = ThaumicHorizons.getCreatureInfusion(this.getEntityContained(), components, player);
            if(var10 != null && (var10.getID((Class)null) == 0 || !((EntityInfusionProperties)this.getEntityContained().getExtendedProperties("CreatureInfusion")).hasInfusion(var10.getID((Class)null)))) {
               if(!(var10.getRecipeOutput() instanceof NBTTagCompound) || ((NBTTagCompound)var10.getRecipeOutput()).getInteger("instilledLoyalty") == 0 || ((EntityLiving)this.entityContained).tasks.taskEntries.size() != 0) {
                  this.recipeType = 0;
                  this.recipeIngredients = new ArrayList();
                  var12 = var10.getComponents();
                  var14 = var12.length;

                  for(var7 = 0; var7 < var14; ++var7) {
                     ing = var12[var7];
                     this.recipeIngredients.add(ing.copy());
                  }

                  if(var10.getRecipeOutput(this.getEntityContained().getClass()) instanceof Object[]) {
                     Object[] var13 = (Object[])((Object[])var10.getRecipeOutput(this.getEntityContained().getClass()));
                     this.recipeOutputLabel = (String)var13[0];
                     this.recipeOutput = (NBTBase)var13[1];
                  } else {
                     this.recipeOutput = var10.getRecipeOutput(this.getEntityContained().getClass());
                  }

                  this.recipeInstability = var10.getInstability(this.getEntityContained().getClass());
                  this.essentiaDemanded = var10.getAspects(this.getEntityContained().getClass()).copy();
                  this.myEssentia = var10.getAspects(this.getEntityContained().getClass()).copy();
                  this.recipePlayer = player.getCommandSenderName();
                  this.instability = this.symmetry + this.recipeInstability;
                  this.mode = 2;
                  super.worldObj.playSoundEffect((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, "thaumcraft:craftstart", 0.5F, 1.0F);
                  super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                  this.markDirty();
               }
            }
         } else {
            SelfInfusionRecipe var9 = ThaumicHorizons.getSelfInfusion(components, player);
            if(var9 != null) {
               for(int var11 = 0; var11 < this.selfInfusions.length; ++var11) {
                  if(this.selfInfusions[var11] == var9.getID()) {
                     return;
                  }
               }

               this.recipeType = 1;
               this.recipeIngredients = new ArrayList();
               var12 = var9.getComponents();
               var14 = var12.length;

               for(var7 = 0; var7 < var14; ++var7) {
                  ing = var12[var7];
                  this.recipeIngredients.add(ing.copy());
               }

               this.recipeOutputLabel = "";
               this.recipeOutput = Integer.valueOf(var9.getID());
               this.recipeInstability = var9.getInstability();
               this.myEssentia = var9.getAspects().copy();
               this.essentiaDemanded = var9.getAspects().copy();
               this.recipePlayer = player.getCommandSenderName();
               this.instability = this.symmetry + this.recipeInstability;
               this.mode = 2;
               super.worldObj.playSoundEffect((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, "thaumcraft:craftstart", 0.5F, 1.0F);
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               this.markDirty();
            }
         }
      }
   }

   public boolean validLocation() {
      return true;
   }

   private void getSurroundings() {
      ArrayList stuff = new ArrayList();
      this.pedestals.clear();

      try {
         boolean cc1;
         int items;
         int x;
         int z;
         int bi;
         for(int e = -12; e <= 12; ++e) {
            for(int cc = -12; cc <= 12; ++cc) {
               cc1 = false;

               for(items = -5; items <= 10; ++items) {
                  if(e != 0 || cc != 0) {
                     x = super.xCoord + e;
                     z = super.yCoord - items;
                     bi = super.zCoord + cc;
                     TileEntity xx = super.worldObj.getTileEntity(x, z, bi);
                     if(!cc1 && items > 0 && Math.abs(e) <= 8 && Math.abs(cc) <= 8 && xx != null && xx instanceof TilePedestal) {
                        this.pedestals.add(new ChunkCoordinates(x, z, bi));
                        cc1 = true;
                     } else {
                        Block zz = super.worldObj.getBlock(x, z, bi);
                        if(zz == Blocks.skull || zz instanceof IInfusionStabiliser && ((IInfusionStabiliser)zz).canStabaliseInfusion(this.getWorldObj(), x, z, bi)) {
                           stuff.add(new ChunkCoordinates(x, z, bi));
                        }
                     }
                  }
               }
            }
         }

         this.symmetry = 0;
         Iterator var12 = this.pedestals.iterator();

         int var20;
         while(var12.hasNext()) {
            ChunkCoordinates var14 = (ChunkCoordinates)var12.next();
            cc1 = false;
            items = super.xCoord - var14.posX;
            x = super.zCoord - var14.posZ;
            TileEntity var18 = super.worldObj.getTileEntity(var14.posX, var14.posY, var14.posZ);
            if(var18 != null && var18 instanceof TilePedestal) {
               this.symmetry += 2;
               if(((TilePedestal)var18).getStackInSlot(0) != null) {
                  ++this.symmetry;
                  cc1 = true;
               }
            }

            bi = super.xCoord + items;
            var20 = super.zCoord + x;
            var18 = super.worldObj.getTileEntity(bi, var14.posY, var20);
            if(var18 != null && var18 instanceof TilePedestal) {
               this.symmetry -= 2;
               if(((TilePedestal)var18).getStackInSlot(0) != null && cc1) {
                  --this.symmetry;
               }
            }
         }

         float var13 = 0.0F;
         Iterator var15 = stuff.iterator();

         while(var15.hasNext()) {
            ChunkCoordinates var17 = (ChunkCoordinates)var15.next();
            boolean var16 = false;
            x = super.xCoord - var17.posX;
            z = super.zCoord - var17.posZ;
            Block var19 = super.worldObj.getBlock(var17.posX, var17.posY, var17.posZ);
            if(var19 == Blocks.skull || var19 instanceof IInfusionStabiliser && ((IInfusionStabiliser)var19).canStabaliseInfusion(this.getWorldObj(), var17.posX, var17.posY, var17.posZ)) {
               var13 += 0.1F;
            }

            var20 = super.xCoord + x;
            int var21 = super.zCoord + z;
            var19 = super.worldObj.getBlock(var20, var17.posY, var21);
            if(var19 == Blocks.skull || var19 instanceof IInfusionStabiliser && ((IInfusionStabiliser)var19).canStabaliseInfusion(this.getWorldObj(), var17.posX, var17.posY, var17.posZ)) {
               var13 -= 0.2F;
            }
         }

         this.symmetry = (int)((float)this.symmetry + var13);
      } catch (Exception var11) {
         ;
      }

   }

   private void doEffects() {
      if(this.mode == 2) {
         if(this.craftCount == 0) {
            super.worldObj.playSound((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, "thaumcraft:infuserstart", 0.5F, 1.0F, false);
         } else if(this.craftCount % 65 == 0) {
            super.worldObj.playSound((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, "thaumcraft:infuser", 0.5F, 1.0F, false);
         }

         ++this.craftCount;
         Thaumcraft.proxy.blockRunes(super.worldObj, (double)super.xCoord, (double)(super.yCoord - 2), (double)super.zCoord, 0.5F + super.worldObj.rand.nextFloat() * 0.2F, 0.1F, 0.7F + super.worldObj.rand.nextFloat() * 0.3F, 25, -0.03F);
      } else if(this.craftCount > 0) {
         this.craftCount -= 2;
         if(this.craftCount < 0) {
            this.craftCount = 0;
         }

         if(this.craftCount > 50) {
            this.craftCount = 50;
         }
      }

      if(this.mode == 2 && this.startUp != 1.0F) {
         if(this.startUp < 1.0F) {
            this.startUp += Math.max(this.startUp / 10.0F, 0.001F);
         }

         if((double)this.startUp > 0.999D) {
            this.startUp = 1.0F;
         }
      }

      String[] var1 = (String[])((String[])this.sourceFX.keySet().toArray(new String[0]));
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String fxk = var1[var3];
         TileVat.SourceFX fx = (TileVat.SourceFX)this.sourceFX.get(fxk);
         if(fx.ticks <= 0) {
            this.sourceFX.remove(fxk);
         } else {
            if(fx.loc.posX == super.xCoord && fx.loc.posY == super.yCoord && fx.loc.posZ == super.zCoord) {
               Entity var11 = super.worldObj.getEntityByID(fx.color);
               if(var11 != null) {
                  for(int var12 = 0; var12 < Thaumcraft.proxy.particleCount(2); ++var12) {
                     Thaumcraft.proxy.drawInfusionParticles4(super.worldObj, var11.posX + (double)((super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * var11.width), var11.boundingBox.minY + (double)(super.worldObj.rand.nextFloat() * var11.height), var11.posZ + (double)((super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * var11.width), super.xCoord, super.yCoord, super.zCoord);
                  }
               }
            } else {
               TileEntity tile = super.worldObj.getTileEntity(fx.loc.posX, fx.loc.posY, fx.loc.posZ);
               if(tile instanceof TilePedestal) {
                  ItemStack is = ((TilePedestal)tile).getStackInSlot(0);
                  if(is != null) {
                     if(super.worldObj.rand.nextInt(3) == 0) {
                        Thaumcraft.proxy.drawInfusionParticles3(super.worldObj, (double)((float)fx.loc.posX + super.worldObj.rand.nextFloat()), (double)((float)fx.loc.posY + super.worldObj.rand.nextFloat() + 1.0F), (double)((float)fx.loc.posZ + super.worldObj.rand.nextFloat()), super.xCoord, super.yCoord, super.zCoord);
                     } else {
                        Item bi = is.getItem();
                        int md = is.getItemDamage();
                        int a;
                        if(is.getItemSpriteNumber() == 0 && bi instanceof ItemBlock) {
                           for(a = 0; a < Thaumcraft.proxy.particleCount(2); ++a) {
                              Thaumcraft.proxy.drawInfusionParticles2(super.worldObj, (double)((float)fx.loc.posX + super.worldObj.rand.nextFloat()), (double)((float)fx.loc.posY + super.worldObj.rand.nextFloat() + 1.0F), (double)((float)fx.loc.posZ + super.worldObj.rand.nextFloat()), super.xCoord, super.yCoord, super.zCoord, Block.getBlockFromItem(bi), md);
                           }
                        } else {
                           for(a = 0; a < Thaumcraft.proxy.particleCount(2); ++a) {
                              Thaumcraft.proxy.drawInfusionParticles1(super.worldObj, (double)((float)fx.loc.posX + 0.4F + super.worldObj.rand.nextFloat() * 0.2F), (double)((float)fx.loc.posY + 1.23F + super.worldObj.rand.nextFloat() * 0.2F), (double)((float)fx.loc.posZ + 0.4F + super.worldObj.rand.nextFloat() * 0.2F), super.xCoord, super.yCoord, super.zCoord, bi, md);
                           }
                        }
                     }
                  }
               } else {
                  fx.ticks = 0;
               }
            }

            --fx.ticks;
            this.sourceFX.put(fxk, fx);
         }
      }

      if(this.mode == 2 && this.instability > 0 && super.worldObj.rand.nextInt(200) <= this.instability) {
         Thaumcraft.proxy.nodeBolt(super.worldObj, (float)super.xCoord + 0.5F, (float)super.yCoord + 0.5F, (float)super.zCoord + 0.5F, (float)super.xCoord + 0.5F + (super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 2.0F, (float)super.yCoord + 0.5F + (super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 2.0F, (float)super.zCoord + 0.5F + (super.worldObj.rand.nextFloat() - super.worldObj.rand.nextFloat()) * 2.0F);
      }

   }

   public void craftCycle() {
      if(this.instability > 0 && super.worldObj.rand.nextInt(500) <= this.instability) {
         switch(super.worldObj.rand.nextInt(21)) {
         case 0:
         case 2:
         case 10:
         case 13:
            this.inEvEjectItem(0);
            break;
         case 1:
         case 11:
            this.inEvEjectItem(2);
            break;
         case 3:
         case 8:
         case 14:
            this.inEvZap(false);
            break;
         case 4:
         case 15:
            this.inEvEjectItem(5);
            break;
         case 5:
         case 16:
            this.inEvHarm(false);
            break;
         case 6:
         case 17:
            this.inEvEjectItem(1);
            break;
         case 7:
            this.inEvEjectItem(4);
            break;
         case 9:
            super.worldObj.createExplosion((Entity)null, (double)((float)super.xCoord + 0.5F), (double)((float)super.yCoord + 0.5F), (double)((float)super.zCoord + 0.5F), 1.5F + super.worldObj.rand.nextFloat(), false);
            break;
         case 12:
            this.inEvZap(true);
            break;
         case 18:
            this.inEvHarm(true);
            break;
         case 19:
            this.inEvEjectItem(3);
            break;
         case 20:
            this.inEvWarp();
         }
      }

      float te;
      float a;
      if(this.instability > 0 && this.entityContained != null) {
         te = 999.0F;
         if(!super.worldObj.isRemote) {
            a = (float)VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord + 1, super.zCoord, Aspect.EARTH, 100);
            if(a < te) {
               te = a;
            }

            a = (float)VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord + 1, super.zCoord, Aspect.WATER, 100);
            if(a < te) {
               te = a;
            }

            a = (float)VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord + 1, super.zCoord, Aspect.ORDER, 100);
            if(a < te) {
               te = a;
            }
         }

         this.getEntityContained().setHealth(this.getEntityContained().getHealth() - (float)this.instability / 10.0F / (5.0F + te));
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         if(this.getEntityContained().getHealth() <= 0.0F) {
            this.killSubject();
            return;
         }
      } else if(this.instability > 0) {
         te = 999.0F;
         if(!super.worldObj.isRemote) {
            a = (float)VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord + 1, super.zCoord, Aspect.EARTH, 100);
            if(a < te) {
               te = a;
            }

            a = (float)VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord + 1, super.zCoord, Aspect.WATER, 100);
            if(a < te) {
               te = a;
            }

            a = (float)VisNetHandler.drainVis(super.worldObj, super.xCoord, super.yCoord + 1, super.zCoord, Aspect.ORDER, 100);
            if(a < te) {
               te = a;
            }
         }

         this.selfInfusionHealth -= (float)this.instability / 10.0F / (5.0F + te);
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         if(this.selfInfusionHealth <= 0.0F) {
            this.killSubject();
            return;
         }
      }

      int var8;
      if(this.essentiaDemanded.visSize() > 0) {
         Aspect[] var10 = this.essentiaDemanded.getAspects();
         var8 = var10.length;

         for(int var6 = 0; var6 < var8; ++var6) {
            Aspect var9 = var10[var6];
            if(this.essentiaDemanded.getAmount(var9) > 0) {
               this.currentlySucking = var9;
               if(this.tryDrawAllEssentia()) {
                  super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                  this.markDirty();
                  return;
               }

               if(super.worldObj.rand.nextInt(100 - this.recipeInstability * 3) == 0) {
                  ++this.instability;
               }

               if(this.instability > 25) {
                  this.instability = 25;
               }

               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
               this.markDirty();
               break;
            }
         }

         this.checkSurroundings = true;
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         this.markDirty();
      } else if(this.recipeIngredients.size() <= 0) {
         this.instability = 0;
         this.craftingFinish(this.recipeOutput, this.recipeOutputLabel);
         this.recipeOutput = null;
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         this.markDirty();
      } else {
         var8 = 0;

         label131:
         while(var8 < this.recipeIngredients.size()) {
            Iterator var3 = this.pedestals.iterator();

            ChunkCoordinates cc;
            TileEntity var11;
            do {
               if(!var3.hasNext()) {
                  ++var8;
                  continue label131;
               }

               cc = (ChunkCoordinates)var3.next();
               var11 = super.worldObj.getTileEntity(cc.posX, cc.posY, cc.posZ);
            } while(var11 == null || !(var11 instanceof TilePedestal) || ((TilePedestal)var11).getStackInSlot(0) == null || !InfusionRecipe.areItemStacksEqual(((TilePedestal)var11).getStackInSlot(0), (ItemStack)this.recipeIngredients.get(var8), true));

            if(this.itemCount == 0) {
               this.itemCount = 5;
               SimpleNetworkWrapper var10000 = PacketHandler.INSTANCE;
               PacketInfusionFX var10001 = new PacketInfusionFX(super.xCoord, super.yCoord - 2, super.zCoord, (byte)(super.xCoord - cc.posX), (byte)(super.yCoord - cc.posY - 2), (byte)(super.zCoord - cc.posZ), 0);
               double var10005 = (double)super.xCoord;
               double var10006 = (double)super.yCoord;
               double var10007 = (double)super.zCoord;
               TargetPoint var10002 = new TargetPoint(this.getWorldObj().provider.dimensionId, var10005, var10006, var10007, 32.0D);
               var10000.sendToAllAround(var10001, var10002);
            } else if(this.itemCount-- <= 1) {
               ItemStack is = ((TilePedestal)var11).getStackInSlot(0).getItem().getContainerItem(((TilePedestal)var11).getStackInSlot(0));
               ((TilePedestal)var11).setInventorySlotContents(0, is == null?null:is.copy());
               this.recipeIngredients.remove(var8);
            }

            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.markDirty();
            return;
         }

      }
   }

   private void inEvZap(boolean all) {
      List targets = super.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand(10.0D, 10.0D, 10.0D));
      if(targets != null && targets.size() > 0) {
         Iterator var3 = targets.iterator();

         while(var3.hasNext()) {
            Entity target = (Entity)var3.next();
            thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float)super.xCoord + 0.5F, (float)super.yCoord + 0.5F, (float)super.zCoord + 0.5F, (float)target.posX, (float)target.posY + target.height / 2.0F, (float)target.posZ), new TargetPoint(super.worldObj.provider.dimensionId, (double)super.xCoord, (double)super.yCoord, (double)super.zCoord, 32.0D));
            target.attackEntityFrom(DamageSource.magic, (float)(4 + super.worldObj.rand.nextInt(4)));
            if(!all) {
               break;
            }
         }
      }

   }

   private void inEvHarm(boolean all) {
      List targets = super.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand(10.0D, 10.0D, 10.0D));
      if(targets != null && targets.size() > 0) {
         Iterator var3 = targets.iterator();

         while(var3.hasNext()) {
            EntityLivingBase target = (EntityLivingBase)var3.next();
            if(super.worldObj.rand.nextBoolean()) {
               target.addPotionEffect(new PotionEffect(Config.potionTaintPoisonID, 120, 0, false));
            } else {
               PotionEffect pe = new PotionEffect(Config.potionVisExhaustID, 2400, 0, true);
               pe.getCurativeItems().clear();
               target.addPotionEffect(pe);
            }

            if(!all) {
               break;
            }
         }
      }

   }

   private void inEvWarp() {
      List targets = super.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand(10.0D, 10.0D, 10.0D));
      if(targets != null && targets.size() > 0) {
         EntityPlayer target = (EntityPlayer)targets.get(super.worldObj.rand.nextInt(targets.size()));
         if(super.worldObj.rand.nextFloat() < 0.25F) {
            Thaumcraft.addStickyWarpToPlayer(target, 1);
         } else {
            Thaumcraft.addWarpToPlayer(target, 1 + super.worldObj.rand.nextInt(5), true);
         }
      }

   }

   private void inEvEjectItem(int type) {
      for(int q = 0; q < 50 && this.pedestals.size() > 0; ++q) {
         ChunkCoordinates cc = (ChunkCoordinates)this.pedestals.get(super.worldObj.rand.nextInt(this.pedestals.size()));
         TileEntity te = super.worldObj.getTileEntity(cc.posX, cc.posY, cc.posZ);
         if(te != null && te instanceof TilePedestal && ((TilePedestal)te).getStackInSlot(0) != null) {
            if(type >= 3 && type != 5) {
               ((TilePedestal)te).setInventorySlotContents(0, (ItemStack)null);
            } else {
               InventoryUtils.dropItems(super.worldObj, cc.posX, cc.posY, cc.posZ);
            }

            if(type != 1 && type != 3) {
               if(type != 2 && type != 4) {
                  if(type == 5) {
                     super.worldObj.createExplosion((Entity)null, (double)((float)cc.posX + 0.5F), (double)((float)cc.posY + 0.5F), (double)((float)cc.posZ + 0.5F), 1.0F, false);
                  }
               } else {
                  super.worldObj.setBlock(cc.posX, cc.posY + 1, cc.posZ, ConfigBlocks.blockFluxGas, 7, 3);
                  super.worldObj.playSoundEffect((double)cc.posX, (double)cc.posY, (double)cc.posZ, "random.fizz", 0.3F, 1.0F);
               }
            } else {
               super.worldObj.setBlock(cc.posX, cc.posY + 1, cc.posZ, ConfigBlocks.blockFluxGoo, 7, 3);
               super.worldObj.playSoundEffect((double)cc.posX, (double)cc.posY, (double)cc.posZ, "game.neutral.swim", 0.3F, 1.0F);
            }

            super.worldObj.addBlockEvent(cc.posX, cc.posY, cc.posZ, ConfigBlocks.blockStoneDevice, 11, 0);
            thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockZap((float)super.xCoord + 0.5F, (float)super.yCoord + 0.5F, (float)super.zCoord + 0.5F, (float)cc.posX + 0.5F, (float)cc.posY + 1.5F, (float)cc.posZ + 0.5F), new TargetPoint(super.worldObj.provider.dimensionId, (double)super.xCoord, (double)super.yCoord, (double)super.zCoord, 32.0D));
            return;
         }
      }

   }

   public void craftingFinish(Object out, String label) {
      if(this.recipeType == 0) {
         if(out instanceof Integer) {
            EntityLivingBase i = null;
            if(((Integer)out).intValue() < 0) {
               i = (EntityLivingBase)EntityList.createEntityByID(-((Integer)out).intValue(), super.worldObj);
            }

            ModContainer map = (ModContainer)Loader.instance().getIndexedModList().get("ThaumicHorizons");

            try {
               i = (EntityLivingBase)((EntityLivingBase)EntityRegistry.instance().lookupModSpawn(map, ((Integer)out).intValue()).getEntityClass().getConstructor(new Class[]{World.class}).newInstance(new Object[]{super.worldObj}));
            } catch (InvocationTargetException var8) {
               var8.getCause().printStackTrace();
            } catch (Exception var9) {
               var9.printStackTrace();
            }

            i.copyLocationAndAnglesFrom(this.getEntityContained());
            i.copyDataFrom(this.getEntityContained(), true);
            if(i instanceof IEntityInfusedStats) {
               ((IEntityInfusedStats)i).resetStats();
            }

            this.setEntityContained(i);
         } else if(out instanceof NBTBase) {
            NBTTagCompound var11 = (NBTTagCompound)out;
            HashMultimap var10 = HashMultimap.create();
            if(var11.getDouble("generic.movementSpeed") > 0.0D) {
               var10.put("generic.movementSpeed", new AttributeModifier("generic.movementSpeed", var11.getDouble("generic.movementSpeed") / 10.0D, 1));
            }

            if(var11.getDouble("generic.maxHealth") > 0.0D) {
               var10.put("generic.maxHealth", new AttributeModifier("generic.maxHealth", var11.getDouble("generic.maxHealth"), 1));
            }

            if(var11.getDouble("generic.attackDamage") > 0.0D) {
               var10.put("generic.attackDamage", new AttributeModifier("generic.attackDamage", var11.getDouble("generic.attackDamage"), 1));
            }

            if(var10.size() > 0) {
               this.getEntityContained().getAttributeMap().applyAttributeModifiers(var10);
            }

            Set keys = var11.func_150296_c();
            Iterator var6 = keys.iterator();

            while(var6.hasNext()) {
               String s = (String)var6.next();
               if(!s.substring(0, 8).equals("generic.")) {
                  ((EntityInfusionProperties)this.getEntityContained().getExtendedProperties("CreatureInfusion")).addInfusion(var11.getInteger(s));
                  if(var11.getInteger(s) == 7) {
                     ((EntityInfusionProperties)this.getEntityContained().getExtendedProperties("CreatureInfusion")).setOwner(this.recipePlayer);
                  }
               }
            }
         }

         ((EntityInfusionProperties)this.getEntityContained().getExtendedProperties("CreatureInfusion")).addCost(this.myEssentia);
         if(this.entityContained instanceof EntityLiving) {
            ((EntityLiving)this.entityContained).func_110163_bv();
         }

         this.mode = 0;
      } else {
         for(int var12 = 0; var12 < this.selfInfusions.length; ++var12) {
            if(this.selfInfusions[var12] == 0) {
               this.selfInfusions[var12] = ((Integer)this.recipeOutput).intValue();
               break;
            }
         }

         this.mode = 4;
      }

      PacketHandler.INSTANCE.sendToAllAround(new PacketFXInfusionDone(super.xCoord, super.yCoord - 1, super.zCoord), new TargetPoint(super.worldObj.provider.dimensionId, (double)super.xCoord, (double)super.yCoord, (double)super.zCoord, 32.0D));
      this.essentiaDemanded = new AspectList();
      this.myEssentia = new AspectList();
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      this.markDirty();
   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("mode", this.mode);
      nbttagcompound.setInteger("progress", this.progress);
      nbttagcompound.setShort("instability", (short)this.instability);
      if(this.currentlySucking != null) {
         nbttagcompound.setString("sucking", this.currentlySucking.getTag());
      } else {
         nbttagcompound.setString("sucking", "");
      }

      NBTTagList tlist = new NBTTagList();
      nbttagcompound.setTag("myEssentia", tlist);
      Aspect[] entityData = this.myEssentia.getAspects();
      int item = entityData.length;

      int itemtoo;
      Aspect aspect;
      NBTTagCompound f;
      for(itemtoo = 0; itemtoo < item; ++itemtoo) {
         aspect = entityData[itemtoo];
         if(aspect != null) {
            f = new NBTTagCompound();
            f.setString("key", aspect.getTag());
            f.setInteger("amount", this.myEssentia.getAmount(aspect));
            tlist.appendTag(f);
         }
      }

      tlist = new NBTTagList();
      nbttagcompound.setTag("essentiaDemanded", tlist);
      entityData = this.essentiaDemanded.getAspects();
      item = entityData.length;

      for(itemtoo = 0; itemtoo < item; ++itemtoo) {
         aspect = entityData[itemtoo];
         if(aspect != null) {
            f = new NBTTagCompound();
            f.setString("key", aspect.getTag());
            f.setInteger("amount", this.essentiaDemanded.getAmount(aspect));
            tlist.appendTag(f);
         }
      }

      NBTTagCompound var8 = new NBTTagCompound();
      if(this.getEntityContained() != null && !(this.getEntityContained() instanceof EntityPlayer)) {
         var8.setString("id", EntityList.getEntityString(this.getEntityContained()));
         this.getEntityContained().writeToNBT(var8);
      } else if(this.getEntityContained() != null) {
         var8.setString("id", "PLAYER");
         var8.setString("playerName", this.getEntityContained().getCommandSenderName());
      }

      nbttagcompound.setTag("entity", var8);
      NBTTagCompound var9 = new NBTTagCompound();
      if(this.sample != null) {
         this.sample.writeToNBT(var9);
      }

      nbttagcompound.setTag("sample", var9);
      NBTTagCompound var10 = new NBTTagCompound();
      if(this.nutrients != null) {
         this.nutrients.writeToNBT(var10);
      }

      nbttagcompound.setTag("nutrients", var10);
      nbttagcompound.setIntArray("selfInfusions", this.selfInfusions);
      nbttagcompound.setFloat("selfInfusionHealth", this.selfInfusionHealth);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.mode = nbttagcompound.getInteger("mode");
      this.progress = nbttagcompound.getInteger("progress");
      this.instability = nbttagcompound.getShort("instability");
      this.currentlySucking = Aspect.getAspect(nbttagcompound.getString("sucking"));
      AspectList al = new AspectList();
      NBTTagList tlist = nbttagcompound.getTagList("myEssentia", 10);

      int j;
      NBTTagCompound rs;
      for(j = 0; j < tlist.tagCount(); ++j) {
         rs = tlist.getCompoundTagAt(j);
         if(rs.hasKey("key")) {
            al.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
         }
      }

      this.myEssentia = al.copy();
      al = new AspectList();
      tlist = nbttagcompound.getTagList("essentiaDemanded", 10);

      for(j = 0; j < tlist.tagCount(); ++j) {
         rs = tlist.getCompoundTagAt(j);
         if(rs.hasKey("key")) {
            al.add(Aspect.getAspect(rs.getString("key")), rs.getInteger("amount"));
         }
      }

      this.essentiaDemanded = al.copy();
      if(nbttagcompound.getCompoundTag("entity").getString("id").equals("PLAYER")) {
         this.setEntityContained(super.worldObj.getPlayerEntityByName(nbttagcompound.getCompoundTag("entity").getString("playerName")));
      } else {
         this.setEntityContained((EntityLivingBase)EntityList.createEntityFromNBT(nbttagcompound.getCompoundTag("entity"), super.worldObj));
      }

      this.sample = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("sample"));
      this.nutrients = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("nutrients"));
      this.selfInfusions = nbttagcompound.getIntArray("selfInfusions");
      if(this.selfInfusions.length == 0) {
         this.selfInfusions = new int[12];
      }

      this.selfInfusionHealth = nbttagcompound.getFloat("selfInfusionHealth");
   }

   public void readFromNBT(NBTTagCompound nbtCompound) {
      super.readFromNBT(nbtCompound);
      NBTTagList nbttaglist = nbtCompound.getTagList("recipein", 10);
      this.recipeIngredients = new ArrayList();

      for(int rot = 0; rot < nbttaglist.tagCount(); ++rot) {
         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(rot);
         byte b0 = nbttagcompound1.getByte("item");
         this.recipeIngredients.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
      }

      String var6 = nbtCompound.getString("rotype");
      if(var6 != null && var6.equals("@")) {
         this.recipeOutput = Integer.valueOf(nbtCompound.getInteger("recipeout"));
      } else if(var6 != null) {
         this.recipeOutputLabel = var6;
         this.recipeOutput = nbtCompound.getTag("recipeout");
      }

      this.recipeInstability = nbtCompound.getInteger("recipeinst");
      this.recipeType = nbtCompound.getInteger("recipetype");
      this.recipePlayer = nbtCompound.getString("recipeplayer");
      if(this.recipePlayer.isEmpty()) {
         this.recipePlayer = null;
      }

   }

   public void writeToNBT(NBTTagCompound nbtCompound) {
      super.writeToNBT(nbtCompound);
      if(this.recipeIngredients != null && this.recipeIngredients.size() > 0) {
         NBTTagList nbttaglist = new NBTTagList();
         int count = 0;
         Iterator var4 = this.recipeIngredients.iterator();

         while(var4.hasNext()) {
            ItemStack stack = (ItemStack)var4.next();
            if(stack != null) {
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               nbttagcompound1.setByte("item", (byte)count);
               stack.writeToNBT(nbttagcompound1);
               nbttaglist.appendTag(nbttagcompound1);
               ++count;
            }
         }

         nbtCompound.setTag("recipein", nbttaglist);
      }

      if(this.recipeOutput != null && this.recipeOutput instanceof Integer) {
         nbtCompound.setString("rotype", "@");
      }

      if(this.recipeOutput != null && this.recipeOutput instanceof NBTBase) {
         nbtCompound.setString("rotype", this.recipeOutputLabel);
      }

      if(this.recipeOutput != null && this.recipeOutput instanceof Integer) {
         nbtCompound.setTag("recipeout", new NBTTagInt(((Integer)this.recipeOutput).intValue()));
      }

      if(this.recipeOutput != null && this.recipeOutput instanceof NBTBase) {
         nbtCompound.setTag("recipeout", (NBTBase)this.recipeOutput);
      }

      nbtCompound.setInteger("recipeinst", this.recipeInstability);
      nbtCompound.setInteger("recipetype", this.recipeType);
      nbtCompound.setInteger("recipexp", this.recipeXP);
      if(this.recipePlayer == null) {
         nbtCompound.setString("recipeplayer", "");
      } else {
         nbtCompound.setString("recipeplayer", this.recipePlayer);
      }

   }

   public boolean isValidInfusionTarget() {
      if(this.getEntityContained() != null && this.getEntityContained().getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && !(this.getEntityContained() instanceof EntityPlayer) && !(this.getEntityContained() instanceof EntityGolem) && !(this.getEntityContained() instanceof EntityGolemBase) && !(this.getEntityContained() instanceof IMerchant) && !(this.getEntityContained() instanceof INpc)) {
         Iterator var1 = ThaumicHorizons.classBanList.iterator();

         Class clazz;
         do {
            if(!var1.hasNext()) {
               return true;
            }

            clazz = (Class)var1.next();
         } while(!this.getEntityContained().getClass().isAssignableFrom(clazz));

         return false;
      } else {
         return false;
      }
   }

   public void killMe() {
      if(this.entityContained != null) {
         this.killSubject();
      }

      EntityItem y;
      if(this.sample != null) {
         y = new EntityItem(super.worldObj, (double)super.xCoord + 0.5D, (double)super.yCoord + 1.5D, (double)super.zCoord - 0.5D, this.sample);
         super.worldObj.spawnEntityInWorld(y);
         this.sample = null;
      }

      if(this.nutrients != null) {
         y = new EntityItem(super.worldObj, (double)super.xCoord + 0.5D, (double)super.yCoord + 1.5D, (double)super.zCoord - 0.5D, this.nutrients);
         super.worldObj.spawnEntityInWorld(y);
         this.nutrients = null;
      }

      for(int var4 = 0; var4 < 4; ++var4) {
         for(int x = -1; x < 2; ++x) {
            for(int z = -1; z < 2; ++z) {
               if(x == 0 && z == 0) {
                  if(var4 != 0 && var4 != 3) {
                     super.worldObj.setBlock(super.xCoord + x, super.yCoord - var4, super.zCoord + z, Blocks.water, 0, 3);
                  } else {
                     super.worldObj.setBlock(super.xCoord + x, super.yCoord - var4, super.zCoord + z, ConfigBlocks.blockMetalDevice, 9, 3);
                  }
               } else if(var4 != 0 && var4 != 3) {
                  super.worldObj.setBlock(super.xCoord + x, super.yCoord - var4, super.zCoord + z, Blocks.glass, 0, 3);
               } else {
                  super.worldObj.setBlock(super.xCoord + x, super.yCoord - var4, super.zCoord + z, ConfigBlocks.blockWoodenDevice, 6, 3);
               }
            }
         }
      }

   }

   public void killSubject() {
      if(!super.worldObj.isRemote && (this.entityContained != null && !(this.entityContained instanceof EntityPlayer) || this.recipeType == 1)) {
         super.worldObj.createExplosion((Entity)null, (double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, 0.5F, false);

         for(int a = 0; a < 25; ++a) {
            int xx = super.xCoord + super.worldObj.rand.nextInt(8) - super.worldObj.rand.nextInt(8);
            int yy = super.yCoord + super.worldObj.rand.nextInt(8) - super.worldObj.rand.nextInt(8);
            int zz = super.zCoord + super.worldObj.rand.nextInt(8) - super.worldObj.rand.nextInt(8);
            if(super.worldObj.isAirBlock(xx, yy, zz)) {
               if(yy < super.yCoord) {
                  super.worldObj.setBlock(xx, yy, zz, ConfigBlocks.blockFluxGoo, 8, 3);
               } else {
                  super.worldObj.setBlock(xx, yy, zz, ConfigBlocks.blockFluxGas, 8, 3);
               }
            }
         }
      }

      this.selfInfusions = new int[12];
      this.setEntityContained((EntityLivingBase)null);
      this.mode = 0;
      this.currentlySucking = null;
      this.myEssentia = new AspectList();
      this.essentiaDemanded = new AspectList();
      this.progress = 0;
      this.symmetry = 0;
      this.instability = 0;
      this.craftCount = 0;
      this.count = 0;
      this.markDirty();
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
   }

   public int getSizeInventory() {
      return 2;
   }

   public ItemStack getStackInSlot(int slot) {
      return slot == 0?this.sample:this.nutrients;
   }

   public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
      ItemStack theStack;
      if(p_70298_1_ == 0) {
         theStack = this.sample;
      } else {
         theStack = this.nutrients;
      }

      if(theStack != null) {
         ItemStack outStack;
         if(theStack.stackSize <= p_70298_2_) {
            if(p_70298_1_ == 0) {
               outStack = this.sample.copy();
               this.sample = null;
            } else {
               outStack = this.nutrients.copy();
               this.nutrients = null;
            }

            return outStack;
         } else {
            outStack = theStack.splitStack(p_70298_2_);
            if(theStack.stackSize == 0) {
               if(p_70298_1_ == 0) {
                  this.sample = null;
               } else {
                  this.nutrients = null;
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

   public void setInventorySlotContents(int slot, ItemStack stack) {
      if(slot == 0) {
         this.sample = stack;
      } else {
         this.nutrients = stack;
      }

      this.markDirty();
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
   }

   public String getInventoryName() {
      return "container.vat";
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

   public boolean isItemValidForSlot(int slot, ItemStack stack) {
      return slot != 0?stack.getItem() == ThaumicHorizons.itemNutrients:stack.getItem() == ThaumicHorizons.itemSyringeBloodSample || stack.getItem() == Items.chicken || stack.getItem() == Items.beef || stack.getItem() == Items.porkchop;
   }

   public int[] getAccessibleSlotsFromSide(int side) {
      return new int[]{0, 1};
   }

   public boolean canInsertItem(int slot, ItemStack item, int side) {
      return this.isItemValidForSlot(slot, item);
   }

   public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
      return false;
   }

   public boolean isConnectable(ForgeDirection face) {
      return this.mode == 1?face == ForgeDirection.UP:false;
   }

   public boolean canInputFrom(ForgeDirection face) {
      return this.mode == 1?face == ForgeDirection.UP:false;
   }

   public boolean canOutputTo(ForgeDirection face) {
      return false;
   }

   public void setSuction(Aspect aspect, int amount) {}

   public Aspect getSuctionType(ForgeDirection face) {
      return this.mode != 2?null:this.currentlySucking;
   }

   public int getSuctionAmount(ForgeDirection face) {
      return this.essentiaDemanded.size() > 0?128:0;
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
      return this.mode != 2?(this.myEssentia.getAspects().length > 0 && this.myEssentia.getAspects()[0] != null?this.myEssentia:null):(this.essentiaDemanded.getAspects().length > 0 && this.essentiaDemanded.getAspects()[0] != null?this.essentiaDemanded:null);
   }

   public void setAspects(AspectList aspects) {}

   public boolean doesContainerAccept(Aspect tag) {
      return this.currentlySucking != null && tag.getTag().equals(this.currentlySucking.getTag());
   }

   public int addToContainer(Aspect tag, int amount) {
      if(this.mode != 2) {
         this.myEssentia.add(tag, amount);
      } else {
         this.essentiaDemanded.reduce(tag, amount);
      }

      this.clientEssentiaFX(tag);
      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
      this.markDirty();
      return 0;
   }

   public void clientEssentiaFX(Aspect tag) {
      SimpleNetworkWrapper var10000 = PacketHandler.INSTANCE;
      PacketFXEssentiaBubble var10001 = new PacketFXEssentiaBubble((double)super.xCoord + 0.5D, (double)(super.yCoord - 2), (double)super.zCoord + 0.5D, tag.getColor());
      double var10005 = (double)super.xCoord;
      double var10006 = (double)super.yCoord;
      double var10007 = (double)super.zCoord;
      TargetPoint var10002 = new TargetPoint(this.getWorldObj().provider.dimensionId, var10005, var10006, var10007, 32.0D);
      var10000.sendToAllAround(var10001, var10002);
   }

   public boolean takeFromContainer(Aspect tag, int amount) {
      return false;
   }

   public boolean takeFromContainer(AspectList ot) {
      return false;
   }

   public boolean doesContainerContainAmount(Aspect tag, int amount) {
      return this.containerContains(tag) >= amount;
   }

   public boolean doesContainerContain(AspectList ot) {
      return false;
   }

   public int containerContains(Aspect tag) {
      return this.myEssentia.getAmount(tag);
   }

   public EntityLivingBase getEntityContained() {
      return this.entityContained;
   }

   public void setEntityContained(EntityLivingBase newEntity) {
      this.entityContained = newEntity;
      if(this.entityContained != null) {
         this.entityContained.setLocationAndAngles((double)super.xCoord + 0.5D, (double)super.yCoord - 1.75D, (double)super.zCoord + 0.5D, 0.0F, 0.0F);
      }

   }

   public class SourceFX {

      public ChunkCoordinates loc;
      public int ticks;
      public int color;
      public int entity;


      public SourceFX(ChunkCoordinates loc, int ticks, int color) {
         this.loc = loc;
         this.ticks = ticks;
         this.color = color;
      }
   }
}
