package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record SingleRecipeDisplay(SlotDisplay input, SlotDisplay result, SlotDisplay craftingStation) implements RecipeDisplay {
    
    public static final MapCodec<SingleRecipeDisplay> MAP_CODEC = RecordCodecBuilder
            .mapCodec(i -> i
                    .group(SlotDisplay.CODEC.fieldOf("input").forGetter(SingleRecipeDisplay::input),
                            SlotDisplay.CODEC.fieldOf("result").forGetter(SingleRecipeDisplay::result),
                            SlotDisplay.CODEC.fieldOf("crafting_station")
                                    .forGetter(SingleRecipeDisplay::craftingStation))
                    .apply(i, SingleRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SingleRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            SlotDisplay.STREAM_CODEC, SingleRecipeDisplay::input, SlotDisplay.STREAM_CODEC, SingleRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC, SingleRecipeDisplay::craftingStation, SingleRecipeDisplay::new);
    public static final RecipeDisplay.Type<SingleRecipeDisplay> DISPLAY_TYPE = new RecipeDisplay.Type<>(
            SingleRecipeDisplay.MAP_CODEC, SingleRecipeDisplay.STREAM_CODEC);
    
    @Override
    public RecipeDisplay.Type<SingleRecipeDisplay> type() {
        return SingleRecipeDisplay.DISPLAY_TYPE;
    }
    
}
