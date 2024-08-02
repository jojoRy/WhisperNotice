package kr.jjory.whispernotice;

import kr.jjory.whispernotice.enums.Sync;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NicknameManager {
    private final WhisperNotice plugin;
    private final Map<UUID, String> nicknames = new HashMap<>();
    private final Map<UUID, String> originalNames = new HashMap<>();
    private LuckPerms luckPerms;

    public NicknameManager(WhisperNotice plugin) {
        this.plugin = plugin;
        loadNicknames();
        setupLuckPerms();
    }

    private void setupLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public void setNickname(Player player, String nickname) {
        UUID uuid = player.getUniqueId();
        originalNames.putIfAbsent(uuid, player.getName());
        nicknames.put(uuid, ChatColor.translateAlternateColorCodes('&', nickname));
        updatePlayerName(player);
        saveNicknames();
    }

    public void removeNickname(Player player) {
        UUID uuid = player.getUniqueId();
        nicknames.remove(uuid);
        if (originalNames.containsKey(uuid)) {
            player.setDisplayName(originalNames.get(uuid));
            originalNames.remove(uuid);
        }
        updatePlayerName(player);
        saveNicknames();
    }

    public String getNickname(Player player) {
        return nicknames.getOrDefault(player.getUniqueId(), player.getName());
    }

    public String getOriginalNameByNickname(String nickname) {
        for (Map.Entry<UUID, String> entry : nicknames.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(nickname)) {
                UUID uuid = entry.getKey();
                return Bukkit.getOfflinePlayer(uuid).getName();
            }
        }
        return null;
    }

    public String getNicknameByName(String name) {
        for (Map.Entry<UUID, String> entry : nicknames.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void updatePlayerName(Player player) {
        String nickname = getNickname(player);
        String prefix = getPrefix(player);
        // Ensure prefix is not null
        if (prefix == null) {
            prefix = "";
        }
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        String displayName = (prefix != null ? prefix + " " : "") + nickname;
        Sync.syncAll(player, displayName);
    }

    public String getPrefix(Player player) {
        if (luckPerms != null) {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                return user.getCachedData().getMetaData().getPrefix();
            }
        }
        return "";
    }

    public void setPrefix(Player player, String prefix) {
        if (luckPerms != null) {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            if (user != null) {
                PrefixNode prefixNode = PrefixNode.builder(prefix, 0).build();
                user.data().add(prefixNode);
                luckPerms.getUserManager().saveUser(user);
                updatePlayerName(player);
            }
        }
    }

    private void loadNicknames() {
        FileConfiguration config = plugin.getConfig();
        if (config.getConfigurationSection("nicknames") == null) {
            plugin.getLogger().warning("No nicknames section found in config.yml");
            return;
        }
        for (String key : config.getConfigurationSection("nicknames").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            nicknames.put(uuid, config.getString("nicknames." + key));
        }
    }

    private void saveNicknames() {
        FileConfiguration config = plugin.getConfig();
        for (Map.Entry<UUID, String> entry : nicknames.entrySet()) {
            config.set("nicknames." + entry.getKey().toString(), entry.getValue());
        }
        plugin.saveConfig();
    }
}
