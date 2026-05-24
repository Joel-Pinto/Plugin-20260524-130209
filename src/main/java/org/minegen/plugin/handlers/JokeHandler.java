package org.minegen.plugin.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

/**
 * Handles loading and serving jokes.
 * <p>
 * Jokes are loaded from {@code jokes} section inside {@code config.yml}.
 * Example:
 * <pre>
 * jokes:
 *   - "&fWhy did the chicken cross the road? ..."
 *   - "&eAnother joke"
 * </pre>
 * <p>
 * MiniMessage is used for color/formatting if MiniMessage is configured in your server.
 */
public class JokeHandler {
    private final JavaPlugin plugin;
    private final ConfigHandler configHandler;

    private final List<String> jokes = new ArrayList<>();

    // Path in config.yml
    private static final String CONFIG_JOKES_PATH = "jokes";

    public JokeHandler(JavaPlugin plugin, ConfigHandler configHandler) {
        this.plugin = plugin;
        this.configHandler = configHandler;
        reload();
    }

    public void reload() {
        jokes.clear();

        FileConfiguration cfg = plugin.getConfig();
        List<String> loaded = cfg.getStringList(CONFIG_JOKES_PATH);

        if (loaded != null) {
            for (String s : loaded) {
                if (s == null) continue;
                String trimmed = s.trim();
                if (!trimmed.isEmpty()) jokes.add(trimmed);
            }
        }
    }

    /**
     * Returns an unformatted raw joke string.
     */
    public String getRandomRawJoke() {
        if (jokes.isEmpty()) return null;
        return jokes.get(ThreadLocalRandom.current().nextInt(jokes.size()));
    }

    /**
     * Returns a formatted joke as Component.
     */
    public Component getRandomJokeComponent(Player player) {
        String raw = getRandomRawJoke();
        if (raw == null) return Component.empty();

        // Support MiniMessage formatting; fallback to plain text if parsing fails.
        try {
            // MiniMessage by default supports legacy color codes if configured server-side;
            // this project uses MiniMessage for player-facing messages.
            return MiniMessage.miniMessage().deserialize(raw);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.WARNING, "Failed to deserialize joke using MiniMessage: {0}", raw);
            return Component.text(raw);
        }
    }

    /**
     * For safety/diagnostics.
     */
    public List<String> getJokesSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(jokes));
    }
}
