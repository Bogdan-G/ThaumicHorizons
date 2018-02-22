package com.kentington.thaumichorizons.common.entities;

import com.kentington.thaumichorizons.common.lib.PacketGetCowData;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.blocks.ItemJarFilled;
import thaumcraft.common.blocks.ItemJarNode;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class EntityWizardCow extends EntityCow implements IEntityAdditionalSpawnData {

   AspectList aspects = new AspectList();
   public AspectList essentia = new AspectList();
   public int nodeMod;
   public int nodeType;
   public boolean hasNode;


   public EntityWizardCow(World p_i1683_1_) {
      super(p_i1683_1_);
      if(p_i1683_1_.isRemote) {
         PacketHandler.INSTANCE.sendToServer(new PacketGetCowData(this.getEntityId()));
      }

   }

   public boolean interact(EntityPlayer p_70085_1_) {
      ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
      if(itemstack != null && this.hasNode && (itemstack.getItem() == ConfigItems.itemJarFilled || itemstack.getItem() == Item.getItemFromBlock(ConfigBlocks.blockJar))) {
         ItemStack var10 = new ItemStack(ConfigItems.itemJarFilled);
         var10.setItemDamage(p_70085_1_.inventory.getCurrentItem().getItemDamage());
         boolean var11 = false;
         Aspect[] var5 = this.essentia.getAspects();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Aspect asp = var5[var7];
            if(this.essentia.getAmount(asp) > 0) {
               if(itemstack.getItem() == Item.getItemFromBlock(ConfigBlocks.blockJar)) {
                  ((ItemJarFilled)var10.getItem()).setAspects(var10, (new AspectList()).add(asp, this.essentia.getAmount(asp)));
                  this.essentia.remove(asp);
                  var11 = true;
               } else {
                  int amount = ((ItemJarFilled)var10.getItem()).getAspects(itemstack).getAmount(asp);
                  if(amount > 0 && amount < 64) {
                     var11 = true;
                     if(amount + this.essentia.getAmount(asp) <= 64) {
                        ((ItemJarFilled)var10.getItem()).setAspects(var10, (new AspectList()).add(asp, amount + this.essentia.getAmount(asp)));
                        this.essentia.remove(asp);
                     } else {
                        this.essentia.remove(asp, 64 - amount);
                        ((ItemJarFilled)var10.getItem()).setAspects(var10, (new AspectList()).add(asp, 64));
                     }
                  }
               }
            }

            if(var11) {
               if(itemstack.stackSize-- == 1) {
                  p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, var10);
               } else if(!p_70085_1_.inventory.addItemStackToInventory(var10)) {
                  p_70085_1_.dropPlayerItemWithRandomChoice(var10, false);
               }

               p_70085_1_.inventory.markDirty();
               return true;
            }
         }

         return false;
      } else if(itemstack != null && !this.hasNode && itemstack.getItem() == ConfigItems.itemJarNode) {
         this.aspects = ((ItemJarNode)itemstack.getItem()).getAspects(itemstack);
         this.hasNode = true;
         NodeModifier mod = ((ItemJarNode)itemstack.getItem()).getNodeModifier(itemstack);
         switch(mod.ordinal()) {
         case 1:
            this.nodeMod = 1;
            break;
         case 2:
            this.nodeMod = -1;
            break;
         case 3:
            this.nodeMod = -2;
         }

         NodeType type = ((ItemJarNode)itemstack.getItem()).getNodeType(itemstack);
         switch(type.ordinal()) {
         case 1:
            this.nodeType = 1;
            break;
         case 2:
            this.nodeType = 2;
            break;
         case 3:
            this.nodeType = 3;
            break;
         case 4:
            this.nodeType = 4;
            break;
         case 5:
            this.nodeType = 5;
            break;
         case 6:
            this.nodeType = 6;
         }

         p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, new ItemStack(ConfigBlocks.blockJar));
         p_70085_1_.inventory.markDirty();
         return true;
      } else {
         return super.interact(p_70085_1_);
      }
   }

   public NodeType getNodeType() {
      switch(this.nodeType) {
      case 2:
         return NodeType.UNSTABLE;
      case 3:
         return NodeType.DARK;
      case 4:
         return NodeType.TAINTED;
      case 5:
         return NodeType.HUNGRY;
      case 6:
         return NodeType.PURE;
      default:
         return NodeType.NORMAL;
      }
   }

   public NodeModifier getNodeMod() {
      switch(this.nodeMod) {
      case -2:
         return NodeModifier.FADING;
      case -1:
         return NodeModifier.PALE;
      case 0:
      default:
         return null;
      case 1:
         return NodeModifier.BRIGHT;
      }
   }

   public void updateAITick() {
      super.updateAITick();
      if(this.hasNode) {
         Aspect[] var1 = this.aspects.getAspects();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Aspect asp = var1[var3];
            int divisor = this.aspects.getAmount(asp) * (3 + this.nodeMod);
            if(this.essentia.getAmount(asp) < 64 && divisor > 0 && super.ticksExisted % (150000 / divisor) == 0) {
               this.essentia.add(asp, 1);
            }
         }
      }

   }

   public AspectList getEssentia() {
      return this.essentia;
   }

   public AspectList getAspects() {
      return this.aspects;
   }

   public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
      super.writeEntityToNBT(p_70014_1_);
      p_70014_1_.setBoolean("hasNode", this.hasNode);
      p_70014_1_.setInteger("nodeMod", this.nodeMod);
      p_70014_1_.setInteger("nodeType", this.nodeType);
      NBTTagCompound aspectTag = new NBTTagCompound();
      this.aspects.writeToNBT(aspectTag);
      p_70014_1_.setTag("aspects", aspectTag);
      NBTTagCompound essentiaTag = new NBTTagCompound();
      this.essentia.writeToNBT(essentiaTag);
      p_70014_1_.setTag("essentia", essentiaTag);
   }

   public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
      super.readEntityFromNBT(p_70037_1_);
      this.hasNode = p_70037_1_.getBoolean("hasNode");
      this.nodeMod = p_70037_1_.getInteger("nodeMod");
      this.nodeType = p_70037_1_.getInteger("nodeType");
      this.aspects.readFromNBT(p_70037_1_.getCompoundTag("aspects"));
      this.essentia.readFromNBT(p_70037_1_.getCompoundTag("essentia"));
   }

   public void writeSpawnData(ByteBuf buffer) {
      if(this.aspects.size() > 0 && this.aspects.getAspects()[0] != null) {
         buffer.writeBoolean(true);
         buffer.writeInt(this.aspects.size());
         Aspect[] var2 = this.aspects.getAspects();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Aspect asp = var2[var4];
            String tag = asp.getTag();
            buffer.writeInt(tag.length());
            buffer.writeBytes(tag.getBytes());
            buffer.writeInt(this.aspects.getAmount(asp));
         }
      } else {
         buffer.writeBoolean(false);
      }

   }

   public void readSpawnData(ByteBuf buffer) {
      if(buffer.readBoolean()) {
         int numAspects = buffer.readInt();

         for(int i = 0; i < numAspects; ++i) {
            int length = buffer.readInt();
            byte[] bytes = new byte[length];
            char[] chars = new char[length];
            buffer.readBytes(bytes);

            for(byte tag = 0; i < bytes.length; ++i) {
               chars[tag] = (char)bytes[tag];
            }

            String var8 = String.copyValueOf(chars);
            this.aspects.add(Aspect.getAspect(var8), buffer.readInt());
         }
      }

   }
}
