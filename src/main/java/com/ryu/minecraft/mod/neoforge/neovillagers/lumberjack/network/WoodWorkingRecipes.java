package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network.client.ClientWoodWorkingRecipes;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network.server.ServerWoodWorkingRecipes;

import net.minecraft.world.level.Level;

public class WoodWorkingRecipes {
    
    public static WoodWorkingRecipeInputs inputs(Level level) {
        return level.isClientSide() ? ClientWoodWorkingRecipes.inputs() : ServerWoodWorkingRecipes.inputs();
    }
    
    private WoodWorkingRecipes() {
    }
}
