package custommob;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

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

public class GeneralZombie extends EntityZombie {
	private static String mobkey = "generalzombie";

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

	public static String getKey() {
		return mobkey;
	}

	public static void onHit(EntityDamageByEntityEvent event) {

	}

	public static void onGetHit(EntityDamageByEntityEvent event) {
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GeneralZombie(org.bukkit.World world) {
		super(((CraftWorld)world).getHandle());

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

		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(32.0D);
	}
}
