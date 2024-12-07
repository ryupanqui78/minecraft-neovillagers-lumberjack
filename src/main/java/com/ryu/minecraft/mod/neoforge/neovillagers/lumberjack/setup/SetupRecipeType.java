package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import java.util.function.Supplier;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupRecipeType {
    
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister
            .create(BuiltInRegistries.RECIPE_BOOK_CATEGORY, NeoVillagersLumberjack.MODID);
    public static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE,
            NeoVillagersLumberjack.MODID);
    
    public static final Supplier<RecipeBookCategory> WOODWORKING_CATEGORY = SetupRecipeType.RECIPE_BOOK_CATEGORIES
            .register("woodworking", RecipeBookCategory::new);
    
    public static final Supplier<RecipeType<WoodWorkingRecipe>> WOODWORKING = SetupRecipeType.REGISTER
            .register(WoodWorkingRecipe.RECIPE_NAME, registryName -> new RecipeType<WoodWorkingRecipe>() {
                @Override
                public String toString() {
                    return registryName.toString();
                }
            });
    
    private SetupRecipeType() {
    }
    
}
