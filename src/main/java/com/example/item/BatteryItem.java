package com.example.item;

import com.example.init.DataComponentInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class BatteryItem extends Item {
    public static final int MAX_ENERGY = 1000;

    public BatteryItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        var energy = stack.getOrDefault(DataComponentInit.ENERGY, 0);
        return energy * 13 / MAX_ENERGY;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        var energy = stack.getOrDefault(DataComponentInit.ENERGY, 0);
        float h = Math.max(0.0F, 1 - ((float) (MAX_ENERGY - energy) / MAX_ENERGY));
        return MathHelper.hsvToRgb(h / 3.0F, 1.0F, 1.0F);
    }
}
