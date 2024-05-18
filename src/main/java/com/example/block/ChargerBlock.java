package com.example.block;

import com.example.init.DataComponentInit;
import com.example.item.BatteryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ChargerBlock extends Block {
    public ChargerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient()) {
            if (entity instanceof ItemEntity item) {
                var stack = item.getStack();
                if (stack.getItem() instanceof BatteryItem) {
                    takeEnergy(stack);
                }
            } else if (entity instanceof PlayerEntity player) {
                var stacks = findStacksWithEnergy(player.getInventory());
                for (var stack : stacks) {
                    takeEnergy(stack);
                }
            }
        }
    }

    private void takeEnergy(ItemStack stack) {
        var energy = stack.getOrDefault(DataComponentInit.ENERGY, 0);
        if (energy < BatteryItem.MAX_ENERGY) {
            stack.apply(DataComponentInit.ENERGY, energy, integer -> Math.min(integer + 10, BatteryItem.MAX_ENERGY));
        }
    }

    private List<ItemStack> findStacksWithEnergy(Inventory inventory) {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < inventory.size(); ++i) {
            var stack = inventory.getStack(i);
            if (stack.getItem().getComponents().contains(DataComponentInit.ENERGY)) {
                stacks.add(stack);
            }
        }
        return stacks;
    }
}
