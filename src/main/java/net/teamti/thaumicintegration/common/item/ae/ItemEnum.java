package net.teamti.thaumicintegration.common.item.ae;

import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.teamti.thaumicintegration.common.lib.LibItemIDs;

public enum ItemEnum
{
	
	ESSENTIASTORAGE("item.storage.Essentia", LibItemIDs.idEssentiaStorage, ItemStorageEssentia.class, "The item used for the Essentia Storage", "EssentiaStorageItem"),
	ESSENTIADISPLAY("item.fluiddisplay", LibItemIDs.idEssentiaDisplay, ItemEssentiaDisplay.class, "ID for the item used for displaying Essentia in the terminal and storing them in the ME Network", "EssentiaDisplayItem");

	private final String internalName;
	private String description, IDName;
	private int ID;
	private Item item;
	private Class<? extends Item> itemClass;

	ItemEnum(String internalName, int ID, Class<? extends Item> itemClass, String description, String IDName)
	{
		this.internalName = internalName;
		this.ID = ID;
		this.itemClass = itemClass;
		this.description = description;
		this.IDName = IDName;
	}

	public String getStatName()
	{
		return StatCollector.translateToLocal(internalName);
	}

	public String getInternalName()
	{
		return internalName;
	}

	public void setID(int ID)
	{
		this.ID = ID;
	}

	public int getID()
	{
		return ID;
	}

	public void setItemInstance(Item item)
	{
		this.item = item;
	}

	public Item getItemInstance()
	{
		return item;
	}

	public String getDescription()
	{
		return description;
	}

	public String getIDName()
	{
		return IDName;
	}

	public Class<? extends Item> getItemClass()
	{
		return itemClass;
	}
	
}
