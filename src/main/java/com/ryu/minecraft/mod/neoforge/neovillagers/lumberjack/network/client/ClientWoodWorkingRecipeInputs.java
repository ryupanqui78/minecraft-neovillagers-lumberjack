package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network.client;

import java.util.List;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network.WoodWorkingRecipeInputs;

import net.minecraft.world.item.crafting.RecipeHolder;

public record ClientWoodWorkingRecipeInputs(List<RecipeHolder<WoodWorkingRecipe>> recipes) implements WoodWorkingRecipeInputs {
    
}
