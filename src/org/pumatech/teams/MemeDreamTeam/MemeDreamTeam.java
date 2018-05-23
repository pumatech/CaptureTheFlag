package org.pumatech.teams.MemeDreamTeam;

import java.awt.Color;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class MemeDreamTeam extends Team {

	public MemeDreamTeam() {
		this(Color.PINK);
	}

	public MemeDreamTeam(Color color) {
		this("Meme Dream Team", color);
	}

	public MemeDreamTeam(String name, Color color) {
		super(name, color);
		addPlayer(new DefensePlayer(new Location(5, 30)));
		addPlayer(new DefensePlayer(new Location(45, 30)));
		addPlayer(new DefensePlayer(new Location(15, 30)));
		addPlayer(new DefensePlayer(new Location(20, 30)));
		 addPlayer(new TaggerPlayer(new Location(30, 30)));
		 addPlayer(new TaggerPlayer(new Location(35, 30)));
		 addPlayer(new OffensePlayer(new Location(40, 30)));
		 addPlayer(new OffensePlayer(new Location(10, 30)));
	}

}
