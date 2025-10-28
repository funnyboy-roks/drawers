package com.funnyboyroks.drawers.data;

import com.funnyboyroks.drawers.MMComponent;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import net.farlandsmc.componentutils.ComponentColor;
import net.kyori.adventure.text.Component;

@Configuration
public class Lang {
    public Component infinite = Component.text("∞");
    public Component empty = ComponentColor.gray("Empty");
    @Comment({
        "'<quantity>' is replaced by the quantity of items in the drawer.",
        "See this for styling info: https://docs.papermc.io/adventure/minimessage/dynamic-replacements/#insert-a-number",
    })
    public MMComponent quantity = MMComponent.parsed("<quantity>");
    public Component item_name = Component.text("Drawer");


    @Comment({
        "",
        "Header for the `/drawers radius` command",
        "<count> - the number of drawers",
        "<choice> - a choice based on the number of drawers",
    })
    public MMComponent search_header = MMComponent.parsed("<gold>Found <count> <choice:'0#drawers|1#drawer|1<drawers'> in radius</gold>");
    @Comment({
        "Line of result in the `/drawers radius` command",
        "<x>, <y>, <z> - the location of the drawer",
        "<count> - the count of items in the drawer (formatted via `quantity`)",
        "<item> - the type of items in the drawer",
    })
    public MMComponent search_line = MMComponent.parsed("<gold><aqua><x> <y> <z></aqua> - <count> * <item></gold>");
    @Comment({
        "Line of result in the `/drawers radius` command when the drawer has an error",
        "<x>, <y>, <z> - the location of the drawer",
    })
    public MMComponent search_line_error = MMComponent.parsed("<red><x> <y> <z> - Drawer error</red>");


    @Comment({
        "",
    })
    public Component missing_drawer = ComponentColor.red("No drawer found at location.");
    public Component drawer_update_success = ComponentColor.green("Drawer updated!");
    public Component player_only = ComponentColor.red("This command may only be run by players");
    public Component reload_success = ComponentColor.green("Configuration and language files reloaded successfully!");
}
