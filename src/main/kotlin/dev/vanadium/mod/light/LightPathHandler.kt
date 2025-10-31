package dev.vanadium.mod.light

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

object LightPathHandler {
    val LIGHT_PATHS = hashMapOf<BlockPos, LightSegment>()

    fun recalculateLightPath(
        pos: BlockPos,
        color: LightColor,
        facing: Direction
    ) {
        LIGHT_PATHS[pos] = LightSegment(
            pos,
            facing,
            10.0f,
            color,
            listOf()
        )
    }

}