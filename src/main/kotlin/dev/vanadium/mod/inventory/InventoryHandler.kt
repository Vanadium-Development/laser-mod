package dev.vanadium.mod.inventory

import dev.vanadium.mod.VanadiumMod
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class InventoryHandler(
    val path: String,
    val titleKey: String
) {
    private val items = mutableListOf<Item>()
    private var icon: Item? = null

    fun add(item: Item) {
        items.add(item)
    }

    fun icon(item: Item) {
        icon = item
    }

    fun register() {
        val key = RegistryKey.of(
            Registries.ITEM_GROUP.key,
            Identifier.of(VanadiumMod.MOD_ID, path)
        )

        val group = FabricItemGroup
            .builder()
            .icon { ItemStack(icon ?: Items.ENDER_PEARL) }
            .displayName(Text.translatable(titleKey))
            .build()

        Registry.register(Registries.ITEM_GROUP, key, group)
        ItemGroupEvents.modifyEntriesEvent(key).register { g ->
            items.forEach { g.add { it } }
        }
    }

}