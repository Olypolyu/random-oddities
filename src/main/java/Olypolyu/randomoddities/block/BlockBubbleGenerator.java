package Olypolyu.randomoddities.block;

import Olypolyu.randomoddities.entity.TileEntityBubbleColumn;
import net.minecraft.src.*;

public class BlockBubbleGenerator extends BlockContainer {
    public BlockBubbleGenerator(int i, Material material) {
        super(i, material);
    }

    protected TileEntity getBlockEntity() {
        return new TileEntityBubbleColumn();
        }
}

