package com.example.menu;

import com.example.blockentity.AlloySmelterBlockEntity;
import com.example.blockentity.BlockEntityInventory;
import com.example.init.ScreenHandlerInit;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class AlloySmelterScreenHandler extends ScreenHandler {
    public int burnTime, totalBurnTime, smeltProgress, totalProgress;

    public BlockEntity blockEntity;

    public AlloySmelterScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(4));
    }

    public AlloySmelterScreenHandler(int id, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate delegate) {
        super(ScreenHandlerInit.ALLOY_SMELTER, id);

        this.burnTime = delegate.get(0);
        this.totalBurnTime = delegate.get(1);
        this.smeltProgress = delegate.get(2);
        this.totalProgress = delegate.get(3);

        if (!(blockEntity instanceof AlloySmelterBlockEntity)) {
            throw new IllegalArgumentException("BlockEntity is not an instance of AlloySmelterBlockEntity!");
        }

        this.blockEntity = blockEntity;

        Inventory inventory = (BlockEntityInventory) blockEntity;

        this.addProperties(delegate);

        this.addSlot(new Slot(inventory, 0, 35, 17)); //input1
        this.addSlot(new Slot(inventory, 1, 57, 17)); //input2
        this.addSlot(new Slot(inventory, 2, 46, 53)); //fuel
        this.addSlot(new Slot(inventory, 3, 116, 35)); //output

        //player hotbar
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        //player inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean isValid(int slot) {
        return true;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
