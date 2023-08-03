package me.mrbedrockpy.util;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public class ItemBuilder {

    public static ItemBuilder open(ItemStack itemStack) {
        ItemBuilder itemBuilder = new ItemBuilder(itemStack.getType(), itemStack.getAmount());

        itemBuilder.itemStack = itemStack;
        itemBuilder.itemMeta = itemStack.getItemMeta();

        return itemBuilder;
    }

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int lvl) {
        itemStack.addEnchantment(enchantment, lvl);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder displayName(String value) {
        itemMeta.setDisplayName(value);

        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        lore.replaceAll(ChatUtil::format);

        itemMeta.setLore(lore);

        return this;
    }

    public ItemBuilder setLore(List<String> lore, Map<String, String> args) {
        for (int i = 0; i < lore.size(); i++) {
            for (String key: args.keySet()) {
                lore.set(i, lore.get(i).replace(key, args.get(key)));
            }

            lore.set(i,  ChatUtil.format(lore.get(i)));
        }

        itemMeta.setLore(lore);

        return this;
    }

    public ItemBuilder addPersistent(String key, PersistentDataType dataType, Object value) {
        itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(key), dataType, value);

        return this;
    }

    public ItemBuilder removePersistent(String key) {
        itemMeta.getPersistentDataContainer().remove(NamespacedKey.fromString(key));
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
