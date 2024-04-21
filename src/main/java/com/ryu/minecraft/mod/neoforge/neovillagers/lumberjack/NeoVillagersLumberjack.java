package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlocks;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(NeoVillagersLumberjack.MODID)
public class NeoVillagersLumberjack {
    
    public static final String MODID = "neovillagerslumberjack";
    
    public NeoVillagersLumberjack(IEventBus modEventBus) {
        SetupBlocks.ITEMS.register(modEventBus);
        SetupBlocks.BLOCKS.register(modEventBus);
        
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }
    
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(SetupBlocks.WOODWORKING_TABLE);
        }
    }
}
