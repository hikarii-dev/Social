package dev.hikarii.socialPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerListener implements Listener {
    private final SocialManager socialManager;

    public PlayerListener(SocialManager socialManager) {
        this.socialManager = socialManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Remove AFK status when player moves
        if (event.getFrom().getX() != event.getTo().getX() ||
                event.getFrom().getY() != event.getTo().getY() ||
                event.getFrom().getZ() != event.getTo().getZ()) {

            socialManager.removeAfk(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Remove AFK status when player chats
        socialManager.removeAfk(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Remove AFK status when player quits
        socialManager.setAfk(event.getPlayer().getUniqueId(), false);
    }
}