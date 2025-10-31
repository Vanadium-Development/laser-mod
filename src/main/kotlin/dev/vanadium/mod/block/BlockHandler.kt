package dev.vanadium.mod.block

import dev.vanadium.mod.VanadiumMod
import dev.vanadium.mod.block.mirror.MirrorBlock
import dev.vanadium.mod.block.source.BlueSourceBlock
import dev.vanadium.mod.block.source.GreenSourceBlock
import dev.vanadium.mod.block.source.RedSourceBlock
import dev.vanadium.mod.block.source.WhiteSourceBlock
import dev.vanadium.mod.inventory.InventoryHandler
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlockHandler {

    lateinit var RED_SOURCE: RedSourceBlock
    lateinit var BLUE_SOURCE: BlueSourceBlock
    lateinit var GREEN_SOURCE: GreenSourceBlock
    lateinit var WHITE_SOURCE: WhiteSourceBlock

    lateinit var MIRROR: MirrorBlock

    fun <T : AbstractModBlock> registerBlock(
        block: T,
        inventory: InventoryHandler
    ): Pair<BlockItem, T> {
        // Register Block
        val id = Identifier.of(VanadiumMod.MOD_ID, block.id)
        val blk = Registry.register(Registries.BLOCK, id, block)
        val item = Registry.register(Registries.ITEM, id, BlockItem(block, Item.Settings()))

        inventory.add(item)

        return item to blk
    }

    fun registerBlocks(inventory: InventoryHandler) {
        // Use the red laser source as the inventory tab icon
        val (icon, redSource) = registerBlock(
            RedSourceBlock(),
            inventory
        )
        inventory.icon(icon)

        RED_SOURCE = redSource

        BLUE_SOURCE = registerBlock(
            BlueSourceBlock(),
            inventory
        ).second

        GREEN_SOURCE = registerBlock(
            GreenSourceBlock(),
            inventory
        ).second

        WHITE_SOURCE = registerBlock(
            WhiteSourceBlock(),
            inventory
        ).second

        MIRROR = registerBlock(
            MirrorBlock(),
            inventory
        ).second
    }

}