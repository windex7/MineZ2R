package listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import util.OffhandUtil;

public class SwapOffhand implements Listener{
	@EventHandler
	public static void disableSwapping(PlayerSwapHandItemsEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;
		if (OffhandUtil.isAllowedOffhand(event.getOffHandItem())) {
			return;
		} else {
			if (event.getOffHandItem().getType().equals(Material.AIR)) {
				return;
			} else {
				event.setCancelled(true);
				return;
			}
		}
	}
}
