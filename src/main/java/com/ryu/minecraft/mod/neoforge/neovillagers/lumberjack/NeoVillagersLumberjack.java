package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network.ClientWoodWorkingRecipes;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network.ClientboundWoodWorkingRecipesPayload;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.network.ServerWoodWorkingRecipes;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupGeneralEvents;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupMenus;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeSerializer;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeType;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupVillagers;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(NeoVillagersLumberjack.MODID)
public class NeoVillagersLumberjack {
    
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "neovillagerslumberjack";
    
    public static final ExtendedRecipeBookCategory WOODWORKING_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {
    };
    
    public NeoVillagersLumberjack(IEventBus modEventBus, ModContainer modContainer) {
        SetupBlocks.BLOCKS.register(modEventBus);
        SetupBlocks.ITEMS.register(modEventBus);
        
        SetupMenus.MENUS.register(modEventBus);
        
        NeoForge.EVENT_BUS.register(SetupGeneralEvents.class);
        NeoForge.EVENT_BUS.register(ServerWoodWorkingRecipes.class);
        SetupVillagers.register(modEventBus);
        
        SetupRecipeType.REGISTER.register(modEventBus);
        SetupRecipeSerializer.REGISTER.register(modEventBus);
        
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerPayloadHandlersEvent);
        
        NeoForge.EVENT_BUS.addListener(ServerWoodWorkingRecipes::onPlayerLoggedIn);
    }
    
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(SetupBlocks.WOODWORKING_TABLE);
        }
    }
    
    public void registerPayloadHandlersEvent(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(ClientboundWoodWorkingRecipesPayload.TYPE_PAYLOAD,
                ClientboundWoodWorkingRecipesPayload.STREAM_CODEC, ClientWoodWorkingRecipes::handle);
    }
    
    @SubscribeEvent
    public void registerSearchCategories(RegisterRecipeBookSearchCategoriesEvent event) {
        event.register(NeoVillagersLumberjack.WOODWORKING_SEARCH_CATEGORY, SetupRecipeType.WOODWORKING_CATEGORY.get());
    }
}
