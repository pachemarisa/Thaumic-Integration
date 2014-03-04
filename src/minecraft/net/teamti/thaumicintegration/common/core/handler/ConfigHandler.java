package net.teamti.thaumicintegration.common.core.handler;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import net.teamti.thaumicintegration.client.lib.LibResources;
import net.teamti.thaumicintegration.common.item.ae.ItemEnum;
import net.teamti.thaumicintegration.common.lib.LibItemIDs;
import net.teamti.thaumicintegration.common.lib.LibItemNames;

public class ConfigHandler
{

	private static Configuration config;

	public static boolean useOreDictMetal = true;

	public static boolean useTootlipIndicators = true;

	public static void loadConfig(File configFile)
	{
		config = new Configuration(configFile);

		config.load();

		Property propEnableTooltips = config.get(Configuration.CATEGORY_GENERAL, "tooltipIndicators.enabled", true);
		propEnableTooltips.comment = "Set to false to disable the [TI] tooltips in the thauminomicon.";
		useTootlipIndicators = propEnableTooltips.getBoolean(true);

		Property propOreDict = config.get(Configuration.CATEGORY_GENERAL, "oreDictMetal.enabled", true);
		propOreDict.comment = "Set to false to disable usage of ore dictionary metals (tin and copper).";
		useOreDictMetal = propOreDict.getBoolean(true);

		LibItemIDs.idPotatoesMashed = loadItem(LibItemNames.POTATOES_MASHED, LibItemIDs.idPotatoesMashed, "The Mashed Potato Id");
		
		LibItemIDs.idToolmodVoid = loadItem(LibItemNames.TOOLMOD_VOID, LibItemIDs.idToolmodVoid, "The Perditio Void Id");
		
		for (ItemEnum current : ItemEnum.values())
		{
			current.setID(config.getItem("id_item." + LibResources.PREFIX_MOD + current.getIDName(), current.getID(), current.getDescription()).getInt());
			//current.setID(loadItem(LibItemNames.ESSENTIA_STORAGE, LibItemIDs.idEssentiaStorage));
		}
		
		config.save();
	}

	private static int loadItem(String label, int defaultID, String defaultDescription)
	{
		return config.getItem("id_item." + label, defaultID, defaultDescription).getInt(defaultID);
	}

}
