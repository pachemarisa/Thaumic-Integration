package net.teamti.thaumicintegration.common.research;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.teamti.thaumicintegration.common.core.handler.ConfigHandler;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ResearchPage.PageType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TIResearchItem extends ResearchItem
{

	public TIResearchItem(String par1, String par2)
	{
		super(par1, par2);
	}

	public TIResearchItem(String par1, String par2, AspectList tags, int par3, int par4, int par5, ItemStack icon)
	{
		super(par1, par2, tags, par3, par4, par5, icon);
	}

	public TIResearchItem(String par1, String par2, AspectList tags, int par3, int par4, int par5, ResourceLocation icon)
	{
		super(par1, par2, tags, par3, par4, par5, icon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName()
	{
		return StatCollector.translateToLocal("tiresearch.name." + key);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getText()
	{
		return (ConfigHandler.useTootlipIndicators ? StatCollector.translateToLocal(getPrefix()) : "") + StatCollector.translateToLocal("tiresearch.lore." + key);
	}

	String getPrefix()
	{
		return "tiresearch.prefix";
	}

	@Override
	public ResearchItem setPages(ResearchPage... par)
	{
		for (ResearchPage page : par)
		{
			if (page.type == PageType.TEXT)
				page.text = "tiresearch.page." + key + "." + page.text;

			if (checkInfusion() && page.type == PageType.INFUSION_CRAFTING)
			{
				if (parentsHidden == null || parentsHidden.length == 0)
					parentsHidden = new String[] { "INFUSION" };
				else
				{
					String[] newParents = new String[parentsHidden.length + 1];
					newParents[0] = "INFUSION";
					for (int i = 0; i < parentsHidden.length; i++)
						newParents[i + 1] = parentsHidden[i];
					parentsHidden = newParents;
				}
			}
		}

		return super.setPages(par);
	}

	boolean checkInfusion()
	{
		return true;
	}
}
