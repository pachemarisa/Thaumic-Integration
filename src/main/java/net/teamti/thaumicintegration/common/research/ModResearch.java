package net.teamti.thaumicintegration.common.research;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.teamti.thaumicintegration.client.lib.LibResources;
import net.teamti.thaumicintegration.common.item.ModItems;
import net.teamti.thaumicintegration.common.lib.LibResearch;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigResearch;

public class ModResearch
{

	public static void initResearch()
	{
		registerResearchPages();

		ResearchItem research;

		research = new TIResearchItem(LibResearch.TOOLMOD_VOID, LibResearch.CATEGORY_TINKERS, new AspectList().add(Aspect.VOID, 5).add(Aspect.ENTROPY, 5).add(Aspect.TOOL, 3).add(Aspect.ELDRITCH, 1), -2, 2, 0, new ItemStack(ModItems.ToolMod_Void)).registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.TOOLMOD_VOID));
	}

	private static void registerResearchPages()
	{
		ResourceLocation background = new ResourceLocation("thaumicintegration", "textures/gui/TIResearchBG.png");

		ResearchCategories.registerCategory(LibResearch.CATEGORY_TINKERS, new ResourceLocation(LibResources.MISC_R_TINKEROMANCY), background);
	}

	private static ResearchPage recipePage(String name)
	{
		return new ResearchPage((IRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage arcaneRecipePage(String name)
	{
		return new ResearchPage((IArcaneRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage infusionPage(String name)
	{
		return new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage enchantPage(String name)
	{
		return new ResearchPage((InfusionEnchantmentRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage cruciblePage(String name)
	{
		return new ResearchPage((CrucibleRecipe) ConfigResearch.recipes.get(name));
	}

}
