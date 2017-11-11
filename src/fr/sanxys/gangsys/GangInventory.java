package fr.sanxys.gangsys;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GangInventory {
	
	public abstract String name();
	
	public abstract void contents(Player player, Inventory inv);
	
	public abstract void onClick(Player player, Inventory inv, ItemStack current, int slot);
	
	public abstract int getSize();

}
