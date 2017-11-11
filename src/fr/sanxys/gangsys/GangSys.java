package fr.sanxys.gangsys;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public class GangSys extends JavaPlugin implements Listener{
	
	public Map<Class<? extends GangInventory>, GangInventory > registeredMenus = new HashMap<>();
	public Team lesBraqueurs;
	
	@Override
	public void onEnable() {
		if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("LesBraqueurs") == null) {
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("LesBraqueurs");
			lesBraqueurs = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("LesBraqueurs");
			lesBraqueurs.setPrefix(ChatColor.GREEN + "Braqueurs " + ChatColor.RESET);
		} else {
			lesBraqueurs = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("LesBraqueurs");
			lesBraqueurs.setPrefix(ChatColor.GREEN + "Braqueurs " + ChatColor.RESET);
		}
		
		getServer().getPluginManager().registerEvents(this, this);
		CommandExecutor executor = this;
		getCommand("gang").setExecutor(executor);
		addMenu(new GangMenu(this));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gang")) {
			Player player = (Player)sender ;
			open(player, GangMenu.class);
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event){
	
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		ItemStack current = event.getCurrentItem();
		
		if(event.getCurrentItem() == null) return;
		
		registeredMenus.values().stream()
		.filter(menu -> inv.getName().equalsIgnoreCase(menu.name()))
		.forEach(menu -> {
			player.closeInventory();
			menu.onClick(player, inv, current, event.getSlot());
			event.setCancelled(true);
		});
		
	}

	public void addMenu(GangInventory m){
		this.registeredMenus.put(m.getClass(), m);
	}

	public void open(Player player, Class<? extends GangInventory> gClass){
		
		if(!this.registeredMenus.containsKey(gClass)) return;

		GangInventory menu = this.registeredMenus.get(gClass);
		Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
		menu.contents(player, inv);
		player.openInventory(inv);
		
	}
	
}
