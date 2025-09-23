package com.funnyboyroks.drawers.data;

import de.exlll.configlib.Configuration;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.tag.TagEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import com.funnyboyroks.drawers.Util;
import com.google.common.collect.ImmutableMap;

import de.exlll.configlib.Comment;


@Configuration
public class Config {
    @Comment({
        "The maximum number of stacks that may be stored in a base drawer.",
        "Example: if max_stack_count = 32, then a cobblestone drawer may store up to 2048 items",
        "         and an ender pearl drawer may store up to 512 items.",
    })
    public int max_stack_count = 32;

    @Comment({
        "",
        "The recipe used to create the drawer item",
    })
    public RecipeConfig drawer_recipe = new RecipeConfig();

    @Override
    public String toString() {
        return "Config[max_stack_count=%d]".formatted(this.max_stack_count);
    }

    @Configuration
    public static class RecipeConfig {
        @Comment({
            "Whether this recipe is shaped",
        })
        public boolean shaped = true;
        
        @Comment({
            "The pattern for the recipe.  Each slot should be represented by a single character that is defined in the `key`",
        })
        public String[] pattern = new String[] {
            "AAA",
            "ABA",
            "AAA",
        };

        @Comment({
            "Keys for the recipe.  Each item should be one character and map to an item.  You can use item ids or tags: minecraft:stone or #planks",
        })
        public Map<Character, String> key = new ImmutableMap.Builder<Character, String>()
            .put('A', "#planks")
            .put('B', "minecraft:chest")
            .build();

        private static record Pair<A, B>(A first, B second) {}

        public Recipe recipe(NamespacedKey key) {
            Map<Character, RecipeChoice> resolvedKey = this.key.entrySet().stream().map(entry -> {
                String item = entry.getValue();
                return new Pair<Character, RecipeChoice>(
                    entry.getKey(),
                    item.startsWith("#")
                        ? new RecipeChoice.MaterialChoice(Util.getTag(item.substring(1)))
                        : new RecipeChoice.MaterialChoice(Material.matchMaterial(item))
                );
            })
            .collect(Collectors.toUnmodifiableMap(e -> e.first(), e -> e.second()));

            if (this.shaped) {
                ShapedRecipe shaped = new ShapedRecipe(key, Util.drawerItem());
                shaped.shape(this.pattern);
                shaped.setCategory(CraftingBookCategory.MISC);
                for (var entry : resolvedKey.entrySet()) {
                    shaped.setIngredient(entry.getKey(), entry.getValue());
                }
                return shaped;
            } else {
                ShapelessRecipe shapeless = new ShapelessRecipe(key, Util.drawerItem());
                shapeless.setCategory(CraftingBookCategory.MISC);
                for (var line : this.pattern) {
                    for (char c : line.trim().toCharArray()) {
                        shapeless.addIngredient(resolvedKey.get(c));
                    }
                }
                return shapeless;
            }
        }
    }
}
