package Olypolyu.randomoddities.mixin;

import Olypolyu.randomoddities.RandomOddities;
import net.minecraft.src.Block;
import net.minecraft.src.BlockRail;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockRail.class, remap = false)
public class BlockRailMixin {

    @Inject(method = "isRailBlockAt", at = @At("TAIL"))
    private static boolean isRailBlockAt(World world, int i, int j, int k, CallbackInfoReturnable<Boolean> info) {
        int l = world.getBlockId(i, j, k);

        if ( l == RandomOddities.unLoaderRail.blockID ) { return true; }
        if ( l == RandomOddities.loaderRail.blockID ) { return true; }

        return l == Block.rail.blockID || l == Block.railPowered.blockID || l == Block.railDetector.blockID;
    }

    @Inject(method = "isRailBlock", at = @At("TAIL"))
    private static boolean isRailBlock(int i, CallbackInfoReturnable<Boolean> info) {

        if ( i == RandomOddities.unLoaderRail.blockID ) { return true; }
        if ( i == RandomOddities.loaderRail.blockID ) { return true; }

        return i == Block.rail.blockID || i == Block.railPowered.blockID || i == Block.railDetector.blockID;
    }

}
