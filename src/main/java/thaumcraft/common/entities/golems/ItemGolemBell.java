package thaumcraft.common.entities.golems;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import thaumcraft.client.lib.PlayerNotifications;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.golems.EntityGolemBase;
import thaumcraft.common.entities.golems.EntityTravelingTrunk;
import thaumcraft.common.entities.golems.Marker;

public class ItemGolemBell extends Item {

   public IIcon icon;


   public ItemGolemBell() {
      this.setHasSubtypes(false);
      this.setCreativeTab(Thaumcraft.tabTC);
      this.setMaxStackSize(1);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumcraft:ironbell");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public boolean getShareTag() {
      return true;
   }

   public static int getGolemId(ItemStack stack) {
      return stack.hasTagCompound() && stack.stackTagCompound.hasKey("golemid")?stack.stackTagCompound.getInteger("golemid"):-1;
   }

   public static int getGolemHomeFace(ItemStack stack) {
      return stack.hasTagCompound() && stack.stackTagCompound.hasKey("golemhomeface")?stack.stackTagCompound.getInteger("golemhomeface"):-1;
   }

   public static ChunkCoordinates getGolemHomeCoords(ItemStack stack) {
      return stack.hasTagCompound() && stack.stackTagCompound.hasKey("golemhomex")?new ChunkCoordinates(stack.stackTagCompound.getInteger("golemhomex"), stack.stackTagCompound.getInteger("golemhomey"), stack.stackTagCompound.getInteger("golemhomez")):null;
   }

   public static ArrayList getMarkers(ItemStack stack) {
      ArrayList markers = new ArrayList();
      if(stack.hasTagCompound() && stack.stackTagCompound.hasKey("markers")) {
         NBTTagList tl = stack.stackTagCompound.getTagList("markers", 10);

         for(int i = 0; i < tl.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = tl.getCompoundTagAt(i);
            int x = nbttagcompound1.getInteger("x");
            int y = nbttagcompound1.getInteger("y");
            int z = nbttagcompound1.getInteger("z");
            int dim = nbttagcompound1.getInteger("dim");
            byte s = nbttagcompound1.getByte("side");
            byte c = nbttagcompound1.getByte("color");
            markers.add(new Marker(x, y, z, (byte)dim, s, c));
         }
      }

      return markers;
   }

   public static void resetMarkers(ItemStack stack, World world, EntityPlayer player) {
      Entity golem = null;
      int gid = getGolemId(stack);
      if(gid > -1) {
         golem = world.getEntityByID(gid);
         if(golem != null && golem instanceof EntityGolemBase) {
            stack.setTagInfo("markers", new NBTTagList());
            ((EntityGolemBase)golem).setMarkers(new ArrayList());
            world.playSoundAtEntity(player, "random.orb", 0.7F, 1.0F + world.rand.nextFloat() * 0.1F);
         }
      }

   }

   public static void changeMarkers(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int side) {
      Entity golem = null;
      ArrayList markers = getMarkers(stack);
      boolean markMultipleColors = false;
      int gid = getGolemId(stack);
      if(gid > -1) {
         golem = world.getEntityByID(gid);
         if(golem != null && golem instanceof EntityGolemBase && ((EntityGolemBase)golem).getUpgradeAmount(4) > 0) {
            markMultipleColors = true;
         }
      }

      int count = markers.size();
      int index = -1;
      int color = 0;
      if(!markMultipleColors) {
         index = markers.indexOf(new Marker(par4, par5, par6, world.provider.dimensionId, (byte)side, (byte)-1));
      } else {
         for(int tl = -1; tl < 16; ++tl) {
            index = markers.indexOf(new Marker(par4, par5, par6, world.provider.dimensionId, (byte)side, (byte)tl));
            color = tl;
            if(index != -1) {
               break;
            }
         }
      }

      if(index >= 0) {
         markers.remove(index);
         if(markMultipleColors && !player.isSneaking()) {
            ++color;
            if(color <= 15) {
               markers.add(new Marker(par4, par5, par6, world.provider.dimensionId, (byte)side, (byte)color));
               ++count;
               if(world.isRemote) {
                  String var18 = StatCollector.translateToLocal("tc.markerchange");
                  if(color > -1) {
                     var18 = var18.replaceAll("%n", UtilsFX.colorNames[color]);
                  } else {
                     var18 = StatCollector.translateToLocal("tc.markerchangeany");
                  }

                  PlayerNotifications.addNotification(var18);
               }
            }
         }
      } else {
         markers.add(new Marker(par4, par5, par6, world.provider.dimensionId, (byte)side, (byte)-1));
      }

      if(count != markers.size()) {
         NBTTagList var19 = new NBTTagList();
         Iterator var15 = markers.iterator();

         while(var15.hasNext()) {
            Marker l = (Marker)var15.next();
            NBTTagCompound nbtc = new NBTTagCompound();
            nbtc.setInteger("x", l.x);
            nbtc.setInteger("y", l.y);
            nbtc.setInteger("z", l.z);
            nbtc.setInteger("dim", l.dim);
            nbtc.setByte("side", l.side);
            nbtc.setByte("color", l.color);
            var19.appendTag(nbtc);
         }

         stack.setTagInfo("markers", var19);
         if(gid > -1) {
            if(golem != null && golem instanceof EntityGolemBase) {
               ((EntityGolemBase)golem).setMarkers(markers);
            } else {
               stack.getTagCompound().removeTag("golemid");
               stack.getTagCompound().removeTag("markers");
               stack.getTagCompound().removeTag("golemhomex");
               stack.getTagCompound().removeTag("golemhomey");
               stack.getTagCompound().removeTag("golemhomez");
               stack.getTagCompound().removeTag("golemhomeface");
            }
         }
      }

      world.playSoundEffect((double)par4, (double)par5, (double)par6, "random.orb", 0.7F, 1.0F + world.rand.nextFloat() * 0.1F);
   }

   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int side, float par8, float par9, float par10) {
      MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
      if(movingobjectposition == null) {
         return true;
      } else {
         if(movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            changeMarkers(stack, player, world, i, j, k, movingobjectposition.sideHit);
         }

         return !world.isRemote;
      }
   }

   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
      if(target instanceof EntityGolemBase) {
         if(stack.hasTagCompound()) {
            stack.getTagCompound().removeTag("golemid");
            stack.getTagCompound().removeTag("markers");
            stack.getTagCompound().removeTag("golemhomex");
            stack.getTagCompound().removeTag("golemhomey");
            stack.getTagCompound().removeTag("golemhomez");
            stack.getTagCompound().removeTag("golemhomeface");
         }

         if(target.worldObj.isRemote) {
            if(player != null) {
               player.swingItem();
            }
         } else {
            ArrayList markers = ((EntityGolemBase)target).getMarkers();
            NBTTagList tl = new NBTTagList();
            Iterator var6 = markers.iterator();

            while(var6.hasNext()) {
               Marker l = (Marker)var6.next();
               NBTTagCompound nbtc = new NBTTagCompound();
               nbtc.setInteger("x", l.x);
               nbtc.setInteger("y", l.y);
               nbtc.setInteger("z", l.z);
               nbtc.setInteger("dim", l.dim);
               nbtc.setByte("side", l.side);
               nbtc.setByte("color", l.color);
               tl.appendTag(nbtc);
            }

            stack.setTagInfo("markers", tl);
            stack.getTagCompound().setInteger("golemid", target.getEntityId());
            stack.getTagCompound().setInteger("golemhomex", ((EntityGolemBase)target).getHomePosition().posX);
            stack.getTagCompound().setInteger("golemhomey", ((EntityGolemBase)target).getHomePosition().posY);
            stack.getTagCompound().setInteger("golemhomez", ((EntityGolemBase)target).getHomePosition().posZ);
            stack.getTagCompound().setInteger("golemhomeface", ((EntityGolemBase)target).homeFacing);
            target.worldObj.playSoundAtEntity(target, "random.orb", 0.7F, 1.0F + target.worldObj.rand.nextFloat() * 0.1F);
            if(player != null && player.capabilities.isCreativeMode) {
               player.setCurrentItemOrArmor(0, stack.copy());
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if(entity instanceof EntityTravelingTrunk && !entity.isDead) {
         byte var18 = (byte)((EntityTravelingTrunk)entity).getUpgrade();
         if(var18 == 3 && !((EntityTravelingTrunk)entity).func_152113_b().equals(player.getCommandSenderName())) {
            return false;
         } else if(entity.worldObj.isRemote && entity instanceof EntityLiving) {
            ((EntityLiving)entity).spawnExplosionParticle();
            return false;
         } else {
            ItemStack var20 = new ItemStack(ConfigItems.itemTrunkSpawner);
            if(player.isSneaking()) {
               if(var18 > -1 && entity.worldObj.rand.nextBoolean()) {
                  ((EntityTravelingTrunk)entity).entityDropItem(new ItemStack(ConfigItems.itemGolemUpgrade, 1, var18), 0.5F);
               }
            } else {
               if(((EntityTravelingTrunk)entity).hasCustomNameTag()) {
                  var20.setStackDisplayName(((EntityTravelingTrunk)entity).getCustomNameTag());
               }

               var20.setTagInfo("upgrade", new NBTTagByte(var18));
               if(var18 == 4) {
                  var20.setTagInfo("inventory", ((EntityTravelingTrunk)entity).inventory.writeToNBT(new NBTTagList()));
               }
            }

            ((EntityTravelingTrunk)entity).entityDropItem(var20, 0.5F);
            if(var18 != 4 || player.isSneaking()) {
               ((EntityTravelingTrunk)entity).inventory.dropAllItems();
            }

            entity.worldObj.playSoundAtEntity(entity, "thaumcraft:zap", 0.5F, 1.0F);
            entity.setDead();
            return true;
         }
      } else if(entity instanceof EntityGolemBase && !(entity instanceof EntityGolemTH) && !entity.isDead) {
         if(entity.worldObj.isRemote && entity instanceof EntityLiving) {
            ((EntityLiving)entity).spawnExplosionParticle();
            return false;
         } else {
            int var17 = ((EntityGolemBase)entity).golemType.ordinal();
            String var19 = ((EntityGolemBase)entity).decoration;
            byte var21 = ((EntityGolemBase)entity).getCore();
            byte[] var22 = ((EntityGolemBase)entity).upgrades;
            boolean var23 = ((EntityGolemBase)entity).advanced;
            ItemStack var24 = new ItemStack(ConfigItems.itemGolemPlacer, 1, var17);
            if(var23) {
               var24.setTagInfo("advanced", new NBTTagByte((byte)1));
            }

            if(player.isSneaking()) {
               if(var21 > -1) {
                  ((EntityGolemBase)entity).entityDropItem(new ItemStack(ConfigItems.itemGolemCore, 1, var21), 0.5F);
               }

               byte[] var26 = var22;
               int var25 = var22.length;

               for(int var29 = 0; var29 < var25; ++var29) {
                  byte var31 = var26[var29];
                  if(var31 > -1 && entity.worldObj.rand.nextBoolean()) {
                     ((EntityGolemBase)entity).entityDropItem(new ItemStack(ConfigItems.itemGolemUpgrade, 1, var31), 0.5F);
                  }
               }
            } else {
               if(((EntityGolemBase)entity).hasCustomNameTag()) {
                  var24.setStackDisplayName(((EntityGolemBase)entity).getCustomNameTag());
               }

               if(var19.length() > 0) {
                  var24.setTagInfo("deco", new NBTTagString(var19));
               }

               if(var21 > -1) {
                  var24.setTagInfo("core", new NBTTagByte(var21));
               }

               var24.setTagInfo("upgrades", new NBTTagByteArray(var22));
               ArrayList var27 = ((EntityGolemBase)entity).getMarkers();
               NBTTagList var30 = new NBTTagList();
               Iterator var34 = var27.iterator();

               while(var34.hasNext()) {
                  Marker var32 = (Marker)var34.next();
                  NBTTagCompound var35 = new NBTTagCompound();
                  var35.setInteger("x", var32.x);
                  var35.setInteger("y", var32.y);
                  var35.setInteger("z", var32.z);
                  var35.setInteger("dim", var32.dim);
                  var35.setByte("side", var32.side);
                  var35.setByte("color", var32.color);
                  var30.appendTag(var35);
               }

               var24.setTagInfo("markers", var30);
               var24.setTagInfo("Inventory", ((EntityGolemBase)entity).inventory.writeToNBT(new NBTTagList()));
            }

            ((EntityGolemBase)entity).entityDropItem(var24, 0.5F);
            ((EntityGolemBase)entity).dropStuff();
            entity.worldObj.playSoundAtEntity(entity, "thaumcraft:zap", 0.5F, 1.0F);
            entity.setDead();
            return true;
         }
      } else if(entity instanceof EntityGolemTH && !entity.isDead) {
         if(entity.worldObj.isRemote && entity instanceof EntityLiving) {
            ((EntityLiving)entity).spawnExplosionParticle();
            return false;
         } else {
            EntityGolemTH golem = (EntityGolemTH)entity;
            if(golem.getCore() == -1) {
               golem.ticksAlive = 0;
               return true;
            } else {
               int type = golem.type.ordinal();
               String deco = golem.decoration;
               byte core = golem.getCore();
               byte[] upgrades = golem.upgrades;
               int[] var10000 = new int[2];
               Block var10003 = golem.blocky;
               var10000[0] = Block.getIdFromBlock(golem.blocky);
               var10000[1] = golem.md;
               int[] blockData = var10000;
               boolean advanced = golem.advanced;
               ItemStack dropped = new ItemStack(ThaumicHorizons.itemGolemPlacer, 1, type);
               if(advanced) {
                  dropped.setTagInfo("advanced", new NBTTagByte((byte)1));
               }

               if(player.isSneaking()) {
                  if(core > -1) {
                     ((EntityGolemBase)entity).entityDropItem(new ItemStack(ConfigItems.itemGolemCore, 1, core), 0.5F);
                  }

                  byte[] var28 = upgrades;
                  int var33 = upgrades.length;

                  for(int var37 = 0; var37 < var33; ++var37) {
                     byte var36 = var28[var37];
                     if(var36 > -1 && entity.worldObj.rand.nextBoolean()) {
                        ((EntityGolemBase)entity).entityDropItem(new ItemStack(ConfigItems.itemGolemUpgrade, 1, var36), 0.5F);
                     }
                  }

                  golem.die();
                  return true;
               } else {
                  if(((EntityGolemBase)entity).hasCustomNameTag()) {
                     dropped.setStackDisplayName(((EntityGolemBase)entity).getCustomNameTag());
                  }

                  if(deco.length() > 0) {
                     dropped.setTagInfo("deco", new NBTTagString(deco));
                  }

                  if(core > -1) {
                     dropped.setTagInfo("core", new NBTTagByte(core));
                  }

                  dropped.setTagInfo("upgrades", new NBTTagByteArray(upgrades));
                  dropped.setTagInfo("block", new NBTTagIntArray(blockData));
                  dropped.stackTagCompound.setBoolean("berserk", golem.berserk);
                  dropped.stackTagCompound.setBoolean("explosive", golem.kaboom);
                  ArrayList markers = ((EntityGolemBase)entity).getMarkers();
                  NBTTagList tl = new NBTTagList();
                  Iterator nbtc = markers.iterator();

                  while(nbtc.hasNext()) {
                     Marker l = (Marker)nbtc.next();
                     NBTTagCompound nbtc1 = new NBTTagCompound();
                     nbtc1.setInteger("x", l.x);
                     nbtc1.setInteger("y", l.y);
                     nbtc1.setInteger("z", l.z);
                     nbtc1.setInteger("dim", l.dim);
                     nbtc1.setByte("side", l.side);
                     nbtc1.setByte("color", l.color);
                     tl.appendTag(nbtc1);
                  }

                  dropped.setTagInfo("markers", tl);
                  dropped.setTagInfo("Inventory", ((EntityGolemBase)entity).inventory.writeToNBT(new NBTTagList()));
                  ((EntityGolemBase)entity).entityDropItem(dropped, 0.5F);
                  ((EntityGolemBase)entity).dropStuff();
                  entity.worldObj.playSoundAtEntity(entity, "thaumcraft:zap", 0.5F, 1.0F);
                  entity.setDead();
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }
}
