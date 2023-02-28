package Olypolyu.randomoddities.entity;

import Olypolyu.randomoddities.mixin.RandomOdditiesEntityMixin;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class TileEntityBubbleColumn extends TileEntity {
    private final Random random = new Random();
    private int columnLength;

    private void calculateColumnLength() {
        this.columnLength = 0;
        boolean reachedTop = false;

        while (!reachedTop) {
            Material blockMaterial = this.worldObj.getBlockMaterial( this.xCoord, this.yCoord + 1 + this.columnLength, this.zCoord );
            if ( blockMaterial == Block.fluidWaterStill.blockMaterial || blockMaterial == Block.fluidWaterFlowing.blockMaterial )
                this.columnLength = this.columnLength + 1;

            else reachedTop = true;
            }
        }

    public void updateEntity() {
        calculateColumnLength();

        if (this.columnLength > 1) {
            // get entities within bounding box, then yeet.
            List<Entity> entitiesInBoundingBox = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 0.6 + this.columnLength, this.zCoord + 1));

            for (int j = 0; j < entitiesInBoundingBox.size(); ++j) {
                Entity entity = entitiesInBoundingBox.get(j);

                // things related to players
                if ( entity instanceof EntityPlayer ) {

                    // slowly regenerate air points
                    if ( entity.air < ((RandomOdditiesEntityMixin) entity).getMaxAir() - 2 )
                        entity.air = entity.air + 2;

                    // don't lift the player if they are sneaking
                    if (entity.isSneaking())
                        break;

                    }

                // yeet!
                if (entity.motionY < 0.8) {
                    entity.motionY = entity.motionY + 0.25;

                    float Strength = (float) ((this.columnLength - entity.getDistance(this.xCoord, this.yCoord, this.zCoord) + 1) * 0.10);
                    if (Strength > 1) entity.motionY = entity.motionY + Strength;
                    }

                }

            int i;
            if (this.columnLength > 0)
                for (i = 0; i < random.nextInt(this.columnLength); i++)
                    this.worldObj.spawnParticle("bubble",
                            this.xCoord + this.random.nextFloat(),
                            this.yCoord + 1,
                            this.zCoord + this.random.nextFloat(),
                            0,
                            this.columnLength,
                            0);
            }
        }
}
