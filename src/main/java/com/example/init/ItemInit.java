package com.example.init;

import com.example.ExampleMod;
import com.example.item.BatteryItem;
import com.example.item.DataItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ItemInit {
    public static final Item DATA_ITEM = register("data_item", new DataItem(new Item.Settings().component(DataComponentInit.USE_COUNT, 0)));
    public static final Item BATTERY = register("battery", new BatteryItem(new Item.Settings().maxCount(1).component(DataComponentInit.ENERGY, 0)));

    public static final Item CHARGER_BLOCK_ITEM = register("charger", BlockInit.CHARGER);
    public static final Item ALLOY_SMELTER_BLOCK_ITEM = register("alloy_smelter", BlockInit.ALLOY_SMELTER);

    private static Item register(String name, Block block) {
        return register(name, new BlockItem(block, new Item.Settings()));
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ExampleMod.MOD_ID, name), item);
    }

    public static void init() {
    }
}
