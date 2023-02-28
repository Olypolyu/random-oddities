package Olypolyu.randomoddities.item;

import Olypolyu.randomoddities.RandomOddities;
import net.minecraft.src.*;
import net.minecraft.src.command.commands.SummonCommand;

public class ItemSpawnEgg extends Item {

    private final Class entityClass;

    public ItemSpawnEgg(int i, Class entity) {
        super(i);
        this.entityClass = entity;
    }

    private void spawnAt(World world, double i, double j, double k, EntityPlayer entityplayer){
        Entity entity = SummonCommand.createEntity(this.entityClass, world);
        entity.entityInitOnSpawn();
        entity.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0F, 0.0F);
        world.entityJoinedWorld(entity);

        RandomOddities.LOGGER.info(entityClass.getName() + " spawned at " + i + " " + j + " " + k + " by " + entityplayer.username);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, double heightPlaced) {
        itemstack.consumeItem(entityplayer);
        switch (l) {
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
                k++;
                break;

            case 4:
                --i;
                break;

            case 5:
                i++;
                break;
            }

        spawnAt(world, i, j, k, entityplayer);
        return true;
    }

    public boolean useItemOnEntity(ItemStack itemstack, EntityLiving entityliving, EntityPlayer entityplayer) {
        itemstack.consumeItem(entityplayer);
        spawnAt(entityliving.worldObj, entityliving.posX, entityliving.posY + 0.5, entityliving.posZ, entityplayer);
        return true;
    }

}
