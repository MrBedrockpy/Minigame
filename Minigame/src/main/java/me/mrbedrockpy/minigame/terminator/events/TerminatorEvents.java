package me.mrbedrockpy.minigame.terminator.events;

import me.mrbedrockpy.minigame.Plugin;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class TerminatorEvents implements Listener {

    @EventHandler
    public void onTerminatorDeath(EntityDeathEvent event) {
        if (Plugin.getInstance().terminator.equals(event.getEntity())) {
            for (Player player : Plugin.getInstance().getServer().getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1, 1);
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    if (player.getInventory().getItem(i).isSimilar(new ItemStack(Material.AIR))) {
                        player.getInventory().setItem(i, new ItemStack(Material.DIAMOND, 10));
                        break;
                    }
                }
            }
        }
    }
}
