package com.example.init;

import com.example.ExampleMod;
import com.example.recipe.AlloySmeltingRecipe;
import com.example.recipe.serializer.AlloySmeltingRecipeSerializer;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class RecipeTypeInit {
    public static final RecipeType<AlloySmeltingRecipe> ALLOY_SMELTING_TYPE = register("alloy_smelting", AlloySmeltingRecipe.Type.INSTANCE);
    public static final RecipeSerializer<AlloySmeltingRecipe> ALLOY_SMELTING_SERIALIZER = register("alloy_smelting", AlloySmeltingRecipeSerializer.INSTANCE);

    private static <T extends Recipe<?>> RecipeSerializer<T> register(String id, RecipeSerializer<T> serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ExampleMod.MOD_ID, id), serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> register(String id, RecipeType<T> type) {
        return Registry.register(Registries.RECIPE_TYPE, new Identifier(ExampleMod.MOD_ID, id), type);
    }

    public static void init() {
    }
}
