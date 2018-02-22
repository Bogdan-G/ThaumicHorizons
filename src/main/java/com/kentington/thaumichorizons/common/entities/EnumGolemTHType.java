package com.kentington.thaumichorizons.common.entities;

import java.util.HashMap;
import java.util.Map;

public enum EnumGolemTHType {

   GRASS("GRASS", 0, 10, 0, 0.38F, false, 2, 1, 75, 0, 750),
   DIRT("DIRT", 1, 10, 0, 0.38F, false, 1, 1, 75, 0, 500),
   WOOD("WOOD", 2, 20, 6, 0.35F, false, 2, 4, 75, 1, 750),
   PLANT("PLANT", 3, 10, 0, 0.4F, false, 2, 1, 75, 0, 750),
   ROCK("ROCK", 4, 30, 12, 0.32F, true, 2, 16, 100, 3, 1000),
   METAL("METAL", 5, 35, 15, 0.31F, true, 2, 32, 125, 4, 1250),
   CLOTH("CLOTH", 6, 10, 0, 0.4F, false, 2, 1, 75, 0, 750),
   SAND("SAND", 7, 10, 0, 0.4F, false, 1, 1, 75, 0, 500),
   REDSTONE("REDSTONE", 8, 20, 9, 0.35F, true, 2, 8, 45, 2, 1000),
   TNT("TNT", 9, 10, 0, 0.38F, false, 1, 1, 75, 0, 750),
   ICE("ICE", 10, 20, 6, 0.35F, false, 2, 4, 75, 1, 750),
   CACTUS("CACTUS", 11, 20, 6, 0.35F, false, 2, 4, 75, 1, 1000),
   CLAY("CLAY", 12, 25, 9, 0.33F, true, 1, 8, 100, 2, 750),
   CAKE("CAKE", 13, 20, 6, 0.35F, false, 2, 4, 25, 1, 750),
   WEB("WEB", 14, 15, 6, 0.35F, false, 2, 4, 40, 1, 1000),
   VOID("VOID", 15, 25, 15, 0.4F, true, 2, 8, 20, 3, 0);
   public final int health;
   public final int armor;
   public final float speed;
   public final boolean fireResist;
   public final int upgrades;
   public final int carry;
   public final int regenDelay;
   public final int strength;
   public final int visCost;
   private static Map codeToTypeMapping;
   // $FF: synthetic field
   private static final EnumGolemTHType[] $VALUES = new EnumGolemTHType[]{GRASS, DIRT, WOOD, PLANT, ROCK, METAL, CLOTH, SAND, REDSTONE, TNT, ICE, CACTUS, CLAY, CAKE, WEB, VOID};


   private EnumGolemTHType(String var1, int var2, int health, int armor, float speed, boolean fireResist, int upgrades, int carry, int regenDelay, int strength, int special) {
      this.health = health;
      this.armor = armor;
      this.speed = speed;
      this.fireResist = fireResist;
      this.upgrades = upgrades;
      this.carry = carry;
      this.regenDelay = regenDelay;
      this.strength = strength;
      this.visCost = special;
   }

   public static EnumGolemTHType getType(int i) {
      if(codeToTypeMapping == null) {
         initMapping();
      }

      return (EnumGolemTHType)codeToTypeMapping.get(Integer.valueOf(i));
   }

   private static void initMapping() {
      codeToTypeMapping = new HashMap();
      EnumGolemTHType[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumGolemTHType s = var0[var2];
         codeToTypeMapping.put(Integer.valueOf(s.ordinal()), s);
      }

   }

}
