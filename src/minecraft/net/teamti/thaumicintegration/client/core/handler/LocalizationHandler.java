package net.teamti.thaumicintegration.client.core.handler;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.teamti.thaumicintegration.client.lib.LibResources;

public final class LocalizationHandler
{

	public static void loadLocalizations()
	{
		for (String locale : LibResources.LANGS)
			LanguageRegistry.instance().loadLocalization(LibResources.PREFIX_LANG + locale + ".lang", locale, false);
	}

}
