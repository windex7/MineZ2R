package custommob;

import java.util.List;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

import net.minecraft.server.v1_9_R2.EntityZombie;
import net.minecraft.server.v1_9_R2.PathfinderGoalSelector;
import util.PrivateField;

public class GeneralZombie extends EntityZombie {
	@SuppressWarnings("rawtypes")
	public GeneralZombie(World world) {
		super(((CraftWorld)world).getHandle());;

		List goalB = (List)PrivateField.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
		List goalC = (List)PrivateField.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
		List targetB = (List)PrivateField.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
		List targetC = (List)PrivateField.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();


	}
}
