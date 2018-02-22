package com.kentington.thaumichorizons.common.items;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityBoatThaumium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemBoatThaumium extends ItemBoat {

   @SideOnly(Side.CLIENT)
   public IIcon icon;


   public ItemBoatThaumium() {
      super.maxStackSize = 1;
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      float f = 1.0F;
      float f1 = p_77659_3_.prevRotationPitch + (p_77659_3_.rotationPitch - p_77659_3_.prevRotationPitch) * f;
      float f2 = p_77659_3_.prevRotationYaw + (p_77659_3_.rotationYaw - p_77659_3_.prevRotationYaw) * f;
      double d0 = p_77659_3_.prevPosX + (p_77659_3_.posX - p_77659_3_.prevPosX) * (double)f;
      double d1 = p_77659_3_.prevPosY + (p_77659_3_.posY - p_77659_3_.prevPosY) * (double)f + 1.62D - (double)p_77659_3_.yOffset;
      double d2 = p_77659_3_.prevPosZ + (p_77659_3_.posZ - p_77659_3_.prevPosZ) * (double)f;
      Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
      float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
      float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
      float f5 = -MathHelper.cos(-f1 * 0.017453292F);
      float f6 = MathHelper.sin(-f1 * 0.017453292F);
      float f7 = f4 * f5;
      float f8 = f3 * f5;
      double d3 = 5.0D;
      Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
      MovingObjectPosition movingobjectposition = p_77659_2_.rayTraceBlocks(vec3, vec31, true);
      if(movingobjectposition == null) {
         return p_77659_1_;
      } else {
         Vec3 vec32 = p_77659_3_.getLook(f);
         boolean flag = false;
         float f9 = 1.0F;
         List list = p_77659_2_.getEntitiesWithinAABBExcludingEntity(p_77659_3_, p_77659_3_.boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand((double)f9, (double)f9, (double)f9));

         int i;
         for(i = 0; i < list.size(); ++i) {
            Entity j = (Entity)list.get(i);
            if(j.canBeCollidedWith()) {
               float k = j.getCollisionBorderSize();
               AxisAlignedBB entityboat = j.boundingBox.expand((double)k, (double)k, (double)k);
               if(entityboat.isVecInside(vec3)) {
                  flag = true;
               }
            }
         }

         if(flag) {
            return p_77659_1_;
         } else {
            if(movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
               i = movingobjectposition.blockX;
               int var34 = movingobjectposition.blockY;
               int var32 = movingobjectposition.blockZ;
               if(p_77659_2_.getBlock(i, var34, var32) == Blocks.snow_layer) {
                  --var34;
               }

               EntityBoatThaumium var33 = new EntityBoatThaumium(p_77659_2_, (double)((float)i + 0.5F), (double)((float)var34 + 1.0F), (double)((float)var32 + 0.5F));
               var33.rotationYaw = (float)(((MathHelper.floor_double((double)(p_77659_3_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90);
               if(!p_77659_2_.getCollidingBoundingBoxes(var33, var33.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
                  return p_77659_1_;
               }

               if(!p_77659_2_.isRemote) {
                  p_77659_2_.spawnEntityInWorld(var33);
               }

               if(!p_77659_3_.capabilities.isCreativeMode) {
                  --p_77659_1_.stackSize;
               }
            }

            return p_77659_1_;
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:boatthaumium");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.boatThaumium";
   }
}
