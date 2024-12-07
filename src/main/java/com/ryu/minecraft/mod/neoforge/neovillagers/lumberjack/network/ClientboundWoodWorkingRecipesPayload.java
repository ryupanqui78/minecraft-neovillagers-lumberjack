package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network;

import java.util.List;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.item.crafting.WoodWorkingRecipe;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public record ClientboundWoodWorkingRecipesPayload(List<RecipeHolder<WoodWorkingRecipe>> recipes) implements CustomPacketPayload {
    
    private static final StreamCodec<RegistryFriendlyByteBuf, RecipeHolder<WoodWorkingRecipe>> HOLDER_STREAM_CODEC = StreamCodec
            .composite(ResourceKey.streamCodec(Registries.RECIPE), RecipeHolder::id,
                    Recipe.STREAM_CODEC.map(WoodWorkingRecipe.class::cast, Recipe.class::cast), RecipeHolder::value,
                    RecipeHolder::new);
    private static final StreamCodec<RegistryFriendlyByteBuf, List<RecipeHolder<WoodWorkingRecipe>>> LIST_STREAM_CODEC = ClientboundWoodWorkingRecipesPayload.HOLDER_STREAM_CODEC
            .apply(ByteBufCodecs.list());
    public static final StreamCodec<ByteBuf, ClientboundWoodWorkingRecipesPayload> STREAM_CODEC = StreamCodec.composite(
            ClientboundWoodWorkingRecipesPayload.LIST_STREAM_CODEC.mapStream(RegistryFriendlyByteBuf.class::cast),
            ClientboundWoodWorkingRecipesPayload::recipes, ClientboundWoodWorkingRecipesPayload::new);
    
    public static final CustomPacketPayload.Type<ClientboundWoodWorkingRecipesPayload> TYPE_PAYLOAD = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(NeoVillagersLumberjack.MODID, "woodworking_recipe"));
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ClientboundWoodWorkingRecipesPayload.TYPE_PAYLOAD;
    }
    
}
