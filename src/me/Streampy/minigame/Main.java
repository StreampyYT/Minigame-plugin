package me.Streampy.minigame;

import org.bukkit.plugin.java.JavaPlugin;

import me.Streampy.minigame.commands.map;
import me.Streampy.minigame.library.EventsHandler;
import me.Streampy.minigame.library.functions;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		new EventsHandler(this);
		functions.loadAll();
		getCommand("map").setExecutor(new map(this));
	}
	
	public void onDisable() {
		functions.saveAll();
	}

}
