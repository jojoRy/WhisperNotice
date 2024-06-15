package kr.jjory.whispernotice.listeners;

import kr.jjory.whispernotice.WhisperNotice;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final WhisperNotice plugin;
    private static final String PREFIX = "§c📢§l공지§r§c📢§r ";
    private String welcomeMessage;

    public PlayerJoinListener(WhisperNotice plugin) {
        this.plugin = plugin;
        updateWelcomeMessage();
    }

    public void updateWelcomeMessage() {
        welcomeMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("접속공지"));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!welcomeMessage.isEmpty()) {
            event.getPlayer().sendMessage(PREFIX + "\n" + welcomeMessage);
        }
    }
}
