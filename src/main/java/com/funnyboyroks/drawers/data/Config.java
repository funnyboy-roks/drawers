package com.funnyboyroks.drawers.data;

import de.exlll.configlib.Configuration;
import de.exlll.configlib.Comment;

@Configuration
public class Config {
    @Comment({
        "The maximum number of stacks that may be stored in a base drawer.",
        "Example: if max_stack_count = 32, then a cobblestone drawer may store up to 2048 items",
        "         and an ender pearl drawer may store up to 512 items.",
    })
    public int max_stack_count = 32;

    @Override
    public String toString() {
        return "Config[max_stack_count=%d]".formatted(this.max_stack_count);
    }
}
