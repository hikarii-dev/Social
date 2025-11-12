package dev.hikarii.socialPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class SocialPlugin extends JavaPlugin {
    private SocialManager socialManager;

    @Override
    public void onEnable() {
        // Save default config
        saveDefaultConfig();

        // Initialize manager
        this.socialManager = new SocialManager(this);

        // Register commands
        getCommand("msg").setExecutor(new MsgCommand(socialManager));
        getCommand("reply").setExecutor(new ReplyCommand(socialManager));
        getCommand("ignore").setExecutor(new IgnoreCommand(socialManager));
        getCommand("unignore").setExecutor(new UnignoreCommand(socialManager));
        getCommand("afk").setExecutor(new AfkCommand(socialManager));

        // Register events
        getServer().getPluginManager().registerEvents(new PlayerListener(socialManager), this);

        getLogger().info("SocialPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Save all ignore lists
        if (socialManager != null) {
            socialManager.saveAllIgnoreLists();
        }

        getLogger().info("SocialPlugin has been disabled!");
    }

    public SocialManager getSocialManager() {
        return socialManager;
    }
}