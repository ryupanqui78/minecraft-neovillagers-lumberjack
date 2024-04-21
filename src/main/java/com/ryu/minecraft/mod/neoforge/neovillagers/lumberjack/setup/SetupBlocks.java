package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.block.WoodWorkingBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlocks {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoVillagersLumberjack.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoVillagersLumberjack.MODID);
    
    public static final DeferredBlock<WoodWorkingBlock> WOODWORKING_TABLE = BLOCKS.registerBlock(
            WoodWorkingBlock.BLOCK_NAME, WoodWorkingBlock::new,
            BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops());
    
    public static final DeferredItem<BlockItem> WOODWORKING_TABLE_ITEM = ITEMS
            .registerSimpleBlockItem(WOODWORKING_TABLE);
    
    private SetupBlocks() {
    }
    
}
