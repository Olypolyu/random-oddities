package Olypolyu.randomoddities.block;

import net.minecraft.src.*;

public class BlockPlatform extends Block {
    public BlockPlatform(int i, Material material) {
        super(i, material);
        this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.setLightOpacity(1);
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
            if (entity.motionY > 0) {
                entity.setPosition(entity.posX, j + entity.height + 1, entity.posZ);
                entity.motionY = 0;
                }
    }

    public boolean isOpaqueCube() {
        return false;
    }

}
