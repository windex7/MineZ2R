package custommob;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;

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
import util.RandomUtil;
import util.SpawnMobUtil;
import util.VerifyUtil;

public class Giant extends EntityZombie{
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
			add(DamageCause.PROJECTILE);
			add(DamageCause.BLOCK_EXPLOSION);
			add(DamageCause.ENTITY_EXPLOSION);
		}
	};

	protected static Map<String, Double> giantservantmap = new LinkedHashMap<String, Double>() {
		{
			put(GeneralZombie.getKey(), 20d);
			put(ShinyToe.getKey(), 100d);
			put(IronZombie.getKey(), 120d);
			put(Revenant.getKey(), 160d);
			put(Forsaken.getKey(), 140d);
			put(LegChopper.getKey(), 30d);
			put(Pigman.getKey(), 50d);
			put(BabyPigman.getKey(), 10d);
		}
	};

	protected static double psum= 0;
	static {
		for (double i : giantservantmap.values()) {
			psum += i;
		}
	}

	public static String getKey() {
		return mobkey;
	}

	public static void onHit(EntityDamageByEntityEvent event) {
		double kb = 20;
		double knockup = 2;

		Entity giant = event.getDamager();
		Entity victim = event.getEntity();
		if (victim.isDead()) return;
		if (!(victim instanceof Player)) return;
		Player player = (Player) victim;
		Vector vec = player.getLocation().toVector().subtract(giant.getLocation().toVector());
		vec.setY(0);
		vec.normalize();
		vec.multiply(kb);
		vec.setY(knockup);
		player.setVelocity(vec);
	}

	public static void onGetHit(EntityDamageByEntityEvent event) {
		Entity giant = event.getEntity();
		double spawnlocheight = 5;
		int minservantnum = 1;
		int maxservantnum = 1;
		double servnum = new RandomUtil(minservantnum, maxservantnum).getRand();

		for (int serv = 0; serv < servnum; serv++) {
			RandomUtil rand = new RandomUtil(-5, 5);
			Location spawnloc = giant.getLocation().add(rand.getRand(), spawnlocheight, rand.getRand());
			RandomUtil randp = new RandomUtil(0, psum);
			double randresult = randp.getRand();
			String mobresult = null;
			Iterator<String> ite = giantservantmap.keySet().iterator();
			while (ite.hasNext()) {
				String mob = ite.next();
				double value = giantservantmap.get(mob);
				randresult -= value;
				if (randresult < 0) {
					mobresult = mob;
					break;
				}
			}
			SpawnMobUtil.spawnCustomInvulMob(mobresult, spawnloc, 40);
		}
	}

	public static void onDamage(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}
	}

	public static void onDeath(EntityDeathEvent event) {

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
