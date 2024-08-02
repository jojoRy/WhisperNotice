package kr.jjory.whispernotice.command;

import kr.jjory.whispernotice.NicknameManager;
import kr.jjory.whispernotice.WhisperNotice;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPrefixCommand implements CommandExecutor {

    private final WhisperNotice plugin;

    public SetPrefixCommand(WhisperNotice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.isOp()) {
            player.sendMessage("이 명령어를 사용할 권한이 없습니다.");
            return false;
        }

        if (args.length < 2) {
            sender.sendMessage("Usage: /칭호 <플레이어> <설정할칭호>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("플레이어를 찾지 못했습니다.");
            return true;
        }

        // Use StringBuilder to concatenate the arguments into a single prefix
        StringBuilder prefixBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i > 1) {
                prefixBuilder.append(" ");
            }
            prefixBuilder.append(args[i]);
        }
        String newPrefix = prefixBuilder.toString();
        newPrefix = ChatColor.translateAlternateColorCodes('&', newPrefix);

        NicknameManager nicknameManager = plugin.getNicknameManager();
        nicknameManager.setPrefix(target, newPrefix);

        sender.sendMessage(target.getName() + "에게 " + newPrefix + " 칭호를 설정했습니다.");

        return true;
    }
}
