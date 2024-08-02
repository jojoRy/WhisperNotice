package kr.jjory.whispernotice;

import kr.jjory.whispernotice.command.*;
import kr.jjory.whispernotice.listeners.PlayerChatListener;
import kr.jjory.whispernotice.listeners.PlayerJoinListener;
import kr.jjory.whispernotice.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public class WhisperNotice extends JavaPlugin {

    private PlayerJoinListener playerJoinListener;
    private NicknameManager nicknameManager;

    @Override
    public void onEnable() {
        // Load configuration
        saveDefaultConfig();

        // Initialize NicknameManager
        nicknameManager = new NicknameManager(this);

        // Register commands
        this.getCommand("공지").setExecutor(new BroadcastCommand());
        this.getCommand("공지변환").setExecutor(new JoinBroadcastConvertCommand(this));
        this.getCommand("귓속말").setExecutor(new WhisperCommand(nicknameManager));
        this.getCommand("귓속말").setTabCompleter(new CommandCompleter(nicknameManager));
        this.getCommand("답장").setExecutor(new ReplyCommand());
        this.getCommand("닉네임변경").setExecutor(new NicknameCommand(this));
        this.getCommand("닉네임제거").setExecutor(new RemoveNicknameCommand(this));
        this.getCommand("칭호").setExecutor(new SetPrefixCommand(this));

        // Register event listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, nicknameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this, nicknameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);

        getLogger().info("WhisperNotice 플러그인이 활성화되었습니다.");
    }

    @Override
    public void onDisable() {
        getLogger().info("WhisperNotice 플러그인이 비활성화되었습니다.");
    }

    public NicknameManager getNicknameManager() {
        return nicknameManager;
    }

    public void reloadPluginConfig() {
        reloadConfig();
        playerJoinListener.updateWelcomeMessage();
    }
}
