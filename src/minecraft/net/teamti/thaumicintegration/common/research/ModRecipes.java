package net.teamti.thaumicintegration.common.research;

import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.common.TContent;
import tconstruct.library.TConstructRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import net.teamti.thaumicintegration.common.core.handler.ConfigHandler;
import net.teamti.thaumicintegration.common.item.ModItems;
import net.teamti.thaumicintegration.common.lib.LibResearch;

public class ModRecipes
{

	public static void initRecipes()
	{
		initCraftingRecipes();
		initInfusionRecipes();
	}

	private static void initCraftingRecipes()
	{
		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(Item.bakedPotato), 1), null, new ItemStack[] { new ItemStack(ModItems.potatoesMashed) });
		TConstructRegistry.addToolRecipe(TContent.pickaxe, new Item[] { ModItems.ToolMod_Void, TContent.toolRod, TContent.binding });

	}

	private static void initInfusionRecipes()
	{
		registerResearchItemI(LibResearch.TOOLMOD_VOID, new ItemStack(ModItems.ToolMod_Void), 2, new AspectList().add(Aspect.VOID, 15).add(Aspect.ENTROPY, 20), new ItemStack(Item.enderPearl), new ItemStack(ConfigItems.itemResource, 1, 0), new ItemStack(Block.tnt), new ItemStack(ConfigItems.itemShard, 1, 5), new ItemStack(ConfigItems.itemShard, 1, 5));
	}

	private static void registerResearchItem(String name, String research, ItemStack output, AspectList aspects, Object... stuff)
	{
		ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(research, output, aspects, stuff);
		ConfigResearch.recipes.put(name, recipe);
	}

	private static void registerResearchItem(String name, ItemStack output, Object... stuff)
	{
		GameRegistry.addRecipe(output, stuff);
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		if (name != null && name.length() != 0)
			ConfigResearch.recipes.put(name, recipeList.get(recipeList.size() - 1));
	}

	private static void registerResearchItem(String name, ItemStack output, ItemStack input, AspectList aspects)
	{
		CrucibleRecipe recipe = ThaumcraftApi.addCrucibleRecipe(name, output, input, aspects);
		ConfigResearch.recipes.put(name, recipe);
	}

	private static void registerResearchItemI(String name, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff)
	{
		registerResearchItemI(name, name, output, instability, aspects, input, stuff);
	}

	private static void registerResearchItemI(String name, String research, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff)
	{
		InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(research, output, instability, aspects, input, stuff);
		ConfigResearch.recipes.put(name, recipe);
	}

	private static Object oreDictOrStack(ItemStack stack, String oreDict)
	{
		return OreDictionary.getOres(oreDict).isEmpty() && ConfigHandler.useOreDictMetal ? stack : oreDict;
	}

}
