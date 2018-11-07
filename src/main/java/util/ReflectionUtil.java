package util;

import java.util.HashMap;
import java.util.Map;

import customitem.ImpactGrenade;
import custommob.ShinyToe;

public class ReflectionUtil {
	private static final Map<String, Class<?>> PRIMITIVE_TYPE_MAP = new HashMap<String, Class<?>>();

	private static final Map<String, Class<?>> ITEM_CLASS_MAP = new HashMap<String, Class<?>>();
	private static final Map<String, Class<?>> MOB_CLASS_MAP = new HashMap<String, Class<?>>();

	static {
		registerPrimitiveType("byte", byte.class);
		registerPrimitiveType("short", short.class);
		registerPrimitiveType("int", int.class);
		registerPrimitiveType("long", long.class);
		registerPrimitiveType("boolean", boolean.class);
		registerPrimitiveType("float", float.class);
		registerPrimitiveType("double", double.class);
		registerPrimitiveType("char", char.class);

		registerItemClass(ImpactGrenade.getKey(), ImpactGrenade.class);

		registerMobClass(ShinyToe.getKey(), ShinyToe.class);
	}

	private ReflectionUtil() {

	}

	private static void registerPrimitiveType(String typeName, Class<?> clazz) {
		PRIMITIVE_TYPE_MAP.put(typeName, clazz);
	}

	private static void registerItemClass(String itemName, Class<?> clazz) {
		ITEM_CLASS_MAP.put(itemName, clazz);
	}

	private static void registerMobClass(String itemName, Class<?> clazz) {
		MOB_CLASS_MAP.put(itemName, clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getType(String className) throws ClassNotFoundException {
		if (PRIMITIVE_TYPE_MAP.containsKey(className)) {
			return (Class<T>) PRIMITIVE_TYPE_MAP.get(className);
		}

		return (Class<T>) Class.forName(className);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getItemClass(String tagName) throws ClassNotFoundException {
		if (ITEM_CLASS_MAP.containsKey(tagName)) {
			return (Class<T>) ITEM_CLASS_MAP.get(tagName);
		}

		// return (Class<T>) GeneralItem.class;
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getMobClass(String tagName) throws ClassNotFoundException {
		if (MOB_CLASS_MAP.containsKey(tagName)) {
			return (Class<T>) MOB_CLASS_MAP.get(tagName);
		}

		// return (Class<T>) GeneralItem.class;
		return null;
	}
}