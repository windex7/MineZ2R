package custommob;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_9_R2.DifficultyDamageScaler;
import net.minecraft.server.v1_9_R2.EnumItemSlot;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import net.minecraft.server.v1_9_R2.GroupDataEntity;
import util.NMSUtil;
import util.VerifyUtil;

public class RevenantReborn extends GeneralZombie implements CustomMob {
	private static String mobkey = "revenantreborn";

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

	public RevenantReborn(World world) {
		super(world);
		ItemStack mainhandis = new ItemStack(Material.STONE_SWORD);
		mainhandis.addEnchantment(Enchantment.DAMAGE_ALL, 1);

		ItemStack headis = new ItemStack(Material.CHAINMAIL_HELMET);

		ItemStack chestis = new ItemStack(Material.CHAINMAIL_CHESTPLATE);

		ItemStack legis = new ItemStack(Material.CHAINMAIL_LEGGINGS);

		ItemStack bootsis = new ItemStack(Material.CHAINMAIL_BOOTS);

		this.setSlot(EnumItemSlot.MAINHAND, NMSUtil.convIStoNMS(mainhandis));
	    this.setSlot(EnumItemSlot.HEAD, NMSUtil.convIStoNMS(headis));
	    this.setSlot(EnumItemSlot.CHEST, NMSUtil.convIStoNMS(chestis));
	    this.setSlot(EnumItemSlot.LEGS, NMSUtil.convIStoNMS(legis));
	    this.setSlot(EnumItemSlot.FEET, NMSUtil.convIStoNMS(bootsis));
	    this.getAttributeInstance(GenericAttributes.g).setValue(5.0D);
	    this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);

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