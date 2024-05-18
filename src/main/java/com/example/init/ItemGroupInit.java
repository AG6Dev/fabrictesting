package com.example.init;

import com.example.ExampleMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class ItemGroupInit {
    public static final ItemGroup MOD_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(ExampleMod.MOD_ID, "example"), FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.example"))
            .icon(ItemInit.DATA_ITEM::getDefaultStack)
            .entries((displayContext, entries) -> {
                entries.add(ItemInit.DATA_ITEM);
                entries.add(ItemInit.BATTERY);
                entries.add(ItemInit.CHARGER_BLOCK_ITEM);
            })
            .build());

    public static void init() {
    }

}
