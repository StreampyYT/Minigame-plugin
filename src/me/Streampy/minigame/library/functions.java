package me.Streampy.minigame.library;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.Streampy.minigame.library.records.mapRec;

public class functions {

	static ArrayList<mapRec> mapsList = records.mapsList;
	
	static File minigame = new File("plugins/minigame");
	static File maps = new File("plugins/minigame/maps.yml");
	
	static FileConfiguration mapsConfig = new YamlConfiguration();
	
	public static void saveAll() {
		if (!minigame.exists()) {
			minigame.mkdir();
		}
		
		saveMaps();
	}
	
	public static void loadAll() {
		if (minigame.exists()) {
			loadMaps();
		}
	}
	
	public static void saveMaps() {
		
		try {
			if (!maps.exists()) {
				maps.createNewFile();
			}
			mapsConfig.load(maps);
			for (int a = 0; a < mapsList.size(); a++) {
				mapRec mapRecord = mapsList.get(a);
				mapsConfig.set("map." + a + ".name", mapRecord.name);
				mapsConfig.set("map." + a + ".spawn.x", mapRecord.spawnLoc.getX());
				mapsConfig.set("map." + a + ".spawn.y", mapRecord.spawnLoc.getY());
				mapsConfig.set("map." + a + ".spawn.z", mapRecord.spawnLoc.getZ());
				mapsConfig.set("map." + a + ".spawn.world", mapRecord.spawnLoc.getWorld().getName());
				mapsConfig.set("map." + a + ".sign.x", mapRecord.sign.getX());
				mapsConfig.set("map." + a + ".sign.y", mapRecord.sign.getY());
				mapsConfig.set("map." + a + ".sign.z", mapRecord.sign.getZ());
				mapsConfig.set("map." + a + ".sign.world", mapRecord.sign.getWorld().getName());
				mapsConfig.set("map." + a + ".maxplayers", mapRecord.maxspelers);
				mapsConfig.save(maps);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void loadMaps() {
		try {
			if (maps.exists()) {
				mapsConfig.load(maps);
				for(int a = 0; mapsConfig.contains("map." + a); a++) {
					mapRec mapRecord = new mapRec();
					mapsList.add(mapRecord);
					
					Location spawnLoc = new Location(Bukkit.getWorld(mapsConfig.getString("map." + a + ".spawn.world")),mapsConfig.getInt("map." + a + ".spawn.x"),mapsConfig.getInt("map." + a + ".spawn.y"),mapsConfig.getInt("map." + a + ".spawn.z"));
					Location blockloc = new Location(Bukkit.getWorld(mapsConfig.getString("map." + a + ".sign.world")),mapsConfig.getInt("map." + a + ".sign.x"),mapsConfig.getInt("map." + a + ".sign.y"),mapsConfig.getInt("map." + a + ".sign.z"));
					Block block = Bukkit.getWorld(mapsConfig.getString("map." + a + ".sign.world")).getBlockAt(blockloc);
					
					mapRecord.name = mapsConfig.getString("map." + a + ".name");
					mapRecord.spawnLoc = spawnLoc;
					mapRecord.sign = block;
					mapRecord.maxspelers = mapsConfig.getInt("map." + a + ".maxplayers");
					
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
}
