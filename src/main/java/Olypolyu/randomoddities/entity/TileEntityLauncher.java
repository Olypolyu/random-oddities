package Olypolyu.randomoddities.entity;

import Olypolyu.randomoddities.mixin.RandomOdditiesEntityMixin;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

import java.util.List;
import java.util.Random;

public class TileEntityLauncher extends TileEntity {

    private final Random random = new Random();

    // parameters for motion values and cooldown.
    private double launchX;
    private double launchY;
    private double launchZ;
    private int cooldown = 2;


    // Visual effects
    private String soundEffect;
    private String particle;

    public TileEntityLauncher(){
    }
    
    public void setLauncherParameters( double launchX, double launchY, double launchZ ) {
        this.launchX = launchX;
        this.launchY = launchY;
        this.launchZ = launchZ;
        this.soundEffect = "random.bow";
        this.particle = "flame";
    }

    public void setLauncherParameters( double launchX, double launchY, double launchZ, String soundEffect, String particle) {
        this.launchX = launchX;
        this.launchY = launchY;
        this.launchZ = launchZ;
        this.soundEffect = soundEffect;
        this.particle = particle;
    }


   public void updateEntity() {

        // get entities within bounding box, then yeet.
        List<Entity> list = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 2, this.zCoord + 1));

        for (int j = 0; j < list.size(); ++j) {
            Entity entity = list.get(j);

            // negates fall damage.
            ((RandomOdditiesEntityMixin) entity).setFallDistance(0f);

            //fixes trampoline shoving players up ceilings
            if (this.cooldown == 0 && list.size() > 0) {
                this.cooldown = 8;

                // will send the entity back with 1/4 of it's falling back + it's launch force
                if (this.launchY != 0 && entity.motionY < launchY * 0.75) {
                    entity.motionY = ((entity.motionY * -1) / 4) + this.launchY;
                    doVisualEffects(entity);
                }

                // x axis
                if (this.launchX != 0 && entity.motionX < launchX * 0.75) {
                    entity.motionX = this.launchX;
                    doVisualEffects(entity);
                }

                // Z axis
                if (this.launchZ != 0 && entity.motionZ < launchZ * 0.75) {
                    entity.motionZ = this.launchZ;
                    doVisualEffects(entity);
                }
            }
        }

        if(this.cooldown >= 1) this.cooldown--;
    }

   // generate particles and play sound.
   private void doVisualEffects(Entity entity) {
            this.worldObj.playSoundEffect((double) this.xCoord + 0.5, (double) this.yCoord + 0.5, (double) this.zCoord + 0.5, this.soundEffect, 0.3F, 0.6F);
            for (int i = 0; i < 8; i++){
                this.worldObj.spawnParticle(this.particle,
                entity.posX,
                entity.posY-0.5f,
                entity.posZ,
                (this.random.nextFloat()-0.5),
                (this.random.nextFloat()-0.5),
                (this.random.nextFloat()-0.5));
            }
        }

   // writes values stated on BlockEntityLauncher to NBT
   public void writeToNBT(NBTTagCompound nbttagcompound) {
       super.writeToNBT(nbttagcompound);
       nbttagcompound.setDouble("launchX", this.launchX);
       nbttagcompound.setDouble("launchY", this.launchY);
       nbttagcompound.setDouble("launchZ", this.launchZ);
       nbttagcompound.setString("soundEffect", soundEffect);
       nbttagcompound.setString("particle", particle);
   }

   // reads all values stated on BlockEntityLauncher from NBT
   public void readFromNBT(NBTTagCompound nbttagcompound) {
       super.readFromNBT(nbttagcompound);
       this.launchX = nbttagcompound.getDouble("launchX");
       this.launchY = nbttagcompound.getDouble("launchY");
       this.launchZ = nbttagcompound.getDouble("launchZ");
       this.soundEffect = nbttagcompound.getString("soundEffect");
       this.particle = nbttagcompound.getString("particle");
   }
}