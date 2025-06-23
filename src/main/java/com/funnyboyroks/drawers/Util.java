package com.funnyboyroks.drawers;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.papermc.paper.entity.PlayerGiveResult;

public class Util {
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
