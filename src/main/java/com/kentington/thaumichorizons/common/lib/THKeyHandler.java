package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.lib.EntityInfusionProperties;
import com.kentington.thaumichorizons.common.lib.PacketFingersToServer;
import com.kentington.thaumichorizons.common.lib.PacketHandler;
import com.kentington.thaumichorizons.common.lib.PacketLensChangeToServer;
import com.kentington.thaumichorizons.common.lib.PacketToggleClimbToServer;
import com.kentington.thaumichorizons.common.lib.PacketToggleInvisibleToServer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import thaumcraft.api.nodes.IRevealer;

public class THKeyHandler {

   public KeyBinding keyV = new KeyBinding("Change Arcane Lens", 47, "key.categories.misc");
   public KeyBinding keyM = new KeyBinding("Activate Morphic Fingers", 49, "key.categories.misc");
   public KeyBinding keyC = new KeyBinding("Toggle Spider Climb", 46, "key.categories.misc");
   public KeyBinding keyX = new KeyBinding("Toggle Chameleon Skin", 45, "key.categories.misc");
   private boolean keyPressedM = false;
   public static long lastPressM = 0L;
   private boolean keyPressedC = false;
   public static long lastPressC = 0L;
   private boolean keyPressedX = false;
   public static long lastPressX = 0L;
   private boolean keyPressedV = false;
   public static boolean radialActive = false;
   public static boolean radialLock = false;
   public static long lastPressV = 0L;


   public THKeyHandler() {
      ClientRegistry.registerKeyBinding(this.keyV);
      ClientRegistry.registerKeyBinding(this.keyM);
      ClientRegistry.registerKeyBinding(this.keyC);
      ClientRegistry.registerKeyBinding(this.keyX);
   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public void playerTick(PlayerTickEvent event) {
      if(event.side != Side.SERVER) {
         if(event.phase == Phase.START) {
            EntityPlayer player;
            if(this.keyV.getIsKeyPressed()) {
               if(FMLClientHandler.instance().getClient().inGameHasFocus) {
                  player = event.player;
                  if(player != null) {
                     if(!this.keyPressedV) {
                        lastPressV = System.currentTimeMillis();
                        radialLock = false;
                     }

                     if(!radialLock && player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() instanceof IRevealer) {
                        if(player.isSneaking()) {
                           PacketHandler.INSTANCE.sendToServer(new PacketLensChangeToServer(player, "REMOVE"));
                        } else {
                           radialActive = true;
                        }
                     }
                  }

                  this.keyPressedV = true;
               }
            } else {
               radialActive = false;
               if(this.keyPressedV) {
                  lastPressV = System.currentTimeMillis();
               }

               this.keyPressedV = false;
            }

            if(this.keyM.getIsKeyPressed()) {
               if(FMLClientHandler.instance().getClient().inGameHasFocus) {
                  player = event.player;
                  if(player != null) {
                     if(!this.keyPressedM) {
                        lastPressM = System.currentTimeMillis();
                     }

                     if(((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).hasPlayerInfusion(2) && !this.keyPressedM) {
                        player.openGui(ThaumicHorizons.instance, 9, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
                        PacketHandler.INSTANCE.sendToServer(new PacketFingersToServer(player, player.dimension));
                     }
                  }

                  this.keyPressedM = true;
               }
            } else {
               if(this.keyPressedM) {
                  lastPressM = System.currentTimeMillis();
               }

               this.keyPressedM = false;
            }

            if(this.keyC.getIsKeyPressed()) {
               if(FMLClientHandler.instance().getClient().inGameHasFocus) {
                  player = event.player;
                  if(player != null) {
                     if(!this.keyPressedC) {
                        lastPressC = System.currentTimeMillis();
                     }

                     if(((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).hasPlayerInfusion(9) && !this.keyPressedC) {
                        ((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleClimb = !((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleClimb;
                        if(((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleClimb) {
                           player.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + "Spider Climb disabled."));
                        } else {
                           player.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + "Spider Climb enabled."));
                        }

                        PacketHandler.INSTANCE.sendToServer(new PacketToggleClimbToServer(player, player.dimension));
                     }
                  }

                  this.keyPressedC = true;
               }
            } else {
               if(this.keyPressedC) {
                  lastPressC = System.currentTimeMillis();
               }

               this.keyPressedC = false;
            }

            if(this.keyX.getIsKeyPressed()) {
               if(FMLClientHandler.instance().getClient().inGameHasFocus) {
                  player = event.player;
                  if(player != null) {
                     if(!this.keyPressedX) {
                        lastPressX = System.currentTimeMillis();
                     }

                     if(((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).hasPlayerInfusion(10) && !this.keyPressedX) {
                        ((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleInvisible = !((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleInvisible;
                        if(((EntityInfusionProperties)player.getExtendedProperties("CreatureInfusion")).toggleInvisible) {
                           player.removePotionEffectClient(Potion.invisibility.id);
                           player.setInvisible(false);
                           player.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + "Chameleon Skin disabled."));
                        } else {
                           PotionEffect effect = new PotionEffect(Potion.invisibility.id, Integer.MAX_VALUE, 0, true);
                           effect.setCurativeItems(new ArrayList());
                           player.addPotionEffect(effect);
                           player.setInvisible(true);
                           player.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + "" + EnumChatFormatting.GRAY + "Chameleon Skin enabled."));
                        }

                        PacketHandler.INSTANCE.sendToServer(new PacketToggleInvisibleToServer(player, player.dimension));
                     }
                  }

                  this.keyPressedX = true;
               }
            } else {
               if(this.keyPressedX) {
                  lastPressX = System.currentTimeMillis();
               }

               this.keyPressedX = false;
            }
         }

      }
   }

}
