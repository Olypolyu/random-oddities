package Olypolyu.randomoddities.mixin;

import Olypolyu.randomoddities.entity.EntityBoar;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BiomeGenRainforest;
import net.minecraft.src.SpawnListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BiomeGenRainforest.class, remap = false)
public class BoarGenMixin extends BiomeGenBase {

    private BoarGenMixin(int id) {
        super(id);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void BiomeGenRainforest(int id, CallbackInfo ci){
        this.spawnableCreatureList.add(new SpawnListEntry(EntityBoar.class, 102));
    }

}
