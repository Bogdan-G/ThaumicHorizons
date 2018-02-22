package com.kentington.thaumichorizons.common.entities;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;
import thaumcraft.common.entities.monster.EntityGiantBrainyZombie;
import thaumcraft.common.entities.monster.EntityInhabitedZombie;

public class EntityGravekeeper extends EntityOcelot {

   public EntityGravekeeper(World p_i1688_1_) {
      super(p_i1688_1_);
      super.tasks.removeTask(((EntityAITaskEntry)super.tasks.taskEntries.get(6)).action);
      super.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, 0, false));
      super.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, 0, false));
      super.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityWither.class, 0, false));
      super.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityPigZombie.class, 0, false));
      super.targetTasks.addTask(6, new EntityAINearestAttackableTarget(this, EntityGiantZombie.class, 0, false));
      super.targetTasks.addTask(7, new EntityAINearestAttackableTarget(this, EntityBrainyZombie.class, 0, false));
      super.targetTasks.addTask(8, new EntityAINearestAttackableTarget(this, EntityEldritchGuardian.class, 0, false));
      super.targetTasks.addTask(9, new EntityAINearestAttackableTarget(this, EntityGiantBrainyZombie.class, 0, false));
      super.targetTasks.addTask(10, new EntityAINearestAttackableTarget(this, EntityInhabitedZombie.class, 0, false));
   }

   public boolean attackEntityAsMob(Entity p_70652_1_) {
      return p_70652_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_70652_1_).isEntityUndead()?true:p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
   }

   public String getCommandSenderName() {
      return this.hasCustomNameTag()?this.getCustomNameTag():(this.isTamed()?StatCollector.translateToLocal("entity.ThaumicHorizons.Gravekeeper.name"):super.getCommandSenderName());
   }

   public void updateAITick() {
      super.updateAITick();
      List critters = super.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(super.posX - 5.0D, super.posY - 5.0D, super.posZ - 5.0D, super.posX + 5.0D, super.posY + 5.0D, super.posZ + 5.0D));
      Iterator var2 = critters.iterator();

      while(var2.hasNext()) {
         EntityLivingBase ent = (EntityLivingBase)var2.next();
         if(ent.isEntityUndead()) {
            ent.setFire(1);
            Thaumcraft.proxy.beam(super.worldObj, super.posX, super.posY + (double)(super.height / 2.0F), super.posZ, ent.posX, ent.posY + (double)(ent.height / 2.0F), ent.posZ, 0, 16773444, false, 2.5F, 1);
         }
      }

   }

   protected void entityInit() {
      super.entityInit();
      byte b0 = super.dataWatcher.getWatchableObjectByte(16);
      super.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 4)));
   }

   public boolean isTamed() {
      return true;
   }
}
