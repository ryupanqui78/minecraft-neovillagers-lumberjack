package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.entity;

import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.setup.SetupBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WoodWorkingBlockEntity extends BlockEntity {
    
    public WoodWorkingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SetupBlockEntity.WOODWORKING_TABLE.get(), pPos, pBlockState);
    }
    
}
