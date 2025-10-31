package dev.vanadium.mod.client.blockentity.renderer

import dev.vanadium.mod.blockentity.source.LightSourceBlockEntity
import dev.vanadium.mod.client.light.LightPathRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.util.math.MatrixStack

@Environment(EnvType.CLIENT)
class LightSourceRenderer(ctx: BlockEntityRendererFactory.Context) : BlockEntityRenderer<LightSourceBlockEntity> {
    private val renderer = LightPathRenderer(ctx)

    override fun render(
        entity: LightSourceBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        renderer.renderFor(entity, matrices, vertexConsumers)
    }

}