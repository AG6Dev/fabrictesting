package com.example.client.screen;

import com.example.init.ScreenHandlerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AlloySmelterScreenHandler extends ScreenHandler {
    private final PropertyDelegate properties;

    public AlloySmelterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(4));
    }

    public AlloySmelterScreenHandler(int id, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ScreenHandlerInit.ALLOY_SMELTER, id);

        this.properties = delegate;

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
    public boolean canUse(PlayerEntity player) {
        return player.getInventory().canPlayerUse(player);
    }

    public int getBurnTime() {
        return this.properties.get(0);
    }

    public int getTotalBurnTime() {
        return this.properties.get(1);
    }

    public int getSmeltProgress() {
        return this.properties.get(2);
    }

    public int getMaxProgress() {
        return this.properties.get(3);
    }
}
