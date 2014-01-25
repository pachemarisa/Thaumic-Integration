package net.teamti.thaumicintegration.common.item;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.teamti.thaumicintegration.common.core.helper.TextHelper;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;

public class ModVoidold extends ToolMod
{
	String tooltipName;

	int increase;

	int max;

	public ModVoidold(ItemStack[] items, int effect, int inc)
	{
		super(items, effect, "VoidMiner");
		tooltipName = TextHelper.AQUA + "Void Touch";
		increase = inc;
		max = 1;
	}

	@Override
	protected boolean canModify(ItemStack tool, ItemStack[] input)
	{
		ToolCore toolItem = (ToolCore) tool.getItem();
		if (!validType(toolItem))
			return false;

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		if (!tags.hasKey(key))
			return tags.getInteger("Modifiers") > 0;

		int keyPair[] = tags.getIntArray(key);
		if (keyPair[0] + increase <= keyPair[1])
			return true;

		else if (keyPair[0] == keyPair[1])
			return tags.getInteger("Modifiers") > 0;

		if (tags.getBoolean("Silk Touch"))
			return false;

		if (tags.getBoolean("Lava"))
			return false;

		else
			return false;
	}

	@Override
	public void modify(ItemStack[] input, ItemStack tool)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		int[] keyPair;
		if (tags.hasKey(key))
		{
			keyPair = tags.getIntArray(key);
			if (keyPair[0] % max == 0)
			{
				keyPair[0] += increase;
				keyPair[1] += max;
				tags.setIntArray(key, keyPair);

				int modifiers = tags.getInteger("Modifiers");
				modifiers -= 1;
				tags.setInteger("Modifiers", modifiers);
			}
			else
			{
				keyPair[0] += increase;
				tags.setIntArray(key, keyPair);
			}
			updateModTag(tool, keyPair);
		}
		else
		{
			int modifiers = tags.getInteger("Modifiers");
			modifiers -= 1;
			tags.setInteger("Modifiers", modifiers);
			String modName = TextHelper.AQUA + "Void Mod (" + increase + "/" + max + ")";
			int tooltipIndex = addToolTip(tool, tooltipName, modName);
			keyPair = new int[] { increase, max, tooltipIndex };
			tags.setIntArray(key, keyPair);
		}
	}

	void updateModTag(ItemStack tool, int[] keys)
	{
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
		String tip = "ModifierTip" + keys[2];
		String modName = TextHelper.AQUA + "Void Mod (" + keys[0] + "/" + keys[1] + ")";
		tags.setString(tip, modName);
	}

	public boolean validType(ToolCore tool)
	{
		List list = Arrays.asList(tool.toolCategories());
		return list.contains("harvest");
	}
}
