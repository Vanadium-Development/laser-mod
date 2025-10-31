package dev.vanadium.mod

import dev.vanadium.mod.block.BlockHandler
import dev.vanadium.mod.blockentity.BlockEntityHandler
import dev.vanadium.mod.inventory.InventoryHandler
import dev.vanadium.mod.translations.TranslationKey
import net.fabricmc.api.ModInitializer

class VanadiumMod : ModInitializer {

    companion object {
        val MOD_ID = "vmod"
    }

    override fun onInitialize() {
        val inventoryHandler = InventoryHandler(
            "inventory",
            TranslationKey.INVENTORY_TAB
        )
        BlockHandler.registerBlocks(inventoryHandler)
        inventoryHandler.register()
        BlockEntityHandler.initialize()
    }
}
