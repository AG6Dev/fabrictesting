package com.example;

import com.example.init.ScreenHandlerInit;
import com.example.screen.AlloySmelterScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        HandledScreens.register(ScreenHandlerInit.ALLOY_SMELTER, AlloySmelterScreen::new);
    }
}