package dev.vanadium.mod.block.source

import com.mojang.serialization.MapCodec
import dev.vanadium.mod.block.common.AbstractSourceBlock
import dev.vanadium.mod.light.LightColor
import net.minecraft.block.BlockWithEntity

class RedSourceBlock : AbstractSourceBlock("source_red", LightColor.RED) {
    override fun getCodec(): MapCodec<out BlockWithEntity> {
        return createCodec { RedSourceBlock() }
    }
}