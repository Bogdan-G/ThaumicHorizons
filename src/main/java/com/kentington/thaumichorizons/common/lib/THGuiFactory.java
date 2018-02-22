package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.lib.THConfigGUI;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionCategoryElement;
import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionGuiHandler;
import java.util.Set;
import net.minecraft.client.Minecraft;

public class THGuiFactory implements IModGuiFactory {

   public void initialize(Minecraft minecraftInstance) {}

   public Class mainConfigGuiClass() {
      return THConfigGUI.class;
   }

   public Set runtimeGuiCategories() {
      return null;
   }

   public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
      return null;
   }
}
