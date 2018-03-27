package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityItemInvulnerable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.damagesource.DamageSourceThaumcraft;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.blocks.BlockAiry;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemCrystalEssence;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileNode;

public class ExplosionAlchemite extends Explosion {

   public boolean field_77286_a;
   public boolean field_82755_b = true;
   private int field_77289_h = 16;
   private Random explosionRNG = new org.bogdang.modifications.random.XSTR();
   private World worldObj;
   public double field_77284_b;
   public double field_77285_c;
   public double field_77282_d;
   public Entity field_77283_e;
   public float field_77280_f;
   public List field_77281_g = new ArrayList();
   private Map field_77288_k = new HashMap();
   private static final String __OBFID = "CL_00000134";


   public ExplosionAlchemite(World p_i1948_1_, Entity p_i1948_2_, double p_i1948_3_, double p_i1948_5_, double p_i1948_7_, float p_i1948_9_) {
      super(p_i1948_1_, p_i1948_2_, p_i1948_3_, p_i1948_5_, p_i1948_7_, p_i1948_9_);
      this.worldObj = p_i1948_1_;
      this.field_77283_e = p_i1948_2_;
      this.field_77280_f = p_i1948_9_;
      this.field_77284_b = p_i1948_3_;
      this.field_77285_c = p_i1948_5_;
      this.field_77282_d = p_i1948_7_;
   }

   public void doExplosionA() {
      float f = this.field_77280_f;
      HashSet hashset = new HashSet();

      int i;
      int j;
      int k;
      double d5;
      double d6;
      double d7;
      for(i = 0; i < this.field_77289_h; ++i) {
         for(j = 0; j < this.field_77289_h; ++j) {
            for(k = 0; k < this.field_77289_h; ++k) {
               if(i == 0 || i == this.field_77289_h - 1 || j == 0 || j == this.field_77289_h - 1 || k == 0 || k == this.field_77289_h - 1) {
                  double i2 = (double)((float)i / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                  double j2 = (double)((float)j / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                  double vec3 = (double)((float)k / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                  double entity = Math.sqrt(i2 * i2 + j2 * j2 + vec3 * vec3);
                  i2 /= entity;
                  j2 /= entity;
                  vec3 /= entity;
                  float f1 = this.field_77280_f * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                  d5 = this.field_77284_b;
                  d6 = this.field_77285_c;
                  d7 = this.field_77282_d;

                  for(float d9 = 0.3F; f1 > 0.0F; f1 -= d9 * 0.75F) {
                     int j1 = MathHelper.floor_double(d5);
                     int d10 = MathHelper.floor_double(d6);
                     int l1 = MathHelper.floor_double(d7);
                     Block d11 = this.worldObj.getBlock(j1, d10, l1);
                     if(this.worldObj.getTileEntity(j1, d10, l1) != null && this.worldObj.getTileEntity(j1, d10, l1) instanceof TileNode) {
                        hashset.add(new ChunkPosition(j1, d10, l1));
                     } else if(d11.getMaterial() != Material.air) {
                        float f3 = this.field_77283_e != null?this.field_77283_e.func_145772_a(this, this.worldObj, j1, d10, l1, d11):d11.getExplosionResistance(this.field_77283_e, this.worldObj, j1, d10, l1, this.field_77284_b, this.field_77285_c, this.field_77282_d);
                        f1 -= (f3 + 0.3F) * d9;
                     }

                     if(f1 > 0.0F && (this.field_77283_e == null || this.field_77283_e.func_145774_a(this, this.worldObj, j1, d10, l1, d11, f1))) {
                        hashset.add(new ChunkPosition(j1, d10, l1));
                     }

                     d5 += i2 * (double)d9;
                     d6 += j2 * (double)d9;
                     d7 += vec3 * (double)d9;
                  }
               }
            }
         }
      }

      this.field_77281_g.addAll(hashset);
      this.field_77280_f *= 2.0F;
      i = MathHelper.floor_double(this.field_77284_b - (double)this.field_77280_f - 1.0D);
      j = MathHelper.floor_double(this.field_77284_b + (double)this.field_77280_f + 1.0D);
      k = MathHelper.floor_double(this.field_77285_c - (double)this.field_77280_f - 1.0D);
      int var29 = MathHelper.floor_double(this.field_77285_c + (double)this.field_77280_f + 1.0D);
      int l = MathHelper.floor_double(this.field_77282_d - (double)this.field_77280_f - 1.0D);
      int var30 = MathHelper.floor_double(this.field_77282_d + (double)this.field_77280_f + 1.0D);
      List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.field_77283_e, AxisAlignedBB.getBoundingBox((double)i, (double)k, (double)l, (double)j, (double)var29, (double)var30));
      Vec3 var31 = Vec3.createVectorHelper(this.field_77284_b, this.field_77285_c, this.field_77282_d);

      for(int i1 = 0; i1 < list.size(); ++i1) {
         Entity var32 = (Entity)list.get(i1);
         double d4 = var32.getDistance(this.field_77284_b, this.field_77285_c, this.field_77282_d) / (double)this.field_77280_f;
         if(d4 <= 1.0D) {
            d5 = var32.posX - this.field_77284_b;
            d6 = var32.posY + (double)var32.getEyeHeight() - this.field_77285_c;
            d7 = var32.posZ - this.field_77282_d;
            double var34 = (double)MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
            if(var34 != 0.0D) {
               d5 /= var34;
               d6 /= var34;
               d7 /= var34;
               double var33 = (double)this.worldObj.getBlockDensity(var31, var32.boundingBox);
               double var35 = (1.0D - d4) * var33;
               var32.attackEntityFrom(DamageSourceThaumcraft.dissolve, (float)((int)((var35 * var35 + var35) / 2.0D * 16.0D * (double)this.field_77280_f + 1.0D)));
               double d8 = EnchantmentProtection.func_92092_a(var32, var35);
               var32.motionX += d5 * d8;
               var32.motionY += d6 * d8;
               var32.motionZ += d7 * d8;
               if(var32 instanceof EntityPlayer) {
                  this.field_77288_k.put((EntityPlayer)var32, Vec3.createVectorHelper(d5 * var35, d6 * var35, d7 * var35));
               }
            }
         }
      }

      this.field_77280_f = f;
   }

   public void doExplosionB(boolean p_77279_1_) {
      this.worldObj.playSoundEffect(this.field_77284_b, this.field_77285_c, this.field_77282_d, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      if(this.field_77280_f >= 2.0F && this.field_82755_b) {
         this.worldObj.spawnParticle("hugeexplosion", this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0D, 0.0D, 0.0D);
      } else {
         this.worldObj.spawnParticle("largeexplode", this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0D, 0.0D, 0.0D);
      }

      ThaumicHorizons.proxy.alchemiteFX(this.worldObj, this.field_77284_b, this.field_77285_c, this.field_77282_d);
      Iterator iterator;
      ChunkPosition chunkposition;
      int i;
      int j;
      int k;
      Block block;
      if(this.field_82755_b) {
         iterator = this.field_77281_g.iterator();

         while(iterator.hasNext()) {
            chunkposition = (ChunkPosition)iterator.next();
            i = chunkposition.chunkPosX;
            j = chunkposition.chunkPosY;
            k = chunkposition.chunkPosZ;
            block = this.worldObj.getBlock(i, j, k);
            AspectList block1;
            ItemStack stack;
            if(this.worldObj.getTileEntity(i, j, k) != null && this.worldObj.getTileEntity(i, j, k) instanceof TileNode) {
               TileNode var19 = (TileNode)this.worldObj.getTileEntity(i, j, k);
               double var18 = (new org.bogdang.modifications.random.XSTR()).nextFloat();
               if(var18 >= 0.25D) {
                  if(var18 < 0.5D) {
                     var19.setNodeModifier(NodeModifier.FADING);
                  } else {
                     var19.setNodeModifier((NodeModifier)null);
                     var19.setNodeType(NodeType.UNSTABLE);
                  }
               } else {
                  block1 = var19.getAspects();
                  if(block1 != null) {
                     Aspect[] var20 = block1.getAspects();
                     int var14 = var20.length;

                     for(int var15 = 0; var15 < var14; ++var15) {
                        Aspect asp1 = var20[var15];
                        stack = new ItemStack(ConfigItems.itemCrystalEssence, block1.getAmount(asp1));
                        ((ItemCrystalEssence)stack.getItem()).setAspects(stack, (new AspectList()).add(asp1, 1));
                        this.worldObj.spawnEntityInWorld(new EntityItemInvulnerable(this.worldObj, (double)i, (double)j, (double)k, stack));
                     }

                     ThaumicHorizons.proxy.disintegrateExplodeFX(this.worldObj, (double)i, (double)j, (double)k);
                  }

                  BlockAiry.explodify(this.worldObj, i, j, k);
               }
            } else if(block.getMaterial() != Material.air) {
               block1 = this.getAspects(Item.getItemFromBlock(block), this.worldObj.getBlockMetadata(i, j, k));
               if(block1 != null && block1.size() > 0) {
                  Aspect[] node = block1.getAspects();
                  int d = node.length;

                  for(int var12 = 0; var12 < d; ++var12) {
                     Aspect asp = node[var12];
                     stack = new ItemStack(ConfigItems.itemCrystalEssence, block1.getAmount(asp));
                     ((ItemCrystalEssence)stack.getItem()).setAspects(stack, (new AspectList()).add(asp, 1));
                     this.worldObj.spawnEntityInWorld(new EntityItemInvulnerable(this.worldObj, (double)i, (double)j, (double)k, stack));
                  }

                  ThaumicHorizons.proxy.disintegrateExplodeFX(this.worldObj, (double)i, (double)j, (double)k);
               } else {
                  block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), 1.0F, 0);
               }

               block.onBlockExploded(this.worldObj, i, j, k, this);
            }
         }
      }

      if(this.field_77286_a) {
         iterator = this.field_77281_g.iterator();

         while(iterator.hasNext()) {
            chunkposition = (ChunkPosition)iterator.next();
            i = chunkposition.chunkPosX;
            j = chunkposition.chunkPosY;
            k = chunkposition.chunkPosZ;
            block = this.worldObj.getBlock(i, j, k);
            Block var17 = this.worldObj.getBlock(i, j - 1, k);
            if(block.getMaterial() == Material.air && var17.func_149730_j() && this.explosionRNG.nextInt(3) == 0) {
               this.worldObj.setBlock(i, j, k, Blocks.fire);
            }
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

   public Map func_77277_b() {
      return this.field_77288_k;
   }

   public EntityLivingBase getExplosivePlacedBy() {
      return this.field_77283_e == null?null:(this.field_77283_e instanceof EntityTNTPrimed?((EntityTNTPrimed)this.field_77283_e).getTntPlacedBy():(this.field_77283_e instanceof EntityLivingBase?(EntityLivingBase)this.field_77283_e:null));
   }
}
