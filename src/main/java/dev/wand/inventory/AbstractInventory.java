package dev.wand.inventory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractInventory {

    protected final String title;
    protected final int rows;
    protected final List<ItemStack> content;
    @Setter
    protected Player owner;

    public AbstractInventory(String title, int rows) {
        this.title = title;
        this.rows = rows;
        this.content = new ArrayList<>(rows * 9);
    }

    public void addItem(ItemStack item) {
        content.add(item);
    }

    public void setContent(List<ItemStack> items) {
        this.content.clear();
        this.content.addAll(items);
    }

    public void setItem(int slot, ItemStack item) {
        content.set(slot, item);
    }

    public ItemStack getItem(int slot) {
        return content.get(slot);
    }

    public ItemStack removeItem(int slot) {
        return content.remove(slot);
    }

    // Abstract method for additional functionality
    public abstract void display();

    public abstract void handleClick(InventoryClickEvent event);
}
