package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network;

import java.util.List;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;

public class ServerWoodWorkingRecipes {
    
    private static ServerWoodWorkingRecipeInputs inputs;
    
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof final ServerPlayer serverPlayer
                && serverPlayer.level() instanceof final ServerLevel serveLevel) {
            ServerWoodWorkingRecipes.inputs = new ServerWoodWorkingRecipeInputs(serveLevel.recipeAccess());
            ServerWoodWorkingRecipes.inputs.loadRecipes();
            ServerWoodWorkingRecipes.inputs.syncToClient(List.of(serverPlayer).stream());
        }
        
    }
    
    public static WoodWorkingRecipeInputs inputs() {
        return ServerWoodWorkingRecipes.inputs;
    }
    
    private ServerWoodWorkingRecipes() {
    }
}
