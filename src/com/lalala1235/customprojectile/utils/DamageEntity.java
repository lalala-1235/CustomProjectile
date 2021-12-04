package com.lalala1235.customprojectile.utils;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Objects;

public class DamageEntity {
    public static void rangeDamage(Location location, double xRange, double yRange, double zRange, double damage) {
        Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, xRange, yRange, zRange).forEach(entity -> {
            if(entity instanceof LivingEntity) ((LivingEntity) entity).damage(damage);
        });
    }
}
