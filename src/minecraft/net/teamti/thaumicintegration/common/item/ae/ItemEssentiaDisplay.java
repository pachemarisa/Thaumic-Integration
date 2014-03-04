package net.teamti.thaumicintegration.common.item.ae;

import thaumcraft.api.aspects.Aspect;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class ItemEssentiaDisplay extends Item
{

	public Aspect aspect = null;
	
	public ItemEssentiaDisplay(int id)
	{
		super(id);
		this.setMaxStackSize(Integer.MAX_VALUE);
	}

	@Override
	public Icon getIconFromDamage(int dmg)
	{
		if (this.aspect != null) {
			Aspect tFluid = Aspect.getAspect(this.aspect.getTag());
			return (Icon) (tFluid != null ? tFluid.getImage() : Block.stone.getIcon(0, 0));
		}
		else
		{
			return Block.stone.getIcon(0, 0);
		}
	}

	public int func_94901_k()
	{
		return 0;
	}

	@Override
	public String getUnlocalizedName()
	{
		return "FluidDisplay - this item is just used to mime fluids!";
	}

	public String getUnlocalizedName(ItemStack aStack)
	{
		if (aStack == null)
		{
			Aspect tFluid = Aspect.getAspect(this.aspect.getTag());
			if (tFluid != null)
			{
				return tFluid.getName();
			}
		}

		return "FluidDisplay - this item is just used to mime fluids!";
	}

	public String getItemStackDisplayName(ItemStack aStack)
	{
		return StatCollector.translateToLocal(this.getUnlocalizedName(aStack));
	}

	public String getItemDisplayName(ItemStack aStack)
	{
		return StatCollector.translateToLocal(this.getUnlocalizedName(aStack));
	}
}
