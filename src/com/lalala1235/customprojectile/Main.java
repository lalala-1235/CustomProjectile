package com.lalala1235.customprojectile;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        System.out.println("CustomProjectile enabled.");
    }

    @Override
    public void onDisable() {
        System.out.println("CustomProjectile disabled.");
    }
}
