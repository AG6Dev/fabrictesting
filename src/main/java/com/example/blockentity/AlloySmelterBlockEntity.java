package com.example.blockentity;

import com.example.init.BlockEntityTypeInit;
import com.example.menu.AlloySmelterScreenHandler;
import com.example.recipe.AlloySmeltingRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
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
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

//TODO: Transfer this over to new transfer api
public class AlloySmelterBlockEntity extends BlockEntity implements Tickable, ExtendedScreenHandlerFactory<BlockPos>, BlockEntityInventory {
    public static final Text TITLE = Text.translatable("container.example.alloy_smelter");

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private Identifier currentRecipe;

    public int burnTime;
    public int totalBurnTime;
    public int currentProgress;
    public int totalProgress;

    public PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> burnTime;
                case 1 -> totalBurnTime;
                case 2 -> currentProgress;
                case 3 -> totalProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> burnTime = value;
                case 1 -> totalBurnTime = value;
                case 2 -> currentProgress = value;
                case 3 -> totalProgress = value;
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public AlloySmelterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeInit.ALLOY_SMELTER, pos, state);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        Inventories.readNbt(nbt, this.inventory, registryLookup);
        this.currentProgress = nbt.getInt("CookTime");
        this.totalProgress = nbt.getInt("TotalCookTime");
        this.burnTime = nbt.getInt("BurnTime");
        this.totalBurnTime = nbt.getInt("TotalBurnTime");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, this.inventory, registryLookup);
        nbt.putInt("CookTime", this.currentProgress);
        nbt.putInt("TotalCookTime", this.totalProgress);
        nbt.putInt("BurnTime", this.burnTime);
        nbt.putInt("TotalBurnTime", this.totalBurnTime);
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
    public void tick() {
        if(this.getWorld().isClient())
            return;

        if(this.burnTime > 0) {
            this.burnTime--;
            markDirty();
        }

        if (this.currentRecipe == null) {
            Optional<RecipeEntry<AlloySmeltingRecipe>> recipeEntry = this.getWorld().getRecipeManager().getFirstMatch(AlloySmeltingRecipe.Type.INSTANCE, this, this.getWorld());
            if(recipeEntry.isPresent()) {
                this.currentRecipe = recipeEntry.get().id();
                this.totalProgress = recipeEntry.get().value().cookTime();
                this.currentProgress = 0;
            }
            markDirty();
            return;
        }

        Optional<RecipeEntry<AlloySmeltingRecipe>> recipeEntry = this.getWorld().getRecipeManager().getFirstMatch(AlloySmeltingRecipe.Type.INSTANCE, this, this.getWorld());
        if(recipeEntry.isEmpty() || !recipeEntry.get().id().equals(this.currentRecipe)) {
            this.currentRecipe = null;
            this.totalProgress = 0;
            this.currentProgress = 0;
            markDirty();
            return;
        }

        if(this.burnTime <= 0) {
            var stack = this.inventory.get(2);
            if (FurnaceBlockEntity.canUseAsFuel(stack)) {
                this.burnTime = this.totalBurnTime = FurnaceBlockEntity.createFuelTimeMap().get(stack.getItem());
                stack.decrement(1);
                markDirty();
            } else {
                this.currentRecipe = null;
                this.totalProgress = 0;
                this.currentProgress = 0;
                this.totalBurnTime = 0;
                markDirty();
                return;
            }
        }

        this.currentProgress++;
        if(this.currentProgress >= this.totalProgress) {
            this.currentProgress = 0;
            this.totalProgress = 0;
            this.currentRecipe = null;

            this.inventory.get(0).decrement(1);
            this.inventory.get(1).decrement(1);
            this.inventory.set(3, recipeEntry.get().value().output().copy());
            markDirty();
        }
    }

    @Override
    public Text getDisplayName() {
        return AlloySmelterBlockEntity.TITLE;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlloySmelterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getStacks() {
        return this.inventory;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }
}
