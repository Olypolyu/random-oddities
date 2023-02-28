package Olypolyu.randomoddities.mixin;

import Olypolyu.randomoddities.RandomOddities;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.WorldGenDungeon;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(value = WorldGenDungeon.class, remap = false)
public class DungeonLootMixin {

    private ItemStack pickCheckLootItem(Random random) {
        ItemStack returnStack;
        int item = random.nextInt(17);

        // i am sorry.

        switch (item) {

            default:
                returnStack = null;
                break;

            case 0:
                returnStack = new ItemStack(Item.saddle);
                break;

            case 1:
                returnStack = new ItemStack(Item.ingotIron, random.nextInt(4) + 1);
                break;

            case 2:
                returnStack = new ItemStack(Item.foodBread);
                break;

            case 3:
                returnStack = new ItemStack(Item.wheat, random.nextInt(4) + 1);
                break;

            case 4:
                returnStack = new ItemStack(Item.sulphur, random.nextInt(4) + 1);
                break;

            case 5:
                returnStack = new ItemStack(Item.string, random.nextInt(4) + 1);
                break;

            case 6:
                returnStack = new ItemStack(Item.bucket);
                break;

            case 7:
                if (random.nextInt(100) == 0) returnStack = new ItemStack(Item.foodAppleGold);
                    else returnStack = null;
                break;

            case 8:
                if (random.nextInt(2) == 0) returnStack = new ItemStack(Item.dustRedstone);
                    else returnStack = null;
                break;

            case 9:
                if (random.nextInt(10) == 0) returnStack = new ItemStack( Item.itemsList[ Item.record13.itemID + random.nextInt(9) ] );
                    else returnStack = null;
                break;

            case 10:
                returnStack = new ItemStack(Item.dye, 1, 3);
                break;

            case 11:
                returnStack = new ItemStack(Item.itemsList[Block.spongeDry.blockID], random.nextInt(4) + 1);
                break;

            case 12:
                returnStack = new ItemStack(Item.bone, random.nextInt(4) + 1);
                break;

            case 13:
                returnStack = new ItemStack(Item.foodApple);
                break;

            case 14:
                returnStack = new ItemStack(RandomOddities.itemPumpkinPie);
                break;

            case 15:
                if (random.nextInt(250) == 0) returnStack = new ItemStack(RandomOddities.windLamp, 1, random.nextInt(3));
                    else returnStack = null;
                break;

            case 16:
                //returnStack = new ItemStack(RandomOddities.brownPaintBrush);
                returnStack = null;
                break;
        }

        return returnStack;
    }

}
