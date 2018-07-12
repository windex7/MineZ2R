package custommob;

import org.bukkit.World;

import net.minecraft.server.v1_9_R2.EnumItemSlot;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.Items;

public class IronZombie extends GeneralZombie{
	public IronZombie(World world) {
		super(world);
		this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
	    this.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.IRON_HELMET));
	    this.setSlot(EnumItemSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
	    this.setSlot(EnumItemSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
	    this.setSlot(EnumItemSlot.FEET, new ItemStack(Items.IRON_BOOTS));
	    this.getAttributeInstance(GenericAttributes.g).setValue(8.0D);
	}
}
