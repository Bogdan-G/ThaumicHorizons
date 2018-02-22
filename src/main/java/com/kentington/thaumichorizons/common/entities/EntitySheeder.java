package com.kentington.thaumichorizons.common.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class EntitySheeder extends EntitySpider implements IShearable {

   private int sheepTimer;
   private EntityAIEatGrass field_146087_bs = new EntityAIEatGrass(this);


   public EntitySheeder(World p_i1743_1_) {
      super(p_i1743_1_);
      this.getNavigator().setAvoidsWater(true);
      super.tasks.addTask(0, new EntityAISwimming(this));
      super.tasks.addTask(1, new EntityAIPanic(this, 0.5D));
      super.tasks.addTask(3, new EntityAITempt(this, 0.44D, Items.wheat, false));
      super.tasks.addTask(5, this.field_146087_bs);
      super.tasks.addTask(6, new EntityAIWander(this, 0.4D));
      super.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
      super.tasks.addTask(8, new EntityAILookIdle(this));
   }

   public void onUpdate() {
      super.onUpdate();
      if(super.isDead && !super.worldObj.isRemote && super.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.getHealth() > 0.0F) {
         super.isDead = false;
      }

   }

   protected Entity findPlayerToAttack() {
      return null;
   }

   protected String getLivingSound() {
      return "mob.sheep.say";
   }

   public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
      return !this.getSheared() && !this.isChild();
   }

   public ArrayList onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
      ArrayList ret = new ArrayList();
      this.setSheared(true);
      int i = 2 + super.rand.nextInt(4);

      for(int j = 0; j < i; ++j) {
         ret.add(new ItemStack(Items.string, 1, 0));
      }

      this.playSound("mob.sheep.shear", 1.0F, 1.0F);
      return ret;
   }

   public void setSheared(boolean p_70893_1_) {
      byte b0 = super.dataWatcher.getWatchableObjectByte(16);
      if(p_70893_1_) {
         super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 16)));
      } else {
         super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -17)));
      }

   }

   public boolean getSheared() {
      return (super.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
   }

   public void eatGrassBonus() {
      this.setSheared(false);
   }

   public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
      super.writeEntityToNBT(p_70014_1_);
      p_70014_1_.setBoolean("Sheared", this.getSheared());
   }

   public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
      super.readEntityFromNBT(p_70037_1_);
      this.setSheared(p_70037_1_.getBoolean("Sheared"));
   }

   protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
      if(!this.getSheared()) {
         this.entityDropItem(new ItemStack(Items.string), 0.0F);
      }

   }

   protected Item getDropItem() {
      return Items.string;
   }

   protected boolean isAIEnabled() {
      return true;
   }

   protected void updateAITasks() {
      this.sheepTimer = this.field_146087_bs.func_151499_f();
      super.updateAITasks();
   }

   public void onLivingUpdate() {
      if(super.worldObj.isRemote) {
         this.sheepTimer = Math.max(0, this.sheepTimer - 1);
      }

      super.onLivingUpdate();
   }

   @SideOnly(Side.CLIENT)
   public void handleHealthUpdate(byte p_70103_1_) {
      if(p_70103_1_ == 10) {
         this.sheepTimer = 40;
      } else {
         super.handleHealthUpdate(p_70103_1_);
      }

   }
}
