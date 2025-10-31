package dev.vanadium.mod.block.source

import com.mojang.serialization.MapCodec
import dev.vanadium.mod.light.LightColor
import net.minecraft.block.BlockWithEntity

class WhiteSourceBlock : AbstractSourceBlock("source_white", LightColor.WHITE) {
    override fun getCodec(): MapCodec<out BlockWithEntity> {
        return createCodec { WhiteSourceBlock() }
    }
}