package org.pumatech.teams.BestTeam;

import java.awt.Color;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class BestTeam extends Team {

	public BestTeam() {
		this(Color.GREEN);
	}
	
	public BestTeam(Color color) {
		this("Best Team", color);
	}
	
	public BestTeam(String name, Color color) {
		super(name, Color.GREEN);
		addPlayer(new Dfence(new Location(5, 30)));
		addPlayer(new Dfence(new Location(10, 30)));
		addPlayer(new Dfence(new Location(15, 30)));
		addPlayer(new Ofence(new Location(20, 30),0));
		addPlayer(new Ofence(new Location(30, 30),1));
		addPlayer(new Ofence(new Location(35, 30),2));
		addPlayer(new Ofence(new Location(40, 30),3));
		addPlayer(new Ofence(new Location(45, 30),4));
	}

}
