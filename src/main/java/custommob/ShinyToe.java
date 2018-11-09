package custommob;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.minecraft.server.v1_9_R2.DifficultyDamageScaler;
import net.minecraft.server.v1_9_R2.EnumItemSlot;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import net.minecraft.server.v1_9_R2.GroupDataEntity;
import util.NMSUtil;
import util.RandomUtil;
import util.StuckUtil;
import util.VerifyUtil;
public class ShinyToe extends GeneralZombie {
	private static String mobkey = "shinytoe";
	private static double tppos = 0.6;

	public static String getKey() {
		return mobkey;
	}

	public static void onHit(EntityDamageByEntityEvent event) {
		Bukkit.broadcastMessage("shiny toe attacked!!");
	}

	public static void onGetHit(EntityDamageByEntityEvent event) {
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}

		if (RandomUtil.onPossibility(tppos)) {
			Entity shinytoe = event.getEntity();
			Location stloc = shinytoe.getLocation();
			Location newloc = stloc.clone().add(RandomUtil.getRand(-3, 3), RandomUtil.getRand(-3, 3), RandomUtil.getRand(-3, 3));
			while (StuckUtil.isStuck(newloc)) {
				newloc = stloc.clone().add(RandomUtil.getRand(-3, 3), RandomUtil.getRand(-3, 3), RandomUtil.getRand(-3, 3));
			}
			Bukkit.broadcastMessage("teleported shinytoe to " + newloc.getBlockX() + newloc.getBlockY() + newloc.getBlockZ());
			shinytoe.teleport(newloc);
		}
	}

	public ShinyToe(World world) {
		super(world);
		ItemStack mainhandis = new ItemStack(Material.WOOD_SWORD);
		mainhandis.addEnchantment(Enchantment.DAMAGE_ALL, 1);

		ItemStack headis = new ItemStack(Material.LEATHER_HELMET);
		headis.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		LeatherArmorMeta headmeta = (LeatherArmorMeta) headis.getItemMeta();
		headmeta.setColor(Color.YELLOW);
		headis.setItemMeta(headmeta);

		ItemStack chestis = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestis.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		LeatherArmorMeta chestmeta = (LeatherArmorMeta) headis.getItemMeta();
		chestmeta.setColor(Color.BLACK);
		chestis.setItemMeta(chestmeta);

		ItemStack legis = new ItemStack(Material.LEATHER_LEGGINGS);
		legis.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		LeatherArmorMeta legmeta = (LeatherArmorMeta) headis.getItemMeta();
		legmeta.setColor(Color.BLACK);
		legis.setItemMeta(legmeta);

		ItemStack bootsis = new ItemStack(Material.LEATHER_BOOTS);
		bootsis.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		LeatherArmorMeta bootsmeta = (LeatherArmorMeta) headis.getItemMeta();
		bootsmeta.setColor(Color.PURPLE);
		bootsis.setItemMeta(bootsmeta);

		this.setSlot(EnumItemSlot.MAINHAND, NMSUtil.convIStoNMS(mainhandis));
	    this.setSlot(EnumItemSlot.HEAD, NMSUtil.convIStoNMS(headis));
	    this.setSlot(EnumItemSlot.CHEST, NMSUtil.convIStoNMS(chestis));
	    this.setSlot(EnumItemSlot.LEGS, NMSUtil.convIStoNMS(legis));
	    this.setSlot(EnumItemSlot.FEET, NMSUtil.convIStoNMS(bootsis));
	    this.getAttributeInstance(GenericAttributes.g).setValue(5.0D);
	    this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4D);

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
