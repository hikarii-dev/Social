package dev.hikarii.socialPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

class MsgCommand implements CommandExecutor {
    private final SocialManager socialManager;

    public MsgCommand(SocialManager socialManager) {
        this.socialManager = socialManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только игроки могут использовать эту команду!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Использование: /msg <игрок> <сообщение>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(ChatColor.RED + "Вы не можете отправить сообщение самому себе!");
            return true;
        }

        // Build message from args
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
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