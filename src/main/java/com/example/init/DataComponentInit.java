package com.example.init;

import com.example.ExampleMod;
import com.mojang.serialization.Codec;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class DataComponentInit {

    public static final DataComponentType<Integer> USE_COUNT = register("use_count", DataComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build());
    public static final DataComponentType<Integer> ENERGY = register("energy", DataComponentType.<Integer>builder().codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT).build());

    private static <T> DataComponentType<T> register(String identifier, DataComponentType<T> dataComponentType) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier(ExampleMod.MOD_ID, identifier), dataComponentType);
    }

    public static void init() {
    }
}