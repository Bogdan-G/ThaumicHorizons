package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityItemInvulnerable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemCrystalEssence;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.utils.BlockUtils;

public class ItemFocusDisintegration extends ItemFocusBasic {

   public static FocusUpgradeType enervation = new FocusUpgradeType(FocusUpgradeType.types.length, new ResourceLocation("thaumichorizons", "textures/foci/enervation.png"), "focus.upgrade.enervation.name", "focus.upgrade.enervation.text", (new AspectList()).add(Aspect.DEATH, 8));
   private static final AspectList cost = (new AspectList()).add(Aspect.FIRE, 30).add(Aspect.ENTROPY, 30).add(Aspect.EARTH, 30);
   private static final AspectList costCritter = (new AspectList()).add(Aspect.FIRE, 12).add(Aspect.ENTROPY, 12).add(Aspect.EARTH, 12);
   static HashMap soundDelay = new HashMap();
   static HashMap beam = new HashMap();
   static HashMap breakcount = new HashMap();
   static HashMap lastX = new HashMap();
   static HashMap lastY = new HashMap();
   static HashMap lastZ = new HashMap();


   public ItemFocusDisintegration() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      super.icon = ir.registerIcon("thaumichorizons:focus_disintegration");
   }

   public String getSortingHelper(ItemStack itemstack) {
      return "DS" + super.getSortingHelper(itemstack);
   }

   public int getFocusColor() {
      return 7930047;
   }

   public boolean isVisCostPerTick() {
      return false;
   }

   public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition mop) {
      p.setItemInUse(itemstack, Integer.MAX_VALUE);
      return itemstack;
   }

   public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int count) {
      ItemWandCasting wand = (ItemWandCasting)stack.getItem();
      int size = 2 + wand.getFocusEnlarge(stack) * 8;
      if(!wand.consumeAllVis(stack, p, this.getVisCost(stack), false, true)) {
         p.stopUsingItem();
      } else {
         String pp = "R" + p.getCommandSenderName();
         if(!p.worldObj.isRemote) {
            pp = "S" + p.getCommandSenderName();
         }

         if(soundDelay.get(pp) == null) {
            soundDelay.put(pp, Long.valueOf(0L));
         }

         if(breakcount.get(pp) == null) {
            breakcount.put(pp, Float.valueOf(0.0F));
         }

         if(lastX.get(pp) == null) {
            lastX.put(pp, Integer.valueOf(0));
         }

         if(lastY.get(pp) == null) {
            lastY.put(pp, Integer.valueOf(0));
         }

         if(lastZ.get(pp) == null) {
            lastZ.put(pp, Integer.valueOf(0));
         }

         MovingObjectPosition mop = BlockUtils.getTargetBlock(p.worldObj, p, true);
         Entity ent = getPointedEntity(p.worldObj, p, 10.0D);
         Vec3 v = p.getLookVec();
         double tx = p.posX + v.xCoord * 10.0D;
         double ty = p.posY + v.yCoord * 10.0D;
         double tz = p.posZ + v.zCoord * 10.0D;
         byte impact = 0;
         if((ent == null || ent instanceof EntityItem && (this.getAspects(((EntityItem)ent).getEntityItem().getItem(), ((EntityItem)ent).getEntityItem().getItemDamage()) == null || this.getAspects(((EntityItem)ent).getEntityItem().getItem(), ((EntityItem)ent).getEntityItem().getItemDamage()).size() == 0)) && mop != null) {
            tx = mop.hitVec.xCoord;
            ty = mop.hitVec.yCoord;
            tz = mop.hitVec.zCoord;
            impact = 5;
            if(!p.worldObj.isRemote && ((Long)soundDelay.get(pp)).longValue() < System.currentTimeMillis()) {
               p.worldObj.playSoundEffect(tx, ty, tz, "thaumcraft:zap", 0.3F, 1.0F);
               soundDelay.put(pp, Long.valueOf(System.currentTimeMillis() + 100L));
            }
         } else if(ent != null) {
            tx = ent.posX;
            ty = ent.posY;
            tz = ent.posZ;
            impact = 5;
            if(!p.worldObj.isRemote && ((Long)soundDelay.get(pp)).longValue() < System.currentTimeMillis()) {
               p.worldObj.playSoundEffect(tx, ty, tz, "thaumcraft:zap", 0.3F, 1.0F);
               soundDelay.put(pp, Long.valueOf(System.currentTimeMillis() + 100L));
            }
         }

         if(p.worldObj.isRemote) {
            beam.put(pp, Thaumcraft.proxy.beamCont(p.worldObj, p, tx, ty, tz, 0, 7930047, false, impact > 0?(float)size:0.0F, beam.get(pp), 5));
         }

         if((ent == null || !(ent instanceof EntityLiving) && (!(ent instanceof EntityItem) || ((EntityItem)ent).getEntityItem().getItem() == ConfigItems.itemCrystalEssence || this.getAspects(((EntityItem)ent).getEntityItem().getItem(), ((EntityItem)ent).getEntityItem().getItemDamage()) == null || this.getAspects(((EntityItem)ent).getEntityItem().getItem(), ((EntityItem)ent).getEntityItem().getItemDamage()).size() == 0)) && mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
            Block var26 = p.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
            int var28 = p.worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
            if(Item.getItemFromBlock(var26) != null && this.getAspects(Item.getItemFromBlock(var26), var28) != null) {
               int var27 = wand.getFocusPotency(stack);
               float var29 = 0.05F + (float)var27 * 0.015F;
               float var31 = var26.getBlockHardness(p.worldObj, mop.blockX, mop.blockY, mop.blockZ);
               if(var31 == -1.0F) {
                  return;
               }

               if(((Integer)lastX.get(pp)).intValue() == mop.blockX && ((Integer)lastY.get(pp)).intValue() == mop.blockY && ((Integer)lastZ.get(pp)).intValue() == mop.blockZ) {
                  float var30 = ((Float)breakcount.get(pp)).floatValue();
                  int x;
                  if(p.worldObj.isRemote && var30 > 0.0F && var26 != null) {
                     x = (int)(var30 / var31 * 9.0F);
                     ThaumicHorizons.proxy.disintegrateFX((double)mop.blockX, (double)mop.blockY, (double)mop.blockZ, p, 15, size > 2);
                  }

                  if(p.worldObj.isRemote) {
                     if(var30 >= var31) {
                        p.worldObj.playAuxSFX(2001, mop.blockX, mop.blockY, mop.blockZ, 0);
                        breakcount.put(pp, Float.valueOf(0.0F));
                     } else {
                        breakcount.put(pp, Float.valueOf(var30 + var29));
                     }
                  } else if(var30 >= var31) {
                     this.processBlock(mop.blockX, mop.blockY, mop.blockZ, wand, stack, p, pp);
                     if(size > 2) {
                        for(x = -1; x < 2; ++x) {
                           for(int y = -1; y < 2; ++y) {
                              for(int z = -1; z < 2; ++z) {
                                 if(x != 0 || y != 0 || z != 0) {
                                    this.processBlock(mop.blockX + x, mop.blockY + y, mop.blockZ + z, wand, stack, p, pp);
                                 }
                              }
                           }
                        }
                     }
                  } else {
                     breakcount.put(pp, Float.valueOf(var30 + var29));
                  }
               } else {
                  lastX.put(pp, Integer.valueOf(mop.blockX));
                  lastY.put(pp, Integer.valueOf(mop.blockY));
                  lastZ.put(pp, Integer.valueOf(mop.blockZ));
                  breakcount.put(pp, Float.valueOf(0.0F));
               }
            }
         } else if(ent != null && ent instanceof EntityLiving && wand.consumeAllVis(stack, p, costCritter, true, true)) {
            if(this.getUpgradeLevel(wand.getFocusItem(stack), enervation) > 0) {
               ((EntityLiving)ent).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40, 0));
            }

            ThaumicHorizons.proxy.disintegrateFX(ent.posX - 0.5D, ent.posY, ent.posZ - 0.5D, p, 5, false);
            ((EntityLiving)ent).attackEntityFrom(DamageSourceThaumcraft.dissolve, 0.5F + 0.25F * (float)wand.getFocusPotency(stack) + 0.5F * (float)this.getUpgradeLevel(wand.getFocusItem(stack), enervation));
         } else if(ent != null && ent instanceof EntityItem && this.getAspects(((EntityItem)ent).getEntityItem().getItem(), ((EntityItem)ent).getEntityItem().getItemDamage()) != null && this.getAspects(((EntityItem)ent).getEntityItem().getItem(), ((EntityItem)ent).getEntityItem().getItemDamage()).size() > 0) {
            int num = ((EntityItem)ent).getEntityItem().stackSize;
            if(wand.consumeAllVis(stack, p, this.getVisCost(stack), true, true)) {
               ThaumicHorizons.proxy.disintegrateFX(ent.posX - 0.5D, ent.posY, ent.posZ - 0.5D, p, 5 * num, false);
               AspectList aspects = this.getAspects(((EntityItem)ent).getEntityItem().getItem(), ((EntityItem)ent).getEntityItem().getItemDamage());
               if(aspects != null && !p.worldObj.isRemote) {
                  if(!p.worldObj.isRemote) {
                     Aspect[] pot = aspects.getAspects();
                     int speed = pot.length;

                     for(int hardness = 0; hardness < speed; ++hardness) {
                        Aspect asp = pot[hardness];
                        stack = new ItemStack(ConfigItems.itemCrystalEssence, aspects.getAmount(asp) * num);
                        ((ItemCrystalEssence)stack.getItem()).setAspects(stack, (new AspectList()).add(asp, 1));
                        p.worldObj.spawnEntityInWorld(new EntityItemInvulnerable(p.worldObj, ent.posX, ent.posY, ent.posZ, stack));
                     }
                  } else {
                     ThaumicHorizons.proxy.disintegrateExplodeFX(p.worldObj, ent.posX, ent.posY, ent.posZ);
                  }
               }
            }

            ent.setDead();
         } else {
            lastX.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            lastY.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            lastZ.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            breakcount.put(pp, Float.valueOf(0.0F));
         }
      }

   }

   public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer p, int count) {
      String pp = "R" + p.getCommandSenderName();
      if(!p.worldObj.isRemote) {
         pp = "S" + p.getCommandSenderName();
      }

      if(soundDelay.get(pp) == null) {
         soundDelay.put(pp, Long.valueOf(0L));
      }

      if(breakcount.get(pp) == null) {
         breakcount.put(pp, Float.valueOf(0.0F));
      }

      if(lastX.get(pp) == null) {
         lastX.put(pp, Integer.valueOf(0));
      }

      if(lastY.get(pp) == null) {
         lastY.put(pp, Integer.valueOf(0));
      }

      if(lastZ.get(pp) == null) {
         lastZ.put(pp, Integer.valueOf(0));
      }

      beam.put(pp, (Object)null);
      lastX.put(pp, Integer.valueOf(Integer.MAX_VALUE));
      lastY.put(pp, Integer.valueOf(Integer.MAX_VALUE));
      lastZ.put(pp, Integer.valueOf(Integer.MAX_VALUE));
      breakcount.put(pp, Float.valueOf(0.0F));
   }

   public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack focusstack) {
      return ItemFocusBasic.WandFocusAnimation.CHARGE;
   }

   public int getFocusColor(ItemStack focusstack) {
      return 7930047;
   }

   public AspectList getVisCost(ItemStack focusstack) {
      return cost;
   }

   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
      switch(rank) {
      case 1:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
      case 2:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
      case 3:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
      case 4:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
      case 5:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, enervation};
      default:
         return null;
      }
   }

   public String getItemStackDisplayName(ItemStack stack) {
      return StatCollector.translateToLocal("item.focusDisintegration.name");
   }

   public static Entity getPointedEntity(World world, EntityLivingBase entityplayer, double range) {
      Entity pointedEntity = null;
      Vec3 vec3d = Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + (double)entityplayer.getEyeHeight(), entityplayer.posZ);
      Vec3 vec3d1 = entityplayer.getLookVec();
      Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * range, vec3d1.yCoord * range, vec3d1.zCoord * range);
      float f1 = 1.1F;
      List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.addCoord(vec3d1.xCoord * range, vec3d1.yCoord * range, vec3d1.zCoord * range).expand((double)f1, (double)f1, (double)f1));
      double d2 = 0.0D;

      for(int i = 0; i < list.size(); ++i) {
         Entity entity = (Entity)list.get(i);
         if((!(entity instanceof EntityItem) || ((EntityItem)entity).getEntityItem().getItem() != ConfigItems.itemCrystalEssence) && world.rayTraceBlocks(Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + (double)entityplayer.getEyeHeight(), entityplayer.posZ), Vec3.createVectorHelper(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ), false) == null) {
            float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
            AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
            if(axisalignedbb.isVecInside(vec3d)) {
               if(0.0D < d2 || d2 == 0.0D) {
                  pointedEntity = entity;
                  d2 = 0.0D;
               }
            } else if(movingobjectposition != null) {
               double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
               if(d3 < d2 || d2 == 0.0D) {
                  pointedEntity = entity;
                  d2 = d3;
               }
            }
         }
      }

      return pointedEntity;
   }

   void processBlock(int x, int y, int z, ItemWandCasting wand, ItemStack stack, EntityPlayer p, String pp) {
      GameType gt = GameType.SURVIVAL;
      if(p.capabilities.allowEdit) {
         if(p.capabilities.isCreativeMode) {
            gt = GameType.CREATIVE;
         }
      } else {
         gt = GameType.ADVENTURE;
      }

      Block bi = p.worldObj.getBlock(x, y, z);
      if(bi.getBlockHardness(p.worldObj, x, y, z) != -1.0F) {
         BreakEvent event = ForgeHooks.onBlockBreakEvent(p.worldObj, gt, (EntityPlayerMP)p, x, y, z);
         if(!event.isCanceled()) {
            int md = p.worldObj.getBlockMetadata(x, y, z);
            if(Item.getItemFromBlock(bi) != null && this.getAspects(Item.getItemFromBlock(bi), md) != null && this.getAspects(Item.getItemFromBlock(bi), md).size() > 0 && wand.consumeAllVis(stack, p, this.getVisCost(stack), true, true)) {
               AspectList aspects = this.getAspects(Item.getItemFromBlock(p.worldObj.getBlock(x, y, z)), p.worldObj.getBlockMetadata(x, y, z));
               if(aspects != null) {
                  Aspect[] var13 = aspects.getAspects();
                  int var14 = var13.length;

                  for(int var15 = 0; var15 < var14; ++var15) {
                     Aspect asp = var13[var15];
                     stack = new ItemStack(ConfigItems.itemCrystalEssence, aspects.getAmount(asp));
                     ((ItemCrystalEssence)stack.getItem()).setAspects(stack, (new AspectList()).add(asp, 1));
                     p.worldObj.spawnEntityInWorld(new EntityItemInvulnerable(p.worldObj, (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, stack));
                  }

                  ThaumicHorizons.proxy.disintegrateExplodeFX(p.worldObj, (double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D);
               }

               p.worldObj.setBlockToAir(x, y, z);
            }

            lastX.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            lastY.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            lastZ.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            breakcount.put(pp, Float.valueOf(0.0F));
            p.worldObj.markBlockForUpdate(x, y, z);
         }
      }
   }

   private AspectList getAspects(Item block, int meta) {
      ItemStack tmpStack = new ItemStack(block, 1, meta);
      AspectList tmp = ThaumcraftCraftingManager.getObjectTags(tmpStack);
      tmp = ThaumcraftCraftingManager.getBonusTags(tmpStack, tmp);
      if(tmp == null || tmp.size() == 0) {
         tmp = (AspectList)ThaumcraftApi.objectTags.get(Arrays.asList(new Object[]{block, Integer.valueOf(32767)}));
         if(meta == 32767 && tmp == null) {
            int index = 0;

            do {
               tmp = (AspectList)ThaumcraftApi.objectTags.get(Arrays.asList(new Object[]{block, Integer.valueOf(index)}));
               ++index;
            } while(index < 16 && tmp == null);
         }
      }

      return tmp;
   }

}
