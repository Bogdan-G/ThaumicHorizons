package com.kentington.thaumichorizons.common.lib;

import com.kentington.thaumichorizons.common.entities.EntityWizardCow;
import com.kentington.thaumichorizons.common.lib.PacketCowUpdate;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class PacketGetCowData implements IMessage, IMessageHandler<PacketGetCowData, IMessage> {

   int id;
   static Aspect[] sorted;


   public PacketGetCowData() {}

   public PacketGetCowData(int id) {
      this.id = id;
   }

   public IMessage onMessage(PacketGetCowData message, MessageContext ctx) {
      World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
      Entity ent = world.getEntityByID(message.id);
      if(!(ent instanceof EntityWizardCow)) {
         return null;
      } else {
         EntityWizardCow cow = (EntityWizardCow)ent;
         AspectList ess = cow.getEssentia();
         int mod = cow.nodeMod;
         int type = cow.nodeType;
         int[] types = new int[ess.size()];
         int[] amounts = new int[ess.size()];
         int pointer = 0;
         Aspect[] var12 = ess.getAspects();
         int var13 = var12.length;
         int var14 = 0;

         while(var14 < var13) {
            Aspect asp = var12[var14];
            amounts[pointer] = ess.getAmount(asp);
            int i = 0;

            while(true) {
               if(i < sorted.length) {
                  if(!sorted[i].getTag().equals(asp.getTag())) {
                     ++i;
                     continue;
                  }

                  types[pointer] = i;
                  ++pointer;
               }

               ++var14;
               break;
            }
         }

         return new PacketCowUpdate(types, amounts, type, mod, message.id);
      }
   }

   public void fromBytes(ByteBuf buf) {
      this.id = buf.readInt();
   }

   public void toBytes(ByteBuf buf) {
      buf.writeInt(this.id);
   }

   static {
      Set keys = Aspect.aspects.keySet();
      Iterator it = keys.iterator();
      AspectList list = new AspectList();

      while(it.hasNext()) {
         list.add((Aspect)Aspect.aspects.get(it.next()), 1);
      }

      sorted = list.getAspectsSorted();
   }
}
