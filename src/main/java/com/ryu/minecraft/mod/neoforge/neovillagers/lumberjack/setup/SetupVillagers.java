package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.google.common.collect.ImmutableSet;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.villagers.Lumberjack;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
    
    public static final ResourceKey<VillagerProfession> LUMBERJACK = SetupVillagers.createKey(Lumberjack.ENTITY_NAME);
    
    public static final DeferredHolder<PoiType, PoiType> LUMBERJACK_POI = SetupVillagers.POI_TYPES
            .register(Lumberjack.ENTITY_POI_NAME,
                    () -> new PoiType(
                            ImmutableSet.copyOf(SetupBlocks.WOODWORKING.get().getStateDefinition().getPossibleStates()),
                            1, 1));
    
    static {
        SetupVillagers.VILLAGER_PROFESSIONS.register(Lumberjack.ENTITY_NAME, SetupVillagers::registerVillager);
    }
    
    private static ResourceKey<VillagerProfession> createKey(String pName) {
        return ResourceKey.create(Registries.VILLAGER_PROFESSION,
                ResourceLocation.fromNamespaceAndPath(NeoVillagersLumberjack.MODID, pName));
    }
    
    public static void register(IEventBus eventBus) {
        SetupVillagers.POI_TYPES.register(eventBus);
        SetupVillagers.VILLAGER_PROFESSIONS.register(eventBus);
    }
    
    private static VillagerProfession registerVillager() {
        final ResourceLocation villagerResource = ResourceLocation.fromNamespaceAndPath(NeoVillagersLumberjack.MODID,
                Lumberjack.ENTITY_NAME);
        final Component villager = Component
                .translatable("entity." + villagerResource.getNamespace() + ".villager." + villagerResource.getPath());
        return new VillagerProfession(villager, x -> x.is(SetupVillagers.LUMBERJACK_POI.getKey()),
                x -> x.is(SetupVillagers.LUMBERJACK_POI.getKey()), ImmutableSet.of(), ImmutableSet.of(),
                SoundEvents.VILLAGER_WORK_FARMER);
    }
    
    private SetupVillagers() {
    }
}
