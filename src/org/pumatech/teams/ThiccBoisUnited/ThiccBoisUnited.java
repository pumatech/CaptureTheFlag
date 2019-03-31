package org.pumatech.teams.ThiccBoisUnited;

import java.awt.Color;

import org.pumatech.CTF2018.Team;
import org.pumatech.teams.sample.BeelinePlayer;
import org.pumatech.teams.sample.RandomPlayer;

import info.gridworld.grid.Location;

public class ThiccBoisUnited extends Team {
	public ThiccBoisUnited() {
		this(Color.RED);
	}

	public ThiccBoisUnited(Color color) {
		this("Daddy Team", color);
	}

	public ThiccBoisUnited(String name, Color color) {
		super(name, color);
		addPlayer(new Bear(new Location(5, 30)));
		addPlayer(new AStar(new Location(10, 30)));
		addPlayer(new AStar(new Location(15, 30)));
		addPlayer(new Bear(new Location(20, 30)));
		addPlayer(new Bear(new Location(30, 30)));
		addPlayer(new AStar(new Location(35, 30)));
		addPlayer(new AStar(new Location(40, 30)));
		addPlayer(new Bear(new Location(45, 30)));
	}
}