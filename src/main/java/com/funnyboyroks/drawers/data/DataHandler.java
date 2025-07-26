package com.funnyboyroks.drawers.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.Location;

import com.funnyboyroks.drawers.Drawers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;

public class DataHandler {
    private static final Gson GSON = new GsonBuilder()
        .serializeNulls()
        .setStrictness(Strictness.LENIENT)
        .setPrettyPrinting()
        .registerTypeAdapter(Location.class, new LocationSerializer())
        .create();
    public PluginData data;

    public void loadData(Drawers plugin) throws IOException {
        Path dataPath = Paths.get(plugin.getDataPath().toString(), "data.json");
        if (!Files.exists(dataPath)) {
            plugin.getLogger().info("Creating default data");
            this.data = new PluginData();
            this.saveData();
            return;
        }
        this.data = GSON.fromJson(Files.readString(dataPath), PluginData.class);
        if (this.data == null) {
            plugin.getLogger().warning("Invalid JSON read from plugin data.json, using default data");
            this.data = new PluginData();
        }
        plugin.getLogger().info("Loaded plugin data");
    }

    public void saveData() throws IOException {
        Drawers plugin = Drawers.instance();
        Path dataPath = Paths.get(plugin.getDataPath().toString(), "data.json");
        Files.writeString(dataPath, GSON.toJson(this.data, PluginData.class));
        plugin.getLogger().info("Saved plugin data");
    }
}
