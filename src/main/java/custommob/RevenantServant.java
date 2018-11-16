package custommob;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_9_R2.DifficultyDamageScaler;
import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntitySkeleton;
import net.minecraft.server.v1_9_R2.EnumItemSlot;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import net.minecraft.server.v1_9_R2.GroupDataEntity;
import net.minecraft.server.v1_9_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R2.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_9_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_9_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_9_R2.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_9_R2.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_9_R2.PathfinderGoalSelector;
import util.NMSUtil;
import util.PrivateField;
import util.VerifyUtil;

public class RevenantServant extends EntitySkeleton implements CustomMob {
	private static String mobkey = "revenantservant";

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
	public RevenantServant(World world) {
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

		ItemStack mainhandis = new ItemStack(Material.STONE_SWORD);
		this.setSlot(EnumItemSlot.MAINHAND, NMSUtil.convIStoNMS(mainhandis));

	    this.getAttributeInstance(GenericAttributes.g).setValue(5.0D);
	    this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.37D);

	    ((Skeleton) this.getBukkitEntity()).setSkeletonType(SkeletonType.WITHER);

	    VerifyUtil.setMobClass((Entity) this.getBukkitEntity(), mobkey);
	}

	@Override
	public void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.g).setValue(5.0D);
	}

	@Override
	public GroupDataEntity prepare(DifficultyDamageScaler dds, GroupDataEntity gde) {
	    // Calling the super method FIRST, so in case it changes the equipment, our equipment overrides it.
	    gde = super.prepare(dds, gde);
	    // We'll set the main hand to a bow and head to a pumpkin now!
	    //this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.WOODEN_HOE));
	    //this.setSlot(EnumItemSlot.HEAD, new ItemStack(Blocks.PUMPKIN));
	    // Last, returning the GroupDataEntity called gde.
	    return gde;
	}
}
