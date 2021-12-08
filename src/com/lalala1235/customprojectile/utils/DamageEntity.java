package com.lalala1235.customprojectile.utils;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class DamageEntity {
    public static void rangeDamage(Location location, double xRange, double yRange, double zRange, double damage, ArrayList<UUID> whitelist) {
        Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, xRange, yRange, zRange).forEach(entity -> {
            if(entity instanceof LivingEntity && !whitelist.contains(entity.getUniqueId())) ((LivingEntity) entity).damage(damage);
        });
    }
}
