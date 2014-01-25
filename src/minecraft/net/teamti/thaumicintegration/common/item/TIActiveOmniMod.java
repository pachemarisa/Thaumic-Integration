package net.teamti.thaumicintegration.common.item;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tconstruct.common.TContent;
import tconstruct.library.tools.ToolCore;
import tconstruct.modifiers.tools.TActiveOmniMod;
import tconstruct.util.config.PHConstruct;

public class TIActiveOmniMod extends TActiveOmniMod
{

	Random random = new Random();

	/* Harvesting */
	@Override
	public boolean beforeBlockBreak(ToolCore tool, ItemStack stack, int x, int y, int z, EntityLivingBase entity)
	{
		if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)
			return false;
		TContent.modLapis.midStreamModify(stack, tool);

		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");
		World world = entity.worldObj;
		int bID = entity.worldObj.getBlockId(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		Block block = Block.blocksList[bID];
		if (block == null || bID < 1 || bID > 4095)
			return false;

		if (tags.hasKey("Void Touch") && block.quantityDropped(meta, 0, random) != 0)
		{
			world.setBlockToAir(x, y, z);
			if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode)
				tool.onBlockDestroyed(stack, world, bID, x, y, z, entity);
			if (!world.isRemote)
			{
				world.playAuxSFX(2001, x, y, z, bID + (meta << 12));
			}
			return true;

		}

		return false;
	}

}
