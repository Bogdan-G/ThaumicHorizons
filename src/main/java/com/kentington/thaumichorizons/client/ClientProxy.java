package com.kentington.thaumichorizons.client;

import com.kentington.thaumichorizons.client.gui.GuiBloodInfuser;
import com.kentington.thaumichorizons.client.gui.GuiCase;
import com.kentington.thaumichorizons.client.gui.GuiFingers;
import com.kentington.thaumichorizons.client.gui.GuiInjector;
import com.kentington.thaumichorizons.client.gui.GuiInspiratron;
import com.kentington.thaumichorizons.client.gui.GuiSoulExtractor;
import com.kentington.thaumichorizons.client.gui.GuiSoulforge;
import com.kentington.thaumichorizons.client.gui.GuiVat;
import com.kentington.thaumichorizons.client.gui.GuiVisDynamo;
import com.kentington.thaumichorizons.client.renderer.block.BlockBloodInfuserRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockEssentiaDynamoRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockInspiratronRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockJarTHRenderer;
import com.kentington.thaumichorizons.client.renderer.block.BlockNodeMonitorRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockRecombinatorRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockSlotRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockSoulBeaconRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockSoulSieveRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockSoulforgeRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockSpikeRenderer;
import com.kentington.thaumichorizons.client.renderer.block.BlockSyntheticNodeRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockTransductionAmplifierRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockVatInteriorRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockVatMatrixRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockVatRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockVatSolidRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockVisDynamoRender;
import com.kentington.thaumichorizons.client.renderer.block.BlockVortexStabilizerRender;
import com.kentington.thaumichorizons.client.renderer.entity.BlastPhialRender;
import com.kentington.thaumichorizons.client.renderer.entity.RenderAlchemitePrimed;
import com.kentington.thaumichorizons.client.renderer.entity.RenderBoatGreatwood;
import com.kentington.thaumichorizons.client.renderer.entity.RenderBoatThaumium;
import com.kentington.thaumichorizons.client.renderer.entity.RenderChocolateCow;
import com.kentington.thaumichorizons.client.renderer.entity.RenderEndersteed;
import com.kentington.thaumichorizons.client.renderer.entity.RenderFamiliar;
import com.kentington.thaumichorizons.client.renderer.entity.RenderGoldChicken;
import com.kentington.thaumichorizons.client.renderer.entity.RenderGolemTH;
import com.kentington.thaumichorizons.client.renderer.entity.RenderGravekeeper;
import com.kentington.thaumichorizons.client.renderer.entity.RenderGuardianPanther;
import com.kentington.thaumichorizons.client.renderer.entity.RenderLightningBoltFinite;
import com.kentington.thaumichorizons.client.renderer.entity.RenderLunarWolf;
import com.kentington.thaumichorizons.client.renderer.entity.RenderMeatSlime;
import com.kentington.thaumichorizons.client.renderer.entity.RenderMedSlime;
import com.kentington.thaumichorizons.client.renderer.entity.RenderMercurialSlime;
import com.kentington.thaumichorizons.client.renderer.entity.RenderNetherHound;
import com.kentington.thaumichorizons.client.renderer.entity.RenderNightmare;
import com.kentington.thaumichorizons.client.renderer.entity.RenderOreBoar;
import com.kentington.thaumichorizons.client.renderer.entity.RenderScholarChicken;
import com.kentington.thaumichorizons.client.renderer.entity.RenderSeawolf;
import com.kentington.thaumichorizons.client.renderer.entity.RenderSheeder;
import com.kentington.thaumichorizons.client.renderer.entity.RenderSoul;
import com.kentington.thaumichorizons.client.renderer.entity.RenderSyringe;
import com.kentington.thaumichorizons.client.renderer.entity.RenderTaintfeeder;
import com.kentington.thaumichorizons.client.renderer.entity.RenderVoltSlime;
import com.kentington.thaumichorizons.client.renderer.item.ItemCorpseEffigyRender;
import com.kentington.thaumichorizons.client.renderer.item.ItemInjectorRender;
import com.kentington.thaumichorizons.client.renderer.item.ItemSyringeRender;
import com.kentington.thaumichorizons.client.renderer.model.ModelFamiliar;
import com.kentington.thaumichorizons.client.renderer.model.ModelGolemTH;
import com.kentington.thaumichorizons.client.renderer.tile.ItemJarTHRenderer;
import com.kentington.thaumichorizons.client.renderer.tile.TileBloodInfuserRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileCloudRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileEssentiaDynamoRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileEtherealShardRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileInspiratronRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileJarTHRenderer;
import com.kentington.thaumichorizons.client.renderer.tile.TileNodeMonitorRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileRecombinatorRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileSlotRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileSoulBeaconRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileSoulSieveRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileSoulforgeRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileSpikeRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileTransductionAmplifierRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileVatMatrixRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileVatSlaveRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileVisDynamoRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileVortexRender;
import com.kentington.thaumichorizons.client.renderer.tile.TileVortexStabilizerRender;
import com.kentington.thaumichorizons.common.CommonProxy;
import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.entities.EntityAlchemitePrimed;
import com.kentington.thaumichorizons.common.entities.EntityBlastPhial;
import com.kentington.thaumichorizons.common.entities.EntityBoatGreatwood;
import com.kentington.thaumichorizons.common.entities.EntityBoatThaumium;
import com.kentington.thaumichorizons.common.entities.EntityChocolateCow;
import com.kentington.thaumichorizons.common.entities.EntityEndersteed;
import com.kentington.thaumichorizons.common.entities.EntityFamiliar;
import com.kentington.thaumichorizons.common.entities.EntityGoldChicken;
import com.kentington.thaumichorizons.common.entities.EntityGolemTH;
import com.kentington.thaumichorizons.common.entities.EntityGravekeeper;
import com.kentington.thaumichorizons.common.entities.EntityGuardianPanther;
import com.kentington.thaumichorizons.common.entities.EntityLightningBoltFinite;
import com.kentington.thaumichorizons.common.entities.EntityLunarWolf;
import com.kentington.thaumichorizons.common.entities.EntityMeatSlime;
import com.kentington.thaumichorizons.common.entities.EntityMedSlime;
import com.kentington.thaumichorizons.common.entities.EntityMercurialSlime;
import com.kentington.thaumichorizons.common.entities.EntityNetherHound;
import com.kentington.thaumichorizons.common.entities.EntityNightmare;
import com.kentington.thaumichorizons.common.entities.EntityOrePig;
import com.kentington.thaumichorizons.common.entities.EntityScholarChicken;
import com.kentington.thaumichorizons.common.entities.EntitySeawolf;
import com.kentington.thaumichorizons.common.entities.EntitySheeder;
import com.kentington.thaumichorizons.common.entities.EntitySoul;
import com.kentington.thaumichorizons.common.entities.EntitySyringe;
import com.kentington.thaumichorizons.common.entities.EntityTaintPig;
import com.kentington.thaumichorizons.common.entities.EntityVoltSlime;
import com.kentington.thaumichorizons.common.items.WandManagerTH;
import com.kentington.thaumichorizons.common.lib.THKeyHandler;
import com.kentington.thaumichorizons.common.tiles.TileBloodInfuser;
import com.kentington.thaumichorizons.common.tiles.TileCloud;
import com.kentington.thaumichorizons.common.tiles.TileEssentiaDynamo;
import com.kentington.thaumichorizons.common.tiles.TileInspiratron;
import com.kentington.thaumichorizons.common.tiles.TileNodeMonitor;
import com.kentington.thaumichorizons.common.tiles.TileRecombinator;
import com.kentington.thaumichorizons.common.tiles.TileSlot;
import com.kentington.thaumichorizons.common.tiles.TileSoulBeacon;
import com.kentington.thaumichorizons.common.tiles.TileSoulExtractor;
import com.kentington.thaumichorizons.common.tiles.TileSoulJar;
import com.kentington.thaumichorizons.common.tiles.TileSoulforge;
import com.kentington.thaumichorizons.common.tiles.TileSpike;
import com.kentington.thaumichorizons.common.tiles.TileSyntheticNode;
import com.kentington.thaumichorizons.common.tiles.TileTransductionAmplifier;
import com.kentington.thaumichorizons.common.tiles.TileVat;
import com.kentington.thaumichorizons.common.tiles.TileVatMatrix;
import com.kentington.thaumichorizons.common.tiles.TileVatSlave;
import com.kentington.thaumichorizons.common.tiles.TileVisDynamo;
import com.kentington.thaumichorizons.common.tiles.TileVortex;
import com.kentington.thaumichorizons.common.tiles.TileVortexStabilizer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXBurst;
import thaumcraft.client.fx.particles.FXSparkle;
import thaumcraft.client.fx.particles.FXWisp;
import thaumcraft.client.renderers.item.ItemWandRenderer;
import thaumcraft.common.Thaumcraft;

public class ClientProxy extends CommonProxy {

   public IWandTriggerManager wandManager = new WandManagerTH();


   public void registerHandlers() {
      MinecraftForge.EVENT_BUS.register(ThaumicHorizons.instance.renderEventHandler);
   }

   public void registerKeyBindings() {
      FMLCommonHandler.instance().bus().register(new THKeyHandler());
   }

   public void registerRenderers() {
      ClientRegistry.bindTileEntitySpecialRenderer(TileNodeMonitor.class, new TileNodeMonitorRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSyntheticNode.class, new TileEtherealShardRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileVisDynamo.class, new TileVisDynamoRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEssentiaDynamo.class, new TileEssentiaDynamoRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSoulExtractor.class, new TileSoulSieveRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileInspiratron.class, new TileInspiratronRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSoulforge.class, new TileSoulforgeRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileVatSlave.class, new TileVatSlaveRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileVatMatrix.class, new TileVatMatrixRender(0));
      ClientRegistry.bindTileEntitySpecialRenderer(TileBloodInfuser.class, new TileBloodInfuserRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSoulBeacon.class, new TileSoulBeaconRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileTransductionAmplifier.class, new TileTransductionAmplifierRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileRecombinator.class, new TileRecombinatorRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileVortexStabilizer.class, new TileVortexStabilizerRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileVortex.class, new TileVortexRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSpike.class, new TileSpikeRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileCloud.class, new TileCloudRender());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSlot.class, new TileSlotRender());
      RenderingRegistry.registerEntityRenderingHandler(EntityAlchemitePrimed.class, new RenderAlchemitePrimed());
      RenderingRegistry.registerEntityRenderingHandler(EntitySyringe.class, new RenderSyringe());
      RenderingRegistry.registerEntityRenderingHandler(EntityBlastPhial.class, new BlastPhialRender());
      RenderingRegistry.registerEntityRenderingHandler(EntityChocolateCow.class, new RenderChocolateCow(new ModelCow(), 0.7F));
      RenderingRegistry.registerEntityRenderingHandler(EntityOrePig.class, new RenderOreBoar(new ModelPig(), new ModelPig(0.5F), 0.7F));
      RenderingRegistry.registerEntityRenderingHandler(EntityGuardianPanther.class, new RenderGuardianPanther(new ModelOcelot(), 1.0F));
      RenderingRegistry.registerEntityRenderingHandler(EntityFamiliar.class, new RenderFamiliar(new ModelFamiliar(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityGravekeeper.class, new RenderGravekeeper(new ModelOcelot(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityGoldChicken.class, new RenderGoldChicken(new ModelChicken(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityScholarChicken.class, new RenderScholarChicken(new ModelChicken(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityTaintPig.class, new RenderTaintfeeder(new ModelPig(), new ModelPig(0.5F), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityNetherHound.class, new RenderNetherHound(new ModelWolf(), new ModelWolf(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntitySeawolf.class, new RenderSeawolf(new ModelWolf(), new ModelWolf(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityLunarWolf.class, new RenderLunarWolf(new ModelWolf(), new ModelWolf(), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityGolemTH.class, new RenderGolemTH(new ModelGolemTH(false)));
      RenderingRegistry.registerEntityRenderingHandler(EntityEndersteed.class, new RenderEndersteed(new ModelHorse(), 0.75F));
      RenderingRegistry.registerEntityRenderingHandler(EntityNightmare.class, new RenderNightmare(new ModelHorse(), 0.75F));
      RenderingRegistry.registerEntityRenderingHandler(EntityBoatGreatwood.class, new RenderBoatGreatwood());
      RenderingRegistry.registerEntityRenderingHandler(EntityBoatThaumium.class, new RenderBoatThaumium());
      RenderingRegistry.registerEntityRenderingHandler(EntityMeatSlime.class, new RenderMeatSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
      RenderingRegistry.registerEntityRenderingHandler(EntityMercurialSlime.class, new RenderMercurialSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
      RenderingRegistry.registerEntityRenderingHandler(EntityVoltSlime.class, new RenderVoltSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
      RenderingRegistry.registerEntityRenderingHandler(EntityMedSlime.class, new RenderMedSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
      RenderingRegistry.registerEntityRenderingHandler(EntitySheeder.class, new RenderSheeder());
      RenderingRegistry.registerEntityRenderingHandler(EntitySoul.class, new RenderSoul());
      RenderingRegistry.registerEntityRenderingHandler(EntityLightningBoltFinite.class, new RenderLightningBoltFinite());
      MinecraftForgeClient.registerItemRenderer(ThaumicHorizons.itemSyringeBloodSample, new ItemSyringeRender());
      MinecraftForgeClient.registerItemRenderer(ThaumicHorizons.itemSyringeHuman, new ItemSyringeRender());
      MinecraftForgeClient.registerItemRenderer(ThaumicHorizons.itemSyringeEmpty, new ItemSyringeRender());
      MinecraftForgeClient.registerItemRenderer(ThaumicHorizons.itemSyringeInjection, new ItemSyringeRender());
      MinecraftForgeClient.registerItemRenderer(ThaumicHorizons.itemCorpseEffigy, new ItemCorpseEffigyRender());
      MinecraftForgeClient.registerItemRenderer(ThaumicHorizons.itemInjector, new ItemInjectorRender());
      MinecraftForgeClient.registerItemRenderer(ThaumicHorizons.itemWandCastingDisposable, new ItemWandRenderer());
   }

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if(world instanceof WorldClient) {
         switch(ID) {
         case 1:
            return new GuiVisDynamo(player, (TileVisDynamo)world.getTileEntity(x, y, z));
         case 2:
            return new GuiSoulExtractor(player.inventory, (TileSoulExtractor)world.getTileEntity(x, y, z));
         case 3:
            return new GuiInspiratron(player.inventory, (TileInspiratron)world.getTileEntity(x, y, z));
         case 4:
            return new GuiSoulforge(player, (TileSoulforge)world.getTileEntity(x, y, z));
         case 5:
            return new GuiBloodInfuser(player, (TileBloodInfuser)world.getTileEntity(x, y, z));
         case 6:
            return new GuiInjector(player);
         case 7:
            return new GuiVat(player, (TileVat)world.getTileEntity(x, y, z));
         case 8:
            return new GuiCase(player.inventory, world, x, y, z);
         case 9:
            return new GuiFingers(player.inventory);
         }
      }

      return null;
   }

   public void registerDisplayInformation() {
      ThaumicHorizons.blockJarRI = RenderingRegistry.getNextAvailableRenderId();
      MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ThaumicHorizons.blockJar), new ItemJarTHRenderer());
      RenderingRegistry.registerBlockHandler(new BlockJarTHRenderer());
      ClientRegistry.bindTileEntitySpecialRenderer(TileSoulJar.class, new TileJarTHRenderer());
      ThaumicHorizons.blockSyntheticNodeRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockSyntheticNodeRender());
      ThaumicHorizons.blockNodeMonRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockNodeMonitorRender());
      ThaumicHorizons.blockVisDynamoRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockVisDynamoRender());
      ThaumicHorizons.blockEssentiaDynamoRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockEssentiaDynamoRender());
      ThaumicHorizons.blockSoulSieveRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockSoulSieveRender());
      ThaumicHorizons.blockInspiratronRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockInspiratronRender());
      ThaumicHorizons.blockSoulforgeRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockSoulforgeRender());
      ThaumicHorizons.blockVatRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockVatRender());
      ThaumicHorizons.blockVatSolidRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockVatSolidRender());
      ThaumicHorizons.blockVatInteriorRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockVatInteriorRender());
      ThaumicHorizons.blockVatMatrixRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockVatMatrixRender());
      ThaumicHorizons.blockBloodInfuserRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockBloodInfuserRender());
      ThaumicHorizons.blockSoulBeaconRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockSoulBeaconRender());
      ThaumicHorizons.blockTransducerRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockTransductionAmplifierRender());
      ThaumicHorizons.blockRecombinatorRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockRecombinatorRender());
      ThaumicHorizons.blockVortexStabilizerRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockVortexStabilizerRender());
      ThaumicHorizons.blockSpikeRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockSpikeRenderer());
      ThaumicHorizons.blockSlotRI = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BlockSlotRender());
   }

   public void disintegrateFX(double blockX, double blockY, double blockZ, EntityPlayer p, int howMany, boolean enlarged) {
      FXSparkle fx;
      int i;
      if(enlarged) {
         for(i = -1; i < 2; ++i) {
            for(int y = -1; y < 2; ++y) {
               for(int z = -1; z < 2; ++z) {
                  for(int i1 = 0; i1 < howMany; ++i1) {
                     fx = new FXSparkle(p.worldObj, blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, 1.0F, 0, 6);
                     fx.motionX = (p.worldObj.rand.nextDouble() - 0.5D) / 4.0D;
                     fx.motionY = (p.worldObj.rand.nextDouble() - 0.5D) / 4.0D;
                     fx.motionZ = (p.worldObj.rand.nextDouble() - 0.5D) / 4.0D;
                     fx.noClip = true;
                     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
                  }
               }
            }
         }
      } else {
         for(i = 0; i < howMany; ++i) {
            fx = new FXSparkle(p.worldObj, blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, 1.0F, 0, 6);
            fx.motionX = (p.worldObj.rand.nextDouble() - 0.5D) / 4.0D;
            fx.motionY = (p.worldObj.rand.nextDouble() - 0.5D) / 4.0D;
            fx.motionZ = (p.worldObj.rand.nextDouble() - 0.5D) / 4.0D;
            fx.noClip = true;
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
         }
      }

   }

   public void smeltFX(double blockX, double blockY, double blockZ, World w, int howMany, boolean enlarged) {
      EntityFlameFX fx;
      int i;
      if(enlarged) {
         for(i = -1; i < 2; ++i) {
            for(int y = -1; y < 2; ++y) {
               for(int z = -1; z < 2; ++z) {
                  for(int i1 = 0; i1 < howMany; ++i1) {
                     fx = new EntityFlameFX(w, blockX + 0.5D + (double)i, blockY + 0.5D + (double)y, blockZ + 0.5D + (double)z, (w.rand.nextDouble() - 0.5D) * 0.25D, (w.rand.nextDouble() - 0.5D) * 0.25D, (w.rand.nextDouble() - 0.5D) * 0.25D);
                     fx.noClip = true;
                     FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
                  }
               }
            }
         }
      } else {
         for(i = 0; i < howMany; ++i) {
            fx = new EntityFlameFX(w, blockX + 0.5D, blockY + 0.5D, blockZ + 0.5D, (w.rand.nextDouble() - 0.5D) * 0.25D, (w.rand.nextDouble() - 0.5D) * 0.25D, (w.rand.nextDouble() - 0.5D) * 0.25D);
            fx.noClip = true;
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
         }
      }

   }

   public void soulParticles(int blockX, int blockY, int blockZ, World world) {
      for(int i = 0; i < 10; ++i) {
         EntitySpellParticleFX fx = new EntitySpellParticleFX(world, (double)blockX + 0.5D + (world.rand.nextDouble() - 0.5D) * 0.8D, (double)blockY + 0.8D, (double)blockZ + 0.5D + (world.rand.nextDouble() - 0.5D) * 0.8D, 0.0D, world.rand.nextDouble() * 0.25D, 0.0D);
         fx.noClip = true;
         FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
      }

   }

   public void containmentFX(double blockX, double blockY, double blockZ, EntityPlayer p, Entity ent, int times) {
      double xSize = ent.boundingBox.maxX - ent.boundingBox.minX;
      double ySize = ent.boundingBox.maxY - ent.boundingBox.minY;
      double zSize = ent.boundingBox.maxZ - ent.boundingBox.minZ;
      double radius = xSize > ySize?(xSize > zSize?xSize:zSize):(ySize > zSize?ySize:zSize);
      double xCenter = (ent.boundingBox.maxX + ent.boundingBox.minX) / 2.0D;
      double yCenter = (ent.boundingBox.maxY + ent.boundingBox.minY) / 2.0D;
      double zCenter = (ent.boundingBox.maxZ + ent.boundingBox.minZ) / 2.0D;

      for(int i = 0; i < times; ++i) {
         double theta = p.worldObj.rand.nextDouble() * 3.141592653589793D * 2.0D;
         double phi = p.worldObj.rand.nextDouble() * 3.141592653589793D * 2.0D;
         double z1 = zCenter + radius * Math.cos(phi);
         double y1 = yCenter + radius * Math.sin(phi) * Math.sin(theta);
         double x1 = xCenter + radius * Math.sin(phi) * Math.cos(theta);
         theta = p.worldObj.rand.nextDouble() * 3.141592653589793D * 2.0D;
         phi = p.worldObj.rand.nextDouble() * 3.141592653589793D * 2.0D;
         double z2 = zCenter + radius * Math.cos(phi);
         double y2 = yCenter + radius * Math.sin(phi) * Math.sin(theta);
         double x2 = xCenter + radius * Math.sin(phi) * Math.cos(theta);
         Thaumcraft.proxy.arcLightning(p.worldObj, x1, y1, z1, x2, y2, z2, p.worldObj.rand.nextFloat() * 0.1F, p.worldObj.rand.nextFloat() * 0.2F, p.worldObj.rand.nextFloat() * 0.8F, p.worldObj.rand.nextFloat());
      }

   }

   public void disintegrateExplodeFX(World worldObj, double posX, double posY, double posZ) {
      FXBurst ef = new FXBurst(worldObj, posX, posY, posZ, 1.0F);
      FMLClientHandler.instance().getClient().effectRenderer.addEffect(ef);
   }

   public void illuminationFX(World world, int xCoord, int yCoord, int zCoord, int md, Color col) {
      if(world.rand.nextInt(9 - Thaumcraft.proxy.particleCount(2)) == 0) {
         FXWisp ef = new FXWisp(world, (double)((float)xCoord + 0.55F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.55F), 0.5F, (float)col.getRed() / 255.0F + 0.01F, (float)col.getGreen() / 255.0F, (float)col.getBlue() / 255.0F);
         ef.setGravity(0.0F);
         ef.shrink = false;
         if(md == 0) {
            ef.blendmode = 0;
         }

         ParticleEngine.instance.addEffect(world, ef);
      }

   }

   public void blockSplosionFX(int x, int y, int z, Block block, int md) {
      Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(x, y, z, block, md);
   }

   public void alchemiteFX(World worldObj, double x, double y, double z) {
      FXBurst ef = new FXBurst(worldObj, x, y, z, 10.0F);
      FMLClientHandler.instance().getClient().effectRenderer.addEffect(ef);
   }

   public boolean readyToRender() {
      return FMLClientHandler.instance().getClient().renderViewEntity != null;
   }

   public void addEffect(Entity entity) {
      FMLClientHandler.instance().getClient().effectRenderer.addEffect((EntityFX)entity);
   }

   public void lightningBolt(World worldObj, double x, double y, double z, int boltLength) {
      Thaumcraft.proxy.arcLightning(worldObj, x + (double)worldObj.rand.nextFloat() - 0.5D, y + (double)boltLength + 0.5D, z + (double)worldObj.rand.nextFloat() - 0.5D, x + (double)worldObj.rand.nextFloat() - 0.5D, y + 1.0D, z + (double)worldObj.rand.nextFloat() - 0.5D, 0.8F, 1.0F, 1.0F, 0.1F);
   }
}
