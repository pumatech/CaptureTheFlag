package org.pumatech.teams.twice;

import java.awt.Color;

import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Location;

public class TeamTwice extends Team {

	public TeamTwice() {
		this(Color.MAGENTA);
	}
	
	public TeamTwice(Color color) {
		this("Twice", color);
	}
	
	public TeamTwice(String name, Color color) {
		super(name, color);
		addPlayer(new DefensePlayer(new Location(14, 10)));
		addPlayer(new DefensePlayer(new Location(24, 20)));
		addPlayer(new DefensePlayer(new Location(34, 10)));
		addPlayer(new NavigatePlayer(new Location(7, 30)));
		addPlayer(new NavigatePlayer(new Location(15, 30)));
		addPlayer(new NavigatePlayer(new Location(25, 30)));
		addPlayer(new NavigatePlayer(new Location(35, 30)));
		addPlayer(new NavigatePlayer(new Location(47, 30)));
		//change where they start (can't start within 10 of flag
		//3 defensive players
	}

}
