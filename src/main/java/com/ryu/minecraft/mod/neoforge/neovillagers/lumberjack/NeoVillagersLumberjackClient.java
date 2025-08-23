package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.client.WoodWorkingScreen;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupMenus;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = NeoVillagersLumberjack.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = NeoVillagersLumberjack.MODID, value = Dist.CLIENT)
public class NeoVillagersLumberjackClient {
    
    public NeoVillagersLumberjackClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    
    @SubscribeEvent
    public static void registerMenuSreen(RegisterMenuScreensEvent event) {
        event.register(SetupMenus.WOODWORKING_CONTAINER.get(), WoodWorkingScreen::new);
    }
    
}
