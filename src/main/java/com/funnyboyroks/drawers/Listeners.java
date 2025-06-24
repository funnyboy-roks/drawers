package com.funnyboyroks.drawers;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Display.Brightness;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
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
        new AxisAngle4f(-0.5f * (float)Math.PI, 1f, 0f, 0f), // UP
        new AxisAngle4f(0.5f * (float)Math.PI, 1f, 0f, 0f), // DOWN
    };

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.BARREL) return;

        CustomModelData modelData = event.getItemInHand().getData(DataComponentTypes.CUSTOM_MODEL_DATA);
        if (modelData == null || !modelData.strings().contains(Util.ns("barrel").toString())) return;

        Directional dir = (Directional) block.getBlockData();
        // TODO: Perhaps set open when the drawer has an item

        Vector facingVec = dir.getFacing().getDirection();
        ItemDisplay item = block.getWorld().spawn(block.getLocation().add(0.5, 10f/16f, 0.5), ItemDisplay.class, entity -> {
            // entity.setItemStack(event.getPlayer().getInventory().getItemInOffHand());
            // TODO: entity.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.NONE);
            entity.setBrightness(new Brightness(15, 15));

            entity.setTransformationMatrix(
                new Matrix4f()
                    .rotate(ANGLES[dir.getFacing().ordinal()])
                    .translate(0f, 0f, -0.5f)
                    .scale(
                        new Vector3f(
                            facingVec.getX() == 0 ? 8f/16f : 0.1f,
                            facingVec.getY() == 0 ? 8f/16f : 0.1f,
                            facingVec.getZ() == 0 ? 8f/16f : 0.1f
                        )
                    )
            );
        });

        TextDisplay text = block.getWorld().spawn(block.getLocation().add(0.5, 2f/16f, 0.5), TextDisplay.class, entity -> {
            // entity.text(Component.text("Empty"));
            entity.setBrightness(new Brightness(15, 15));
            entity.setBackgroundColor(org.bukkit.Color.fromARGB(0));

            entity.setTransformationMatrix(
                new Matrix4f()
                    .rotate(OPPOSING_ANGLES[dir.getFacing().ordinal()])
                    .translate(0f, 0f, 0.5f)
                    .scale(0.75f) // 3px?
            );
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

        Util.tryRemoveEntity(drawer.itemDisplay);
        Util.tryRemoveEntity(drawer.textDisplay);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.BARREL) return;

        Optional<DrawerBlock> optDrawer = DrawerBlock.fromBlock(block);
        if (optDrawer.isEmpty()) return;
        DrawerBlock drawer = optDrawer.get(); // Checked above

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getPlayer().isSneaking()) return;
            // add a single item
            ItemStack is = event.getItem();
            // TODO: drawer max capacity
            if (is != null && drawer.add(is)) {
                event.getPlayer().getInventory().setItem(event.getHand(), null);
                drawer.saveData();
                drawer.updateDisplay();
            }
            event.setCancelled(true);
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (drawer.isEmpty()) return;
            ItemStack stack = drawer.removeItem(event.getPlayer().isSneaking());
            if (stack != null) {
                event.getPlayer().give(stack);
            }
            drawer.saveData();
            drawer.updateDisplay();
            event.setCancelled(true);
        }
    }
}
