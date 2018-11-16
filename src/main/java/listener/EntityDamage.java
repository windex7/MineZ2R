package listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import custommob.CustomMob;
import util.VerifyUtil;

public class EntityDamage implements Listener{
	private static String onhitmethodName = "onHit";
	private static String ongethitmethodName = "onGetHit";

	private static String ondamagedmethodName = "onDamage";

	private static String ondeathmethodName = "onDeath";

	@EventHandler
	public static void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity victim = event.getEntity();

		Class<Object> damagerclazz = VerifyUtil.getMobClass(damager);
		if (damagerclazz != null) {
			CustomMob mob = (CustomMob) damager;
			mob.onHit(event);
			/*
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
			*/
		} else if (damager instanceof Player) {

		}

		Class<Object> victimclazz = VerifyUtil.getMobClass(victim);
		if (victimclazz != null) {
			CustomMob mob = (CustomMob) victim;
			mob.onGetHit(event);
			/*
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
			*/
		} else if (victim instanceof Player) {

		}
	}

	@EventHandler
	public static void onEntityDamage(EntityDamageEvent event) {
		Entity victim = event.getEntity();
		Class<Object> victimclazz = VerifyUtil.getMobClass(victim);
		if (victimclazz != null) {
			CustomMob mob = (CustomMob) victim;
			mob.onDamage(event);
			/*
			try {
				Method method = victimclazz.getMethod(ondamagedmethodName, EntityDamageEvent.class);
				method.invoke(victimclazz, event);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				Bukkit.broadcastMessage(e.getCause().toString());
			}
			*/
		} else if (victim instanceof Player) {

		}
	}

	@EventHandler
	public static void onEntityDeath(EntityDeathEvent event) {
		Entity victim = event.getEntity();
		Class<Object> victimclazz = VerifyUtil.getMobClass(victim);
		if (victimclazz != null) {
			CustomMob mob = (CustomMob) victim;
			mob.onDeath(event);
			/*
			try {
				Method method = victimclazz.getMethod(ondeathmethodName, EntityDeathEvent.class);
				method.invoke(victimclazz, event);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				Bukkit.broadcastMessage(e.getCause().toString());
			}
			*/
		} else if (victim instanceof Player) {

		}
	}
}
