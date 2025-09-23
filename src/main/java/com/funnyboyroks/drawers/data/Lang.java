package com.funnyboyroks.drawers.data;

import java.util.Arrays;
import java.util.stream.Collectors;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;

@Configuration
public class Lang {
    // This comment really appears at the top of the file, not really associated with `infinite`.
    @Comment({
        "Configure the text that is displayed on the drawer.  Most fields support MiniMessage (https://docs.advntr.dev/minimessage/format.html)",
        ""
    })
    public String infinite = "∞";
    public String empty = "<gray>Empty</gray>";
    @Comment("'%d' is replaced by the quantity of items in the drawer")
    public String quantity = "%d";

    @Override
    public String toString() {
        return "Config[" +
            Arrays.stream(new String[][]{
                { "infinite", this.infinite },
                { "empty",    this.empty    },
                { "quantity", this.quantity },
            })
            .map(s -> s[0] + "='" + s[1] + "'")
            .collect(Collectors.joining(","))
        + "]";
    }
}
