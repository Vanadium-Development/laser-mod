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

object LightPathHandler {
    val LIGHT_PATHS = hashMapOf<BlockPos, LightSegment>()
    val MAX_RAY_LENGTH = 20f

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

    private fun nextSegment(
        source: BlockPos,
        direction: Direction,
        world: World,
        sourceColor: LightColor,
    ): LightSegment {
        val segment = LightSegment(source, direction, 0f, sourceColor, mutableListOf())

        // Find origin vector (centered, match direction)
        val sourceType = world.getBlockEntity(source)

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
        val end = lightOrigin.add(direction.unitVector.mul(MAX_RAY_LENGTH).toVector3d())
        segment.length = MAX_RAY_LENGTH

        val hit = rayCast(lightOrigin, end, world) ?: return segment
        segment.length = hit.pos.distanceTo(lightOrigin).toFloat()

        val entity = world.getBlockEntity(hit.blockPos) ?: return segment
        if (entity !is MirrorBlockEntity) return segment
        segment.length += .5f // Mirrors are at the center of the block model

        val nextDirection = entity.cachedState[Properties.FACING]
        segment.next.add(
            nextSegment(entity.pos, nextDirection, world, sourceColor)
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