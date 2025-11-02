package dev.vanadium.mod.block.common

import dev.vanadium.mod.blockentity.BlockEntityHandler
import dev.vanadium.mod.blockentity.source.LightSourceBlockEntity
import dev.vanadium.mod.light.LightColor
import dev.vanadium.mod.light.LightPathHandler
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Represents a block with a known facing and powered state
 */
abstract class AbstractSourceBlock(
    id: String,
    val color: LightColor
) : AbstractModBlockWithEntity(
    id,
    Settings
        .create()
        .hardness(Blocks.COBBLESTONE.hardness)
        .emissiveLighting { state, _, _ ->
            state[Properties.POWERED]
        }
        .luminance { state ->
            if (state[Properties.POWERED]) 10
            else 0
        }
        .requiresTool()
) {
    override fun neighborUpdate(
        state: BlockState,
        world: World,
        pos: BlockPos,
        sourceBlock: Block,
        sourcePos: BlockPos,
        notify: Boolean
    ) {
        LightPathHandler.recalculateLightPath(pos, color, state[FACING], world)
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify)
    }

    override fun createBlockEntity(
        pos: BlockPos,
        state: BlockState
    ): BlockEntity = LightSourceBlockEntity(pos, state)

    @Suppress("unchecked_cast")
    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return validateTicker(type, BlockEntityHandler.LIGHT_SOURCE_TYPE, { world, pos, state, entity ->
            LightSourceBlockEntity.Companion.tick(world, pos, state, color)
        }) ?: throw RuntimeException("Could not initialize ticker for light source block entity")
    }
}