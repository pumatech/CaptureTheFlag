package org.pumatech.teams.danielRuiFanClub;

import java.awt.Color;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class danielRuiFanClub extends Team {

	public danielRuiFanClub() {
		this(Color.ORANGE);
	}
	
	public danielRuiFanClub(Color color) {
		this("danielRuiFanClub", color);
	}
	
	public danielRuiFanClub(String name, Color color) {
		super(name, color);
		
		addPlayer(new BLGuard(new Location(14, 15)));
		addPlayer(new TLGuard(new Location(14, 5)));
		addPlayer(new BRGuard(new Location(34, 15)));
		addPlayer(new TRGuard(new Location(34, 5)));
		addPlayer(new Defender(new Location(24,20)));
		addPlayer(new Attacker(new Location(23,20)));
		addPlayer(new Attacker(new Location(24,30)));
		addPlayer(new Attacker(new Location(25,40)));
	}

}
