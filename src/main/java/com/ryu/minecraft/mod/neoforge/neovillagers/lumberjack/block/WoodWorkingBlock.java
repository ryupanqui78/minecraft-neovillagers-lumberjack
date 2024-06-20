package com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.block;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.entity.WoodWorkingBlockEntity;
import com.ryu.minecraft.mod.neoforge.neovillagers.lumberjack.inventory.WoodWorkingMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodWorkingBlock extends BaseEntityBlock {
    
    public static final String BLOCK_NAME = "woodworking_table";
    public static final MapCodec<WoodWorkingBlock> CODEC = BlockBehaviour.simpleCodec(WoodWorkingBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    
    private static final Component CONTAINER_TITLE = Component.translatable("screen.container.woodworking");
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    
    public WoodWorkingBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WoodWorkingBlock.FACING, Direction.NORTH));
    }
    
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return WoodWorkingBlock.CODEC;
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WoodWorkingBlock.FACING);
    }
    
    @Override
    protected MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        return new SimpleMenuProvider((pContainerId, playerInv, pAccess) -> new WoodWorkingMenu(pContainerId, playerInv,
                ContainerLevelAccess.create(pLevel, pPos)), WoodWorkingBlock.CONTAINER_TITLE);
    }
    
    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    
    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter getter, BlockPos pPos, CollisionContext pContext) {
        return WoodWorkingBlock.SHAPE;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(WoodWorkingBlock.FACING,
                pContext.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WoodWorkingBlockEntity(pPos, pState);
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide && (pPlayer instanceof final ServerPlayer serverPlayer)) {
            serverPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
}
