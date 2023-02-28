package Olypolyu.randomoddities.mixin;

import Olypolyu.randomoddities.RandomOddities;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ContainerPlayerCreative.class, remap = false)
public abstract class ContainerPlayerCreativeMixin extends ContainerPlayer {
    public ContainerPlayerCreativeMixin(InventoryPlayer inventoryplayer) {
        super(inventoryplayer);
    }

    @Shadow
    public static int creativeItemsCount;

    @Shadow
    public static ItemStack[] creativeItems;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectPaintedGlass(CallbackInfo ci) {
        int glassCount = 0;
        ItemStack[] glasses = new ItemStack[16];

        // create array with glass
        for (int j = 1; j < 16; j++) {
            glasses[glassCount] = new ItemStack(RandomOddities.paintedGlass.blockID, 1, j);
            glassCount++;
        }

        // create copy of the creativeItems array, can't do directly.
        ItemStack[] creativeItemsNew = new ItemStack[creativeItems.length];
        System.arraycopy(creativeItems, 0 , creativeItemsNew, 0, creativeItemsCount);

        // go through the until we find the glass block
        for (int index = 0; index < creativeItemsCount; index++) {
            if (creativeItems[index].getItem().itemID == Block.glass.blockID) {

                // insert the painted glasses into the array.
                for (int x = 0; x < (glassCount); x++) creativeItemsNew[index + 1 + x] = glasses[x];

                // continue the array as normal.
                int indexGlass = index + glassCount;
                System.arraycopy(creativeItems, index + 1, creativeItemsNew, indexGlass + 1, creativeItemsCount);
            }
        }

        // set the actual values.
        creativeItems = creativeItemsNew;
        creativeItemsCount = creativeItemsCount + glassCount;
    }


}
