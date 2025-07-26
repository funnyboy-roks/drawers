package com.funnyboyroks.drawers;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class Util {
    private static final ItemStack DRAWER_ITEM = ItemStack.of(Material.BARREL);

    static {
        DRAWER_ITEM.setData(
            DataComponentTypes.CUSTOM_MODEL_DATA,
            CustomModelData.customModelData()
                .addString(Util.ns("barrel").toString())
                .build()
        );

        DRAWER_ITEM.editMeta(meta -> {
            meta.customName(Component.translatable("block.drawers.drawer", "Drawer").decoration(TextDecoration.ITALIC, false));
        });
    }

    public static ItemStack drawerItem() {
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
}
