package com.example.init;

import com.example.ExampleMod;
import com.example.menu.AlloySmelterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public final class ScreenHandlerInit {
    public static final ScreenHandlerType<AlloySmelterScreenHandler> ALLOY_SMELTER = register("alloy_smelter", new ExtendedScreenHandlerType<>(AlloySmelterScreenHandler::new, BlockPos.PACKET_CODEC.cast()));

    public static <T extends ScreenHandler, D> ExtendedScreenHandlerType<T, D> register(String name, ExtendedScreenHandlerType<T, D> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(ExampleMod.MOD_ID, name), factory);
    }

    public static void init() {
    }
}
