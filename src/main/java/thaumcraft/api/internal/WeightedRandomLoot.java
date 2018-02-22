package thaumcraft.api.internal;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom.Item;

public class WeightedRandomLoot extends Item {

   public ItemStack item;
   public static ArrayList lootBagCommon = new ArrayList();
   public static ArrayList lootBagUncommon = new ArrayList();
   public static ArrayList lootBagRare = new ArrayList();


   public WeightedRandomLoot(ItemStack stack, int weight) {
      super(weight);
      this.item = stack;
   }

}
