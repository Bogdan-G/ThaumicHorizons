package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.PacketFXBlocksplosion;
import com.kentington.thaumichorizons.common.lib.PacketFXContainment;
import com.kentington.thaumichorizons.common.lib.PacketFXDeadCreature;
import com.kentington.thaumichorizons.common.lib.PacketFXEssentiaBubble;
import com.kentington.thaumichorizons.common.lib.PacketFXInfusionDone;
import com.kentington.thaumichorizons.common.lib.PacketFingersToServer;
import com.kentington.thaumichorizons.common.lib.PacketInfusionFX;
import com.kentington.thaumichorizons.common.lib.PacketLensChangeToServer;
import com.kentington.thaumichorizons.common.lib.PacketMountNightmare;
import com.kentington.thaumichorizons.common.lib.PacketNoMoreItems;
import com.kentington.thaumichorizons.common.lib.PacketPlayerInfusionSync;
import com.kentington.thaumichorizons.common.lib.PacketRemoveNightvision;
import com.kentington.thaumichorizons.common.lib.PacketToggleClimbToServer;
import com.kentington.thaumichorizons.common.lib.PacketToggleInvisibleToServer;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

   public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("ThaumicHorizons".toLowerCase());


   public static void init() {
      byte idx = 0;
      int var1 = idx + 1;
      INSTANCE.registerMessage(PacketLensChangeToServer.class, PacketLensChangeToServer.class, idx, Side.SERVER);
      INSTANCE.registerMessage(PacketFXEssentiaBubble.class, PacketFXEssentiaBubble.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketInfusionFX.class, PacketInfusionFX.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketFXInfusionDone.class, PacketFXInfusionDone.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketFXDeadCreature.class, PacketFXDeadCreature.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketFXContainment.class, PacketFXContainment.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketNoMoreItems.class, PacketNoMoreItems.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketFXBlocksplosion.class, PacketFXBlocksplosion.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketRemoveNightvision.class, PacketRemoveNightvision.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketFingersToServer.class, PacketFingersToServer.class, var1++, Side.SERVER);
      INSTANCE.registerMessage(PacketPlayerInfusionSync.class, PacketPlayerInfusionSync.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketMountNightmare.class, PacketMountNightmare.class, var1++, Side.CLIENT);
      INSTANCE.registerMessage(PacketToggleClimbToServer.class, PacketToggleClimbToServer.class, var1++, Side.SERVER);
      INSTANCE.registerMessage(PacketToggleInvisibleToServer.class, PacketToggleInvisibleToServer.class, var1++, Side.SERVER);
   }

}
