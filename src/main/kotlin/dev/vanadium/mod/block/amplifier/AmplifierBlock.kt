package dev.vanadium.mod.block.amplifier

import com.mojang.serialization.MapCodec
import dev.vanadium.mod.block.common.AbstractModBlockWithEntity
import dev.vanadium.mod.blockentity.amplifier.AmplifierBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class AmplifierBlock : AbstractModBlockWithEntity(
    "amp",
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
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return defaultState
            .with(POWERED, isPowered(ctx.world, ctx.blockPos))
            .with(FACING, ctx.player?.horizontalFacing?.opposite ?: Direction.NORTH)
    }

    override fun getCodec(): MapCodec<out BlockWithEntity> {
        return createCodec { AmplifierBlock() }
    }

    override fun createBlockEntity(
        pos: BlockPos,
        state: BlockState
    ): BlockEntity = AmplifierBlockEntity(pos, state)
}