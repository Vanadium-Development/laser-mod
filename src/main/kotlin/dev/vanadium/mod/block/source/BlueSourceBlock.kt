package dev.vanadium.mod.block.source

import com.mojang.serialization.MapCodec
import dev.vanadium.mod.light.LightColor
import net.minecraft.block.BlockWithEntity

class BlueSourceBlock : AbstractSourceBlock("source_blue", LightColor.BLUE) {
    override fun getCodec(): MapCodec<out BlockWithEntity> {
        return createCodec { BlueSourceBlock() }
    }
}