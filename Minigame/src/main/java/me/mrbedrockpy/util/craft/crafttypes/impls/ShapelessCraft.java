package me.mrbedrockpy.util.craft.crafttypes.impls;

import me.mrbedrockpy.util.craft.crafttypes.Craft;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShapelessCraft extends Craft {

    private List<ItemStack> ingredients = new ArrayList<>();

    public ShapelessCraft(String craftId, ItemStack result, List<ItemStack> ingredients) {
        super(craftId, result);

        this.ingredients = ingredients;
    }

    public List<ItemStack> getIngredients() {
        return ingredients;
    }
}
