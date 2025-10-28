package com.funnyboyroks.drawers.data;

import com.funnyboyroks.drawers.MMComponent;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@Configuration
public class Lang {
    // This comment really appears at the top of the file, not really associated with `infinite`.
    @Comment({
        "Configure the text that is displayed on the drawer.  Most fields support MiniMessage (https://docs.advntr.dev/minimessage/format.html)",
        ""
    })
    public Component infinite = Component.text("∞");
    public Component empty = Component.text("Empty").color(NamedTextColor.GRAY);
    @Comment({
        "'<quantity>' is replaced by the quantity of items in the drawer.",
        "See this for styling info: https://docs.papermc.io/adventure/minimessage/dynamic-replacements/#insert-a-number",
    })
    public MMComponent quantity = MMComponent.parsed("<quantity>");
    public Component item_name = Component.text("Drawer");
}
