package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupRecipeType {
    
    public static final DeferredRegister<RecipeType<?>> REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE,
            NeoVillagersLumberjack.MODID);
    
    public static final DeferredHolder<RecipeType<?>, RecipeType<WoodWorkingRecipe>> WOODWORKING = SetupRecipeType.REGISTER
            .register(WoodWorkingRecipe.RECIPE_NAME, () -> new RecipeType<WoodWorkingRecipe>() {
            });
    
    private SetupRecipeType() {
    }
    
}
