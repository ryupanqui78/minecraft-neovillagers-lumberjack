package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network;

import net.minecraft.world.level.Level;

public class WoodWorkingRecipes {
    
    public static WoodWorkingRecipeInputs inputs(Level level) {
        return level.isClientSide ? ClientWoodWorkingRecipes.inputs() : ServerWoodWorkingRecipes.inputs();
    }
    
    private WoodWorkingRecipes() {
    }
}
