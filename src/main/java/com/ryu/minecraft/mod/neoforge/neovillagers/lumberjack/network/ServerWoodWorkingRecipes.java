package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

public class ServerWoodWorkingRecipes {
    
    private static ServerWoodWorkingRecipeInputs inputs;
    
    // On the game event bus
    @SubscribeEvent
    public static void datapackSync(OnDatapackSyncEvent event) {
        // Send to client
        if (event.getPlayer() instanceof final ServerPlayer serverPlayer
                && serverPlayer.level() instanceof final ServerLevel serveLevel) {
            ServerWoodWorkingRecipes.inputs = new ServerWoodWorkingRecipeInputs(serveLevel.recipeAccess());
            ServerWoodWorkingRecipes.inputs.loadRecipes();
        }
        ServerWoodWorkingRecipes.inputs.syncToClient(event.getRelevantPlayers());
    }
    
    public static WoodWorkingRecipeInputs inputs() {
        return ServerWoodWorkingRecipes.inputs;
    }
    
    private ServerWoodWorkingRecipes() {
    }
}
