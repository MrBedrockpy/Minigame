package me.mrbedrockpy.util;

import org.bukkit.Server;

public class TeamBuilder {

    private final String name;
    private final Server server;

    public TeamBuilder(String name, Server server) {
        this.name = name;
        this.server = server;

        server.dispatchCommand(server.getConsoleSender(), "team add " + name);
    }

    public TeamBuilder setColor(String color) {
        server.dispatchCommand(server.getConsoleSender(), "team modify " + name + " color " + color);

        return this;
    }

    public TeamBuilder setFriendlyFire(boolean value) {
        server.dispatchCommand(server.getConsoleSender(), "team modify " + name + " friendlyFire " + value);

        return this;
    }

    public TeamBuilder setNameVisibility(String value) {
        server.dispatchCommand(server.getConsoleSender(), "team modify " + name + " nametagVisibility " + value);

        return this;
    }

    public TeamBuilder setCollision(String value) {
        server.dispatchCommand(server.getConsoleSender(), "team modify " + name + " collisionRule " + value);

        return this;
    }

    public TeamBuilder joinPlayer(String name) {
        server.dispatchCommand(server.getConsoleSender(), "team join blue " + name);

        return this;
    }

}
