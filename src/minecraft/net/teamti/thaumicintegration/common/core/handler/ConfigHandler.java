package net.teamti.thaumicintegration.common.core.handler;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
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

		LibItemIDs.idPotatoesMashed = loadItem(LibItemNames.POTATOES_MASHED, LibItemIDs.idPotatoesMashed);
	}

	private static int loadItem(String label, int defaultID)
	{
		return config.getItem("id_item." + label, defaultID).getInt(defaultID);
	}

}
