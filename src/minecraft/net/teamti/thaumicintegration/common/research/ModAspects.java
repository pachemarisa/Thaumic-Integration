package net.teamti.thaumicintegration.common.research;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.ItemApi;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModAspects
{
	public static Aspect MANUFACTURE;

	public static void init()
	{
		MANUFACTURE = new Aspect("confectio", 0x9639ff, new Aspect[] { Aspect.CRAFT, Aspect.MECHANISM }, new ResourceLocation("thaumicintegration", "textures/aspects/confectio.png"), 1);
	}

	public static void addAspects()
	{

		AspectList list;

		list = ThaumcraftApiHelper.getObjectAspects(new ItemStack(Block.furnaceIdle));
		list.add(MANUFACTURE, 1);
		ThaumcraftApi.registerComplexObjectTag(Block.furnaceIdle.blockID, 0, list);

		list = ThaumcraftApiHelper.getObjectAspects(new ItemStack(Block.furnaceBurning));
		list.add(MANUFACTURE, 3);
		ThaumcraftApi.registerComplexObjectTag(Block.furnaceBurning.blockID, 0, list);

		//Am getting a NPE for the following registry. Leaving it out for now.

		list = ThaumcraftApiHelper.getObjectAspects(new ItemStack(Item.brewingStand));
		list.add(MANUFACTURE, 3);
		ThaumcraftApi.registerComplexObjectTag(Item.brewingStand.itemID, 0, list);
		
		ThaumcraftApi.registerComplexObjectTag(Block.brewingStand.blockID, 0, new AspectList().add(MANUFACTURE, 3));

	}
}
