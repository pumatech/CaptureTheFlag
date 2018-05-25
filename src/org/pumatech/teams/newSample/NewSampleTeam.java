package org.pumatech.teams.newSample;

import java.awt.Color;

import org.pumatech.newCTF.Team;

import info.gridworld.grid.Location;

public class NewSampleTeam extends Team {

	public NewSampleTeam() {
		this(Color.DARK_GRAY);
	}
	
	public NewSampleTeam(Color color) {
		this("Sample Team", color);
	}
	
	public NewSampleTeam(String name, Color color) {
		super(name, color);
	}
	
	public void generateTeam() {
		addPlayer(new BeelinePlayer(new Location(5, 30)));
		addPlayer(new BeelinePlayer(new Location(10, 30)));
		addPlayer(new BeelinePlayer(new Location(15, 30)));
		addPlayer(new RandomPlayer(new Location(20, 30)));
		addPlayer(new RandomPlayer(new Location(30, 30)));
		addPlayer(new RandomPlayer(new Location(35, 30)));
		addPlayer(new RandomPlayer(new Location(40, 30)));
		addPlayer(new RandomPlayer(new Location(45, 30)));
	}

}
