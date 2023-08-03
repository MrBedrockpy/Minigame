package me.mrbedrockpy.util.craft.crafttypes;

import org.bukkit.inventory.ItemStack;

public abstract class Craft {

    private final String craftId;
    private final ItemStack result;

    public String getCraftId() {
        return craftId;
    }

    public ItemStack getResult() {
        return result;
    }

    public Craft(String craftId, ItemStack result) {
        this.craftId = craftId;
        this.result = result;
    }
}
