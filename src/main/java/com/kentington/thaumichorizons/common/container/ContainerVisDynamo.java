package com.kentington.thaumichorizons.common.container;

import com.kentington.thaumichorizons.common.tiles.TileVisDynamo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerVisDynamo extends Container {

   private EntityPlayer player;
   private TileVisDynamo tile;


   public ContainerVisDynamo(EntityPlayer p, TileVisDynamo t) {
      this.player = p;
      this.tile = t;
   }

   public boolean canInteractWith(EntityPlayer p_75145_1_) {
      return this.tile.isUseableByPlayer(p_75145_1_);
   }

   public boolean enchantItem(EntityPlayer par1EntityPlayer, int button) {
      switch(button) {
      case 1:
         this.tile.provideAer = !this.tile.provideAer;
         this.tile.markDirty();
         return true;
      case 2:
         this.tile.provideTerra = !this.tile.provideTerra;
         this.tile.markDirty();
         return true;
      case 3:
         this.tile.provideIgnis = !this.tile.provideIgnis;
         this.tile.markDirty();
         return true;
      case 4:
         this.tile.provideAqua = !this.tile.provideAqua;
         this.tile.markDirty();
         return true;
      case 5:
         this.tile.provideOrdo = !this.tile.provideOrdo;
         this.tile.markDirty();
         return true;
      case 6:
         this.tile.providePerditio = !this.tile.providePerditio;
         this.tile.markDirty();
         return true;
      default:
         return false;
      }
   }
}
