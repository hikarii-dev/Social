package dev.hikarii.socialPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

class ReplyCommand implements CommandExecutor {
    private final SocialManager socialManager;

    public ReplyCommand(SocialManager socialManager) {
        this.socialManager = socialManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только игроки могут использовать эту команду!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Использование: /reply <сообщение>");
            return true;
        }

        UUID lastMessagedUUID = socialManager.getLastMessaged(player.getUniqueId());

        if (lastMessagedUUID == null) {
            player.sendMessage(ChatColor.RED + "Нет игрока для ответа!");
            return true;
        }

        Player target = Bukkit.getPlayer(lastMessagedUUID);

        if (target == null || !target.isOnline()) {
            player.sendMessage(ChatColor.RED + "Игрок не в сети!");
            return true;
        }

        // Build message from args
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            messageBuilder.append(args[i]);
            if (i < args.length - 1) {
                messageBuilder.append(" ");
            }
        }

        String message = messageBuilder.toString();
        socialManager.sendMessage(player, target, message);
        return true;
    }
}