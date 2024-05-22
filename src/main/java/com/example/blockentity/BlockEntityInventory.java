package com.example.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface BlockEntityInventory extends Inventory {
    DefaultedList<ItemStack> getStacks();

    @Override
    default int size() {
        return this.getStacks().size();
    }

    @Override
    default boolean isEmpty() {
        return this.getStacks().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    default ItemStack getStack(int slot) {
        return this.getStacks().get(slot);
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.getStacks(), slot, amount);
    }

    @Override
    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.getStacks(), slot);
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        this.getStacks().set(slot, stack);
    }

    @Override
    default void markDirty() {

    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    default void clear() {
        this.getStacks().clear();
    }
}
