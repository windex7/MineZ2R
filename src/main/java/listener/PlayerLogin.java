package listener;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {
	@EventHandler
	public static void onPlayerAttemptToLogin(PlayerLoginEvent event) {

	}

	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
	}
}
