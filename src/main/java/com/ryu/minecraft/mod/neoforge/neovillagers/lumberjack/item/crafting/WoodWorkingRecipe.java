package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeSerializer;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeType;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public class WoodWorkingRecipe extends SingleItemRecipe {
    
    public static final String RECIPE_NAME = "woodworking";
    
    public WoodWorkingRecipe(String pGroup, Ingredient pIngredient, ItemStack pResult) {
        super(pGroup, pIngredient, pResult);
    }
    
    public ItemStack getResult() {
        return this.result();
    }
    
    @Override
    public RecipeSerializer<? extends SingleItemRecipe> getSerializer() {
        return SetupRecipeSerializer.WOODWORKING.get();
    }
    
    @Override
    public RecipeType<WoodWorkingRecipe> getType() {
        return SetupRecipeType.WOODWORKING.get();
    }
    
    @Override
    public boolean isSpecial() {
        return true;
    }
    
    @Override
    public RecipeBookCategory recipeBookCategory() {
        return SetupRecipeType.WOODWORKING_CATEGORY.get();
    }
    
    public SlotDisplay resultDisplay() {
        return new SlotDisplay.ItemStackSlotDisplay(this.result());
    }
    
}
