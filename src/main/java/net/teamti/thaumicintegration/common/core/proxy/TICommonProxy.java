package net.teamti.thaumicintegration.common.core.proxy;

import appeng.api.Util;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.teamti.thaumicintegration.common.core.handler.ConfigHandler;
import net.teamti.thaumicintegration.common.core.handler.ae.EssentiaCellHandler;
import net.teamti.thaumicintegration.common.item.ModItems;
import net.teamti.thaumicintegration.common.research.ModAspects;
import net.teamti.thaumicintegration.common.research.ModRecipes;
import net.teamti.thaumicintegration.common.research.ModResearch;

public class TICommonProxy
{

	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
	}

	public void init(FMLInitializationEvent event)
	{
		ModItems.initItems();
		
		if (Loader.isModLoaded("AppliedEnergistics"))
		{
			Util.getCellRegistry().addCellHandler(new EssentiaCellHandler());
		}
		
		ModAspects.init();
		
	}

	public void postInit(FMLPostInitializationEvent event)
	{
		ModRecipes.initRecipes();
		ModAspects.addAspects();
		ModResearch.initResearch();
		
	}

	public boolean isClient()
	{
		return false;
	}

	public EntityPlayer getClientPlayer()
	{
		return null;
	}

}
