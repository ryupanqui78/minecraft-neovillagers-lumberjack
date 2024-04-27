package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.Lumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.Worker;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

@Mod.EventBusSubscriber(modid = NeoVillagersLumberjack.MODID)
public class SetupTraders {
    
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == SetupVillagers.LUMBERJACK.get()) {
            final Worker worker = new Lumberjack();
            worker.getTrades(event);
        }
    }
    
    private SetupTraders() {
    }
}
