package me.mrbedrockpy.minigame.forestbattle.events;

import me.mrbedrockpy.minigame.Plugin;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class ForestBattleEvents implements Listener {

    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        Player player;
        try {
            player = (Player) event.getEntity();
        }catch (Exception e) {
            return;
        }

        World world = player.getWorld();

        player.teleport(Plugin.getInstance().spawnLocations.get(new Random().nextInt(4)));

    }
}
