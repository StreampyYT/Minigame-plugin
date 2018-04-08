package me.Streampy.minigame.library;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Streampy.minigame.Main;
import me.Streampy.minigame.library.records.mapRec;

public class EventsHandler implements Listener {

	public EventsHandler(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	static ArrayList<mapRec> mapsList = records.mapsList;
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (event.getLine(0).toLowerCase().equals("[minigame]")) {
			if (player.hasPermission("minigame.sign.create")) {
				event.setLine(0, ChatColor.GREEN + "[Minigame]");
				boolean mapExist = false;
				for (mapRec mapRecord : mapsList) {
					if (event.getLine(1).toLowerCase().equals(mapRecord.name)) {
						event.setLine(1, ChatColor.GREEN + event.getLine(1).toLowerCase());
						event.setLine(3, "0 / " + mapRecord.maxspelers);
						mapExist = true;
						mapRecord.sign = event.getBlock();
						break;
					}
				}
				
				if (mapExist == false) {
					event.setLine(1, ChatColor.RED + "Map doens't exist!");
				}
			}else {
				event.setLine(0, ChatColor.RED + "No permission");
			}
		}
	}
	
	@EventHandler
	public void onGameLeaveEvent(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		for (mapRec mapRecord : mapsList) {
			for (int a = 0; a < mapRecord.playerList.size(); a++) {
				String target = mapRecord.playerList.get(a);
				if (player.getUniqueId().toString().equals(target)) {
					mapRecord.playerList.remove(a);
					if (mapRecord.sign.getState() instanceof Sign) {
						Sign sign = (Sign) mapRecord.sign.getState();
						sign.setLine(3, mapRecord.playerList.size() + " / " + mapRecord.maxspelers);
						sign.update();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action act = event.getAction();
		Player player = event.getPlayer();
		
		if (act == Action.RIGHT_CLICK_BLOCK) {
			//join game
			if (event.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) event.getClickedBlock().getState();
				if (sign.getLine(0).equals(ChatColor.GREEN + "[Minigame]")) {
					if (player.hasPermission("minigame.sign.interact")) {
						for (mapRec mapRecord : mapsList) {
							if (mapRecord.name.toLowerCase().equals(ChatColor.stripColor(sign.getLine(1)))) {
								for (String players : mapRecord.playerList) {
									if (players.equals(player.getUniqueId().toString())) {
										player.sendMessage(ChatColor.RED + "You are already in the game!");
										return;
									}
								}
								if (mapRecord.maxspelers > mapRecord.playerList.size()) {
									mapRecord.playerList.add(player.getUniqueId().toString());
									
									player.teleport(mapRecord.spawnLoc);
									player.sendMessage(ChatColor.GREEN + "You got Teleported to your game!");
									ItemStack bed = new ItemStack(Material.BED);
									ItemMeta bedmeta = bed.getItemMeta();
									bedmeta.setDisplayName(ChatColor.RED + "Leave game");
									bed.setItemMeta(bedmeta);
									player.getInventory().addItem(bed);
									sign.setLine(3, mapRecord.playerList.size() + " / " + mapRecord.maxspelers);
									sign.update();
								}else {
									player.sendMessage(ChatColor.RED + "This game is Full!");
								}
								//toevoegen aan game!
							}
						}
					}else {
						player.sendMessage(ChatColor.RED + "Je hebt geen permission!");
					}
				}
			}
		}else if (act == Action.RIGHT_CLICK_AIR ) {
			//leave game with bed
			if (event.getItem() == null || event.getItem().getType().equals(Material.AIR)) {
				return;
			}
			
			if (event.getItem().getType().equals(Material.BED)) {
				for (mapRec mapRecord : mapsList) {
					for (int a = 0; a < mapRecord.playerList.size(); a++) {
						String players = mapRecord.playerList.get(a);
						if (players.equals(player.getUniqueId().toString())) {
							mapRecord.playerList.remove(a);
							player.sendMessage(ChatColor.RED + "You left the game!");
							if (mapRecord.sign.getState() instanceof Sign) {
								Sign sign = (Sign) mapRecord.sign.getState();
								sign.setLine(3, mapRecord.playerList.size() + " / " + mapRecord.maxspelers);
								sign.update();
								player.getInventory().clear();
							}
							return;
						}
					}
				}
				player.sendMessage(ChatColor.RED + "You didn't joined a game!");
			}
		}
	}
}
