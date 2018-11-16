package custommob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_9_R2.EntityHorse;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import util.VerifyUtil;

public class MineZHorse extends EntityHorse implements CustomMob {
	private static String mobkey = "poweredsuit";

	protected static List<DamageCause> ignore_damagecause = new ArrayList<DamageCause>() {
		{
			add(DamageCause.CONTACT);
			add(DamageCause.DROWNING);
			add(DamageCause.FALL);
			add(DamageCause.FIRE);
			add(DamageCause.FIRE_TICK);
			add(DamageCause.LAVA);
		}
	};

	public String getKey() {
		return mobkey;
	}

	public void onHit(EntityDamageByEntityEvent event) {

	}

	public void onGetHit(EntityDamageByEntityEvent event) {
		/*
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}
		*/
	}

	public void onDamage(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}
	}

	public void onDeath(EntityDeathEvent event) {

	}

	public MineZHorse(org.bukkit.World world) {
		super(((CraftWorld)world).getHandle());

		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);

		Horse suit = (Horse) this.getBukkitEntity();
		suit.setVariant(Variant.HORSE);
		suit.setTamed(true);
		suit.getInventory().setSaddle(new ItemStack(Material.SADDLE));

		VerifyUtil.setMobClass((Entity) this.getBukkitEntity(), getKey());
	}
}
