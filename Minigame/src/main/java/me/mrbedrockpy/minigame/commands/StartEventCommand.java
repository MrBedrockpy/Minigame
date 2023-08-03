package me.mrbedrockpy.minigame.commands;

import me.mrbedrockpy.minigame.Plugin;
import me.mrbedrockpy.minigame.terminator.Terminator;
import me.mrbedrockpy.util.TeamBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class StartEventCommand implements CommandExecutor {

    public Date time = new Date();
    public long lastTimeDrop;

    public void setupTeams(Server server) {

        Player[] players = server.getWorld("world").getPlayers().toArray(new Player[0]);
        int countPlayers = players.length;

        if (countPlayers % 2 == 0) {

            TeamBuilder redTeam = new TeamBuilder("red", server)
                    .setColor("red")
                    .setFriendlyFire(false)
                    .setNameVisibility("hideOtherTeam")
                    .setCollision("pushOtherTeams");

            TeamBuilder blueTeam = new TeamBuilder("blue", server)
                    .setColor("blue")
                    .setFriendlyFire(false)
                    .setNameVisibility("hideOtherTeam")
                    .setCollision("pushOtherTeams");

            for (int i = 0; i < countPlayers; i++) {
                if (i <= countPlayers / 2) {

                    blueTeam.joinPlayer(players[i].getName());

                } else if (i > countPlayers / 2) {

                    redTeam.joinPlayer(players[i].getName());

                }
            }

        }else {

            TeamBuilder teamForAll = new TeamBuilder("all", server)
                    .setFriendlyFire(true)
                    .setNameVisibility("hideOwnTeam")
                    .setCollision("pushOwnTeam");

            for (Player player : players) {
                teamForAll.joinPlayer(player.getName());
            }

        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args[0].equals("ForestBattle")) {

            if (!(Plugin.getInstance().isGame)) {
                World world = sender.getServer().getWorld("world");

                for (Player player : sender.getServer().getOnlinePlayers()) {
                    player.sendMessage("Игра началась!");
                }
                Plugin.getInstance().isGame = true;

                lastTimeDrop = time.getTime();

                setupTeams(sender.getServer());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        while (Plugin.getInstance().isGame) {

                            if (lastTimeDrop + 3000 <= time.getTime()) {
                                world.dropItem(new Location(world, 170, 7, 50), new ItemStack(Material.EMERALD, 1));
                                lastTimeDrop = time.getTime();
                            }

                            for (Player player : world.getPlayers()) {
                                int coins = 0;
                                for (ItemStack slot : player.getInventory().getContents()) {
                                    if (slot.getType() == Material.EMERALD) {
                                        coins += slot.getAmount();
                                    }
                                }
                                if (coins >= 50) {
                                    Plugin.getInstance().isGame = false;
                                }
                            }
                        }

                        for (Player player : sender.getServer().getOnlinePlayers()) {
                            player.sendMessage("Игра закончена!");
                        }
                    }
                }.run();

            }if (Plugin.getInstance().isGame) {
                sender.sendMessage("Мини игра отключена!");
                Plugin.getInstance().isGame = false;
            }
            return false;

        } else if (args[0].equals("Terminator")) {

            if (sender instanceof Player) {
                Plugin.getInstance().terminator = new Terminator(100, 5);
                Plugin.getInstance().terminator.spawn(((Player) sender).getLocation());
            }

        }

        return false;
    }
}
