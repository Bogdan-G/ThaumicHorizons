package thaumcraft.api.visnet;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.WorldCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.visnet.VisNetHandler;

public abstract class TileVisNode extends TileThaumcraft {

   WeakReference parent = null;
   ArrayList children = new ArrayList();
   protected int nodeCounter = 0;
   private boolean nodeRegged = false;
   public boolean nodeRefresh = false;


   public WorldCoordinates getLocation() {
      return new WorldCoordinates(this);
   }

   public abstract int getRange();

   public abstract boolean isSource();

   public int consumeVis(Aspect aspect, int vis) {
      if(VisNetHandler.isNodeValid(this.getParent())) {
         int out = ((TileVisNode)this.getParent().get()).consumeVis(aspect, vis);
         if(out > 0) {
            this.triggerConsumeEffect(aspect);
         }

         return out;
      } else {
         return 0;
      }
   }

   public void removeThisNode() {
      Iterator sourcelist = this.getChildren().iterator();

      while(sourcelist.hasNext()) {
         WeakReference n = (WeakReference)sourcelist.next();
         if(n != null && n.get() != null) {
            ((TileVisNode)n.get()).removeThisNode();
         }
      }

      this.children = new ArrayList();
      if(VisNetHandler.isNodeValid(this.getParent())) {
         ((TileVisNode)this.getParent().get()).nodeRefresh = true;
      }

      this.setParent((WeakReference)null);
      this.parentChanged();
      if(this.isSource()) {
         HashMap sourcelist1 = (HashMap)VisNetHandler.sources.get(Integer.valueOf(super.worldObj.provider.dimensionId));
         if(sourcelist1 == null) {
            sourcelist1 = new HashMap();
         }

         sourcelist1.remove(this.getLocation());
         VisNetHandler.sources.put(Integer.valueOf(super.worldObj.provider.dimensionId), sourcelist1);
      }

      super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
   }

   public void invalidate() {
      this.removeThisNode();
      super.invalidate();
   }

   public void triggerConsumeEffect(Aspect aspect) {}

   public WeakReference getParent() {
      return this.parent;
   }

   public WeakReference getRootSource() {
      return VisNetHandler.isNodeValid(this.getParent())?((TileVisNode)this.getParent().get()).getRootSource():(this.isSource()?new WeakReference(this):null);
   }

   public void setParent(WeakReference parent) {
      this.parent = parent;
   }

   public ArrayList getChildren() {
      return this.children;
   }

   public boolean canUpdate() {
      return true;
   }

   public void updateEntity() {
      if(!super.worldObj.isRemote && (this.nodeCounter++ % 40 == 0 || this.nodeRefresh)) {
         Iterator var1;
         WeakReference n;
         if(!this.nodeRefresh && this.children.size() > 0) {
            var1 = this.children.iterator();

            while(var1.hasNext()) {
               n = (WeakReference)var1.next();
               if(n == null || n.get() == null || !VisNetHandler.canNodeBeSeen(this, (TileVisNode)n.get())) {
                  this.nodeRefresh = true;
                  break;
               }
            }
         }

         if(this.nodeRefresh) {
            var1 = this.children.iterator();

            while(var1.hasNext()) {
               n = (WeakReference)var1.next();
               if(n.get() != null) {
                  ((TileVisNode)n.get()).nodeRefresh = true;
               }
            }

            this.children.clear();
            this.parent = null;
         }

         if(this.isSource() && !this.nodeRegged) {
            VisNetHandler.addSource(this.getWorldObj(), this);
            this.nodeRegged = true;
         } else if(!this.isSource() && !VisNetHandler.isNodeValid(this.getParent())) {
            this.setParent(VisNetHandler.addNode(this.getWorldObj(), this));
            this.nodeRefresh = true;
         }

         if(this.nodeRefresh) {
            super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.parentChanged();
         }

         this.nodeRefresh = false;
      }

   }

   public void parentChanged() {}

   public byte getAttunement() {
      return (byte)-1;
   }
}
