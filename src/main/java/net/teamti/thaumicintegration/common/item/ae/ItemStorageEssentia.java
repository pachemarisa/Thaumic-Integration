package net.teamti.thaumicintegration.common.item.ae;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.teamti.thaumicintegration.common.core.handler.ModCreativeTab;
import appeng.api.Util;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStorageEssentia extends Item
{

	// Localization suffixes
	public static final String[] suffixes = new String[]
	{ "1k", "4k", "16k", "64k"};

	public static final int[] spaces = new int[]
	{ 1024, 4096, 16348, 65536 };

	// Icons
	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public ItemStorageEssentia(int id)
	{
		super(id);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		int j = MathHelper.clamp_int(par1, 0, 7);
		return this.icons[j];
	}

	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		this.icons = new Icon[suffixes.length];

		for (int i = 0; i < suffixes.length; ++i)
		{
			this.icons[i] = iconRegister.registerIcon("thaumicintegration:" + "ae/storage.essentia." + suffixes[i]);
		}
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(int i, CreativeTabs creativeTab, List listSubItems)
	{
		for (int j = 0; j < 4; ++j)
		{
			listSubItems.add(new ItemStack(i, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int i = itemstack.getItemDamage();
		return "item.thaumicintegration:storageessentia." + suffixes[i] + ".name";//"item.storage.essentia." + suffixes[i];
	}

	@Override
	public String getItemDisplayName(ItemStack itemstack)
	{
		Boolean hasName = !Util.getCellRegistry().getHandlerForCell(itemstack).getName().isEmpty();
		String partitionName = Util.getCellRegistry().getHandlerForCell(itemstack).getName();
		long used_bytes = Util.getCellRegistry().getHandlerForCell(itemstack).usedBytes();
		if (itemstack.getItemDamage() == 4)
		{
			if (used_bytes != 0 && !Util.getCellRegistry().getHandlerForCell(itemstack).getAvailableItems().getItems().isEmpty())
			{
				return StatCollector.translateToLocal(getUnlocalizedName(itemstack)) + " - " + Util.getCellRegistry().getHandlerForCell(itemstack).getAvailableItems().getItems().get(0).getDisplayName();
			} else
			{
				return StatCollector.translateToLocal("tooltip.empty2") + " " + StatCollector.translateToLocal(getUnlocalizedName(itemstack));
			}
		} else
		{

			if (hasName)
			{
				return StatCollector.translateToLocal(getUnlocalizedName(itemstack)) + " - " + partitionName;
			} else
			{
				return StatCollector.translateToLocal(getUnlocalizedName(itemstack));
			}
		}
	}

	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		Boolean partitioned = Util.getCellRegistry().getHandlerForCell(stack).isPreformatted();
		long used_bytes = Util.getCellRegistry().getHandlerForCell(stack).usedBytes();
		long total_bytes = Util.getCellRegistry().getHandlerForCell(stack).totalBytes();
		long used_types = Util.getCellRegistry().getHandlerForCell(stack).storedItemTypes();
		long total_types = Util.getCellRegistry().getHandlerForCell(stack).getTotalItemTypes();

		Util.getCellRegistry().getHandlerForCell(stack).getPreformattedItems();

		list.add((used_bytes / 250) + " of " + total_bytes / 250 + " bytes used");
		list.add(used_types + " of " + total_types + " Essentia types used");
		if (used_bytes != 0)
			list.add("contains " + used_bytes + "units of Aspects");

		if (partitioned)
		{
			list.add(StatCollector.translateToLocal("Appeng.GuiITooltip.Partitioned") + " - " + StatCollector.translateToLocal("Appeng.GuiITooltip.Precise"));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack i, World w, EntityPlayer p)
	{
		if (p.isSneaking())
		{
			if (Util.getCellRegistry().getHandlerForCell(i).storedItemCount() == 0 && p.inventory.addItemStackToInventory(new ItemStack(ItemEnum.ESSENTIASTORAGE.getItemInstance(), 1, 1)))
				return new ItemStack(ItemEnum.ESSENTIASTORAGE.getItemInstance(), 1, i.getItemDamage());
		}
		return i;
	}

	public int getBytes(ItemStack itemstack)
	{
		return spaces[itemstack.getItemDamage()] * 250;
	}

	public int getTotalTypes(ItemStack i)
	{
		return 64;
	}

	public EnumRarity getRarity(ItemStack par1)
	{
		return EnumRarity.epic;
	}
}