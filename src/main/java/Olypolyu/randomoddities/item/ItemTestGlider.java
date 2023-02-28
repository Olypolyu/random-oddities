package Olypolyu.randomoddities.item;

import Olypolyu.randomoddities.mixin.RandomOdditiesEntityMixin;
import net.minecraft.src.*;

public class ItemTestGlider extends Item {
    public ItemTestGlider(int i) {
        super(i);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        float f = 0.4F;

        entityplayer.motionX = (-MathHelper.sin(entityplayer.rotationYaw / 180.0F * 3.141593F));
        entityplayer.motionZ = (MathHelper.cos(entityplayer.rotationYaw / 180.0F * 3.141593F));
        entityplayer.motionY = (-MathHelper.cos(f / 30F * 3.141593F) + 0.5F);
        ((RandomOdditiesEntityMixin) entityplayer).setFallDistance(0);
        return itemstack;
    }
}
