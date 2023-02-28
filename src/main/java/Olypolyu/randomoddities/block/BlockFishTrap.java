package Olypolyu.randomoddities.block;

import Olypolyu.randomoddities.RandomOddities;
import Olypolyu.randomoddities.util.FishTrapLootTable;
import Olypolyu.randomoddities.util.LootStack;
import net.minecraft.src.*;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.Random;

public class BlockFishTrap extends Block {
    protected int radius = 1;
    private final Random random = new Random();
    private final int[][] fishTrapTextures = new int[3][2];

    public BlockFishTrap(int i, Material material) {
        super(i, material);
        this.setTickOnLoad(true);

        fishTrapTextures[0] = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, "FishTrap.png");
        fishTrapTextures[1] = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, "FishTrapEngaged.png");
        fishTrapTextures[2] = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, "FishTrapFull.png");
    }

    private boolean isInWater(World world, int i, int j, int k){
    int x;
    int z;
    int water = 0;

        for ( z = k - radius; z <= k + radius; z++) {
            for ( x = i - radius; x <= i + radius; x++  ) {
                Material mat = world.getBlockMaterial(x, j, z);
                if (mat == Block.fluidWaterStill.blockMaterial || mat == Block.fluidWaterFlowing.blockMaterial)
                    water = water + 1;
                }
            }

        return water >= 6;
    }

    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return isInWater(world, i, j, k);
    }

    public void updateTick(World world, int i, int j, int k, Random random) {
        if (isInWater(world, i, j, k)) {
            if (random.nextInt(100) == 0 && world.getBlockMetadata(i, j, k) == 1) {
                world.setBlockMetadataWithNotify(i, j, k, 2);
                }
            } else checkForWater(world, i, j, k);
        }

    public void checkForWater(World world, int i, int j, int k) {
        if (!isInWater(world, i, j, k)) {
            world.setBlockWithNotify(i, j, k, 0);
            world.dropItem(i, j, k, new ItemStack(RandomOddities.fishTrap));
            }
        }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        checkForWater(world, i, j, k);
    }

    public int getBlockTextureFromSideAndMetadata(int side, int meta) {
        return texCoordToIndex(fishTrapTextures[meta][0], fishTrapTextures[meta][1]);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        ItemStack currentItem = entityplayer.getCurrentEquippedItem();

        if ( currentItem != null && world.getBlockMetadata(i, j, k) == 0) {
            if (currentItem.getItem() == Item.string) {
                entityplayer.inventory.getCurrentItem().consumeItem(entityplayer);
                world.setBlockMetadataWithNotify(i, j, k, 1);
                this.getBlockTextureFromSideAndMetadata(1,0);
                return true;
                }
            }

        if ( world.getBlockMetadata(i, j, k) == 2) {

                world.setBlockMetadataWithNotify(i, j, k, 0);
                ItemStack returnStack = null;

                while (returnStack == null) {
                    LootStack loot = FishTrapLootTable.FishingLoot.get( random.nextInt(FishTrapLootTable.FishingLoot.size() + 1) );
                    System.out.println(FishTrapLootTable.FishingLoot.size());
                    if (loot != null) returnStack = loot.generateLoot();
                }

                world.dropItem(i, j + 1, k, returnStack);
                return true;
            }

        return false;
    }

}