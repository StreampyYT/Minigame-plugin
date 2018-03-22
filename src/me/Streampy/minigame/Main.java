package me.Streampy.minigame;

import org.bukkit.plugin.java.JavaPlugin;

import me.Streampy.minigame.commands.map;
import me.Streampy.minigame.library.EventsHandler;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		new EventsHandler(this);
		getCommand("map").setExecutor(new map(this));
	}
	
	public void onDisable() {
		
	}

}
