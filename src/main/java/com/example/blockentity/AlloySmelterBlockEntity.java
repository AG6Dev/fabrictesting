package com.example.blockentity;

import com.example.init.BlockEntityTypeInit;
import com.example.recipe.AlloySmeltingRecipe;
import com.example.client.screen.AlloySmelterScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

//eventually add hopper functionality etc
public class AlloySmelterBlockEntity extends BlockEntity implements Tickable, BlockEntityInventory, NamedScreenHandlerFactory {
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    public final PropertyDelegate propertyDelegate;

    public int burnTime, totalBurnTime, smeltingProgress, maxProgress;
    public Identifier currentRecipe;

    public AlloySmelterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeInit.ALLOY_SMELTER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> burnTime;
                    case 1 -> totalBurnTime;
                    case 2 -> smeltingProgress;
                    case 3 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> burnTime = value;
                    case 1 -> totalBurnTime = value;
                    case 2 -> smeltingProgress = value;
                    case 3 -> maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public void tick() {
        if (this.world == null || this.world.isClient) return;

        if (this.currentRecipe == null) {
            Optional<RecipeEntry<AlloySmeltingRecipe>> recipe = this.world.getRecipeManager().getFirstMatch(AlloySmeltingRecipe.Type.INSTANCE, this, this.world);
            if (recipe.isPresent()) {
                this.currentRecipe = recipe.get().id();
                this.maxProgress = recipe.get().value().cookTime();
                this.smeltingProgress = 0;
            }
            update();
            return;
        }

        Optional<RecipeEntry<AlloySmeltingRecipe>> recipe = this.world.getRecipeManager().getFirstMatch(AlloySmeltingRecipe.Type.INSTANCE, this, this.world);
        if (recipe.isEmpty() || !recipe.get().id().equals(this.currentRecipe) || !canOutputToSlot(recipe.get().value().output().copy())) {
            resetProgress();
            return;
        }

        if (this.burnTime > 0) {
            this.burnTime--;
            update();
        }

        if (this.burnTime <= 0) {
            var stack = this.inventory.get(2);
            if (FurnaceBlockEntity.canUseAsFuel(stack)) {
                this.burnTime = this.totalBurnTime = FurnaceBlockEntity.createFuelTimeMap().get(stack.getItem());
                stack.decrement(1);
                update();
            } else {
                resetAll();
                return;
            }
        }

        this.smeltingProgress++;
        update();
        if (this.smeltingProgress >= this.maxProgress) {
            this.inventory.get(0).decrement(1);
            this.inventory.get(1).decrement(1);
            this.inventory.set(3, recipe.get().value().output().copy());
            resetProgress();
        }

    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, this.inventory, registryLookup);
        this.burnTime = nbt.getInt("BurnTime");
        this.totalBurnTime = nbt.getInt("TotalBurnTime");
        this.smeltingProgress = nbt.getInt("SmeltingProgress");
        this.maxProgress = nbt.getInt("MaxProgress");
        super.readNbt(nbt, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
        nbt.putInt("BurnTime", this.burnTime);
        nbt.putInt("TotalBurnTime", this.totalBurnTime);
        nbt.putInt("SmeltingProgress", this.smeltingProgress);
        nbt.putInt("MaxProgress", this.maxProgress);
    }

    @Override
    public DefaultedList<ItemStack> getStacks() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.example.alloy_smelter");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlloySmelterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public boolean canOutputToSlot(ItemStack stackToOutput) {
        return this.inventory.get(3).isEmpty() || (ItemStack.areItemsAndComponentsEqual(this.inventory.get(3), stackToOutput) && this.inventory.get(3).getCount() + stackToOutput.getCount() <= stackToOutput.getMaxCount());
    }

    public void resetAll() {
        this.burnTime = 0;
        this.totalBurnTime = 0;
        this.smeltingProgress = 0;
        this.maxProgress = 0;
        this.currentRecipe = null;
        update();
    }

    public void resetProgress() {
        this.smeltingProgress = 0;
        this.maxProgress = 0;
        this.currentRecipe = null;
        update();
    }

    public void update() {
        if (this.world == null) return;

        this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
        markDirty();
    }
}
