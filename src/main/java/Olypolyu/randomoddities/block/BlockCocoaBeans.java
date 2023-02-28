package Olypolyu.randomoddities.block;

import Olypolyu.randomoddities.RandomOddities;
import net.minecraft.src.*;
import net.minecraft.src.helper.Direction;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.ArrayList;
import java.util.Random;

public class BlockCocoaBeans extends Block {
    public static int maxGrowth = 4;

    private final int[][] vineTexture = new int[5][2];

    public BlockCocoaBeans(int i, Material material) {
        super(i, material);
        this.setTickOnLoad(true);
        this.notInCreativeMenu = true;

        for (int index = 0; index <= 4; index++)
            vineTexture[index] = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, "vines_" + index + ".png");
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        int side = world.getBlockMetadata(i, j, k) & 0b1111;
        int growthStage = world.getBlockMetadata(i, j, k) >> 4;

        if ( random.nextInt(150) == 0 && growthStage < maxGrowth )
            world.setBlockAndMetadataWithNotify(i, j, k, RandomOddities.cocoaBeans.blockID, ((growthStage + 1) << 4) | side);
    }

    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        int growthStage = meta >> 4;
        return texCoordToIndex(vineTexture[growthStage][0], vineTexture[growthStage][1]);
    }

    public static ArrayList<Integer> growsOn = new ArrayList<>();

    static {
        growsOn.add(Block.logOakMossy.blockID);
     /* growsOn.add(Block.logBirch.blockID);
        growsOn.add(Block.logCherry.blockID);
        growsOn.add(Block.logOak.blockID);
        growsOn.add(Block.logEucalyptus.blockID);
        growsOn.add(Block.logPine.blockID); */
        }

    public int getRenderType() {
        return this.blockID;
        }

    public boolean renderAsNormalBlock() {
        return false;
        }

    public boolean isOpaqueCube () {
        return false;
        }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance) {
        if ( (meta >> 4) > 1 )
            world.dropItem(x, y, z, new ItemStack(Item.dye, world.rand.nextInt(meta >> 4) + 1, 3));
        else
            world.dropItem(x, y, z, new ItemStack(Item.dye,1, 3));
        }

    public void onBlockPlaced(World world, int i, int j, int k, Direction side, EntityLiving player, double sideHeight) {

        // North
        if ( growsOn.contains( world.getBlockId( i, j, k + 1) ) )  {
            world.setBlockMetadataWithNotify(i, j, k,2);
            return;
        }

        // South
        if ( growsOn.contains( world.getBlockId( i, j, k + 1) ) )  {
            world.setBlockMetadataWithNotify(i, j, k,3);
            return;
        }

        // West
        if ( growsOn.contains( world.getBlockId( i, j, k + 1) ) )  {
            world.setBlockMetadataWithNotify(i, j, k,4);
            return;
            }

        // East
        if ( growsOn.contains( world.getBlockId( i, j, k + 1) ) )  {
            world.setBlockMetadataWithNotify(i, j, k,5);
            return;
            }

        this.dropBlockAsItem(world, i, j, k, 0);
        world.setBlockWithNotify(i, j, k, 0);
        }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        int side = world.getBlockMetadata(i, j, k) & 0b1111;
        switch (side){

            // North
            case 2:
                if ( !growsOn.contains( world.getBlockId(i, j, k + 1) ) ) {
                    world.setBlockWithNotify(i, j, k, 0);
                    this.dropBlockAsItem(world, i, j, k, 0);
                }
                break;

            // South
            case 3:
                if ( !growsOn.contains( world.getBlockId( i, j, k - 1 ) ) ) {
                    world.setBlockWithNotify(i, j, k, 0);
                    this.dropBlockAsItem(world, i, j, k, 0);
                }
                break;

            // West
            case 4:
                if ( !growsOn.contains( world.getBlockId( i + 1, j, k ) ) ) {
                    world.setBlockWithNotify(i, j, k, 0);
                    this.dropBlockAsItem(world, i, j, k, 0);
                    }
                break;

            // East
            case 5:
                if ( !growsOn.contains( world.getBlockId( i - 1, j, k ) ) ) {
                    world.setBlockWithNotify(i, j, k, 0);
                    this.dropBlockAsItem(world, i, j, k, 0);
                    }
                break;

            }
        }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
        }

    public void setBlockBoundsBasedOnState(World world, int i, int j, int k) {
        int side = world.getBlockMetadata(i, j, k) & 0b1111;

        switch (side) {
            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.999F, 1.0F, 1.0F, 1.0F);
                break;

            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.001F);
                break;

            case 4:
                this.setBlockBounds(0.999F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 5:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F);
                break;
            }
        }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
        int side = world.getBlockMetadata(i, j, k) & 0b1111;
        float f = 0.125F;

        switch (side) {
            case 2:
                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                break;

            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                break;

            case 4:
                this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;

            case 5:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                break;
            }

        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
        }

}
