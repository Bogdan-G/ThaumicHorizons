package thaumcraft.api.research;

import cpw.mods.fml.common.FMLLog;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.logging.log4j.Level;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;

public class ResearchCategories {

   public static LinkedHashMap researchCategories = new LinkedHashMap();


   public static ResearchCategoryList getResearchList(String key) {
      return (ResearchCategoryList)researchCategories.get(key);
   }

   public static String getCategoryName(String key) {
      return StatCollector.translateToLocal("tc.research_category." + key);
   }

   public static ResearchItem getResearch(String key) {
      Collection rc = researchCategories.values();
      Iterator var2 = rc.iterator();

      while(var2.hasNext()) {
         Object cat = var2.next();
         Collection rl = ((ResearchCategoryList)cat).research.values();
         Iterator var5 = rl.iterator();

         while(var5.hasNext()) {
            Object ri = var5.next();
            if(((ResearchItem)ri).key.equals(key)) {
               return (ResearchItem)ri;
            }
         }
      }

      return null;
   }

   public static void registerCategory(String key, ResourceLocation icon, ResourceLocation background) {
      if(getResearchList(key) == null) {
         ResearchCategoryList rl = new ResearchCategoryList(icon, background);
         researchCategories.put(key, rl);
      }

   }

   public static void addResearch(ResearchItem ri) {
      ResearchCategoryList rl = getResearchList(ri.category);
      if(rl != null && !rl.research.containsKey(ri.key)) {
         if(!ri.isVirtual()) {
            Iterator var2 = rl.research.values().iterator();

            while(var2.hasNext()) {
               ResearchItem rr = (ResearchItem)var2.next();
               if(rr.displayColumn == ri.displayColumn && rr.displayRow == ri.displayRow) {
                  FMLLog.log(Level.FATAL, "[Thaumcraft] Research [" + ri.getName() + "] not added as it overlaps with existing research [" + rr.getName() + "]", new Object[0]);
                  return;
               }
            }
         }

         rl.research.put(ri.key, ri);
         if(ri.displayColumn < rl.minDisplayColumn) {
            rl.minDisplayColumn = ri.displayColumn;
         }

         if(ri.displayRow < rl.minDisplayRow) {
            rl.minDisplayRow = ri.displayRow;
         }

         if(ri.displayColumn > rl.maxDisplayColumn) {
            rl.maxDisplayColumn = ri.displayColumn;
         }

         if(ri.displayRow > rl.maxDisplayRow) {
            rl.maxDisplayRow = ri.displayRow;
         }
      }

   }

}
