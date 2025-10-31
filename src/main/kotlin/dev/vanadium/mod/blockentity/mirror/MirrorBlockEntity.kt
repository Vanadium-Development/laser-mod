package dev.vanadium.mod.blockentity.mirror

import dev.vanadium.mod.blockentity.BlockEntityHandler
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class MirrorBlockEntity(
    pos: BlockPos,
    state: BlockState
) : BlockEntity(BlockEntityHandler.MIRROR_TYPE, pos, state) {
}