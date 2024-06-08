package kr.jjory.jbroadcast.event;

import kr.jjory.jbroadcast.JBroadcast;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final JBroadcast plugin;
    private static final String PREFIX = "§c📢§l공지§r§c📢§r ";
    private String welcomeMessage;

    public PlayerJoinListener(JBroadcast plugin) {
        this.plugin = plugin;
        updateWelcomeMessage();
    }

    public void updateWelcomeMessage() {
        welcomeMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("메세지"));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!welcomeMessage.isEmpty()) {
            event.getPlayer().sendMessage(PREFIX + "\n" + welcomeMessage);
        }
    }
}
