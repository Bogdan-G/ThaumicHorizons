package thaumcraft.api.visnet;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.WorldCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.TileVisNode;

public class VisNetHandler {

   public static HashMap sources = new HashMap();
   static ArrayList cache = new ArrayList();
   private static HashMap nearbyNodes = new HashMap();


   public static int drainVis(World world, int x, int y, int z, Aspect aspect, int amount) {
      int drainedAmount = 0;
      WorldCoordinates drainer = new WorldCoordinates(x, y, z, world.provider.dimensionId);
      if(!nearbyNodes.containsKey(drainer)) {
         calculateNearbyNodes(world, x, y, z);
      }

      ArrayList nodes = (ArrayList)nearbyNodes.get(drainer);
      if(nodes != null && nodes.size() > 0) {
         Iterator var9 = nodes.iterator();

         while(var9.hasNext()) {
            WeakReference noderef = (WeakReference)var9.next();
            TileVisNode node = (TileVisNode)noderef.get();
            if(node != null) {
               int a = node.consumeVis(aspect, amount);
               drainedAmount += a;
               amount -= a;
               if(a > 0) {
                  int color = Aspect.getPrimalAspects().indexOf(aspect);
                  generateVisEffect(world.provider.dimensionId, x, y, z, node.xCoord, node.yCoord, node.zCoord, color);
               }

               if(amount <= 0) {
                  break;
               }
            }
         }
      }

      return drainedAmount;
   }

   public static void generateVisEffect(int dim, int x, int y, int z, int x2, int y2, int z2, int color) {
      ThaumcraftApi.internalMethods.generateVisEffect(dim, x, y, z, x2, y2, z2, color);
   }

   public static void addSource(World world, TileVisNode vs) {
      HashMap sourcelist = (HashMap)sources.get(Integer.valueOf(world.provider.dimensionId));
      if(sourcelist == null) {
         sourcelist = new HashMap();
      }

      sourcelist.put(vs.getLocation(), new WeakReference(vs));
      sources.put(Integer.valueOf(world.provider.dimensionId), sourcelist);
      nearbyNodes.clear();
   }

   public static boolean isNodeValid(WeakReference node) {
      return node != null && node.get() != null && !((TileVisNode)node.get()).isInvalid();
   }

   public static WeakReference addNode(World world, TileVisNode vn) {
      WeakReference ref = new WeakReference(vn);
      HashMap sourcelist = (HashMap)sources.get(Integer.valueOf(world.provider.dimensionId));
      if(sourcelist == null) {
         new HashMap();
         return null;
      } else {
         ArrayList nearby = new ArrayList();
         Iterator dist = sourcelist.values().iterator();

         while(dist.hasNext()) {
            WeakReference closest = (WeakReference)dist.next();
            if(isNodeValid(closest)) {
               TileVisNode source = (TileVisNode)closest.get();
               float o = inRange(world, vn.getLocation(), source.getLocation(), vn.getRange());
               if(o > 0.0F) {
                  nearby.add(new Object[]{source, Float.valueOf(o - (float)(vn.getRange() * 2))});
               }

               nearby = findClosestNodes(vn, source, nearby);
               cache.clear();
            }
         }

         float dist1 = Float.MAX_VALUE;
         TileVisNode closest1 = null;
         if(nearby.size() > 0) {
            Iterator source1 = nearby.iterator();

            while(source1.hasNext()) {
               Object[] o1 = (Object[])source1.next();
               if(((Float)o1[1]).floatValue() < dist1 && (vn.getAttunement() == -1 || ((TileVisNode)o1[0]).getAttunement() == -1 || vn.getAttunement() == ((TileVisNode)o1[0]).getAttunement()) && canNodeBeSeen(vn, (TileVisNode)o1[0])) {
                  dist1 = ((Float)o1[1]).floatValue();
                  closest1 = (TileVisNode)o1[0];
               }
            }
         }

         if(closest1 != null) {
            closest1.getChildren().add(ref);
            nearbyNodes.clear();
            return new WeakReference(closest1);
         } else {
            return null;
         }
      }
   }

   public static ArrayList findClosestNodes(TileVisNode target, TileVisNode parent, ArrayList in) {
      if(cache.size() <= 512 && !cache.contains(new WorldCoordinates(parent))) {
         cache.add(new WorldCoordinates(parent));
         Iterator var3 = parent.getChildren().iterator();

         while(var3.hasNext()) {
            WeakReference childWR = (WeakReference)var3.next();
            TileVisNode child = (TileVisNode)childWR.get();
            if(child != null && !child.equals(target) && !child.equals(parent)) {
               float r2 = inRange(child.getWorldObj(), child.getLocation(), target.getLocation(), target.getRange());
               if(r2 > 0.0F) {
                  in.add(new Object[]{child, Float.valueOf(r2)});
               }

               in = findClosestNodes(target, child, in);
            }
         }

         return in;
      } else {
         return in;
      }
   }

   private static float inRange(World world, WorldCoordinates cc1, WorldCoordinates cc2, int range) {
      float distance = cc1.getDistanceSquaredToWorldCoordinates(cc2);
      return distance > (float)(range * range)?-1.0F:distance;
   }

   private static void calculateNearbyNodes(World world, int x, int y, int z) {
      HashMap sourcelist = (HashMap)sources.get(Integer.valueOf(world.provider.dimensionId));
      if(sourcelist == null) {
         new HashMap();
      } else {
         ArrayList cn = new ArrayList();
         WorldCoordinates drainer = new WorldCoordinates(x, y, z, world.provider.dimensionId);
         new ArrayList();
         Iterator var8 = sourcelist.values().iterator();

         while(var8.hasNext()) {
            WeakReference root = (WeakReference)var8.next();
            if(isNodeValid(root)) {
               TileVisNode source = (TileVisNode)root.get();
               TileVisNode closest = null;
               float range = Float.MAX_VALUE;
               float r = inRange(world, drainer, source.getLocation(), source.getRange());
               if(r > 0.0F) {
                  range = r;
                  closest = source;
               }

               ArrayList children = new ArrayList();
               children = getAllChildren(source, children);
               Iterator var15 = children.iterator();

               while(var15.hasNext()) {
                  WeakReference child = (WeakReference)var15.next();
                  TileVisNode n = (TileVisNode)child.get();
                  if(n != null && !n.equals(root)) {
                     float r2 = inRange(n.getWorldObj(), n.getLocation(), drainer, n.getRange());
                     if(r2 > 0.0F && r2 < range) {
                        range = r2;
                        closest = n;
                     }
                  }
               }

               if(closest != null) {
                  cn.add(new WeakReference(closest));
               }
            }
         }

         nearbyNodes.put(drainer, cn);
      }
   }

   private static ArrayList getAllChildren(TileVisNode source, ArrayList list) {
      Iterator var2 = source.getChildren().iterator();

      while(var2.hasNext()) {
         WeakReference child = (WeakReference)var2.next();
         TileVisNode n = (TileVisNode)child.get();
         if(n != null) {
            list.add(child);
            list = getAllChildren(n, list);
         }
      }

      return list;
   }

   public static boolean canNodeBeSeen(TileVisNode source, TileVisNode target) {
      MovingObjectPosition mop = ThaumcraftApiHelper.rayTraceIgnoringSource(source.getWorldObj(), Vec3.createVectorHelper((double)source.xCoord + 0.5D, (double)source.yCoord + 0.5D, (double)source.zCoord + 0.5D), Vec3.createVectorHelper((double)target.xCoord + 0.5D, (double)target.yCoord + 0.5D, (double)target.zCoord + 0.5D), false, true, false);
      return mop == null || mop.typeOfHit == MovingObjectType.BLOCK && mop.blockX == target.xCoord && mop.blockY == target.yCoord && mop.blockZ == target.zCoord;
   }

}
