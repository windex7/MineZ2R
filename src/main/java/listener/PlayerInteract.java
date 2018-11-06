package listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import util.VerifyUtil;

public class PlayerInteract implements Listener {
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		ItemStack is = event.getItem();
		if (block != null) {
			onClickItem(event, player, action, is, true);

			switch (block.getType()) {
			case SKULL:
				break;
			default:
				break;
			}
		} else {
			onClickItem(event, player, action, is, false);
		}
	}

	public static void onClickItem(PlayerInteractEvent event, Player player, Action action, ItemStack is, boolean clickedblock) {
		String onclickmethodName = "onClick";
		Class<Object> clazz = VerifyUtil.getItemClass(is);
		if (clazz == null) return;
		try {
			Method method = clazz.getMethod(onclickmethodName, PlayerInteractEvent.class);
			method.invoke(clazz, event);
		} catch (NoSuchMethodException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		/*
		String disp = is.getItemMeta().getDisplayName();
		switch (is.getType()) {
		case ENDER_PEARL:
			String impact = "Impact Grenade";
			if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				if (disp.equals(impact)) {
					event.setCancelled(true);
					ImpactGrenade.onThrow(player, is);
				}
			}
			break;
		default:
			break;
		}
		*/
	}
}
