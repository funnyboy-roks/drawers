package com.funnyboyroks.drawers;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class DrawerBlock {
    public Block block;
    public ItemDisplay itemDisplay;
    public TextDisplay textDisplay;
    public DrawerState state;

    public DrawerBlock(Block block, ItemDisplay itemDisplay, TextDisplay textDisplay, DrawerState state) {
        this.block = block;
        this.itemDisplay = itemDisplay;
        this.textDisplay = textDisplay;
        this.state = state;
    }

    public org.bukkit.block.data.type.Barrel getBlockData() {
        return (org.bukkit.block.data.type.Barrel) this.block.getBlockData();
    }

    public boolean hasType() {
        return this.state.item() != null;
    }

    public boolean isEmpty() {
        return !this.hasType() || this.state.count() == 0;
    }

    public boolean isInfinite() {
        return this.state.count() == -1;
    }

    // Capacity in _stacks_
    public int capacity() {
        return 32;
    }

    public int maxCount() {
        return this.state.item().getMaxStackSize() * this.capacity();
    }

    public @NotNull Optional<ItemStack> add(ItemStack stack) {
        if (this.isInfinite()) return Optional.empty();
        if (this.hasType()) {
            if (!stack.isSimilar(this.state.item()) || this.state.count() > this.maxCount()) return Optional.of(stack);
            int newCount = this.state.count() + stack.getAmount();
            int maxCap = this.maxCount();
            ItemStack overflow = null;
            if (newCount > maxCap) {
                int overflowQty = newCount % maxCap;
                overflow = stack.asQuantity(overflowQty);
            }
            this.state = this.state.withCount(Math.max(this.state.count(), Math.min(newCount, maxCap)));
            return Optional.ofNullable(overflow);
        } else {
            this.state = this.state
                .withItem(stack.clone())
                .withCount(stack.getAmount());
            return Optional.empty();
        }
    }

    public @Nullable ItemStack removeItem(boolean fullStack) {
        if (!this.hasType()) return null;
        int desired = fullStack ? this.state.item().getMaxStackSize() : 1;
        int actual;
        DrawerState nextState;
        if (this.isInfinite()) {
            nextState = this.state;
            actual = desired;
        } else if (this.state.count() > desired) {
            nextState = this.state.withCount(this.state.count() - desired);
            actual = desired;
        } else {
            nextState = new DrawerState();
            actual = this.state.count();
        }
        ItemStack out = this.state.item().clone();
        out.setAmount(actual);
        this.state = nextState;
        return out;
    }

    private Component displayText() {
        return switch (this.state.count()) {
            case -1 -> Component.text("∞");
            case 0 -> Component.text("Empty").color(NamedTextColor.GRAY);
            // TODO: More human-readable content (i.e., 4.2k)
            default -> Component.text(this.state.count());
        };
    }

    public void updateDisplay() {
        this.textDisplay.text(this.displayText());
        this.itemDisplay.setItemStack(this.state.item());
    }

    /**
     * Save PDC into block assocated with this Drawer
     */
    public void saveData() {
        if (this.block.getType() != Material.BARREL) {
            Drawers.instance().getLogger().warning(
                    "Tried to save data of block that is not a barrel at " + Util.format(block.getLocation()) + ".");
            return;
        }
        Barrel barrel = (Barrel) this.block.getState();
        PersistentDataContainer pdc = barrel.getPersistentDataContainer();
        pdc.set(Util.ns("is_drawer"), PersistentDataType.BOOLEAN, true);
        pdc.set(Util.ns("item_display"), PersistentDataType.LONG_ARRAY,
                Util.uuidToLongArr(this.itemDisplay.getUniqueId()));
        pdc.set(Util.ns("text_display"), PersistentDataType.LONG_ARRAY,
                Util.uuidToLongArr(this.textDisplay.getUniqueId()));

        this.state.toPdc(pdc);
        barrel.update();
    }

    public static @NotNull Optional<DrawerBlock> fromBlock(@NotNull Block block) {
        if (block.getType() != Material.BARREL) {
            Drawers.instance().getLogger().warning(
                    "Tried to load data of block that is not a barrel at " + Util.format(block.getLocation()) + ".");
            return Optional.empty();
        }
        Barrel barrel = (Barrel) block.getState();
        PersistentDataContainer pdc = barrel.getPersistentDataContainer();

        boolean isDrawer = pdc.getOrDefault(Util.ns("is_drawer"), PersistentDataType.BOOLEAN, false);
        if (!isDrawer)
            return Optional.empty();
        // TODO: Custom pdc data type for UUIDs
        UUID item = Util.longArrToUUID(pdc.get(Util.ns("item_display"), PersistentDataType.LONG_ARRAY));
        UUID text = Util.longArrToUUID(pdc.get(Util.ns("text_display"), PersistentDataType.LONG_ARRAY));

        Entity entity = block.getWorld().getEntity(item);
        ItemDisplay itemDisplay = null;
        if (entity == null) {
            Drawers.instance().getLogger()
                    .warning("Item display is missing for drawer at " + Util.format(block.getLocation()) + ".");
        } else if (entity instanceof ItemDisplay id) {
            itemDisplay = id;
        } else {
            Drawers.instance().getLogger().warning("Item display entity is not an ItemDisplay for drawer at "
                    + Util.format(block.getLocation()) + ".");
            itemDisplay = null;
        }

        entity = block.getWorld().getEntity(text);
        TextDisplay textDisplay = null;
        if (entity == null) {
            Drawers.instance().getLogger()
                    .warning("Text display is missing for drawer at " + Util.format(block.getLocation()) + ".");
        } else if (entity instanceof TextDisplay td) {
            textDisplay = td;
        } else {
            Drawers.instance().getLogger().warning("Text display entity is not an TextDisplay for drawer at "
                    + Util.format(block.getLocation()) + ".");
            textDisplay = null;
        }

        DrawerState state = DrawerState.fromPdc(pdc);

        return Optional.of(new DrawerBlock(block, itemDisplay, textDisplay, state));
    }

    public void dropContents() {
        if (this.isEmpty() || this.isInfinite()) return;
        int stackSize = this.state.item().getMaxStackSize();
        int stacks = this.state.count() / stackSize;

        Location summonAt = this.block.getLocation().clone().add(0.5, 0.5, 0.5);
        ItemStack fullStack = this.state.item().asQuantity(stackSize);
        for (int i = 0; i < stacks; ++i) {
            this.block.getWorld().dropItemNaturally(summonAt, fullStack);
        }

        if (this.state.count() % stackSize != 0) {
            this.block.getWorld().dropItemNaturally(summonAt, fullStack.asQuantity(this.state.count() % stackSize));
        }
    }

    public static record DrawerState(@Nullable ItemStack item, int count) {
        public DrawerState() {
            this(null, 0);
        }

        public DrawerState withCount(int count) {
            return new DrawerState(this.item(), count);
        }

        public DrawerState withItem(@Nullable ItemStack stack) {
            return new DrawerState(stack, this.count());
        }

        public void toPdc(@NotNull PersistentDataContainer pdc) {
            // TODO: Better itemstack ser/de
            if (this.item() == null) {
                pdc.set(Util.ns("item_stack"), PersistentDataType.BYTE_ARRAY, new byte[0]);
            } else {
                pdc.set(Util.ns("item_stack"), PersistentDataType.BYTE_ARRAY, this.item().serializeAsBytes());
            }
            pdc.set(Util.ns("count"), PersistentDataType.INTEGER, this.count());
        }

        public static @NotNull DrawerState fromPdc(@NotNull PersistentDataContainer pdc) {
            ItemStack item;
            NamespacedKey isKey = Util.ns("item_stack");
            byte[] isSer = pdc.getOrDefault(isKey, PersistentDataType.BYTE_ARRAY, new byte[0]);
            if (isSer.length == 0) {
                item = null;
            } else {
                // TODO: Better itemstack ser/de
                item = ItemStack.deserializeBytes(isSer);
            }
            return new DrawerState(
                item,
                pdc.getOrDefault(Util.ns("count"), PersistentDataType.INTEGER, 0)
            );
        }
    }

}
