package org.minegen.plugin.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.minegen.plugin.utils.Constants;

public final class MessagesHandler {
    private FileConfiguration fileConfiguration;

    // Core message variables - DO NOT MODIFY
    private String prefix;
    private String noPermission;
    private String reloadStart;
    private String reloadSuccess;

    // AI_CAN_DECLARE_MESSAGE_VARIABLES - Add custom message variable fields below this line
    private String joke-delivered;
    private String joke-not-configured;
    private String feature-disabled;
    private String joke-delivery-failed;
    private String joke-command-usage;
    // AI_END - Do not add message variables after this line

    public MessagesHandler(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
        loadMessages();
    }

    /**
     * Reloads all message strings from a new FileConfiguration object.
     * This is called from the main plugin's reload sequence.
     * @param newFileConfiguration The newly reloaded messages.yml config object.
     */
    public void reload(FileConfiguration newFileConfiguration) {
        this.fileConfiguration = newFileConfiguration;
        loadMessages();
    }

    public void loadMessages(){
        // Core messages loading - DO NOT MODIFY
        this.prefix = fileConfiguration.getString("prefix", "&#FFD700" + Constants.PLUGIN_NAME + " &e» ");
        this.noPermission = fileConfiguration.getString("no-permission", "&cYou do not have permission to perform this action.");
        this.reloadStart = fileConfiguration.getString("reload-start", "&eReloading plugin... Please wait.");
        this.reloadSuccess = fileConfiguration.getString("reload-success", "&aPlugin successfully reloaded! All configuration files are up to date.");

        // AI_CAN_LOAD_MESSAGE_VARIABLES - Add custom message variable loading below this line
        this.joke-delivered = fileConfiguration.getString(
                "joke-delivered",
                "&aHere is your joke: {0}"
        );
        this.joke-not-configured = fileConfiguration.getString(
                "joke-not-configured",
                "&cNo jokes are configured right now."
        );
        this.feature-disabled = fileConfiguration.getString(
                "feature-disabled",
                "&cThe joke feature is currently disabled."
        );
        this.joke-delivery-failed = fileConfiguration.getString(
                "joke-delivery-failed",
                "&cSorry, I couldn't deliver a joke right now. Please try again later."
        );
        this.joke-command-usage = fileConfiguration.getString(
                "joke-command-usage",
                "&eUsage: /" + Constants.PLUGIN_ALIAS + " joke"
        );
        // AI_END - Do not add message loading after this line
    }

    // Core message getters - DO NOT MODIFY
    public String getPrefixMessage() { return this.prefix; }
    public String getNoPermissionMessage() { return this.noPermission; }
    public String getReloadStartMessage() { return this.reloadStart; }
    public String getReloadSuccessMessage() { return this.reloadSuccess; }

    // AI_CAN_ADD_MESSAGE_GETTERS - Add custom message getter methods below this line
    public String getJokeDeliveredMessage() { return this.joke-delivered; }
    public String getJokeNotConfiguredMessage() { return this.joke-not-configured; }
    public String getFeatureDisabledMessage() { return this.feature-disabled; }
    public String getJokeDeliveryFailedMessage() { return this.joke-delivery-failed; }
    public String getJokeCommandUsageMessage() { return this.joke-command-usage; }
    // AI_END - Do not add message getters after this line
}
