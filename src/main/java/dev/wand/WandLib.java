package dev.wand;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.wand.inventory.AbstractInventory;
import dev.wand.inventory.InventoryListener;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class WandLib {

    private static JavaPlugin instance;

    /**
     * Sets the instance of the plugin <br>
     * Call this method in the onEnable method of the main class <br>
     * <b>This is required for the library to function.</b>
     * @param instance The instance of the plugin
     */
    public static void setInstance(JavaPlugin instance) {
        WandLib.instance = instance;

        // Register events
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new InventoryListener(), instance);
    }

    /**
     * Creates config files. Does not saveDefaultConfig().
     * @param configs The names of the config files to create
     * @return A map of the config names and their respective YamlDocument
     * @see #createConfigs(boolean, String...)
     */
    public static HashMap<String,YamlDocument> createConfigs(String... configs) {
        return createConfigs(false, configs);
    }

    public static HashMap<String,YamlDocument> createConfigs(boolean defaultConfig, String... configs) {
        if (defaultConfig) instance.saveDefaultConfig();

        HashMap<String, YamlDocument> documents = new HashMap<>();
        for (String config : configs) {
            String path = instance.getDataFolder().getPath();
            YamlDocument document;
            try {
                document = YamlDocument.create(new File(path, config + ".yml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            documents.put(config, document);
        }

        return documents;
    }

    public static void log(String message) {
        instance.getLogger().info(message);
    }

    /**
     * Gets all online players
     * @return A list of all online players
     * @see #getOnlinePlayerNames()
     */
    public static List<Player> getOnlinePlayers() {
        return new ArrayList<>(instance.getServer().getOnlinePlayers());
    }

    /**
     * Gets the names of all online players
     * @return A list of all online player names
     * @see #getOnlinePlayers()
     */
    public static List<String> getOnlinePlayerNames() {
        List<String> names = new ArrayList<>();
        for (Player player : getOnlinePlayers()) {
            names.add(player.getName());
        }
        return names;
    }

    /**
     * Broadcasts a message to all players
     * @param message The message to broadcast
     * @see #broadcast(String message, String permission)
     */
    public static void broadcast(String message) {
        for (Player player : getOnlinePlayers()) {
            player.sendMessage(translate(message));
        }
    }

    /**
     * Broadcasts a message to all players with the given permission
     * @param message The message to broadcast
     * @param permission The permission to check for
     * @see Player#hasPermission(String)
     * @see #broadcast(String)
     */
    public static void broadcast(String message, String permission) {
        for (Player player : getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                player.sendMessage(translate(message));
            }
        }
    }

    /**
     * Translates the string into Bukkit's color codes
     * @param string The string to translate
     * @return The translated string
     * @see ChatColor#translateAlternateColorCodes(char, String)
     * @see #translate(String, Object...) 
     */
    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Translates the string and formats it with the given objects
     * @param string The string to translate
     * @param objects The objects to format the string with
     * @return The translated and formatted string
     * @see String#format(String, Object...)
     * @see #translate(String)
     */
    public static String translate(String string, Object... objects) {
        return String.format(translate(string), objects);
    }
}