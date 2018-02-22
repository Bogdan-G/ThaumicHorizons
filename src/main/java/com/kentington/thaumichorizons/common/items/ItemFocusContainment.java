package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntitySoul;
import com.kentington.thaumichorizons.common.lib.PacketFXContainment;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.InventoryUtils;

public class ItemFocusContainment extends ItemFocusBasic {

   public static FocusUpgradeType slow = new FocusUpgradeType(FocusUpgradeType.types.length, new ResourceLocation("thaumichorizons", "textures/foci/slow.png"), "focus.upgrade.slow.name", "focus.upgrade.slow.text", (new AspectList()).add(Aspect.TRAP, 8));
   public static HashMap beam = new HashMap();
   public static HashMap hitCritters = new HashMap();
   public static HashMap contain = new HashMap();
   public static HashMap soundDelay = new HashMap();
   IIcon depthIcon = null;
   private static final AspectList cost = (new AspectList()).add(Aspect.AIR, 10).add(Aspect.ENTROPY, 10);


   public ItemFocusContainment() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public String getItemStackDisplayName(ItemStack stack) {
      return StatCollector.translateToLocal("item.focusContainment.name");
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.depthIcon = ir.registerIcon("thaumichorizons:focus_containment_depth");
      super.icon = ir.registerIcon("thaumichorizons:focus_containment");
   }

   public IIcon getFocusDepthLayerIcon(ItemStack itemstack) {
      return this.depthIcon;
   }

   public int getFocusColor(ItemStack itemstack) {
      return 29631;
   }

   public String getSortingHelper(ItemStack itemstack) {
      return "CN" + super.getSortingHelper(itemstack);
   }

   public AspectList getVisCost(ItemStack focusstack) {
      return cost.copy();
   }

   public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
      switch(rank) {
      case 1:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
      case 2:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, slow};
      case 3:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
      case 4:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency, slow};
      case 5:
         return new FocusUpgradeType[]{FocusUpgradeType.frugal, FocusUpgradeType.potency};
      default:
         return null;
      }
   }

   public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition movingobjectposition) {
      ItemWandCasting wand = (ItemWandCasting)itemstack.getItem();
      p.setItemInUse(itemstack, Integer.MAX_VALUE);
      return itemstack;
   }

   public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int count) {
      ItemWandCasting wand = (ItemWandCasting)stack.getItem();
      if(!this.canJarEntity(p)) {
         if(p.ticksExisted % 5 == 0) {
            p.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + StatCollector.translateToLocal("thaumichorizons.noJar")));
         }

         p.stopUsingItem();
      } else if(!wand.consumeAllVis(stack, p, this.getVisCost(stack), false, false)) {
         p.stopUsingItem();
      } else {
         String pp = "R" + p.getDisplayName();
         Entity ent = getPointedEntity(p.worldObj, p, 10.0D);
         MovingObjectPosition mop = BlockUtils.getTargetBlock(p.worldObj, p, true);
         Vec3 v = p.getLookVec();
         double tx = p.posX + v.xCoord * 10.0D;
         double ty = p.posY + v.yCoord * 10.0D;
         double tz = p.posZ + v.zCoord * 10.0D;
         byte impact = 0;
         if(ent != null && ent instanceof EntityLiving) {
            tx = ent.posX;
            ty = ent.posY + (ent.boundingBox.maxY - ent.boundingBox.minY) / 2.0D;
            tz = ent.posZ;
            impact = 5;
            if(p != null && p.worldObj != null && !p.worldObj.isRemote && ((Long)soundDelay.get(pp) == null || ((Long)soundDelay.get(pp)).longValue() < System.currentTimeMillis())) {
               ent.worldObj.playSoundEffect(tx, ty, tz, "thaumcraft:jacobs", 0.3F, 1.0F);
               soundDelay.put(pp, Long.valueOf(System.currentTimeMillis() + 1200L));
            }
         } else if(mop != null) {
            tx = mop.hitVec.xCoord;
            ty = mop.hitVec.yCoord;
            tz = mop.hitVec.zCoord;
            impact = 5;
         } else {
            soundDelay.put(pp, Long.valueOf(0L));
         }

         if(p.worldObj.isRemote) {
            beam.put(pp, Thaumcraft.proxy.beamCont(p.worldObj, p, tx, ty, tz, 2, 4482815, true, impact > 0?2.0F:0.0F, beam.get(pp), impact));
         }

         if(ent != null && ent instanceof EntityLiving && !(ent instanceof EntityPlayer) && !ent.isDead && !(ent instanceof IBossDisplayData) && !(ent instanceof EntityGolemBase) && wand.consumeAllVis(stack, p, this.getVisCost(stack), true, false)) {
            if(this.getUpgradeLevel(wand.getFocusItem(stack), slow) > 0) {
               ((EntityLiving)ent).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40, this.getUpgradeLevel(wand.getFocusItem(stack), slow) - 1));
            }

            if(hitCritters.get(pp) != null && ent.getEntityId() == ((Entity)hitCritters.get(pp)).getEntityId()) {
               contain.put(pp, Float.valueOf(((Float)contain.get(pp)).floatValue() + 2.0F + (float)(wand.getFocusPotency(stack) / 3)));
            } else {
               hitCritters.put(pp, ent);
               contain.put(pp, Float.valueOf(2.0F + (float)wand.getFocusPotency(stack) / 2.0F));
            }

            if(!p.worldObj.isRemote && ((Float)contain.get(pp)).floatValue() > ((EntityLiving)ent).getHealth() * 20.0F && !(ent instanceof EntitySoul)) {
               NBTTagCompound jar1 = new NBTTagCompound();
               jar1.setString("id", EntityList.getEntityString(ent));
               ent.writeToNBT(jar1);
               this.jarEntity(p, jar1, ent.getCommandSenderName(), ent.posX, ent.posY + (double)(ent.height / 2.0F), ent.posZ);
               p.worldObj.playSoundEffect(ent.posX, ent.posY + (ent.boundingBox.maxY - ent.boundingBox.minY) / 2.0D, ent.posZ, "thaumcraft:craftfail", 1.0F, 1.0F);
               contain.remove(pp);
               hitCritters.remove(pp);
               p.worldObj.removeEntity(ent);
            } else if(!p.worldObj.isRemote && ((Float)contain.get(pp)).floatValue() > ((EntityLiving)ent).getHealth() * 20.0F) {
               p.worldObj.playSoundEffect(ent.posX, ent.posY + (ent.boundingBox.maxY - ent.boundingBox.minY) / 2.0D, ent.posZ, "thaumcraft:craftfail", 1.0F, 1.0F);
               p.inventory.decrStackSize(InventoryUtils.isPlayerCarrying(p, new ItemStack(ConfigBlocks.blockJar, 1, 0)), 1);
               ItemStack jar = new ItemStack(ThaumicHorizons.blockJar);
               jar.setTagCompound(new NBTTagCompound());
               jar.getTagCompound().setBoolean("isSoul", true);
               if(!p.inventory.addItemStackToInventory(jar)) {
                  p.entityDropItem(jar, 1.0F);
               }

               if(!p.worldObj.isRemote) {
                  PacketHandler.INSTANCE.sendToAllAround(new PacketFXContainment(ent.posX, ent.posY, ent.posZ), new TargetPoint(p.worldObj.provider.dimensionId, ent.posX, ent.posY, ent.posZ, 32.0D));
               }

               contain.remove(pp);
               hitCritters.remove(pp);
               p.worldObj.removeEntity(ent);
            }
         }

      }
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
         if(entity.canBeCollidedWith() && world.rayTraceBlocks(Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + (double)entityplayer.getEyeHeight(), entityplayer.posZ), Vec3.createVectorHelper(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ), false) == null) {
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

   boolean canJarEntity(EntityPlayer p) {
      return InventoryUtils.inventoryContains(p.inventory, new ItemStack(ConfigBlocks.blockJar, 1, 0), 0, true, true, false) && InventoryUtils.placeItemStackIntoInventory(new ItemStack(ThaumicHorizons.blockJar), p.inventory, 0, false) == null;
   }

   void jarEntity(EntityPlayer p, NBTTagCompound tag, String name, double x, double y, double z) {
      p.inventory.decrStackSize(InventoryUtils.isPlayerCarrying(p, new ItemStack(ConfigBlocks.blockJar, 1, 0)), 1);
      ItemStack jar = new ItemStack(ThaumicHorizons.blockJar);
      jar.setTagCompound(tag);
      jar.getTagCompound().setString("jarredCritterName", name);
      jar.getTagCompound().setBoolean("isSoul", false);
      if(!p.inventory.addItemStackToInventory(jar)) {
         p.entityDropItem(jar, 1.0F);
      }

      if(!p.worldObj.isRemote) {
         PacketHandler.INSTANCE.sendToAllAround(new PacketFXContainment(x, y, z), new TargetPoint(p.worldObj.provider.dimensionId, x, y, z, 32.0D));
      }

   }

   public boolean isVisCostPerTick(ItemStack focusstack) {
      return true;
   }

   public ItemFocusBasic.WandFocusAnimation getAnimation(ItemStack focusstack) {
      return ItemFocusBasic.WandFocusAnimation.CHARGE;
   }

}
