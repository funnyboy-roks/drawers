package com.funnyboyroks.drawers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.funnyboyroks.drawers.data.Config;
import com.funnyboyroks.drawers.data.DataHandler;
import com.funnyboyroks.drawers.data.Lang;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;

import de.exlll.configlib.Serializer;
import de.exlll.configlib.YamlConfigurations;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.math.BlockPosition;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.farlandsmc.componentutils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class Drawers extends JavaPlugin {

    private static Drawers INSTANCE;
    private Config config;
    private Lang lang;
    private DataHandler dataHandler;

    public static Drawers instance() {
        return Drawers.INSTANCE;
    }

    public static Config config() {
        return Drawers.instance().config;
    }

    public static Lang lang() {
        return Drawers.instance().lang;
    }

    public static DataHandler dataHandler() {
        return Drawers.instance().dataHandler;
    }

    public Drawers() {
        Drawers.INSTANCE = this;
    }

    @Override
    public void onEnable() {
        this.load();

        this.getLogger().info("Loaded Config");

        this.dataHandler = new DataHandler();
        try {
            this.dataHandler.loadData(this);
        } catch (IOException ex) {
            this.getLogger().severe("Unable to load plugin data: " + ex);
            this.getLogger().severe("Disabling plugin");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            try {
                this.dataHandler.saveData();
            } catch (IOException ex) {
                this.getLogger().severe("Unable to save plugin data: " + ex);
            }
        }, 5 * 60 * 20L, 5 * 60 * 20L); // 5 minutes * 60 seconds * 20 ticks

        this.getServer().getPluginManager().registerEvents(new Listeners(), this);

        this.addRecipes();
        this.getLogger().info("Registered Recipes");

        this.addCommands();
    }

    public void load() {
        Path configPath = Paths.get(this.getDataPath().toString(), "config.yml");
        this.config = YamlConfigurations.update(configPath, Config.class);

        Path langPath = Paths.get(this.getDataPath().toString(), "lang.yml");
        this.lang = YamlConfigurations.update(
            langPath,
            Lang.class,
            c -> c
                .header("Configure the text that is displayed on the drawer.  Most fields support MiniMessage (https://docs.advntr.dev/minimessage/format.html)")
                .addSerializer(MMComponent.class, new MMComponent.Serializer())
                .addSerializer(Component.class, new Serializer<Component, String>() {
                    @Override
                    public Component deserialize(String s) {
                        return MiniMessage.miniMessage().deserialize(s);
                    }

                    @Override
                    public String serialize(Component c) {
                        return MiniMessage.builder()
                            .strict(true)
                            .build()
                            .serialize(c);
                    }
                })
        );
    }

    public void addRecipes() {
        this.getServer().addRecipe(this.config.drawer_recipe.recipe(Util.ns("drawer_recipe")));
    }

    private void setCount(CommandSender sender, Location location, Function<Integer, Integer> count) {
        Optional<DrawerBlock> odb = DrawerBlock.fromBlock(location.getBlock());
        if (odb.isEmpty()) {
            sender.sendMessage(Drawers.lang().missing_drawer);
            return;
        }
        DrawerBlock db = odb.get();
        db.state = db.state.withCount(count.apply(db.state.count()));
        sender.sendMessage(Drawers.lang().drawer_update_success);
        db.saveData();
        db.updateDisplay();
    }

    private LiteralCommandNode<CommandSourceStack> drawersCommand() {
        return Commands.literal("drawers")
            .requires(c -> c.getSender().hasPermission("drawers.command"))
            .then(
                Commands.literal("nearby")
                    .requires(c -> c.getSender().hasPermission("drawers.command.nearby"))
                    .then(Commands.argument("radius", DoubleArgumentType.doubleArg(0, 100))
                        .executes(ctx -> {
                            double radius = DoubleArgumentType.getDouble(ctx, "radius");
                            var sender = ctx.getSource().getSender();
                            var executor = ctx.getSource().getExecutor();

                            List<Component> locations = Drawers.dataHandler().data.locations
                                .stream()
                                .filter(l -> l.distanceSquared(executor.getLocation()) < radius * radius)
                                .sorted(Comparator.comparingDouble(l -> l.distanceSquared(executor.getLocation())))
                                .map(l -> {
                                    Optional<DrawerBlock> db = DrawerBlock.fromBlock(l.getBlock());
                                    if (db.isPresent()) {
                                        DrawerBlock d = db.get();
                                        return Drawers.lang().search_line.toComponent(
                                            Placeholder.component("x", Component.text(l.getBlockX())),
                                            Placeholder.component("y", Component.text(l.getBlockY())),
                                            Placeholder.component("z", Component.text(l.getBlockZ())),
                                            Formatter.number("count", d.state.count()),
                                            Placeholder.component("item", ComponentUtils.item(d.state.item(), 1))
                                        );
                                    } else {
                                        return Drawers.lang().search_line_error.toComponent(
                                            Placeholder.component("x", Component.text(l.getBlockX())),
                                            Placeholder.component("y", Component.text(l.getBlockY())),
                                            Placeholder.component("z", Component.text(l.getBlockZ()))
                                        );
                                    }
                                })
                                .collect(Collectors.toList());

                            sender.sendMessage(Drawers.lang().search_header.toComponent(
                                Formatter.number("count", locations.size()),
                                Formatter.choice("choice", locations.size())
                            ));
                            if (!locations.isEmpty()) {
                                sender.sendMessage(Component.join(JoinConfiguration.newlines(), locations));
                            }
                            return Command.SINGLE_SUCCESS;
                        })
                    )
            )
            .then(
                Commands.literal("infinite")
                    .requires(c -> c.getSender().hasPermission("drawers.command.set-count"))
                    .then(Commands.argument("block", ArgumentTypes.blockPosition())
                        .executes(ctx -> {
                            BlockPositionResolver blockPositionResolver = ctx.getArgument("block", BlockPositionResolver.class);
                            BlockPosition blockPosition = blockPositionResolver.resolve(ctx.getSource());
                            var sender = ctx.getSource().getSender();
                            var executor = ctx.getSource().getExecutor();
                            setCount(sender, blockPosition.toLocation(executor.getWorld()), old -> old == -1 ? 1 : -1);
                            return Command.SINGLE_SUCCESS;
                        })
                    )
                    .executes(ctx -> {
                        var sender = ctx.getSource().getSender();
                        var executor = ctx.getSource().getExecutor();
                        if (!(executor instanceof Player player)) {
                            sender.sendMessage(Drawers.lang().player_only);
                            return Command.SINGLE_SUCCESS;
                        }
                        var block = player.getTargetBlock(null, 5);
                        setCount(sender, block.getLocation(), old -> old == -1 ? 1 : -1);
                        return Command.SINGLE_SUCCESS;
                    })
            )
            .then(
                Commands.literal("set-count")
                    .requires(c -> c.getSender().hasPermission("drawers.command.set-count"))
                    .then(
                        Commands.argument("count", IntegerArgumentType.integer(0))
                            .then(Commands.argument("block", ArgumentTypes.blockPosition())
                                .executes(ctx -> {
                                    BlockPositionResolver blockPositionResolver = ctx.getArgument("block", BlockPositionResolver.class);
                                    BlockPosition blockPosition = blockPositionResolver.resolve(ctx.getSource());
                                    int count = ctx.getArgument("count", Integer.class);
                                    var sender = ctx.getSource().getSender();
                                    var executor = ctx.getSource().getExecutor();
                                    setCount(sender, blockPosition.toLocation(executor.getWorld()), _old -> count);
                                    return Command.SINGLE_SUCCESS;
                                })
                            )
                            .executes(ctx -> {
                                int count = ctx.getArgument("count", Integer.class);
                                var sender = ctx.getSource().getSender();
                                var executor = ctx.getSource().getExecutor();
                                if (!(executor instanceof Player player)) {
                                    sender.sendMessage(Drawers.lang().player_only);
                                    return Command.SINGLE_SUCCESS;
                                }
                                var block = player.getTargetBlock(null, 5);
                                setCount(sender, block.getLocation(), _old -> count);
                                return Command.SINGLE_SUCCESS;
                            })
                    )
            )
            .then(
                Commands.literal("reload")
                    .requires(c -> c.getSender().hasPermission("drawers.command.reload"))
                    .executes(ctx -> {
                        var sender = ctx.getSource().getSender();
                        Drawers.instance().load();
                        sender.sendMessage(Drawers.lang().reload_success);
                        return Command.SINGLE_SUCCESS;
                    })
            )
            .build();
    }

    public void addCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(this.drawersCommand(), "Manage the Drawers plugin");
        });
    }

    @Override
    public void onDisable() {
        try {
            this.dataHandler.saveData();
        } catch (IOException ex) {
            this.getLogger().severe("Unable to save plugin data: " + ex);
        }
    }
}
