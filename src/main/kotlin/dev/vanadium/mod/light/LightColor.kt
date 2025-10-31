package dev.vanadium.mod.light

data class LightColor(
    val r: Int,
    val g: Int,
    val b: Int
) {
    companion object {
        val RED = LightColor(255, 0, 0)
        val GREEN = LightColor(0, 255, 0)
        val BLUE = LightColor(0, 0, 255)
        val WHITE = LightColor(255, 255, 255)
    }
}