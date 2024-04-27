package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.google.common.collect.ImmutableSet;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.Lumberjack;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupVillagers {
    
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister
            .create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, NeoVillagersLumberjack.MODID);
    
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister
            .create(BuiltInRegistries.VILLAGER_PROFESSION, NeoVillagersLumberjack.MODID);
    
    public static final DeferredHolder<PoiType, PoiType> LUMBERJACK_POI = SetupVillagers.POI_TYPES.register(
            Lumberjack.ENTITY_POI_NAME,
            () -> new PoiType(
                    ImmutableSet.copyOf(SetupBlocks.WOODWORKING_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));
    
    public static final DeferredHolder<VillagerProfession, VillagerProfession> LUMBERJACK = SetupVillagers.VILLAGER_PROFESSIONS
            .register(Lumberjack.ENTITY_NAME,
                    () -> new VillagerProfession(Lumberjack.ENTITY_NAME,
                            x -> x.is(SetupVillagers.LUMBERJACK_POI.getKey()),
                            x -> x.is(SetupVillagers.LUMBERJACK_POI.getKey()), ImmutableSet.of(), ImmutableSet.of(),
                            SoundEvents.VILLAGER_WORK_FARMER));
    
    public static void register(IEventBus eventBus) {
        SetupVillagers.POI_TYPES.register(eventBus);
        SetupVillagers.VILLAGER_PROFESSIONS.register(eventBus);
    }
    
    private SetupVillagers() {
    }
}
