package kr.jjory.whispernotice.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {

    private static final String PREFIX = "§c📢§l공지§r§c📢§r ";
    private static final String BOLD_STYLE = ChatColor.BOLD.toString();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender has OP permissions
        if (!sender.isOp()) {
            sender.sendMessage("§6[ 공지 ] §r이 명령어를 실행할 권한이 없습니다.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§6[ 공지 ] §r사용법: /공지 <메시지>");
            return false;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }

        String rawMessage = messageBuilder.toString().trim().replaceAll("&", "§");
        String broadcastMessage = PREFIX + "\n" + BOLD_STYLE + ChatColor.translateAlternateColorCodes('&', rawMessage);;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(broadcastMessage);
        }

        return true;
    }
}
