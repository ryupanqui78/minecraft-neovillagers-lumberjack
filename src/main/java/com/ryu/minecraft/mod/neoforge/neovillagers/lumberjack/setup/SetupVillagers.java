package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.google.common.collect.ImmutableSet;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.Lumberjack;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupVillagers {
    
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister
            .create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, NeoVillagersLumberjack.MODID);
    
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister
            .create(BuiltInRegistries.VILLAGER_PROFESSION, NeoVillagersLumberjack.MODID);
    
    public static final ResourceKey<VillagerProfession> LUMBERJACK = SetupVillagers.createKey(Lumberjack.ENTITY_NAME);
    
    public static final DeferredHolder<PoiType, PoiType> LUMBERJACK_POI = SetupVillagers.POI_TYPES
            .register(Lumberjack.ENTITY_POI_NAME, () -> SetupVillagers.createPoiType(SetupBlocks.WOODWORKING));
    
    static {
        SetupVillagers.VILLAGER_PROFESSIONS.register(Lumberjack.ENTITY_NAME, Lumberjack::registerVillager);
    }
    
    private static ResourceKey<VillagerProfession> createKey(String pName) {
        return ResourceKey.create(Registries.VILLAGER_PROFESSION,
                Identifier.fromNamespaceAndPath(NeoVillagersLumberjack.MODID, pName));
    }
    
    private static PoiType createPoiType(DeferredBlock<? extends Block> block) {
        return new PoiType(ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()), 1, 1);
    }
    
    public static void register(IEventBus eventBus) {
        SetupVillagers.POI_TYPES.register(eventBus);
        SetupVillagers.VILLAGER_PROFESSIONS.register(eventBus);
    }
    
    private SetupVillagers() {
    }
}
