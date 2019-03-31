package org.pumatech.teams.weston;

import java.awt.Color;

import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Location;

public class WestonTeam extends Team {

	public WestonTeam(Color c) {
		super("Weston", c);
		addPlayer(new Forward(new Location(6, 49)));
		addPlayer(new Forward(new Location(46, 49)));
		addPlayer(new Defender(new Location(19, 10)));
		addPlayer(new Defender(new Location(24, 15)));
		addPlayer(new Defender(new Location(29, 10)));
		addPlayer(new Midfielder(new Location(10, 45)));
		addPlayer(new Midfielder(new Location(25, 45)));
		addPlayer(new Midfielder(new Location(40, 45)));		
	}

	
	
}
