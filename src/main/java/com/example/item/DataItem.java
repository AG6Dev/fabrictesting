package com.example.item;

import com.example.init.DataComponentInit;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

/**
 * Basic item using new item data component system
 */
public class DataItem extends Item {
    public DataItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (!world.isClient) {
            stack.apply(DataComponentInit.USE_COUNT, 0, integer -> integer + 1);
        }
        return TypedActionResult.success(stack, world.isClient());
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Optional<Integer> optional = Optional.ofNullable(stack.get(DataComponentInit.USE_COUNT));
        optional.ifPresent(count -> tooltip.add(Text.translatable("item.example.data_item.tooltip", count)));
    }
}
