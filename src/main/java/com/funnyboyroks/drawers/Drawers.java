package com.funnyboyroks.drawers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.plugin.java.JavaPlugin;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

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

        this.addRecipes();
    }

    public void addRecipes() {
        NamespacedKey key = Util.ns("drawer");
        ItemStack item = ItemStack.of(Material.BARREL);
        item.setData(
            DataComponentTypes.CUSTOM_MODEL_DATA,
            CustomModelData.customModelData()
                .addString(Util.ns("barrel").toString())
                .build()
        );

        item.editMeta(meta -> {
            meta.customName(Component.translatable("block.drawers.drawer", "Drawer").decoration(TextDecoration.ITALIC, false));
        });

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape(
            "AAA",
            "ABA",
            "AAA"
        );
        recipe.setIngredient('A', Material.OAK_PLANKS);
        recipe.setIngredient('B', Material.CHEST);

        this.getServer().addRecipe(recipe);
    }

    @Override
    public void onDisable() {
    }
}
