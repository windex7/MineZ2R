package util;

import java.lang.reflect.Field;

import net.minecraft.server.v1_9_R2.Item;

public class StacksizeUtil {
	public static void setStackSize(String itemname, int size) {
		try {
			Item item = Item.d(itemname);
			Field field;
			field = Item.class.getDeclaredField("maxStackSize");
			field.setAccessible(true);
			field.setInt(item, size);
			field.setAccessible(false);
		} catch (NoSuchFieldException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
