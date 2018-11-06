package listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import util.VerifyUtil;

public class ProjectileHit implements Listener {

	private static String onhitmethodName = "onHit";

	@EventHandler
	public void onProjectileHit (ProjectileHitEvent event) {
		Projectile proj = event.getEntity();
		EntityType type = proj.getType();
		switch (type) {
		case ENDER_PEARL:
			Class<Object> clazz = VerifyUtil.getEntityClass(proj);
			if (clazz == null) return;
			try {
				Method method = clazz.getMethod(onhitmethodName, Projectile.class);
				method.invoke(clazz, proj);
			} catch (NoSuchMethodException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		return;
	}
}
