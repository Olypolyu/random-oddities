package Olypolyu.randomoddities.mixin;

import Olypolyu.randomoddities.RandomOddities;
import Olypolyu.randomoddities.block.BlockCocoaBeans;

import net.minecraft.src.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemDye.class, remap = false)
public class ItemDyeMixin{

    @Inject(method = "onItemUse", at = @At("TAIL"))
    private boolean callOnItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, double heightPlaced, CallbackInfoReturnable<Boolean> info){

        if (itemstack.getMetadata() == 3) {
            if ( BlockCocoaBeans.growsOn.contains( world.getBlockId(i, j, k) ) ) {

                switch (l) {
                    default:
                        return false;

                    case 2:
                        --k;
                        break;

                    case 3:
                        ++k;
                        break;

                    case 4:
                        --i;
                        break;

                    case 5:
                        ++i;
                        break;
                    }

                if ( world.canBlockBePlacedAt(RandomOddities.cocoaBeans.blockID, i, j, k, false, l) ) {
                    itemstack.consumeItem(entityplayer);
                    world.setBlockAndMetadataWithNotify(i, j, k, RandomOddities.cocoaBeans.blockID, l);
                    return true;
                    }
                }
            }

        if (itemstack.getMetadata() == 15 && Block.getBlock( world.getBlockId(i, j, k) ) == RandomOddities.cocoaBeans) {
            int metadata = world.getBlockMetadata(i, j, k);
            int side = metadata & 0b1111;
            int growthStage = metadata >> 4;

            if ( growthStage >= BlockCocoaBeans.maxGrowth) return false;

            world.setBlockAndMetadataWithNotify(i, j, k, RandomOddities.cocoaBeans.blockID,( (growthStage + 1) << 4 )  + side  );
            itemstack.consumeItem(entityplayer);
            return true;
        }


        // I SURE HOPE THIS DOESNT RUINS ANYONE ELSE'S DAAAAAAAAAY
        return false;
        }

}
