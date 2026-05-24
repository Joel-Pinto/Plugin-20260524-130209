package org.minegen.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.minegen.plugin.PluginMain;
import org.minegen.plugin.handlers.ConfigHandler;
import org.minegen.plugin.handlers.MessagesHandler;
import org.minegen.plugin.helpers.ConfigHandler;
import org.minegen.plugin.helpers.MessagesHelper;
import org.minegen.plugin.utils.Constants;

import org.bukkit.command.CommandExecutor;

import org.minegen.plugin.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class JokeCommand implements SubCommand {

    private final PluginMain plugin;
    private final MessagesHelper messagesHelper;
    private final ConfigHandler configHandler;
    private final MessagesHandler messagesHandler;

    public JokeCommand(PluginMain plugin, MessagesHelper messagesHelper, ConfigHandler configHandler, MessagesHandler messagesHandler) {
        this.plugin = plugin;
        this.messagesHelper = messagesHelper;
        this.configHandler = configHandler;
        this.messagesHandler = messagesHandler;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(Constants.Permissions.CMD_JOKE)) {
            messagesHelper.sendCommandSenderMessage(sender, messagesHandler.getNoPermissionMessage());
            return true;
        }

        // Try to resolve jokes from messages.yml first (preferred)
        List<String> jokes = messagesHandler.getJokes();
        if (jokes == null || jokes.isEmpty()) {
            // Fallback to config.yml
            jokes = configHandler.getJokes();
        }

        if (jokes == null || jokes.isEmpty()) {
            messagesHelper.sendCommandSenderMessage(sender, "&cNo jokes are loaded.");
            return true;
        }

        String joke = jokes.get(ThreadLocalRandom.current().nextInt(jokes.size()));
        if (joke == null || joke.isBlank()) {
            messagesHelper.sendCommandSenderMessage(sender, "&cThe loaded joke was empty. Try again.");
            return true;
        }

        messagesHelper.sendCommandSenderMessage(sender, joke);
        return true;
    }

    @Override
    public List<String> getSubcommandCompletions(CommandSender sender, String[] args) {
        // /joke [optionalIndex]
        if (args.length == 1) {
            return List.of();
        }
        return List.of();
    }
}
