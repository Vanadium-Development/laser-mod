package dev.vanadium.mod.block.common

import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

abstract class AbstractModBlockWithEntity(
    val id: String,
    settings: Settings
) : BlockWithEntity(settings) {
    companion object Companion {
        val FACING: DirectionProperty = Properties.FACING
        val POWERED: BooleanProperty = Properties.POWERED
    }

    init {
        defaultState = stateManager.defaultState
            .with(FACING, Direction.NORTH)
            .with(POWERED, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING, POWERED)
    }

    fun isPowered(
        world: World,
        pos: BlockPos
    ) = world.isReceivingRedstonePower(pos)

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        val facing = ctx.side
        val powered = isPowered(ctx.world, ctx.blockPos)
        return defaultState
            .with(FACING, facing)
            .with(POWERED, powered)
    }

    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    override fun neighborUpdate(
        state: BlockState,
        world: World,
        pos: BlockPos,
        sourceBlock: Block,
        sourcePos: BlockPos,
        notify: Boolean
    ) {
        if (world.isClient)
            return

        val powered = isPowered(world, pos)

        if (powered == state[POWERED])
            return

        world.setBlockState(
            pos,
            state.with(
                POWERED,
                powered,
            ),
            NOTIFY_ALL
        )
    }
}