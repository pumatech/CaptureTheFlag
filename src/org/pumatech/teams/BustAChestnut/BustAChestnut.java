package org.pumatech.teams.BustAChestnut;

import java.awt.Color;

import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Location;

public class BustAChestnut extends Team {
	
	public BustAChestnut() {
		this(Color.CYAN);
	}
	
	public BustAChestnut(Color color) {
		this("Bust A Chestnut", color);
	}
	
	public BustAChestnut(String name, Color color) {
		super(name, color);
		addPlayer(new CircleDefense(new Location (14, 10)));
		addPlayer(new CircleDefense(new Location (34, 10)));
		addPlayer(new CircleDefense(new Location (24, 20)));
		addPlayer(new TrackerJacker(new Location (4, 20)));
		addPlayer(new NoseTackle(new Location(25, 45)));
		addPlayer(new EdgeRusher(new Location(35, 45)));
		addPlayer(new EdgeRusher(new Location(15, 45)));
		addPlayer(new EdgeRusher(new Location(10, 45)));
	}
	

}
