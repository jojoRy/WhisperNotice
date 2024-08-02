package kr.jjory.whispernotice.command;

import kr.jjory.whispernotice.NicknameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class WhisperCommand implements CommandExecutor {

    private final NicknameManager nicknameManager;

    public WhisperCommand(NicknameManager nicknameManager) {
        this.nicknameManager = nicknameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length < 2) {
                player.sendMessage("§6[귓속말] §r/귓, /귓속, /귓속말, /ㅈ, /w 플레이어 <메세지>");
                return false;
            }

            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);

            // 바뀐 닉네임으로 플레이어를 찾지 못하면 기존 닉네임으로 찾기
            if (target == null) {
                String originalName = nicknameManager.getOriginalNameByNickname(targetName);
                if (originalName != null) {
                    target = Bukkit.getPlayer(originalName);
                }
            }

            if (target == null) {
                player.sendMessage("§6[귓속말]§r " + targetName + " 를 찾을 수 없습니다.");
                return false;
            }

            // 플레이어가 자신에게 귓속말을 보내는 것을 방지
            if (player.equals(target)) {
                player.sendMessage("§6[귓속말]§r 자신에게 귓속말을 보낼 수 없습니다.");
                return false;
            }

            // 메시지 조합
            String message = String.join(" ", args).substring(args[0].length() + 1);

            String playerName = nicknameManager.getNickname(player);
            String targetNameDisplay = nicknameManager.getNickname(target);

            target.sendMessage(ChatColor.RED + "[ " + playerName + "에게 받은 귓속말 ] " + ChatColor.RESET + message);
            player.sendMessage(ChatColor.GREEN + "[ " + targetNameDisplay + "에게 보낸 귓속말 ] " + ChatColor.RESET + message);

            // 마지막 귓속말 대상 저장
            player.setMetadata("lastMessageTarget", new FixedMetadataValue(player.getServer().getPluginManager().getPlugin("WhisperNotice"), target));
            target.setMetadata("lastMessageTarget", new FixedMetadataValue(player.getServer().getPluginManager().getPlugin("WhisperNotice"), player));

            return true;
        }
        return false;
    }
}
