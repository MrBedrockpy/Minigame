package me.mrbedrockpy.util.craft;

import me.mrbedrockpy.minigame.Plugin;
import me.mrbedrockpy.util.ItemBuilder;
import me.mrbedrockpy.util.craft.crafttypes.Craft;
import me.mrbedrockpy.util.craft.crafttypes.impls.ShapedCraft;
import me.mrbedrockpy.util.craft.crafttypes.impls.ShapelessCraft;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CraftingManager implements Listener {

    public static final CraftingManager instance = new CraftingManager();

    private List<Craft> existingCraft = new ArrayList<Craft>();

    public void register(Craft craft) {
        existingCraft.add(craft);
    }

    public void craftCheckingTask() {

        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getOpenInventory().getTopInventory() != player.getOpenInventory().getBottomInventory()) {
                        Inventory topInventory = player.getOpenInventory().getTopInventory();

                        if (topInventory instanceof CraftingInventory) {
                            Craft craft = getCraftBy((CraftingInventory) topInventory);

                            if (craft != null) {
                                ((CraftingInventory) topInventory).setResult(
                                        ItemBuilder.open(craft.getResult().clone())
                                                .addPersistent("custom_craft_id", PersistentDataType.STRING, craft.getCraftId())
                                                .build()
                                );
                            }
                        }
                    }
                }
            }

        }.runTaskTimerAsynchronously(Plugin.getInstance(), 0L, 1L);

    }

    private Craft getCraftBy(CraftingInventory topInventory) {
        for (Craft craft : existingCraft) {
            if (craft instanceof ShapedCraft) {

                ShapedCraft shapedCraft = (ShapedCraft) craft;

                int count = 0;

                for (int i = 0; i < shapedCraft.getIngredientMap().size(); i++) {
                    if (topInventory.getMatrix()[i] == null && shapedCraft.getIngredientMap().get(i) == null || topInventory.getMatrix()[i] != null && topInventory.getMatrix()[i].isSimilar(shapedCraft.getIngredientMap().get(i))) {
                        if (shapedCraft.getIngredientMap().get(i) == null || topInventory.getMatrix()[i].getAmount() >= shapedCraft.getIngredientMap().get(i).getAmount()) {
                            count++;
                        }
                    }
                }
                if (count == 9) {
                    return craft;
                }
            } else if (craft instanceof ShapelessCraft) {

                ShapelessCraft shapelessCraft = (ShapelessCraft) craft;

                boolean craftFound = true;

                for (ItemStack ingredient : shapelessCraft.getIngredients()) {
                    if (ingredient != null) {

                        boolean isFound = false;

                        for (ItemStack itemStack : topInventory.getMatrix()) {
                            if (itemStack != null) {
                                if (itemStack.isSimilar(ingredient)) {
                                    isFound = true;
                                    break;
                                }
                            }
                        }

                        if (!isFound) {
                            craftFound = false;
                            break;
                        }
                    }

                    if (!craftFound) {
                        break;
                    }
                }

                if (craftFound)
                    return craft;
            }
        }
        return null;
    }

    public Craft getCraftBy(String craftId) {
        for (Craft craft : existingCraft) {
            if (craft.getCraftId().equals(craftId)) {
                return craft;
            }
        }

        return null;
    }

    @EventHandler
    public void onCraft(InventoryClickEvent event) {

        Inventory inventory = event.getWhoClicked().getOpenInventory().getTopInventory();

        if (inventory.getType() == InventoryType.WORKBENCH && event.getClickedInventory() == inventory) {
            if (event.getSlot() == 0) {

                ItemStack itemStack = inventory.getItem(0);

                if (itemStack != null) {
                    if (itemStack.getItemMeta() != null) {
                        String craftId = itemStack.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("custom_craft_id"), PersistentDataType.STRING);

                        if (craftId != null) {
                            Craft craft = getCraftBy(craftId);

                            if (craft != null && (event.getCursor() != null || event.getCursor().getType().isAir()
                                    || event.getCursor().getAmount() < event.getCursor().getMaxStackSize())) {

                                event.setCancelled(true);

                                if (event.getClick().name().contains("SHIFT_")) {

                                    while (getCraftBy((CraftingInventory) inventory) != null) {

                                        takeItemFromTable(craft, inventory);

                                        inventory.setItem(0, null);

                                        event.getWhoClicked().getInventory().addItem(
                                                ItemBuilder.open(itemStack)
                                                        .removePersistent("custom_craft_id")
                                                        .build()
                                        );

                                    }

                                } else {
                                    if (event.getCursor() == null || event.getCursor().getType().isAir()) {

                                        takeItemFromTable(craft, inventory);

                                        inventory.setItem(0, null);

                                        event.setCursor(
                                                ItemBuilder.open(itemStack)
                                                        .removePersistent("custom_craft_id")
                                                        .build()
                                        );
                                    } else if (event.getCursor().getAmount() < event.getCursor().getMaxStackSize()) {

                                        takeItemFromTable(craft, inventory);

                                        inventory.setItem(0, null);

                                        event.getCursor().setAmount(event.getCursor().getAmount() + craft.getResult().getAmount());

                                    } else {
                                        return;
                                    }
                                }

                            }
                            else {
                                event.setCancelled(true);
                            }
                        }
                    }
                }

            }
        }

    }

    public void takeItemFromTable(Craft craft, Inventory inventory) {
        if (craft instanceof ShapedCraft) {

            ShapedCraft shapedCraft = (ShapedCraft) craft;

            int slot = 1;
            for (int i = 0; i < 9; i++) {
                if (shapedCraft.getIngredientMap().get(i) != null) {
                    if (shapedCraft.getIngredientMap().get(i).getAmount() == inventory.getItem(slot).getAmount()) {
                        inventory.setItem(slot, null);
                    }
                    else {
                        inventory.getItem(slot).setAmount(inventory.getItem(slot).getAmount() - shapedCraft.getIngredientMap().get(i).getAmount());
                    }
                }

                slot++;
            }
        } else if (craft instanceof ShapelessCraft) {

            ShapelessCraft shapelessCraft = (ShapelessCraft) craft;

            for (ItemStack craftItem : shapelessCraft.getIngredients()) {
                for (ItemStack item : inventory) {
                    if (item.isSimilar(craftItem)) {
                        item.setAmount(item.getAmount() - craftItem.getAmount());
                        break;
                    }
                }
            }

        }
    }

}
