package net.teamti.thaumicintegration.common.item.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import net.teamti.thaumicintegration.client.core.helper.IconHelper;
import net.teamti.thaumicintegration.common.core.handler.ModCreativeTab;

public class ItemModFood extends ItemFood
{

	public ItemModFood(int par1, int par2, float par3, boolean par4)
	{
		super(par1, par2, par3, par4);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

}
