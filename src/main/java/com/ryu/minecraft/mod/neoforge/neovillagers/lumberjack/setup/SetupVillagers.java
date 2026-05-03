package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import org.jspecify.annotations.Nullable;

import com.google.common.collect.ImmutableSet;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
    
    public static final DeferredHolder<PoiType, PoiType> LUMBERJACK_POI = SetupVillagers.POI_TYPES
            .register("lumberjack", () -> SetupVillagers.createPoiType(SetupBlocks.WOODWORKING));
    
    public static final DeferredHolder<VillagerProfession, VillagerProfession> LUMBERJACK = SetupVillagers
            .createVillagerProfession("lumberjack", SetupVillagers.LUMBERJACK_POI, SoundEvents.VILLAGER_WORK_FARMER);
    
    private static PoiType createPoiType(DeferredBlock<? extends Block> block) {
        return new PoiType(ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()), 1, 1);
    }
    
    private static DeferredHolder<VillagerProfession, VillagerProfession> createVillagerProfession(String pName, DeferredHolder<PoiType, PoiType> pPoiType, @Nullable SoundEvent workSound) {
        final Component villager = Component.translatable("entity.neovillagers.villager." + pName);
        
        final VillagerProfession profession = new VillagerProfession(villager, x -> x.is(pPoiType.getKey()),
                x -> x.is(pPoiType.getKey()), ImmutableSet.of(), ImmutableSet.of(), workSound,
                // A map of profession level to trade set keys
                Int2ObjectMap.ofEntries( //
                        Int2ObjectMap.entry(1,
                                ResourceKey.create(Registries.TRADE_SET,
                                        Identifier.fromNamespaceAndPath(NeoVillagersLumberjack.MODID,
                                                pName + "/level_1"))),
                        Int2ObjectMap.entry(2,
                                ResourceKey.create(Registries.TRADE_SET,
                                        Identifier.fromNamespaceAndPath(NeoVillagersLumberjack.MODID,
                                                pName + "/level_2"))),
                        Int2ObjectMap.entry(3,
                                ResourceKey.create(Registries.TRADE_SET,
                                        Identifier.fromNamespaceAndPath(NeoVillagersLumberjack.MODID,
                                                pName + "/level_3"))),
                        Int2ObjectMap.entry(4,
                                ResourceKey.create(Registries.TRADE_SET,
                                        Identifier.fromNamespaceAndPath(NeoVillagersLumberjack.MODID,
                                                pName + "/level_4"))),
                        Int2ObjectMap.entry(5, ResourceKey.create(Registries.TRADE_SET,
                                Identifier.fromNamespaceAndPath(NeoVillagersLumberjack.MODID, pName + "/level_5")))));
        
        return SetupVillagers.VILLAGER_PROFESSIONS.register(pName, () -> profession);
    }
    
    public static void register(IEventBus eventBus) {
        SetupVillagers.POI_TYPES.register(eventBus);
        SetupVillagers.VILLAGER_PROFESSIONS.register(eventBus);
    }
    
    private SetupVillagers() {
    }
}
