package Olypolyu.randomoddities.mixin;

import Olypolyu.randomoddities.RandomOddities;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RenderBlocks.class, remap = false)
public class RenderMixin {

    @Shadow
    private IBlockAccess blockAccess;

    @Shadow
    private int overrideBlockTexture;

    @Shadow
    private boolean renderAllFaces;

    public boolean renderThicccCake(Block block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.instance;

        tessellator.drawRectangle(i, j, k, 1);
        tessellator.setTextureUV(1,1);

        return true;
    }

    public boolean renderBlockCocoaBeans(Block block, int i, int j, int k) {
        Tessellator tessellator = Tessellator.instance;
        int l = block.getBlockTextureFromSideAndMetadata(0, blockAccess.getBlockMetadata(i, j, k));

        if (this.overrideBlockTexture >= 0) {
            l = this.overrideBlockTexture;
        }

        float f = block.getBlockBrightness(this.blockAccess, i, j, k);
        tessellator.setColorOpaque_F(f, f, f);
        int i1 = l % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        int j1 = l / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        double d = (double)((float)i1 / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        double d1 = (double)(((float)i1 + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        double d2 = (double)((float)j1 / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        double d3 = (double)(((float)j1 + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        int k1 = this.blockAccess.getBlockMetadata(i, j, k) & 0b1111;
        float f1 = 0.0F;
        float f2 = 0.05F;

        if (k1 == 5) {
            tessellator.addVertexWithUV((double)((float)i + f2), (double)((float)(j + 1) + f1), (double)((float)(k + 1) + f1), d, d2);
            tessellator.addVertexWithUV((double)((float)i + f2), (double)((float)(j + 0) - f1), (double)((float)(k + 1) + f1), d, d3);
            tessellator.addVertexWithUV((double)((float)i + f2), (double)((float)(j + 0) - f1), (double)((float)(k + 0) - f1), d1, d3);
            tessellator.addVertexWithUV((double)((float)i + f2), (double)((float)(j + 1) + f1), (double)((float)(k + 0) - f1), d1, d2);
        }

        if (k1 == 4) {
            tessellator.addVertexWithUV((double)((float)(i + 1) - f2), (double)((float)(j + 0) - f1), (double)((float)(k + 1) + f1), d1, d3);
            tessellator.addVertexWithUV((double)((float)(i + 1) - f2), (double)((float)(j + 1) + f1), (double)((float)(k + 1) + f1), d1, d2);
            tessellator.addVertexWithUV((double)((float)(i + 1) - f2), (double)((float)(j + 1) + f1), (double)((float)(k + 0) - f1), d, d2);
            tessellator.addVertexWithUV((double)((float)(i + 1) - f2), (double)((float)(j + 0) - f1), (double)((float)(k + 0) - f1), d, d3);
        }

        if (k1 == 3) {
            tessellator.addVertexWithUV((double)((float)(i + 1) + f1), (double)((float)(j + 0) - f1), (double)((float)k + f2), d1, d3);
            tessellator.addVertexWithUV((double)((float)(i + 1) + f1), (double)((float)(j + 1) + f1), (double)((float)k + f2), d1, d2);
            tessellator.addVertexWithUV((double)((float)(i + 0) - f1), (double)((float)(j + 1) + f1), (double)((float)k + f2), d, d2);
            tessellator.addVertexWithUV((double)((float)(i + 0) - f1), (double)((float)(j + 0) - f1), (double)((float)k + f2), d, d3);
        }

        if (k1 == 2) {
            tessellator.addVertexWithUV((double)((float)(i + 1) + f1), (double)((float)(j + 1) + f1), (double)((float)(k + 1) - f2), d, d2);
            tessellator.addVertexWithUV((double)((float)(i + 1) + f1), (double)((float)(j + 0) - f1), (double)((float)(k + 1) - f2), d, d3);
            tessellator.addVertexWithUV((double)((float)(i + 0) - f1), (double)((float)(j + 0) - f1), (double)((float)(k + 1) - f2), d1, d3);
            tessellator.addVertexWithUV((double)((float)(i + 0) - f1), (double)((float)(j + 1) + f1), (double)((float)(k + 1) - f2), d1, d2);
        }

        return true;
    }

    @Inject(method = "renderBlockByRenderType", at = @At("TAIL"), cancellable = true)
    public boolean renderBlockByRenderType(Block block, int i, int j, int k, CallbackInfoReturnable<Boolean> info) {
        int renderType = block.getRenderType();

        if (renderType == RandomOddities.cocoaBeans.blockID)
            return this.renderThicccCake(block, i, j, k);

        return true;
    }

}
