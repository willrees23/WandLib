package dev.wand.util;

import com.cryptomorin.xseries.XMaterial;
import dev.wand.WandLib;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtils {

    // make utils to create XSeries ItemStacks
    public static ItemStack createItemStack(XMaterial material) {
        return material.parseItem();
    }

    public static ItemStack setDisplayName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }
        meta.setDisplayName(WandLib.translate(name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItemStack(Material material, String name) {
        ItemStack toReturn = XMaterial.matchXMaterial(material).parseItem();
        return setDisplayName(toReturn, name);
    }

    public static ItemStack createItemStack(Material material) {
        return XMaterial.matchXMaterial(material).parseItem();
    }
}
