package Olypolyu.randomoddities.block;

import net.minecraft.src.*;

public class BlockMinecartLoaderRail extends BlockMinecartUnloaderRail {

    protected final int maxCoolDown = 25;
    protected final int extractionRate = 1;

    private int coolDown = 0;

    public BlockMinecartLoaderRail(int i, boolean flag) {
        super(i, flag);
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j) {
        return this.atlasIndices[i];
    }


    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return (iblockaccess.getBlockMetadata(i, j, k) & 8) != 0;
    }

    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
        if ((world.getBlockMetadata(i, j, k) & 8) == 0) {
            return false;
        } else {
            return l == 1;
        }
    }

    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {

        if (entity instanceof EntityMinecart && ((EntityMinecart) entity).minecartType == 1) {

            // coolDown so we don't unload the ENTIRE cart at once.
            if (coolDown <= 0) {

                // get stuff from chest
                TileEntityChest chest = findChest(world, i, j, k);

                if (chest != null) {
                    ItemStack itemStack = getItemFromChest(chest);

                    // put stuff into minecart
                    if (itemStack != null) {
                        boolean flag = putInChestMinecart((EntityMinecart) entity, itemStack);

                        // drop items if it cannot put item into minecart
                        if (!flag)
                            world.dropItem(i, j, k, new ItemStack(itemStack.getItem(), extractionRate, itemStack.getMetadata()));
                    }
                }
                coolDown = maxCoolDown;
            } else
                coolDown--;

        }

    }

    private ItemStack getItemFromChest( TileEntityChest chest) {

        ItemStack itemStack = null;
        int invSize = chest.getSizeInventory();
        int invSlot;

        // picks up first available stack
        for (invSlot = 0; invSlot < invSize; invSlot++) {
            itemStack = chest.getStackInSlot(invSlot);

            // break if we have something
            if (itemStack != null) break;
        }

        if (itemStack != null) {
            // decrease stack by the extraction rate then drop the item.
            chest.decrStackSize(invSlot, extractionRate);
        }

        return itemStack;
    }

    private boolean putInChestMinecart( EntityMinecart chestMinecart, ItemStack itemstack) {
        int chestInvSlot;

        // looks up the chest's inventory for a place to place the item.
        for (chestInvSlot = 0; chestInvSlot < chestMinecart.getSizeInventory(); chestInvSlot++) {

            if (chestMinecart.getStackInSlot(chestInvSlot) == null) {
                chestMinecart.setInventorySlotContents(chestInvSlot, new ItemStack(itemstack.getItem(), extractionRate, itemstack.getMetadata()));
                return true;
            }

            if (chestMinecart.getStackInSlot(chestInvSlot).getItem() == itemstack.getItem() && chestMinecart.getStackInSlot(chestInvSlot).getMetadata() == itemstack.getMetadata() && chestMinecart.getStackInSlot(chestInvSlot).stackSize != chestMinecart.getStackInSlot(chestInvSlot).getMaxStackSize()) {
                chestMinecart.setInventorySlotContents(chestInvSlot, new ItemStack(itemstack.getItem(), chestMinecart.getStackInSlot(chestInvSlot).stackSize + extractionRate, itemstack.getMetadata()));
                return true;
            }

        }

        return false;
    }


}
