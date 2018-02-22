package com.kentington.thaumichorizons.common.items.lenses;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.items.lenses.ILens;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.research.IScanEventHandler;
import thaumcraft.api.research.ScanResult;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketScannedToServer;
import thaumcraft.common.lib.research.ScanManager;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.EntityUtils;

public class ItemLensOrderEntropy extends Item implements ILens {

   ScanResult startScan = null;
   int count = 250;
   boolean isNew = true;
   IIcon icon;


   public ItemLensOrderEntropy() {
      this.setCreativeTab(ThaumicHorizons.tabTH);
   }

   public String lensName() {
      return "LensOrderEntropy";
   }

   @SideOnly(Side.CLIENT)
   public void handleRender(Minecraft mc, float partialTicks) {
      if(Minecraft.getMinecraft().thePlayer.worldObj.isRemote) {
         double x = 0.0D;
         double y = 0.0D;
         double z = 0.0D;
         EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
         this.isNew = false;
         String text = "?";
         ScanResult scan = this.doScan(new ItemStack(ConfigItems.itemThaumometer), p.worldObj, p, this.count);
         if(scan != null) {
            AspectList aspects = null;
            if(!this.isNew) {
               aspects = ScanManager.getScanAspects(scan, p.worldObj);
            }

            ItemStack stack = null;
            if(scan.id > 0) {
               stack = new ItemStack(Item.getItemById(scan.id), 1, scan.meta);
               if(stack.getItem() != null) {
                  try {
                     text = stack.getDisplayName();
                  } catch (Exception var17) {
                     ;
                  }
               } else if(stack.getItem() != null) {
                  try {
                     text = stack.getItem().getItemStackDisplayName(stack);
                  } catch (Exception var16) {
                     ;
                  }
               }
            }

            if(scan.type == 2) {
               if(!(scan.entity instanceof EntityItem)) {
                  text = scan.entity.getCommandSenderName();
                  x = scan.entity.posX;
                  y = scan.entity.posY;
                  z = scan.entity.posZ;
               } else {
                  text = ((EntityItem)scan.entity).getEntityItem().getDisplayName();
                  x = scan.entity.posX;
                  y = scan.entity.posY;
                  z = scan.entity.posZ;
               }
            } else {
               MovingObjectPosition mop = EntityUtils.getMovingObjectPositionFromPlayer(p.worldObj, p, true);
               if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
                  x = (double)mop.blockX;
                  y = (double)mop.blockY;
                  z = (double)mop.blockZ;
                  TileEntity tile = p.worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
                  if(scan.type == 3 && scan.phenomena.startsWith("NODE") && tile != null && tile instanceof INode) {
                     if(!this.isNew) {
                        aspects = ((INode)tile).getAspects();
                     }

                     if(p.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ) == ConfigBlocks.blockAiry) {
                        text = StatCollector.translateToLocal(p.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getUnlocalizedName() + "." + p.worldObj.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ) + ".name");
                     } else {
                        text = StatCollector.translateToLocal(p.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getLocalizedName());
                     }

                     text = text + " (" + StatCollector.translateToLocal("nodetype." + ((INode)tile).getNodeType() + ".name");
                     if(((INode)tile).getNodeModifier() != null) {
                        text = text + ", " + StatCollector.translateToLocal("nodemod." + ((INode)tile).getNodeModifier() + ".name");
                     }

                     text = text + ")";
                  }
               }
            }

            if(aspects != null || text.length() > 0) {
               this.renderNameAndAspects(aspects, text);
            }
         }

         if(scan != null && scan.equals(this.startScan) && this.isNew) {
            --this.count;
            this.renderNameAndAspects((AspectList)null, text);
            if(this.count <= 5) {
               this.startScan = null;
               if(ScanManager.completeScan(p, scan, "@")) {
                  PacketHandler.INSTANCE.sendToServer(new PacketScannedToServer(scan, p, "@"));
               }

               this.count = 250;
            }

            if(this.count % 20 == 0) {
               p.worldObj.playSound(p.posX, p.posY, p.posZ, "thaumcraft:cameraticks", 0.2F, 0.45F + p.worldObj.rand.nextFloat() * 0.1F, false);
            }
         } else {
            this.startScan = scan;
            this.count = 250;
         }
      }

   }

   private void renderNameAndAspects(AspectList aspects, String text) {
      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      int w = sr.getScaledWidth();
      int h = sr.getScaledHeight();
      if(aspects != null && aspects.size() > 0) {
         int num = 0;
         boolean yOff = false;
         boolean thisRow = false;
         byte size = 18;
         int var15;
         if(aspects.size() - num < 5) {
            var15 = aspects.size() - num;
         } else {
            var15 = 5;
         }

         Aspect[] var10 = aspects.getAspects();
         int var11 = var10.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            Aspect asp = var10[var12];
            int var14 = num / 5 * size;
            this.drawAspectTag(asp, aspects.getAmount(asp), w / 2 - size * var15 / 2 + size * (num % 5), h / 2 + 16 + var14, w);
            ++num;
            if(num % 5 == 0) {
               if(aspects.size() - num < 5) {
                  var15 = aspects.size() - num;
               } else {
                  var15 = 5;
               }
            }
         }
      }

      if(text.length() > 0) {
         Minecraft.getMinecraft().ingameGUI.drawString(Minecraft.getMinecraft().fontRenderer, text, w / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2, h / 2 - 16, 16777215);
      }

   }

   private ScanResult doScan(ItemStack stack, World world, EntityPlayer p, int count) {
      Entity pointedEntity = EntityUtils.getPointedEntity(p.worldObj, p, 0.5D, 10.0D, 0.0F, true);
      if(pointedEntity != null) {
         ScanResult mop1 = new ScanResult((byte)2, 0, 0, pointedEntity, "");
         if(ScanManager.isValidScanTarget(p, mop1, "@")) {
            Thaumcraft.proxy.blockRunes(world, pointedEntity.posX - 0.5D, pointedEntity.posY + (double)(pointedEntity.getEyeHeight() / 2.0F), pointedEntity.posZ - 0.5D, 0.3F + world.rand.nextFloat() * 0.7F, 0.0F, 0.3F + world.rand.nextFloat() * 0.7F, (int)(pointedEntity.height * 15.0F), 0.03F);
            this.isNew = true;
            return mop1;
         } else {
            return mop1;
         }
      } else {
         MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(p.worldObj, p, true);
         if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
            TileEntity tile = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            if(tile instanceof INode) {
               ScanResult seh1 = new ScanResult((byte)3, 0, 0, (Entity)null, "NODE" + ((INode)tile).getId());
               if(ScanManager.isValidScanTarget(p, seh1, "@")) {
                  Thaumcraft.proxy.blockRunes(world, (double)mop.blockX, (double)mop.blockY + 0.25D, (double)mop.blockZ, 0.3F + world.rand.nextFloat() * 0.7F, 0.0F, 0.3F + world.rand.nextFloat() * 0.7F, 15, 0.03F);
                  this.isNew = true;
                  return seh1;
               }

               return seh1;
            }

            Block seh = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
            if(seh != Blocks.air) {
               int scan1 = seh.getDamageValue(world, mop.blockX, mop.blockY, mop.blockZ);
               ItemStack is = seh.getPickBlock(mop, p.worldObj, mop.blockX, mop.blockY, mop.blockZ);
               ScanResult sr = null;

               try {
                  if(is == null) {
                     is = BlockUtils.createStackedBlock(seh, scan1);
                  }
               } catch (Exception var14) {
                  ;
               }

               try {
                  if(is == null) {
                     sr = new ScanResult((byte)1, Block.getIdFromBlock(seh), scan1, (Entity)null, "");
                  } else {
                     sr = new ScanResult((byte)1, Item.getIdFromItem(is.getItem()), is.getItemDamage(), (Entity)null, "");
                  }
               } catch (Exception var13) {
                  ;
               }

               if(ScanManager.isValidScanTarget(p, sr, "@")) {
                  Thaumcraft.proxy.blockRunes(world, (double)mop.blockX, (double)mop.blockY + 0.25D, (double)mop.blockZ, 0.3F + world.rand.nextFloat() * 0.7F, 0.0F, 0.3F + world.rand.nextFloat() * 0.7F, 15, 0.03F);
                  this.isNew = true;
                  return sr;
               }

               return sr;
            }
         }

         Iterator tile1 = ThaumcraftApi.scanEventhandlers.iterator();

         ScanResult scan;
         do {
            if(!tile1.hasNext()) {
               return null;
            }

            IScanEventHandler seh2 = (IScanEventHandler)tile1.next();
            scan = seh2.scanPhenomena(stack, world, p);
         } while(scan == null);

         return scan;
      }
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return "item.LensOrderEntropy";
   }

   public void drawAspectTag(Aspect aspect, int amount, int x, int y, int sw) {
      GL11.glPushMatrix();
      GL11.glAlphaFunc(516, 0.003921569F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      Minecraft mc = Minecraft.getMinecraft();
      Color color = new Color(aspect.getColor());
      mc.renderEngine.bindTexture(aspect.getImage());
      GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.5F);
      Tessellator var9 = Tessellator.instance;
      var9.startDrawingQuads();
      var9.setColorRGBA_F((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.5F);
      var9.addVertexWithUV((double)x + 0.0D, (double)y + 16.0D, 0.0D, 0.0D, 1.0D);
      var9.addVertexWithUV((double)x + 16.0D, (double)y + 16.0D, 0.0D, 1.0D, 1.0D);
      var9.addVertexWithUV((double)x + 16.0D, (double)y + 0.0D, 0.0D, 1.0D, 0.0D);
      var9.addVertexWithUV((double)x + 0.0D, (double)y + 0.0D, 0.0D, 0.0D, 0.0D);
      var9.draw();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      DecimalFormat myFormatter = new DecimalFormat("#######.##");
      String am = myFormatter.format((long)amount);
      mc.fontRenderer.drawString(am, 24 + x * 2, 32 - mc.fontRenderer.FONT_HEIGHT + y * 2, 16777215);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glPopMatrix();
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister ir) {
      this.icon = ir.registerIcon("thaumichorizons:lensorderentropy");
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.icon;
   }

   public void handleRemoval(EntityPlayer p) {}
}
