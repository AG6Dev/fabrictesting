package com.example;

import com.example.init.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "example";
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleMod.MOD_ID);

    @Override
    public void onInitialize() {
        ItemGroupInit.init();
        ItemInit.init();
        BlockInit.init();
        BlockEntityTypeInit.init();
        RecipeTypeInit.init();
        ScreenHandlerInit.init();
        DataComponentInit.init();
    }
}