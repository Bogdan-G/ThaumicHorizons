package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.client.fx.FXSonic;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVortex;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.Thaumcraft;

public class TileVortexStabilizer extends TileThaumcraft implements IWandable {

   public boolean hasTarget;
   public int prevType;
   public int xTarget = Integer.MAX_VALUE;
   public int yTarget = Integer.MAX_VALUE;
   public int zTarget = Integer.MAX_VALUE;
   public TileEntity target = null;
   public int direction;
   boolean fireOnce = false;
   public boolean redstoned;
   public ForgeDirection dir;
   public Object theBeam = null;
   public Entity[] sonicFX = null;


   public void updateEntity() {
      super.updateEntity();
      if(!this.fireOnce) {
         ThaumicHorizons.blockVortexStabilizer.onNeighborBlockChange(super.worldObj, super.xCoord, super.yCoord, super.zCoord, ThaumicHorizons.blockVortexStabilizer);
         this.direction = (byte)this.getBlockMetadata();
         super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
         this.dir = ForgeDirection.getOrientation(this.direction);
         if(this.target == null) {
            this.target = super.worldObj.getTileEntity(this.xTarget, this.yTarget, this.zTarget);
         }

         this.fireOnce = true;
      }

      if(super.worldObj.getWorldTime() % 5L == 0L) {
         MovingObjectPosition i = null;
         if(this.redstoned) {
            i = super.worldObj.rayTraceBlocks(Vec3.createVectorHelper((double)(super.xCoord + this.dir.offsetX) + 0.75D, (double)(super.yCoord + this.dir.offsetY) + 0.75D, (double)(super.zCoord + this.dir.offsetZ) + 0.75D), Vec3.createVectorHelper((double)(super.xCoord + this.dir.offsetX * 10) + 0.5D, (double)(super.yCoord + this.dir.offsetY * 10) + 0.5D, (double)(super.zCoord + this.dir.offsetZ * 10) + 0.5D));
         }

         if(i != null) {
            if(i.blockX != this.xTarget || i.blockY != this.yTarget || i.blockZ != this.zTarget) {
               if(this.hasTarget) {
                  this.reHungrifyTarget();
                  this.hasTarget = false;
               } else if(!this.hasTarget && super.worldObj.getTileEntity(i.blockX, i.blockY, i.blockZ) instanceof INode) {
                  this.hasTarget = true;
                  this.target = super.worldObj.getTileEntity(i.blockX, i.blockY, i.blockZ);
                  this.prevType = ((INode)super.worldObj.getTileEntity(i.blockX, i.blockY, i.blockZ)).getNodeType().ordinal();
                  this.deHungrifyTarget();
               } else if(!this.hasTarget && super.worldObj.getTileEntity(i.blockX, i.blockY, i.blockZ) instanceof TileVortex) {
                  this.hasTarget = true;
                  this.target = super.worldObj.getTileEntity(i.blockX, i.blockY, i.blockZ);
                  this.deHungrifyTarget();
               }

               this.xTarget = i.blockX;
               this.yTarget = i.blockY;
               this.zTarget = i.blockZ;
               this.markDirty();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            }
         } else {
            if(this.hasTarget) {
               this.reHungrifyTarget();
               this.hasTarget = false;
               this.markDirty();
               super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            }

            this.xTarget = super.xCoord + this.dir.offsetX * 10;
            this.yTarget = super.yCoord + this.dir.offsetY * 10;
            this.zTarget = super.zCoord + this.dir.offsetZ * 10;
            this.target = null;
         }
      }

      int var2;
      if(super.worldObj.isRemote && this.redstoned && ThaumicHorizons.proxy.readyToRender() && this.xTarget != Integer.MAX_VALUE && this.yTarget != Integer.MAX_VALUE && this.zTarget != Integer.MAX_VALUE) {
         if(this.sonicFX == null) {
            this.sonicFX = new Entity[3];
         }

         for(var2 = 0; var2 < 3; ++var2) {
            if(this.sonicFX[var2] == null || this.sonicFX[var2].isDead) {
               this.sonicFX[var2] = new FXSonic(Thaumcraft.proxy.getClientWorld(), (double)this.xTarget + 0.5D, (double)this.yTarget + 0.5D, (double)this.zTarget + 0.5D, 10, this.direction);
               ThaumicHorizons.proxy.addEffect(this.sonicFX[var2]);
               break;
            }
         }

         this.theBeam = Thaumcraft.proxy.beamBore(super.worldObj, (double)super.xCoord + 0.5D, (double)super.yCoord + 0.5D, (double)super.zCoord + 0.5D, (double)this.xTarget + 0.5D - (double)this.dir.offsetX, (double)this.yTarget + 0.5D - (double)this.dir.offsetY, (double)this.zTarget + 0.5D - (double)this.dir.offsetZ, 1, '\u80ff', false, 2.0F, this.theBeam, 1);
      } else if(this.sonicFX != null) {
         for(var2 = 0; var2 < 3; ++var2) {
            if(this.sonicFX[var2] != null) {
               this.sonicFX[var2].setDead();
               this.sonicFX[var2] = null;
            }
         }
      }

   }

   public void reHungrifyTarget() {
      if(this.target instanceof INode) {
         ((INode)this.target).setNodeType(NodeType.values()[this.prevType]);
      } else if(this.target instanceof TileVortex) {
         --((TileVortex)this.target).beams;
      }

      if(this.target != null) {
         this.target.markDirty();
         super.worldObj.markBlockForUpdate(this.target.xCoord, this.target.yCoord, this.target.zCoord);
      }

   }

   void deHungrifyTarget() {
      if(this.target instanceof INode) {
         ((INode)this.target).setNodeType(NodeType.NORMAL);
      } else if(this.target instanceof TileVortex) {
         ++((TileVortex)this.target).beams;
      }

      if(this.target != null) {
         this.target.markDirty();
         super.worldObj.markBlockForUpdate(this.target.xCoord, this.target.yCoord, this.target.zCoord);
      }

   }

   public void writeCustomNBT(NBTTagCompound nbttagcompound) {
      super.writeCustomNBT(nbttagcompound);
      nbttagcompound.setInteger("xT", this.xTarget);
      nbttagcompound.setInteger("yT", this.yTarget);
      nbttagcompound.setInteger("zT", this.zTarget);
      nbttagcompound.setInteger("direction", this.direction);
      nbttagcompound.setBoolean("hasTarget", this.hasTarget);
      nbttagcompound.setBoolean("active", this.redstoned);
      nbttagcompound.setInteger("prevType", this.prevType);
   }

   public void readCustomNBT(NBTTagCompound nbttagcompound) {
      super.readCustomNBT(nbttagcompound);
      this.xTarget = nbttagcompound.getInteger("xT");
      this.yTarget = nbttagcompound.getInteger("yT");
      this.zTarget = nbttagcompound.getInteger("zT");
      this.direction = nbttagcompound.getInteger("direction");
      this.hasTarget = nbttagcompound.getBoolean("hasTarget");
      this.redstoned = nbttagcompound.getBoolean("active");
      this.prevType = nbttagcompound.getInteger("prevType");
   }

   @SideOnly(Side.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return TileEntity.INFINITE_EXTENT_AABB;
   }

   public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
      this.dir = ForgeDirection.getOrientation(side);
      this.direction = side;
      world.setBlockMetadataWithNotify(x, y, z, side, 3);
      player.worldObj.playSound((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "thaumcraft:tool", 0.5F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
      player.swingItem();
      this.markDirty();
      return 0;
   }

   public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) {
      return null;
   }

   public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

   public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
}
