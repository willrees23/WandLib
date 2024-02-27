package dev.wand.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PaginationItem {
    private final ItemStack item;
    private Consumer<InventoryClickEvent> onClick;

    public PaginationItem(ItemStack item) {
        this.item = item;
    }

    public PaginationItem onClick(Consumer<InventoryClickEvent> onClick) {
        this.onClick = onClick;
        return this;
    }

    public ItemStack build() {
        return item;
    }

    public void handleClick(InventoryClickEvent event) {
        if (onClick != null) {
            onClick.accept(event);
        }
    }
}
