package Olypolyu.randomoddities.block;

import net.minecraft.src.*;

public class BlockMinecartUnloaderRail extends BlockRail {

    protected final int maxCoolDown = 25;
    protected final int extractionRate = 1;

    private int coolDown = 0;

    public BlockMinecartUnloaderRail(int i, boolean flag) {
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

                ItemStack itemstack = null;
                int invSize = ((EntityMinecart) entity).getSizeInventory();
                int invSlot;

                // picks up first available stack
                for (invSlot = 0; invSlot < invSize; invSlot++) {
                    itemstack = ((EntityMinecart) entity).getStackInSlot(invSlot);

                    // break if we have something
                    if (itemstack != null) break;
                }

                if (itemstack != null) {
                    // decrease stack by the extraction rate then drop the item.
                    ((EntityMinecart) entity).decrStackSize(invSlot, extractionRate);

                    TileEntityChest chest = findChest(world, i, j, k);

                    if (chest != null) {
                        boolean flag = putInChest(chest, itemstack);

                        // drop items if it cannot put item into chest
                        if (!flag) {
                            EntityItem entityitem = new EntityItem(world, i + 0.5, j - 1, k + 0.5, new ItemStack(itemstack.itemID, 1, itemstack.getMetadata()));
                            world.entityJoinedWorld(entityitem);
                        }

                    } else {
                        EntityItem entityitem = new EntityItem(world, i + 0.5, j - 1, k + 0.5, new ItemStack(itemstack.itemID, 1, itemstack.getMetadata()));
                        world.entityJoinedWorld(entityitem);
                        }

                    coolDown = maxCoolDown;
                }
            } else
                coolDown--;

        }

    }

    public TileEntityChest findChest(World world, int i, int j, int k) {
        int xOffset = 1;
        int zOffset = 1;

        // look if there's a chest adjacent to the rail block
        int adjacentId = world.getBlockId(i, j, k + zOffset);
        if (Block.blocksList[adjacentId] instanceof BlockChest)
            return (TileEntityChest) world.getBlockTileEntity(i, j, k + zOffset);

        adjacentId = world.getBlockId(i, j, k - zOffset);
        if (Block.blocksList[adjacentId] instanceof BlockChest)
            return (TileEntityChest) world.getBlockTileEntity(i, j, k - zOffset);

        adjacentId = world.getBlockId(i + xOffset, j, k);
        if (Block.blocksList[adjacentId] instanceof BlockChest)
            return (TileEntityChest) world.getBlockTileEntity(i + xOffset, j, k);

        adjacentId = world.getBlockId(i - xOffset, j, k);
        if (Block.blocksList[adjacentId] instanceof BlockChest)
            return (TileEntityChest) world.getBlockTileEntity(i - xOffset, j, k);

        return null;
    }

    public boolean putInChest( TileEntityChest chest, ItemStack itemstack) {
        int chestInvSlot;

        // looks up the chest's inventory for a place to place the item.
        for (chestInvSlot = 0; chestInvSlot < chest.getSizeInventory(); chestInvSlot++) {

            if (chest.getStackInSlot(chestInvSlot) == null) {
                chest.setInventorySlotContents(chestInvSlot, new ItemStack(itemstack.getItem(), extractionRate, itemstack.getMetadata()));
                return true;
            }

            if (chest.getStackInSlot(chestInvSlot).getItem() == itemstack.getItem() && chest.getStackInSlot(chestInvSlot).getMetadata() == itemstack.getMetadata() && chest.getStackInSlot(chestInvSlot).stackSize != chest.getStackInSlot(chestInvSlot).getMaxStackSize()) {
                chest.setInventorySlotContents(chestInvSlot, new ItemStack(itemstack.getItem(), chest.getStackInSlot(chestInvSlot).stackSize + extractionRate, itemstack.getMetadata()));
                return true;
            }

        }

        return false;
    }


}
