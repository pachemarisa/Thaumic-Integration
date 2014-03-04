package net.teamti.thaumicintegration.common.core.handler.ae;

import appeng.api.ICellHandler;
import appeng.api.me.util.IMEInventoryHandler;
import net.minecraft.item.ItemStack;
import net.teamti.thaumicintegration.common.item.ae.ItemEnum;
import net.teamti.thaumicintegration.common.item.ae.ItemStorageEssentia;

public class EssentiaCellHandler implements ICellHandler
{
	@Override
	public boolean isCell(ItemStack is)
	{
		return is != null ? is.getItem() == ItemEnum.ESSENTIASTORAGE.getItemInstance() : false;
	}

	@Override
	public IMEInventoryHandler getCellHandler(ItemStack itemstack)
	{
		if (itemstack.getItem() == ItemEnum.ESSENTIASTORAGE.getItemInstance())
		{
			ItemStorageEssentia cell = (ItemStorageEssentia) itemstack.getItem();
			return new EssentiaStorageInventoryHandler(itemstack, cell.getBytes(itemstack), cell.getTotalTypes(itemstack));
		}
		return null;
	}
}