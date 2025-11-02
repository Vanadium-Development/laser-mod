package dev.vanadium.mod.blockentity.amplifier

import dev.vanadium.mod.blockentity.BlockEntityHandler
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class AmplifierBlockEntity(
    pos: BlockPos,
    state: BlockState
) : BlockEntity(BlockEntityHandler.AMPLIFIER_TYPE, pos, state)