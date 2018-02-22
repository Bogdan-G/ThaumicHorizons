package com.kentington.thaumichorizons.client.lib;

import baubles.api.BaublesApi;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityBoatThaumium;
import com.kentington.thaumichorizons.common.items.lenses.ILens;
import com.kentington.thaumichorizons.common.items.lenses.ItemLensCase;
import com.kentington.thaumichorizons.common.items.lenses.LensManager;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import com.kentington.thaumichorizons.common.lib.PacketLensChangeToServer;
import com.kentington.thaumichorizons.common.lib.THKeyHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.nodes.IRevealer;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;

public class RenderEventHandler {

   static float radialHudScale = 0.0F;
   TreeMap foci = new TreeMap();
   HashMap fociItem = new HashMap();
   HashMap fociHover = new HashMap();
   HashMap fociScale = new HashMap();
   long lastTime = 0L;
   boolean lastState = false;
   float breakProgress = 0.0F;
   public int cacheX = Integer.MAX_VALUE;
   public int cacheY = Integer.MAX_VALUE;
   public int cacheZ = Integer.MAX_VALUE;
   int evanescentStage = 0;
   Block[][] tempBlock = new Block[3][3];
   int[][] tempMD = new int[3][3];
   public ForgeDirection tempDir;
   public ArrayList thingsThatSparkle = new ArrayList();


   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public void renderOverlay(RenderGameOverlayEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      long time = System.nanoTime() / 1000000L;
      if(event.type == ElementType.TEXT) {
         this.handleFociRadial(mc, time, event);
         ItemStack goggles = mc.thePlayer.inventory.armorItemInSlot(3);
         if(LensManager.nightVisionOffTime > 0L && (goggles == null || !(goggles.getItem() instanceof IRevealer) || goggles.stackTagCompound == null) && mc.thePlayer.getActivePotionEffect(Potion.nightVision) != null && mc.thePlayer.getActivePotionEffect(Potion.nightVision).getIsAmbient()) {
            mc.thePlayer.removePotionEffect(Potion.nightVision.id);
            LensManager.nightVisionOffTime = 0L;
         }

         if(goggles != null && goggles.getItem() instanceof IRevealer && goggles.stackTagCompound != null && goggles.stackTagCompound.getString("Lens") != null && !goggles.stackTagCompound.getString("Lens").equals("")) {
            ILens theLens = (ILens)LensManager.getLens(goggles.stackTagCompound.getString("Lens"));
            if(theLens != null) {
               theLens.handleRender(mc, event.partialTicks);
            }
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void handleFociRadial(Minecraft mc, long time, RenderGameOverlayEvent event) {
      if(THKeyHandler.radialActive || radialHudScale > 0.0F) {
         long timeDiff = System.currentTimeMillis() - THKeyHandler.lastPressV;
         if(THKeyHandler.radialActive) {
            if(mc.currentScreen != null) {
               THKeyHandler.radialActive = false;
               THKeyHandler.radialLock = true;
               mc.setIngameFocus();
               mc.setIngameNotInFocus();
               return;
            }

            if(radialHudScale == 0.0F) {
               this.foci.clear();
               this.fociItem.clear();
               this.fociHover.clear();
               this.fociScale.clear();
               int pouchcount = 0;
               ItemStack key = null;
               IInventory baubles = BaublesApi.getBaubles(mc.thePlayer);

               int a;
               ItemStack[] inv;
               int q;
               for(a = 0; a < 4; ++a) {
                  if(baubles.getStackInSlot(a) != null && baubles.getStackInSlot(a).getItem() instanceof ItemLensCase) {
                     ++pouchcount;
                     key = baubles.getStackInSlot(a);
                     inv = ((ItemLensCase)key.getItem()).getInventory(key);

                     for(q = 0; q < inv.length; ++q) {
                        key = inv[q];
                        if(key != null && key.getItem() instanceof ILens) {
                           this.foci.put(((ILens)key.getItem()).lensName(), Integer.valueOf(q + pouchcount * 1000));
                           this.fociItem.put(((ILens)key.getItem()).lensName(), key.copy());
                           this.fociScale.put(((ILens)key.getItem()).lensName(), Float.valueOf(1.0F));
                           this.fociHover.put(((ILens)key.getItem()).lensName(), Boolean.valueOf(false));
                        }
                     }
                  }
               }

               for(a = 0; a < 36; ++a) {
                  key = mc.thePlayer.inventory.mainInventory[a];
                  if(key != null && key.getItem() instanceof ILens) {
                     this.foci.put(((ILens)key.getItem()).lensName(), Integer.valueOf(a));
                     this.fociItem.put(((ILens)key.getItem()).lensName(), key.copy());
                     this.fociScale.put(((ILens)key.getItem()).lensName(), Float.valueOf(1.0F));
                     this.fociHover.put(((ILens)key.getItem()).lensName(), Boolean.valueOf(false));
                  }

                  if(key != null && key.getItem() instanceof ItemLensCase) {
                     ++pouchcount;
                     inv = ((ItemLensCase)key.getItem()).getInventory(key);

                     for(q = 0; q < inv.length; ++q) {
                        key = inv[q];
                        if(key != null && key.getItem() instanceof ILens) {
                           this.foci.put(((ILens)key.getItem()).lensName(), Integer.valueOf(q + pouchcount * 1000));
                           this.fociItem.put(((ILens)key.getItem()).lensName(), key.copy());
                           this.fociScale.put(((ILens)key.getItem()).lensName(), Float.valueOf(1.0F));
                           this.fociHover.put(((ILens)key.getItem()).lensName(), Boolean.valueOf(false));
                        }
                     }
                  }
               }

               if(this.foci.size() > 0 && mc.inGameHasFocus) {
                  mc.inGameHasFocus = false;
                  mc.mouseHelper.ungrabMouseCursor();
               }
            }
         } else if(mc.currentScreen == null && this.lastState) {
            if(Display.isActive() && !mc.inGameHasFocus) {
               mc.inGameHasFocus = true;
               mc.mouseHelper.grabMouseCursor();
            }

            this.lastState = false;
         }

         this.renderFocusRadialHUD(event.resolution.getScaledWidth_double(), event.resolution.getScaledHeight_double(), time, event.partialTicks);
         if(time > this.lastTime) {
            Iterator var13 = this.fociHover.keySet().iterator();

            while(var13.hasNext()) {
               String var14 = (String)var13.next();
               if(((Boolean)this.fociHover.get(var14)).booleanValue()) {
                  if(!THKeyHandler.radialActive && !THKeyHandler.radialLock) {
                     PacketHandler.INSTANCE.sendToServer(new PacketLensChangeToServer(mc.thePlayer, var14));
                     THKeyHandler.radialLock = true;
                  }

                  if(((Float)this.fociScale.get(var14)).floatValue() < 1.3F) {
                     this.fociScale.put(var14, Float.valueOf(((Float)this.fociScale.get(var14)).floatValue() + 0.025F));
                  }
               } else if(((Float)this.fociScale.get(var14)).floatValue() > 1.0F) {
                  this.fociScale.put(var14, Float.valueOf(((Float)this.fociScale.get(var14)).floatValue() - 0.025F));
               }
            }

            if(!THKeyHandler.radialActive) {
               radialHudScale -= 0.05F;
            } else if(THKeyHandler.radialActive && radialHudScale < 1.0F) {
               radialHudScale += 0.05F;
            }

            if(radialHudScale > 1.0F) {
               radialHudScale = 1.0F;
            }

            if(radialHudScale < 0.0F) {
               radialHudScale = 0.0F;
               THKeyHandler.radialLock = false;
            }

            this.lastTime = time + 5L;
            this.lastState = THKeyHandler.radialActive;
         }
      }

   }

   @SideOnly(Side.CLIENT)
   private void renderFocusRadialHUD(double sw, double sh, long time, float partialTicks) {
      RenderItem ri = new RenderItem();
      Minecraft mc = Minecraft.getMinecraft();
      if(mc.thePlayer.inventory.armorItemInSlot(3) != null && mc.thePlayer.inventory.armorItemInSlot(3).getItem() instanceof IRevealer) {
         ItemStack goggles = mc.thePlayer.inventory.armorItemInSlot(3);
         ILens lens = null;
         if(goggles.stackTagCompound != null) {
            lens = (ILens)LensManager.getLens(goggles.stackTagCompound.getString("Lens"));
         }

         int i = (int)((double)Mouse.getEventX() * sw / (double)mc.displayWidth);
         int j = (int)(sh - (double)Mouse.getEventY() * sh / (double)mc.displayHeight - 1.0D);
         int k = Mouse.getEventButton();
         if(this.fociItem.size() != 0) {
            GL11.glPushMatrix();
            GL11.glClear(256);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0D, sw, sh, 0.0D, 1000.0D, 3000.0D);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            GL11.glTranslated(sw / 2.0D, sh / 2.0D, 0.0D);
            ItemStack tt = null;
            float width = 16.0F + (float)this.fociItem.size() * 2.5F;
            UtilsFX.bindTexture("textures/misc/radial.png");
            GL11.glPushMatrix();
            GL11.glRotatef(partialTicks + (float)(mc.thePlayer.ticksExisted % 720) / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glAlphaFunc(516, 0.003921569F);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            UtilsFX.renderQuadCenteredFromTexture(width * 2.75F * radialHudScale, 0.5F, 0.5F, 0.5F, 200, 771, 0.5F);
            GL11.glDisable(3042);
            GL11.glAlphaFunc(516, 0.1F);
            GL11.glPopMatrix();
            UtilsFX.bindTexture("textures/misc/radial2.png");
            GL11.glPushMatrix();
            GL11.glRotatef(-(partialTicks + (float)(mc.thePlayer.ticksExisted % 720) / 2.0F), 0.0F, 0.0F, 1.0F);
            GL11.glAlphaFunc(516, 0.003921569F);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            UtilsFX.renderQuadCenteredFromTexture(width * 2.55F * radialHudScale, 0.5F, 0.5F, 0.5F, 200, 771, 0.5F);
            GL11.glDisable(3042);
            GL11.glAlphaFunc(516, 0.1F);
            GL11.glPopMatrix();
            if(lens != null) {
               GL11.glPushMatrix();
               GL11.glEnable('\u803a');
               RenderHelper.enableGUIStandardItemLighting();
               ItemStack currentRot = new ItemStack((Item)lens);
               currentRot.stackTagCompound = null;
               ri.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, currentRot, -8, -8);
               RenderHelper.disableStandardItemLighting();
               GL11.glDisable('\u803a');
               GL11.glPopMatrix();
               int pieSlice = (int)((double)i - sw / 2.0D);
               int key = (int)((double)j - sh / 2.0D);
               if(pieSlice >= -10 && pieSlice <= 10 && key >= -10 && key <= 10) {
                  tt = new ItemStack((Item)lens);
               }
            }

            GL11.glScaled((double)radialHudScale, (double)radialHudScale, (double)radialHudScale);
            float var28 = -90.0F * radialHudScale;
            float var30 = 360.0F / (float)this.fociItem.size();
            String var29 = (String)this.foci.firstKey();

            for(int a = 0; a < this.fociItem.size(); ++a) {
               double xx = (double)(MathHelper.cos(var28 / 180.0F * 3.141593F) * width);
               double yy = (double)(MathHelper.sin(var28 / 180.0F * 3.141593F) * width);
               var28 += var30;
               GL11.glPushMatrix();
               GL11.glTranslated(xx, yy, 100.0D);
               GL11.glScalef(((Float)this.fociScale.get(var29)).floatValue(), ((Float)this.fociScale.get(var29)).floatValue(), ((Float)this.fociScale.get(var29)).floatValue());
               GL11.glEnable('\u803a');
               RenderHelper.enableGUIStandardItemLighting();
               ItemStack item = ((ItemStack)this.fociItem.get(var29)).copy();
               item.stackTagCompound = null;
               ri.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, item, -8, -8);
               RenderHelper.disableStandardItemLighting();
               GL11.glDisable('\u803a');
               GL11.glPopMatrix();
               if(!THKeyHandler.radialLock && THKeyHandler.radialActive) {
                  int mx = (int)((double)i - sw / 2.0D - xx);
                  int my = (int)((double)j - sh / 2.0D - yy);
                  if(mx >= -10 && mx <= 10 && my >= -10 && my <= 10) {
                     this.fociHover.put(var29, Boolean.valueOf(true));
                     tt = (ItemStack)this.fociItem.get(var29);
                     if(k == 0) {
                        THKeyHandler.radialActive = false;
                        THKeyHandler.radialLock = true;
                        PacketHandler.INSTANCE.sendToServer(new PacketLensChangeToServer(mc.thePlayer, var29));
                        break;
                     }
                  } else {
                     this.fociHover.put(var29, Boolean.valueOf(false));
                  }
               }

               var29 = (String)this.foci.higherKey(var29);
            }

            GL11.glPopMatrix();
            if(tt != null) {
               UtilsFX.drawCustomTooltip(mc.currentScreen, ri, mc.fontRenderer, tt.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips), -4, 20, 11);
            }

            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public void blockHighlight(DrawBlockHighlightEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      ItemStack goggles = mc.thePlayer.inventory.armorItemInSlot(3);
      if(goggles != null && goggles.getItem() instanceof IRevealer && goggles.stackTagCompound != null) {
         if(goggles.stackTagCompound.getString("Lens") != null && !goggles.stackTagCompound.getString("Lens").equals("")) {
            ILens theLens = (ILens)LensManager.getLens(goggles.stackTagCompound.getString("Lens"));
            if(theLens == ThaumicHorizons.itemLensEarth) {
               if(this.cacheX != event.target.blockX || this.cacheY != event.target.blockY || this.cacheZ != event.target.blockZ || this.tempDir != ForgeDirection.getOrientation(event.target.sideHit)) {
                  this.resetBlocks(mc.thePlayer);
               }

               if(event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ).getMaterial() != Material.air) {
                  this.cacheX = event.target.blockX;
                  this.cacheY = event.target.blockY;
                  this.cacheZ = event.target.blockZ;
                  this.tempDir = ForgeDirection.getOrientation(event.target.sideHit);
               } else {
                  this.resetBlocks(mc.thePlayer);
               }
            } else {
               this.resetBlocks(mc.thePlayer);
            }
         } else {
            this.resetBlocks(mc.thePlayer);
         }
      } else if(this.evanescentStage != 0) {
         this.resetBlocks(mc.thePlayer);
      }

   }

   void setBlocksEvanescent(EntityPlayer p) {
      if(p.isSwingInProgress) {
         this.breakProgress += p.worldObj.getBlock(this.cacheX, this.cacheY, this.cacheZ).getPlayerRelativeBlockHardness(p, p.worldObj, this.cacheX, this.cacheY, this.cacheZ);
         if(this.breakProgress > 1.0F) {
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, p.getEntityId(), this.cacheX, this.cacheY, this.cacheZ));
            Minecraft.getMinecraft().playerController.onPlayerDestroyBlock(p.getEntityId(), this.cacheX, this.cacheY, this.cacheZ);
            this.breakProgress = 0.0F;
            return;
         }
      }

      for(int i = -1; i < 2; ++i) {
         for(int j = -1; j < 2; ++j) {
            if(this.tempDir != ForgeDirection.UP && this.tempDir != ForgeDirection.DOWN) {
               if(this.tempDir != ForgeDirection.NORTH && this.tempDir != ForgeDirection.SOUTH) {
                  if(p.worldObj.getTileEntity(this.cacheX, this.cacheY + j, this.cacheZ + i) == null && p.worldObj.getBlock(this.cacheX, this.cacheY + j, this.cacheZ + i).getLightValue() == 0 && p.worldObj.getBlock(this.cacheX, this.cacheY + j, this.cacheZ + i).canCollideCheck(p.worldObj.getBlockMetadata(this.cacheX, this.cacheY + j, this.cacheZ + i), false)) {
                     if(p.worldObj.getBlock(this.cacheX, this.cacheY + j, this.cacheZ + i) != ThaumicHorizons.blockEvanescent) {
                        this.tempBlock[i + 1][j + 1] = p.worldObj.getBlock(this.cacheX, this.cacheY + j, this.cacheZ + i);
                        this.tempMD[i + 1][j + 1] = p.worldObj.getBlockMetadata(this.cacheX, this.cacheY + j, this.cacheZ + i);
                     }

                     if(this.tempBlock[i + 1][j + 1].getMaterial() != Material.air) {
                        p.worldObj.setBlock(this.cacheX, this.cacheY + j, this.cacheZ + i, ThaumicHorizons.blockEvanescent);
                     }
                  } else if(p.worldObj.getBlock(this.cacheX, this.cacheY + j, this.cacheZ + i) != ThaumicHorizons.blockEvanescent) {
                     this.tempBlock[i + 1][j + 1] = null;
                  }
               } else if(p.worldObj.getTileEntity(this.cacheX + i, this.cacheY + j, this.cacheZ) == null && p.worldObj.getBlock(this.cacheX + i, this.cacheY + j, this.cacheZ).getLightValue() == 0 && p.worldObj.getBlock(this.cacheX + i, this.cacheY + j, this.cacheZ).canCollideCheck(p.worldObj.getBlockMetadata(this.cacheX + i, this.cacheY + j, this.cacheZ), false)) {
                  if(p.worldObj.getBlock(this.cacheX + i, this.cacheY + j, this.cacheZ) != ThaumicHorizons.blockEvanescent) {
                     this.tempBlock[i + 1][j + 1] = p.worldObj.getBlock(this.cacheX + i, this.cacheY + j, this.cacheZ);
                     this.tempMD[i + 1][j + 1] = p.worldObj.getBlockMetadata(this.cacheX + i, this.cacheY + j, this.cacheZ);
                  }

                  if(this.tempBlock[i + 1][j + 1].getMaterial() != Material.air) {
                     p.worldObj.setBlock(this.cacheX + i, this.cacheY + j, this.cacheZ, ThaumicHorizons.blockEvanescent);
                  }
               } else if(p.worldObj.getBlock(this.cacheX + i, this.cacheY + j, this.cacheZ) != ThaumicHorizons.blockEvanescent) {
                  this.tempBlock[i + 1][j + 1] = null;
               }
            } else if(p.worldObj.getTileEntity(this.cacheX + i, this.cacheY, this.cacheZ + j) == null && p.worldObj.getBlock(this.cacheX + i, this.cacheY, this.cacheZ + j).getLightValue() == 0 && p.worldObj.getBlock(this.cacheX + i, this.cacheY, this.cacheZ + j).canCollideCheck(p.worldObj.getBlockMetadata(this.cacheX + i, this.cacheY, this.cacheZ + j), false)) {
               if(p.worldObj.getBlock(this.cacheX + i, this.cacheY, this.cacheZ + j) != ThaumicHorizons.blockEvanescent) {
                  this.tempBlock[i + 1][j + 1] = p.worldObj.getBlock(this.cacheX + i, this.cacheY, this.cacheZ + j);
                  this.tempMD[i + 1][j + 1] = p.worldObj.getBlockMetadata(this.cacheX + i, this.cacheY, this.cacheZ + j);
               }

               if(this.tempBlock[i + 1][j + 1].getMaterial() != Material.air) {
                  p.worldObj.setBlock(this.cacheX + i, this.cacheY, this.cacheZ + j, ThaumicHorizons.blockEvanescent);
               }
            } else if(p.worldObj.getBlock(this.cacheX + i, this.cacheY, this.cacheZ + j) != ThaumicHorizons.blockEvanescent) {
               this.tempBlock[i + 1][j + 1] = null;
            }
         }
      }

      this.evanescentStage = 2;
   }

   public void resetBlocks(EntityPlayer p) {
      this.breakProgress = 0.0F;

      for(int i = -1; i < 2; ++i) {
         for(int j = -1; j < 2; ++j) {
            if(this.tempDir != ForgeDirection.UP && this.tempDir != ForgeDirection.DOWN) {
               if(this.tempDir != ForgeDirection.NORTH && this.tempDir != ForgeDirection.SOUTH) {
                  if(this.tempBlock[i + 1][j + 1] != null && p.worldObj.getBlock(this.cacheX, this.cacheY + j, this.cacheZ + i) == ThaumicHorizons.blockEvanescent) {
                     p.worldObj.setBlock(this.cacheX, this.cacheY + j, this.cacheZ + i, this.tempBlock[i + 1][j + 1], this.tempMD[i + 1][j + 1], 4);
                  }
               } else if(this.tempBlock[i + 1][j + 1] != null && p.worldObj.getBlock(this.cacheX + i, this.cacheY + j, this.cacheZ) == ThaumicHorizons.blockEvanescent) {
                  p.worldObj.setBlock(this.cacheX + i, this.cacheY + j, this.cacheZ, this.tempBlock[i + 1][j + 1], this.tempMD[i + 1][j + 1], 4);
               }
            } else if(this.tempBlock[i + 1][j + 1] != null && p.worldObj.getBlock(this.cacheX + i, this.cacheY, this.cacheZ + j) == ThaumicHorizons.blockEvanescent) {
               p.worldObj.setBlock(this.cacheX + i, this.cacheY, this.cacheZ + j, this.tempBlock[i + 1][j + 1], this.tempMD[i + 1][j + 1], 4);
            }
         }
      }

      this.tempBlock = new Block[3][3];
      this.tempMD = new int[3][3];
   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public void renderTick(RenderTickEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      if(mc.thePlayer != null) {
         ItemStack goggles = mc.thePlayer.inventory.armorItemInSlot(3);
         if(goggles != null && goggles.getItem() instanceof IRevealer && goggles.stackTagCompound != null && goggles.stackTagCompound.getString("Lens") != null && !goggles.stackTagCompound.getString("Lens").equals("")) {
            ILens theLens = (ILens)LensManager.getLens(goggles.stackTagCompound.getString("Lens"));
            if(theLens == ThaumicHorizons.itemLensEarth) {
               this.setBlocksEvanescent(mc.thePlayer);
            }
         }

      }
   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public void renderLast(RenderWorldLastEvent event) {
      Iterator temp = this.thingsThatSparkle.iterator();

      while(temp.hasNext()) {
         EntityLivingBase entity = (EntityLivingBase)temp.next();
         if(Minecraft.getMinecraft().thePlayer.getDistanceSqToEntity(entity) < 32.0D && entity.worldObj.rand.nextFloat() > 0.95F) {
            float angle = (float)((double)(entity.worldObj.rand.nextFloat() * 2.0F) * 3.141592653589793D);
            Thaumcraft.proxy.sparkle((float)entity.posX + entity.width * (float)Math.cos((double)angle), (float)entity.posY + entity.height * (entity.worldObj.rand.nextFloat() - 0.1F) * 1.2F, (float)entity.posZ + entity.width * (float)Math.sin((double)angle), 2.0F, 7, 0.0F);
         }
      }

      if(this.evanescentStage == 2) {
         float temp1 = this.breakProgress;
         this.resetBlocks(Minecraft.getMinecraft().thePlayer);
         this.breakProgress = temp1;
      }

   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public void clearWater(RenderBlockOverlayEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      ItemStack goggles = mc.thePlayer.inventory.armorItemInSlot(3);
      if(goggles != null && goggles.getItem() instanceof IRevealer && goggles.stackTagCompound != null && goggles.stackTagCompound.getString("Lens") != null && !goggles.stackTagCompound.getString("Lens").equals("")) {
         ILens theLens = (ILens)LensManager.getLens(goggles.stackTagCompound.getString("Lens"));
         if(theLens == ThaumicHorizons.itemLensWater) {
            event.setCanceled(true);
         }
      }

      if(event.overlayType == OverlayType.FIRE && mc.thePlayer.ridingEntity != null && mc.thePlayer.ridingEntity instanceof EntityBoatThaumium) {
         event.setCanceled(true);
      }

   }

}
