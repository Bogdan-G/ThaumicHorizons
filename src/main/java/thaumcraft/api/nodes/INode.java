package thaumcraft.api.nodes;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;

public interface INode extends IAspectContainer {

   String getId();

   AspectList getAspectsBase();

   NodeType getNodeType();

   void setNodeType(NodeType var1);

   void setNodeModifier(NodeModifier var1);

   NodeModifier getNodeModifier();

   int getNodeVisBase(Aspect var1);

   void setNodeVisBase(Aspect var1, short var2);
}
