package Olypolyu.randomoddities.mixin;

import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin( value = CraftingManager.class, remap = false)

public interface ReparableRecipeMixin {
    @Invoker("addRepairableStackableRecipe")
    void callAddRepairableStackableRecipe(Item inItem, ItemStack materialStack);

}
