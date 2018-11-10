package util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;

public class ConfigUtil {
	private static Plugin mz2r = MineZ2R.getInstance();
	private static FileConfiguration config = mz2r.getConfig();
	private static File devfile = new File(mz2r.getDataFolder(), "devfile.yml");
	private static FileConfiguration devconfig = YamlConfiguration.loadConfiguration(devfile);

	public static void initConfig() {
		if (!(mz2r.getDataFolder().exists())) {
			mz2r.getDataFolder().mkdirs();
		}
		mz2r.saveConfig();
		if (!devfile.exists())
			try {
				devfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static String getFilename(String keyword, Object data) {
		switch (keyword) {
		case "player":
			return ((Player)data).getUniqueId().toString();
		case "config":
			return "config";
		case "dev":
		case "devfile":
			return "devfile";
		default:
			return keyword;
		}
	}

	public static void setGameConfig(String key, Object data) {
		config.set(key, data);
		mz2r.saveConfig();
	}

	public static Object getGameConfigData(String key) {
		if (config.get(key) != null) {
			return config.get(key);
		} else {
			return null;
		}
	}

	public static void setDevConfig(String key, Object data) {
		devconfig.set(key, data);
		try {
			devconfig.save(devfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object getDevConfigData(String key) {
		return getConfig("devfile", key);
	}

	public static File getConfigFile(String configname) {
		File configfile = new File(mz2r.getDataFolder(), configname + ".yml");
		if (!(configfile.exists()))
			try {
				configfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return configfile;
	}

	public static void setConfig(String configname, String key, Object data) {
		File targetfile = getConfigFile(configname);
		FileConfiguration targetconfig = YamlConfiguration.loadConfiguration(targetfile);
		targetconfig.set(key, data);
		try {
			targetconfig.save(targetfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	public static Object getConfig(String configname, String key) {
		File targetfile = getConfigFile(configname);
		FileConfiguration targetconfig = YamlConfiguration.loadConfiguration(targetfile);
		if (targetconfig.get(key) != null) {
			return targetconfig.get(key);
		} else {
			return null;
		}
	}

	public static File getPlayerFile(Player player) {
		return getConfigFile(player.getUniqueId().toString());
	}

	public static void setPlayerConfig(Player player, String key, Object data) {
		setConfig(player.getUniqueId().toString(), key, data);
	}

	public static Object getPlayerConfig(Player player, String key) {
		return getConfig(player.getUniqueId().toString(), key);
	}
}
