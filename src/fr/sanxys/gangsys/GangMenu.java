package fr.sanxys.gangsys;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GangMenu implements GangInventory {
	
	private GangSys main;

	public GangMenu(GangSys main) { this.main = main; }

	@Override
	public String name() {
		return "Gangs de PrisonLife";
	}

	@Override
	public void contents(Player player, Inventory inv) {
		
		//@SuppressWarnings("deprecation")
		//ItemStack lesbraqueurs = new ItemStack(Material.getMaterial(6421));
		ItemStack lesbraqueurs = new ItemStack(Material.APPLE);
		ItemMeta lesbraqueursmeta = lesbraqueurs.getItemMeta();
		lesbraqueursmeta.setDisplayName("Les Braqueurs");
		lesbraqueursmeta.setLore(Arrays.asList("Gang achetable sur la Boutique !", "http://prisonlife.fr/shop/", "Chef: Egon_Girard"));
		lesbraqueurs.setItemMeta(lesbraqueursmeta);
		
		inv.setItem(4, lesbraqueurs);
		
	}

	@Override
	public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
		
		switch(current.getItemMeta().getDisplayName()){
					
			case "Les Braqueurs":
				if(player.hasPermission("prisonlife.braqueur")){
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit Prisonnier " + player.getName());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "skin Braqueur " + player.getName());
					World world = player.getWorld();
					int x = -150;
					int y = 52;
					int z = 132;
					player.teleport(new Location(world, x, y, z));
					Bukkit.broadcastMessage(ChatColor.BLUE + "[NOTIFICATION] " + ChatColor.GRAY + player.getName() + " est maintenant un Braqueur !");
					main.lesBraqueurs.addPlayer(player);
				} else {
					player.sendMessage(ChatColor.RED + "Vous devez être un Braqueur pour choisir ce Métier !");
				}
				break;
		
			default:
				break;
				
		}
		
	}

	@Override
	public int getSize() {
		return 9;
	}

	
}
