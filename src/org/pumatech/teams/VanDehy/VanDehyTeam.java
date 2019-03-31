package org.pumatech.teams.VanDehy;

import java.awt.Color;

import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Location;

public class VanDehyTeam extends Team {

	public VanDehyTeam() {
		this(Color.PINK);
	}
	
	public VanDehyTeam(Color color) {
		this("Team Van Dehy", color);
	}
	
	public VanDehyTeam(String name, Color color) {
		super(name, color);
		addPlayer(new VanDehyDefense(new Location(12,35),new Location(12,12)));
		addPlayer(new VanDehyDefense(new Location(24,35),new Location(12,36)));
		addPlayer(new VanDehyDefense(new Location(36,35),new Location(36,12)));
		addPlayer(new VanDehyDefense(new Location(23,38),new Location(36,36)));
		addPlayer(new VanDehyDefense(new Location(25, 38),new Location(24,10)));

		addPlayer(new VanDehyOffense(new Location(1, 39)));
		addPlayer(new VanDehyOffense(new Location(24, 39)));
		addPlayer(new VanDehyOffense(new Location(48, 39)));
	}

}
