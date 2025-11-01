package dev.vanadium.mod.client.render

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gl.ShaderProgram
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import org.lwjgl.opengl.GL11

object RenderLayerHandler {
    lateinit var LIGHT_BEAM_LAYER: RenderLayer

    fun initialize() {
        LIGHT_BEAM_LAYER = RenderLayer.of(
            "vmod:light_beam_layer",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,

            // Enough space for 20 beams
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL.vertexSizeByte * 16 * 20,

            RenderLayer.MultiPhaseParameters.builder()
                .depthTest(RenderPhase.DepthTest.LEQUAL_DEPTH_TEST)
                .program(RenderPhase.LIGHTNING_PROGRAM)
                .transparency(RenderPhase.LIGHTNING_TRANSPARENCY)
                .build(false)
        )
    }
}