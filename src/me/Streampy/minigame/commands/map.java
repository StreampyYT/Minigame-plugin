package me.Streampy.minigame.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Streampy.minigame.Main;
import me.Streampy.minigame.library.records;
import me.Streampy.minigame.library.records.mapRec;

public class map implements CommandExecutor {

	public map(Main main) {
		// TODO Auto-generated constructor stub
	}
	
	static ArrayList<mapRec> mapsList = records.mapsList;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("minigame.map")) {
				if (args.length == 0) {
					player.sendMessage("- /map create <name>");
				}else {
					switch(args[0]) {
						case "create": 
							if (player.hasPermission("minigame.map.create")) {
								if (args.length == 3) {
									for (mapRec mapRecord : mapsList) {
										if (mapRecord.name.equals(args[1].toLowerCase())) {
											player.sendMessage(ChatColor.RED + "This map is already created!");
											return false;
										}
									}
									
									String numbers = "[0-9]+";
									if (!args[2].matches(numbers)) {
										player.sendMessage(ChatColor.RED + args[2] + " isn't a number!");
										return false;
									}
									
									mapRec mapRecord = new mapRec();
									mapsList.add(mapRecord);
									mapRecord.name = args[1].toLowerCase();
									mapRecord.spawnLoc = player.getLocation();
									mapRecord.maxspelers = Integer.parseInt(args[2]);
									player.sendMessage(ChatColor.GOLD + "Map " + ChatColor.GREEN + args[1] + ChatColor.GOLD + " is created!");
								}else {
									player.sendMessage("/map create <name> <maxspelers>");
								}
							}else {
								player.sendMessage(ChatColor.RED + "You dont have the right permissions!");
							}
							break;
					}
				}
			}else {
				player.sendMessage(ChatColor.RED + "You dont have the right permissions!");
			}
		}
		return false;
	}

}
