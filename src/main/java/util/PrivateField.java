package util;

import java.lang.reflect.Field;

public class PrivateField {
	@SuppressWarnings("rawtypes")
	public static Object getPrivateField(String fieldname, Class clas, Object obj) {
		Field field;
		Object o = null;
		try {
			field = clas.getDeclaredField(fieldname);
			field.setAccessible(true);
			o = field.get(obj);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}
}
