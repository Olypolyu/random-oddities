package Olypolyu.randomoddities.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemShears.class, remap = false)
public class ItemShearsMixin extends Item{

    public ItemShearsMixin(int i) {
        super(i);
    }

    @Inject(method = "getStrVsBlock", at = @At("TAIL"))
    private float callGetStrVsBlock(ItemStack itemstack, Block block, CallbackInfoReturnable<Float> info){
        if (block.blockMaterial == Material.cloth) return 5.0F;
            else return super.getStrVsBlock(itemstack, block);
    }

}
