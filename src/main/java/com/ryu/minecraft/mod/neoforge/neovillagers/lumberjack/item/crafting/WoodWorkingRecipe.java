package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeSerializer;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeType;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;

public class WoodWorkingRecipe extends SingleItemRecipe {
    
    public static final String RECIPE_NAME = "woodworking";
    
    public WoodWorkingRecipe(String pGroup, Ingredient pIngredient, ItemStack pResult) {
        super(SetupRecipeType.WOODWORKING.get(), SetupRecipeSerializer.WOODWORKING.get(), pGroup, pIngredient, pResult);
    }
    
    public Ingredient getIngredient() {
        return this.ingredient;
    }
    
    public ItemStack getResult() {
        return this.result;
    }
    
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(SetupBlocks.WOODWORKING_TABLE.get());
    }
    
    @Override
    public RecipeType<?> getType() {
        return SetupRecipeType.WOODWORKING.get();
    }
    
    @Override
    public boolean isSpecial() {
        return true;
    }
    
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return this.ingredient.test(pContainer.getItem(0));
    }
    
}
