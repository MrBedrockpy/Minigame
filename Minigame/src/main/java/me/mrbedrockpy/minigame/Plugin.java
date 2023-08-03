package me.mrbedrockpy.minigame;

import me.mrbedrockpy.minigame.commands.StartEventCommand;
import me.mrbedrockpy.minigame.commands.StartEventTabCompleter;
import me.mrbedrockpy.minigame.forestbattle.events.ForestBattleEvents;
import me.mrbedrockpy.minigame.terminator.Terminator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Plugin extends JavaPlugin {

    public static Plugin instance;
    public boolean isGame = false;

    public Terminator terminator;

    public ArrayList<Location> spawnLocations = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        getCommand("start").setExecutor(new StartEventCommand());
        getCommand("start").setTabCompleter(new StartEventTabCompleter());

        getServer().getPluginManager().registerEvents(new ForestBattleEvents(), this);

        World world = Bukkit.getWorld("world");

        spawnLocations.add(new Location(world, 193, 4, 31));
        spawnLocations.add(new Location(world, 187, 4, 78));
        spawnLocations.add(new Location(world, 141, 4, 73));
        spawnLocations.add(new Location(world, 141, 4, 27));
    }

    public static Plugin getInstance() {
        return instance;
    }
}
