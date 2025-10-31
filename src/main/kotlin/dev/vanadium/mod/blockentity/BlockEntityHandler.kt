package dev.vanadium.mod.blockentity

import dev.vanadium.mod.block.BlockHandler
import dev.vanadium.mod.blockentity.mirror.MirrorBlockEntity
import dev.vanadium.mod.blockentity.source.LightSourceBlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlockEntityHandler {
    lateinit var LIGHT_SOURCE_TYPE: BlockEntityType<LightSourceBlockEntity>
    lateinit var MIRROR_TYPE: BlockEntityType<MirrorBlockEntity>

    fun <T : BlockEntityType<*>> register(
        path: String,
        blockEntityType: T
    ): T = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("vmod", path), blockEntityType)

    fun initialize() {
        LIGHT_SOURCE_TYPE = register(
            "source_blue", BlockEntityType.Builder.create(
                { pos, state -> LightSourceBlockEntity(pos, state) },
                BlockHandler.RED_SOURCE, BlockHandler.GREEN_SOURCE, BlockHandler.BLUE_SOURCE,
                BlockHandler.WHITE_SOURCE
            ).build()
        )
        MIRROR_TYPE = register(
            "mirror", BlockEntityType.Builder.create(
                { pos, state -> MirrorBlockEntity(pos, state) },
                BlockHandler.MIRROR
            ).build()
        )
    }

}