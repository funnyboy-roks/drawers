package com.funnyboyroks.drawers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

/**
 * A simple wrapper around a string for minimessage serialisation/deserialisation
 */
public class MMComponent {
    private final String inner;
    private MMComponent(String inner) {
        this.inner = inner;
    }

    public Component toComponent(TagResolver... tags) {
        return MiniMessage.miniMessage().deserialize(this.inner, tags);
    }

    public static MMComponent parsed(String text) {
        return new MMComponent(text);
    }

    public static class Serializer implements de.exlll.configlib.Serializer<MMComponent, String> {
		@Override
		public MMComponent deserialize(String s) {
            // NOTE: <quantity::'#'> has the same behaviour as %d.  (<quantity> adds thousands separators)
            return new MMComponent(s.replaceAll("%d", "<quantity::'#'>"));
		}

		@Override
		public String serialize(MMComponent c) {
            return c.inner;
		}
    }
}
