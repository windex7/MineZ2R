package util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.server.v1_9_R2.Entity;
import net.minecraft.server.v1_9_R2.EntityTypes;
import net.minecraft.server.v1_9_R2.MinecraftKey;
import net.minecraft.server.v1_9_R2.RegistryMaterials;

@SuppressWarnings("rawtypes")
public class EntityRegistry extends RegistryMaterials {
	private static EntityRegistry instance = null;

	private final BiMap<MinecraftKey, Class<? extends Entity>> customEntities = HashBiMap.create();
	private final BiMap<Class<? extends Entity>, MinecraftKey> customEntityClasses = this.customEntities.inverse();
	private final Map<Class<? extends Entity>, Integer> customEntityIds = new HashMap<>();

	private final RegistryMaterials wrapped;

	private EntityRegistry(RegistryMaterials original) {
		this.wrapped = original;
	}

	private static EntityRegistry getInstance() {
		if (instance != null) {
			return instance;
		}

		instance = new EntityRegistry((RegistryMaterials)PrivateField.getPrivateField("b", EntityTypes.class, null));

		try {
			Field registryMaterialsField = EntityTypes.class.getDeclaredField("modifiers");
			registryMaterialsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(registryMaterialsField, registryMaterialsField.getModifiers() & ~Modifier.FINAL);

			registryMaterialsField.set(null, instance);
		} catch (Exception e) {
			instance = null;
			throw new RuntimeException("Unable to override the old entity RegistryMaterials", e);
		}

		return instance;

	}

	public static void registerCustomEntity(int entityid, String entityname, Class<? extends Entity> entityclass) {
		getInstance().putCustomEntity(entityid, entityname, entityclass);
	}

	public void putCustomEntity(int entityid, String entityname, Class<? extends Entity> entityclass) {
		MinecraftKey key = new MinecraftKey(entityname);

		this.customEntities.put(key, entityclass);
		this.customEntityIds.put(entityclass, entityid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Entity> get(Object key) {
		if (this.customEntities.containsKey(key)) {
			return this.customEntities.get(key);
		}
		return (Class<? extends Entity>) wrapped.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int a(Object key) {
		if (this.customEntityClasses.containsKey(key)) {
			return this.customEntityIds.get(key);
		}
		return this.wrapped.a(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MinecraftKey b(Object value) {
		if (this.customEntityClasses.containsKey(value)) {
			return this.customEntityClasses.get(value);
		}
		return (MinecraftKey) wrapped.b(value);
	}
}