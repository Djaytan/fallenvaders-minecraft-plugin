package fr.fallenvaders.minecraft.mods.fallenvaders_core

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object FallenVadersMod : ModInitializer {

    private const val MOD_ID = "fallenvaders_core"

    val EXAMPLE_BLOCK: Block = Block(FabricBlockSettings.of(Material.METAL).strength(4.0f))

    override fun onInitialize() {
        println("Example mod has been initialized.")
        Registry.register(Registry.BLOCK, Identifier(MOD_ID, "example_block"), EXAMPLE_BLOCK)
        Registry.register(
            Registry.ITEM,
            Identifier(MOD_ID, "example_block"),
            BlockItem(EXAMPLE_BLOCK, FabricItemSettings().group(ItemGroup.MISC))
        )
    }
}
