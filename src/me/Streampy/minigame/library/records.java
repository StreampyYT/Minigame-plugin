package me.Streampy.minigame.library;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class records {

	public static class mapRec {
		public String name;
		public Location spawnLoc;
		public Block sign;
		public int maxspelers;
		public ArrayList<String> playerList = new ArrayList<String>();
	}
	
	public static ArrayList<mapRec> mapsList = new ArrayList<mapRec>();
	
}
