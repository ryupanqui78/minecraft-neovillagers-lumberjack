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
    
    final SingleItemRecipe.Factory<WoodWorkingRecipe> factory;
    
    public WoodWorkingSerialize(SingleItemRecipe.Factory<WoodWorkingRecipe> pFactory) {
        this.factory = pFactory;
    }
    
    @Override
    public MapCodec<WoodWorkingRecipe> codec() {
        return RecordCodecBuilder
                .mapCodec(elements -> elements
                        .group(Codec.STRING.optionalFieldOf("group", "").forGetter(WoodWorkingRecipe::getGroup),
                                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient")
                                        .forGetter(WoodWorkingRecipe::getIngredient),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(WoodWorkingRecipe::getResult))
                        .apply(elements, this.factory::create));
    }
    
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, WoodWorkingRecipe> streamCodec() {
        return StreamCodec.composite(ByteBufCodecs.STRING_UTF8, WoodWorkingRecipe::getGroup,
                Ingredient.CONTENTS_STREAM_CODEC, WoodWorkingRecipe::getIngredient, ItemStack.STREAM_CODEC,
                WoodWorkingRecipe::getResult, this.factory::create);
    }
}
