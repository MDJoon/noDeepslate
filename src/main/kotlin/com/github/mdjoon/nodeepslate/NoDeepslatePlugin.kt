package com.github.mdjoon.nodeepslate

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.plugin.java.JavaPlugin
import org.checkerframework.checker.units.qual.m
import java.util.*


class NoDeepslatePlugin : JavaPlugin(), Listener {
    private val blockChangeMap = mapOf(
        Material.DEEPSLATE to Material.STONE,
        Material.DEEPSLATE_IRON_ORE to Material.IRON_ORE,
        Material.DEEPSLATE_GOLD_ORE to Material.GOLD_ORE,
        Material.DEEPSLATE_REDSTONE_ORE to Material.REDSTONE_ORE,
        Material.DEEPSLATE_EMERALD_ORE to Material.EMERALD_ORE,
        Material.DEEPSLATE_DIAMOND_ORE to Material.DIAMOND_ORE,
        Material.DEEPSLATE_COAL_ORE to Material.COAL_ORE,
        Material.DEEPSLATE_COPPER_ORE to Material.COPPER_ORE,
        Material.DEEPSLATE_LAPIS_ORE to Material.LAPIS_ORE
    )

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        server.sendMessage(Component.text("plugin on"))
    }

    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        if(event.isNewChunk) {
            val chunk = event.chunk
            val world = chunk.world

            if(world.environment == World.Environment.NORMAL) {
                for (y in world.minHeight..world.maxHeight) {
                    for (x in 0..15) {
                        for (z in 0..15) {
                            val block = chunk.getBlock(x, y, z)
                            if(blockChangeMap.containsKey(block.type)) {
                                block.type = blockChangeMap[block.type]!!
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val player = sender as Player
        val chunk = player.chunk
        val world = player.world

        for (y in world.minHeight..world.maxHeight) {
            for (x in 0..15) {
                for (z in 0..15) {
                    player.sendMessage(chunk.getBlock(x, y, z).type.name)
                }
            }
        }
        return true
    }
}