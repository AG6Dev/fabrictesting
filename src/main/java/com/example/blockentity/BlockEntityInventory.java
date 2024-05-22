package com.example.blockentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public interface BlockEntityInventory extends SidedInventory {
    DefaultedList<ItemStack> getStacks();

    @Override
    default int[] getAvailableSlots(Direction side) {
        return this.getStacks().stream().filter(ItemStack::isEmpty).mapToInt(value -> this.getStacks().indexOf(value)).toArray();
    }

    @Override
    default boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    default boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

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
