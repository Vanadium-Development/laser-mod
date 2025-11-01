package dev.vanadium.mod.light

import dev.vanadium.mod.blockentity.mirror.MirrorBlockEntity
import net.minecraft.block.ShapeContext
import net.minecraft.state.property.Properties
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import org.joml.Vector3f
import kotlin.math.max

object LightPathHandler {
    val LIGHT_PATHS = hashMapOf<BlockPos, LightSegment>()

    val POWER_FALLOFF = 0.05f

    private fun rayCast(
        start: Vec3d,
        end: Vec3d,
        world: World
    ): BlockHitResult? {
        val ctx = RaycastContext(
            start,
            end,
            RaycastContext.ShapeType.OUTLINE,
            RaycastContext.FluidHandling.NONE,
            ShapeContext.absent()
        )

        return world.raycast(ctx)
    }

    private fun Vector3f.toVector3d() = Vec3d(x.toDouble(), y.toDouble(), z.toDouble())

    private fun calculatePowerAfter(
        startPower: Float,
        distance: Float
    ): Float = max(startPower - (distance * POWER_FALLOFF), 0f)

    private fun calculateMaxLengthWithPower(power: Float) = power / POWER_FALLOFF

    private fun mirrorReflect(
        incoming: Direction,
        facing: Direction
    ): Direction? {
        val reflection = facing.rotateYClockwise()

        return when (incoming.opposite) {
            facing -> reflection
            reflection -> facing
            else -> null
        }
    }

    private fun nextSegment(
        source: BlockPos,
        direction: Direction,
        world: World,
        sourceColor: LightColor,
        sourceType: LightSourceType = LightSourceType.SOURCE_BLOCK,
        power: Float = 1f
    ): LightSegment {
        val segment = LightSegment(source, sourceType, direction, 0f, sourceColor, mutableListOf(), power, 0f)

        val lightOrigin = Vec3d(
            source.x.toDouble(),
            source.y.toDouble(),
            source.z.toDouble()
        ).add(.5, .5, .5).add(
            Vec3d(
                direction.offsetX.toDouble(),
                direction.offsetY.toDouble(),
                direction.offsetZ.toDouble()
            ).multiply(.5)
        )

        // Pick an end point
        val maxLength = calculateMaxLengthWithPower(power)
        val end = lightOrigin.add(direction.unitVector.mul(maxLength).toVector3d())
        segment.length = maxLength

        val hit = rayCast(lightOrigin, end, world) ?: return segment
        segment.length = hit.pos.distanceTo(lightOrigin).toFloat()
        segment.endPower = calculatePowerAfter(power, segment.length)

        val entity = world.getBlockEntity(hit.blockPos) ?: return segment
        if (entity !is MirrorBlockEntity) return segment
        val nextDirection = mirrorReflect(direction, entity.cachedState[Properties.FACING]) ?: return segment
        segment.length += .5f // Mirrors are at the center of the block model

        val nextInitialPower = calculatePowerAfter(power, segment.length)
        segment.endPower = nextInitialPower
        segment.next.add(
            nextSegment(entity.pos, nextDirection, world, sourceColor, LightSourceType.MIRROR, power = nextInitialPower)
        )

        return segment
    }

    fun recalculateLightPath(
        pos: BlockPos,
        color: LightColor,
        facing: Direction,
        world: World,
    ) {
        val segment = nextSegment(pos, facing, world, color)
        LIGHT_PATHS[pos] = segment
    }

}