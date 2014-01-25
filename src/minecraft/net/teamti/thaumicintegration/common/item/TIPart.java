package net.teamti.thaumicintegration.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.teamti.thaumicintegration.client.lib.LibResources;
import tconstruct.library.ActiveToolMod;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.library.util.IToolPart;

public class TIPart extends Item implements IToolPart
{

	public TIPart(int par1)
	{
		super(par1);
		setCreativeTab(TConstructRegistry.materialTab);
		setMaxStackSize(64);
		setMaxDamage(0);
		setHasSubtypes(true);

	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon)
	{
		this.itemIcon = icon.registerIcon(LibResources.PREFIX_MOD + "parts/" + ((this.getUnlocalizedName()).replace("thaumicintegration:", "")).substring(5));
	}

	@Override
	public int getMaterialID(ItemStack stack)
	{
		return stack.getItemDamage();//ModItems.void_id;
	}

}
