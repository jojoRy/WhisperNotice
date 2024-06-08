package kr.jjory.whispernotice.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhisperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 2) {
                player.sendMessage("§6[귓속말] §r/귓, /귓속, /귓속말 플레이어 <메세지>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§6[귓속말]§r " + args[0] + " 를 찾을 수 없습니다.");
                return false;
            }

            StringBuilder message = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                message.append(args[i]).append(" ");
            }

            target.sendMessage(ChatColor.RED + "[ " + player.getName() + "에게 받은 귓속말 ] " + ChatColor.RESET + message.toString().trim());
            player.sendMessage(ChatColor.GREEN + "[ " + target.getName() + "에게 보낸 귓속말 ] " + ChatColor.RESET + message.toString().trim());
            return true;
        }
        return false;
    }
}
