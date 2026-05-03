package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.display.SingleRecipeDisplay;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeType;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay.ItemSlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay.ItemStackSlotDisplay;

public class WoodWorkingRecipe extends SingleItemRecipe {
    
    public static final MapCodec<WoodWorkingRecipe> MAP_CODEC = SingleItemRecipe.simpleMapCodec(WoodWorkingRecipe::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, WoodWorkingRecipe> STREAM_CODEC = SingleItemRecipe
            .simpleStreamCodec(WoodWorkingRecipe::new);
    public static final RecipeSerializer<WoodWorkingRecipe> SERIALIZER = new RecipeSerializer<>(
            WoodWorkingRecipe.MAP_CODEC, WoodWorkingRecipe.STREAM_CODEC);
    
    public WoodWorkingRecipe(Recipe.CommonInfo commonInfo, Ingredient ingredient, ItemStackTemplate result) {
        super(commonInfo, ingredient, result);
    }
    
    @Override
    public List<RecipeDisplay> display() {
        ItemSlotDisplay blockItem = new SlotDisplay.ItemSlotDisplay(SetupBlocks.WOODWORKING.asItem());
        ItemStackSlotDisplay resultDisplay = new SlotDisplay.ItemStackSlotDisplay(this.result());
        return List.of(new SingleRecipeDisplay(this.input().display(), resultDisplay, blockItem));
    }
    
    public ItemStack getResultItem() {
        return this.result().create();
    }
    
    @Override
    public String group() {
        return "";
    }
    
    @Override
    public boolean isSpecial() {
        return true;
    }
    
    @Override
    public RecipeSerializer<WoodWorkingRecipe> getSerializer() {
        return WoodWorkingRecipe.SERIALIZER;
    }
    
    @Override
    public RecipeType<WoodWorkingRecipe> getType() {
        return SetupRecipeType.WOODWORKING.get();
    }
    
    @Override
    public RecipeBookCategory recipeBookCategory() {
        return SetupRecipeType.WOODWORKING_CATEGORY.get();
    }
    
}
