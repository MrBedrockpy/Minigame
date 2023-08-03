package me.mrbedrockpy.util.craft.crafttypes.impls;

import me.mrbedrockpy.util.craft.crafttypes.Craft;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShapedCraft extends Craft {

    private List<ItemStack> ingredientMap = new ArrayList<>();

    public ShapedCraft(String craftId, ItemStack result, List<ItemStack> ingredientMap) {
        super(craftId, result);

        this.ingredientMap = ingredientMap;
    }

    public List<ItemStack> getIngredientMap() {
        return ingredientMap;
    }
}
