package kr.jjory.jbroadcast.command;

import kr.jjory.jbroadcast.JBroadcast;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class JoinBroadcastConvertCommand implements CommandExecutor {

    private final JBroadcast plugin;

    public JoinBroadcastConvertCommand(JBroadcast plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage("§6[ 공지 ] §r이 명령어를 실행할 권한이 없습니다.");
            return true;
        }

        if (args.length == 0) {
            plugin.getConfig().set("메세지", "");
            plugin.saveConfig();
            plugin.reloadPluginConfig();
            sender.sendMessage("§6[ 공지 ] §r접속공지가 초기화되었습니다");
            return true;
        }

        String newMessage = String.join(" ", args);
        plugin.getConfig().set("메세지", newMessage);
        plugin.saveConfig();
        plugin.reloadPluginConfig();
        sender.sendMessage("§6[ 공지 ] §r접속공지가 변경되었습니다 : " + newMessage);
        return true;
    }
}
