package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network;

import java.util.List;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

public interface WoodWorkingRecipeInputs {
    
    List<RecipeHolder<WoodWorkingRecipe>> recipes();
    
    default boolean test(ItemStack pItemStack) {
        return this.recipes().stream().anyMatch(holder -> holder.value().input().test(pItemStack));
    }
}
