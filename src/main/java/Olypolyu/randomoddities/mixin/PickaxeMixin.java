package Olypolyu.randomoddities.mixin;

import net.minecraft.src.*;
import net.minecraft.src.material.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemToolPickaxe.class, remap = false)
public class PickaxeMixin extends ItemTool {
    protected PickaxeMixin(int i, int j, ToolMaterial toolMaterial, Material[] materialsEffectiveAgainst) {
        super(i, j, toolMaterial, materialsEffectiveAgainst);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, double heightPlaced) {

        int torchAt;
        boolean hasTorches = false;
        for ( torchAt = 0; torchAt < entityplayer.inventory.getSizeInventory(); torchAt++) {
            if ( entityplayer.inventory.getStackInSlot(torchAt) != null && entityplayer.inventory.getStackInSlot(torchAt).itemID == Block.torchCoal.blockID ) {
                hasTorches = true;
                break;
            }
        }

        // return false if the for loop does not return anything.
        if (!hasTorches) return false;

        //place on the side of block
        switch (l) {
            default:
                return false;

            case 0:
                --j;
                break;

            case 1:
                ++j;
                break;

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

        if ( world.canBlockBePlacedAt(Block.torchCoal.blockID, i, j, k, false, l) ) {
            //take one torch from player, if there's 0 torches destroy the stack.
            entityplayer.inventory.getStackInSlot(torchAt).consumeItem(entityplayer);
            if (entityplayer.inventory.getStackInSlot(torchAt).stackSize < 1) entityplayer.inventory.setInventorySlotContents(torchAt, null);

            // place block and play audio
            world.setBlockAndMetadataWithNotify(i, j, k, Block.torchCoal.blockID, l);
            world.playSoundEffect((i + 0.5F), (j + 0.5F), (k + 0.5F), Block.getBlock(Block.torchCoal.blockID).stepSound.func_1145_d(), ( Block.getBlock(Block.torchCoal.blockID).stepSound.getVolume() + 1.0F) / 2.0F, Block.getBlock(Block.torchCoal.blockID).stepSound.getPitch() * 0.8F);
            return true;
        }
        return false;
    }
}
