package com.funnyboyroks.drawers;

import org.bukkit.plugin.java.JavaPlugin;

public final class Drawers extends JavaPlugin {

    private static Drawers INSTANCE;

    public static Drawers instance() {
        return Drawers.INSTANCE;
    }

    public Drawers() {
        Drawers.INSTANCE = this;
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
    }
}
