package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.entity.WoodWorkingBlockEntity;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.inventory.WoodWorkingMenu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlockEntity {
    
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NeoVillagersLumberjack.MODID);
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodWorkingBlockEntity>> WOODWORKING_TABLE = SetupBlockEntity.BLOCK_ENTITIES
            .register(WoodWorkingMenu.MENU_NAME, () -> BlockEntityType.Builder
                    .of(WoodWorkingBlockEntity::new, SetupBlocks.WOODWORKING_TABLE.get()).build(null));
    
    private SetupBlockEntity() {
    }
}
