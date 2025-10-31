package dev.vanadium.mod.light

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

data class LightSegment(
    val origin: BlockPos,
    val direction: Direction,
    val length: Float,
    val color: LightColor,
    val next: List<LightSegment>
)