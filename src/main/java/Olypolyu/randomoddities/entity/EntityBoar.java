package Olypolyu.randomoddities.entity;

import net.minecraft.src.*;
import net.minecraft.src.helper.DamageType;

import java.util.List;

public class EntityBoar extends EntityMob {
    protected int angerLevel = 0;
    protected int forgivenessLevel;
    protected boolean enraged = false;

    public EntityBoar(World world) {
        super(world);
        this.texture = "/mob/boar.png";
        this.health = 35;
        this.moveSpeed = 0.25F;
        this.attackStrength = 7;
        this.scoreValue = 30;
    }

    // changes the boar's state to enraged
    public void setAngerStats(){
        this.texture = "/mob/boarAngry.png";
        this.moveSpeed = 1F;
        this.scoreValue = 300;
        this.enraged = true;
    }

    // resets the boar back to its passive state.
    public void setPassiveStats() {
        this.angerLevel = 0;
        this.texture = "/mob/boar.png";
        this.health = 35;
        this.moveSpeed = 0.25F;
        this.scoreValue = 30;
        this.entityToAttack = null;
        this.enraged = false;
    }
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short)this.angerLevel);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.angerLevel = nbttagcompound.getShort("Anger");

        // sets the boar's state back to enraged when loading in from nbt.
        if (this.angerLevel >= 1) this.setAngerStats();
    }
    protected Entity findPlayerToAttack() {
        return this.angerLevel == 0 ? null : super.findPlayerToAttack();
    }

    public boolean attackEntityFrom(Entity entity, int i, DamageType type) {
        if (entity instanceof EntityPlayer) {
            // creates a list with every entity in a 32x32x32 bounding box.
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0, 32.0, 32.0));

            //if they are boars set their target to whoever hurt the fist boar and enrage them.
            for(int j = 0; j < list.size(); ++j) {
                Entity entity1 = list.get(j);
                if (entity1 instanceof EntityBoar) {
                    EntityBoar entityboar = (EntityBoar)entity1;
                    entityboar.becomeAngryAt(entity);
                }
            }

            this.becomeAngryAt(entity);
        }

        return super.attackEntityFrom(entity, i, type);
    }

    public void becomeAngryAt(Entity entity) {
        this.entityToAttack = entity;
        this.angerLevel = 4 + this.rand.nextInt(14);
        this.setAngerStats();
    }

    //maybe,  just maybe, you should not have punched that boar.
    protected boolean canDespawn() {
        return !this.enraged;
    }

    // adds one forgiveness level each tick, every 100 forgiveness levels we take an anger level.
    // If the boar is in an enraged state and has less than 1 anger level we revert it back to being passive.
    public void onLivingUpdate() {
        if (this.forgivenessLevel < 100) this.forgivenessLevel++; // stops at 100 cuz... let's not blow past the 64 bit limit shall we?
        if (this.forgivenessLevel >= 100 && this.angerLevel >= 1) { this.angerLevel--; this.forgivenessLevel = 0;}
        if (this.angerLevel < 1 && this.enraged) this.setPassiveStats();
        super.onLivingUpdate();
    }

    //makes it sound like a pig
    protected String getLivingSound() { return "mob.pig"; }
    protected String getHurtSound() { return "mob.pig"; }
    protected String getDeathSound() { return "mob.pigdeath"; }

    // makes it drop pork-chops and what rate.
    protected int getDropItemId() {
        return this.fire > 0 ? Item.foodPorkchopCooked.itemID : Item.foodPorkchopRaw.itemID;
    }
    protected void dropFewItems() {
        int i = this.getDropItemId();
        if (i > 0) {
            int j = rand.nextInt(4) + 1;
            for( int k = 0; k < j; k++) {
                this.dropItem(i, 1);
            }
        }
    }

    //honestly, i just copied EntityLiving's getCanSpawnHere and removed the super. call, so far hasn't caused any issues related to mob spawning. I would be weary about them.
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        int blockId = this.worldObj.getBlockId(i, j - 1, k);
        return (blockId == Block.grass.blockID || blockId == Block.dirt.blockID || blockId == Block.grassRetro.blockID) && this.worldObj.getFullBlockLightValue(i, j, k) > 8;
    }
}
