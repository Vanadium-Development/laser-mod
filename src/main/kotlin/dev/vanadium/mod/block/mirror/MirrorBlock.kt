package dev.vanadium.mod.block.mirror

import com.mojang.serialization.MapCodec
import dev.vanadium.mod.block.AbstractModBlock
import dev.vanadium.mod.blockentity.mirror.MirrorBlockEntity
import net.minecraft.block.AbstractBlock
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

class MirrorBlock : AbstractModBlock(
    "mirror",
    AbstractBlock.Settings.create()
        .nonOpaque()
        .hardness(Blocks.IRON_BLOCK.hardness)
        .requiresTool()
) {
    override fun getCodec(): MapCodec<out BlockWithEntity?> {
        return createCodec { MirrorBlock() }
    }

    override fun createBlockEntity(
        pos: BlockPos,
        state: BlockState
    ): BlockEntity = MirrorBlockEntity(pos, state)
}