package kr.jjory.whispernotice.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;


public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // 플레이어의 마지막 귓속말 대상 제거
        if (player.hasMetadata("lastMessageTarget")) {
            player.removeMetadata("lastMessageTarget", player.getServer().getPluginManager().getPlugin("WhisperNotice"));
        }

        for (Player onlinePlayer : player.getServer().getOnlinePlayers()) {
            if (onlinePlayer.hasMetadata("lastMessageTarget")) {
                MetadataValue metadataValue = onlinePlayer.getMetadata("lastMessageTarget").get(0);
                if (metadataValue.value() != null && metadataValue.value().equals(player)) {
                    onlinePlayer.removeMetadata("lastMessageTarget", player.getServer().getPluginManager().getPlugin("WhisperNotice"));
                }
            }
        }
    }
}
