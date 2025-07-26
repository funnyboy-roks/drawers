package com.funnyboyroks.drawers.data;

import java.lang.reflect.Type;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocationSerializer implements JsonSerializer<Location>, JsonDeserializer<Location> {

	@Override
	public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        UUID world = UUID.fromString(obj.getAsJsonPrimitive("world").getAsString());
        double x = obj.getAsJsonPrimitive("x").getAsDouble();
        double y = obj.getAsJsonPrimitive("y").getAsDouble();
        double z = obj.getAsJsonPrimitive("z").getAsDouble();
        return new Location(Bukkit.getWorld(world), x, y, z);
	}

	@Override
	public JsonElement serialize(Location src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject ret = new JsonObject();

        ret.add("world", new JsonPrimitive(src.getWorld().getUID().toString()));
        ret.add("x", new JsonPrimitive(src.getX()));
        ret.add("y", new JsonPrimitive(src.getY()));
        ret.add("z", new JsonPrimitive(src.getZ()));

        return ret;
	}

    
}
