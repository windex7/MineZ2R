package customitem;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import util.InventoryUtil;
import util.MetadataUtil;
import util.NBTUtil;
import util.TimingUtil;
import util.VerifyUtil;

public class ImpactGrenade {

	private static String igkey = "impactgrenade";
	private static float power = 2.5F;
	private static String powerkey = "power";
	private static String speedkey = "speed";
	private static String ctkey = "ct";

	public static String getKey() {
		return igkey;
	}

	public static void onClick(PlayerInteractEvent event) {
		Action action = event.getAction();
		if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || action == Action.PHYSICAL) return;
		int grect = 60; //tick
		Player player = event.getPlayer();
		ItemStack is = event.getItem();
		if (NBTUtil.readItemIntTag(is, ctkey) != null) grect = NBTUtil.readItemIntTag(is, ctkey);
		event.setCancelled(true);
		if (MetadataUtil.getMetadata(player, igkey) != null) {
			if (TimingUtil.getTimeDiff(Long.parseLong(MetadataUtil.getMetadata(player, igkey).toString())) <= grect) {
				return;
			}
		}
		EnderPearl grenade = player.launchProjectile(EnderPearl.class);
		grenade.setShooter(null);
		VerifyUtil.setEntityClass(grenade, igkey);
		if (NBTUtil.readItemIntTag(is, powerkey) != null) MetadataUtil.setMetadata(grenade, powerkey, NBTUtil.readItemIntTag(is, powerkey));
		if (NBTUtil.readItemIntTag(is, speedkey) != null) grenade.setVelocity(grenade.getVelocity().multiply(NBTUtil.readItemIntTag(is, speedkey)));
		InventoryUtil.removeIS(player, is, 1);
		MetadataUtil.setMetadata(player, igkey, TimingUtil.getTime());
	}

	public static void onHit(Projectile proj) {
		float grepower = power;
		if (MetadataUtil.getMetadata(proj, powerkey) != null) grepower = ((Integer) MetadataUtil.getMetadata(proj, powerkey)).floatValue();
		proj.getWorld().createExplosion(proj.getLocation(), grepower);
	}
}