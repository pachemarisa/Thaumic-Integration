package net.teamti.thaumicintegration.client.core.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.teamti.thaumicintegration.client.core.handler.LocalizationHandler;
import net.teamti.thaumicintegration.client.core.helper.ClientHelper;
import net.teamti.thaumicintegration.common.core.proxy.TICommonProxy;

public class TIClientProxy extends TICommonProxy
{

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);

		LocalizationHandler.loadLocalizations();
	}

	@Override
	public boolean isClient()
	{
		return true;
	}

	@Override
	public EntityPlayer getClientPlayer()
	{
		return ClientHelper.clientPlayer();
	}

}
