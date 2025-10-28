package com.funnyboyroks.drawers;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class Util {
    private static ItemStack DRAWER_ITEM = null;

    public static ItemStack drawerItem() {
        if (Util.DRAWER_ITEM == null) {
            Util.DRAWER_ITEM = new ItemStack(Material.BARREL);
            DRAWER_ITEM.setData(
                DataComponentTypes.CUSTOM_MODEL_DATA,
                CustomModelData.customModelData()
                    .addString(Util.ns("barrel").toString())
                    .build()
            );

            DRAWER_ITEM.editMeta(meta -> {
                meta.customName(
                    Component.translatable(
                        "block.drawers.drawer",
                        "%s",
                        Drawers.lang().item_name
                    )
                    .decoration(TextDecoration.ITALIC, false)
                );
            });
        }
        return Util.DRAWER_ITEM.clone();
    }

    public static NamespacedKey ns(String key) {
        return NamespacedKey.fromString(key, Drawers.instance());
    }

    public static long[] uuidToLongArr(UUID uuid) {
        return new long[] {
                uuid.getMostSignificantBits(),
                uuid.getLeastSignificantBits(),
        };
    }

    public static UUID longArrToUUID(long[] longs) {
        return new UUID(longs[0], longs[1]);
    }

    public static void tryRemoveEntity(World world, UUID uuid) {
        Entity e = world.getEntity(uuid);
        Util.tryRemoveEntity(e);
    }

    public static void tryRemoveEntity(@Nullable Entity e) {
        if (e == null)
            return;
        e.remove();
    }

    public static String format(Location loc) {
        return String.format("x=%d, y=%d, z=%d, world=%s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(),
                loc.getWorld().getName());
    }

    // There may be a better way to do this ._.
    public static Tag<Material> getTag(String tag) {
        switch (tag.toUpperCase()) {
            case "ACACIA_LOGS": return Tag.ACACIA_LOGS;
            case "AIR": return Tag.AIR;
            case "ALL_HANGING_SIGNS": return Tag.ALL_HANGING_SIGNS;
            case "ALL_SIGNS": return Tag.ALL_SIGNS;
            case "ANCIENT_CITY_REPLACEABLE": return Tag.ANCIENT_CITY_REPLACEABLE;
            case "ANIMALS_SPAWNABLE_ON": return Tag.ANIMALS_SPAWNABLE_ON;
            case "ANVIL": return Tag.ANVIL;
            case "ARMADILLO_SPAWNABLE_ON": return Tag.ARMADILLO_SPAWNABLE_ON;
            case "AXOLOTLS_SPAWNABLE_ON": return Tag.AXOLOTLS_SPAWNABLE_ON;
            case "AZALEA_GROWS_ON": return Tag.AZALEA_GROWS_ON;
            case "AZALEA_ROOT_REPLACEABLE": return Tag.AZALEA_ROOT_REPLACEABLE;
            case "BADLANDS_TERRACOTTA": return Tag.BADLANDS_TERRACOTTA;
            case "BAMBOO_BLOCKS": return Tag.BAMBOO_BLOCKS;
            case "BAMBOO_PLANTABLE_ON": return Tag.BAMBOO_PLANTABLE_ON;
            case "BANNERS": return Tag.BANNERS;
            case "BASE_STONE_NETHER": return Tag.BASE_STONE_NETHER;
            case "BASE_STONE_OVERWORLD": return Tag.BASE_STONE_OVERWORLD;
            case "BATS_SPAWNABLE_ON": return Tag.BATS_SPAWNABLE_ON;
            case "BEACON_BASE_BLOCKS": return Tag.BEACON_BASE_BLOCKS;
            case "BEDS": return Tag.BEDS;
            case "BEE_ATTRACTIVE": return Tag.BEE_ATTRACTIVE;
            case "BEE_GROWABLES": return Tag.BEE_GROWABLES;
            case "BEEHIVES": return Tag.BEEHIVES;
            case "BIG_DRIPLEAF_PLACEABLE": return Tag.BIG_DRIPLEAF_PLACEABLE;
            case "BIRCH_LOGS": return Tag.BIRCH_LOGS;
            case "BLOCKS_WIND_CHARGE_EXPLOSIONS": return Tag.BLOCKS_WIND_CHARGE_EXPLOSIONS;
            case "BUTTONS": return Tag.BUTTONS;
            case "CAMEL_SAND_STEP_SOUND_BLOCKS": return Tag.CAMEL_SAND_STEP_SOUND_BLOCKS;
            case "CAMELS_SPAWNABLE_ON": return Tag.CAMELS_SPAWNABLE_ON;
            case "CAMPFIRES": return Tag.CAMPFIRES;
            case "CANDLE_CAKES": return Tag.CANDLE_CAKES;
            case "CANDLES": return Tag.CANDLES;
            case "CAULDRONS": return Tag.CAULDRONS;
            case "CAVE_VINES": return Tag.CAVE_VINES;
            case "CEILING_HANGING_SIGNS": return Tag.CEILING_HANGING_SIGNS;
            case "CHERRY_LOGS": return Tag.CHERRY_LOGS;
            case "CLIMBABLE": return Tag.CLIMBABLE;
            case "COAL_ORES": return Tag.COAL_ORES;
            case "COMBINATION_STEP_SOUND_BLOCKS": return Tag.COMBINATION_STEP_SOUND_BLOCKS;
            case "COMPLETES_FIND_TREE_TUTORIAL": return Tag.COMPLETES_FIND_TREE_TUTORIAL;
            case "CONCRETE_POWDER": return Tag.CONCRETE_POWDER;
            case "CONVERTABLE_TO_MUD": return Tag.CONVERTABLE_TO_MUD;
            case "COPPER_ORES": return Tag.COPPER_ORES;
            case "CORAL_BLOCKS": return Tag.CORAL_BLOCKS;
            case "CORAL_PLANTS": return Tag.CORAL_PLANTS;
            case "CORALS": return Tag.CORALS;
            case "CRIMSON_STEMS": return Tag.CRIMSON_STEMS;
            case "CROPS": return Tag.CROPS;
            case "CRYSTAL_SOUND_BLOCKS": return Tag.CRYSTAL_SOUND_BLOCKS;
            case "DAMPENS_VIBRATIONS": return Tag.DAMPENS_VIBRATIONS;
            case "DARK_OAK_LOGS": return Tag.DARK_OAK_LOGS;
            case "DEEPSLATE_ORE_REPLACEABLES": return Tag.DEEPSLATE_ORE_REPLACEABLES;
            case "DIAMOND_ORES": return Tag.DIAMOND_ORES;
            case "DIRT": return Tag.DIRT;
            case "DOES_NOT_BLOCK_HOPPERS": return Tag.DOES_NOT_BLOCK_HOPPERS;
            case "DOORS": return Tag.DOORS;
            case "DRAGON_IMMUNE": return Tag.DRAGON_IMMUNE;
            case "DRAGON_TRANSPARENT": return Tag.DRAGON_TRANSPARENT;
            case "DRY_VEGETATION_MAY_PLACE_ON": return Tag.DRY_VEGETATION_MAY_PLACE_ON;
            case "EDIBLE_FOR_SHEEP": return Tag.EDIBLE_FOR_SHEEP;
            case "EMERALD_ORES": return Tag.EMERALD_ORES;
            case "ENCHANTMENT_POWER_PROVIDER": return Tag.ENCHANTMENT_POWER_PROVIDER;
            case "ENCHANTMENT_POWER_TRANSMITTER": return Tag.ENCHANTMENT_POWER_TRANSMITTER;
            case "ENDERMAN_HOLDABLE": return Tag.ENDERMAN_HOLDABLE;
            case "FALL_DAMAGE_RESETTING": return Tag.FALL_DAMAGE_RESETTING;
            case "FEATURES_CANNOT_REPLACE": return Tag.FEATURES_CANNOT_REPLACE;
            case "FENCE_GATES": return Tag.FENCE_GATES;
            case "FENCES": return Tag.FENCES;
            case "FIRE": return Tag.FIRE;
            case "FLOWER_POTS": return Tag.FLOWER_POTS;
            case "FLOWERS": return Tag.FLOWERS;
            case "FOXES_SPAWNABLE_ON": return Tag.FOXES_SPAWNABLE_ON;
            case "FROG_PREFER_JUMP_TO": return Tag.FROG_PREFER_JUMP_TO;
            case "FROGS_SPAWNABLE_ON": return Tag.FROGS_SPAWNABLE_ON;
            case "GEODE_INVALID_BLOCKS": return Tag.GEODE_INVALID_BLOCKS;
            case "GOATS_SPAWNABLE_ON": return Tag.GOATS_SPAWNABLE_ON;
            case "GOLD_ORES": return Tag.GOLD_ORES;
            case "GUARDED_BY_PIGLINS": return Tag.GUARDED_BY_PIGLINS;
            case "HAPPY_GHAST_AVOIDS": return Tag.HAPPY_GHAST_AVOIDS;
            case "HOGLIN_REPELLENTS": return Tag.HOGLIN_REPELLENTS;
            case "ICE": return Tag.ICE;
            case "IMPERMEABLE": return Tag.IMPERMEABLE;
            case "INCORRECT_FOR_DIAMOND_TOOL": return Tag.INCORRECT_FOR_DIAMOND_TOOL;
            case "INCORRECT_FOR_GOLD_TOOL": return Tag.INCORRECT_FOR_GOLD_TOOL;
            case "INCORRECT_FOR_IRON_TOOL": return Tag.INCORRECT_FOR_IRON_TOOL;
            case "INCORRECT_FOR_NETHERITE_TOOL": return Tag.INCORRECT_FOR_NETHERITE_TOOL;
            case "INCORRECT_FOR_STONE_TOOL": return Tag.INCORRECT_FOR_STONE_TOOL;
            case "INCORRECT_FOR_WOODEN_TOOL": return Tag.INCORRECT_FOR_WOODEN_TOOL;
            case "INFINIBURN_END": return Tag.INFINIBURN_END;
            case "INFINIBURN_NETHER": return Tag.INFINIBURN_NETHER;
            case "INFINIBURN_OVERWORLD": return Tag.INFINIBURN_OVERWORLD;
            case "INSIDE_STEP_SOUND_BLOCKS": return Tag.INSIDE_STEP_SOUND_BLOCKS;
            case "INVALID_SPAWN_INSIDE": return Tag.INVALID_SPAWN_INSIDE;
            case "IRON_ORES": return Tag.IRON_ORES;
            case "ITEMS_ARMADILLO_FOOD": return Tag.ITEMS_ARMADILLO_FOOD;
            case "ITEMS_ARROWS": return Tag.ITEMS_ARROWS;
            case "ITEMS_AXES": return Tag.ITEMS_AXES;
            case "ITEMS_AXOLOTL_FOOD": return Tag.ITEMS_AXOLOTL_FOOD;
            case "ITEMS_BANNERS": return Tag.ITEMS_BANNERS;
            case "ITEMS_BEACON_PAYMENT_ITEMS": return Tag.ITEMS_BEACON_PAYMENT_ITEMS;
            case "ITEMS_BEE_FOOD": return Tag.ITEMS_BEE_FOOD;
            case "ITEMS_BOATS": return Tag.ITEMS_BOATS;
            case "ITEMS_BOOK_CLONING_TARGET": return Tag.ITEMS_BOOK_CLONING_TARGET;
            case "ITEMS_BOOKSHELF_BOOKS": return Tag.ITEMS_BOOKSHELF_BOOKS;
            case "ITEMS_BREAKS_DECORATED_POTS": return Tag.ITEMS_BREAKS_DECORATED_POTS;
            case "ITEMS_BREWING_FUEL": return Tag.ITEMS_BREWING_FUEL;
            case "ITEMS_BUNDLES": return Tag.ITEMS_BUNDLES;
            case "ITEMS_CAMEL_FOOD": return Tag.ITEMS_CAMEL_FOOD;
            case "ITEMS_CAT_FOOD": return Tag.ITEMS_CAT_FOOD;
            case "ITEMS_CHEST_ARMOR": return Tag.ITEMS_CHEST_ARMOR;
            case "ITEMS_CHEST_BOATS": return Tag.ITEMS_CHEST_BOATS;
            case "ITEMS_CHICKEN_FOOD": return Tag.ITEMS_CHICKEN_FOOD;
            case "ITEMS_COALS": return Tag.ITEMS_COALS;
            case "ITEMS_COMPASSES": return Tag.ITEMS_COMPASSES;
            case "ITEMS_COW_FOOD": return Tag.ITEMS_COW_FOOD;
            case "ITEMS_CREEPER_DROP_MUSIC_DISCS": return Tag.ITEMS_CREEPER_DROP_MUSIC_DISCS;
            case "ITEMS_CREEPER_IGNITERS": return Tag.ITEMS_CREEPER_IGNITERS;
            case "ITEMS_DECORATED_POT_INGREDIENTS": return Tag.ITEMS_DECORATED_POT_INGREDIENTS;
            case "ITEMS_DECORATED_POT_SHERDS": return Tag.ITEMS_DECORATED_POT_SHERDS;
            case "ITEMS_DIAMOND_TOOL_MATERIALS": return Tag.ITEMS_DIAMOND_TOOL_MATERIALS;
            case "ITEMS_DROWNED_PREFERRED_WEAPONS": return Tag.ITEMS_DROWNED_PREFERRED_WEAPONS;
            case "ITEMS_DUPLICATES_ALLAYS": return Tag.ITEMS_DUPLICATES_ALLAYS;
            case "ITEMS_DYEABLE": return Tag.ITEMS_DYEABLE;
            case "ITEMS_EGGS": return Tag.ITEMS_EGGS;
            case "ITEMS_ENCHANTABLE_ARMOR": return Tag.ITEMS_ENCHANTABLE_ARMOR;
            case "ITEMS_ENCHANTABLE_BOW": return Tag.ITEMS_ENCHANTABLE_BOW;
            case "ITEMS_ENCHANTABLE_CHEST_ARMOR": return Tag.ITEMS_ENCHANTABLE_CHEST_ARMOR;
            case "ITEMS_ENCHANTABLE_CROSSBOW": return Tag.ITEMS_ENCHANTABLE_CROSSBOW;
            case "ITEMS_ENCHANTABLE_DURABILITY": return Tag.ITEMS_ENCHANTABLE_DURABILITY;
            case "ITEMS_ENCHANTABLE_EQUIPPABLE": return Tag.ITEMS_ENCHANTABLE_EQUIPPABLE;
            case "ITEMS_ENCHANTABLE_FIRE_ASPECT": return Tag.ITEMS_ENCHANTABLE_FIRE_ASPECT;
            case "ITEMS_ENCHANTABLE_FISHING": return Tag.ITEMS_ENCHANTABLE_FISHING;
            case "ITEMS_ENCHANTABLE_FOOT_ARMOR": return Tag.ITEMS_ENCHANTABLE_FOOT_ARMOR;
            case "ITEMS_ENCHANTABLE_HEAD_ARMOR": return Tag.ITEMS_ENCHANTABLE_HEAD_ARMOR;
            case "ITEMS_ENCHANTABLE_LEG_ARMOR": return Tag.ITEMS_ENCHANTABLE_LEG_ARMOR;
            case "ITEMS_ENCHANTABLE_MACE": return Tag.ITEMS_ENCHANTABLE_MACE;
            case "ITEMS_ENCHANTABLE_MINING": return Tag.ITEMS_ENCHANTABLE_MINING;
            case "ITEMS_ENCHANTABLE_MINING_LOOT": return Tag.ITEMS_ENCHANTABLE_MINING_LOOT;
            case "ITEMS_ENCHANTABLE_SHARP_WEAPON": return Tag.ITEMS_ENCHANTABLE_SHARP_WEAPON;
            case "ITEMS_ENCHANTABLE_SWORD": return Tag.ITEMS_ENCHANTABLE_SWORD;
            case "ITEMS_ENCHANTABLE_TRIDENT": return Tag.ITEMS_ENCHANTABLE_TRIDENT;
            case "ITEMS_ENCHANTABLE_VANISHING": return Tag.ITEMS_ENCHANTABLE_VANISHING;
            case "ITEMS_ENCHANTABLE_WEAPON": return Tag.ITEMS_ENCHANTABLE_WEAPON;
            case "ITEMS_FISHES": return Tag.ITEMS_FISHES;
            case "ITEMS_FOOT_ARMOR": return Tag.ITEMS_FOOT_ARMOR;
            case "ITEMS_FOX_FOOD": return Tag.ITEMS_FOX_FOOD;
            case "ITEMS_FROG_FOOD": return Tag.ITEMS_FROG_FOOD;
            case "ITEMS_FURNACE_MINECART_FUEL": return Tag.ITEMS_FURNACE_MINECART_FUEL;
            case "ITEMS_GAZE_DISGUISE_EQUIPMENT": return Tag.ITEMS_GAZE_DISGUISE_EQUIPMENT;
            case "ITEMS_GOAT_FOOD": return Tag.ITEMS_GOAT_FOOD;
            case "ITEMS_GOLD_TOOL_MATERIALS": return Tag.ITEMS_GOLD_TOOL_MATERIALS;
            case "ITEMS_HANGING_SIGNS": return Tag.ITEMS_HANGING_SIGNS;
            case "ITEMS_HAPPY_GHAST_FOOD": return Tag.ITEMS_HAPPY_GHAST_FOOD;
            case "ITEMS_HAPPY_GHAST_TEMPT_ITEMS": return Tag.ITEMS_HAPPY_GHAST_TEMPT_ITEMS;
            case "ITEMS_HARNESSES": return Tag.ITEMS_HARNESSES;
            case "ITEMS_HEAD_ARMOR": return Tag.ITEMS_HEAD_ARMOR;
            case "ITEMS_HOES": return Tag.ITEMS_HOES;
            case "ITEMS_HOGLIN_FOOD": return Tag.ITEMS_HOGLIN_FOOD;
            case "ITEMS_HORSE_FOOD": return Tag.ITEMS_HORSE_FOOD;
            case "ITEMS_HORSE_TEMPT_ITEMS": return Tag.ITEMS_HORSE_TEMPT_ITEMS;
            case "ITEMS_IRON_TOOL_MATERIALS": return Tag.ITEMS_IRON_TOOL_MATERIALS;
            case "ITEMS_LECTERN_BOOKS": return Tag.ITEMS_LECTERN_BOOKS;
            case "ITEMS_LEG_ARMOR": return Tag.ITEMS_LEG_ARMOR;
            case "ITEMS_LLAMA_FOOD": return Tag.ITEMS_LLAMA_FOOD;
            case "ITEMS_LLAMA_TEMPT_ITEMS": return Tag.ITEMS_LLAMA_TEMPT_ITEMS;
            case "ITEMS_MAP_INVISIBILITY_EQUIPMENT": return Tag.ITEMS_MAP_INVISIBILITY_EQUIPMENT;
            case "ITEMS_MEAT": return Tag.ITEMS_MEAT;
            case "ITEMS_NETHERITE_TOOL_MATERIALS": return Tag.ITEMS_NETHERITE_TOOL_MATERIALS;
            case "ITEMS_NON_FLAMMABLE_WOOD": return Tag.ITEMS_NON_FLAMMABLE_WOOD;
            case "ITEMS_OCELOT_FOOD": return Tag.ITEMS_OCELOT_FOOD;
            case "ITEMS_PANDA_EATS_FROM_GROUND": return Tag.ITEMS_PANDA_EATS_FROM_GROUND;
            case "ITEMS_PANDA_FOOD": return Tag.ITEMS_PANDA_FOOD;
            case "ITEMS_PARROT_FOOD": return Tag.ITEMS_PARROT_FOOD;
            case "ITEMS_PARROT_POISONOUS_FOOD": return Tag.ITEMS_PARROT_POISONOUS_FOOD;
            case "ITEMS_PICKAXES": return Tag.ITEMS_PICKAXES;
            case "ITEMS_PIG_FOOD": return Tag.ITEMS_PIG_FOOD;
            case "ITEMS_PIGLIN_FOOD": return Tag.ITEMS_PIGLIN_FOOD;
            case "ITEMS_PIGLIN_LOVED": return Tag.ITEMS_PIGLIN_LOVED;
            case "ITEMS_PIGLIN_PREFERRED_WEAPONS": return Tag.ITEMS_PIGLIN_PREFERRED_WEAPONS;
            case "ITEMS_PIGLIN_SAFE_ARMOR": return Tag.ITEMS_PIGLIN_SAFE_ARMOR;
            case "ITEMS_PILLAGER_PREFERRED_WEAPONS": return Tag.ITEMS_PILLAGER_PREFERRED_WEAPONS;
            case "ITEMS_RABBIT_FOOD": return Tag.ITEMS_RABBIT_FOOD;
            case "ITEMS_REPAIRS_CHAIN_ARMOR": return Tag.ITEMS_REPAIRS_CHAIN_ARMOR;
            case "ITEMS_REPAIRS_DIAMOND_ARMOR": return Tag.ITEMS_REPAIRS_DIAMOND_ARMOR;
            case "ITEMS_REPAIRS_GOLD_ARMOR": return Tag.ITEMS_REPAIRS_GOLD_ARMOR;
            case "ITEMS_REPAIRS_IRON_ARMOR": return Tag.ITEMS_REPAIRS_IRON_ARMOR;
            case "ITEMS_REPAIRS_LEATHER_ARMOR": return Tag.ITEMS_REPAIRS_LEATHER_ARMOR;
            case "ITEMS_REPAIRS_NETHERITE_ARMOR": return Tag.ITEMS_REPAIRS_NETHERITE_ARMOR;
            case "ITEMS_REPAIRS_TURTLE_HELMET": return Tag.ITEMS_REPAIRS_TURTLE_HELMET;
            case "ITEMS_REPAIRS_WOLF_ARMOR": return Tag.ITEMS_REPAIRS_WOLF_ARMOR;
            case "ITEMS_SHEEP_FOOD": return Tag.ITEMS_SHEEP_FOOD;
            case "ITEMS_SHOVELS": return Tag.ITEMS_SHOVELS;
            case "ITEMS_SKELETON_PREFERRED_WEAPONS": return Tag.ITEMS_SKELETON_PREFERRED_WEAPONS;
            case "ITEMS_SKULLS": return Tag.ITEMS_SKULLS;
            case "ITEMS_SNIFFER_FOOD": return Tag.ITEMS_SNIFFER_FOOD;
            case "ITEMS_STONE_TOOL_MATERIALS": return Tag.ITEMS_STONE_TOOL_MATERIALS;
            case "ITEMS_STRIDER_FOOD": return Tag.ITEMS_STRIDER_FOOD;
            case "ITEMS_STRIDER_TEMPT_ITEMS": return Tag.ITEMS_STRIDER_TEMPT_ITEMS;
            case "ITEMS_SWORDS": return Tag.ITEMS_SWORDS;
            case "ITEMS_TRIM_MATERIALS": return Tag.ITEMS_TRIM_MATERIALS;
            case "ITEMS_TRIMMABLE_ARMOR": return Tag.ITEMS_TRIMMABLE_ARMOR;
            case "ITEMS_TURTLE_FOOD": return Tag.ITEMS_TURTLE_FOOD;
            case "ITEMS_VILLAGER_PICKS_UP": return Tag.ITEMS_VILLAGER_PICKS_UP;
            case "ITEMS_VILLAGER_PLANTABLE_SEEDS": return Tag.ITEMS_VILLAGER_PLANTABLE_SEEDS;
            case "ITEMS_WITHER_SKELETON_DISLIKED_WEAPONS": return Tag.ITEMS_WITHER_SKELETON_DISLIKED_WEAPONS;
            case "ITEMS_WOLF_FOOD": return Tag.ITEMS_WOLF_FOOD;
            case "ITEMS_WOODEN_TOOL_MATERIALS": return Tag.ITEMS_WOODEN_TOOL_MATERIALS;
            case "JUNGLE_LOGS": return Tag.JUNGLE_LOGS;
            case "LAPIS_ORES": return Tag.LAPIS_ORES;
            case "LAVA_POOL_STONE_CANNOT_REPLACE": return Tag.LAVA_POOL_STONE_CANNOT_REPLACE;
            case "LEAVES": return Tag.LEAVES;
            case "LOGS": return Tag.LOGS;
            case "LOGS_THAT_BURN": return Tag.LOGS_THAT_BURN;
            case "LUSH_GROUND_REPLACEABLE": return Tag.LUSH_GROUND_REPLACEABLE;
            case "MAINTAINS_FARMLAND": return Tag.MAINTAINS_FARMLAND;
            case "MANGROVE_LOGS": return Tag.MANGROVE_LOGS;
            case "MANGROVE_LOGS_CAN_GROW_THROUGH": return Tag.MANGROVE_LOGS_CAN_GROW_THROUGH;
            case "MANGROVE_ROOTS_CAN_GROW_THROUGH": return Tag.MANGROVE_ROOTS_CAN_GROW_THROUGH;
            case "MINEABLE_AXE": return Tag.MINEABLE_AXE;
            case "MINEABLE_HOE": return Tag.MINEABLE_HOE;
            case "MINEABLE_PICKAXE": return Tag.MINEABLE_PICKAXE;
            case "MINEABLE_SHOVEL": return Tag.MINEABLE_SHOVEL;
            case "MOB_INTERACTABLE_DOORS": return Tag.MOB_INTERACTABLE_DOORS;
            case "MOOSHROOMS_SPAWNABLE_ON": return Tag.MOOSHROOMS_SPAWNABLE_ON;
            case "MOSS_REPLACEABLE": return Tag.MOSS_REPLACEABLE;
            case "MUSHROOM_GROW_BLOCK": return Tag.MUSHROOM_GROW_BLOCK;
            case "NEEDS_DIAMOND_TOOL": return Tag.NEEDS_DIAMOND_TOOL;
            case "NEEDS_IRON_TOOL": return Tag.NEEDS_IRON_TOOL;
            case "NEEDS_STONE_TOOL": return Tag.NEEDS_STONE_TOOL;
            case "NETHER_CARVER_REPLACEABLES": return Tag.NETHER_CARVER_REPLACEABLES;
            case "NYLIUM": return Tag.NYLIUM;
            case "OAK_LOGS": return Tag.OAK_LOGS;
            case "OCCLUDES_VIBRATION_SIGNALS": return Tag.OCCLUDES_VIBRATION_SIGNALS;
            case "OVERWORLD_CARVER_REPLACEABLES": return Tag.OVERWORLD_CARVER_REPLACEABLES;
            case "PALE_OAK_LOGS": return Tag.PALE_OAK_LOGS;
            case "PARROTS_SPAWNABLE_ON": return Tag.PARROTS_SPAWNABLE_ON;
            case "PIGLIN_REPELLENTS": return Tag.PIGLIN_REPELLENTS;
            case "PLANKS": return Tag.PLANKS;
            case "POLAR_BEARS_SPAWNABLE_ON_ALTERNATE": return Tag.POLAR_BEARS_SPAWNABLE_ON_ALTERNATE;
            case "PORTALS": return Tag.PORTALS;
            case "PRESSURE_PLATES": return Tag.PRESSURE_PLATES;
            case "PREVENT_MOB_SPAWNING_INSIDE": return Tag.PREVENT_MOB_SPAWNING_INSIDE;
            case "RABBITS_SPAWNABLE_ON": return Tag.RABBITS_SPAWNABLE_ON;
            case "RAILS": return Tag.RAILS;
            case "REDSTONE_ORES": return Tag.REDSTONE_ORES;
            case "REPLACEABLE": return Tag.REPLACEABLE;
            case "REPLACEABLE_BY_MUSHROOMS": return Tag.REPLACEABLE_BY_MUSHROOMS;
            case "REPLACEABLE_BY_TREES": return Tag.REPLACEABLE_BY_TREES;
            case "TRIGGERS_AMBIENT_DRIED_GHAST_BLOCK_SOUNDS": return Tag.TRIGGERS_AMBIENT_DRIED_GHAST_BLOCK_SOUNDS;
            case "SAND": return Tag.SAND;
            case "SAPLINGS": return Tag.SAPLINGS;
            case "SCULK_REPLACEABLE": return Tag.SCULK_REPLACEABLE;
            case "SCULK_REPLACEABLE_WORLD_GEN": return Tag.SCULK_REPLACEABLE_WORLD_GEN;
            case "SHULKER_BOXES": return Tag.SHULKER_BOXES;
            case "SIGNS": return Tag.SIGNS;
            case "SLABS": return Tag.SLABS;
            case "SMALL_DRIPLEAF_PLACEABLE": return Tag.SMALL_DRIPLEAF_PLACEABLE;
            case "SMALL_FLOWERS": return Tag.SMALL_FLOWERS;
            case "SMELTS_TO_GLASS": return Tag.SMELTS_TO_GLASS;
            case "SNAPS_GOAT_HORN": return Tag.SNAPS_GOAT_HORN;
            case "SNIFFER_DIGGABLE_BLOCK": return Tag.SNIFFER_DIGGABLE_BLOCK;
            case "SNIFFER_EGG_HATCH_BOOST": return Tag.SNIFFER_EGG_HATCH_BOOST;
            case "SNOW": return Tag.SNOW;
            case "SNOW_LAYER_CAN_SURVIVE_ON": return Tag.SNOW_LAYER_CAN_SURVIVE_ON;
            case "SNOW_LAYER_CANNOT_SURVIVE_ON": return Tag.SNOW_LAYER_CANNOT_SURVIVE_ON;
            case "SOUL_FIRE_BASE_BLOCKS": return Tag.SOUL_FIRE_BASE_BLOCKS;
            case "SOUL_SPEED_BLOCKS": return Tag.SOUL_SPEED_BLOCKS;
            case "SPRUCE_LOGS": return Tag.SPRUCE_LOGS;
            case "STAIRS": return Tag.STAIRS;
            case "STANDING_SIGNS": return Tag.STANDING_SIGNS;
            case "STONE_BRICKS": return Tag.STONE_BRICKS;
            case "STONE_BUTTONS": return Tag.STONE_BUTTONS;
            case "STONE_ORE_REPLACEABLES": return Tag.STONE_ORE_REPLACEABLES;
            case "STONE_PRESSURE_PLATES": return Tag.STONE_PRESSURE_PLATES;
            case "STRIDER_WARM_BLOCKS": return Tag.STRIDER_WARM_BLOCKS;
            case "SWORD_EFFICIENT": return Tag.SWORD_EFFICIENT;
            case "SWORD_INSTANTLY_MINES": return Tag.SWORD_INSTANTLY_MINES;
            case "TERRACOTTA": return Tag.TERRACOTTA;
            case "TRAIL_RUINS_REPLACEABLE": return Tag.TRAIL_RUINS_REPLACEABLE;
            case "TRAPDOORS": return Tag.TRAPDOORS;
            case "TRIGGERS_AMBIENT_DESERT_DRY_VEGETATION_BLOCK_SOUNDS": return Tag.TRIGGERS_AMBIENT_DESERT_DRY_VEGETATION_BLOCK_SOUNDS;
            case "TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS": return Tag.TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS;
            case "UNDERWATER_BONEMEALS": return Tag.UNDERWATER_BONEMEALS;
            case "UNSTABLE_BOTTOM_CENTER": return Tag.UNSTABLE_BOTTOM_CENTER;
            case "VALID_SPAWN": return Tag.VALID_SPAWN;
            case "VIBRATION_RESONATORS": return Tag.VIBRATION_RESONATORS;
            case "WALL_CORALS": return Tag.WALL_CORALS;
            case "WALL_HANGING_SIGNS": return Tag.WALL_HANGING_SIGNS;
            case "WALL_POST_OVERRIDE": return Tag.WALL_POST_OVERRIDE;
            case "WALL_SIGNS": return Tag.WALL_SIGNS;
            case "WALLS": return Tag.WALLS;
            case "WARPED_STEMS": return Tag.WARPED_STEMS;
            case "WART_BLOCKS": return Tag.WART_BLOCKS;
            case "WITHER_IMMUNE": return Tag.WITHER_IMMUNE;
            case "WITHER_SUMMON_BASE_BLOCKS": return Tag.WITHER_SUMMON_BASE_BLOCKS;
            case "WOLVES_SPAWNABLE_ON": return Tag.WOLVES_SPAWNABLE_ON;
            case "WOODEN_BUTTONS": return Tag.WOODEN_BUTTONS;
            case "WOODEN_DOORS": return Tag.WOODEN_DOORS;
            case "WOODEN_FENCES": return Tag.WOODEN_FENCES;
            case "WOODEN_PRESSURE_PLATES": return Tag.WOODEN_PRESSURE_PLATES;
            case "WOODEN_SLABS": return Tag.WOODEN_SLABS;
            case "WOODEN_STAIRS": return Tag.WOODEN_STAIRS;
            case "WOODEN_TRAPDOORS": return Tag.WOODEN_TRAPDOORS;
            case "WOOL": return Tag.WOOL;
            case "WOOL_CARPETS": return Tag.WOOL_CARPETS;
        }
        throw new IllegalArgumentException("Unknown tag: " + tag);
    }
}
