package me.mrbedrockpy.util;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityBuilder {

    private LivingEntity entity;

    public EntityBuilder(EntityType entityType, Location location) {
         entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public EntityBuilder setHelmet(ItemStack helmet) {
        entity.getEquipment().setHelmet(helmet);
        return this;
    }

    public EntityBuilder setChestplate(ItemStack chestplate) {
        entity.getEquipment().setChestplate(chestplate);
        return this;
    }

    public EntityBuilder setLeggings(ItemStack leggings) {
        entity.getEquipment().setLeggings(leggings);
        return this;
    }

    public EntityBuilder setBoots(ItemStack boots) {
        entity.getEquipment().setBoots(boots);
        return this;
    }

    public EntityBuilder setMainHand(ItemStack item) {
        entity.getEquipment().setItemInMainHand(item);
        return this;
    }

    public EntityBuilder setOffHand(ItemStack item) {
        entity.getEquipment().setItemInOffHand(item);
        return this;
    }

    public EntityBuilder addEffect(PotionEffectType effectType, int time, int lvl) {
        if (lvl > 255) {
            if (time > 0) {
                entity.addPotionEffect(new PotionEffect(effectType, time, 255, true, true));
            } else if (time == 0) {
                entity.addPotionEffect(new PotionEffect(effectType, Integer.MAX_VALUE, 255, true, true));
            }
        } else {
            if (time > 0) {
                entity.addPotionEffect(new PotionEffect(effectType, time, lvl, true, true));
            } else if (time == 0) {
                entity.addPotionEffect(new PotionEffect(effectType, Integer.MAX_VALUE, lvl, true, true));
            } else {
                entity.addPotionEffect(new PotionEffect(effectType, 30, lvl, true, true));
            }
        }
        return this;
    }

    public EntityBuilder removeEffect(PotionEffectType effectType) {
        entity.removePotionEffect(effectType);
        return this;
    }

    public EntityBuilder setHeath(double heath) {
        if (entity.getMaxHealth() < heath) {
            entity.setMaxHealth(heath);
            entity.setHealth(heath);
        } else {
            entity.setHealth(heath);
        }
        return this;
    }

    public EntityBuilder setMaxHeath(double heath) {
        entity.setMaxHealth(heath);
        return this;
    }
}
