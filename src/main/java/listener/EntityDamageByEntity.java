package listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import util.VerifyUtil;

public class EntityDamageByEntity implements Listener{
	private static String onhitmethodName = "onHit";
	private static String ongethitmethodName = "onGetHit";

	@EventHandler
	public static void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity victim = event.getEntity();

		Class<Object> damagerclazz = VerifyUtil.getMobClass(damager);
		if (damagerclazz != null) {
			try {
				//@SuppressWarnings("unchecked")
				//Class<Object> instance = (Class<Object>) damagerclazz.newInstance();
				Method method = damagerclazz.getMethod(onhitmethodName, EntityDamageByEntityEvent.class);
				method.invoke(damagerclazz, event);
			} catch (NoSuchMethodException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} else {
			Bukkit.broadcastMessage("not registered mob type");
		}

		Class<Object> victimclazz = VerifyUtil.getMobClass(victim);
		if (victimclazz != null) {
			try {
				Method method = victimclazz.getMethod(ongethitmethodName, EntityDamageByEntityEvent.class);
				method.invoke(victimclazz, event);
			} catch (NoSuchMethodException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} else {
			Bukkit.broadcastMessage("not registered mob type");
		}
	}
}
