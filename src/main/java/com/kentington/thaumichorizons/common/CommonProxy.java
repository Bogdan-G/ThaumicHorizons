package com.kentington.thaumichorizons.common;

import com.kentington.thaumichorizons.common.container.ContainerBloodInfuser;
import com.kentington.thaumichorizons.common.container.ContainerCase;
import com.kentington.thaumichorizons.common.container.ContainerFingers;
import com.kentington.thaumichorizons.common.container.ContainerInjector;
import com.kentington.thaumichorizons.common.container.ContainerInspiratron;
import com.kentington.thaumichorizons.common.container.ContainerSoulExtractor;
import com.kentington.thaumichorizons.common.container.ContainerSoulforge;
import com.kentington.thaumichorizons.common.container.ContainerVat;
import com.kentington.thaumichorizons.common.container.ContainerVisDynamo;
import com.kentington.thaumichorizons.common.items.WandManagerTH;
import com.kentington.thaumichorizons.common.tiles.TileBloodInfuser;
import com.kentington.thaumichorizons.common.tiles.TileInspiratron;
import com.kentington.thaumichorizons.common.tiles.TileSoulExtractor;
import com.kentington.thaumichorizons.common.tiles.TileSoulforge;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import com.kentington.thaumichorizons.common.tiles.TileVisDynamo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import thaumcraft.api.wands.IWandTriggerManager;

public class CommonProxy implements IGuiHandler {

   public IWandTriggerManager wandManager = new WandManagerTH();


   public void registerRenderers() {}

   public void registerKeyBindings() {}

   public void sendCustomPacketToAllNear(Packet packet, double range, Entity enity) {
      if(enity != null) {
         FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendToAllNear(enity.posX, enity.posY, enity.posZ, range, enity.worldObj.provider.dimensionId, packet);
      }

   }

   public void essentiaTrailEntityFx(WorldClient world, int posX, int posY, int posZ, Entity end, int i, int color, float scale) {}

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      return null;
   }

   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      switch(ID) {
      case 1:
         return new ContainerVisDynamo(player, (TileVisDynamo)world.getTileEntity(x, y, z));
      case 2:
         return new ContainerSoulExtractor(player.inventory, (TileSoulExtractor)world.getTileEntity(x, y, z));
      case 3:
         return new ContainerInspiratron(player.inventory, (TileInspiratron)world.getTileEntity(x, y, z));
      case 4:
         return new ContainerSoulforge(player, (TileSoulforge)world.getTileEntity(x, y, z));
      case 5:
         return new ContainerBloodInfuser(player, (TileBloodInfuser)world.getTileEntity(x, y, z));
      case 6:
         return new ContainerInjector(player);
      case 7:
         return new ContainerVat(player, (TileVat)world.getTileEntity(x, y, z));
      case 8:
         return new ContainerCase(player.inventory, world, x, y, z);
      case 9:
         return new ContainerFingers(player.inventory);
      default:
         return null;
      }
   }

   public void registerDisplayInformation() {}

   public void soulParticles(int blockX, int blockY, int blockZ, World world) {}

   public void disintegrateFX(double blockX, double blockY, double blockZ, EntityPlayer p, int howMany, boolean enlarged) {}

   public void smeltFX(double blockX, double blockY, double blockZ, World w, int howMany, boolean enlarged) {}

   public void containmentFX(double blockX, double blockY, double blockZ, EntityPlayer p, Entity ent, int times) {}

   public void registerHandlers() {}

   public void disintegrateExplodeFX(World worldObj, double posX, double posY, double posZ) {}

   public void illuminationFX(World world, int xCoord, int yCoord, int zCoord, int md, Color col) {}

   public void blockSplosionFX(int x, int y, int z, Block block, int md) {}

   public void alchemiteFX(World worldObj, double x, double y, double z) {}

   public boolean readyToRender() {
      return false;
   }

   public void addEffect(Entity entity) {}

   public void lightningBolt(World worldObj, double x, double y, double z, int boltLength) {}
}
