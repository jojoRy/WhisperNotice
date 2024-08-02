package kr.jjory.whispernotice.command;

import kr.jjory.whispernotice.NicknameManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandCompleter implements TabCompleter {

    private final NicknameManager nicknameManager;

    public CommandCompleter(NicknameManager nicknameManager) {
        this.nicknameManager = nicknameManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("귓속말") && args.length == 1) {
            List<String> completions = new ArrayList<>();
            String partialName = args[0].toLowerCase();
            for (Player player : Bukkit.getOnlinePlayers()) {
                String nickname = nicknameManager.getNickname(player).toLowerCase();
                String playerName = player.getName().toLowerCase();
                if (nickname.startsWith(partialName) || playerName.startsWith(partialName)) {
                    completions.add(nicknameManager.getNickname(player));
                }
            }
            return completions;
        }
        return null;
    }
}
