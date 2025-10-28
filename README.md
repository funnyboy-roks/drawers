# Drawers

A plugin that adds drawers to your Minecraft server for storing large
quantities of a single item.

![Drawer holding 314 cobblestone](https://raw.githubusercontent.com/funnyboy-roks/drawers/refs/heads/main/img/screenshot.png)

## Usage

### Server Admin

1. Install plugin jar into your `plugins/` folder
2. Restart your server

The plugin can be configured in the `config.yml` and `lang.yml` files
that get generated when the server starts.

### User

To get started, craft a drawer by surrounding a chest with planks of any
kind in the crafting table:

![Crafting recipe](https://raw.githubusercontent.com/funnyboy-roks/drawers/refs/heads/main/img/recipe.gif)

> Note: This recipe can be configured

Once you have the drawer, place it down (places like a barrel).

Holding the stack of items that you wish to place into the drawer, right
click the front.

You can now interact with the drawer by doing the following:
- right click - add the item in your hand
- double right click - add all matching items in your inventory
- left click - remove a single item
- sneak + left click - remove a full stack of the item

<!-- MODRINTH_EXCLUDE_START -->

## Development

To build the plugin, run

```sh
mvn package
```

<!-- MODRINTH_EXCLUDE_END -->
