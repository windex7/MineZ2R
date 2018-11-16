package custommob;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public interface CustomMob {
	String getKey();
	void onHit(EntityDamageByEntityEvent event);
	void onGetHit(EntityDamageByEntityEvent event);
	void onDamage(EntityDamageEvent event);
	void onDeath(EntityDeathEvent event);
}
