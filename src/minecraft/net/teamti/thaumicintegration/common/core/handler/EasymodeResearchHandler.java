package net.teamti.thaumicintegration.common.core.handler;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.common.container.ContainerResearchTable;
import thaumcraft.common.items.ItemResearchNotes;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.research.ResearchNoteData;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class EasymodeResearchHandler implements ITickHandler
{

	public static void onPlayerTick(EntityPlayer player)
	{
		if (player != null && player.openContainer != null && player.openContainer instanceof ContainerResearchTable)
		{
			ContainerResearchTable researchContainer = (ContainerResearchTable) player.openContainer;
			ItemStack notes = researchContainer.tileEntity.getStackInSlot(2);
			if (notes != null && notes.getItem() instanceof ItemResearchNotes)
			{
				ResearchNoteData data = ResearchManager.getData(notes);
				boolean didAny = false;

				if (data != null && data.grid != null)
					for (int i = 0; i < 15; i++)
						for (int j = 0; j < 15; j++)
							if (data.grid[i][j].equals("O"))
							{
								data.grid[i][j] = "S";
								didAny = true;
							}

				if (didAny)
					ResearchManager.updateData(notes, data);
				researchContainer.tileEntity.setInventorySlotContents(2, notes.copy());
			}
		}
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		// NO-OP
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		onPlayerTick((EntityPlayer) tickData[0]);
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel()
	{
		return "TI_Easymode";
	}
}
