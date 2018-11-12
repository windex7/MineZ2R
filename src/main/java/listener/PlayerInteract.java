package listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import util.VerifyUtil;

public class PlayerInteract implements Listener {
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		ItemStack is = event.getItem();
		if (block != null) {
			onClickItem(event, is);

			Material blocktype = block.getType();
			// ReflectionUtil.getBlockClass(blocktype);

			/*
			switch (block.getType()) {
			case SKULL:
				break;
			case ENCHANTMENT_TABLE:
				break;
			case ENDER_CHEST:
				break;
			default:
				break;
			}
			*/
		} else {
			onClickItem(event, is);
		}
	}

	public static void onClickItem(PlayerInteractEvent event, ItemStack is) {
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
