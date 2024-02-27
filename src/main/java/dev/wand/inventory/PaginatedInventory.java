package dev.wand.inventory;

import dev.wand.util.InventoryUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class PaginatedInventory extends AbstractInventory {

    private final int pageSize;
    @Setter
    private PaginationItem previousPageItem;
    @Setter
    private PaginationItem nextPageItem;

    public PaginatedInventory(String title, int rows, int pageSize) {
        super(title, rows);
        this.pageSize = pageSize;
        previousPageItem = new PaginationItem(InventoryUtils.createItemStack(Material.ARROW, ChatColor.GRAY + "Previous page"));
        nextPageItem = new PaginationItem(InventoryUtils.createItemStack(Material.ARROW, ChatColor.GRAY + "Next page"));
    }

    public void displayPage(int page) {
        // create a new inventory
        // fill the inventory with the items of the current page
        // add the pagination buttons
        // open the inventory for the player
        Inventory inventory = Bukkit.createInventory(null, rows * 9, title + " - Page " + (page + 1));
        for (int i = page * pageSize; i < (page + 1) * pageSize; i++) {
            if (i >= content.size()) {
                break;
            }
            inventory.setItem(i - page * pageSize, content.get(i));
        }
        if (page > 0) {
            inventory.setItem(rows * 9 - 9, previousPageItem.build());
        }
        if (page < (int) Math.ceil((double) content.size() / pageSize) - 1) {
            inventory.setItem(rows * 9 - 1, nextPageItem.build());
        }
        getOwner().openInventory(inventory);
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player != getOwner()) {
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        if (item.equals(previousPageItem.build())) {
            // open the previous page
            // we have to calculate the current page
            int page = 0;
            for (int i = 0; i < content.size(); i++) {
                if (content.get(i).equals(item)) {
                    page = i / pageSize;
                    break;
                }
            }
            if (page > 0) {
                page--;
            }

            displayPage(page);
        } else if (item.equals(nextPageItem.build())) {
            // open the next page
            // we have to calculate the current page
            int page = 0;
            for (int i = 0; i < content.size(); i++) {
                if (content.get(i).equals(item)) {
                    page = i / pageSize;
                    break;
                }
            }
            if (page < (int) Math.ceil((double) content.size() / pageSize) - 1) {
                page++;
            }
            displayPage(page);
        }
    }

    @Override
    public void display() {
        // we have to calculate the number of pages based on the content
        int pages = (int) Math.ceil((double) content.size() / pageSize);
        // we have to create a new inventory for each page
        for (int page = 0; page < pages; page++) {
            // create a new inventory
            // fill the inventory with the items of the current page
            // add the pagination buttons
            // open the inventory for the player

            Inventory inventory = Bukkit.createInventory(null, rows * 9, title + " - Page " + (page + 1));
            for (int i = page * pageSize; i < (page + 1) * pageSize; i++) {
                if (i >= content.size()) {
                    break;
                }
                inventory.setItem(i - page * pageSize, content.get(i));
            }
            if (page > 0) {
                inventory.setItem(rows * 9 - 9, InventoryUtils.createItemStack(Material.ARROW, ChatColor.GRAY + "Previous page"));
            }
            if (page < pages - 1) {
                inventory.setItem(rows * 9 - 1, InventoryUtils.createItemStack(Material.ARROW, ChatColor.GRAY + "Next page"));
            }

            getOwner().openInventory(inventory);
        }

    }
}
