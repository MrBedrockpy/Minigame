package me.mrbedrockpy.minigame.terminator;

import me.mrbedrockpy.util.EntityBuilder;
import me.mrbedrockpy.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.Random;

public class Terminator {

    private Random random = new Random();

    private WitherSkeleton bot;

    private String displayName;

    private int heath, speed;

    private boolean spawnEffects, lightningHits, spawnMinions;

    public Terminator(int heath, int speed) {
        this.heath = heath;
        this.speed = speed;
        this.displayName = "Terminator";
        this.spawnEffects = true;
        this.lightningHits = true;
        this.spawnMinions = true;
    }

    public Terminator(String displayName, int heath, int speed) {
        this.displayName = displayName;
        this.heath = heath;
        this.speed = speed;
        this.spawnEffects = true;
        this.lightningHits = true;
        this.spawnMinions = true;
    }

    public Terminator(int heath, int speed, boolean spawnEffects, boolean lightningHits, boolean spawnMinions) {
        this.heath = heath;
        this.speed = speed;
        this.displayName = "Terminator";
        this.spawnEffects = spawnEffects;
        this.lightningHits = lightningHits;
        this.spawnMinions = spawnMinions;
    }

    public Terminator(String displayName, int heath, int speed, boolean spawnEffects, boolean lightningHits, boolean spawnMinions) {
        this.displayName = displayName;
        this.heath = heath;
        this.speed = speed;
        this.spawnEffects = spawnEffects;
        this.lightningHits = lightningHits;
        this.spawnMinions = spawnMinions;
    }

    public void spawn(Location location) {
        bot = (WitherSkeleton) new EntityBuilder(EntityType.WITHER_SKELETON, location)
                .setHelmet(new ItemBuilder(Material.NETHERITE_HELMET, 1)
                        .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .addEnchantment(Enchantment.THORNS, 3)
                        .build())
                .setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE, 1)
                        .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .addEnchantment(Enchantment.THORNS, 3)
                        .build())
                .setChestplate(new ItemBuilder(Material.NETHERITE_LEGGINGS, 1)
                        .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .addEnchantment(Enchantment.THORNS, 3)
                        .build())
                .setChestplate(new ItemBuilder(Material.NETHERITE_BOOTS, 1)
                        .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .addEnchantment(Enchantment.THORNS, 3)
                        .build())
                .setMainHand(new ItemBuilder(Material.NETHERITE_SWORD, 1)
                        .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                        .addEnchantment(Enchantment.KNOCKBACK, 2)
                        .addEnchantment(Enchantment.FIRE_ASPECT, 2)
                        .build())
                .setHeath(this.heath)
                .addEffect(PotionEffectType.SPEED, 0, 999)
                .getEntity();

        bot.setCustomName(this.displayName);

        if (spawnEffects) {

            for (int i = 0; i < 5; i++) {

                Location botLocation = bot.getLocation();

                bot.getWorld().strikeLightning(new Location(bot.getWorld(), botLocation.getX() + random.nextInt(10) - 5, botLocation.getY(), botLocation.getZ() + random.nextInt(10) - 5));

            }
        }

        new BukkitRunnable() {

            @Override
            public void run() {

                long time = new Date().getTime();

                while (!bot.isDead()) {

                   if (new Date().getTime() >= time + 5000) {

                       if (lightningHits && spawnMinions) {

                           switch (random.nextInt(2)) {

                               case 0:

                                   for (int i = 0; i < 5; i++) {

                                       Location botLocation = bot.getLocation();

                                       bot.getWorld().strikeLightning(new Location(bot.getWorld(), botLocation.getX() + random.nextInt(10) - 5, botLocation.getY(), botLocation.getZ() + random.nextInt(10) - 5));

                                   }

                               case 1:

                                   for (int i = 0; i < 5; i++) {
                                       Location botLocation = bot.getLocation();

                                       Location minionLocation = new Location(bot.getWorld(), botLocation.getX() + random.nextInt(10) - 5, botLocation.getY(), botLocation.getZ() + random.nextInt(10) - 5);

                                       new EntityBuilder(EntityType.SKELETON, minionLocation)
                                               .setHelmet(new ItemStack(Material.LEATHER_HELMET))
                                               .setChestplate(new ItemStack(Material.AIR))
                                               .setLeggings(new ItemStack(Material.AIR))
                                               .setBoots(new ItemStack(Material.AIR))
                                               .setMainHand(new ItemStack(Material.AIR));
                                   }

                           }

                       } else if (lightningHits && !spawnMinions) {
                           for (int i = 0; i < 5; i++) {

                               Location botLocation = bot.getLocation();

                               bot.getWorld().strikeLightning(new Location(bot.getWorld(), botLocation.getX() + random.nextInt(10) - 5, botLocation.getY(), botLocation.getZ() + random.nextInt(10) - 5));

                           }
                       } else if (!lightningHits && spawnMinions) {
                           for (int i = 0; i < 5; i++) {
                               Location botLocation = bot.getLocation();

                               Location minionLocation = new Location(bot.getWorld(), botLocation.getX() + random.nextInt(10) - 5, botLocation.getY(), botLocation.getZ() + random.nextInt(10) - 5);

                               new EntityBuilder(EntityType.SKELETON, minionLocation)
                                       .setHelmet(new ItemStack(Material.LEATHER_HELMET))
                                       .setChestplate(new ItemStack(Material.AIR))
                                       .setLeggings(new ItemStack(Material.AIR))
                                       .setBoots(new ItemStack(Material.AIR))
                                       .setMainHand(new ItemStack(Material.AIR));
                           }
                       }

                       time = new Date().getTime();

                   }

                }

            }

        }.run();
    }

    public WitherSkeleton getEntity() {
        return bot;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getHeath() {
        return heath;
    }

    public int getSpeed() {
        return speed;
    }
}
