package net.teamti.thaumicintegration.asm.helper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModBoolean;
import tconstruct.modifiers.tools.ModButtertouch;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ModButtertouchTI extends ModButtertouch
{

	public ModButtertouchTI(ItemStack[] items, int effect)
	{
		super(items, effect);
	}

	@Override
	protected boolean canModify(ItemStack tool, ItemStack[] input)
	{
		ToolCore toolItem = (ToolCore) tool.getItem();
		if (!validType(toolItem))
			return false;

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		if (!tags.getBoolean("Lava") && !tags.hasKey("Lapis") && !tags.hasKey("Void Touch"))
		{
			return tags.getInteger("Modifiers") > 0 && !tags.getBoolean(key);
		}
		return false;
	}
}
