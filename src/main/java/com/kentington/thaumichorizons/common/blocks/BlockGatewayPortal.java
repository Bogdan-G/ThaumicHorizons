package com.kentington.thaumichorizons.common.blocks;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileSlot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;

public class BlockGatewayPortal extends Block {

   public IIcon CornerTR;
   public IIcon CornerTL;
   public IIcon CornerBR;
   public IIcon CornerBL;
   public IIcon topR;
   public IIcon topL;
   public IIcon R;
   public IIcon L;
   public IIcon B;
   public IIcon lapiz;
   public IIcon stone;


   public BlockGatewayPortal() {
      super(Material.rock);
      this.setHardness(2.5F);
      this.setResistance(2.5F);
      this.setBlockName("ThaumicHorizons_gateway");
   }

   public void breakBlock(World world, int x, int y, int z, Block block, int md) {
      int slotX = 0;
      boolean slotY = false;
      int slotZ = 0;
      int slotY1;
      if(md < 5) {
         slotY1 = y + md;
         if(world.getBlock(x + 1, y, z) != ThaumicHorizons.blockPortal && world.getBlock(x + 1, y, z) != ThaumicHorizons.blockGateway) {
            slotX = x;
            slotZ = z + 2;
         } else {
            slotX = x + 2;
            slotZ = z;
         }
      } else if(md == 5) {
         slotY1 = y;
         if(world.getBlock(x + 1, y, z) == ThaumicHorizons.blockSlot) {
            slotX = x + 1;
            slotZ = z;
         } else {
            slotX = x;
            slotZ = z + 1;
         }
      } else if(md == 8) {
         slotY1 = y;
         if(world.getBlock(x - 1, y, z) == ThaumicHorizons.blockSlot) {
            slotX = x - 1;
            slotZ = z;
         } else {
            slotX = x;
            slotZ = z - 1;
         }
      } else if(md != 6 && md != 7 && md != 9) {
         slotY1 = y + md - 10;
         if(world.getBlock(x - 1, y, z) != ThaumicHorizons.blockPortal && world.getBlock(x - 1, y, z) != ThaumicHorizons.blockGateway) {
            slotX = x;
            slotZ = z - 2;
         } else {
            slotX = x - 2;
            slotZ = z;
         }
      } else {
         slotY1 = y + 4;
         if(world.getBlock(x + 1, y, z) == ThaumicHorizons.blockGateway) {
            slotZ = z;
            switch(md) {
            case 6:
               slotX = x + 1;
               break;
            case 7:
               slotX = x;
            case 8:
            default:
               break;
            case 9:
               slotX = x - 1;
            }
         } else {
            slotX = x;
            switch(md) {
            case 6:
               slotZ = z + 1;
               break;
            case 7:
               slotZ = z;
            case 8:
            default:
               break;
            case 9:
               slotZ = z - 1;
            }
         }
      }

      TileEntity te = world.getTileEntity(slotX, slotY1, slotZ);
      if(te instanceof TileSlot) {
         ((TileSlot)te).destroyPortal();
      }

   }

   @SideOnly(Side.CLIENT)
   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
      return Item.getItemById(0);
   }

   public int quantityDropped(Random p_149745_1_) {
      return 0;
   }

   public void registerBlockIcons(IIconRegister ir) {
      this.CornerTR = ir.registerIcon("thaumichorizons:gateway_topright");
      this.CornerTL = ir.registerIcon("thaumichorizons:gateway_topleft");
      this.CornerBR = ir.registerIcon("thaumichorizons:gateway_bottomright");
      this.CornerBL = ir.registerIcon("thaumichorizons:gateway_bottomleft");
      this.topR = ir.registerIcon("thaumichorizons:gateway_top2");
      this.topL = ir.registerIcon("thaumichorizons:gateway_top");
      this.R = ir.registerIcon("thaumichorizons:gateway_right");
      this.L = ir.registerIcon("thaumichorizons:gateway_left");
      this.B = ir.registerIcon("thaumichorizons:gateway_bottom");
      this.lapiz = Blocks.lapis_block.getIcon(0, 0);
      this.stone = ConfigBlocks.blockCosmeticSolid.getIcon(0, 11);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
      boolean isXAligned = world.getBlock(x + 1, y, z) == ThaumicHorizons.blockGateway || world.getBlock(x + 1, y, z) == ThaumicHorizons.blockPortal || world.getBlock(x - 1, y, z) == ThaumicHorizons.blockGateway || world.getBlock(x - 1, y, z) == ThaumicHorizons.blockPortal;
      switch(world.getBlockMetadata(x, y, z)) {
      case 0:
         switch(side) {
         case 0:
            return this.lapiz;
         case 1:
            return this.stone;
         case 2:
            if(isXAligned) {
               return this.CornerTR;
            }

            return this.stone;
         case 3:
            if(isXAligned) {
               return this.CornerTL;
            }

            return this.stone;
         case 4:
            if(isXAligned) {
               return this.stone;
            }

            return this.CornerTL;
         case 5:
            if(isXAligned) {
               return this.stone;
            }

            return this.CornerTR;
         }
      case 1:
         return this.leftSide(isXAligned, side);
      case 2:
         return this.leftSide(isXAligned, side);
      case 3:
         return this.leftSide(isXAligned, side);
      case 4:
         switch(side) {
         case 0:
            return this.stone;
         case 1:
            return this.lapiz;
         case 2:
            if(isXAligned) {
               return this.CornerBR;
            }

            return this.stone;
         case 3:
            if(isXAligned) {
               return this.CornerBL;
            }

            return this.stone;
         case 4:
            if(isXAligned) {
               return this.stone;
            }

            return this.CornerBL;
         case 5:
            if(isXAligned) {
               return this.stone;
            }

            return this.CornerBR;
         }
      case 5:
         switch(side) {
         case 0:
            return this.lapiz;
         case 1:
            return this.stone;
         case 2:
            if(isXAligned) {
               return this.topR;
            }
         case 3:
            if(isXAligned) {
               return this.topL;
            }
         case 4:
            if(!isXAligned) {
               return this.topL;
            }
         case 5:
            if(!isXAligned) {
               return this.topR;
            }
         default:
            return this.lapiz;
         }
      case 6:
         return this.bottomSide(isXAligned, side);
      case 7:
         return this.bottomSide(isXAligned, side);
      case 8:
         switch(side) {
         case 0:
            return this.lapiz;
         case 1:
            return this.stone;
         case 2:
            if(isXAligned) {
               return this.topL;
            }
         case 3:
            if(isXAligned) {
               return this.topR;
            }
         case 4:
            if(!isXAligned) {
               return this.topR;
            }
         case 5:
            if(!isXAligned) {
               return this.topL;
            }
         default:
            return this.lapiz;
         }
      case 9:
         return this.bottomSide(isXAligned, side);
      case 10:
         switch(side) {
         case 0:
            return this.stone;
         case 1:
            return this.lapiz;
         case 2:
            if(isXAligned) {
               return this.CornerTL;
            }

            return this.stone;
         case 3:
            if(isXAligned) {
               return this.CornerTR;
            }

            return this.stone;
         case 4:
            if(isXAligned) {
               return this.stone;
            }

            return this.CornerTR;
         case 5:
            if(isXAligned) {
               return this.stone;
            }

            return this.CornerTL;
         }
      case 11:
         return this.rightSide(isXAligned, side);
      case 12:
         return this.rightSide(isXAligned, side);
      case 13:
         return this.rightSide(isXAligned, side);
      case 14:
         switch(side) {
         case 0:
            return this.stone;
         case 1:
            return this.lapiz;
         case 2:
            if(isXAligned) {
               return this.CornerBL;
            }

            return this.stone;
         case 3:
            if(isXAligned) {
               return this.CornerBR;
            }

            return this.stone;
         case 4:
            if(isXAligned) {
               return this.stone;
            }

            return this.CornerBR;
         case 5:
            if(isXAligned) {
               return this.stone;
            }

            return this.CornerBL;
         }
      default:
         return this.lapiz;
      }
   }

   IIcon leftSide(boolean xAligned, int side) {
      if(xAligned) {
         switch(side) {
         case 2:
            return this.R;
         case 3:
            return this.L;
         case 4:
            return this.stone;
         default:
            return this.lapiz;
         }
      } else {
         switch(side) {
         case 2:
            return this.stone;
         case 3:
         default:
            return this.lapiz;
         case 4:
            return this.L;
         case 5:
            return this.R;
         }
      }
   }

   IIcon rightSide(boolean xAligned, int side) {
      if(xAligned) {
         switch(side) {
         case 2:
            return this.L;
         case 3:
            return this.R;
         case 4:
         default:
            return this.lapiz;
         case 5:
            return this.stone;
         }
      } else {
         switch(side) {
         case 3:
            return this.stone;
         case 4:
            return this.R;
         case 5:
            return this.L;
         default:
            return this.lapiz;
         }
      }
   }

   IIcon bottomSide(boolean xAligned, int side) {
      switch(side) {
      case 0:
         return this.stone;
      case 1:
         return this.lapiz;
      default:
         return this.B;
      }
   }
}
