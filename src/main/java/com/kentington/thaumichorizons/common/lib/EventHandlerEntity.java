package com.kentington.thaumichorizons.common.lib;

import baubles.api.BaublesApi;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import com.kentington.thaumichorizons.common.entities.EntityMeatSlime;
import com.kentington.thaumichorizons.common.entities.EntityMercurialSlime;
import com.kentington.thaumichorizons.common.entities.EntityNightmare;
import com.kentington.thaumichorizons.common.entities.EntityVoltSlime;
import com.kentington.thaumichorizons.common.entities.ai.EntityAIAttackOnCollideTH;
import com.kentington.thaumichorizons.common.entities.ai.EntityAIFollowOwnerTH;
import com.kentington.thaumichorizons.common.entities.ai.EntityAIHurtByTargetTH;
import com.kentington.thaumichorizons.common.entities.ai.EntityAIOwnerHurtByTargetTH;
import com.kentington.thaumichorizons.common.entities.ai.EntityAIOwnerHurtTargetTH;
import com.kentington.thaumichorizons.common.entities.ai.EntityAISitTH;
import com.kentington.thaumichorizons.common.entities.ai.EntityAIWanderTH;
import com.kentington.thaumichorizons.common.items.ItemAmuletMirror;
import com.kentington.thaumichorizons.common.items.ItemFocusContainment;
import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import com.kentington.thaumichorizons.common.lib.PacketFXContainment;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import com.kentington.thaumichorizons.common.lib.PacketNoMoreItems;
import com.kentington.thaumichorizons.common.lib.PacketPlayerInfusionSync;
import com.kentington.thaumichorizons.common.tiles.TileSoulBeacon;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.EntityAspectOrb;
import thaumcraft.common.entities.EntityFollowingItem;
import thaumcraft.common.items.relics.ItemHandMirror;
import thaumcraft.common.lib.network.fx.PacketFXShield;
import thaumcraft.common.lib.utils.EntityUtils;

public class EventHandlerEntity {

   @SideOnly(Side.CLIENT)
   public static int clientNightmareID;
   @SideOnly(Side.CLIENT)
   public static int clientPlayerID;


   @SubscribeEvent
   public void ConstructEntity(EntityConstructing event) {
      if(event.entity instanceof EntityLivingBase && event.entity.getExtendedProperties("CreatureInfusion") == null) {
         EntityInfusionProperties prop = new EntityInfusionProperties();
         prop.entity = event.entity;
         event.entity.registerExtendedProperties("CreatureInfusion", prop);
      }

   }

   @SubscribeEvent
   public void EntityJoinWorld(EntityJoinWorldEvent event) {
      if(event.entity instanceof EntityLivingBase) {
         this.applyInfusions((EntityLivingBase)event.entity);
      }

      if(event.world.isRemote && event.entity instanceof EntityNightmare && event.entity.getEntityId() == clientNightmareID) {
         event.entity.worldObj.getEntityByID(clientPlayerID).ridingEntity = event.entity;
         event.entity.riddenByEntity = event.entity.worldObj.getEntityByID(clientPlayerID);
         clientNightmareID = -2;
         clientPlayerID = -2;
      }

   }

   public void applyInfusions(EntityLivingBase entity) {
      StackTraceElement[] above = Thread.currentThread().getStackTrace();
      int[] infusions;
      int i;
      if(entity instanceof EntityPlayer) {
         infusions = ((EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion")).getPlayerInfusions();

         for(i = 0; i < infusions.length; ++i) {
            if(infusions[i] != 0 && infusions[i] == 8 && !entity.worldObj.isRemote) {
               this.warpTumor((EntityPlayer)entity, 50 - ((EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion")).tumorWarpPermanent - ((EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion")).tumorWarp - ((EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion")).tumorWarpTemp);
            }
         }

         this.applyPlayerPotionInfusions((EntityPlayer)entity, infusions, ((EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion")).toggleInvisible);
         if(!entity.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendToAll(new PacketPlayerInfusionSync(entity.getCommandSenderName(), infusions));
         }
      } else {
         StackTraceElement[] var7 = above;
         i = above.length;

         for(int effect = 0; effect < i; ++effect) {
            StackTraceElement el = var7[effect];
            if(el.getMethodName().equals("applyNewAI")) {
               return;
            }
         }

         infusions = ((EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion")).getInfusions();

         for(i = 0; i < 12; ++i) {
            if(infusions[i] != 0) {
               PotionEffect var8;
               if(infusions[i] == 1) {
                  var8 = new PotionEffect(Potion.jump.id, Integer.MAX_VALUE, 0, true);
                  var8.setCurativeItems(new ArrayList());
                  entity.addPotionEffect(var8);
               } else if(infusions[i] == 3) {
                  var8 = new PotionEffect(Potion.regeneration.id, Integer.MAX_VALUE, 0, true);
                  var8.setCurativeItems(new ArrayList());
                  entity.addPotionEffect(var8);
               } else if(infusions[i] == 4) {
                  var8 = new PotionEffect(Potion.resistance.id, Integer.MAX_VALUE, 0, true);
                  var8.setCurativeItems(new ArrayList());
                  entity.addPotionEffect(var8);
                  ThaumicHorizons.instance.renderEventHandler.thingsThatSparkle.add(entity);
               } else if(infusions[i] == 8 && !entity.getEntityData().hasKey("runicCharge")) {
                  entity.getEntityData().setInteger("runicCharge", 6);
               } else if(infusions[i] == 7) {
                  this.applyNewAI((EntityLiving)entity);
               }
            }
         }
      }

   }

   public void applyPlayerPotionInfusions(EntityPlayer entity, int[] infusions, boolean toggled) {
      for(int i = 0; i < infusions.length; ++i) {
         PotionEffect effect;
         if(infusions[i] == 1) {
            effect = new PotionEffect(Potion.jump.id, Integer.MAX_VALUE, 0, true);
            effect.setCurativeItems(new ArrayList());
            entity.addPotionEffect(effect);
            effect = new PotionEffect(Potion.moveSpeed.id, Integer.MAX_VALUE, 0, true);
            effect.setCurativeItems(new ArrayList());
            entity.addPotionEffect(effect);
         } else if(infusions[i] == 3) {
            effect = new PotionEffect(Potion.regeneration.id, Integer.MAX_VALUE, 0, true);
            effect.setCurativeItems(new ArrayList());
            entity.addPotionEffect(effect);
         } else if(infusions[i] == 4) {
            effect = new PotionEffect(Potion.resistance.id, Integer.MAX_VALUE, 0, true);
            effect.setCurativeItems(new ArrayList());
            entity.addPotionEffect(effect);
         } else if(infusions[i] == 10 && !toggled) {
            effect = new PotionEffect(Potion.invisibility.id, Integer.MAX_VALUE, 0, true);
            effect.setCurativeItems(new ArrayList());
            entity.addPotionEffect(effect);
            entity.setInvisible(true);
         }
      }

   }

   public void warpTumor(EntityPlayer entity, int capacity) {
      if(capacity > 0) {
         int warpPermanent = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(entity.getCommandSenderName());
         int warp = Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(entity.getCommandSenderName());
         int tempWarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(entity.getCommandSenderName());
         boolean capacity1;
         EntityInfusionProperties var10000;
         if(warpPermanent > capacity) {
            Thaumcraft.proxy.getPlayerKnowledge().addWarpPerm(entity.getCommandSenderName(), -capacity);
            var10000 = (EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion");
            var10000.tumorWarpPermanent += capacity;
            capacity1 = false;
         } else {
            capacity -= warpPermanent;
            Thaumcraft.proxy.getPlayerKnowledge().addWarpPerm(entity.getCommandSenderName(), -warpPermanent);
            var10000 = (EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion");
            var10000.tumorWarpPermanent += warpPermanent;
            if(warp > capacity) {
               Thaumcraft.proxy.getPlayerKnowledge().addWarpSticky(entity.getCommandSenderName(), -capacity);
               var10000 = (EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion");
               var10000.tumorWarp += capacity;
               capacity1 = false;
            } else {
               capacity -= warp;
               Thaumcraft.proxy.getPlayerKnowledge().addWarpSticky(entity.getCommandSenderName(), -warp);
               var10000 = (EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion");
               var10000.tumorWarp += warp;
               if(tempWarp > capacity) {
                  Thaumcraft.proxy.getPlayerKnowledge().addWarpTemp(entity.getCommandSenderName(), -capacity);
                  var10000 = (EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion");
                  var10000.tumorWarpTemp += capacity;
                  capacity1 = false;
               } else {
                  int var7 = capacity - tempWarp;
                  Thaumcraft.proxy.getPlayerKnowledge().addWarpTemp(entity.getCommandSenderName(), -tempWarp);
                  var10000 = (EntityInfusionProperties)entity.getExtendedProperties("CreatureInfusion");
                  var10000.tumorWarpTemp += tempWarp;
               }
            }
         }

      }
   }

   public void applyNewAI(EntityLiving entity) {
      if(entity.tasks.taskEntries.size() == 0) {
         ;
      }

      ArrayList tasks = new ArrayList();
      Iterator it = entity.tasks.taskEntries.iterator();

      while(it.hasNext()) {
         tasks.add(it.next());
      }

      it = tasks.iterator();

      while(it.hasNext()) {
         entity.tasks.removeTask(((EntityAITaskEntry)it.next()).action);
      }

      tasks = new ArrayList();
      it = entity.targetTasks.taskEntries.iterator();

      while(it.hasNext()) {
         tasks.add(it.next());
      }

      it = tasks.iterator();

      while(it.hasNext()) {
         entity.targetTasks.removeTask(((EntityAITaskEntry)it.next()).action);
      }

      entity.tasks.addTask(1, new EntityAISwimming(entity));
      entity.tasks.addTask(2, new EntityAISitTH(entity));
      entity.tasks.addTask(3, new EntityAILeapAtTarget(entity, 0.4F));
      entity.tasks.addTask(4, new EntityAIAttackOnCollideTH(entity, 1.0D, true));
      entity.tasks.addTask(5, new EntityAIFollowOwnerTH(entity, 1.0D, 10.0F, 2.0F));
      if(entity instanceof EntityAnimal) {
         entity.tasks.addTask(6, new EntityAIMate((EntityAnimal)entity, 1.0D));
      }

      entity.tasks.addTask(7, new EntityAIWanderTH(entity, 1.0D));
      entity.tasks.addTask(9, new EntityAIWatchClosest(entity, EntityPlayer.class, 8.0F));
      entity.tasks.addTask(9, new EntityAILookIdle(entity));
      entity.targetTasks.addTask(1, new EntityAIOwnerHurtByTargetTH(entity));
      entity.targetTasks.addTask(2, new EntityAIOwnerHurtTargetTH(entity));
      entity.targetTasks.addTask(3, new EntityAIHurtByTargetTH(entity, true));
   }

   @SubscribeEvent
   public void livingTick(LivingUpdateEvent event) {
      if(event.entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)event.entity;
         String boost = "R" + player.getDisplayName();
         if(ItemFocusContainment.hitCritters.containsKey(boost)) {
            ItemFocusContainment.contain.put(boost, Float.valueOf(((Float)ItemFocusContainment.contain.get(boost)).floatValue() - 1.0F));
            if(((Float)ItemFocusContainment.contain.get(boost)).floatValue() > 0.0F) {
               Entity stuff = (Entity)ItemFocusContainment.hitCritters.get(boost);
               ThaumicHorizons.proxy.containmentFX(stuff.posX, stuff.posY, stuff.posZ, player, stuff, (int)(((Float)ItemFocusContainment.contain.get(boost)).floatValue() / ((EntityLiving)stuff).getHealth()) / 3 + 1);
            } else {
               ItemFocusContainment.contain.remove(boost);
               ItemFocusContainment.hitCritters.remove(boost);
            }
         }

         EntityInfusionProperties stuff1 = (EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion");
         if(stuff1.hasPlayerInfusion(5) && (player.getActivePotionEffect(Potion.poison) != null || player.getActivePotionEffect(Potion.wither) != null || player.getActivePotionEffect(Potion.potionTypes[Config.potionInfVisExhaustID]) != null || player.getActivePotionEffect(Potion.potionTypes[Config.potionVisExhaustID]) != null || player.getActivePotionEffect(Potion.potionTypes[Config.potionThaumarhiaID]) != null || player.getActivePotionEffect(Potion.potionTypes[Config.potionTaintPoisonID]) != null)) {
            Collection orb = event.entityLiving.getActivePotionEffects();
            Iterator e = orb.iterator();
            ArrayList d6 = new ArrayList();

            while(e.hasNext()) {
               PotionEffect pot = (PotionEffect)e.next();
               if(pot.getPotionID() != Potion.poison.id && pot.getPotionID() != Potion.wither.id && pot.getPotionID() != Config.potionInfVisExhaustID && pot.getPotionID() != Config.potionTaintPoisonID && pot.getPotionID() != Config.potionVisExhaustID && pot.getPotionID() != Config.potionThaumarhiaID) {
                  d6.add(pot);
               } else {
                  int d8 = pot.getPotionID();
                  byte amplifier = 0;
                  int d10 = pot.getDuration() - 1;
                  d6.add(new PotionEffect(d8, d10, amplifier, false));
               }
            }

            event.entityLiving.clearActivePotions();
            Iterator pot1 = d6.iterator();

            while(pot1.hasNext()) {
               PotionEffect d81 = (PotionEffect)pot1.next();
               event.entityLiving.addPotionEffect(d81);
            }
         }

         if(stuff1.hasPlayerInfusion(6) && ((EntityLivingBase)event.entity).ticksExisted % 200 == 0 && player.worldObj.isDaytime() && player.worldObj.canBlockSeeTheSky(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ))) {
            player.getFoodStats().addStats(1, 0.0F);
         }

         if(stuff1.hasPlayerInfusion(7) && player.isInWater()) {
            player.setAir(300);
         }

         if(event.entityLiving.ticksExisted % 30 == 0) {
            if(stuff1.hasPlayerInfusion(8)) {
               this.warpTumor((EntityPlayer)event.entityLiving, 50 - stuff1.tumorWarpPermanent - stuff1.tumorWarp - stuff1.tumorWarpTemp);
            }

            this.applyPlayerPotionInfusions(player, stuff1.playerInfusions, stuff1.toggleInvisible);
         }

         if(stuff1.hasPlayerInfusion(9) && !stuff1.toggleClimb) {
            if(event.entityLiving.isCollidedHorizontally) {
               event.entityLiving.motionY = 0.2D;
               if(event.entityLiving.isSneaking()) {
                  event.entityLiving.motionY = 0.0D;
               }

               event.entity.fallDistance = 0.0F;
            } else {
               List orb1 = event.entityLiving.worldObj.func_147461_a(AxisAlignedBB.getBoundingBox(event.entityLiving.posX - (double)event.entityLiving.width / 1.5D, event.entityLiving.posY, event.entityLiving.posZ - (double)event.entityLiving.width / 1.5D, event.entityLiving.posX + (double)event.entityLiving.width / 1.5D, event.entityLiving.posY + (double)event.entityLiving.height * 0.9D, event.entityLiving.posZ + (double)event.entityLiving.width / 1.5D));
               if(orb1.size() > 0) {
                  if(event.entityLiving.isSneaking()) {
                     event.entityLiving.motionY = 0.0D;
                  } else {
                     event.entityLiving.motionY = -0.15D;
                  }

                  event.entity.fallDistance = 0.0F;
               }
            }
         }
      }

      boolean player1;
      if(event.entityLiving.ticksExisted % 30 == 0) {
         player1 = ((EntityInfusionProperties)event.entityLiving.getExtendedProperties("CreatureInfusion")).hasInfusion(6);
         if(player1 && event.entityLiving.getAITarget() != null) {
            event.entityLiving.getAITarget().attackEntityFrom(DamageSource.magic, 1.0F);
            Thaumcraft.proxy.arcLightning(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY + (double)(event.entityLiving.height / 2.0F), event.entityLiving.posZ, event.entityLiving.getAITarget().posX, event.entityLiving.getAITarget().posY + (double)(event.entityLiving.getAITarget().height / 2.0F), event.entityLiving.getAITarget().posZ, 0.2F, 0.8F, 0.8F, 1.0F);
            event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft:zap", 1.0F, 1.0F + (event.entityLiving.worldObj.rand.nextFloat() - event.entityLiving.worldObj.rand.nextFloat()) * 0.2F);
         } else if(player1 && event.entityLiving instanceof EntityLiving && ((EntityLiving)event.entityLiving).getAttackTarget() != null) {
            ((EntityLiving)event.entityLiving).getAttackTarget().attackEntityFrom(DamageSource.magic, 1.0F);
            Thaumcraft.proxy.arcLightning(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY + (double)(event.entityLiving.height / 2.0F), event.entityLiving.posZ, ((EntityLiving)event.entityLiving).getAttackTarget().posX, ((EntityLiving)event.entityLiving).getAttackTarget().posY + (double)(((EntityLiving)event.entityLiving).getAttackTarget().height / 2.0F), ((EntityLiving)event.entityLiving).getAttackTarget().posZ, 0.2F, 0.8F, 0.8F, 1.0F);
            event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft:zap", 1.0F, 1.0F + (event.entityLiving.worldObj.rand.nextFloat() - event.entityLiving.worldObj.rand.nextFloat()) * 0.2F);
         }
      }

      if(event.entityLiving instanceof EntityVoltSlime && event.entityLiving.ticksExisted % 2 == 0 && event.entityLiving.getAITarget() != null) {
         event.entityLiving.getAITarget().attackEntityFrom(DamageSource.magic, 0.5F);
         Thaumcraft.proxy.arcLightning(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY + (double)(event.entityLiving.height / 2.0F), event.entityLiving.posZ, event.entityLiving.getAITarget().posX, event.entityLiving.getAITarget().posY + (double)(event.entityLiving.getAITarget().height / 2.0F), event.entityLiving.getAITarget().posZ, 0.2F, 0.8F, 0.8F, 1.0F);
         event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft:zap", 1.0F, 1.0F + (event.entityLiving.worldObj.rand.nextFloat() - event.entityLiving.worldObj.rand.nextFloat()) * 0.2F);
      }

      int boost1;
      if(event.entityLiving.ticksExisted % 100 == 0) {
         player1 = ((EntityInfusionProperties)event.entityLiving.getExtendedProperties("CreatureInfusion")).hasInfusion(8);
         if(player1) {
            boost1 = event.entityLiving.getEntityData().getInteger("runicCharge") + 1;
            if(boost1 > 6) {
               boost1 = 6;
            }

            event.entityLiving.getEntityData().setInteger("runicCharge", boost1);
         }
      }

      EntityLivingBase player2;
      Entity e1;
      Iterator orb3;
      if(((EntityLivingBase)event.entity).isPotionActive(ThaumicHorizons.potionShockID)) {
         player2 = (EntityLivingBase)event.entity;
         ArrayList boost2 = EntityUtils.getEntitiesInRange(player2.worldObj, player2.posX, player2.posY, player2.posZ, player2, EntityLivingBase.class, 10.0D);
         if(boost2 != null && boost2.size() > 0) {
            int stuff2 = player2.getActivePotionEffect(Potion.potionTypes[ThaumicHorizons.potionShockID]).getAmplifier();
            orb3 = boost2.iterator();

            while(orb3.hasNext()) {
               e1 = (Entity)orb3.next();
               int d61 = player2.worldObj.rand.nextInt(1000);
               if(d61 < 20 * (1 + stuff2) && !e1.isDead && e1 instanceof EntityLivingBase) {
                  if(player2 instanceof EntityPlayer) {
                     e1.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player2), 1.0F);
                  } else {
                     e1.attackEntityFrom(DamageSource.magic, 1.0F);
                  }

                  Thaumcraft.proxy.arcLightning(player2.worldObj, player2.posX, player2.posY + (double)(player2.height / 2.0F), player2.posZ, e1.posX, e1.posY + (double)(e1.height / 2.0F), e1.posZ, 0.2F, 0.8F, 0.8F, 1.0F);
                  player2.worldObj.playSoundAtEntity(player2, "thaumcraft:zap", 1.0F, 1.0F + (player2.worldObj.rand.nextFloat() - player2.worldObj.rand.nextFloat()) * 0.2F);
               }
            }
         }
      }

      if(((EntityLivingBase)event.entity).isPotionActive(ThaumicHorizons.potionVisRegenID)) {
         player2 = (EntityLivingBase)event.entity;
         boost1 = player2.getActivePotionEffect(Potion.potionTypes[ThaumicHorizons.potionVisRegenID]).getAmplifier();
         if(((EntityLivingBase)event.entity).ticksExisted % (20 - 3 * boost1) == 0) {
            Aspect stuff3 = null;
            switch(player2.worldObj.rand.nextInt(6)) {
            case 0:
               stuff3 = Aspect.AIR;
               break;
            case 1:
               stuff3 = Aspect.EARTH;
               break;
            case 2:
               stuff3 = Aspect.FIRE;
               break;
            case 3:
               stuff3 = Aspect.WATER;
               break;
            case 4:
               stuff3 = Aspect.ORDER;
               break;
            case 5:
               stuff3 = Aspect.ENTROPY;
            }

            if(stuff3 != null) {
               EntityAspectOrb orb2 = new EntityAspectOrb(player2.worldObj, player2.posX, player2.posY, player2.posZ, stuff3, 1);
               player2.worldObj.spawnEntityInWorld(orb2);
            }
         }
      }

      if(((EntityLivingBase)event.entity).isPotionActive(ThaumicHorizons.potionVacuumID)) {
         player2 = (EntityLivingBase)event.entity;
         boost1 = player2.getActivePotionEffect(Potion.potionTypes[ThaumicHorizons.potionVacuumID]).getAmplifier();
         ArrayList stuff4 = EntityUtils.getEntitiesInRange(player2.worldObj, player2.posX, player2.posY, player2.posZ, player2, EntityItem.class, 10.0D + (double)(2 * boost1));
         if(stuff4 != null && stuff4.size() > 0) {
            orb3 = stuff4.iterator();

            while(orb3.hasNext()) {
               e1 = (Entity)orb3.next();
               if((!(e1 instanceof EntityFollowingItem) || ((EntityFollowingItem)e1).target == null) && !e1.isDead && e1 instanceof EntityItem) {
                  double d62 = e1.posX - player2.posX;
                  double d82 = e1.posY - player2.posY + (double)(player2.height / 2.0F);
                  double d101 = e1.posZ - player2.posZ;
                  double d11 = (double)MathHelper.sqrt_double(d62 * d62 + d82 * d82 + d101 * d101);
                  d62 /= d11;
                  d82 /= d11;
                  d101 /= d11;
                  double d13 = 0.1D;
                  e1.motionX -= d62 * d13;
                  e1.motionY -= d82 * d13;
                  e1.motionZ -= d101 * d13;
                  if(e1.motionX > 0.35D) {
                     e1.motionX = 0.35D;
                  }

                  if(e1.motionX < -0.35D) {
                     e1.motionX = -0.35D;
                  }

                  if(e1.motionY > 0.35D) {
                     e1.motionY = 0.35D;
                  }

                  if(e1.motionY < -0.35D) {
                     e1.motionY = -0.35D;
                  }

                  if(e1.motionZ > 0.35D) {
                     e1.motionZ = 0.35D;
                  }

                  if(e1.motionZ < -0.35D) {
                     e1.motionZ = -0.35D;
                  }

                  Thaumcraft.proxy.spark((float)e1.posX + (player2.worldObj.rand.nextFloat() - player2.worldObj.rand.nextFloat()) * 0.125F, (float)e1.posY + (player2.worldObj.rand.nextFloat() - player2.worldObj.rand.nextFloat()) * 0.125F, (float)e1.posZ + (player2.worldObj.rand.nextFloat() - player2.worldObj.rand.nextFloat()) * 0.125F, 1.0F, 0.25F, 0.25F, 0.25F, 1.0F);
               }
            }
         }
      }

      if(((EntityLivingBase)event.entity).isPotionActive(ThaumicHorizons.potionSynthesisID)) {
         player2 = (EntityLivingBase)event.entity;
         boost1 = player2.getActivePotionEffect(Potion.potionTypes[ThaumicHorizons.potionSynthesisID]).getAmplifier();
         if(((EntityLivingBase)event.entity).ticksExisted % (15 - 2 * boost1) == 0 && player2.worldObj.isDaytime() && player2.worldObj.canBlockSeeTheSky(MathHelper.floor_double(player2.posX), MathHelper.floor_double(player2.posY), MathHelper.floor_double(player2.posZ))) {
            player2.heal(0.5F);
            if(player2 instanceof EntityPlayer) {
               ((EntityPlayer)player2).getFoodStats().addStats(1, 0.5F);
            }
         }
      }

   }

   @SubscribeEvent
   public void livingDeath(LivingDeathEvent event) {
      if(!event.entityLiving.worldObj.isRemote) {
         if(event.entityLiving instanceof EntityMeatSlime && ((EntityMeatSlime)event.entityLiving).getSlimeSize() == 1) {
            switch(event.entityLiving.worldObj.rand.nextInt(5)) {
            case 0:
               event.entityLiving.entityDropItem(new ItemStack(Items.beef), 0.0F);
               break;
            case 1:
               event.entityLiving.entityDropItem(new ItemStack(Items.porkchop), 0.0F);
               break;
            case 2:
               event.entityLiving.entityDropItem(new ItemStack(Items.chicken), 0.0F);
               break;
            case 3:
               event.entityLiving.entityDropItem(new ItemStack(Items.fish), 0.0F);
               break;
            default:
               event.entityLiving.entityDropItem(new ItemStack(Items.rotten_flesh), 0.0F);
            }
         } else if(event.entityLiving instanceof EntityMercurialSlime && ((EntityMercurialSlime)event.entityLiving).getSlimeSize() == 1) {
            event.entityLiving.entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 3), 0.0F);
         }
      }

   }

   @SubscribeEvent
   public void onPlayerHurt(LivingHurtEvent event) {
      EntityInfusionProperties prop = (EntityInfusionProperties)event.entity.getExtendedProperties("CreatureInfusion");
      if(prop.hasPlayerInfusion(5) && event.source == DamageSourceThaumcraft.taint) {
         event.setCanceled(true);
         event.ammount = 0.0F;
      } else {
         EntityPlayer player;
         int x;
         int z;
         if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer && event.entityLiving.getHealth() - event.ammount <= 0.0F) {
            player = (EntityPlayer)event.entity;
            if(prop.tumorWarp > 0 || prop.tumorWarpTemp > 0) {
               Thaumcraft.proxy.getPlayerKnowledge().addWarpPerm(event.entity.getCommandSenderName(), prop.tumorWarpPermanent);
               Thaumcraft.proxy.getPlayerKnowledge().addWarpSticky(event.entity.getCommandSenderName(), prop.tumorWarp);
               Thaumcraft.proxy.getPlayerKnowledge().addWarpTemp(event.entity.getCommandSenderName(), prop.tumorWarpTemp);
            }

            prop.resetPlayerInfusions();
            IInventory world = BaublesApi.getBaubles(player);

            for(x = 0; x < 4; ++x) {
               ItemStack baubles = world.getStackInSlot(x);
               if(baubles != null && baubles.getItem() instanceof ItemAmuletMirror) {
                  boolean y = false;

                  ItemStack baubles1;
                  for(z = 0; z < player.inventory.armorInventory.length; ++z) {
                     baubles1 = player.inventory.armorInventory[z];
                     if(baubles1 != null && ItemHandMirror.transport(baubles, baubles1, player, player.worldObj)) {
                        y = true;
                        player.inventory.armorInventory[z] = null;
                     }
                  }

                  for(z = 0; z < player.inventory.mainInventory.length; ++z) {
                     baubles1 = player.inventory.mainInventory[z];
                     if(baubles1 != null && ItemHandMirror.transport(baubles, baubles1, player, player.worldObj)) {
                        y = true;
                        player.inventory.mainInventory[z] = null;
                     }
                  }

                  for(z = 0; z < 4; ++z) {
                     if(x != z && world.getStackInSlot(z) != null && ItemHandMirror.transport(baubles, world.getStackInSlot(z), player, player.worldObj)) {
                        y = true;
                        world.setInventorySlotContents(z, (ItemStack)null);
                     }
                  }

                  if(y) {
                     PacketHandler.INSTANCE.sendToAllAround(new PacketFXContainment(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ), new TargetPoint(player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, 32.0D));
                     player.worldObj.playSoundEffect(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ, "thaumcraft:craftfail", 1.0F, 1.0F);
                     world.setInventorySlotContents(x, (ItemStack)null);
                     player.inventory.markDirty();
                     world.markDirty();
                     ItemStack var18 = new ItemStack(ConfigItems.itemEldritchObject, 1, 3);
                     EntityItem var19 = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, var18);
                     player.worldObj.spawnEntityInWorld(var19);
                  }
                  break;
               }
            }
         }

         int var13;
         if(!event.entity.worldObj.isRemote && event.entityLiving instanceof EntityPlayer && event.entityLiving.getHealth() - event.ammount <= 0.0F && event.entityLiving.getEntityData().getBoolean("soulBeacon")) {
            player = (EntityPlayer)event.entity;
            var13 = player.getEntityData().getInteger("soulBeaconDim");
            WorldServer var14 = MinecraftServer.getServer().worldServerForDimension(var13);
            x = player.getEntityData().getIntArray("soulBeaconCoords")[0];
            int var16 = player.getEntityData().getIntArray("soulBeaconCoords")[1];
            z = player.getEntityData().getIntArray("soulBeaconCoords")[2];
            if(var14.getTileEntity(x, var16, z) instanceof TileSoulBeacon && var14.getTileEntity(x, var16 - 1, z) instanceof TileVat && ((TileVat)var14.getTileEntity(x, var16 - 1, z)).mode == 4) {
               event.setCanceled(true);
               int i;
               if(!var14.isRemote) {
                  var14.createExplosion((Entity)null, player.posX, player.posY + (double)player.getEyeHeight(), player.posZ, 0.0F, false);

                  for(int var17 = 0; var17 < 25; ++var17) {
                     i = (int)player.posX + var14.rand.nextInt(8) - var14.rand.nextInt(8);
                     int bauble = (int)player.posY + var14.rand.nextInt(8) - var14.rand.nextInt(8);
                     int zz = (int)player.posZ + var14.rand.nextInt(8) - var14.rand.nextInt(8);
                     if(var14.isAirBlock(i, bauble, zz)) {
                        if(bauble <= (int)player.posY + 1) {
                           var14.setBlock(i, bauble, zz, ConfigBlocks.blockFluxGoo, 8, 3);
                        } else {
                           var14.setBlock(i, bauble, zz, ConfigBlocks.blockFluxGas, 8, 3);
                        }
                     }
                  }
               }

               player.inventory.dropAllItems();
               IInventory var21 = BaublesApi.getBaubles(player);

               for(i = 0; i < 4; ++i) {
                  if(var21.getStackInSlot(i) != null) {
                     EntityItem var20 = new EntityItem(var14, player.posX, player.posY, player.posZ, var21.getStackInSlot(i));
                     var14.spawnEntityInWorld(var20);
                     var21.setInventorySlotContents(i, (ItemStack)null);
                  }
               }

               var21.markDirty();
               player.inventory.markDirty();
               PacketHandler.INSTANCE.sendTo(new PacketNoMoreItems(), (EntityPlayerMP)player);
               player.curePotionEffects(new ItemStack(Items.milk_bucket));
               player.heal(999.0F);
               if(var13 != player.worldObj.provider.dimensionId) {
                  player.travelToDimension(var13);
               }

               player.setPositionAndUpdate((double)x + 0.5D, (double)var16 - 2.5D, (double)z + 0.5D);
               Thaumcraft.proxy.blockSparkle(var14, x, var16 - 2, z, 16777215, 20);
               Thaumcraft.proxy.blockSparkle(var14, x, var16 - 3, z, 16777215, 20);
               var14.playSoundEffect((double)x + 0.5D, (double)var16 + 0.5D, (double)z + 0.5D, "thaumcraft:whispers", 1.0F, var14.rand.nextFloat());
               this.applyPlayerInfusions(player, (TileVat)var14.getTileEntity(x, var16 - 1, z));
               ((TileVat)var14.getTileEntity(x, var16 - 1, z)).selfInfusions = new int[12];
               ((TileVat)var14.getTileEntity(x, var16 - 1, z)).mode = 0;
               ((TileVat)var14.getTileEntity(x, var16 - 1, z)).setEntityContained(player);
               ((TileVat)var14.getTileEntity(x, var16 - 1, z)).markDirty();
               player.worldObj.markBlockForUpdate(x, var16 - 1, z);
            }
         } else if(event.entity.worldObj.isRemote && event.entityLiving instanceof EntityPlayer && event.entityLiving.getHealth() - event.ammount <= 0.0F && event.entityLiving.getEntityData().getBoolean("soulBeacon")) {
            player = (EntityPlayer)event.entity;

            for(var13 = 0; var13 < player.inventory.mainInventory.length; ++var13) {
               player.inventory.mainInventory[var13] = null;
            }

            for(var13 = 0; var13 < player.inventory.armorInventory.length; ++var13) {
               player.inventory.armorInventory[var13] = null;
            }

            IInventory var15 = BaublesApi.getBaubles(player);
            var15.setInventorySlotContents(0, (ItemStack)null);
            var15.setInventorySlotContents(1, (ItemStack)null);
            var15.setInventorySlotContents(2, (ItemStack)null);
            var15.setInventorySlotContents(3, (ItemStack)null);
         }

      }
   }

   void applyPlayerInfusions(EntityPlayer player, TileVat tile) {
      EntityInfusionProperties prop = (EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion");

      for(int i = 0; i < tile.selfInfusions.length; ++i) {
         if(tile.selfInfusions[i] != 0) {
            prop.addPlayerInfusion(tile.selfInfusions[i]);
         }
      }

      this.applyInfusions(player);
   }

   @SubscribeEvent
   public void onAttack(LivingAttackEvent event) {
      boolean ender;
      if(event.source.getEntity() != null && event.source.getEntity() instanceof EntityLivingBase) {
         ender = ((EntityInfusionProperties)event.entityLiving.getExtendedProperties("CreatureInfusion")).hasInfusion(9);
         if(ender) {
            event.entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, 40, 0, false));
         }
      }

      if(event.source.isProjectile()) {
         ender = ((EntityInfusionProperties)event.entityLiving.getExtendedProperties("CreatureInfusion")).hasInfusion(5);
         if(ender) {
            event.setCanceled(true);
            this.teleport(event.entityLiving);
         }
      }
   }

   public void teleport(EntityLivingBase entity) {
      EnderTeleportEvent event = new EnderTeleportEvent(entity, entity.posX + (entity.worldObj.rand.nextDouble() - 0.5D) * 64.0D, entity.posY + (double)(entity.worldObj.rand.nextInt(64) - 32), entity.posZ + (entity.worldObj.rand.nextDouble() - 0.5D) * 64.0D, 0.0F);
      if(!MinecraftForge.EVENT_BUS.post(event)) {
         double d3 = entity.posX;
         double d4 = entity.posY;
         double d5 = entity.posZ;
         entity.posX = event.targetX;
         entity.posY = event.targetY;
         entity.posZ = event.targetZ;
         boolean flag = false;
         int i = MathHelper.floor_double(entity.posX);
         int j = MathHelper.floor_double(entity.posY);
         int k = MathHelper.floor_double(entity.posZ);
         if(entity.worldObj.blockExists(i, j, k)) {
            boolean short1 = false;

            while(!short1 && j > 0) {
               Block l = entity.worldObj.getBlock(i, j - 1, k);
               if(l.getMaterial().blocksMovement()) {
                  short1 = true;
               } else {
                  --entity.posY;
                  --j;
               }
            }

            if(short1) {
               entity.setPosition(entity.posX, entity.posY, entity.posZ);
               if(entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox).isEmpty() && !entity.worldObj.isAnyLiquid(entity.boundingBox)) {
                  flag = true;
               }
            }
         }

         if(!flag) {
            entity.setPosition(d3, d4, d5);
         } else {
            short var26 = 128;

            for(int var27 = 0; var27 < var26; ++var27) {
               double d6 = (double)var27 / ((double)var26 - 1.0D);
               float f = (entity.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
               float f1 = (entity.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
               float f2 = (entity.worldObj.rand.nextFloat() - 0.5F) * 0.2F;
               double d7 = d3 + (entity.posX - d3) * d6 + (entity.worldObj.rand.nextDouble() - 0.5D) * (double)entity.width * 2.0D;
               double d8 = d4 + (entity.posY - d4) * d6 + entity.worldObj.rand.nextDouble() * (double)entity.height;
               double d9 = d5 + (entity.posZ - d5) * d6 + (entity.worldObj.rand.nextDouble() - 0.5D) * (double)entity.width * 2.0D;
               entity.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
            }

            entity.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            entity.playSound("mob.endermen.portal", 1.0F, 1.0F);
         }
      }
   }

   @SubscribeEvent
   public void entityHurt(LivingHurtEvent event) {
      boolean runic = ((EntityInfusionProperties)event.entityLiving.getExtendedProperties("CreatureInfusion")).hasInfusion(8);
      if(runic) {
         int charge = event.entityLiving.getEntityData().getInteger("runicCharge");
         if(charge <= 0 || event.source == DamageSource.drown || event.source == DamageSource.wither || event.source == DamageSource.outOfWorld || event.source == DamageSource.starve) {
            return;
         }

         int target = -1;
         if(event.source.getEntity() != null) {
            target = event.source.getEntity().getEntityId();
         }

         if(event.source == DamageSource.fall) {
            target = -2;
         }

         if(event.source == DamageSource.fallingBlock) {
            target = -3;
         }

         thaumcraft.common.lib.network.PacketHandler.INSTANCE.sendToAllAround(new PacketFXShield(event.entity.getEntityId(), target), new TargetPoint(event.entity.worldObj.provider.dimensionId, event.entity.posX, event.entity.posY, event.entity.posZ, 64.0D));
         if((float)charge > event.ammount) {
            charge = (int)((float)charge - event.ammount);
            event.ammount = 0.0F;
         } else {
            event.ammount -= (float)charge;
            charge = 0;
         }

         event.entityLiving.getEntityData().setInteger("runicCharge", charge);
      }

   }

   @SubscribeEvent
   public void golemDies(LivingDeathEvent event) {
      if(event.entity instanceof EntityGolemTH) {
         ((EntityGolemTH)event.entity).die();
      }

   }

   @SubscribeEvent
   public void sitStay(EntityInteractEvent event) {
      if(event.target.getExtendedProperties("CreatureInfusion") != null) {
         EntityInfusionProperties prop = (EntityInfusionProperties)event.target.getExtendedProperties("CreatureInfusion");
         if(prop.hasInfusion(10) && event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == ConfigItems.itemWandCasting) {
            ItemStack jar = new ItemStack(ThaumicHorizons.blockJar);
            NBTTagCompound entityData = new NBTTagCompound();
            entityData.setString("id", EntityList.getEntityString(event.target));
            event.target.writeToNBT(entityData);
            jar.setTagCompound(entityData);
            jar.getTagCompound().setString("jarredCritterName", event.target.getCommandSenderName());
            jar.getTagCompound().setBoolean("isSoul", false);
            event.target.entityDropItem(jar, 1.0F);
            if(!event.target.worldObj.isRemote) {
               PacketHandler.INSTANCE.sendToAllAround(new PacketFXContainment(event.target.posX, event.target.posY + (double)(event.target.height / 2.0F), event.target.posZ), new TargetPoint(event.target.worldObj.provider.dimensionId, event.target.posX, event.target.posY + (double)(event.target.height / 2.0F), event.target.posZ, 32.0D));
            }

            event.target.worldObj.removeEntity(event.target);
         } else if(prop.hasInfusion(7) && event.entityPlayer.getCommandSenderName().equals(prop.getOwner())) {
            prop.setSitting(!prop.isSitting());
            if(event.target.worldObj.isRemote) {
               if(prop.isSitting()) {
                  event.entityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + event.target.getCommandSenderName() + " is waiting."));
               } else {
                  event.entityPlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + event.target.getCommandSenderName() + " will follow you."));
               }
            }
         }
      }

   }
}
