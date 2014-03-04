package net.teamti.thaumicintegration.common.item.tcon;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.modifiers.tools.ModBoolean;

public class ModVoid extends ModBoolean
{

	public ModVoid(ItemStack[] items, int effect, String tag, String c, String tip)
	{
		super(items, effect, tag, c, tip);
	}

	@Override
	protected boolean canModify(ItemStack tool, ItemStack[] input)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		if (tags.getBoolean("Silk Touch"))
			return false;
		if (tags.getBoolean("Lava"))
			return false;
		return tags.getInteger("Modifiers") > 0 && !tags.getBoolean(key); //Will fail if the modifier is false or the tag doesn't exist
	}

}
