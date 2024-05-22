package com.example.data.providers;

import com.example.init.BlockInit;
import com.example.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ExampleLangProvider extends FabricLanguageProvider {
    public ExampleLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("itemGroup.example", "Example Tab");

        translationBuilder.add(ItemInit.DATA_ITEM, "Data Item");
        translationBuilder.add("item.example.data_item.tooltip", "Use count: %s");
        translationBuilder.add(ItemInit.BATTERY, "Battery");
        ;

        translationBuilder.add(BlockInit.ALLOY_SMELTER, "Alloy Smelter");
        translationBuilder.add(BlockInit.CHARGER, "Charger");

        translationBuilder.add("container.example.alloy_smelter", "Alloy Smelter");
    }
}
