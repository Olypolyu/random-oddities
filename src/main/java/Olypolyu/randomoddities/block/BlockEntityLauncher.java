package Olypolyu.randomoddities.block;

import Olypolyu.randomoddities.entity.TileEntityLauncher;
import net.minecraft.src.BlockContainerRotatable;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockEntityLauncher extends BlockContainerRotatable {

    private final double launchX;
    private final double launchY;
    private final double launchZ;

    public BlockEntityLauncher(int id, Material material, double launchX, double launchY, double launchZ) {
        super(id, material);
        this.setBlockBounds(0F, 0F, 0F, 1.0F, 0.5F, 1.0F);
        this.setLightOpacity(1);
        this.launchX = launchX;
        this.launchY = launchY;
        this.launchZ = launchZ;

    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean canPlaceOnSurfaceOnCondition(World world, int x, int y, int z) {
        return false;
    }

    protected TileEntity getBlockEntity() {
        TileEntityLauncher launcher = new TileEntityLauncher();
        launcher.setLauncherParameters(this.launchX, this.launchY, this.launchZ);
        return launcher;
    }
}