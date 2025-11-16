package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup;

import java.util.function.Function;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.NeoVillagersLumberjack;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.block.WoodWorkingBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlocks {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoVillagersLumberjack.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoVillagersLumberjack.MODID);
    
    public static final DeferredBlock<WoodWorkingBlock> WOODWORKING = SetupBlocks
            .registerSingleBlock(WoodWorkingBlock.BLOCK_NAME, WoodWorkingBlock::new, 1.5f);
    
    private static <B extends Block> DeferredBlock<B> registerSingleBlock(String pName, Function<BlockBehaviour.Properties, ? extends B> func, float pStrength) {
        final DeferredBlock<B> block = SetupBlocks.BLOCKS.registerBlock(pName, func,
                BlockBehaviour.Properties.of().strength(pStrength).requiresCorrectToolForDrops());
        ITEMS.registerSimpleBlockItem(block);
        return block;
    }
    
    private SetupBlocks() {
    }
    
}
