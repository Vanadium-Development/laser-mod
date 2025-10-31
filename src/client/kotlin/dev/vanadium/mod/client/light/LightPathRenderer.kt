package dev.vanadium.mod.client.light

import dev.vanadium.mod.client.render.RenderLayerHandler
import dev.vanadium.mod.light.LightColor
import dev.vanadium.mod.light.LightPathHandler
import dev.vanadium.mod.light.LightSegment
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.state.property.Properties
import org.joml.Matrix4f
import org.joml.Vector3f

class LightPathRenderer(
    val context: BlockEntityRendererFactory.Context
) {
    private val beamSize: Float = 0.15f

    fun VertexConsumer.vert(
        positionMatrix: Matrix4f,
        x: Float,
        y: Float,
        z: Float,
        u: Float,
        v: Float,
        normal: Vector3f,
        red: Int,
        green: Int,
        blue: Int,
        alpha: Int
    ) {
        vertex(positionMatrix, x, y, z)
            .color(red, green, blue, alpha)
            .texture(u, v)
            .light(0xF000F0)
            .overlay(OverlayTexture.DEFAULT_UV)
            .normal(normal.x, normal.y, normal.z)
    }

    private fun renderNorthBeamFace(
        buffer: VertexConsumer,
        pMat: Matrix4f,
        length: Float,
        lightColor: LightColor
    ) {
        val normal = Vector3f(0f, 0f, -1f)
        buffer.vert(
            pMat,
            -beamSize / 2f, length, -beamSize / 2f,
            1f, 1f, normal,
            lightColor.r, lightColor.g, lightColor.b, 0
        )
        buffer.vert(
            pMat,
            beamSize / 2, length, -beamSize / 2f,
            0f, 1f, normal,
            lightColor.r, lightColor.g, lightColor.b, 0
        )
        buffer.vert(
            pMat,
            beamSize / 2, 0f, -beamSize / 2f,
            0f, 0f, normal,
            lightColor.r, lightColor.g, lightColor.b, 255
        )
        buffer.vert(
            pMat,
            -beamSize / 2f, 0f, -beamSize / 2f,
            1f, 0f, normal,
            lightColor.r, lightColor.g, lightColor.b, 255
        )
    }

    private fun renderEastBeamFace(
        buffer: VertexConsumer,
        pMat: Matrix4f,
        length: Float,
        lightColor: LightColor
    ) {
        val normal = Vector3f(0f, 0f, 1f)
        buffer.vert(
            pMat,
            beamSize / 2, length, -beamSize / 2f,
            1f, 1f, normal,
            lightColor.r, lightColor.g, lightColor.b, 0
        )
        buffer.vert(
            pMat,
            beamSize / 2, length, beamSize / 2,
            0f, 1f, normal,
            lightColor.r, lightColor.g, lightColor.b, 0
        )
        buffer.vert(
            pMat,
            beamSize / 2, 0f, beamSize / 2,
            0f, 0f, normal,
            lightColor.r, lightColor.g, lightColor.b, 255
        )
        buffer.vert(
            pMat,
            beamSize / 2, 0f, -beamSize / 2f,
            1f, 0f, normal,
            lightColor.r, lightColor.g, lightColor.b, 255
        )
    }

    private fun renderWestBeamFace(
        buffer: VertexConsumer,
        pMat: Matrix4f,
        length: Float,
        lightColor: LightColor
    ) {
        val normal = Vector3f(-1f, 0f, 0f)
        buffer.vert(
            pMat,
            -beamSize / 2, 0f, -beamSize / 2,
            1f, 0f, normal,
            lightColor.r, lightColor.g, lightColor.b, 255
        )
        buffer.vert(
            pMat,
            -beamSize / 2, 0f, beamSize / 2,
            0f, 0f, normal,
            lightColor.r, lightColor.g, lightColor.b, 255
        )
        buffer.vert(
            pMat,
            -beamSize / 2, length, beamSize / 2,
            0f, 1f, normal,
            lightColor.r, lightColor.g, lightColor.b, 0,
        )
        buffer.vert(
            pMat,
            -beamSize / 2, length, -beamSize / 2,
            1f, 1f, normal,
            lightColor.r, lightColor.g, lightColor.b, 0
        )
    }

    private fun renderSouthBeamFace(
        buffer: VertexConsumer,
        pMat: Matrix4f,
        length: Float,
        lightColor: LightColor
    ) {
        val normal = Vector3f(1f, 0f, 0f)
        buffer.vert(
            pMat,
            -beamSize / 2, 0f, beamSize / 2,
            1f, 0f, normal,
            lightColor.r, lightColor.g, lightColor.b, 255
        )
        buffer.vert(
            pMat,
            beamSize / 2, 0f, beamSize / 2,
            0f, 0f, normal,
            lightColor.r, lightColor.g, lightColor.b, 255
        )
        buffer.vert(
            pMat,
            beamSize / 2, length, beamSize / 2,
            0f, 1f, normal,
            lightColor.r, lightColor.g, lightColor.b, 0
        )
        buffer.vert(
            pMat,
            -beamSize / 2, length, beamSize / 2,
            1f, 1f, normal,
            lightColor.r, lightColor.g, lightColor.b, 0
        )
    }

    fun renderLightSegment(
        lightSegment: LightSegment,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
    ) {
        matrices.push()

        val buffer = vertexConsumers.getBuffer(RenderLayerHandler.LIGHT_BEAM_LAYER)
        val color = lightSegment.color
        val length = lightSegment.length

        val direction = lightSegment.direction
        val center = Vector3f(.5f, .5f, .5f)
        val offset = Vector3f(direction.offsetX.toFloat(), direction.offsetY.toFloat(), direction.offsetZ.toFloat())
            .mul(0.5f)
        val location = center.add(offset)

        matrices.translate(location.x, location.y, location.z)
        matrices.multiply(direction.rotationQuaternion)

        val pMat = matrices.peek().positionMatrix

        renderNorthBeamFace(buffer, pMat, length, color)
        renderEastBeamFace(buffer, pMat, length, color)
        renderSouthBeamFace(buffer, pMat, length, color)
        renderWestBeamFace(buffer, pMat, length, color)

        matrices.pop()
    }

    fun <T : BlockEntity> forceRenderFor(
        entity: T,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider
    ) {
        val lightPath = LightPathHandler.LIGHT_PATHS[entity.pos] ?: return
        renderLightSegment(lightPath, matrices, vertexConsumers)

        lightPath.next.forEach {
            renderLightSegment(it, matrices, vertexConsumers)
        }
    }

    fun <T : BlockEntity> renderFor(
        entity: T,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider
    ) {
        if (!entity.cachedState[Properties.POWERED])
            return

        forceRenderFor(entity, matrices, vertexConsumers)
    }

}