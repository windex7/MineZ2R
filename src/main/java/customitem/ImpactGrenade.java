package customitem;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import util.InventoryUtil;
import util.MetadataUtil;
import util.TimingUtil;
import util.VerifyUtil;

public class ImpactGrenade {

	private static float power = 2.5F;

	public static void onClick(PlayerInteractEvent event) {
		String igkey = "impactgrenade";
		int grect = 60; //tick
		Player player = event.getPlayer();
		ItemStack is = event.getItem();
		event.setCancelled(true);
		if (MetadataUtil.getMetadata(player, igkey) != null) {
			if (TimingUtil.getTimeDiff(Long.parseLong(MetadataUtil.getMetadata(player, igkey).toString())) <= grect) {
				event.setCancelled(true);
				return;
			}
		}
		event.setCancelled(true);
		EnderPearl grenade = player.launchProjectile(EnderPearl.class);
		grenade.setShooter(null);
		VerifyUtil.setEntityClass(grenade, igkey);
		InventoryUtil.removeIS(player, is, 1);
		MetadataUtil.setMetadata(player, igkey, TimingUtil.getTime());
	}

	public static void onHit(Projectile proj) {
		proj.getWorld().createExplosion(proj.getLocation(), power);
	}
}
