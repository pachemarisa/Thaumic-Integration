package net.teamti.thaumicintegration.common.core.handler;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.teamti.thaumicintegration.common.item.ModItems;
import net.teamti.thaumicintegration.common.item.ae.ItemEnum;
import net.teamti.thaumicintegration.common.lib.LibMisc;

public class ModCreativeTab extends CreativeTabs
{

	public static ModCreativeTab INSTANCE = new ModCreativeTab();

	ItemStack displayItem;

	List list;

	public ModCreativeTab()
	{
		super(LibMisc.MOD_ID);
	}

	@Override
	public ItemStack getIconItemStack()
	{
		if(displayItem == null)
			addIcon();
		
		return displayItem;
	}

	@Override
	public void displayAllReleventItems(List list)
	{
		this.list = list;

		addItem(ModItems.potatoesMashed);
		addItem(ModItems.ToolMod_Void);
		addItem(ItemEnum.ESSENTIASTORAGE.getItemInstance());
	}
	
	private void addIcon() {
		ItemStack wand = new ItemStack(ModItems.ToolMod_Void);
		displayItem = wand;
	}

	private void addItem(Item item)
	{
		item.getSubItems(item.itemID, this, list);
	}

	private void addBlock(Block block)
	{
		block.getSubBlocks(block.blockID, this, list);
	}

}
