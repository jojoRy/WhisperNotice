package kr.jjory.whispernotice.listeners;

import kr.jjory.whispernotice.NicknameManager;
import kr.jjory.whispernotice.WhisperNotice;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private final WhisperNotice plugin;

    public PlayerChatListener(WhisperNotice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        NicknameManager nicknameManager = plugin.getNicknameManager();
        String prefix = nicknameManager.getPrefix(event.getPlayer());
        String nickname = nicknameManager.getNickname(event.getPlayer());
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        event.setFormat((prefix != null ? prefix + " " : "") + nickname + "§r: " + event.getMessage());
    }
}
