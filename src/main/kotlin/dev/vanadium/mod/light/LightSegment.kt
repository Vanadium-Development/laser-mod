package dev.vanadium.mod.light

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

data class LightSegment(
    var origin: BlockPos,
    var direction: Direction,
    var length: Float,
    var color: LightColor,
    val next: MutableList<LightSegment>
)