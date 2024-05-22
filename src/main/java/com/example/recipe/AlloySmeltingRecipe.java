package com.example.recipe;

import com.example.init.ItemInit;
import com.example.recipe.serializer.AlloySmeltingRecipeSerializer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public record AlloySmeltingRecipe(Ingredient ingredientA, Ingredient ingredientB, ItemStack output, int cookTime) implements Recipe<Inventory> {
    @Override
    public ItemStack createIcon() {
        return ItemInit.ALLOY_SMELTER_BLOCK_ITEM.getDefaultStack();
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.ingredientA().test(inventory.getStack(0)) && this.ingredientB().test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(Inventory inventory, RegistryWrapper.WrapperLookup lookup) {
        return this.output().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.output();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AlloySmeltingRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AlloySmeltingRecipe> {
        public static final Type INSTANCE = new Type();

        @Override
        public String toString() {
            return "alloy_smelting";
        }
    }
}
