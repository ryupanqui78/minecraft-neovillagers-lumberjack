package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingSerialize;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupRecipeSerializer {
    
    public static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, NeoVillagersLumberjack.MODID);
    
    public static final DeferredHolder<RecipeSerializer<?>, WoodWorkingSerialize> WOODWORKING = SetupRecipeSerializer.REGISTER
            .register(WoodWorkingRecipe.RECIPE_NAME, () -> new WoodWorkingSerialize(WoodWorkingRecipe::new));
    
    private SetupRecipeSerializer() {
    }
}
