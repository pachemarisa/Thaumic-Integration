package net.teamti.thaumicintegration.common.item.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.teamti.thaumicintegration.client.core.helper.IconHelper;
import net.teamti.thaumicintegration.common.core.handler.ModCreativeTab;

public class ItemMod extends Item
{

	public ItemMod(int par1)
	{
		super(par1);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

}
