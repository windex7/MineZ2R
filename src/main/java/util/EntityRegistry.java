package util;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityTypes;

@SuppressWarnings("rawtypes")
public enum EntityRegistry {
	MZ_ZOMBIE("Zombie", 54, custommob.GeneralZombie.class),
	MZ_SHINYTOE("Zombie", 54, custommob.ShinyToe.class),
	MZ_IRONZOMBIE("Zombie", 54, custommob.IronZombie.class),
	MZ_REVENANT("Zombie", 54, custommob.Revenant.class),
	MZ_REVENANTRE("Zombie", 54, custommob.RevenantReborn.class),
	MZ_REVENANTSERVANT("Skeleton", 51, custommob.RevenantServant.class), // <- if set to 52, it spawns a creature it looks like spider but actually wither skeleton, which drops bones and coals lol
	MZ_FORSAKEN("Zombie", 54, custommob.Forsaken.class),
	MZ_PIGMAN("PigZombie", 57, custommob.Pigman.class),
	MZ_BABYPIGMAN("PigZombie", 57, custommob.BabyPigman.class),
	MZ_LEGCHOPPER("Zombie", 54, custommob.LegChopper.class),
	MZ_HORSE("EntityHorse", 100, custommob.MineZHorse.class),
	MZ_GIANT("Zombie", 53, custommob.Giant.class);

	private EntityRegistry(String name, int id, Class<? extends Entity> custom) {
		addToMaps(custom, name, id);
	}

	public static void spawnEntity(Entity entity, Location loc) {
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
	}

	@SuppressWarnings("unchecked")
	private static void addToMaps(Class clazz, String name, int id) {
		((Map)util.PrivateField.getPrivateField("c", EntityTypes.class, null)).put(name, clazz);
		((Map)util.PrivateField.getPrivateField("d", EntityTypes.class, null)).put(clazz, name);
		((Map)util.PrivateField.getPrivateField("f", EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
	}
}