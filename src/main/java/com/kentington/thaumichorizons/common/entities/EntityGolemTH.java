package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.client.lib.GolemTHTexture;
import com.kentington.thaumichorizons.common.entities.EntityLightningBoltFinite;
import com.kentington.thaumichorizons.common.entities.EnumGolemTHType;
import com.kentington.thaumichorizons.common.entities.ai.EntityAIFollowPlayer;
import com.kentington.thaumichorizons.common.lib.PacketFXBlocksplosion;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.ai.combat.AIAvoidCreeperSwell;
import thaumcraft.common.entities.ai.combat.AIGolemAttackOnCollide;
import thaumcraft.common.entities.ai.combat.AIHurtByTarget;
import thaumcraft.common.entities.ai.combat.AINearestAttackableTarget;
import thaumcraft.common.entities.ai.misc.AIOpenDoor;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.golems.EnumGolemType;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;

public class EntityGolemTH extends EntityGolemBase {

   public ResourceLocation texture = null;
   public Block blocky;
   public int md;
   public int ticksAlive = 0;
   public int voidCount = 0;
   public boolean berserk;
   public boolean kaboom;
   public EnumGolemTHType type;


   public EntityGolemTH(World par1World) {
      super(par1World);
      this.type = EnumGolemTHType.SAND;
   }

   public void loadGolem(double x, double y, double z, Block block, int md, int ticksAlive, boolean adv, boolean berserk, boolean kaboom) {
      this.setPosition(x, y, z);
      this.md = md;
      this.blocky = block;
      this.ticksAlive = ticksAlive;
      this.advanced = adv;
      this.berserk = berserk;
      this.kaboom = kaboom;
      this.loadGolemTexturesAndStats();
      this.setupGolem();
      this.upgrades = new byte[this.type.upgrades + (this.advanced?1:0)];

      for(int a = 0; a < this.upgrades.length; ++a) {
         this.upgrades[a] = -1;
      }

   }

   public void loadGolemTexturesAndStats() {
      if(this.blocky == null) {
         this.type = EnumGolemTHType.VOID;
      } else {
         Material m = this.blocky.getMaterial();
         if(m == Material.grass) {
            this.type = EnumGolemTHType.GRASS;
         } else if(m == Material.ground) {
            this.type = EnumGolemTHType.DIRT;
         } else if(m != Material.wood && m != Material.gourd) {
            if(m != Material.leaves && m != Material.plants && m != Material.vine) {
               if(m == Material.rock) {
                  this.type = EnumGolemTHType.ROCK;
               } else if(m != Material.iron && m != Material.anvil) {
                  if(m != Material.sponge && m != Material.cloth && m != Material.carpet) {
                     if(m == Material.sand) {
                        this.type = EnumGolemTHType.SAND;
                     } else if(m != Material.redstoneLight && m != Material.circuits) {
                        if(m == Material.tnt) {
                           this.type = EnumGolemTHType.TNT;
                        } else if(m != Material.ice && m != Material.packedIce && m != Material.snow && m != Material.craftedSnow) {
                           if(m == Material.cactus) {
                              this.type = EnumGolemTHType.CACTUS;
                           } else if(m == Material.cake) {
                              this.type = EnumGolemTHType.CAKE;
                           } else if(m == Material.web) {
                              this.type = EnumGolemTHType.WEB;
                           } else {
                              this.type = EnumGolemTHType.ROCK;
                           }
                        } else {
                           this.type = EnumGolemTHType.ICE;
                        }
                     } else {
                        this.type = EnumGolemTHType.REDSTONE;
                     }
                  } else {
                     this.type = EnumGolemTHType.CLOTH;
                  }
               } else {
                  this.type = EnumGolemTHType.METAL;
               }
            } else {
               this.type = EnumGolemTHType.PLANT;
            }
         } else {
            this.type = EnumGolemTHType.WOOD;
         }

         if(this.worldObj.isRemote) {
            this.loadTexture();
         }

      }
   }

   public boolean isValidTarget(Entity target) {
      return this.berserk?(!target.isEntityAlive()?false:(target instanceof EntityPlayer && ((EntityPlayer)target).getCommandSenderName().equals(this.getOwnerName())?false:!target.getCommandSenderName().equals(this.getCommandSenderName()))):super.isValidTarget(target) || target instanceof EntityCreeper;
   }

   public boolean setupGolem() {
      super.setupGolem();
      if(this.getCore() == -1) {
         this.tasks.addTask(0, new AIAvoidCreeperSwell(this));
         this.targetTasks.addTask(1, new AIHurtByTarget(this, false));
         this.targetTasks.addTask(2, new AINearestAttackableTarget(this, 0, true));
         this.tasks.addTask(3, new AIGolemAttackOnCollide(this));
         this.tasks.addTask(5, new AIOpenDoor(this, true));
         if(!this.kaboom) {
            this.tasks.addTask(6, new EntityAIFollowPlayer(this, (double)(this.func_70689_ay() * 3.0F), 2.0F, 12.0F));
         } else {
            this.tasks.addTask(6, new EntityAIFollowPlayer(this, (double)(this.func_70689_ay() * 3.0F), 8.0F, 12.0F));
         }

         this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
         this.paused = false;
         this.inactive = false;
         this.bootup = 0.0F;
      }

      if(!this.worldObj.isRemote) {
         this.dataWatcher.updateObject(19, Byte.valueOf((byte)this.type.ordinal()));
      }

      if(this.getGolemTHType() != EnumGolemTHType.ROCK && this.getGolemTHType() != EnumGolemTHType.METAL && this.getGolemTHType() != EnumGolemTHType.REDSTONE) {
         this.getNavigator().setAvoidsWater(true);
      } else {
         this.getNavigator().setAvoidsWater(false);
      }

      int bonus = 0;

      try {
         bonus = this.getGolemDecoration().contains("H")?5:0;
      } catch (Exception var3) {
         ;
      }

      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)(this.getGolemTHType().health + bonus));
      return true;
   }

   public boolean attackEntityFrom(DamageSource ds, float par2) {
      this.paused = false;
      if(ds == DamageSource.cactus) {
         return false;
      } else {
         if(this.blocky == ConfigBlocks.blockCosmeticSolid && this.md == 4 && ds == DamageSource.magic) {
            par2 *= 0.5F;
         }

         if(ds.getSourceOfDamage() != null && this.getUpgradeAmount(5) > 0 && ds.getSourceOfDamage().getEntityId() != this.getEntityId()) {
            ds.getSourceOfDamage().attackEntityFrom(DamageSource.causeThornsDamage(this), (float)(this.getUpgradeAmount(5) * 2 + this.rand.nextInt(2 * this.getUpgradeAmount(5))));
            ds.getSourceOfDamage().playSound("damage.thorns", 0.5F, 1.0F);
         } else if(ds.getSourceOfDamage() != null && this.blocky == Blocks.cactus && ds.getSourceOfDamage().getEntityId() != this.getEntityId()) {
            ds.getSourceOfDamage().attackEntityFrom(DamageSource.causeThornsDamage(this), (float)(this.getUpgradeAmount(5) * 2 + this.rand.nextInt(2)));
            ds.getSourceOfDamage().playSound("damage.thorns", 0.5F, 1.0F);
         }

         return super.attackEntityFrom(ds, par2);
      }
   }

   public int getCarryLimit() {
      int base = this.type.carry;
      if(this.worldObj.isRemote) {
         base = this.getGolemTHType().carry;
      }

      base += Math.min(16, Math.max(4, base)) * this.getUpgradeAmount(1);
      return base;
   }

   public float func_70689_ay() {
      if(!this.paused && !this.inactive) {
         float speed = this.type.speed * (this.decoration.contains("B")?1.1F:1.0F);
         if(this.decoration.contains("P")) {
            speed *= 0.88F;
         }

         speed *= 1.0F + (float)this.getUpgradeAmount(0) * 0.15F;
         if(this.advanced) {
            speed *= 1.1F;
         }

         if(this.isInWater() && (this.getGolemTHType() == EnumGolemTHType.ROCK || this.getGolemTHType() == EnumGolemTHType.METAL || this.getGolemTHType() == EnumGolemTHType.REDSTONE)) {
            speed *= 2.0F;
         }

         return speed;
      } else {
         return 0.0F;
      }
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(!this.worldObj.isRemote) {
         if(this.getCore() == -1 && this.ticksAlive > 0) {
            --this.ticksAlive;
            if(this.worldObj.getPlayerEntityByName(this.getOwnerName()) != null) {
               this.setHomeArea((int)this.worldObj.getPlayerEntityByName(this.getOwnerName()).posX, (int)this.worldObj.getPlayerEntityByName(this.getOwnerName()).posY, (int)this.worldObj.getPlayerEntityByName(this.getOwnerName()).posZ, 16);
            }
         } else if(this.getCore() == -1 && this.ticksAlive != -420) {
            this.die();
         } else if(this.getCore() == -1) {
            if(this.ticksExisted % 10 == 0 && this.worldObj.rand.nextInt(500) == 0) {
               EntityPlayer player = this.worldObj.getPlayerEntityByName(this.getOwnerName());
               switch(this.voidCount) {
               case 0:
                  if(player != null) {
                     player.addChatMessage(new ChatComponentText("ï¿½5ï¿½o" + StatCollector.translateToLocal("thaumichorizons.golemWarning1")));
                  }
                  break;
               case 1:
                  if(player != null) {
                     player.addChatMessage(new ChatComponentText("ï¿½5ï¿½o" + StatCollector.translateToLocal("thaumichorizons.golemWarning2")));
                  }
                  break;
               case 2:
                  if(player != null) {
                     player.addChatMessage(new ChatComponentText("ï¿½5ï¿½o" + StatCollector.translateToLocal("thaumichorizons.golemWarning3")));
                  }
                  break;
               case 3:
                  this.die();
                  Thaumcraft.proxy.burst(this.worldObj, this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, 2.0F);
                  EntityEldritchGuardian scaryThing = new EntityEldritchGuardian(this.worldObj);
                  scaryThing.setPosition(this.posX, this.posY, this.posZ);
                  this.worldObj.spawnEntityInWorld(scaryThing);
                  scaryThing.setHomeArea((int)this.posX, (int)this.posY, (int)this.posZ, 32);
               }

               ++this.voidCount;
            }

            if(this.worldObj.getPlayerEntityByName(this.getOwnerName()) != null) {
               this.setHomeArea((int)this.worldObj.getPlayerEntityByName(this.getOwnerName()).posX, (int)this.worldObj.getPlayerEntityByName(this.getOwnerName()).posY, (int)this.worldObj.getPlayerEntityByName(this.getOwnerName()).posZ, 16);
            }
         }

         if(this.regenTimer <= 0) {
            this.regenTimer = this.type.regenDelay;
            if(this.decoration.contains("F")) {
               this.regenTimer = (int)((float)this.regenTimer * 0.66F);
            }

            if(!this.worldObj.isRemote && this.getHealth() < this.getMaxHealth()) {
               this.worldObj.setEntityState(this, (byte)5);
               this.heal(1.0F);
            }
         }
      }

   }

   public boolean isWithinHomeDistance(int par1, int par2, int par3) {
      return this.getCore() == -1?true:super.isWithinHomeDistance(par1, par2, par3);
   }

   public void setFire(int par1) {
      if(!this.type.fireResist) {
         super.setFire(par1);
      }

   }

   public void writeEntityToNBT(NBTTagCompound nbt) {
      super.writeEntityToNBT(nbt);
      nbt.setByte("GolemTypeTH", (byte)this.type.ordinal());
      nbt.setByte("GolemType", (byte)EnumGolemType.FLESH.ordinal());
      if(this.blocky != null) {
         Block var10002 = this.blocky;
         nbt.setInteger("block", Block.getIdFromBlock(this.blocky));
      } else {
         nbt.setInteger("block", 0);
      }

      nbt.setByteArray("upgrades", this.upgrades);
      nbt.setInteger("metadata", this.md);
      nbt.setInteger("ticksAlive", this.ticksAlive);
      nbt.setInteger("voidCount", this.voidCount);
      nbt.setBoolean("berserk", this.berserk);
      nbt.setBoolean("explosive", this.kaboom);
   }

   public void readEntityFromNBT(NBTTagCompound nbt) {
      super.readEntityFromNBT(nbt);
      this.type = EnumGolemTHType.getType(nbt.getByte("GolemTypeTH"));
      this.upgrades = new byte[this.type.upgrades + (this.advanced?1:0)];
      int ul = this.upgrades.length;
      this.upgrades = nbt.getByteArray("upgrades");
      if(ul != this.upgrades.length) {
         byte[] st = new byte[ul];

         int a;
         for(a = 0; a < ul; ++a) {
            st[a] = -1;
         }

         for(a = 0; a < this.upgrades.length; ++a) {
            if(a < ul) {
               st[a] = this.upgrades[a];
            }
         }

         this.upgrades = st;
      }

      String var8 = "";
      byte[] var9 = this.upgrades;
      int var5 = var9.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         byte c = var9[var6];
         var8 = var8 + Integer.toHexString(c);
      }

      this.dataWatcher.updateObject(23, String.valueOf(var8));
      this.blocky = Block.getBlockById(nbt.getInteger("block"));
      this.md = nbt.getInteger("metadata");
      this.ticksAlive = nbt.getInteger("ticksAlive");
      this.voidCount = nbt.getInteger("voidCount");
      this.berserk = nbt.getBoolean("berserk");
      this.kaboom = nbt.getBoolean("explosive");
      if(this.worldObj.isRemote) {
         this.loadTexture();
      }

   }

   protected void damageEntity(DamageSource ds, float par2) {
      if(!ds.isFireDamage() || !this.type.fireResist) {
         super.damageEntity(ds, par2);
      }
   }

   public int getTotalArmorValue() {
      int var1 = super.getTotalArmorValue() + this.type.armor;
      if(this.decoration.contains("V")) {
         ++var1;
      }

      if(this.decoration.contains("P")) {
         var1 += 4;
      }

      if(var1 > 20) {
         var1 = 20;
      }

      return var1;
   }

   public EnumGolemTHType getGolemTHType() {
      return EnumGolemTHType.getType(this.dataWatcher.getWatchableObjectByte(19));
   }

   @SideOnly(Side.CLIENT)
   public void loadTexture() {
      if(this.blocky != null && this.blocky != Blocks.air) {
         IIcon bottom = this.blocky.getIcon(0, this.md);
         IIcon top = this.blocky.getIcon(1, this.md);
         IIcon east = this.blocky.getIcon(2, this.md);
         IIcon west = this.blocky.getIcon(3, this.md);
         IIcon north = this.blocky.getIcon(4, this.md);
         IIcon south = this.blocky.getIcon(5, this.md);
         GolemTHTexture newTex = new GolemTHTexture(new IIcon[]{bottom, top, east, west, north, south}, this.blocky == Blocks.grass?1:(this.blocky == Blocks.cake?2:0));
         this.texture = new ResourceLocation("thaumichorizons", "TEMPGOLEMTEX" + this.getEntityId());
         Minecraft.getMinecraft().getTextureManager().loadTexture(this.texture, newTex);
      }
   }

   public boolean interact(EntityPlayer player) {
      if(player.isSneaking()) {
         return false;
      } else {
         ItemStack itemstack = player.inventory.getCurrentItem();
         if(this.getCore() == -1 && itemstack != null && itemstack.getItem() == ConfigItems.itemGolemCore && itemstack.getItemDamage() != 100) {
            this.setHomeArea((int)Math.round(this.posX - 0.5D), (int)Math.round(this.posY), (int)Math.round(this.posZ - 0.5D), 32);
            this.setCore((byte)itemstack.getItemDamage());
            this.setupGolem();
            this.setupGolemInventory();
            --itemstack.stackSize;
            if(itemstack.stackSize <= 0) {
               player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }

            this.worldObj.playSoundAtEntity(this, "thaumcraft:upgrade", 0.5F, 1.0F);
            player.swingItem();
            this.worldObj.setEntityState(this, (byte)7);
            return true;
         } else if(this.getCore() == -1) {
            return false;
         } else if(itemstack != null && itemstack.getItem() == ConfigItems.itemGolemUpgrade) {
            for(int a = 0; a < this.upgrades.length; ++a) {
               if(this.getUpgrade(a) == -1 && this.getUpgradeAmount(itemstack.getItemDamage()) < 2) {
                  this.setUpgrade(a, (byte)itemstack.getItemDamage());
                  this.setupGolem();
                  this.setupGolemInventory();
                  --itemstack.stackSize;
                  if(itemstack.stackSize <= 0) {
                     player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                  }

                  this.worldObj.playSoundAtEntity(this, "thaumcraft:upgrade", 0.5F, 1.0F);
                  player.swingItem();
                  return true;
               }
            }

            return false;
         } else {
            return super.interact(player);
         }
      }
   }

   public void die() {
      this.setDead();
      this.spawnExplosionParticle();
      this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thaumcraft:craftfail", 1.0F, 1.0F);
      if(this.blocky != null && this.blocky != Blocks.air && this.blocky.getMaterial() == Material.tnt) {
         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4.0F, true);
      } else if(this.kaboom) {
         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.0F, true);
      } else {
         PacketFXBlocksplosion var10001;
         SimpleNetworkWrapper var10000;
         Block var10003;
         if(this.worldObj.isAirBlock((int)Math.round(this.posX - 0.5D), (int)Math.round(this.posY), (int)Math.round(this.posZ - 0.5D))) {
            if(this.blocky != null && this.blocky != Blocks.air) {
               this.worldObj.setBlock((int)Math.round(this.posX - 0.5D), (int)Math.round(this.posY), (int)Math.round(this.posZ - 0.5D), this.blocky, this.md, 3);
               var10000 = PacketHandler.INSTANCE;
               var10001 = new PacketFXBlocksplosion(Block.getIdFromBlock(this.blocky), this.md, (int)Math.round(this.posX - 0.5D), (int)Math.round(this.posY), (int)Math.round(this.posZ - 0.5D));
               var10003 = this.blocky;
               var10000.sendToAllAround(var10001, new TargetPoint(this.worldObj.provider.dimensionId, (double)((int)Math.round(this.posX - 0.5D)), (double)((int)Math.round(this.posY)), (double)((int)Math.round(this.posZ - 0.5D)), 32.0D));
            }

         } else {
            int x;
            int z;
            for(x = -1; x < 2; ++x) {
               for(z = -1; z < 2; ++z) {
                  if(this.worldObj.isAirBlock((int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY), (int)Math.round(this.posZ - 0.5D) + z)) {
                     if(this.blocky != null && this.blocky != Blocks.air) {
                        this.worldObj.setBlock((int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY), (int)Math.round(this.posZ - 0.5D) + z, this.blocky, this.md, 3);
                        var10000 = PacketHandler.INSTANCE;
                        var10001 = new PacketFXBlocksplosion(Block.getIdFromBlock(this.blocky), this.md, (int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY), (int)Math.round(this.posZ - 0.5D) + z);
                        var10003 = this.blocky;
                        var10000.sendToAllAround(var10001, new TargetPoint(this.worldObj.provider.dimensionId, (double)((int)Math.round(this.posX - 0.5D) + x), (double)((int)Math.round(this.posY)), (double)((int)Math.round(this.posZ - 0.5D) + z), 32.0D));
                     }

                     return;
                  }
               }
            }

            for(x = -1; x < 2; ++x) {
               for(z = -1; z < 2; ++z) {
                  if(this.worldObj.isAirBlock((int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY) - 1, (int)Math.round(this.posZ - 0.5D) + z)) {
                     if(this.blocky != null && this.blocky != Blocks.air) {
                        this.worldObj.setBlock((int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY) - 1, (int)Math.round(this.posZ - 0.5D) + z, this.blocky, this.md, 3);
                        var10000 = PacketHandler.INSTANCE;
                        var10001 = new PacketFXBlocksplosion(Block.getIdFromBlock(this.blocky), this.md, (int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY) - 1, (int)Math.round(this.posZ - 0.5D) + z);
                        var10003 = this.blocky;
                        var10000.sendToAllAround(var10001, new TargetPoint(this.worldObj.provider.dimensionId, (double)((int)Math.round(this.posX - 0.5D) + x), (double)((int)Math.round(this.posY) - 1), (double)((int)Math.round(this.posZ - 0.5D) + z), 32.0D));
                     }

                     return;
                  }
               }
            }

            for(x = -1; x < 2; ++x) {
               for(z = -1; z < 2; ++z) {
                  if(this.worldObj.isAirBlock((int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY) + 1, (int)Math.round(this.posZ - 0.5D) + z)) {
                     if(this.blocky != null && this.blocky != Blocks.air) {
                        this.worldObj.setBlock((int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY) + 1, (int)Math.round(this.posZ - 0.5D) + z, this.blocky, this.md, 3);
                        var10000 = PacketHandler.INSTANCE;
                        var10001 = new PacketFXBlocksplosion(Block.getIdFromBlock(this.blocky), this.md, (int)Math.round(this.posX - 0.5D) + x, (int)Math.round(this.posY) + 1, (int)Math.round(this.posZ - 0.5D) + z);
                        var10003 = this.blocky;
                        var10000.sendToAllAround(var10001, new TargetPoint(this.worldObj.provider.dimensionId, (double)((int)Math.round(this.posX - 0.5D) + x), (double)((int)Math.round(this.posY) + 1), (double)((int)Math.round(this.posZ - 0.5D) + z), 32.0D));
                     }

                     return;
                  }
               }
            }

         }
      }
   }

   public void writeSpawnData(ByteBuf data) {
      super.writeSpawnData(data);
      if(this.blocky != null) {
         Block var10001 = this.blocky;
         data.writeInt(Block.getIdFromBlock(this.blocky));
      } else {
         data.writeInt(0);
      }

      data.writeByte(this.md);
      data.writeByte(this.upgrades.length);
      byte[] var2 = this.upgrades;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte b = var2[var4];
         data.writeByte(b);
      }

   }

   public void readSpawnData(ByteBuf data) {
      super.readSpawnData(data);
      this.blocky = Block.getBlockById(data.readInt());
      this.md = data.readByte();
      this.upgrades = new byte[data.readByte()];

      for(int a = 0; a < this.upgrades.length; ++a) {
         this.upgrades[a] = data.readByte();
      }

   }

   public String getCommandSenderName() { // // func_70005_c_
      if(this.hasCustomNameTag()) {
         return this.getCustomNameTag();
      } else {
         ItemStack stack = new ItemStack(this.blocky, 1, this.md);
         return stack.getItem() != null?stack.getDisplayName() + " Golem":(this.blocky != null && this.blocky != Blocks.air?this.blocky.getLocalizedName() + " Golem":"Voidling Golem");
      }
   }

   public EnumGolemType getGolemType() {
      return EnumGolemType.WOOD;
   }

   public int getGolemStrength() {
      return this.type.strength + this.getUpgradeAmount(1);
   }

   public boolean attackEntityAsMob(Entity par1Entity) {
      if(this.blocky == Blocks.web && par1Entity instanceof EntityLivingBase) {
         ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 0));
      }

      return super.attackEntityAsMob(par1Entity);
   }

   @SideOnly(Side.CLIENT)
   public void handleHealthUpdate(byte par1) {
      if(par1 == 4) {
         this.action = 6;
      } else if(par1 == 5) {
         this.healing = 5;
         int bonus = 0;

         try {
            bonus = this.getGolemDecoration().contains("H")?5:0;
         } catch (Exception var4) {
            ;
         }

         this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)(this.type.health + bonus));
      } else if(par1 == 6) {
         this.leftArm = 5;
      } else if(par1 == 8) {
         this.rightArm = 5;
      } else if(par1 == 7) {
         this.bootup = 33.0F;
      } else {
         super.handleHealthUpdate(par1);
      }

   }

   public void onStruckByLightning(EntityLightningBolt bolt) {
      if(!(bolt instanceof EntityLightningBoltFinite)) {
         super.onStruckByLightning(bolt);
      }
   }
}
