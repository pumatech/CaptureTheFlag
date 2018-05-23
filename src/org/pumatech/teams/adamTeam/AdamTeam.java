package org.pumatech.teams.adamTeam;

import java.awt.Color;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class AdamTeam extends Team {

	public AdamTeam() {
		this(Color.DARK_GRAY);
	}
	
	public AdamTeam(Color color) {
		this("Sample Team", color);
	}
	
	public AdamTeam(String name, Color color) {
		super(name, color);
		addPlayer(new SouthPlayer(new Location(5, 30)));
		addPlayer(new SouthPlayer(new Location(10, 30)));
		addPlayer(new NorthDefensivePlayer(new Location(15, 30)));
		addPlayer(new NorthDefensivePlayer(new Location(20, 30)));
		addPlayer(new SouthDefensivePlayer(new Location(30, 30)));
		addPlayer(new SouthDefensivePlayer(new Location(35, 30)));
		addPlayer(new NorthPlayer(new Location(40, 30)));
		addPlayer(new NorthPlayer(new Location(45, 30)));
	}

}
