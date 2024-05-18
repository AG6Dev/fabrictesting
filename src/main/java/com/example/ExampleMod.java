package com.example;

import com.example.init.BlockInit;
import com.example.init.DataComponentInit;
import com.example.init.ItemGroupInit;
import com.example.init.ItemInit;
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
        DataComponentInit.init();
    }
}