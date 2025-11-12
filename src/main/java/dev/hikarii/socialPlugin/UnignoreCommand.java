package dev.hikarii.socialPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class UnignoreCommand implements CommandExecutor {
    private final SocialManager socialManager;

    public UnignoreCommand(SocialManager socialManager) {
        this.socialManager = socialManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только игроки могут использовать эту команду!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Использование: /unignore <игрок>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        if (!socialManager.isIgnoring(player.getUniqueId(), target.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "Вы не игнорируете этого игрока!");
            return true;
        }

        socialManager.unignorePlayer(player.getUniqueId(), target.getUniqueId());
        player.sendMessage(ChatColor.GREEN + "Вы больше не игнорируете игрока " + target.getName());
        return true;
    }
}