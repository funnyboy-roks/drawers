package com.funnyboyroks.drawers;

import java.util.Arrays;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ExplosionResult;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Display.Brightness;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;

public class Listeners implements Listener {

    private static final AxisAngle4f[] ANGLES = new AxisAngle4f[] {
        new AxisAngle4f(0f, 0f, 0f, 0f), // NORTH
        new AxisAngle4f(-0.5f * (float)Math.PI, 0f, 1f, 0f), // EAST
        new AxisAngle4f((float)Math.PI, 0f, 1f, 0f), // SOUTH
        new AxisAngle4f(0.5f * (float)Math.PI, 0f, 1f, 0f), // WEST
        new AxisAngle4f(0.5f * (float)Math.PI, 1f, 0f, 0f), // UP
        new AxisAngle4f(-0.5f * (float)Math.PI, 1f, 0f, 0f), // DOWN
    };

    private static final AxisAngle4f[] OPPOSING_ANGLES = new AxisAngle4f[] {
        new AxisAngle4f((float)Math.PI, 0f, 1f, 0f), // NORTH
        new AxisAngle4f(0.5f * (float)Math.PI, 0f, 1f, 0f), // EAST
        new AxisAngle4f(0f, 0f, 0f, 0f), // SOUTH
        new AxisAngle4f(-0.5f * (float)Math.PI, 0f, 1f, 0f), // WEST
        new AxisAngle4f(0.5f * (float)Math.PI, 1f, 0f, 0f), // UP
        new AxisAngle4f(-0.5f * (float)Math.PI, 1f, 0f, 0f), // DOWN
    };

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.BARREL) return;

        CustomModelData modelData = event.getItemInHand().getData(DataComponentTypes.CUSTOM_MODEL_DATA);
        if (modelData == null || !modelData.strings().contains(Util.ns("barrel").toString())) return;

        Directional dir = (Directional) block.getBlockData();

        ItemDisplay item = block.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ItemDisplay.class, entity -> {
            entity.setBrightness(new Brightness(15, 15));

            entity.setTransformationMatrix(
                new Matrix4f()
                    .rotate(ANGLES[dir.getFacing().ordinal()])
                    .translate(0f, 2f/16f, -0.5f)
                    .scale(
                        new Vector3f(
                            8f/16f,
                            8f/16f,
                            0.1f
                        )
                    )
            );
        });

        TextDisplay text = block.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), TextDisplay.class, entity -> {
            entity.setBrightness(new Brightness(15, 15));
            entity.setBackgroundColor(org.bukkit.Color.fromARGB(0));

            Matrix4f transformation = new Matrix4f()
                .rotate(OPPOSING_ANGLES[dir.getFacing().ordinal()]);

            if (dir.getFacing().getModY() != 0) {
                transformation = transformation.rotate((float)Math.PI, 0f, 1f, 0f);
            }

            transformation = transformation.translate(0f, -6f/16f, 0.5f)
                .scale(0.75f);

            entity.setTransformationMatrix(transformation);
        });

        DrawerBlock drawer = new DrawerBlock(block, item, text, new DrawerBlock.DrawerState());
        drawer.updateDisplay();
        drawer.saveData();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.BARREL) return;

        Optional<DrawerBlock> optDrawer = DrawerBlock.fromBlock(block);
        if (optDrawer.isEmpty()) return;
        DrawerBlock drawer = optDrawer.get(); // Checked above
        drawer.dropContents();

        // Change block drop
        if (event.isDropItems()) {
            event.setDropItems(false);
            drawer.block.getWorld().dropItemNaturally(drawer.block.getLocation().clone().add(0.5, 0.5, 0.5), Util.drawerItem());
        }

        Util.tryRemoveEntity(drawer.itemDisplay);
        Util.tryRemoveEntity(drawer.textDisplay);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        for (Block block : event.blockList()) {
            if (block.getType() != Material.BARREL) return;

            Optional<DrawerBlock> optDrawer = DrawerBlock.fromBlock(block);
            if (optDrawer.isEmpty()) return;
            DrawerBlock drawer = optDrawer.get(); // Checked above
            drawer.dropContents();

            Util.tryRemoveEntity(drawer.itemDisplay);
            Util.tryRemoveEntity(drawer.textDisplay);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            if (block.getType() != Material.BARREL) return;

            Optional<DrawerBlock> optDrawer = DrawerBlock.fromBlock(block);
            if (optDrawer.isEmpty()) return;
            DrawerBlock drawer = optDrawer.get(); // Checked above
            drawer.dropContents();
            block.setType(Material.AIR);
            if (event.getExplosionResult() == ExplosionResult.DESTROY || event.getExplosionResult() == ExplosionResult.DESTROY_WITH_DECAY) {
                drawer.block.getWorld().dropItemNaturally(drawer.block.getLocation().clone().add(0.5, 0.5, 0.5), Util.drawerItem());
            }

            Util.tryRemoveEntity(drawer.itemDisplay);
            Util.tryRemoveEntity(drawer.textDisplay);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.BARREL) return;

        Optional<DrawerBlock> optDrawer = DrawerBlock.fromBlock(block);
        if (optDrawer.isEmpty()) return;
        DrawerBlock drawer = optDrawer.get(); // Checked above

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack is = event.getItem();
            if (event.getPlayer().isSneaking() && is != null) return;
            // add a single item
            if (event.getBlockFace() == drawer.getBlockData().getFacing() && is != null) {
                Optional<ItemStack> overflowOpt = drawer.add(is);
                event.getPlayer().getInventory().setItem(event.getHand(), overflowOpt.isPresent() ? overflowOpt.get() : null);
                drawer.saveData();
                drawer.updateDisplay();
            }
            event.setCancelled(true);
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (drawer.isEmpty()) return;
            if (event.getBlockFace() != drawer.getBlockData().getFacing()) return;
            ItemStack stack = drawer.removeItem(event.getPlayer().isSneaking());
            if (stack != null) {
                event.getPlayer().give(stack);
            }
            drawer.saveData();
            drawer.updateDisplay();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        Inventory dest = event.getDestination();
        Location destLoc = dest.getLocation();
        if (destLoc != null) {
            Block block = destLoc.getBlock();
            if (block != null && block.getType() == Material.BARREL) {
                Optional<DrawerBlock> optDrawer = DrawerBlock.fromBlock(block);
                if (optDrawer.isPresent()) {
                    DrawerBlock drawer = optDrawer.get(); // Checked above
                    ItemStack stack = event.getItem();
                    Optional<ItemStack> overflowOpt = drawer.add(stack);
                    if (overflowOpt.isEmpty() || !overflowOpt.get().equals(stack)) {
                        event.setItem(new ItemStack(Material.AIR));
                        drawer.saveData();
                        drawer.updateDisplay();
                    } else {
                        event.setCancelled(true);
                    }
                    return;
                }
            }
        }

        // Dest is not a drawer, but source might be
        Inventory src = event.getSource();
        Location srcLoc = src.getLocation();
        if (srcLoc != null) {
            Block block = srcLoc.getBlock();
            if (block != null && block.getType() == Material.BARREL) {
                Optional<DrawerBlock> optDrawer = DrawerBlock.fromBlock(block);
                if (optDrawer.isPresent()) {
                    DrawerBlock drawer = optDrawer.get(); // Checked above
                    if (dest.firstEmpty() == -1 && Arrays.stream(dest.getContents()).noneMatch(stack -> stack.getAmount() < stack.getMaxStackSize())) return;
                    drawer.removeItem(false);
                    drawer.saveData();
                    drawer.updateDisplay();
                }
            }
        }
    }

    @EventHandler
    public void onHopperSearch(HopperInventorySearchEvent event) {
        Block block = event.getSearchBlock();
        if (block == null || block.getType() != Material.BARREL) return;

        Optional<DrawerBlock> optDrawer = DrawerBlock.fromBlock(block);
        if (optDrawer.isEmpty()) return;
        DrawerBlock drawer = optDrawer.get(); // Checked above

        Inventory newInv = Bukkit.createInventory(event.getInventory().getHolder(), 9);
        if (!drawer.isEmpty()) {
            newInv.setItem(0, drawer.state.item().asOne());
        }
        event.setInventory(newInv);
    }
}
