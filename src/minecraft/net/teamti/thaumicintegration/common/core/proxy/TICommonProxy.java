package net.teamti.thaumicintegration.common.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.teamti.thaumicintegration.common.core.handler.ConfigHandler;
import net.teamti.thaumicintegration.common.item.ModItems;
import net.teamti.thaumicintegration.common.research.ModRecipes;
import net.teamti.thaumicintegration.common.research.ModResearch;

public class TICommonProxy
{

	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		ModItems.initItems();
	}

	public void init(FMLInitializationEvent event)
	{

	}

	public void postInit(FMLPostInitializationEvent event)
	{
		ModRecipes.initRecipes();
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
