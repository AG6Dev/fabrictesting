package com.example.init;

import com.example.ExampleMod;
import com.example.block.ChargerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class BlockInit {
    public static final Block CHARGER = register("charger", new ChargerBlock(AbstractBlock.Settings.create()));

    private static Block register(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(ExampleMod.MOD_ID, name), block);
    }

    public static void init() {
    }
}
