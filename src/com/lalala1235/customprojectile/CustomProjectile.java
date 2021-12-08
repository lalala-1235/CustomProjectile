package com.lalala1235.customprojectile;

import com.lalala1235.customprojectile.utils.DamageEntity;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomProjectile {
    private final EntityType type;
    private final Plugin plugin = Main.plugin;
    private boolean useParticle = false, useEndParticle = false, useDamage = false;
    private Particle particle = Particle.BARRIER, endParticle = Particle.EXPLOSION_LARGE;
    private int particleAmount = 1,endParticleAmount = 1;
    private double damage = 1;
    private boolean startingPointAsHead = false;
    private final ArrayList<UUID> whitelist = new ArrayList<>();

    public CustomProjectile(EntityType type) {
        this.type = type;
    }


    public void setUseParticle(boolean flag) {
        useParticle = flag;
    }

    public void setUseEndParticle(boolean flag) {
        useEndParticle = flag;
    }

    public void setUseDamage(boolean flag) {
        useDamage = flag;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public void setEndParticle(Particle particle) {
        this.endParticle = particle;
    }

    public void setParticleAmount(int amount) {
        particleAmount = amount;
    }

    public void setEndParticleAmount(int amount) {
        endParticleAmount = amount;
    }

    public void setStartingPointAsHead(boolean flag) {
        startingPointAsHead = flag;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setWhiteList(UUID uuid) {
        whitelist.add(uuid);
    }

    /**
     * @param start 시작 위치
     * @param distance 사거리
     * @param direction 방향
     * @param speed 속도
     */
    public void launch(Location start, int distance, Vector direction, int speed) {
        if(startingPointAsHead) {
            start = new Location(start.getWorld(), start.getX(), start.getY() + 1, start.getZ());
        }

        Entity projectile = Objects.requireNonNull(start.getWorld()).spawnEntity(start, type);
        Vector vec = direction.normalize();

        projectile.setGravity(false);
        projectile.setInvulnerable(true);

        if(projectile instanceof LivingEntity) {
            ((LivingEntity) projectile).setAI(false);
        }

        AtomicInteger repeat = new AtomicInteger();

        Location finalStart = start;
        new BukkitRunnable() {
            @Override
            public void run() {
                projectile.teleport(projectile.getLocation().add(vec));
                if(useParticle) finalStart.getWorld().spawnParticle(particle, projectile.getLocation(), particleAmount);
                if(useDamage) DamageEntity.rangeDamage(projectile.getLocation(), 1, 1, 1, damage, whitelist);
                repeat.getAndIncrement();

                if(repeat.get()==distance) {
                    if(useEndParticle) finalStart.getWorld().spawnParticle(endParticle, projectile.getLocation(), endParticleAmount);

                    projectile.remove();
                    cancel();
                }

                if(!projectile.getLocation().getBlock().isPassable()) {
                    if(useEndParticle) finalStart.getWorld().spawnParticle(endParticle, projectile.getLocation(), endParticleAmount);
                    projectile.remove();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, (20 / speed));
    }

}
