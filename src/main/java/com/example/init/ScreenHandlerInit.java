package com.example.init;

import com.example.ExampleMod;
import com.example.client.screen.AlloySmelterScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public final class ScreenHandlerInit {
    public static final ScreenHandlerType<AlloySmelterScreenHandler> ALLOY_SMELTER = register("alloy_smelter", new ScreenHandlerType<>(AlloySmelterScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(ExampleMod.MOD_ID, name), factory);
    }

    public static void init() {
    }
}
