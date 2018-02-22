package thaumcraft.api.aspects;

import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;

public interface IEssentiaTransport {

   boolean isConnectable(ForgeDirection var1);

   boolean canInputFrom(ForgeDirection var1);

   boolean canOutputTo(ForgeDirection var1);

   void setSuction(Aspect var1, int var2);

   Aspect getSuctionType(ForgeDirection var1);

   int getSuctionAmount(ForgeDirection var1);

   int takeEssentia(Aspect var1, int var2, ForgeDirection var3);

   int addEssentia(Aspect var1, int var2, ForgeDirection var3);

   Aspect getEssentiaType(ForgeDirection var1);

   int getEssentiaAmount(ForgeDirection var1);

   int getMinimumSuction();

   boolean renderExtendedTube();
}
