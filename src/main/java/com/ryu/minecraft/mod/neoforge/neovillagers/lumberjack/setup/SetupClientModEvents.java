package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.client.WoodWorkingScreen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod.EventBusSubscriber(modid = NeoVillagersLumberjack.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupClientModEvents {
    
    @SubscribeEvent
    public static void registerMenuSreen(RegisterMenuScreensEvent event) {
        event.register(SetupMenus.WOODWORKING_CONTAINER.get(), WoodWorkingScreen::new);
    }
    
    private SetupClientModEvents() {
        
    }
}
