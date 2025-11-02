package dev.vanadium.mod.blockentity.source

import dev.vanadium.mod.blockentity.BlockEntityHandler
import dev.vanadium.mod.light.LightColor
import dev.vanadium.mod.light.LightPathHandler
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class LightSourceBlockEntity(
    pos: BlockPos,
    state: BlockState,
) : BlockEntity(BlockEntityHandler.LIGHT_SOURCE_TYPE, pos, state) {

    companion object {
        fun tick(
            world: World,
            pos: BlockPos,
            state: BlockState,
            color: LightColor
        ) {
            LightPathHandler.recalculateLightPath(pos, color, state[Properties.FACING], world)
        }
    }

}