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

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomProjectile {
    private final EntityType type;
    private final Plugin plugin = Main.plugin;
    private boolean useParticle = false, useEndParticle = false, useDamage = false;
    private Particle particle = Particle.BARRIER, endParticle = Particle.EXPLOSION_LARGE;
    private int particleAmount = 1,endParticleAmount = 1;
    private double damage = 1;

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
        this.useDamage = true;
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

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void launch(Location start, int distance, Vector direction, int speed) {
        Entity projectile = Objects.requireNonNull(start.getWorld()).spawnEntity(start, type);
        Vector vec = direction.normalize();

        projectile.setGravity(false);
        projectile.setInvulnerable(true);

        if(projectile instanceof LivingEntity) {
            ((LivingEntity) projectile).setAI(false);
        }

        AtomicInteger repeat = new AtomicInteger();

        new BukkitRunnable() {
            @Override
            public void run() {
                projectile.teleport(projectile.getLocation().add(vec));
                if(useParticle) start.getWorld().spawnParticle(particle, projectile.getLocation(), particleAmount);
                if(useDamage) DamageEntity.rangeDamage(projectile.getLocation(), 1, 1, 1, damage);
                repeat.getAndIncrement();

                if(repeat.get()==distance) {
                    if(useEndParticle) start.getWorld().spawnParticle(endParticle, projectile.getLocation(), endParticleAmount);

                    projectile.remove();
                    cancel();
                }

                if(!projectile.getLocation().getBlock().isPassable()) {
                    if(useEndParticle) start.getWorld().spawnParticle(endParticle, projectile.getLocation(), endParticleAmount);
                    projectile.remove();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, (20 / speed));
    }

}
