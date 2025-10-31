package dev.vanadium.mod.client

import dev.vanadium.mod.blockentity.BlockEntityHandler
import dev.vanadium.mod.client.blockentity.renderer.LightSourceRenderer
import dev.vanadium.mod.client.render.RenderLayerHandler
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories

class VanadiumModClient : ClientModInitializer {

    override fun onInitializeClient() {
        RenderLayerHandler.initialize()

        BlockEntityRendererFactories.register(
            BlockEntityHandler.LIGHT_SOURCE_TYPE,
            { ctx -> LightSourceRenderer(ctx) }
        )
    }

}
