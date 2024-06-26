package kr.jjory.whispernotice.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class WhisperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length < 2) {
                player.sendMessage("§6[귓속말] §r/귓, /귓속, /귓속말, /ㅈ, /w 플레이어 <메세지>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§6[귓속말]§r " + args[0] + " 를 찾을 수 없습니다.");
                return false;
            }

            // 플레이어가 자신에게 귓속말을 보내는 것을 방지
            if (player.equals(target)) {
                player.sendMessage("§6[귓속말]§r 자신에게 귓속말을 보낼 수 없습니다.");
                return false;
            }

            // 메시지 조합
            String message = String.join(" ", args).substring(args[0].length() + 1);

            target.sendMessage(ChatColor.RED + "[ " + player.getName() + "에게 받은 귓속말 ] " + ChatColor.RESET + message);
            player.sendMessage(ChatColor.GREEN + "[ " + target.getName() + "에게 보낸 귓속말 ] " + ChatColor.RESET + message);

            // 마지막 귓속말 대상 저장
            player.setMetadata("lastMessageTarget", new FixedMetadataValue(player.getServer().getPluginManager().getPlugin("WhisperNotice"), target));
            target.setMetadata("lastMessageTarget", new FixedMetadataValue(player.getServer().getPluginManager().getPlugin("WhisperNotice"), player));

            return true;
        }
        return false;
    }
}
