package net.teamti.thaumicintegration;

import thaumcraft.common.CommonProxy;
import thaumcraft.common.Thaumcraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.teamti.thaumicintegration.common.core.proxy.TICommonProxy;
import net.teamti.thaumicintegration.common.lib.LibMisc;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION, dependencies = LibMisc.DEPENDENCIES)
public class ThaumicIntegration
{

	@Instance(LibMisc.MOD_ID)
	public static ThaumicIntegration instance;

	@SidedProxy(clientSide = LibMisc.CLIENT_PROXY, serverSide = LibMisc.COMMON_PROXY)
	public static TICommonProxy proxy;

	public static CommonProxy tcProxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		tcProxy = Thaumcraft.proxy;

		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}

}
