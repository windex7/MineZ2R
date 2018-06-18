package listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;

public class PlayerTeleport implements Listener {
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Plugin plugin = MineZ2R.getInstance();
		if (event.getCause().equals(TeleportCause.ENDER_PEARL)) {
			event.setCancelled(true);
		}
	}
}
