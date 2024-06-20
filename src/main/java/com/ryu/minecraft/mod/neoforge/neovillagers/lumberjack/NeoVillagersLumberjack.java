package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlockEntity;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupGeneralEvents;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupMenus;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeSerializer;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupRecipeType;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupVillagers;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(NeoVillagersLumberjack.MODID)
public class NeoVillagersLumberjack {
    
    public static final String MODID = "neovillagerslumberjack";
    
    public NeoVillagersLumberjack(IEventBus modEventBus, ModContainer modContainer) {
        SetupBlocks.BLOCKS.register(modEventBus);
        SetupBlocks.ITEMS.register(modEventBus);
        
        SetupBlockEntity.BLOCK_ENTITIES.register(modEventBus);
        SetupMenus.MENUS.register(modEventBus);
        
        NeoForge.EVENT_BUS.register(SetupGeneralEvents.class);
        
        SetupVillagers.register(modEventBus);
        
        SetupRecipeType.REGISTER.register(modEventBus);
        SetupRecipeSerializer.REGISTER.register(modEventBus);
        
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }
    
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(SetupBlocks.WOODWORKING_TABLE);
        }
    }
}
