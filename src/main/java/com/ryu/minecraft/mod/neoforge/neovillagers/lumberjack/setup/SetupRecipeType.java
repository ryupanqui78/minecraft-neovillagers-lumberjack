package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import java.util.function.Supplier;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.block.WoodWorkingBlock;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.display.SingleRecipeDisplay;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupRecipeType {
    
    public static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAYS = DeferredRegister
            .create(BuiltInRegistries.RECIPE_DISPLAY, NeoVillagersLumberjack.MODID);
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister
            .create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, NeoVillagersLumberjack.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE,
            NeoVillagersLumberjack.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, NeoVillagersLumberjack.MODID);
    
    public static final Supplier<RecipeDisplay.Type<SingleRecipeDisplay>> SINGLE_RECIPE_DISPLAY = SetupRecipeType.RECIPE_DISPLAYS
            .register("single_recipe_diplay", () -> SingleRecipeDisplay.DISPLAY_TYPE);
    
    public static final Supplier<RecipeBookCategory> WOODWORKING_CATEGORY = SetupRecipeType.RECIPE_BOOK_CATEGORIES
            .register(WoodWorkingBlock.BLOCK_NAME, RecipeBookCategory::new);
    
    public static final Supplier<RecipeType<WoodWorkingRecipe>> WOODWORKING = SetupRecipeType.RECIPE_TYPES
            .register(WoodWorkingBlock.BLOCK_NAME, () -> RecipeType.<WoodWorkingRecipe> simple(
                    Identifier.fromNamespaceAndPath(NeoVillagersLumberjack.MODID, WoodWorkingBlock.BLOCK_NAME)));
    
    public static final Supplier<RecipeSerializer<WoodWorkingRecipe>> WOODWORKING_SERIALIZER = SetupRecipeType.RECIPE_SERIALIZERS
            .register("woodworking", () -> WoodWorkingRecipe.SERIALIZER);
    
    private SetupRecipeType() {
    }
    
}
