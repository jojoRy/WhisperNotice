package kr.jjory.whispernotice;

import kr.jjory.whispernotice.command.*;
import kr.jjory.whispernotice.listeners.PlayerJoinListener;
import kr.jjory.whispernotice.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public class WhisperNotice extends JavaPlugin {

    private PlayerJoinListener playerJoinListener;

    @Override
    public void onEnable() {
        // Load configuration
        saveDefaultConfig();

        // Register the /공지 command
        this.getCommand("공지").setExecutor(new BroadcastCommand());

        // Register the /리로드 command
        this.getCommand("공지변환").setExecutor(new JoinBroadcastConvertCommand(this));

        this.getCommand("귓속말").setExecutor(new WhisperCommand());
        this.getCommand("귓속말").setTabCompleter(new CommandCompleter());
        this.getCommand("답장").setExecutor(new ReplyCommand());

        // Register event listeners
        playerJoinListener = new PlayerJoinListener(this);
        getServer().getPluginManager().registerEvents(playerJoinListener, this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);

        getLogger().info("WhisperNotice 플러그인이 활성화되었습니다.");
    }

    @Override
    public void onDisable() {
        getLogger().info("WhisperNotice 플러그인이 비활성화되었습니다.");
    }

    public void reloadPluginConfig() {
        reloadConfig();
        playerJoinListener.updateWelcomeMessage();
    }
}
