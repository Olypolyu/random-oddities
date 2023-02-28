package Olypolyu.randomoddities.block;

import Olypolyu.randomoddities.RandomOddities;
import Olypolyu.randomoddities.entity.TileEntityResizableChest;
import net.minecraft.src.*;
import net.minecraft.src.helper.Direction;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.Random;

public class BlockResizableChest extends BlockContainer {
    private final Random random = new Random();
    private final int chestSize;

    private final int[] top;
    private final int[] side;
    private final int[] front;

    public BlockResizableChest(int i, Material material, int chestSize, String top, String side, String front) {
        super(i, material);
        this.chestSize = chestSize;
        this.top = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, top);
        this.side = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, side);
        this.front = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, front);
    }

    public void onBlockRemoval(World world, int i, int j, int k) {
        TileEntityResizableChest tileentityironchest = (TileEntityResizableChest) world.getBlockTileEntity(i, j, k);

        for (int l = 0; l < tileentityironchest.getSizeInventory(); ++l) {
            ItemStack itemstack = tileentityironchest.getStackInSlot(l);
            if (itemstack != null) {
                float f = this.random.nextFloat() * 0.8F + 0.1F;
                float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                while (itemstack.stackSize > 0) {
                    int i1 = this.random.nextInt(21) + 10;
                    if (i1 > itemstack.stackSize) {
                        i1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= i1;
                    EntityItem entityitem = new EntityItem(world, (float) i + f, (float) j + f1, (float) k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getMetadata()));
                    float f3 = 0.05F;
                    entityitem.motionX = (float) this.random.nextGaussian() * f3;
                    entityitem.motionY = (float) this.random.nextGaussian() * f3 + 0.4F;
                    entityitem.motionZ = (float) this.random.nextGaussian() * f3;
                    world.entityJoinedWorld(entityitem);
                }
            }
        }
        super.onBlockRemoval(world, i, j, k);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        TileEntityResizableChest chest = (TileEntityResizableChest) world.getBlockTileEntity(i, j, k);
        if (!world.isMultiplayerAndNotHost) {
            entityplayer.displayGUIChest(chest);
        }
        return true;
    }

    @Override
    public void onBlockPlaced(World world, int x, int y, int z, Direction side, EntityLiving player, double sideHeight) {
        int metadata = player.getHorizontalPlacementDirection(side).getOpposite().index;
        world.setBlockMetadataWithNotify(x, y, z, metadata);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        if (side == 0 || side == 1)
            return texCoordToIndex(this.top[0], this.top[1]);
        else {

            // block's facing :
            switch (meta) {

                //north
                case 0:
                    if (side == 3)
                         return texCoordToIndex(this.front[0], this.front[1]);
                    else return texCoordToIndex(this.side[0], this.side[1]);

                //east
                case 1:
                    if (side == 4)
                         return texCoordToIndex(this.front[0], this.front[1]);
                    else return texCoordToIndex(this.side[0], this.side[1]);

                //south
                case 2:
                    if (side == 2)
                         return texCoordToIndex(this.front[0], this.front[1]);
                    else return texCoordToIndex(this.side[0], this.side[1]);

                //west
                default:
                    if (side == 5)
                         return texCoordToIndex(this.front[0], this.front[1]);
                    else return texCoordToIndex(this.side[0], this.side[1]);
                }
            }

    }

    // Sides           Metadata
    // 0 - bottom      0 - north
    // 1 - top         1 - east
    // 2 - north       2 - south
    // 3 - south       3 - west
    // 4 - west        4 - up
    // 5 - east        5 - down




    protected TileEntity getBlockEntity() {
        TileEntityResizableChest chest = new TileEntityResizableChest();
        chest.SetTileEntityResizableChest(this.chestSize);
        return chest;
    }
}
