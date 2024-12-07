package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientWoodWorkingRecipes {
    
    private static ClientWoodWorkingRecipeInputs inputs;
    
    // Handling the sent packet
    public static void handle(final ClientboundWoodWorkingRecipesPayload data, final IPayloadContext context) {
        ClientWoodWorkingRecipes.inputs = new ClientWoodWorkingRecipeInputs(data.recipes());
    }
    
    public static ClientWoodWorkingRecipeInputs inputs() {
        return ClientWoodWorkingRecipes.inputs;
    }
    
    private ClientWoodWorkingRecipes() {
    }
}
