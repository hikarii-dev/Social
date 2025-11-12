package dev.hikarii.socialPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class SocialManager {
    private final SocialPlugin plugin;
    private final Map<UUID, UUID> lastMessaged; // Кому последний раз писал игрок
    private final Map<UUID, Set<UUID>> ignoredPlayers; // Список игнорируемых игроков
    private final Set<UUID> afkPlayers; // Игроки в AFK режиме

    public SocialManager(SocialPlugin plugin) {
        this.plugin = plugin;
        this.lastMessaged = new HashMap<>();
        this.ignoredPlayers = new HashMap<>();
        this.afkPlayers = new HashSet<>();

        loadAllIgnoreLists();
    }

    // === MESSAGE SYSTEM ===

    public void sendMessage(Player sender, Player receiver, String message) {
        // Check if receiver ignores sender
        if (isIgnoring(receiver.getUniqueId(), sender.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "Вы не можете отправить сообщение этому игроку!");
            return;
        }

        // Format messages
        String senderMessage = ChatColor.GRAY + "[Вы -> " + receiver.getName() + "] " + ChatColor.WHITE + message;
        String receiverMessage = ChatColor.GRAY + "[" + sender.getName() + " -> Вам] " + ChatColor.WHITE + message;

        // Send messages
        sender.sendMessage(senderMessage);
        receiver.sendMessage(receiverMessage);

        // Save last messaged player
        lastMessaged.put(sender.getUniqueId(), receiver.getUniqueId());
        lastMessaged.put(receiver.getUniqueId(), sender.getUniqueId());
    }

    public UUID getLastMessaged(UUID playerUUID) {
        return lastMessaged.get(playerUUID);
    }

    // === IGNORE SYSTEM ===

    public void ignorePlayer(UUID player, UUID target) {
        ignoredPlayers.computeIfAbsent(player, k -> new HashSet<>()).add(target);
        saveIgnoreList(player);
    }

    public void unignorePlayer(UUID player, UUID target) {
        Set<UUID> ignored = ignoredPlayers.get(player);
        if (ignored != null) {
            ignored.remove(target);
            saveIgnoreList(player);
        }
    }

    public boolean isIgnoring(UUID player, UUID target) {
        Set<UUID> ignored = ignoredPlayers.get(player);
        return ignored != null && ignored.contains(target);
    }

    public Set<UUID> getIgnoredPlayers(UUID player) {
        return ignoredPlayers.getOrDefault(player, new HashSet<>());
    }

    private void saveIgnoreList(UUID player) {
        Set<UUID> ignored = ignoredPlayers.get(player);
        List<String> ignoredList = new ArrayList<>();

        if (ignored != null) {
            for (UUID uuid : ignored) {
                ignoredList.add(uuid.toString());
            }
        }

        plugin.getConfig().set("ignore." + player.toString(), ignoredList);
        plugin.saveConfig();
    }

    private void loadAllIgnoreLists() {
        if (!plugin.getConfig().contains("ignore")) {
            return;
        }

        for (String playerUUID : plugin.getConfig().getConfigurationSection("ignore").getKeys(false)) {
            List<String> ignoredList = plugin.getConfig().getStringList("ignore." + playerUUID);
            Set<UUID> ignored = new HashSet<>();

            for (String ignoredUUID : ignoredList) {
                try {
                    ignored.add(UUID.fromString(ignoredUUID));
                } catch (IllegalArgumentException e) {
                    // Invalid UUID, skip
                }
            }

            try {
                ignoredPlayers.put(UUID.fromString(playerUUID), ignored);
            } catch (IllegalArgumentException e) {
                // Invalid UUID, skip
            }
        }
    }

    public void saveAllIgnoreLists() {
        for (UUID player : ignoredPlayers.keySet()) {
            saveIgnoreList(player);
        }
    }

    // === AFK SYSTEM ===

    public void setAfk(UUID player, boolean afk) {
        if (afk) {
            afkPlayers.add(player);
        } else {
            afkPlayers.remove(player);
        }
    }

    public boolean isAfk(UUID player) {
        return afkPlayers.contains(player);
    }

    public void toggleAfk(Player player) {
        UUID uuid = player.getUniqueId();
        boolean wasAfk = isAfk(uuid);

        setAfk(uuid, !wasAfk);

        if (!wasAfk) {
            // Player went AFK
            player.sendMessage(ChatColor.GRAY + "Вы теперь в AFK режиме.");
            Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + " теперь AFK");
        } else {
            // Player returned from AFK
            player.sendMessage(ChatColor.GRAY + "Вы больше не в AFK режиме.");
            Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + " вернулся из AFK");
        }
    }

    public void removeAfk(Player player) {
        UUID uuid = player.getUniqueId();
        if (isAfk(uuid)) {
            setAfk(uuid, false);
            player.sendMessage(ChatColor.GRAY + "Вы больше не в AFK режиме.");
            Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + " вернулся из AFK");
        }
    }
}