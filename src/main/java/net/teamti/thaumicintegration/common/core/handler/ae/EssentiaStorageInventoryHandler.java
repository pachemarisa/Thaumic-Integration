package net.teamti.thaumicintegration.common.core.handler.ae;

import java.util.ArrayList;
import java.util.List;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.teamti.thaumicintegration.common.item.ae.ItemEnum;
import appeng.api.IAEItemStack;
import appeng.api.IItemList;
import appeng.api.Util;
import appeng.api.config.FuzzyMode;
import appeng.api.config.ItemFlow;
import appeng.api.config.ListMode;
import appeng.api.me.util.IGridInterface;
import appeng.api.me.util.IMEInventoryHandler;

public class EssentiaStorageInventoryHandler implements IMEInventoryHandler
{
	private ItemStack storage;
	private long totalBytes;
	private int totalTypes;
	private int priority;
	private TileEntity updateTarget;
	private IGridInterface grid;
	private IMEInventoryHandler parent;
	private List<AspectList> cachedInventory;
	private List<Aspect> cachedPreformats = new ArrayList<Aspect>(63);
	private Item fluidItem = ItemEnum.ESSENTIADISPLAY.getItemInstance();
	private ListMode preformattedMode = ListMode.WHITELIST;
	private String cachedName;

	public EssentiaStorageInventoryHandler(ItemStack itemstack, long totalBytes, int totalTypes)
	{
		this.storage = itemstack;
		this.totalBytes = totalBytes;
		this.totalTypes = totalTypes;
		cachedInventory = new ArrayList<AspectList>(totalTypes);

		for (int i = 0; i < totalTypes; i++)
		{
			cachedInventory.add(readAspectListFromSlot(i));
		}

		cachedName = readNameFromNBT();

		for (int i = 0; i < 63; i++)
		{
			cachedPreformats.add(getPreformattedFluid(i));
		}

		preformattedMode = readPreformattedMode();
	}

	@Override
	public long storedItemTypes()
	{
		int storedFluidTypes = 0;

		for (int i = 0; i < totalTypes; i++)
		{
			if (cachedInventory.get(i) != null)
				storedFluidTypes++;
		}

		return storedFluidTypes;
	}

	@Override
	public long storedItemCount()
	{
		int storedFluidAmount = 0;

		for (int i = 0; i < totalTypes; i++)
		{
			AspectList currentStack = cachedInventory.get(i);
			storedFluidAmount += currentStack != null ? currentStack.getAspects().length : 0;
		}

		return storedFluidAmount;
	}

	@Override
	public long remainingItemCount()
	{
		long remainingFluidSpace = totalBytes;

		for (int i = 0; i < totalTypes; i++)
		{
			AspectList currentStack = cachedInventory.get(i);
			remainingFluidSpace -= currentStack != null ? currentStack.getAspects().length : 0;
		}

		return remainingFluidSpace < 0 ? 0 : remainingFluidSpace;
	}

	@Override
	public long remainingItemTypes()
	{
		long remainingFluidTypes = totalBytes;

		for (int i = 0; i < totalTypes; i++)
		{
			if (cachedInventory.get(i) == null)
				remainingFluidTypes += 1;
		}

		return remainingFluidTypes;
	}

	@Override
	public boolean containsItemType(IAEItemStack aeitemstack)
	{
		if (aeitemstack != null && aeitemstack.getItem() == fluidItem)
		{
			for (int i = 0; i < totalTypes; i++)
			{
				AspectList currentStack = cachedInventory.get(i);
				if (currentStack != null && currentStack.getAspects().length == aeitemstack.getItemDamage())
					return true;
			}
		}

		return false;
	}

	@Override
	public long getTotalItemTypes()
	{
		return totalTypes;
	}

	@Override
	public long countOfItemType(IAEItemStack aeitemstack)
	{
		long countOfFluidType = 0;

		if (aeitemstack != null && aeitemstack.getItem() == fluidItem)
		{
			for (int i = 0; i < totalTypes; i++)
			{
				AspectList currentStack = cachedInventory.get(i);
				if (currentStack != null && currentStack.aspects.size() == aeitemstack.getItemDamage())
					countOfFluidType += currentStack.getAspects().length;
			}
		}
		return countOfFluidType;
	}

	@Override
	public IAEItemStack addItems(IAEItemStack input)
	{
		IAEItemStack addedStack = input.copy();

		if (input.getItem() == fluidItem && (!isPreformatted() || (isPreformatted() && isItemInPreformattedItems(input.getItemStack()))))
		{
			for (int i = 0; i < totalTypes; i++)
			{
				AspectList currentStack = cachedInventory.get(i);
				if (currentStack != null && currentStack.aspects.size() == input.getItemDamage())
				{
					if (input.getStackSize() <= freeBytes())
					{
						writeAspectListToSlot(i, new AspectList(input.getItemDamage(), currentStack.getAspects().length + (int) input.getStackSize()));
						addedStack = null;
					} else
					{
						writeAspectListToSlot(i, new AspectList(input.getItemDamage(), currentStack.getAspects().length + (int) freeBytes()));
						addedStack.setStackSize(input.getStackSize() - freeBytes());
					}
					return addedStack;
				}
			}

			for (int i = 0; i < totalTypes; i++)
			{
				AspectList currentStack = cachedInventory.get(i);
				if (currentStack == null)
				{
					if (input.getStackSize() <= freeBytes())
					{
						writeAspectListToSlot(i, new AspectList(input.getItemDamage(), (int) input.getStackSize()));
						addedStack = null;
					} else
					{
						writeAspectListToSlot(i, new AspectList(input.getItemDamage(), (int) freeBytes()));
						addedStack.setStackSize(input.getStackSize() - freeBytes());
					}
					return addedStack;
				}
			}
		}
		return addedStack;
	}

	@Override
	public IAEItemStack calculateItemAddition(IAEItemStack input)
	{
		if (input.getItem() == fluidItem)
		{
			if (!isPreformatted() || (isPreformatted() && isItemInPreformattedItems(input.getItemStack())))
			{
				IAEItemStack addedStack = input.copy();

				for (int i = 0; i < totalTypes; i++)
				{
					AspectList currentStack = cachedInventory.get(i);
					if (currentStack == null || currentStack.aspects.size() == input.getItemDamage())
					{
						if (input.getStackSize() <= freeBytes())
						{
							addedStack = null;
						} else
						{
							addedStack.setStackSize(input.getStackSize() - freeBytes());
						}
						return addedStack;
					}
				}
			}
		}
		return input;
	}

	@Override
	public IAEItemStack extractItems(IAEItemStack request)
	{
		IAEItemStack removedStack = request.copy();

		if (request.getItem() == fluidItem)
		{
			for (int i = 0; i < totalTypes; i++)
			{
				AspectList currentStack = cachedInventory.get(i);
				if (currentStack != null && currentStack.aspects.size() == request.getItemDamage())
				{
					if (currentStack.getAspects().length - request.getStackSize() >= 0)
					{
						removedStack.setStackSize(request.getStackSize());
						if (currentStack.getAspects().length - request.getStackSize() == 0)
						{
							writeAspectListToSlot(i, null);
						} else
						{
							writeAspectListToSlot(i, new AspectList(currentStack.size(), currentStack.getAspects().length - (int) request.getStackSize()));
						}
					} else
					{
						removedStack.setStackSize(currentStack.getAspects().length);
						writeAspectListToSlot(i, null);
					}

					if (updateTarget != null)
						updateTarget.onInventoryChanged();

					return removedStack;
				}
			}
		}
		return null;
	}

	@Override
	public IItemList getAvailableItems(IItemList out)
	{
		for (int i = 0; i < totalTypes; i++)
		{
			AspectList currentStack = cachedInventory.get(i);
			if (currentStack != null)
			{
				IAEItemStack currentItemStack = Util.createItemStack(new ItemStack(fluidItem, 1, currentStack.size()));
				currentItemStack.setStackSize(currentStack.getAspects().length);
				out.add(currentItemStack);
			}
		}

		return out;
	}

	@Override
	public IItemList getAvailableItems()
	{
		return getAvailableItems(Util.createItemList());
	}

	public boolean isItemInPreformattedItems(ItemStack request)
	{
		if (!isPreformatted())
			return true;

		for (ItemStack itemstack : getPreformattedItems())
		{
			if (itemstack.getItem() == request.getItem() && itemstack.getItemDamage() == request.getItemDamage())
				return preformattedMode == ListMode.WHITELIST ? true : false;
		}
		return preformattedMode == ListMode.WHITELIST ? false : true;
	}

	@Override
	public long getAvailableSpaceByItem(IAEItemStack itemstack, long maxNeeded)
	{
		if (itemstack != null)
		{
			if (remainingItemCount() > 0)
			{
				return itemstack.getItem() == fluidItem ? remainingItemCount() : 0;
			} else
			{
				for (IAEItemStack stack : this.getAvailableItems())
				{
					if (stack == null || stack != null && stack.getItem() == itemstack.getItem() && stack.getItemDamage() == itemstack.getItemDamage())
						return remainingItemCount();
				}
			}
		}
		return 0;
	}

	@Override
	public int getPriority()
	{
		return priority;
	}

	@Override
	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	@Override
	public long totalBytes()
	{
		return totalBytes;
	}

	@Override
	public long freeBytes()
	{
		return remainingItemCount();
	}

	@Override
	public long usedBytes()
	{
		return totalBytes() - freeBytes();
	}

	@Override
	public long unusedItemCount()
	{
		return freeBytes();
	}

	@Override
	public boolean canHoldNewItem()
	{
		for (int i = 0; i < totalTypes; i++)
		{
			if (cachedInventory.get(i) == null)
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void setUpdateTarget(TileEntity e)
	{
		this.updateTarget = e;
	}

	@Override
	public List<ItemStack> getPreformattedItems()
	{
		List<ItemStack> fluidItemList = new ArrayList<ItemStack>();

		for (int i = 0; i < 63; i++)
		{
			Aspect current = cachedPreformats.get(i);
			if (current != null)
			{
				//fluidItemList.add(new ItemStack(ItemEnum.ESSENTIADISPLAY.getItemInstance(), 1, current));
			}
		}
		return fluidItemList;
	}

	@Override
	public boolean isPreformatted()
	{
		for (int i = 0; i < 63; i++)
		{
			if (cachedPreformats.get(0) != null)
				return true;
		}
		return false;
	}

	@Override
	public boolean isFuzzyPreformatted()
	{
		return false;
	}

	@Override
	public void setFuzzyPreformatted(boolean nf)
	{
		// Fuzzy on Fluids? I don't think so.
	}

	@Override
	public void setName(String name)
	{
		writeNameToNBT(name);
		cachedName = name;
	}

	@Override
	public String getName()
	{
		return !cachedName.isEmpty() ? cachedName : readNameFromNBT();
	}

	@Override
	public void setGrid(IGridInterface grid)
	{
		this.grid = grid;
	}

	@Override
	public IGridInterface getGrid()
	{
		return grid;
	}

	@Override
	public void setParent(IMEInventoryHandler parent)
	{
		this.parent = parent;

	}

	@Override
	public IMEInventoryHandler getParent()
	{
		return parent;
	}

	@Override
	public void removeGrid(IGridInterface grid, IMEInventoryHandler ignore, List<IMEInventoryHandler> duplicates)
	{
		// Algo told me to do nothing here :P
	}

	@Override
	public void validate(List<IMEInventoryHandler> duplicates)
	{
		// Algo told me to do nothing here :P
	}

	@Override
	public boolean canAccept(IAEItemStack input)
	{
		if (input != null && input.getItem() == fluidItem)
		{
			if (getAvailableItems() != null)
			{
				for (IAEItemStack current : getAvailableItems())
				{
					if (current == null || current.getItemDamage() == input.getItemDamage())
						return true;
				}
				if (getAvailableItems().size() < getTotalItemTypes())
					return true;
			} else
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemFlow getFlow()
	{
		return ItemFlow.READ_WRITE;
	}

	@Override
	public void setFlow(ItemFlow p)
	{
		// nothing
	}

	@Override
	public FuzzyMode getFuzzyModePreformatted()
	{
		return FuzzyMode.Percent_99;
	}

	@Override
	public void setPreformattedItems(IItemList in, FuzzyMode mode, ListMode m)
	{
		if (storage.stackTagCompound == null)
			storage.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = storage.stackTagCompound;

		cachedPreformats = new ArrayList<Aspect>(63);
		preformattedMode = m;
		writePreformattedMode(m);

		for (int i = 0; i < 63; i++)
		{
			nbt.removeTag("PreformattedFluidName#" + i);
		}

		if (!in.getItems().isEmpty())
		{
			for (int i = 0; i < in.getItems().size(); i++)
			{
				ItemStack currentItemStack = in.getItems().get(i);
				if (currentItemStack != null)
				{
					/**if (FluidContainerRegistry.isFilledContainer(currentItemStack))
					{
						AspectList toWrite = FluidContainerRegistry.getFluidForFilledItem(currentItemStack);
						cachedPreformats.add(toWrite.getFluid());
						nbt.setString("PreformattedFluidName#" + i, FluidContainerRegistry.getFluidForFilledItem(currentItemStack).getFluid().getName());
					} else if (currentItemStack.getItem() == ItemEnum.ESSENTIADISPLAY.getItemInstance())
					{
						int toWrite = currentItemStack.getItemDamage();
						cachedPreformats.add(FluidRegistry.getFluid(toWrite));
						nbt.setString("PreformattedFluidName#" + i, FluidRegistry.getFluidName(toWrite));
					} else if (currentItemStack.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem) currentItemStack.getItem()).getFluid(currentItemStack) != null)
					{
						AspectList toWrite = ((IFluidContainerItem) currentItemStack.getItem()).getFluid(currentItemStack);
						nbt.setString("PreformattedFluidName#" + i, toWrite.getFluid().getName());
					}*/
				}
			}
		} else
		{
			for (int i = 0; i < 63; i++)
			{
				nbt.removeTag("PreformattedFluidName#" + i);
			}
		}
		System.out.println("");
	}

	@Override
	public ListMode getListMode()
	{
		return preformattedMode;
	}

	private void writeAspectListToSlot(int slotID, AspectList input)
	{
		cachedInventory.set(slotID, input);

		if (storage.stackTagCompound == null)
			storage.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = storage.stackTagCompound;

		if (input != null)
		{
			NBTTagCompound fluidTag = new NBTTagCompound();
			input.writeToNBT(fluidTag);
			nbt.setCompoundTag("Fluid#" + slotID, fluidTag);
		} else
		{
			nbt.removeTag("Fluid#" + slotID);
		}
	}

	private AspectList readAspectListFromSlot(int slotID)
	{
		if (storage.stackTagCompound == null)
			storage.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = storage.stackTagCompound;

		// Temporary Code, will stay 5 versions!
		int oldFluidID = nbt.getInteger("FluidID#" + slotID);
		long oldFluidAmount = nbt.getLong("FluidAmount#" + slotID);
		if (oldFluidID > 0 && oldFluidAmount > 0)
		{
			return new AspectList(oldFluidID, (int) oldFluidAmount);
		}
		nbt.removeTag("FluidID#" + slotID);
		nbt.removeTag("FluidAmount#" + slotID);

		return null;//currentStack.(nbt.getCompoundTag("Fluid#" + slotID));
	}

	private void writeNameToNBT(String name)
	{
		if (storage.stackTagCompound == null)
			storage.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = storage.stackTagCompound;

		nbt.setString("PreformattedName", name);
	}

	private String readNameFromNBT()
	{
		if (storage.stackTagCompound == null)
			storage.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = storage.stackTagCompound;

		return nbt.getString("PreformattedName");
	}

	private Aspect getPreformattedFluid(int slotID)
	{
		if (storage.stackTagCompound == null)
			storage.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = storage.stackTagCompound;

		return Aspect.getAspect(nbt.getString("PreformattedFluidName#" + slotID));
	}

	public ListMode readPreformattedMode()
	{
		if (storage.stackTagCompound == null)
			storage.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = storage.stackTagCompound;

		int mode = nbt.getInteger("PreformattedMode");
		return mode == 0 ? ListMode.WHITELIST : mode == 1 ? ListMode.BLACKLIST : ListMode.WHITELIST;
	}

	public void writePreformattedMode(ListMode mode)
	{
		if (storage.stackTagCompound == null)
			storage.stackTagCompound = new NBTTagCompound();
		NBTTagCompound nbt = storage.stackTagCompound;

		nbt.setInteger("PreformattedMode", mode != null ? mode == ListMode.BLACKLIST ? 1 : 0 : 0);
	}
}