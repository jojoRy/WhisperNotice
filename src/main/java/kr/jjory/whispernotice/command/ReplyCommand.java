package kr.jjory.whispernotice.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasMetadata("lastMessageTarget")) {
                player.sendMessage("§6[귓속말] §r답장할 플레이어가 없습니다.");
                return false;
            }

            MetadataValue metadataValue = player.getMetadata("lastMessageTarget").get(0);
            if (metadataValue.value() == null || !(metadataValue.value() instanceof Player)) {
                player.sendMessage("§6[귓속말] §r답장할 플레이어가 없습니다.");
                return false;
            }

            Player target = (Player) metadataValue.value();

            if (args.length < 1) {
                player.sendMessage("§6[귓속말] §r/답장, /답, /ㄱ, /w <메세지>");
                return false;
            }

            String message = String.join(" ", args);

            target.sendMessage(ChatColor.RED + "[ " + player.getName() + "에게 받은 귓속말 ] " + ChatColor.RESET + message);
            player.sendMessage(ChatColor.GREEN + "[ " + target.getName() + "에게 보낸 귓속말 ] " + ChatColor.RESET + message);

            // 마지막 귓속말 대상 업데이트
            player.setMetadata("lastMessageTarget", new FixedMetadataValue(player.getServer().getPluginManager().getPlugin("WhisperNotice"), target));

            return true;
        }
        return false;
    }
}
