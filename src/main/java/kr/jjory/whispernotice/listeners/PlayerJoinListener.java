package kr.jjory.whispernotice.listeners;

import kr.jjory.whispernotice.NicknameManager;
import kr.jjory.whispernotice.WhisperNotice;
import kr.jjory.whispernotice.enums.Format;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final WhisperNotice plugin;
    private static final String PREFIX = "§c📢§l공지§r§c📢§r ";
    private String welcomeMessage;
    private final NicknameManager nicknameManager;

    public PlayerJoinListener(WhisperNotice plugin, NicknameManager nicknameManager) {
        this.plugin = plugin;
        this.nicknameManager = nicknameManager;
        updateWelcomeMessage();
    }

    public void updateWelcomeMessage() {
        welcomeMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("접속공지"));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        nicknameManager.updatePlayerName(event.getPlayer());
        if (!welcomeMessage.isEmpty()) {
            event.getPlayer().sendMessage(PREFIX + "\n" + welcomeMessage);
        }
        String joinMessage = Format.JOIN.getMessage(nicknameManager.getNickname(event.getPlayer()));
        event.setJoinMessage(joinMessage);
    }
}
