package kr.jjory.jbroadcast;

import kr.jjory.jbroadcast.command.BroadcastCommand;
import kr.jjory.jbroadcast.command.JoinBroadcastConvertCommand;
import kr.jjory.jbroadcast.event.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class JBroadcast extends JavaPlugin {
    private PlayerJoinListener playerJoinListener;

    @Override
    public void onEnable() {
        // Load configuration
        saveDefaultConfig();

        // Register the /공지 command
        this.getCommand("공지").setExecutor(new BroadcastCommand());

        // Register the /리로드 command
        this.getCommand("공지변환").setExecutor(new JoinBroadcastConvertCommand(this));

        // Register event listeners
        playerJoinListener = new PlayerJoinListener(this);
        getServer().getPluginManager().registerEvents(playerJoinListener, this);

        getLogger().info("JBroadcast 플러그인이 활성화되었습니다.");
    }

    @Override
    public void onDisable() {
        getLogger().info("JBroadcast 플러그인이 비활성화되었습니다.");
    }

    public void reloadPluginConfig() {
        reloadConfig();
        playerJoinListener.updateWelcomeMessage();
    }
}
