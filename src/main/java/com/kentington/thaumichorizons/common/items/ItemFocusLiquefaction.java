package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.Utils;

public class ItemFocusLiquefaction extends ItemFocusBasic {

   public static FocusUpgradeType nuggets = new FocusUpgradeType(FocusUpgradeType.types.length, new ResourceLocation("thaumichorizons", "textures/foci/nuggets.png"), "focus.upgrade.nuggets.name", "focus.upgrade.nuggets.text", (new AspectList()).add(Aspect.METAL, 8));
   public static FocusUpgradeType purity = new FocusUpgradeType(FocusUpgradeType.types.length, new ResourceLocation("thaumichorizons", "textures/foci/purity.png"), "focus.upgrade.purity.name", "focus.upgrade.purity.text", (new AspectList()).add(Aspect.ORDER, 8));
   private static final AspectList cost = (new AspectList()).add(Aspect.FIRE, 40);
   private static final AspectList costCritter = (new AspectList()).add(Aspect.FIRE, 15);
   static HashMap soundDelay = new HashMap();
   static HashMap beam = new HashMap();
   static HashMap breakcount = new HashMap();
   static HashMap lastX = new HashMap();
   static HashMap lastY = new HashMap();
   static HashMap lastZ = new HashMap();


   public ItemFocusLiquefaction() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      super.icon = ir.registerIcon("thaumichorizons:focus_liquefaction");
   }

   public String getSortingHelper(ItemStack itemstack) {
      return "BE" + super.getSortingHelper(itemstack);
   }

   public int getFocusColor() {
      return 16729156;
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
         if((ent == null || ent instanceof EntityItem && FurnaceRecipes.smelting().getSmeltingResult(((EntityItem)ent).getEntityItem()) == null) && mop != null) {
            tx = mop.hitVec.xCoord;
            ty = mop.hitVec.yCoord;
            tz = mop.hitVec.zCoord;
            impact = 5;
            if(!p.worldObj.isRemote && ((Long)soundDelay.get(pp)).longValue() < System.currentTimeMillis()) {
               p.worldObj.playSoundEffect(tx, ty, tz, "fire.fire", 8.0F, 1.0F);
               soundDelay.put(pp, Long.valueOf(System.currentTimeMillis() + 1200L));
            }
         } else if(ent != null) {
            tx = ent.posX;
            ty = ent.posY;
            tz = ent.posZ;
            impact = 5;
            if(!p.worldObj.isRemote && ((Long)soundDelay.get(pp)).longValue() < System.currentTimeMillis()) {
               p.worldObj.playSoundEffect(tx, ty, tz, "fire.fire", 0.3F, 1.0F);
               soundDelay.put(pp, Long.valueOf(System.currentTimeMillis() + 1200L));
            }
         } else {
            soundDelay.put(pp, Long.valueOf(0L));
         }

         if(p.worldObj.isRemote) {
            beam.put(pp, Thaumcraft.proxy.beamCont(p.worldObj, p, tx, ty, tz, 0, 16729156, false, impact > 0?(float)size:0.0F, beam.get(pp), 5));
         }

         if((ent == null || !(ent instanceof EntityLiving) && (!(ent instanceof EntityItem) || FurnaceRecipes.smelting().getSmeltingResult(((EntityItem)ent).getEntityItem()) == null)) && mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
            Block var28 = p.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
            int var29 = p.worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
            int meltable = this.isMeltableBlock(var28, var29);
            boolean flammable = var28.isFlammable(p.worldObj, mop.blockX, mop.blockY, mop.blockZ, ForgeDirection.UNKNOWN);
            if(meltable > 0 || flammable || FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(var28, 1, var29)) != null) {
               int pot = wand.getFocusPotency(stack);
               float speed = 0.15F + (float)pot * 0.05F;
               float hardness = 2.0F;
               if(meltable > 0 || var28.isFlammable(p.worldObj, mop.blockX, mop.blockY, mop.blockZ, ForgeDirection.UP)) {
                  hardness = 0.25F;
               }

               if(meltable == 6) {
                  hardness = 20.0F;
               }

               if(((Integer)lastX.get(pp)).intValue() == mop.blockX && ((Integer)lastY.get(pp)).intValue() == mop.blockY && ((Integer)lastZ.get(pp)).intValue() == mop.blockZ) {
                  float bc = ((Float)breakcount.get(pp)).floatValue();
                  int x;
                  if(p.worldObj.isRemote && bc > 0.0F && var28 != null) {
                     x = (int)(bc / hardness * 9.0F);
                     ThaumicHorizons.proxy.smeltFX((double)mop.blockX, (double)mop.blockY, (double)mop.blockZ, p.worldObj, 15, size > 2);
                  }

                  if(p.worldObj.isRemote) {
                     if(bc >= hardness) {
                        p.worldObj.playAuxSFX(2001, mop.blockX, mop.blockY, mop.blockZ, 0);
                        breakcount.put(pp, Float.valueOf(0.0F));
                     } else {
                        breakcount.put(pp, Float.valueOf(bc + speed));
                     }
                  } else if(bc >= hardness) {
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
                     breakcount.put(pp, Float.valueOf(bc + speed));
                  }
               } else {
                  lastX.put(pp, Integer.valueOf(mop.blockX));
                  lastY.put(pp, Integer.valueOf(mop.blockY));
                  lastZ.put(pp, Integer.valueOf(mop.blockZ));
                  breakcount.put(pp, Float.valueOf(0.0F));
               }
            }
         } else if(ent != null && ent instanceof EntityLiving && wand.consumeAllVis(stack, p, costCritter, true, true)) {
            ThaumicHorizons.proxy.smeltFX(ent.posX - 0.5D, ent.posY, ent.posZ - 0.5D, p.worldObj, 5, false);
            ((EntityLiving)ent).attackEntityFrom(DamageSource.inFire, 1.0F + 0.5F * (float)wand.getFocusPotency(stack));
         } else if(ent != null && ent instanceof EntityItem && FurnaceRecipes.smelting().getSmeltingResult(((EntityItem)ent).getEntityItem()) != null) {
            int num = ((EntityItem)ent).getEntityItem().stackSize;
            if(wand.consumeAllVis(stack, p, this.getVisCost(stack), true, true)) {
               ThaumicHorizons.proxy.smeltFX(ent.posX - 0.5D, ent.posY, ent.posZ - 0.5D, p.worldObj, 5 * num, false);
               ItemStack stacky = FurnaceRecipes.smelting().getSmeltingResult(((EntityItem)ent).getEntityItem());
               stacky.stackSize = num;
               ((EntityItem)ent).setEntityItemStack(stacky);
            }
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

   public int isMeltableBlock(Block block, int meta) {
      if(block == Blocks.stone) {
         return 6;
      } else {
         Material mat = block.getMaterial();
         return mat == Material.tnt?4:(mat == Material.grass?3:(mat != Material.ice && mat != Material.packedIce?(mat != Material.craftedSnow && mat != Material.leaves && mat != Material.plants && mat != Material.snow && mat != Material.vine && mat != Material.web?0:1):2));
      }
   }

   public int getFocusColor(ItemStack focusstack) {
      return 16711680;
   }

   public AspectList getVisCost(ItemStack focusstack) {
      return cost;
   }

   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
      switch(rank) {
      case 1:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, nuggets};
      case 2:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, nuggets};
      case 3:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, FocusUpgradeType.enlarge};
      case 4:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, nuggets};
      case 5:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, purity};
      default:
         return null;
      }
   }

   public String getItemStackDisplayName(ItemStack stack) {
      return StatCollector.translateToLocal("item.focusLiquefaction.name");
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
         if(world.rayTraceBlocks(Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + (double)entityplayer.getEyeHeight(), entityplayer.posZ), Vec3.createVectorHelper(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ), false) == null) {
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

      BreakEvent event = ForgeHooks.onBlockBreakEvent(p.worldObj, gt, (EntityPlayerMP)p, x, y, z);
      if(!event.isCanceled()) {
         Block bi = p.worldObj.getBlock(x, y, z);
         if(bi.getBlockHardness(p.worldObj, x, y, z) != -1.0F) {
            int md = p.worldObj.getBlockMetadata(x, y, z);
            int meltable = this.isMeltableBlock(bi, md);
            boolean flammable = bi.isFlammable(p.worldObj, x, y, z, ForgeDirection.UNKNOWN);
            ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(bi, 1, md));
            if(result != null && wand.consumeAllVis(stack, p, this.getVisCost(stack), true, true)) {
               ItemStack theItem;
               if(this.getUpgradeLevel(wand.getFocusItem(stack), purity) > 0 && Utils.findSpecialMiningResult(new ItemStack(bi, 1, md), 9999.0F, p.worldObj.rand) != null) {
                  theItem = Utils.findSpecialMiningResult(new ItemStack(bi, 1, md), 9999.0F, p.worldObj.rand);
                  if(FurnaceRecipes.smelting().getSmeltingResult(theItem) != null) {
                     result = FurnaceRecipes.smelting().getSmeltingResult(theItem);
                  }
               }

               if(this.getUpgradeLevel(wand.getFocusItem(stack), nuggets) > 0 && ThaumcraftApi.getSmeltingBonus(new ItemStack(bi, 1, md)) != null) {
                  theItem = ThaumcraftApi.getSmeltingBonus(new ItemStack(bi, 1, md)).copy();

                  for(int bonusEntity = 0; bonusEntity < this.getUpgradeLevel(wand.getFocusItem(stack), nuggets); ++bonusEntity) {
                     if(p.worldObj.rand.nextFloat() < 0.45F) {
                        ++theItem.stackSize;
                     }
                  }

                  if(theItem.stackSize > 0) {
                     EntityItem var17 = new EntityItem(p.worldObj, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), theItem);
                     p.worldObj.spawnEntityInWorld(var17);
                  }
               }

               if(result.getItem() instanceof ItemBlock) {
                  p.worldObj.setBlock(x, y, z, Block.getBlockFromItem(result.getItem()));
                  p.worldObj.setBlockMetadataWithNotify(x, y, z, result.getItemDamage(), 3);
               } else {
                  p.worldObj.setBlockToAir(x, y, z);
                  EntityItem var18 = new EntityItem(p.worldObj, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F));
                  var18.setEntityItemStack(result.copy());
                  p.worldObj.spawnEntityInWorld(var18);
               }
            } else if(meltable > 0 && wand.consumeAllVis(stack, p, this.getVisCost(stack), true, true)) {
               if(meltable == 1) {
                  p.worldObj.setBlockToAir(x, y, z);
               } else if(meltable == 2) {
                  if(p.worldObj.provider.dimensionId != -1) {
                     p.worldObj.setBlock(x, y, z, Blocks.water, 0, 3);
                  } else {
                     p.worldObj.setBlockToAir(x, y, z);
                  }
               } else if(meltable == 3) {
                  p.worldObj.setBlock(x, y, z, Blocks.dirt, 0, 3);
               } else if(meltable == 4) {
                  Blocks.tnt.onBlockDestroyedByPlayer(p.worldObj, x, y, z, 1);
                  p.worldObj.setBlockToAir(x, y, z);
               } else if(meltable == 6) {
                  p.worldObj.setBlock(x, y, z, Blocks.lava, 0, 3);
               }
            } else if(flammable && wand.consumeAllVis(stack, p, this.getVisCost(stack), true, true)) {
               p.worldObj.setBlock(x, y, z, Blocks.fire);
            }

            lastX.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            lastY.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            lastZ.put(pp, Integer.valueOf(Integer.MAX_VALUE));
            breakcount.put(pp, Float.valueOf(0.0F));
            p.worldObj.markBlockForUpdate(x, y, z);
         }
      }
   }

}
