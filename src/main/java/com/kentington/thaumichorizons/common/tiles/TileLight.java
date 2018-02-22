package com.kentington.thaumichorizons.common.tiles;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.ItemFocusIllumination;
import java.awt.Color;
import net.minecraft.tileentity.TileEntity;

public class TileLight extends TileEntity {

   int md = -1;
   Color col = null;


   public boolean canUpdate() {
      return true;
   }

   public void updateEntity() {
      super.updateEntity();
      if(super.worldObj.isRemote) {
         if(this.md == -1) {
            this.md = super.worldObj.getBlockMetadata(super.xCoord, super.yCoord, super.zCoord);
            this.col = new Color(ItemFocusIllumination.colors[this.md]);
         }

         ThaumicHorizons.proxy.illuminationFX(super.worldObj, super.xCoord, super.yCoord, super.zCoord, this.md, this.col);
      }

   }
}
