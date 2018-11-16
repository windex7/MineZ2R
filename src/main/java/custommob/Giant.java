package custommob;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntityZombie;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import net.minecraft.server.v1_9_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R2.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_9_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_9_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_9_R2.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_9_R2.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_9_R2.PathfinderGoalSelector;
import util.PrivateField;
import util.VerifyUtil;

public class Giant extends EntityZombie implements CustomMob{
	private static String mobkey = "giant";

	private static double health = 2000;

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
		//Bukkit.broadcastMessage("iron zombie attacked!!");
	}

	public void onGetHit(EntityDamageByEntityEvent event) {
		/*DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}*/
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Giant(World world) {
		super(((CraftWorld)world).getHandle());

		setSize(this.width * 6.0F, this.length * 6.0F);

		Set goalB = (Set)PrivateField.getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector); goalB.clear();
		Set goalC = (Set)PrivateField.getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector); goalC.clear();
		Set targetB = (Set)PrivateField.getPrivateField("b", PathfinderGoalSelector.class, this.targetSelector); targetB.clear();
		Set targetC = (Set)PrivateField.getPrivateField("c", PathfinderGoalSelector.class, this.targetSelector); targetC.clear();

		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0.3D));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));

	    this.getAttributeInstance(GenericAttributes.g).setValue(8.0D);

	    this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);
	    this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(128.0D);
	    this.getAttributeInstance(GenericAttributes.c).setValue(1); // kb

	    Zombie giant = (Zombie) this.getBukkitEntity();
	    giant.setMaxHealth(health);
	    giant.setHealth(health);
	    giant.setSilent(true);

	    VerifyUtil.setMobClass((Entity) this.getBukkitEntity(), mobkey);
	}
}
