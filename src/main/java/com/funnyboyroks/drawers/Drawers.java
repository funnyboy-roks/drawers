package com.funnyboyroks.drawers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import com.funnyboyroks.drawers.data.Config;
import com.funnyboyroks.drawers.data.DataHandler;
import com.funnyboyroks.drawers.data.Lang;
import com.funnyboyroks.drawers.commands.DrawersCommand;

import de.exlll.configlib.YamlConfigurations;

public final class Drawers extends JavaPlugin {

    private static Drawers INSTANCE;
    private Config config;
    private Lang lang;
    private DataHandler dataHandler;

    public static Drawers instance() {
        return Drawers.INSTANCE;
    }

    public static Config config() {
        return Drawers.instance().config;
    }

    public static Lang lang() {
        return Drawers.instance().lang;
    }

    public static DataHandler dataHandler() {
        return Drawers.instance().dataHandler;
    }

    public Drawers() {
        Drawers.INSTANCE = this;
    }

    @Override
    public void onEnable() {
        Path configPath = Paths.get(this.getDataPath().toString(), "config.yml");
        this.config = YamlConfigurations.update(configPath, Config.class);

        Path langPath = Paths.get(this.getDataPath().toString(), "lang.yml");
        this.lang = YamlConfigurations.update(langPath, Lang.class);

        this.getLogger().info("Loaded Config");

        this.dataHandler = new DataHandler();
        try {
            this.dataHandler.loadData(this);
        } catch (IOException ex) {
            this.getLogger().severe("Unable to load plugin data: " + ex);
            this.getLogger().severe("Disabling plugin");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            try {
                this.dataHandler.saveData();
            } catch (IOException ex) {
                this.getLogger().severe("Unable to save plugin data: " + ex);
            }
        }, 5 * 60 * 20L, 5 * 60 * 20L); // 5 minutes * 60 seconds * 20 ticks

        this.getServer().getPluginManager().registerEvents(new Listeners(), this);

        getCommand("drawers").setExecutor(new DrawersCommand(this));

        this.addRecipes();
        this.getLogger().info("Registered Recipes");
    }

    public void addRecipes() {
        this.getServer().addRecipe(this.config.drawer_recipe.recipe(Util.ns("drawer_recipe")));
    }

    @Override
    public void onDisable() {
        try {
            this.dataHandler.saveData();
        } catch (IOException ex) {
            this.getLogger().severe("Unable to save plugin data: " + ex);
        }
    }
}
