package net.teamti.thaumicintegration.common.item;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.ModAutoSmelt;
import thaumcraft.common.lib.Utils;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.teamti.thaumicintegration.common.core.helper.TextHelper;
import net.teamti.thaumicintegration.common.item.ae.ItemEnum;
import net.teamti.thaumicintegration.common.item.misc.ItemMod;
import net.teamti.thaumicintegration.common.item.misc.ItemModFood;
import net.teamti.thaumicintegration.common.item.tcon.ModVoid;
import net.teamti.thaumicintegration.common.item.tcon.TIActiveOmniMod;
import net.teamti.thaumicintegration.common.lib.LibItemIDs;
import net.teamti.thaumicintegration.common.lib.LibItemNames;

public class ModItems
{
	public static Item ToolMod_Void;

	public static Item potatoesMashed;

	public static void initItems()
	{

		if (Loader.isModLoaded("AppliedEnergistics"))
		{
			
			for (ItemEnum current : ItemEnum.values())
			{
				try
				{
					current.setItemInstance(current.getItemClass().getConstructor(int.class).newInstance(current.getID()));
					GameRegistry.registerItem(current.getItemInstance(), current.getItemInstance().getUnlocalizedName(), "thaumicintegration");
				}
				catch (Throwable e)
				{
				}
			}
		}

		potatoesMashed = new ItemModFood(LibItemIDs.idPotatoesMashed, 8, 0.8F, true).setUnlocalizedName(LibItemNames.POTATOES_MASHED);
		ToolMod_Void = new ItemMod(LibItemIDs.idToolmodVoid).setUnlocalizedName(LibItemNames.TOOLMOD_VOID);

		GameRegistry.registerItem(ToolMod_Void, "titanium_pickaxe_head");

		addModifiers();
	}

	private static void addModifiers()
	{
		ToolBuilder tb = ToolBuilder.instance;
		ItemStack pearl = new ItemStack(ToolMod_Void, 1, 0);
		int effect = 20;
		tb.registerToolMod(new ModVoid(new ItemStack[] { pearl }, effect, "Void Touch", TextHelper.AQUA, "Void Touch"));

		for (ToolCore tool : TConstructRegistry.getToolMapping())
		{
			if (tool instanceof HarvestTool)
			{
				TConstructClientRegistry.addEffectRenderMapping(tool, effect, "thaumicintegration", "VoidMiner", true);
			}
		}
		TConstructRegistry.registerActiveToolMod(new TIActiveOmniMod());
	}
}
