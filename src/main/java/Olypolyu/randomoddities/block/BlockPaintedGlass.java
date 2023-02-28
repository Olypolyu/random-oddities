package Olypolyu.randomoddities.block;

import net.minecraft.src.BlockGlass;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.helper.Colors;

public class BlockPaintedGlass extends BlockGlass {

    public BlockPaintedGlass(int i, Material material, boolean flag) {
        super(i, material, flag);
    }

    public int colorMultiplier(World world, IBlockAccess iblockaccess, int i, int j, int k) {
        int meta = world.getBlockMetadata(i, j, k);
        return this.getRenderColor(meta);
    }

    public int getRenderColor(int i) {
        try {
            return Colors.allPlankColors[i % 16].getARGB();
        } catch (Exception var3) {
            return 16711935;
        }
    }

    public int getRenderBlockPass() {
        return 0;
    }

}
