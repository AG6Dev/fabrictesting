package com.example.recipe.serializer;

import com.example.recipe.AlloySmeltingRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.dynamic.Codecs;

public class AlloySmeltingRecipeSerializer implements RecipeSerializer<AlloySmeltingRecipe> {
    public static final AlloySmeltingRecipeSerializer INSTANCE = new AlloySmeltingRecipeSerializer();

    private static final MapCodec<AlloySmeltingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.ALLOW_EMPTY_CODEC.fieldOf("ingredientA").forGetter(AlloySmeltingRecipe::ingredientA),
            Ingredient.ALLOW_EMPTY_CODEC.fieldOf("ingredientB").forGetter(AlloySmeltingRecipe::ingredientB),
            ItemStack.CODEC.fieldOf("output").forGetter(AlloySmeltingRecipe::output),
            Codecs.POSITIVE_INT.fieldOf("cookTime").forGetter(AlloySmeltingRecipe::cookTime)
    ).apply(inst, AlloySmeltingRecipe::new));

    private static final PacketCodec<RegistryByteBuf, AlloySmeltingRecipe> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        Ingredient.PACKET_CODEC.encode(buf, value.ingredientA());
        Ingredient.PACKET_CODEC.encode(buf, value.ingredientB());
        ItemStack.PACKET_CODEC.encode(buf, value.output());
        buf.writeVarInt(value.cookTime());
    }, buf -> {
        Ingredient inputA = Ingredient.PACKET_CODEC.decode(buf);
        Ingredient inputB = Ingredient.PACKET_CODEC.decode(buf);
        ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
        int cookTime = buf.readVarInt();
        return new AlloySmeltingRecipe(inputA, inputB, output, cookTime);
    });

    @Override
    public PacketCodec<RegistryByteBuf, AlloySmeltingRecipe> packetCodec() {
        return PACKET_CODEC;
    }

    @Override
    public MapCodec<AlloySmeltingRecipe> codec() {
        return CODEC;
    }
}
