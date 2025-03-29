package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeType;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ServerWoodWorkingRecipeInputs implements WoodWorkingRecipeInputs {
    
    private final RecipeManager recipeManager;
    
    private List<RecipeHolder<WoodWorkingRecipe>> recipes = new ArrayList<>();
    
    public ServerWoodWorkingRecipeInputs(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }
    
    public void loadRecipes() {
        final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) { // Should never be null
            this.recipes = new ArrayList<>();
            this.recipeManager.recipeMap().byType(SetupRecipeType.WOODWORKING.get())
                    .forEach(element -> this.recipes.add(element));
        }
    }
    
    @Override
    public List<RecipeHolder<WoodWorkingRecipe>> recipes() {
        return this.recipes;
    }
    
    public void syncToClient(Stream<ServerPlayer> players) {
        final ClientboundWoodWorkingRecipesPayload payload = new ClientboundWoodWorkingRecipesPayload(this.recipes);
        players.forEach(player -> {
            PacketDistributor.sendToPlayer(player, payload);
        });
    }
}
