package Olypolyu.randomoddities.block;

import Olypolyu.randomoddities.RandomOddities;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockCake;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import turniplabs.halplibe.helper.TextureHelper;

public class BlockPumpkinPie extends BlockCake {
    private final int[] pieEaten = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, "pumpkinPieEaten.png");

    public BlockPumpkinPie(int i) {
        super(i);
        this.setTickOnLoad(true);
    }

    public void setBlockBoundsBasedOnState(World world, int i, int j, int k) {
        int slice = world.getBlockMetadata(i, j, k);
        float f = 0.0625F;
        float f1 = (1 + slice * 4) / 16.0F;
        this.setBlockBounds(f1, 0.0F, f, 1.0F - f, 0.3125F ,1.0F - f);
    }

    public void setBlockBoundsForItemRender() {
        float f = 0.0625F;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, 0.3125F, 1.0F - f);
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
        int slice = world.getBlockMetadata(i, j, k);
        float f = 0.0625F;
        float f1 = (1 + slice * 4) / 16.0F;
        float f2 = 0.3125F;
        return AxisAlignedBB.getBoundingBoxFromPool((float)i + f1, j, (float)k + f, (float)(i + 1) - f, (float)j + f2, (float)(k + 1) - f);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        this.eatSlice(world, i, j, k, entityplayer);
        return true;
    }

    private void eatSlice(World world, int i, int j, int k, EntityPlayer entityplayer) {
        if (entityplayer.health < 20) {
            entityplayer.heal(5);
            int l = world.getBlockMetadata(i, j, k) + 1;
            if (l >= 4) {
                world.setBlockWithNotify(i, j, k, 0);
            } else {
                world.setBlockMetadataWithNotify(i, j, k, l);
                world.markBlockAsNeedsUpdate(i, j, k);
            }
        }

    }

    public int getBlockTextureFromSideAndMetadata(int i, int j) {
        return j > 0 && i == 4 ? texCoordToIndex(this.pieEaten[0], this.pieEaten[1]) : this.atlasIndices[i];
    }

}
