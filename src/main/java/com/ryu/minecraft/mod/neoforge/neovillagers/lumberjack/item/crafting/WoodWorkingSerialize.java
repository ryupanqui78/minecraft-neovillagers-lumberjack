package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;

public class WoodWorkingSerialize implements RecipeSerializer<WoodWorkingRecipe> {
    
    private static final String ELEMENT_INGREDIENT = "ingredient";
    private static final String ELEMENT_GROUP = "group";
    private static final String ELEMENT_RESULT = "result";
    
    private final MapCodec<WoodWorkingRecipe> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, WoodWorkingRecipe> streamCodec;
    
    public WoodWorkingSerialize(SingleItemRecipe.Factory<WoodWorkingRecipe> factory) {
        this.codec = RecordCodecBuilder.mapCodec(element -> element.group(
                Codec.STRING.optionalFieldOf(WoodWorkingSerialize.ELEMENT_GROUP, "")
                        .forGetter(WoodWorkingRecipe::group),
                Ingredient.CODEC.fieldOf(WoodWorkingSerialize.ELEMENT_INGREDIENT).forGetter(WoodWorkingRecipe::input),
                ItemStack.STRICT_CODEC.fieldOf(WoodWorkingSerialize.ELEMENT_RESULT)
                        .forGetter(WoodWorkingRecipe::getResult))
                .apply(element, factory::create));
        this.streamCodec = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, WoodWorkingRecipe::group,
                Ingredient.CONTENTS_STREAM_CODEC, WoodWorkingRecipe::input, ItemStack.STREAM_CODEC,
                WoodWorkingRecipe::getResult, factory::create);
    }
    
    @Override
    public MapCodec<WoodWorkingRecipe> codec() {
        return this.codec;
    }
    
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, WoodWorkingRecipe> streamCodec() {
        return this.streamCodec;
    }
}
