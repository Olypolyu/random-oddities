package Olypolyu.randomoddities.block;

import Olypolyu.randomoddities.RandomOddities;
import net.minecraft.src.*;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.List;
import java.util.Random;

public class BlockFireStriker extends BlockRotatable {
    private final Random random = new Random();
    private final int[] faceTexture;

    public BlockFireStriker(int i, Material material) {
        super(i, material);

        faceTexture = TextureHelper.registerBlockTexture(RandomOddities.MOD_ID, "fireman.png");
    }
    private boolean lastState = false;
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
        boolean power = world.isBlockGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j, k);
        if (power && !lastState) this.onPowered(world, i, j, k);
        lastState = power;
    }


    public int getBlockTextureFromSideAndMetadata(int side, int meta) {

            // block's facing :
            switch (meta) {

                //north
                case 2:
                    if (side == 2)
                         return texCoordToIndex(faceTexture[0], faceTexture[1]);
                    else return texCoordToIndex(14, 3);

                //south
                case 3:
                    if (side == 3)
                        return texCoordToIndex(faceTexture[0], faceTexture[1]);
                    else return texCoordToIndex(14, 3);

                //west
                default:
                    if (side == 4)
                        return texCoordToIndex(faceTexture[0], faceTexture[1]);
                    else return texCoordToIndex(14, 3);

                //east
                case 5:
                    if (side == 5)
                        return texCoordToIndex(faceTexture[0], faceTexture[1]);
                    else return texCoordToIndex(14, 3);

            }
    }

    // Sides           Metadata
    // 0 - bottom      0 - north
    // 1 - top         1 - east
    // 2 - north       2 - south
    // 3 - south       3 - west
    // 4 - west        4 - up
    // 5 - east        5 - down

    public void onPowered(World world, int i, int j, int k) {
        int side = world.getBlockMetadata(i, j, k);

        // is getting powered
        if (world.isBlockIndirectlyGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i, j, k)) {
            switch (side) {
                // north
                case 2:
                    k--;
                    break;

                // east
                case 5:
                    i++;
                    break;

                // south
                case 3:
                    k++;
                    break;

                // west
                default:
                    i--;
                    break;
            }

            // ignite tnt
            if (world.getBlockId(i, j, k) == Block.tnt.blockID) {

                // destroy tnt block
                world.setBlockWithNotify(i, j, k, 0);

                // spawn tnt entity
                EntityTNTPrimed e = new EntityTNTPrimed(world, i + 0.5F, j + 0.5F, k + 0.5F);
                world.entityJoinedWorld(e);
                world.playSoundAtEntity(e, "random.fuse", 1.0F, 1.0F);

                return;
            }

            List<Entity> entitiesInBoundingBox = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1));

            for (int list = 0; list < entitiesInBoundingBox.size(); ++list) {
                Entity entity = entitiesInBoundingBox.get(list);

                // things related to players
                if (entity instanceof EntityCreeper) {
                    world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 3.0F);
                    entity.setEntityDead();
                    return;
                }
            }

            // Set fire on whatever
            if (world.canBlockBePlacedAt(Block.fire.blockID, i, j, k, false, 0)) {
                world.setBlockWithNotify(i, j, k, Block.fire.blockID);
                visualEffects(world, i, j, k);
            }

        }
}

    private void visualEffects( World world, int i, int j, int k) {

        world.playSoundEffect( i, j, k, "fire.ignite", 1.0F, 1.0F);

        // summon particles
        int h;
        for ( int x = 0; x < 5; x++) {
            if (random.nextInt(2) == 0) h = 1;
            else h = -1;

            world.spawnParticle("smoke", i + 0.5, j + 0.5, k + 0.5, random.nextDouble() / 4 * h, random.nextDouble() / 4 * h, random.nextDouble() / 4 * h);
        }

    }

}
