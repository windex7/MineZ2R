package util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.server.v1_9_R2.Item;

public class StacksizeUtil {
	private static Map<String, Integer> stacklimit = new HashMap<String, Integer>();
	static {
		stacklimit.put("ender_pearl", 1);
		stacklimit.put("skull", 1);
		stacklimit.put("slime_ball", 1);
		stacklimit.put("web", 1);
	};

	public static void initialStackSize() {
		for (Entry<String, Integer> entry : stacklimit.entrySet()) {
			setStackSize(entry.getKey(), entry.getValue());
		}
	}

	public static void setStackSize(String itemname, int size) {
		try {
			Item item = Item.d(itemname);
			Field field;
			field = Item.class.getDeclaredField("maxStackSize");
			field.setAccessible(true);
			field.setInt(item, size);
			field.setAccessible(false);
		} catch (NoSuchFieldException |
				SecurityException |
				IllegalArgumentException |
				IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
