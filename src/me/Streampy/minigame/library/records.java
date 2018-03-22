package me.Streampy.minigame.library;

import java.util.ArrayList;

import org.bukkit.Location;

public class records {

	public static class mapRec {
		public String name;
		public Location spawnLoc;
		public int maxspelers;
		public ArrayList<String> playerList = new ArrayList<String>();
	}
	
	public static ArrayList<mapRec> mapsList = new ArrayList<mapRec>();
	
}
