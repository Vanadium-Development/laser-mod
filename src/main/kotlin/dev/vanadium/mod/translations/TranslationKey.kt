package dev.vanadium.mod.translations

object TranslationKey {
    val INVENTORY_TAB = key("inventory", "tab")

    private fun key(vararg path: String) = "vmod.${path.joinToString(".")}"
}