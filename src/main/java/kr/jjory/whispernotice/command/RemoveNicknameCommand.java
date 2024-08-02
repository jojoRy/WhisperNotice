package kr.jjory.whispernotice.command;

import kr.jjory.whispernotice.WhisperNotice;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveNicknameCommand implements CommandExecutor {

    private final WhisperNotice plugin;

    public RemoveNicknameCommand(WhisperNotice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§6[ 닉네임 ] §r이 명령어를 실행할 권한이 없습니다.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§6[ 닉네임 ] §r사용법: /닉네임제거 <플레이어>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§6[ 닉네임 ] §r플레이어를 찾을 수 없습니다: " + args[0]);
            return false;
        }

        // NicknameManager를 통해 닉네임 제거
        plugin.getNicknameManager().removeNickname(target);

        // 설정 파일에서 닉네임 제거
        plugin.getConfig().set("nicknames." + target.getUniqueId().toString(), null);
        plugin.saveConfig();

        sender.sendMessage("§6[ 닉네임 ] §r플레이어 " + target.getName() + "의 닉네임이 원래 이름으로 복원되었습니다.");

        return true;
    }
}
