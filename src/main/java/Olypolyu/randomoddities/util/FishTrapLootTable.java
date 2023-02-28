package Olypolyu.randomoddities.util;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

import java.util.HashMap;

public class FishTrapLootTable {
    public static final HashMap<Integer, LootStack> FishingLoot;

    static {
        FishingLoot = new HashMap<Integer, LootStack>() {{
            put(0, new LootStack(Item.foodFishRaw, 0, 4, 2));
            put(1, new LootStack(Item.armorBootsLeather, 10, 1, 1, Item.armorBootsLeather.getMaxDamage(), 90));
            put(3, new LootStack(Block.tnt, 20, 3));
            //put(4, new LootStack(RandomOddities.brownPaintBrush, 20, 1,1, 24,12));
            put(5, new LootStack(Item.string, 10, 3));
            put(6, new LootStack(Item.dye, 10, 2, 1, 3, 3));
            put(7, new LootStack(Block.planksOakPainted, 5, 16, 1, 15));
        }};
    }

}
