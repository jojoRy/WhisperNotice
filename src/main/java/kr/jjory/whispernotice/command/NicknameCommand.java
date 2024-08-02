package kr.jjory.whispernotice.command;

import kr.jjory.whispernotice.WhisperNotice;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {

    private final WhisperNotice plugin;

    public NicknameCommand(WhisperNotice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§6[ 공지 ] §r이 명령어를 실행할 권한이 없습니다.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage("§6[ 공지 ] §r사용법: /닉네임변경 <플레이어> <바꿀닉네임>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§6[ 공지 ] §r플레이어를 찾을 수 없습니다: " + args[0]);
            return false;
        }

        String newNickname = args[1];
        plugin.getNicknameManager().setNickname(target, newNickname);
        sender.sendMessage("§6[ 공지 ] §r플레이어 " + target.getName() + "의 닉네임이 " + newNickname + "으로 변경되었습니다.");

        return true;
    }
}
