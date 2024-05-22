package com.example.init;

import com.example.ExampleMod;
import com.example.blockentity.AlloySmelterBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class BlockEntityTypeInit {
    public static final BlockEntityType<AlloySmelterBlockEntity> ALLOY_SMELTER = register("alloy_smelter", BlockEntityType.Builder.create(AlloySmelterBlockEntity::new, BlockInit.ALLOY_SMELTER).build(null));

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(ExampleMod.MOD_ID, name), blockEntityType);
    }

    public static void init() {
    }
}
