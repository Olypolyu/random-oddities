package Olypolyu.randomoddities.mixin;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFenceGate;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockFenceGate.class, remap = false)
public class BlockFenceGateMixin extends Block {

    public BlockFenceGateMixin(int i, Material material) {
        super(i, material);
    }

    @Override
    public void onEntityWalking(World world, int i, int j, int k, Entity entity) {

        if (world.getBlockId(i, j, k) == Block.farmlandDirt.blockID) {
            if (world.rand.nextInt(4) == 0 && entity instanceof EntityPlayer) {

                if (((EntityPlayer) entity).inventory.armorInventory[0] != null && ((EntityPlayer) entity).inventory.armorInventory[0].getItem() == Item.armorBootsLeather) {
                    return;
                }

                world.setBlockWithNotify(i, j, k, Block.dirt.blockID);
            }
        }

    }


}