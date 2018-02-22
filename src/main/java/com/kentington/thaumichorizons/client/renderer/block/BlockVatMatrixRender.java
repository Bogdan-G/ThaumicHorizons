package com.kentington.thaumichorizons.client.renderer.block;

import com.kentington.thaumichorizons.common.ThaumicHorizons;
import com.kentington.thaumichorizons.common.tiles.TileVatMatrix;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.block.BlockRenderer;

public class BlockVatMatrixRender extends BlockRenderer implements ISimpleBlockRenderingHandler {

   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
      GL11.glPushMatrix();
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      TileVatMatrix tc = new TileVatMatrix();
      tc.blockMetadata = metadata;
      TileEntityRendererDispatcher.instance.renderTileEntityAt(tc, 0.0D, 0.0D, 0.0D, 0.0F);
      GL11.glEnable('\u803a');
      GL11.glPopMatrix();
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      return false;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return ThaumicHorizons.blockVatMatrixRI;
   }
}
